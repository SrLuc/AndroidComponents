package br.ufpe.classapplication;

import static br.ufpe.classapplication.R.id.btnNext2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtContact = findViewById(R.id.txtContact);

        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 1, txtContact); // primeiro contato
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
        }



        // Inicia o Service com número 2
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("activityNumber", 2);
        startService(serviceIntent);

       Button btnNext2 = findViewById(R.id.btnNext2);
       btnNext2.setOnClickListener(v -> {
           Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
           startActivity(intent);
       });

        // Botão para parar o Service
        Button btnStopService = findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(v -> stopService(new Intent(this, MyService.class)));


    }
}