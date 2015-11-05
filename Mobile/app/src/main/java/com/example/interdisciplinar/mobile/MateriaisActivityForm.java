package com.example.interdisciplinar.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    //private EditText valor;
    //private NumberFormat formatMoeda = NumberFormat.getCurrencyInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais_form);

        spnGrupos  =(Spinner)findViewById(R.id.materialGruopoSpinner);
        adpGrupos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spnGrupos.setAdapter(adpGrupos);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("MATERIAL")){
            material =(Materiais) bundle.getSerializable("MATERIAL");
            SetItem(material);
        }
        new CarregaGrupos().execute();



        Button btnSalvar = (Button)findViewById(R.id.materialBtnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetItem();
                Dialog.ShowProgressDialog(MateriaisActivityForm.this);
                new Salvar().execute();
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
        if(material.getGrupo()!=null)
            if(adpGrupos.getCount()>0) {
                adpGrupos.add(mtr.getGrupo());
                adpGrupos.notifyDataSetChanged();
                spnGrupos.setSelection(adpGrupos.getPosition(mtr.getGrupo()));
            }else
                grupo = material.getGrupo();
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);
        EditText txtDescricao = (EditText)findViewById(R.id.materialTxtDescricao);
        txtNome.setText(mtr.getMtr_vnome());
        txtDescricao.setText(mtr.getMtr_vdescricao());
        txtEstoque.setText(String.valueOf(mtr.getMtr_iestoque()));
        txtvalor.setText(""+DecimalFormat.getInstance().format(mtr.getMtr_nvalor()));
    }

    public void SalvarItem(View view){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);


        if(FuncoesExternas.Valida(txtNome))
            if(FuncoesExternas.Valida(txtvalor))
                if(FuncoesExternas.Valida(txtEstoque)){
                    GetItem();
                    new Salvar().execute();
                    Dialog.ShowProgressDialog(MateriaisActivityForm.this);
                }
    }

    @Override
    protected void onResume() {
        //final NumberFormat formatMoeda = NumberFormat.getCurrencyInstance();
        super.onResume();
        // valor =(EditText)findViewById(R.id.materialTxtValor);
        ///        valor.setInputType(InputType.TYPE_CLASS_NUMBER);

        /*  valor.addTextChangedListener(new TextWatcher() {

            boolean update = false;
            String current="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (update) {
                        update = false;
                        return;
                    }

                    update = true;
                    Log.i("----diffe---", "S:"+s.toString()+"  formm.:?"+current+" - "+s.equals(current) );
                    if (!s.equals(current)) {
                        valor.removeTextChangedListener(this);
                        String valorString = valor.getText().toString().replace("R$","").replace(",",".");
                        double parsed = Double.parseDouble(valorString);
                        String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
                        Log.i("Formatado", formatted);
                        current = formatted;
                        //  double valorM = Double.parseDouble(valorString.toString());
                        //  valor.setText("");
                        //valor.setText(""+formatMoeda.format(valorM));
                        valor.setText(formatted.toString());
                        valor.setSelection(formatted.length());
                        valor.addTextChangedListener(this);
                        // valor.setSelection(valorString.toString().length()+2);
                    }
                } catch (Exception e) {
                    Log.e("ROROROROR", e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });*/

    }



    private class Salvar extends AsyncTask<Materiais, String, Boolean>{

        @Override
        protected Boolean doInBackground(Materiais... params) {
            return DAO.Salvar(material);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                MateriaisActivity.GoLoad  =true;
                finish();
            }else
                Dialog.ShowAlertError(MateriaisActivityForm.this);
            Dialog.CancelProgressDialog();
        }
    }
    private class CarregaGrupos extends AsyncTask<Grupo, String, List<Grupo>>{
        GrupoDAO gDAO =new GrupoDAO();
        @Override
        protected List<Grupo> doInBackground(Grupo... params) {
            return gDAO.SelecionaTodosGrupo();
        }

        @Override
        protected void onPostExecute(List<Grupo> lsGrupos) {
            super.onPostExecute(lsGrupos);
            if(lsGrupos!=null) {
                for (Grupo gp : lsGrupos)
                    adpGrupos.add(gp);
                spnGrupos.setAdapter(adpGrupos);
                if(grupo!=null)
                    spnGrupos.setSelection(adpGrupos.getPosition(grupo));

            }
        }
    }

}
