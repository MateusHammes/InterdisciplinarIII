package com.example.interdisciplinar.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.List;

import DAO.GrupoDAO;
import DAO.MateriaisDAO;
import model.Grupo;
import model.Materiais;
import util.Dialog;
import util.FuncoesExternas;

public class MateriaisActivityForm extends AppCompatActivity {

    private   Materiais material = new Materiais();
    private MateriaisDAO DAO = new MateriaisDAO();
    private Spinner spnGrupos ;
    private ArrayAdapter<Grupo>adpGrupos;
    private Grupo grupo = null;
    private ProgressBar pgg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais_form);

        spnGrupos  =(Spinner)findViewById(R.id.materialGruopoSpinner);
        adpGrupos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spnGrupos.setAdapter(adpGrupos);

        pgg = (ProgressBar)findViewById(R.id.materialProgressBarGrupos);
        pgg.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("MATERIAL")){
            material =(Materiais) bundle.getSerializable("MATERIAL");
            SetItem(material);
            grupo = material.getGrupo();
        }
        new CarregaGrupos().execute();
        Button btnSalvar = (Button)findViewById(R.id.materialBtnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarItem();
            }
        });
        Button btnCancel = (Button)findViewById(R.id.materialBtnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void GetItem(){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);
        EditText txtDescricao = (EditText)findViewById(R.id.materialTxtDescricao);

        if(material==null)
            material=new Materiais();

        material.setGrupo((Grupo) spnGrupos.getSelectedItem());
        material.setMtr_vnome(txtNome.getText().toString());
        material.setMtr_nvalor(Double.parseDouble(txtvalor.getText().toString().replace(",",".")));
        material.setMtr_vdescricao(txtDescricao.getText().toString());
        material.setMtr_iestoque(Integer.parseInt(txtEstoque.getText().toString()));
    }


    public void SetItem(Materiais mtr){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);
        EditText txtDescricao = (EditText)findViewById(R.id.materialTxtDescricao);
        txtNome.setText(mtr.getMtr_vnome());
        txtDescricao.setText(mtr.getMtr_vdescricao());
        txtEstoque.setText(String.valueOf(mtr.getMtr_iestoque()));
        txtvalor.setText(""+DecimalFormat.getInstance().format(mtr.getMtr_nvalor()));
    }

    private void SalvarItem(){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);

        if(FuncoesExternas.Valida(txtNome))
            if(FuncoesExternas.Valida(txtvalor))
                if(FuncoesExternas.Valida(txtEstoque)){
                    GetItem();
                    Dialog.ShowProgressDialog(MateriaisActivityForm.this);
                    Log.i("vai ", "sarva!!");
                    new Salvar().execute();
                }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private class Salvar extends AsyncTask<Materiais, String, Boolean>{

        @Override
        protected Boolean doInBackground(Materiais... params) {
            try {
                return DAO.Salvar(material);
            }catch (Exception e){
                Log.e("ERRRO!",e.toString());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                MateriaisActivity.GoLoad = true;
                MateriaisActivity.ClearList=true;
                Dialog.CancelProgressDialog();
                finish();
            }else {
                Dialog.CancelProgressDialog();
                Dialog.ShowAlertError(MateriaisActivityForm.this);
            }
        }
    }

    private class CarregaGrupos extends AsyncTask<Grupo, String, List<Grupo>>{
        GrupoDAO gDAO =new GrupoDAO();
        @Override
        protected List<Grupo> doInBackground(Grupo... params) {
            try {
                return gDAO.SelecionaTodosGrupo();
            }catch (Exception e){
                Log.e("ERROR",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Grupo> lsGrupos) {
            super.onPostExecute(lsGrupos);
            Grupo gSelect = new Grupo();
            if(lsGrupos!=null && lsGrupos.size()>0) {
                for (Grupo gp : lsGrupos) {
                    adpGrupos.add(gp);
                    if(grupo!=null && gp.getGru_codigo()==grupo.getGru_codigo()) {
                        gSelect = gp;
                    }
                }
                spnGrupos.setAdapter(adpGrupos);
                if(grupo!=null) {
                    spnGrupos.setSelection(adpGrupos.getPosition(gSelect));
                }
            }
            pgg.setVisibility(View.GONE);
        }
    }

}
