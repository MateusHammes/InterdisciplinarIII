package util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Dialog {
    static private boolean bool;
    static private ProgressDialog PD;

    public static void ShowAlertError(Context ctx){
        Show(ctx, "Erro", "Ops, houve um imprevisto, favor tente novamente!", android.R.drawable.ic_dialog_alert);
    }

    public static void ShowAlert(Context ctx, String titulo, String message){
        Show(ctx, titulo, message, android.R.drawable.ic_dialog_alert);
    }

    public static void Show(Context ctx, String titulo, String message){
        Show(ctx, titulo, message, 0);
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
        return bool;
    }

    public static  void ShowProgressDialog(Context ctx){
        PD = new ProgressDialog(ctx);
        PD.setTitle("Salvando...");
        PD.setIndeterminate(true);
        PD.setCancelable(false);
        PD.setMessage("Por favor, espere um momento");
        PD.show();
    }

    public static void CancelProgressDialog(){
        try {
            PD.cancel();
        }catch (Exception e){
            Log.e("errro",e.toString());
        }
    }
}
