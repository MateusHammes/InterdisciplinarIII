package util;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.NumberFormat;

public class FuncoesExternas {

    public static boolean Valida(EditText txt){
        boolean var=true;
        if(txt.getText().toString().trim().isEmpty()) {
            var = false;
            txt.setError("Campo Obrigatório");
            // txt.setFocusable(true);
            txt.requestFocus();
        }else
            txt.setError(null);

        return var;
    }

    public static void MascaraDinheiro(final EditText txt) {
        final NumberFormat mbf = NumberFormat.getCurrencyInstance();
       final boolean update = false;
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int start, int count, int after) {
               /* if (update) {
                    Log.i("Entro na MAskara","NO IFIFIFIFIFIIF ----");
                    update = false;
                    return;
                }*/
                Log.i("Entro na MAskara","ta verrificando");
               // update = true;
              /* String str = cs.toString();
                boolean hasMask = (str.indexOf("R$") >= 0 && str.indexOf(".") >= 0 && str.indexOf(",") >= 0);

                if (hasMask) {
                    str = str.replaceAll("[R$]", "").replaceAll("[.]", "").replaceAll("[,]", "");
                }

                try {
                    double value = (Double.parseDouble(str) / 100);
                    str = mbf.format(value);
                    txt.setText(str);
                    txt.setSelection(str.length());
                } catch (Exception e) {
                    Log.e("ROROROROR", e.toString());

                }
*/
        }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

}
