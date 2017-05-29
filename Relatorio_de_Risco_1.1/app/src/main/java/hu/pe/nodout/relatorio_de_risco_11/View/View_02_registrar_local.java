package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hu.pe.nodout.relatorio_de_risco_11.Control.LocalControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_02_registrar_local extends AppCompatActivity {
    private EditText edt_nome_local;
    private ListView listView_de_locais;
    private Button btn_adicionar_local;
    private int resultado = 0;
    private int id_local = 0;
    private String data_atual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_02_registrar_local);

        this.edt_nome_local = (EditText)findViewById(R.id.edt_nome_local);
        this.listView_de_locais = (ListView) findViewById(R.id.listView_de_locais1);
        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.data_atual = simpleDateFormat.format(data);

        this.listView_de_locais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_locais.getItemAtPosition(position);
                Intent intent = new Intent(View_02_registrar_local.this, View_03_registrar_inspecao.class);
                intent.putExtra(Local.COLUNA_id_Local,cursor.getInt(0));
                startActivity(intent);
            }
        });
        this.listView_de_locais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_locais.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);
                return true;
            }
        });

        btn_adicionar_local = (Button)findViewById(R.id.btn_adicionar_local);
        btn_adicionar_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAdcionarLocal();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    try{
        String[] vetorColunas = {Local.COLUNA_id_Local,Local.COLUNA_nome_Local,Local.COLUNA_data_Local};
        int[] vetorCodigo ={R.id.txt_item_codigo_local,R.id.txt_item_nome_local,R.id.txt_item_data_local};

        SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                this,R.layout.table_layout_01_lista_de_locais,
                LocalControl.cursorListarTodos(), vetorColunas,vetorCodigo,1);

        listView_de_locais.setAdapter(adapter);

        DataBaseManager.getInstance().closeDatabase();

    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    public void alertDialogAdcionarLocal() {
          if(!(edt_nome_local.length() > 3)){
              edt_nome_local.requestFocus();
              Toast.makeText(this,"Campo Local, Mínimo de 3 Caracteres!",Toast.LENGTH_LONG).show();
        }else {
              try {
              Local local = new Local();
              local.setNome_Local(edt_nome_local.getText().toString().trim());
              local.setData_Local(data_atual);
              LocalControl localControl = new LocalControl();
              this.resultado = localControl.insert(local);
              if(resultado >0){
                  edt_nome_local.setText("");
                  this.id_local = resultado;
                      Toast.makeText(this,"Registro inserido!",Toast.LENGTH_SHORT).show();
                  onResume();/*Atualizando listView de locais*/
                  }
              } catch (Exception e) {
                  e.printStackTrace();
                  Toast.makeText(this,"Falha ao inserido Registro!",Toast.LENGTH_SHORT).show();
              }
        }

    }//fim método add_nome_do_local

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
                    Toast.makeText(View_02_registrar_local.this, "Campo Local, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (!(edt_editar_data_local.length() > 7)) {
                    edt_editar_data_local.requestFocus();
                    Toast.makeText(View_02_registrar_local.this, "Campo data, Mínimo de 8 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Local local = new Local();
                        local.setId_Local(cursor.getInt(0));
                        local.setNome_Local(edt_editar_nome_local.getText().toString());
                        local.setData_Local(edt_editar_data_local.getText().toString());
                        int resultado = LocalControl.update(local);
                        if (resultado > 0) {
                            Toast.makeText(View_02_registrar_local.this, " Registro Alterado!",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            onResume();/*Atualizando listView de locais*/
                        } else {
                            Toast.makeText(View_02_registrar_local.this, "Falha ao Alterar registro!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(View_02_registrar_local.this,
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
                        Toast.makeText(View_02_registrar_local.this, " Registro removido!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(View_02_registrar_local.this, "Falha ao remover registro!",
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

}
