package com.example.androidt2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.model.Contact;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextPhone;
    private EditText editTextObs;
    private Bitmap photo = null;
    private ContactDao contactDao;
    private int operacao = AddActivity.CREATE;
    private int idUpdate;

    public static int CREATE = 1;
    public static int UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.initComponents();
        this.permicaoGaleria();
        this.checkUpdate();
        setTitle("Adicionar/Atualizar Contato");
    }

    private void checkUpdate(){
        Bundle extra = getIntent().getExtras();
        int value;
        if(extra != null){
            value = extra.getInt("ContactID");
            Contact contact = contactDao.getContact(value);

            this.editTextNome.setText(contact.getNome());
            this.editTextPhone.setText(contact.getTelefone());
            this.editTextObs.setText(contact.getObservacao());
            this.setPhoto(contact.getFoto());
            this.operacao = AddActivity.UPDATE;
            this.idUpdate = contact.getId();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            setPhoto((Bitmap) extras.get("data"));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                setPhoto(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Parece que isso não é uma imagem", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initComponents() {
        this.editTextNome = findViewById(R.id.editTextNome);
        this.editTextPhone = findViewById(R.id.editTextPhone);
        this.editTextObs = findViewById(R.id.editTextObs);
        this.contactDao = new ContactDao(this);
    }

    private void permicaoGaleria() {

        int permissionGranted = PackageManager.PERMISSION_GRANTED;
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if ((checkSelfPermission != permissionGranted) && !shouldShowRequestPermissionRationale) {
            String[] a = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, a, 2);
        }
    }

    public void takePhotoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }

    public void selectPhotoClick(View view) {

        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, 2);
    }

    private void setPhoto(Bitmap bitmap) {
        ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);

        imageViewPhoto.setImageBitmap(bitmap);
        this.photo = bitmap;
    }

    public void save(View view) {
        String nome = this.editTextNome.getText().toString();
        String telefone = this.editTextPhone.getText().toString();
        String obs = this.editTextObs.getText().toString();

        if(nomeValidate() & phoneValidate() & photoValidate()){
            if(this.operacao == AddActivity.CREATE) {
                Contact contact = new Contact(nome, telefone, this.photo, obs);
                this.contactDao.addContact(contact);
            } else {
                Contact contact = new Contact(idUpdate ,nome, telefone, this.photo, obs);
                this.contactDao.updateContact(contact);
            }
            this.finish();
        }
    }

    public Boolean nomeValidate(){
        TextInputLayout nomeLayout = findViewById(R.id.nomeLayout);
        String nome = this.editTextNome.getText().toString();

        if(nome.equals("")){
            nomeLayout.setError("Nome é obrigatório!");
            return false;
        } else {
            nomeLayout.setError(null);
            return true;
        }
    }

    public Boolean phoneValidate(){
        TextInputLayout phoneLayout = findViewById(R.id.phoneLayout);
        String phone = this.editTextPhone.getText().toString();

        if(phone.equals("")){
            phoneLayout.setError("Telefone é obrigatório!");
            return false;
        } else {
            phoneLayout.setError(null);
            return true;
        }
    }

    public Boolean photoValidate(){
        Button takePictureButton = findViewById(R.id.takePictureButton);
        Button galleryButton = findViewById(R.id.galleryButton);
        TextView photoHelp = findViewById(R.id.photoHelp);

        if(this.photo == null){
            int colorError = Color.parseColor("#B00020");

            takePictureButton.setBackgroundColor(colorError);
            galleryButton.setBackgroundColor(colorError);
            photoHelp.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            int color = Color.parseColor("#2c2c2c");

            takePictureButton.setBackgroundColor(color);
            galleryButton.setBackgroundColor(color);
            photoHelp.setVisibility(View.INVISIBLE);
            return true;
        }
    }
}
