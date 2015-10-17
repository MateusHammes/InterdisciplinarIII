package util;

import android.app.AlertDialog;
import android.content.Context;

public class Dialog {


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

}
