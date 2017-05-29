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
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hu.pe.nodout.relatorio_de_risco_11.Control.LocalControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.SetorControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_05_lista_de_locais extends AppCompatActivity {
    ListView listView_de_locais;
    EditText edt_pesquisar_local;
    String data_atual = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_05_lista_locais);


        edt_pesquisar_local = (EditText)findViewById(R.id.edt_pesquisar_local);

        edt_pesquisar_local.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                    pequisarLocal(edt_pesquisar_local.getText().toString().trim());

                return false;
            }
        });


        listView_de_locais = (ListView)findViewById(R.id.listView_de_locais);

        listView_de_locais.setLongClickable(true);
        listView_de_locais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_locais.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);
                return true;
            }
        });

        listView_de_locais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_locais.getItemAtPosition(position);

                try {
                    Cursor cursorResultado = SetorControl.cursorListarTodos2(cursor.getInt(0));
                    if(cursorResultado.moveToFirst()) {
                        Intent intent = new Intent(View_05_lista_de_locais.this,
                                View_06_lista_de_setores.class);
                        intent.putExtra(Local.COLUNA_id_Local,cursor.getInt(0));
                        startActivity(intent);
                    } else {
                        alertDialogContinuarSetor(cursor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data_atual =simpleDateFormat.format(data);
    }
    public void alertDialogContinuarSetor(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_aviso_local = getLayoutInflater().inflate(R.layout.dialog_continue_01_local,null);

        final ListView listView_aviso_opcoes_local =
                (ListView) view_aviso_local.findViewById(R.id.listView_aviso_opcoes_local);

        builder.setView(view_aviso_local);

        final AlertDialog dialog = builder.create();

        listView_aviso_opcoes_local.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        alertDialogNovoSetor(cursor);
                        dialog.dismiss();
                        break;
                }

            }
        });

        dialog.show();
    }

    public void alertDialogNovoSetor(final Cursor cursor) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_setor = getLayoutInflater().inflate(R.layout.dialog_02_novo_setor, null);

        final EditText edt_novo_nome_setor =
                (EditText) view_setor.findViewById(R.id.edt_novo_nome_setor);
        final EditText edt_novo_qtd_pessoas_setor =
                (EditText) view_setor.findViewById(R.id.edt_novo_qtd_pessoas_setor);
        final EditText edt_novo_tipo_de_pessoas_setor =
                (EditText) view_setor.findViewById(R.id.edt_novo_tipo_de_pessoas_setor);

        final Button btn_cancelar_novo_setor =
                (Button) view_setor.findViewById(R.id.btn_cancelar_novo_setor);
        final Button btn_salvar_novo_setor =
                (Button) view_setor.findViewById(R.id.btn_salvar_novo_setor);

        builder.setView(view_setor);
        final AlertDialog dialog = builder.create();

        btn_salvar_novo_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_novo_nome_setor.length() < 3) {
                    edt_novo_nome_setor.requestFocus();
                    Toast.makeText(View_05_lista_de_locais.this,
                            "Campo Setor, Mínimo de 3 Caracteres!",
                            Toast.LENGTH_LONG).show();
                } else if (edt_novo_tipo_de_pessoas_setor.length() < 3) {
                    edt_novo_tipo_de_pessoas_setor.requestFocus();
                    Toast.makeText(View_05_lista_de_locais.this,
                            "Campo Tipo de Pessoas, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Setor setor = new Setor();
                        setor.setNome_Setor(edt_novo_nome_setor.getText().toString().trim());
                        if (edt_novo_qtd_pessoas_setor.length() < 1) {
                            setor.setQuantidade_de_pessoas_setor(0);
                        } else {
                            setor.setQuantidade_de_pessoas_setor(Integer.parseInt(
                                    edt_novo_qtd_pessoas_setor.getText().toString().trim()));
                        }
                        setor.setTipo_de_pessoas_setor(
                                edt_novo_tipo_de_pessoas_setor.getText().toString().trim());
                        setor.setId_Local(cursor.getInt(0));
                        Toast.makeText(View_05_lista_de_locais.this,
                                "id local enviado: " + cursor.getInt(0),
                                Toast.LENGTH_LONG).show();

                        int resultado = SetorControl.insert(setor);

                        if (resultado > 0) {

                            dialog.dismiss();/*Fechando dialog*/

                            Toast.makeText(View_05_lista_de_locais.this,
                                    "Registro inserido!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(View_05_lista_de_locais.this,
                                    View_06_lista_de_setores.class);
                            intent.putExtra(Local.COLUNA_id_Local, cursor.getInt(0));
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_cancelar_novo_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }//fim novo_setor()

    @Override
    protected void onResume() {
        super.onResume();

        try {
            String[] vetorColunas = {Local.COLUNA_id_Local,Local.COLUNA_nome_Local,
                    Local.COLUNA_data_Local};

            int[] vetorCodigo ={R.id.txt_item_codigo_local,R.id.txt_item_nome_local,
                    R.id.txt_item_data_local};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_01_lista_de_locais,
                    LocalControl.cursorListarTodos(), vetorColunas,vetorCodigo,1);

            listView_de_locais.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//fim onResume()

    public void mostrarDialogoOpcoes(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opções").setItems(R.array.opcoes_alert_global, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int opcaoMenu) {

                switch (opcaoMenu) {
                    case 0:
                        alertDialogEditar(cursor);
                        break;
                    case 1:
                        alertDialogRemover(cursor);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void alertDialogEditar(final Cursor cursor){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_local = getLayoutInflater().inflate(R.layout.dialog_01_editar_local,null);

        final EditText edt_editar_nome_local = (EditText)view_local.findViewById(R.id.edt_editar_nome_local);
        final EditText edt_editar_data_local = (EditText)view_local.findViewById(R.id.edt_editar_data_local);
        final Button btn_editar_local = (Button)view_local.findViewById(R.id.btn_editar_local);
        final Button btn_cancelar_local = (Button)view_local.findViewById(R.id.btn_cancelar_local);

        edt_editar_nome_local.setText(cursor.getString(1));
        edt_editar_data_local.setText(cursor.getString(2));

        builder.setView(view_local);
        final AlertDialog dialog = builder.create();
        btn_editar_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edt_editar_nome_local.length() > 3)) {
                    edt_editar_nome_local.requestFocus();
                    Toast.makeText(View_05_lista_de_locais.this, "Campo Local, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (!(edt_editar_data_local.length() > 7)) {
                    edt_editar_data_local.requestFocus();
                    Toast.makeText(View_05_lista_de_locais.this, "Campo data, Mínimo de 8 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Local local = new Local();
                        local.setId_Local(cursor.getInt(0));
                        local.setNome_Local(edt_editar_nome_local.getText().toString().trim());
                        local.setData_Local(edt_editar_data_local.getText().toString().trim());
                        int resultado = LocalControl.update(local);
                        if (resultado > 0) {
                            Toast.makeText(View_05_lista_de_locais.this, " Registro Alterado!",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            onResume();/*Atualizando listView de locais*/
                        } else {
                            Toast.makeText(View_05_lista_de_locais.this, "Falha ao Alterar registro!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(View_05_lista_de_locais.this,
                                "Falha ao inserir Registro no Banco de Dados!",Toast.LENGTH_LONG).show();
                    }
                }//fim onClick
            }//fim else
        });
        btn_cancelar_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void alertDialogRemover(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_deletar_34dp);
        builder.setTitle("Você tem certeza?");
        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int resultado = LocalControl.delete(cursor.getInt(0));
                    if (resultado > 0) {
                        onResume();/*Atualizando listView de locais*/
                        Toast.makeText(View_05_lista_de_locais.this, " Registro removido!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(View_05_lista_de_locais.this, "Falha ao remover registro!",
                                Toast.LENGTH_SHORT).show();
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

    public void pequisarLocal(String nomeLocal){
        try {
            String[] vetorColunas = {Local.COLUNA_id_Local,Local.COLUNA_nome_Local,
                    Local.COLUNA_data_Local};

            int[] vetorCodigo ={R.id.txt_item_codigo_local,R.id.txt_item_nome_local,
                    R.id.txt_item_data_local};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_01_lista_de_locais,
                    LocalControl.pesquisar(nomeLocal), vetorColunas,vetorCodigo,1);

            listView_de_locais.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
