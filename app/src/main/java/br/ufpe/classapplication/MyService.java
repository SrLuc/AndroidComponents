package br.ufpe.classapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    public int onStartCommand(Intent intent, int flags, int startId){

        int activityNumber = intent.getIntExtra("activityNumber", -1);

        Toast.makeText(this, "Service iniciado pela Activity" + activityNumber, Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service Finalizado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}