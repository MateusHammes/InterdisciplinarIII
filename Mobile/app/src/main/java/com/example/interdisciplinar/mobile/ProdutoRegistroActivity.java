package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import DAO.RegistrosDAO;
import Enum.RegistroStatus;
import model.Negocio;
import model.Produto;
import model.Registros;
import util.Dialog;
import util.FuncoesExternas;
import Enum.NegocioStatus;

public class ProdutoRegistroActivity extends AppCompatActivity {
    private ArrayAdapter<Registros> adpRegistros;
    private ListView listViewRegistros;
    private AlertDialog alert;
    private AlertDialog alert2;
    private Registros registro = new Registros();
    private Produto produto = new Produto();
    private RegistrosDAO DAO = new RegistrosDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_registro);

        adpRegistros  =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listViewRegistros = (ListView)findViewById(R.id.produtoRegistroListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.produtoRegistroBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getNegocio().getNeg_codigo()!=0 && produto.getNegocio().getNeg_cstatus() == NegocioStatus.ABERTO)
                NovoRegistro();
                else
                    Dialog.Show(ProdutoRegistroActivity.this,"Adicionar Especificação", "Não é possível adicionar especificações, pois este registro esta fechado!");
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("PRODUTO")){
            produto =(Produto) bundle.getSerializable("PRODUTO");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        listViewRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Registros reg = adpRegistros.getItem(position);

                Negocio neg = reg.getProduto().getNegocio();
                if (neg != null && neg.getNeg_codigo() != 0 && neg.getNeg_cstatus() == NegocioStatus.ABERTO) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoRegistroActivity.this);
                    if (reg.getRgs_cstatus() == RegistroStatus.aberto) {
                        dialog.setTitle("Deseja completar esta Especificaçao?");
                        dialog.setNegativeButton(R.string.Completar, null);
                    } else {
                        dialog.setTitle("Deseja abrir esta Especificaçao?");
                        dialog.setNegativeButton(R.string.Abrir, null);
                    }
                    dialog.setMessage("Especificaçao: " + reg.getRgs_vdescricao());

                    dialog.setNeutralButton(R.string.Editar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final EditText txt = new EditText(ProdutoRegistroActivity.this);
                            txt.setText(reg.getRgs_vdescricao());
                            AlertDialog.Builder dialogEdit = new AlertDialog.Builder(ProdutoRegistroActivity.this);
                            dialogEdit.setTitle(R.string.EditarEspecificacao);
                            dialogEdit.setView(txt);
                            dialogEdit.setNeutralButton(R.string.Cancelar, null);
                            dialogEdit.setNegativeButton(R.string.Salvar, null);

                            alert2 = dialogEdit.create();
                            alert2.show();
                            alert2.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (FuncoesExternas.Valida(txt)) {
                                        registro = reg;
                                        registro.setRgs_vdescricao(txt.getText().toString());
                                        new SalvaRegistro().execute();
                                        Dialog.ShowProgressDialog(ProdutoRegistroActivity.this);
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
                            Dialog.ShowProgressDialog(ProdutoRegistroActivity.this);
                        }
                    });
                }
            }
        });
        new CarregaRegistros().execute();

    }


    public void NovoRegistro(){
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
                    Dialog.ShowProgressDialog(ProdutoRegistroActivity.this);

                }
            }
        });
    }

    private void ListaRegistros(List<Registros> lsRegistros){
        if(lsRegistros!=null) {
            adpRegistros.clear();
            for (Registros rg : lsRegistros)
                adpRegistros.add(rg);//converte object em Grupo
            listViewRegistros.setAdapter(adpRegistros);
        }
    }

    private class CarregaRegistros extends AsyncTask<Registros, String, List<Registros>> {

        ProgressBar pg = (ProgressBar) findViewById(R.id.produtoRegistroProgressBar);
        @Override
        protected List<Registros> doInBackground(Registros... params) {
            return DAO.SelecionaRegistros(produto.getPro_codigo());
        }

        @Override
        protected void onPostExecute(List<Registros> lsRegistros) {
            super.onPostExecute(lsRegistros);
            adpRegistros.clear();
            ListaRegistros(lsRegistros);
            pg.setVisibility(View.GONE);
        }
    }

    private class SalvaRegistro extends AsyncTask<Registros,String, Boolean> {
        @Override
        protected Boolean doInBackground(Registros... params) {
            Log.i("Salva registo::?","vamos ver "+registro.getRgs_codigo());
            return DAO.Salvar(registro);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                alert.cancel();
                alert2.cancel();
                adpRegistros.clear();
                new CarregaRegistros().execute();
            }else {
                Dialog.ShowAlertError(ProdutoRegistroActivity.this);
            }
            Dialog.CancelProgressDialog();
        }
    }

}

