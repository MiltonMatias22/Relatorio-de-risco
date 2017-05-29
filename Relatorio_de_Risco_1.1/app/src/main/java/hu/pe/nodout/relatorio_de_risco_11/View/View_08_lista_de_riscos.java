package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import hu.pe.nodout.relatorio_de_risco_11.Control.RiscoControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Risco;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_08_lista_de_riscos extends AppCompatActivity {
    TextView txt_nome_setor_area;
    ListView listView_de_riscos;
    int id_inspecao,resultado_contador = 0;
    String grau_risco ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_08_lista_de_riscos);

       // txt_nome_setor_area = (TextView)findViewById(R.id.txt_nome_setor_area);
        listView_de_riscos = (ListView)findViewById(R.id.listView_de_riscos);

        Intent intent = getIntent();
        id_inspecao = intent.getIntExtra(Inspecao.COLUNA_id_Inspecao, 0);

        resultado_contador = RiscoControl.Contador(id_inspecao);
        if(!(resultado_contador == 5)){
            dialog_inspecaoImcompleta();
        }


        listView_de_riscos.setLongClickable(true);
        listView_de_riscos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_riscos.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_04_lista_de_risco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_continuar_risco:

                if(!(resultado_contador == 5)){

                    Intent intent = new Intent(View_08_lista_de_riscos.this,
                            View_04_registrar_risco.class);
                    intent.putExtra(Inspecao.COLUNA_id_Inspecao,id_inspecao);
                    intent.putExtra("resultado_contador",resultado_contador);
                    intent.putExtra("ativador",1);
                    startActivity(intent);

                }else {
                    Toast.makeText(this,"Inspeção já está completa!",Toast.LENGTH_LONG).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] vetorColunas = {Risco.COLUNA_id_Risco,Risco.COLUNA_tipo_Risco,
                Risco.COLUNA_nome_Agentes_Causadores,Risco.COLUNA_grau_risco,
                Risco.COLUNA_recomendacoes_risco,Risco.COLUNA_data_risco};

        int[] vetorCodigo ={R.id.txt_item_codigo_risco,R.id.txt_item_tipo_risco,
                R.id.txt_item_agentes_causadores_risco,R.id.txt_item_grau_risco,
                R.id.txt_item_recomendacoes_risco,R.id.txt_item_data_risco};

        SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                this,R.layout.table_layout_04_lista_de_riscos,
                RiscoControl.cursorlistarTodos(id_inspecao),
                vetorColunas,vetorCodigo,1);

        listView_de_riscos.setAdapter(adapter);

        DataBaseManager.getInstance().closeDatabase();
    }

    private void mostrarDialogoOpcoes(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opções").setItems(R.array.opcoes_alert_global, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int opcaoMenu) {

                switch (opcaoMenu) {
                    case 0:
                        //PASSAR O CURSOR
                        editar(cursor);
                        break;
                    case 1:
                        remover(cursor);
                        break;
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void editar(final Cursor cursor){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_risco = getLayoutInflater().inflate(R.layout.dialog_04_editar_risco,null);

        final TextView txt_editar_nome_risco = (TextView)view_risco.findViewById(R.id.txt_editar_nome_risco);
        final EditText edt_tipo_agente = (EditText)view_risco.findViewById(R.id.edt_editar_tipo_agentes_causadores_risco);
        final EditText edt_editar_grau_risco = (EditText)view_risco.findViewById(R.id.edt_editar_grau_risco);
        final EditText edt_recomendacoes = (EditText)view_risco.findViewById(R.id.edt_editar_recomendacoes_risco);
        final EditText edt_editar_data_risco = (EditText)view_risco.findViewById(R.id.edt_editar_data_risco);
        final Button btn_editar_risco = (Button)view_risco.findViewById(R.id.btn_editar_risco);
        final Button btn_cancelar_risco = (Button)view_risco.findViewById(R.id.btn_cancelar_risco);
        final Button btn_escolher_editar_grau_risco = (Button)view_risco.findViewById(R.id.btn_escolher_editar_grau_risco);

        txt_editar_nome_risco.setText(cursor.getString(1));
        edt_tipo_agente.setText(cursor.getString(2));
        edt_editar_grau_risco.setText(cursor.getString(3));
        edt_recomendacoes.setText(cursor.getString(4));
        edt_editar_data_risco.setText(cursor.getString(5));

        builder.setView(view_risco);
        final AlertDialog dialog = builder.create();

        btn_editar_risco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edt_tipo_agente.length() > 3)) {
                    edt_tipo_agente.requestFocus();
                    Toast.makeText(View_08_lista_de_riscos.this, "Campo Tipo Agente, Mínimo de 3 Caracteres!",
                            Toast.LENGTH_LONG).show();
                } else if (!(edt_editar_grau_risco.length() > 3)) {
                    edt_editar_grau_risco.requestFocus();
                    Toast.makeText(View_08_lista_de_riscos.this, "Campo Grau Risco, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else if (!(edt_recomendacoes.length() > 3)) {
                    edt_recomendacoes.requestFocus();
                    Toast.makeText(View_08_lista_de_riscos.this, "Campo Recomendações, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else if (!(edt_editar_data_risco.length() > 8)) {
                    edt_editar_data_risco.requestFocus();
                    Toast.makeText(View_08_lista_de_riscos.this, "Campo Data, Mínimo de 8 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Risco risco = new Risco();
                        risco.setId_Risco(cursor.getInt(0));
                        risco.setTipo_Risco(txt_editar_nome_risco.getText().toString().trim());
                        risco.setNome_Agentes_Causadores(edt_tipo_agente.getText().toString().trim());
                        risco.setGrau_risco(edt_editar_grau_risco.getText().toString());
                        risco.setRecomendacoes_risco(edt_recomendacoes.getText().toString().trim());
                        risco.setData_risco(edt_editar_data_risco.getText().toString().trim());
                        int resultado = RiscoControl.update(risco);
                        if (resultado > 0) {
                            dialog.dismiss();
                            onResume();/*Atualizando listView de riscos*/
                            Toast.makeText(View_08_lista_de_riscos.this, " Registro Alterado!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(View_08_lista_de_riscos.this, "Falha ao Alterar registro!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_cancelar_risco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_escolher_editar_grau_risco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View listDialogGrauRisco = getLayoutInflater().inflate(R.layout.list_dialog_01_grau_risco,null);
                final ListView listViewDialog_grauRisco = (ListView)listDialogGrauRisco.findViewById(R.id.listViewDialog_grauRisco);
                builder.setView(listDialogGrauRisco);
                final AlertDialog dialog = builder.create();

                listViewDialog_grauRisco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        grau_risco = (String) listViewDialog_grauRisco.getItemAtPosition(position);
                        edt_editar_grau_risco.setText(grau_risco);
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        dialog.show();
    }
    public void remover(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_deletar_34dp);
        builder.setTitle("Você tem certeza?");
        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                int resultado = RiscoControl.delete(cursor.getInt(0));
                if (resultado > 0) {
                    onResume();/*Atualizando listView de riscos*/
                    Toast.makeText(View_08_lista_de_riscos.this, " Registro removido!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(View_08_lista_de_riscos.this, "Falha ao remover registro!", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void dialog_inspecaoImcompleta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_aviso_risco = getLayoutInflater().inflate(R.layout.dialog_continue_04_risco,null);

        final ListView listView_aviso_opcoes_risco =
                (ListView)view_aviso_risco.findViewById(R.id.listView_aviso_opcoes_risco);

        builder.setView(view_aviso_risco);
        final AlertDialog dialog = builder.create();

        listView_aviso_opcoes_risco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        Intent intent = new Intent(View_08_lista_de_riscos.this,
                                View_04_registrar_risco.class);
                        intent.putExtra(Inspecao.COLUNA_id_Inspecao,id_inspecao);
                        intent.putExtra("resultado_contador",resultado_contador);
                        intent.putExtra("ativador",1);
                        startActivity(intent);
                        break;
                }
            }
        });

        dialog.show();
    }

}
