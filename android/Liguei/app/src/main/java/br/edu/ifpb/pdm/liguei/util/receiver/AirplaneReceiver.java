package br.edu.ifpb.pdm.liguei.util.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Cuidado! Você acabou de mexer como o modo de avião, talvez sem querer...", Toast.LENGTH_LONG).show();
    }
}
