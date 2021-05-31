package com.example.androidt2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.rv.ContactAdapter;
import com.example.androidt2.rv.DeleteContactTouchHelperCallBack;
import com.example.androidt2.rv.UpdateContactTouchHelperCallBack;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lista de Contatos");

        this.initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateRV();
    }

    private void initComponents() {
        findViewById(R.id.floatingActionButton).setOnClickListener(v -> startActivity(new Intent(this, AddActivity.class)));
        this.rv = findViewById(R.id.rv);
        this.contactDao = new ContactDao(this);

        ContactAdapter adapter = new ContactAdapter(this, contactDao.getAllContacts());//
        rv.setAdapter(adapter);//
        setUpItemTouchHelper();
    }

    private void setUpItemTouchHelper() {//faz deslizar
        ItemTouchHelper itemTouchHelperU = new ItemTouchHelper(new UpdateContactTouchHelperCallBack(this, 0, ItemTouchHelper.LEFT));
        itemTouchHelperU.attachToRecyclerView(rv);

        ItemTouchHelper itemTouchHelperD = new ItemTouchHelper(new DeleteContactTouchHelperCallBack(this, 0, ItemTouchHelper.RIGHT));
        itemTouchHelperD.attachToRecyclerView(rv);
    }

    public void updateRV() {
        ContactAdapter adapter = new ContactAdapter(this, contactDao.getAllContacts());
        rv.setAdapter(adapter);
    }
}
