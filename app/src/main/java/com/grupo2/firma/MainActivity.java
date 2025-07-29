package com.grupo2.firma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    EditText etDescripcion;
    Button btnGuardar, btnVerFirmas;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar elementos de la interfaz
        signaturePad = findViewById(R.id.signature_pad);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerFirmas = findViewById(R.id.btnVerFirmas);

        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(v -> guardarFirma());

        btnVerFirmas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FirmasActivity.class);
            startActivity(intent);
        });
    }

    private void guardarFirma() {
        if (signaturePad.isEmpty()) {
            Toast.makeText(this, "Dibuja una firma primero", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = signaturePad.getSignatureBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] firmaBytes = stream.toByteArray();

        String descripcion = etDescripcion.getText().toString().trim();
        if (descripcion.isEmpty()) {
            Toast.makeText(this, "Agrega una descripción", Toast.LENGTH_SHORT).show();
            return;
        }

        Signature firma = new Signature(descripcion, firmaBytes);
        dbHelper.insertarFirma(firma);

        Toast.makeText(this, "Firma guardada", Toast.LENGTH_SHORT).show();
        signaturePad.clear();
        etDescripcion.setText("");
    }
}
