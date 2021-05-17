package com.example.androidt2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.rvAdapter.ContactAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.floatingActionButton)
                .setOnClickListener(v -> startActivity(new Intent(this, AddActivity.class)));

        this.rv = findViewById(R.id.rv);
        this.initRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initRV();
    }

    private void initRV() {
        ContactDao contactDao = new ContactDao(this);

        ContactAdapter adapter = new ContactAdapter(this, contactDao.getAllContacts());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }
}
