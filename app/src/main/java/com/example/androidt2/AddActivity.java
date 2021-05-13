package com.example.androidt2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.androidt2.model.Contact;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextPhone;
    private EditText editTextObs;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.initComponents();
        this.permicaoGaleria();
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

        Contact contact = new Contact(nome, telefone, this.photo, obs);

        /** aqui falta codigo para salvar **/
        /** também tem que validar os campos **/

        this.finish();
    }
}
