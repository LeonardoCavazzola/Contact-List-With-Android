package com.example.androidt2;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.androidt2.dao.ContactDao;
import com.example.androidt2.model.Contact;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getContact();
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

            textViewNomeView.setText(contact.getNome());
            textViewTelefoneView.setText(contact.getTelefone());
            textViewObsView.setText(contact.getObservacao());
            imageViewView.setImageBitmap(contact.getFoto());
        }
    }
}
