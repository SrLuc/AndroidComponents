package br.ufpe.classapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    private BroadcastReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtContact = findViewById(R.id.txtContact);

        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContactsHelper.showContact(this, 2, txtContact); // primeiro contato
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
        }

        // Inicia o Service com número 3
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("activityNumber", 3);
        startService(serviceIntent);

        // Botão para parar o Service
        Button btnStopService = findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(v -> stopService(new Intent(this, MyService.class)));

        //ir para power Activity
        Button powerActivityButton = findViewById(R.id.power);
        powerActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, PowerActivity.class);
            startActivity(intent);
        });

        // Cria o receiver
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {
                            Log.d("WIFI_STATE", "Wi-Fi está LIGADO e conectado");
                            Toast.makeText(context, "Wi-Fi LIGADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("WIFI_STATE", "Wi-Fi está DESCONECTADO");
                            Toast.makeText(context, "Wi-Fi DESCONECTADO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra o receiver quando a Activity está ativa
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove o receiver quando a Activity não está visível
        unregisterReceiver(wifiReceiver);
    }
}
