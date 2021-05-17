package com.example.androidt2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.androidt2.model.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactsDB";
    private static final String TABELA_CONTACTS = "contacts";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String TELEFONE = "telefone";
    private static final String OBSERVACAO = "observacao";
    private static final String FOTO = "foto";
    private static final String[] COLUNAS = {ID, NOME, TELEFONE, OBSERVACAO, FOTO};

    public ContactDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =

                "CREATE TABLE contacts (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome TEXT," +
                        "telefone TEXT," +
                        "observacao TEXT, " +
                        "foto BLOB)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        this.onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        contact.getFoto().compress(Bitmap.CompressFormat.PNG, 100, saida);
        byte[] img = saida.toByteArray();

        values.put(NOME, contact.getNome());
        values.put(TELEFONE, contact.getTelefone());
        values.put(OBSERVACAO, contact.getObservacao());
        values.put(FOTO, img);

        db.insert(TABELA_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_CONTACTS, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[]{String.valueOf(id)}, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            return cursorToContact(cursor);
        }
    }

    private Contact cursorToContact(Cursor cursor) {
        //int id = cursor.getInt(0);
        String nome = cursor.getString(1);
        String telefone = cursor.getString(2);
        String observacao = cursor.getString(3);
        byte[] blob = cursor.getBlob(4);
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);

        return new Contact(nome, telefone, bitmap, observacao);
    }

    public List<Contact> getAllContacts() {
        ArrayList<Contact> listaLivros = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_CONTACTS + " ORDER BY " + NOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = cursorToContact(cursor);
                listaLivros.add(contact);
            } while (cursor.moveToNext());
        }
        return listaLivros;
    }

//    public int updateLivro(Livro livro) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(TITULO, livro.getTitulo());
//        values.put(AUTOR, livro.getAutor());
//        values.put(ANO, new Integer(livro.getAno()));
//        int i = db.update(TABELA_LIVROS, //tabela
//                values, // valores
//                ID + " = ?", // colunas para comparar
//                new String[]
//                        { String.valueOf(livro.getId()) }); //parâmetros
//        db.close();
//        return i; // número de linhas modificadas
//    }


//    public int deleteLivro(Livro livro) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int i = db.delete(TABELA_LIVROS, //tabela
//                ID + " = ?", // colunas para comparar
//                new String[]
//                        { String.valueOf(livro.getId()) });
//        db.close();
//        return i; // número de linhas excluídas
//    }
}
