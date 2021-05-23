package com.example.androidt2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.model.Contact;
import com.example.androidt2.rvAdapter.ContactAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
//        SwipeHelper swipeHelper = new SwipeHelper( this, rv,150){
//
//            @Override
//            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<SwipeHelper.MyButton> buffer) {
//                buffer.add(new MyButton(MainActivity.this,
//                        "Eliminar",
//                        30,
//                        R.drawable.ic_edit_24,
//                        Color.parseColor("FF3C30"),
//                        new MyButtonClickListener(){
//
//                            @Override
//                            public void onClick(int pos) {
//                                Toast.makeText(MainActivity.this,"Eliminar", Toast.LENGTH_SHORT).show();
//                            }
//                        }));
//            }
//        };
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ContactTouchHelperCallBack());
        itemTouchHelper.attachToRecyclerView(rv); //faz deslizar
    }

    private void updateRV() {
        ContactAdapter adapter = new ContactAdapter(this, contactDao.getAllContacts());
        rv.setAdapter(adapter);
    }
}
