package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
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

import hu.pe.nodout.relatorio_de_risco_11.Control.InspecaoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.SetorControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.TecnicoControl;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_03_registrar_inspecao extends AppCompatActivity {

    Button btn_escolher_setor, btn_escolher_tecnico, btn_salvar_inspecao;
    EditText edt_escolher_tecnico, edt_escolher_setor;
    String data_atual = "",nome_setor = "";
    int id_setor, id_tecnico, id_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_03_registrar_inspecao);

        edt_escolher_tecnico = (EditText) findViewById(R.id.edt_escolher_tecnico);
        edt_escolher_setor = (EditText) findViewById(R.id.edt_escolher_setor);

        btn_escolher_tecnico = (Button) findViewById(R.id.btn_escolher_tecnico);
        /*chamando método layoutList_tecnico()*/
        btn_escolher_tecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogLayoutListTecnico();
            }
        });

        btn_salvar_inspecao = (Button) findViewById(R.id.btn_salvar_inspecao);
        /*Salvando inspeção*/
        btn_salvar_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarInspecao();
            }
        });

        btn_escolher_setor = (Button) findViewById(R.id.btn_escolher_setor);
        btn_escolher_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogLayoutListSetor();
            }
        });

        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data_atual = simpleDateFormat.format(data);

        Intent intent = getIntent();
        id_local = intent.getIntExtra(Local.COLUNA_id_Local, 0);
        id_setor = intent.getIntExtra(Setor.COLUNA_id_Setor,0);
        nome_setor = intent.getStringExtra(Setor.COLUNA_nome_Setor);
        edt_escolher_setor.setText(nome_setor);


    }//fim onCrete()

    public void salvarInspecao() {
        try {
            Inspecao inspecao = new Inspecao();
            inspecao.setData_Inicio_Inspecao(data_atual);
            inspecao.setId_Setor(id_setor);
            inspecao.setId_Tecnico(id_tecnico);
            int id_Inspecao = InspecaoControl.insert(inspecao);
            if (id_Inspecao > 0) {
                Toast.makeText(this, "Registro inserido!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, View_04_registrar_risco.class);
                intent.putExtra(Inspecao.COLUNA_id_Inspecao, id_Inspecao);
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método, exiber dialog ListView de Técnicos
     */
    public void alertDialogLayoutListTecnico() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_tecnico = getLayoutInflater().inflate(R.layout.list_dialog_02_tecnico, null);

        final ListView listView_dialog_tecnico =
                (ListView) view_tecnico.findViewById(R.id.listView_dialog_tecnico);
        final Button btn_dialog_novo_tecnico =
                (Button) view_tecnico.findViewById(R.id.btn_dialog_novo_tecnico);

        builder.setView(view_tecnico);
        final AlertDialog dialog = builder.create();

        String[] vetorColunas = {Tecnico.COLUNA_id_Tecnico, Tecnico.COLUNA_nome_Tecnico};
        int[] vetorCodigo = {R.id.txt_item_codigo_tecnico, R.id.txt_item_nome_tecnico};

        try {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.layout_dialog_01_tecnico,
                    TecnicoControl.cursorListarTodos(), vetorColunas, vetorCodigo, 1);

            listView_dialog_tecnico.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Pegando elemento da ListView*/
        listView_dialog_tecnico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_dialog_tecnico.getItemAtPosition(position);
                edt_escolher_tecnico.setText(cursor.getString(1));
                id_tecnico = cursor.getInt(0);
                dialog.dismiss();

            }
        });

        btn_dialog_novo_tecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar dialog aqui!
                alertDialogNovoTecnico();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Método, exibir dialog inserir novo técnico
     */
    public void alertDialogNovoTecnico() {

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
                    Toast.makeText(View_03_registrar_inspecao.this,
                            "Campo Nome Técnico, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Tecnico tecnico = new Tecnico();
                        tecnico.setNome_Tecnico(edt_novo_nome_tecnico.getText().toString().trim());
                        tecnico.setData_Tecnico(data_atual);
                        int resultado = TecnicoControl.insert(tecnico);

                        if (resultado > 0) {

                            id_tecnico = resultado;
                            edt_escolher_tecnico.setText(
                                    edt_novo_nome_tecnico.getText().toString());

                            dialog.dismiss();
                            Toast.makeText(View_03_registrar_inspecao.this,
                                    "Registro inserido!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(View_03_registrar_inspecao.this,
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

    /**
     * Método, exiber ListView de Setores
     */
    public void alertDialogLayoutListSetor() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_setor = getLayoutInflater().inflate(R.layout.list_dialog_03_setor, null);

        final ListView listView_dialog_setor =
                (ListView) view_setor.findViewById(R.id.listView_dialog_setor);
        final Button btn_dialog_novo_setor =
                (Button) view_setor.findViewById(R.id.btn_dialog_novo_setor);

        builder.setView(view_setor);
        final AlertDialog dialog = builder.create();

        String[] vetorColunas = {Setor.COLUNA_id_Setor, Setor.COLUNA_nome_Setor,
                Local.COLUNA_nome_Local};
        int[] vetorCodigo = {R.id.txt_item_codigo_setor_02, R.id.txt_item_nome_setor_02,
                R.id.txt_item_nome_local_02};
        try {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.table_layout_02_lista_de_setores,
                    SetorControl.cursorListarTodos2(id_local), vetorColunas, vetorCodigo, 1);

            listView_dialog_setor.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        listView_dialog_setor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_dialog_setor.getItemAtPosition(position);
                edt_escolher_setor.setText(cursor.getString(1));
                id_setor = cursor.getInt(0);
                dialog.dismiss();
            }
        });

        btn_dialog_novo_setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogNovoSetor();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Método, exibir dialog inserir novo técnico
     */
    public void alertDialogNovoSetor() {

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
                    Toast.makeText(View_03_registrar_inspecao.this,
                            "Campo Setor, Mínimo de 3 Caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (edt_novo_tipo_de_pessoas_setor.length() < 3) {

                    edt_novo_tipo_de_pessoas_setor.requestFocus();
                    Toast.makeText(View_03_registrar_inspecao.this,
                            "Campo Tipo de Pessoas, Mínimo de 3 caracteres!",
                            Toast.LENGTH_LONG).show();

                } else {
                    try {
                        Setor setor = new Setor();
                        setor.setNome_Setor(edt_novo_nome_setor.getText().toString().trim());

                        if (edt_novo_qtd_pessoas_setor.length() < 1){
                            setor.setQuantidade_de_pessoas_setor(0);
                        }else {
                            setor.setQuantidade_de_pessoas_setor(Integer.parseInt(
                                    edt_novo_qtd_pessoas_setor.getText().toString().trim()));
                        }

                        setor.setTipo_de_pessoas_setor(
                                edt_novo_tipo_de_pessoas_setor.getText().toString().trim());
                        setor.setId_Local(id_local);

                        int resultado = SetorControl.insert(setor);

                        if (resultado > 0) {
                            edt_escolher_setor.setText(edt_novo_nome_setor.getText().toString());
                            id_setor = resultado;
                            dialog.dismiss();
                            Toast.makeText(View_03_registrar_inspecao.this,
                                    "Registro inserido!", Toast.LENGTH_SHORT).show();
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
}
