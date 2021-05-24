package com.example.androidt2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.rv.ContactAdapter;
import com.example.androidt2.rv.ContactDecoration;
import com.example.androidt2.rv.ContactTouchHelperCallBack;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        rv.setLayoutManager(new LinearLayoutManager(this));
        setUpItemTouchHelper();
    }

    private void setUpItemTouchHelper() {//faz deslizar
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ContactTouchHelperCallBack(this, 0, ItemTouchHelper.LEFT));
        itemTouchHelper.attachToRecyclerView(rv);
        rv.addItemDecoration(new ContactDecoration());
    }

    private void updateRV() {
        ContactAdapter adapter = new ContactAdapter(this, contactDao.getAllContacts());
        rv.setAdapter(adapter);
    }
}
