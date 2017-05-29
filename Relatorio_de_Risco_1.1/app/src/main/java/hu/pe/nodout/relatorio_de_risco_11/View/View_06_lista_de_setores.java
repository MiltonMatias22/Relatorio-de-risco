package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import hu.pe.nodout.relatorio_de_risco_11.Control.InspecaoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.SetorControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_06_lista_de_setores extends AppCompatActivity {
    ListView listView_de_setores;
    EditText edt_pesquisar_setor;
    int id_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_06_lista_de_setores);


        edt_pesquisar_setor = (EditText)findViewById(R.id.edt_pesquisar_setor);

        edt_pesquisar_setor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                pequisarSetor(edt_pesquisar_setor.getText().toString().trim());
                return false;
            }
        });


        listView_de_setores = (ListView)findViewById(R.id.listView_de_setores);

        listView_de_setores.setLongClickable(true);
        listView_de_setores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_setores.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);
                return true;
            }
        });

        listView_de_setores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_setores.getItemAtPosition(position);
                try {
                    Cursor cursorResultado = InspecaoControl.CursorlistarTodos(cursor.getInt(0));
                    if (cursorResultado.moveToFirst()) {
                        Intent intent = new Intent(View_06_lista_de_setores.this,
                                View_07_lista_de_inspecoes.class);
                        intent.putExtra(Local.COLUNA_id_Local, id_local);
                        intent.putExtra(Setor.COLUNA_id_Setor, cursor.getInt(0));
                        startActivity(intent);
                    } else {
                        continuarSetor(cursor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent = getIntent();
        id_local = intent.getIntExtra(Local.COLUNA_id_Local, 0);

    }

    public void continuarSetor(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_aviso_setor = getLayoutInflater().inflate(R.layout.dialog_continue_02_setor,null);

        final ListView listView_aviso_opcoes_setor =
                (ListView) view_aviso_setor.findViewById(R.id.listView_aviso_opcoes_setor);

        builder.setView(view_aviso_setor);

        final AlertDialog dialog = builder.create();

        listView_aviso_opcoes_setor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        dialog.dismiss();
                        Intent intent = new Intent(View_06_lista_de_setores.this,
                                View_03_registrar_inspecao.class);
                        intent.putExtra(Local.COLUNA_id_Local, 1);
                        intent.putExtra(Setor.COLUNA_id_Setor, cursor.getInt(0));
                        intent.putExtra(Setor.COLUNA_nome_Setor, cursor.getString(1));
                        startActivity(intent);
                        break;
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            String[] vetorColunas = {Setor.COLUNA_id_Setor,Setor.COLUNA_nome_Setor,
                Local.COLUNA_nome_Local};
            int[] vetorCodigo ={R.id.txt_item_codigo_setor_02,R.id.txt_item_nome_setor_02,
                R.id.txt_item_nome_local_02};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_02_lista_de_setores,
                    SetorControl.cursorListarTodos2(id_local),
                    vetorColunas,vetorCodigo,1);

            listView_de_setores.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }
   }

    public void mostrarDialogoOpcoes(final Cursor cursor){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Opções").setItems(R.array.opcoes_alert_detalhes_setor,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcaoMenu) {

                        switch (opcaoMenu){
                            case 0:/*nova inspeção*/
                                dialog.dismiss();
                                Intent intent = new Intent(View_06_lista_de_setores.this,
                                        View_03_registrar_inspecao.class);
                                intent.putExtra(Local.COLUNA_id_Local, id_local);
                                intent.putExtra(Setor.COLUNA_id_Setor, cursor.getInt(0));
                                intent.putExtra(Setor.COLUNA_nome_Setor,cursor.getString(1));
                                startActivity(intent);
                                break;
                            case 1:
                                detalhes(cursor);/*alertDialog mostrar detalhes*/
                                break;
                            case 2:
                                editar(cursor);/*alertDialog editar*/
                                break;
                            case 3:
                                remover(cursor);/*alertDialog remover*/
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editar(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_setor = getLayoutInflater().inflate(R.layout.dialog_02_editar_setor,null);

        final EditText edt_editar_nome_setor =
                (EditText)view_setor.findViewById(R.id.edt_editar_nome_setor);
        final EditText edt_editar_qtd_pessoas_setor =
                (EditText)view_setor.findViewById(R.id.edt_editar_qtd_pessoas_setor);
        final EditText edt_editar_tipo_de_pessoas_setor =
                (EditText)view_setor.findViewById(R.id.edt_editar_tipo_de_pessoas_setor);

        final Button btn_cancelar_setor = (Button)view_setor.findViewById(R.id.btn_cancelar_setor);
        final Button btn_editar_setor = (Button)view_setor.findViewById(R.id.btn_editar_setor);

        try {

            Setor setor = SetorControl.pesquisar(cursor.getInt(0));
            edt_editar_nome_setor.setText(setor.getNome_Setor());
            edt_editar_qtd_pessoas_setor.setText(Integer.toString(setor.getQuantidade_de_pessoas_setor()));
            edt_editar_tipo_de_pessoas_setor.setText(setor.getTipo_de_pessoas_setor());

        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.setView(view_setor);
        final AlertDialog dialog = builder.create();

        btn_editar_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_editar_nome_setor.length() < 3) {

                    edt_editar_nome_setor.requestFocus();
                    Toast.makeText(View_06_lista_de_setores.this,
                            "Campo Setor, Mínimo de 3 Caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (edt_editar_tipo_de_pessoas_setor.length() < 3) {

                    edt_editar_tipo_de_pessoas_setor.requestFocus();
                    Toast.makeText(View_06_lista_de_setores.this,
                            "Campo Tipo de Pessoas, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Setor setor = new Setor();
                        setor.setId_Setor(cursor.getInt(0));
                        setor.setNome_Setor(edt_editar_nome_setor.getText().toString());

                        if (edt_editar_qtd_pessoas_setor.length() < 1) {
                            setor.setQuantidade_de_pessoas_setor(0);
                        } else {
                            setor.setQuantidade_de_pessoas_setor(Integer.parseInt(
                                    edt_editar_qtd_pessoas_setor.getText().toString()));
                        }

                        setor.setTipo_de_pessoas_setor(edt_editar_tipo_de_pessoas_setor.getText().toString());
                        setor.setId_Local(id_local);

                        int resultado = SetorControl.update(setor);

                        if (resultado >0){

                            Toast.makeText(View_06_lista_de_setores.this, "Registro Alterado!",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            onResume();/*Atualizando listView de setores*/
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(View_06_lista_de_setores.this, "Falha ao Alterar Registro!",
                                Toast.LENGTH_LONG).show();
                    }
                }//fim else
            }
        });
        btn_cancelar_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
                try {

                    int resultado = SetorControl.delete(cursor.getInt(0));

                    if (resultado > 0) {
                        onResume();
                        Toast.makeText(View_06_lista_de_setores.this, "Registro removido!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(View_06_lista_de_setores.this, "Falha ao Removido Registro!",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
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

    public void detalhes(Cursor cursor){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_detalhes_setor = getLayoutInflater().inflate(R.layout.layout_01_detalhes_setor,null);

        final TextView edt_detalhes_codigo_setor =
                (TextView)view_detalhes_setor.findViewById(R.id.edt_detalhes_codigo_setor);
        final TextView edt_detalhes_nome_setor =
                (TextView)view_detalhes_setor.findViewById(R.id.edt_detalhes_nome_setor);
        final TextView edt_detalhes_qtd_pessoas_setor =
                (TextView)view_detalhes_setor.findViewById(R.id.edt_detalhes_qtd_pessoas_setor);

        final EditText edt_detalhes_tipo_de_pessoas_setor =
                (EditText)view_detalhes_setor.findViewById(R.id.edt_detalhes_tipo_de_pessoas_setor);

        final Button btn_detalhes_fechar_setor =
                (Button) view_detalhes_setor.findViewById(R.id.btn_detalhes_fechar_setor);

        try {
            Setor setor = SetorControl.pesquisar(cursor.getInt(0));
            edt_detalhes_codigo_setor.setText(Integer.toString(setor.getId_Setor()));
            edt_detalhes_nome_setor.setText(setor.getNome_Setor());
            edt_detalhes_qtd_pessoas_setor.setText(
                    Integer.toString(setor.getQuantidade_de_pessoas_setor()));
            edt_detalhes_tipo_de_pessoas_setor.setText(setor.getTipo_de_pessoas_setor());
        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.setView(view_detalhes_setor);
        final AlertDialog dialog = builder.create();

        btn_detalhes_fechar_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Método, pesquisar setor pelo nome
     * */
    public void pequisarSetor(String nomeSetor){
        try {
            String[] vetorColunas = {Setor.COLUNA_id_Setor,Setor.COLUNA_nome_Setor,
                    Local.COLUNA_nome_Local};
            int[] vetorCodigo ={R.id.txt_item_codigo_setor_02,R.id.txt_item_nome_setor_02,
                    R.id.txt_item_nome_local_02};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_02_lista_de_setores,
                    SetorControl.pesquisar(nomeSetor,id_local), vetorColunas,vetorCodigo,1);

            listView_de_setores.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
