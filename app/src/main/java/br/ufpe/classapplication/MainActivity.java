package br.ufpe.classapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView txtContact;

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

        txtContact = findViewById(R.id.txtContact);

        // Verifica permissão para contatos
        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 0, txtContact); // primeiro contato
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
        }

        PackageManager pm = getPackageManager();

        // Botão para ir para Activity 2 (verifica se está habilitada)
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            ComponentName component = new ComponentName(this, SecondActivity.class);
            int state = pm.getComponentEnabledSetting(component);
            if (state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            } else {
                Toast.makeText(this, "SecondActivity está desabilitada!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botão para parar o Service
        Button btnStopService = findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(v -> stopService(new Intent(this, MyService.class)));

        // ==== BOTÕES DE PACKAGE MANAGER ====
        Button btnDisableSecond = findViewById(R.id.btnDisableSecond);
        Button btnEnableSecond = findViewById(R.id.btnEnableSecond);
        Button btnDisableThird = findViewById(R.id.btnDisableThird);
        Button btnEnableThird = findViewById(R.id.btnEnableThird);

        // Desabilita SecondActivity
        btnDisableSecond.setOnClickListener(v -> {
            ComponentName component = new ComponentName(this, SecondActivity.class);
            pm.setComponentEnabledSetting(component,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "SecondActivity desabilitada", Toast.LENGTH_SHORT).show();
        });

        // Habilita SecondActivity
        btnEnableSecond.setOnClickListener(v -> {
            ComponentName component = new ComponentName(this, SecondActivity.class);
            pm.setComponentEnabledSetting(component,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "SecondActivity habilitada", Toast.LENGTH_SHORT).show();
        });

        // Desabilita ThirdActivity
        btnDisableThird.setOnClickListener(v -> {
            ComponentName component = new ComponentName(this, ThirdActivity.class);
            pm.setComponentEnabledSetting(component,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "ThirdActivity desabilitada", Toast.LENGTH_SHORT).show();
        });

        // Habilita ThirdActivity
        btnEnableThird.setOnClickListener(v -> {
            ComponentName component = new ComponentName(this, ThirdActivity.class);
            pm.setComponentEnabledSetting(component,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "ThirdActivity habilitada", Toast.LENGTH_SHORT).show();
        });
    }

    // Permissão de contatos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 0, txtContact);
        }
    }
}
