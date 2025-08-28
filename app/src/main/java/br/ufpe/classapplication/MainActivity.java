package br.ufpe.classapplication;

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

public class MainActivity extends AppCompatActivity {

    private TextView txtContact; // <- mover para aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtContact = findViewById(R.id.txtContact); // inicializa aqui

        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 0, txtContact); // primeiro contato
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
        }

        // Botões
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));

        Button btnStopService = findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(v -> stopService(new Intent(this, MyService.class)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 0, txtContact);
        }
    }
}

