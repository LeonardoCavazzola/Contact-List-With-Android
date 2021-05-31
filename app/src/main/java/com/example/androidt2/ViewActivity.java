package com.example.androidt2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.model.Contact;

public class ViewActivity extends AppCompatActivity {

    String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getContact();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero =  telefone;
                Uri uri = Uri.parse("tel:"+numero);

                Intent intent = new Intent(Intent.ACTION_CALL,uri);
                if(ActivityCompat.checkSelfPermission(ViewActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ViewActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(intent);
            }
        });
    }

    private void getContact(){
        Bundle extra = getIntent().getExtras();
        int value;
        if(extra != null){
            value = extra.getInt("ContactID");
            Contact contact = new ContactDao(this).getContact(value);

            TextView textViewNomeView = findViewById(R.id.textViewNomeView);
            TextView textViewTelefoneView = findViewById(R.id.textViewTelefoneView);
            TextView textViewObsView = findViewById(R.id.textViewObsView);
            ImageView imageViewView = findViewById(R.id.imageViewView);

            telefone = contact.getTelefone();

            textViewNomeView.setText(contact.getNome());
            textViewTelefoneView.setText(contact.getTelefone());
            textViewObsView.setText(contact.getObservacao());
            imageViewView.setImageBitmap(contact.getFoto());
        }
    }
}
