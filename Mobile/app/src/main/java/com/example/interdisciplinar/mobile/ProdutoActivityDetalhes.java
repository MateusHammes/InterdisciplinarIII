package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import DAO.ProdutoMaterialDAO;
import DAO.RegistrosDAO;
import Enum.RegistroStatus;
import model.Produto;
import model.Produto_material;
import model.Registros;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoActivityDetalhes extends AppCompatActivity {

    private Produto produto;
    private Registros registro;
    private RegistrosDAO DAO =new RegistrosDAO();
    private Produto_material produtMaterial = new Produto_material();
    private ArrayAdapter<Registros> adpRegistros;
    private ListView listViewRegistros;
    AlertDialog alert;
    AlertDialog alertM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("PRODUTO")){
            Log.i("TEM PRODU","ALKI detalhes");
            produto =(Produto) bundle.getSerializable("PRODUTO");
            SetValues(produto);
        }


        Button btnHome =(Button) findViewById(R.id.produtoDetalhesBtnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdutoActivityDetalhes.this, MainActivity.class);
                Log.i("Fecho","Homee");
                finish();
                startActivity(i);
            }
        });
        Button btnNegocio =(Button) findViewById(R.id.produtoDetalhesBtnNegocio);
        btnNegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Fecho", "Fechou");
                finish();
            }
        });


        Button btnMaterial  =(Button)findViewById(R.id.produtoDetalhesMaterialBtn);
        btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdutoActivityDetalhes.this, ProdutoMaterialActivity.class);
                i.putExtra("PRODUTO", produto);
                startActivity(i);
            }
        });

        Button btnRegistro = (Button)findViewById(R.id.produtoDetalhesBtnRegistros);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdutoActivityDetalhes.this, ProdutoRegistroActivity.class);
                i.putExtra("PRODUTO", produto);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  listViewRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Registros reg = adpRegistros.getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoActivityDetalhes.this);
                if (reg.getRgs_cstatus() == RegistroStatus.aberto) {
                    dialog.setTitle("Deseja completar esta Especificaçao?");
                    dialog.setNegativeButton("Completar", null);
*//*                    dialog.setMessage("Especificaçao: " + reg.getRgs_vdescricao());*//*
                    *//* dialog.setNeutralButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();*//*
                } else {
                    // new AlertDialog.Builder(ProdutoActivityDetalhes.this)
                    dialog.setTitle("Deseja abrir esta Especificaçao?");
                    dialog.setNegativeButton("abrir", null);
                }
                dialog.setMessage("Especificaçao: " + reg.getRgs_vdescricao());

                dialog.setNeutralButton(R.string.Editar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText txt = new EditText(ProdutoActivityDetalhes.this);
                        //   AlertDialog alertEdit;
                        txt.setText(reg.getRgs_vdescricao());
                        AlertDialog.Builder dialogEdit = new AlertDialog.Builder(ProdutoActivityDetalhes.this);
                        dialogEdit.setTitle(R.string.EditarEspecificacao);
                        dialogEdit.setView(txt);
                        dialogEdit.setNeutralButton(R.string.Cancelar, null);
                        dialogEdit.setNegativeButton(R.string.Salvar, null);

                        alert = dialogEdit.create();
                        alert.show();
                        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (FuncoesExternas.Valida(txt)) {
                                    registro = reg;
                                    registro.setRgs_vdescricao(txt.getText().toString());
                                    new SalvaRegistro().execute();
                                    Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                                }
                            }
                        });
                    }
                });
                dialog.setPositiveButton(R.string.Cancelar, null).show();

                alert = dialog.create();
                alert.show();
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registro = reg;
                        if (reg.getRgs_cstatus() == RegistroStatus.aberto)
                            registro.setRgs_cstatus(RegistroStatus.completo);
                        else
                            registro.setRgs_cstatus(RegistroStatus.aberto);
                        new SalvaRegistro().execute();
                        Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                    }
                });

            }
        });
        new CarregaRegistros().execute();*/
    }

   /* private void EditaValorMaterial(int position){
        final Produto_material prm = adpMateriais.getItem(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoActivityDetalhes.this);
        final EditText txt = new EditText(ProdutoActivityDetalhes.this);
        txt.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialog.setView(txt);
        dialog.setTitle("Unidades do Material");
        dialog.setMessage("Informe quantas unidades você já utilizou de " + prm.getMaterial().getMtr_vnome());
        dialog.setNegativeButton(R.string.Salvar, null);
        dialog.setNeutralButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertM = dialog.create();
        alertM.show();
        alertM.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edita produto amrterial
                if (FuncoesExternas.Valida(txt)) {
                    int unidUsadas = Integer.parseInt(txt.getText().toString());
                    if (unidUsadas > 0) {///&& unidUsadas < prm.getPrm_iunidade()
                        produtMaterial = prm;
                        //   int  unidReservadas
                        // produtMaterial.setPrm_iunidade(prm.getPrm_iunidade() - unidUsadas);
                        produtMaterial.setPrm_iunidadeUtilizada(prm.getPrm_iunidadeUtilizada() + unidUsadas);
                        Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                        new EditaProdutoMaterial().execute();
                    } else
                        txt.setError("O quantidade deve ser menor que " + prm.getPrm_iunidade() + " e maior que 0");
                }
            }
        });
    }*/

    private void SetValues(Produto produto){
        TextView nome = (TextView)findViewById(R.id.produtoDetalheNome);
        TextView descricao = (TextView)findViewById(R.id.produtoDetalheDescricao);

        nome.setText(produto.getPro_vnome());
        descricao.setText(produto.getPro_vdescricao());

        /*if(produto.getLsRegistros()!=null){
            adpRegistros.addAll(produto.getLsRegistros());
            listViewRegistros.setAdapter(adpRegistros);
        }
        if(produto.getLsRegistros()!=null) {
            adpMateriais.addAll(produto.getLsProdutoMaterial());
            listViewMateriais.setAdapter(adpMateriais);
        }*/
    }

    public void NovoRegistro(View view){
        registro = new Registros();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.Registros);
        dialog.setMessage(R.string.RegistrosNome);
        final EditText input = new EditText(this);
        dialog.setView(input);
        dialog.setCancelable(false);

        dialog.setNegativeButton(R.string.Salvar, null);
        dialog.setPositiveButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///cancelar
                dialog.cancel();
            }
        });

        alert = dialog.create();
        alert.show();
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncoesExternas.Valida(input)) {
                    registro.setRgs_vdescricao(input.getText().toString());
                    registro.setProduto(produto);
                    //Negocio neg = produto.getNegocio();
                    Log.i("NEGO do PRoed", "Vai erra!!u N??");
                    /// registro.setNegocio(produto.getNegocio());
                    registro.setRgs_cstatus(RegistroStatus.aberto);
                    new SalvaRegistro().execute();
                    Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);

                }
            }
        });
    }

    private void ListaRegistros(List<Registros>lsRegistros){
        if(lsRegistros!=null) {
            adpRegistros.clear();
            for (Registros rg : lsRegistros)
                adpRegistros.add(rg);//converte object em Grupo
            listViewRegistros.setAdapter(adpRegistros);
        }
    }

    private class CarregaRegistros extends AsyncTask<Registros, String, List<Registros>>{

        @Override
        protected List<Registros> doInBackground(Registros... params) {
            return DAO.SelecionaRegistros(produto.getPro_codigo());
        }

        @Override
        protected void onPostExecute(List<Registros> lsRegistros) {
            super.onPostExecute(lsRegistros);
            adpRegistros.clear();
            ListaRegistros(lsRegistros);
        }
    }

    private class SalvaRegistro extends AsyncTask<Registros,String, Boolean> {
        @Override
        protected Boolean doInBackground(Registros... params) {
            Log.i("Salva registo::?","vamos ver"+registro.getRgs_codigo());
            return DAO.Salvar(registro);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                alert.cancel();
                adpRegistros.clear();
                new CarregaRegistros().execute();
            }else {
                Dialog.ShowAlertError(ProdutoActivityDetalhes.this);
            }
            Dialog.CancelProgressDialog();
        }
    }


    private class SalvaProdutoMaterial extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();
        @Override
        protected Boolean doInBackground(Produto_material... params) {
            return prmDAO.Salvar(produtMaterial);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                alertM.cancel();
                //  new CarregaMaterial().execute();
                //   adpMateriais.clear();
            }else{
                Dialog.ShowAlert(ProdutoActivityDetalhes.this,"Material do Produto","Ops.. Não foi posivel salvar, favor tente novamente!");
            }
            Dialog.CancelProgressDialog();
        }
    }



}









