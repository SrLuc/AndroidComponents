package br.ufpe.classapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.TextView;
import android.os.PowerManager;


import androidx.appcompat.app.AppCompatActivity;

public class PowerActivity extends AppCompatActivity {

    private TextView txtPowerState;
    private BroadcastReceiver idleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        txtPowerState = findViewById(R.id.txtPowerState);

        // Atualiza o estado inicial
        updatePowerState();

        // Cria BroadcastReceiver para mudanças de idle mode
        idleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED.equals(intent.getAction())) {
                    updatePowerState();
                }

            }
        };
    }

    private void updatePowerState() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        String state = "";

        if (pm.isDeviceIdleMode()) {
            state += "Idle Mode Ativo\n";
        } else {
            state += "Idle Mode Inativo\n";
        }

        if (pm.isInteractive()) {
            state += "Dispositivo Interativo\n";
        } else {
            state += "Dispositivo Não Interativo\n";
        }

        if (pm.isPowerSaveMode()) {
            state += "Power Save Mode Ativo";
        } else {
            state += "Power Save Mode Inativo";
        }

        txtPowerState.setText(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra receiver
        IntentFilter filter = new IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
        registerReceiver(idleReceiver, filter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(idleReceiver);
    }
}
