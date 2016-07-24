package com.android.androidannotactions.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.android.androidannotactions.R;

/**
 * Created by vinicius on 7/22/16.
 */
public class Alertas {

    public static AlertDialog alerta(Context context, String msg) {
        if (context != null && !TextUtils.isEmpty(msg))
            return alerta(context, msg, context.getString(R.string.app_name), false);
        return null;
    }

    public static AlertDialog alerta(Context context, String msg, String titulo, final boolean btnNegative) {
        if (context != null && !TextUtils.isEmpty(msg)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog = null;
            if (TextUtils.isEmpty(titulo)) {
                titulo = context.getString(R.string.app_name);
            }
            builder.setMessage(msg)
                    .setTitle(titulo);


            if (btnNegative == false) {
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
            }

            if (btnNegative) {
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
            }
            dialog = builder.create();
            dialog.show();
            return dialog;
        }

        return null;
    }

    public static AlertDialog.Builder alerta(Context context) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog = null;
            builder.setTitle(context.getString(R.string.app_name));
            return builder;
        }

        return null;
    }

}
