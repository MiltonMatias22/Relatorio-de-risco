package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hu.pe.nodout.relatorio_de_risco_11.Control.TecnicoControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;
import hu.pe.nodout.relatorio_de_risco_11.R;

/**
 * Created by Milton Matias on 21/03/2017.
 */
public class View_09_lista_de_tecnicos extends AppCompatActivity {
    EditText edt_pesquisar_tecnico;
    ListView listView_de_tecnicos;
    String data_atual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_09_lista_de_tecnicos);

        edt_pesquisar_tecnico = (EditText)findViewById(R.id.edt_pesquisar_tecnico);

        edt_pesquisar_tecnico.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                pequisarTenico(edt_pesquisar_tecnico.getText().toString());
                return false;
            }
        });

        listView_de_tecnicos = (ListView)findViewById(R.id.listView_de_tecnicos);

        listView_de_tecnicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor)listView_de_tecnicos.getItemAtPosition(position);
                Intent intent = new Intent(View_09_lista_de_tecnicos.this,
                        View_10_lista_de_tecnico_inspecoes.class);
                intent.putExtra(Tecnico.COLUNA_id_Tecnico,cursor.getInt(0));
                startActivity(intent);


            }
        });

        listView_de_tecnicos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_tecnicos.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);
                return true;
            }
        });

        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data_atual = simpleDateFormat.format(data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            String[] vetorColunas = {Tecnico.COLUNA_id_Tecnico,Tecnico.COLUNA_nome_Tecnico,
                    Tecnico.COLUNA_data_Tecnico};
            int[] vetorCodigo ={R.id.txt_item_codigo_tecnico,R.id.txt_item_nome_tecnico,
                    R.id.txt_item_data_tecnico};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_05_lista_de_tecnicos,
                    TecnicoControl.cursorListarTodos(), vetorColunas,vetorCodigo,1);

            listView_de_tecnicos.setAdapter(adapter);

            //DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_02_tecnico,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_01_novo_tecnico:
                novo_tecnico();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoOpcoes(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Opções").setItems(R.array.opcoes_alert_dialog_tecnico,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int opcaoMenu) {

                        switch (opcaoMenu) {

                            case 0://Editar
                                editar(cursor);
                                break;
                            case 1://Remover
                                remover(cursor);
                                break;
                        }//fim switch
                    }//fim onClick()

                }//DialogInterface
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }//mostrarDialogoOpcoes

    /**
     * Método, exibir dialog inserir novo técnico
     */
    public void novo_tecnico() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_tecnico = getLayoutInflater().inflate(R.layout.dialog_03_novo_tecnico, null);

        // configurando o campo input
        final EditText edt_novo_nome_tecnico = (EditText)
                view_tecnico.findViewById(R.id.edt_novo_nome_tecnico);
        final Button btn_cancelar_novo_tecnico = (Button)
                view_tecnico.findViewById(R.id.btn_cancelar_novo_tecnico);
        final Button btn_salvar_novo_tecnico = (Button)
                view_tecnico.findViewById(R.id.btn_salvar_novo_tecnico);

        builder.setView(view_tecnico);
        final AlertDialog dialog = builder.create();

        //configurando botões
        btn_salvar_novo_tecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_novo_nome_tecnico.length() < 3) {
                    edt_novo_nome_tecnico.requestFocus();
                    Toast.makeText(View_09_lista_de_tecnicos.this,
                            "Campo Nome Técnico, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Tecnico tecnico = new Tecnico();
                        tecnico.setNome_Tecnico(edt_novo_nome_tecnico.getText().toString());
                        tecnico.setData_Tecnico(data_atual);
                        int resultado = TecnicoControl.insert(tecnico);

                        if (resultado > 0) {

                            onResume();/*Atualizando listView de linspeções*/

                            dialog.dismiss();/*Fechando dialog*/

                            Toast.makeText(View_09_lista_de_tecnicos.this,
                                    "Registro inserido!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        Toast.makeText(View_09_lista_de_tecnicos.this,
                                "Falha ao inserir Registro no Banco de Dados!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btn_cancelar_novo_tecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }//fim método novo_tecnico

    public void editar(final Cursor cursor){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view_editar_tecnico =
                getLayoutInflater().inflate(R.layout.dialog_03_editar_tecnico,null);

        final EditText edt_editar_nome_tecnico =
                (EditText)view_editar_tecnico.findViewById(R.id.edt_editar_nome_tecnico);
        final EditText edt_editar_data_tecnico =
                (EditText)view_editar_tecnico.findViewById(R.id.edt_editar_data_tecnico);

        final Button btn_editar_cancelar_tecnico =
                (Button)view_editar_tecnico.findViewById(R.id.btn_editar_cancelar_tecnico);
        final Button btn_editar_tecnico =
                (Button)view_editar_tecnico.findViewById(R.id.btn_editar_tecnico);

        /*Povoando campos*/
        edt_editar_nome_tecnico.setText(cursor.getString(1));
        edt_editar_data_tecnico.setText(cursor.getString(2));

        builder.setView(view_editar_tecnico);
        final AlertDialog dialog = builder.create();

        btn_editar_tecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_editar_nome_tecnico.length() < 3) {

                    edt_editar_nome_tecnico.requestFocus();
                    Toast.makeText(View_09_lista_de_tecnicos.this,
                            "Campo Técnico, Mínimo de 3 Caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (edt_editar_data_tecnico.length() < 10){

                    edt_editar_data_tecnico.requestFocus();
                    Toast.makeText(View_09_lista_de_tecnicos.this,
                            "Campo data, formato: 00/00/0000!",
                            Toast.LENGTH_LONG).show();

                }else {
                    try {
                        Tecnico tecnico = new Tecnico();
                        tecnico.setId_Tecnico(cursor.getInt(0));
                        tecnico.setNome_Tecnico(edt_editar_nome_tecnico.getText().toString().trim());
                        tecnico.setData_Tecnico(edt_editar_data_tecnico.getText().toString().trim());

                        int resultado = TecnicoControl.update(tecnico);

                        if (resultado > 0){

                            dialog.dismiss();/*Fechando dialog*/

                            onResume();/*Atualizando listView de linspeções*/

                            Toast.makeText(View_09_lista_de_tecnicos.this,
                                    "Registro Alterado!",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        Toast.makeText(View_09_lista_de_tecnicos.this,
                                "Falha ao Alterado Registro!",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btn_editar_cancelar_tecnico.setOnClickListener(new View.OnClickListener() {
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
        builder.setTitle("Você Tem Certeza?");

        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    int resultado = TecnicoControl.delete(cursor.getInt(0));
                    if (resultado > 0) {

                        onResume();/*Atualizando listView de linspeções*/

                        Toast.makeText(View_09_lista_de_tecnicos.this,
                                "Registro removido!",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(View_09_lista_de_tecnicos.this,
                                "Falha ao remover registro!",
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
    }//fim método remover();


    /**
     * Método, pesquisar setor pelo nome
     * */
    public void pequisarTenico(String nomeTecnico){
        try {
            String[] vetorColunas = {Tecnico.COLUNA_id_Tecnico,Tecnico.COLUNA_nome_Tecnico,
                    Tecnico.COLUNA_data_Tecnico};
            int[] vetorCodigo ={R.id.txt_item_codigo_tecnico,R.id.txt_item_nome_tecnico,
                    R.id.txt_item_data_tecnico};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_05_lista_de_tecnicos,
                    TecnicoControl.pesquisar(nomeTecnico), vetorColunas,vetorCodigo,1);

            listView_de_tecnicos.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
