package com.example.interdisciplinar.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import model.Negocio;

public class NegocioActivityDetails extends AppCompatActivity {

    private Negocio negocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_details);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            negocio =(Negocio) bundle.getSerializable("NEGOCIO");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_negocio_activity_details, menu);
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


    //region Get e Set Values

    private void SetValues(Negocio item){

    }


    //endregion

}
