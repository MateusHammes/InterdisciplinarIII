package util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Dialog {
    static private boolean bool;

    public static void ShowAlert(Context ctx, String titulo, String message){
        Show(ctx, titulo, message, android.R.drawable.ic_dialog_alert);
    }

    public static void Show(Context ctx, String titulo, String message){
        Show(ctx, titulo, message,0);
    }

    public static void Show(Context ctx, String titulo, String message, int iconId){
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setIcon(iconId);
        alert.setTitle(titulo);
        alert.setMessage(message);
        alert.setNeutralButton("Ok", null);
        alert.show();
    }
    public static boolean ShowDialog(Context ctx, String titulo, String msm){
        return ShowDialog(ctx, titulo, msm, "Sim", "Não");
    }

    public static boolean ShowDialog(Context ctx, String titulo, String msm, String textBtnPositive, String textBtnNegative){
        bool = false;
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        Log.i("DIALOG", "TAMO AKI");
        dialog.setTitle(titulo);
        dialog.setMessage(msm);
        dialog.setPositiveButton(textBtnPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                bool = true;
            }
        });

        dialog.setNegativeButton(textBtnNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bool = false;
            }
        });
        dialog.show();
       /* new AlertDialog.Builder(ctx).setTitle(titulo).setMessage(msm).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bool = true;
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bool = false;
            }
        });*/
        return bool;
    }

    public static  void ShowProgressDialog(Context ctx, String titulo, String msn){

    }
    public static void CancelProgressDialog(){

    }
}
