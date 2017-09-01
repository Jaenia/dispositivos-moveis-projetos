package br.edu.ifpb.pdm.liguei.util.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;

public class LowBatteryReceiver extends BroadcastReceiver {
    private AlertDialog ad;

    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder ab;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ab = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        } else {
            ab = new AlertDialog.Builder(context);
        }

        ab.setTitle("BATERIA BAIXA!")
                .setMessage("Sua bateria está perto de acabar. Conecte-se a um carregador antes que ela acabe!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ad.dismiss();
                    }
                });
        ad = ab.create();
        ad.show();
    }
}
