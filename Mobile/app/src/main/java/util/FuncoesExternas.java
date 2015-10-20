package util;


import android.widget.EditText;

public class FuncoesExternas {

    public static boolean Valida( EditText txt){
        boolean var=true;
        if(txt.getText().toString().trim().isEmpty()) {
            var = false;
            txt.setError("Campo Obrigatório");
           // txt.setFocusable(true);
            //txt.requestFocus();
        }else
            txt.setError(null);

        return var;
    }

}
