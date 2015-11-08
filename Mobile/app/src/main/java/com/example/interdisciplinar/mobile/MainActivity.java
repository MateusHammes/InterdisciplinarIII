package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import Enum.NegocioTipo;
public class MainActivity extends AppCompatActivity {

    private Button btnTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void CallGrupo(View v) {
        Intent intent = new Intent(this, GrupoActivity.class);
        startActivity(intent);
    }
    public void CallNegocios(View view){
        Intent intent = new Intent(this,NegocioActivity.class);
        intent.putExtra("TIPO", NegocioTipo.Negocio);
        startActivity(intent);
    }

    public void CallOrcamentos(View view){
        Intent intent = new Intent(this,NegocioActivity.class);
        intent.putExtra("TIPO", NegocioTipo.Orcamento);
        startActivity(intent);
    }
    public void CallMateriais(View view){
        Intent intent = new Intent(this,MateriaisActivity.class);
        startActivity(intent);
    }

}
