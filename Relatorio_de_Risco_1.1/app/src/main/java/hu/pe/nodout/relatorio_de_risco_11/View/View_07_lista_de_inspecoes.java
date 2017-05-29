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

import hu.pe.nodout.relatorio_de_risco_11.Control.InspecaoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.RiscoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.SetorControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.TecnicoControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_07_lista_de_inspecoes extends AppCompatActivity {
    ListView listView_de_inspecoes;
    EditText edt_pesquisar_inspecao;
    int id_inspecao, id_setor,id_local, id_tecnico;
    String data_atual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_07_lista_de_inspecoes);

        edt_pesquisar_inspecao = (EditText)findViewById(R.id.edt_pesquisar_inspecao);

        edt_pesquisar_inspecao.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                pesquisarInspecao(edt_pesquisar_inspecao.getText().toString().trim());
                return false;
            }
        });

        listView_de_inspecoes = (ListView)findViewById(R.id.listView_de_inspecoes);

        Intent intent = getIntent();
        id_setor = intent.getIntExtra(Setor.COLUNA_id_Setor,0);
        id_local = intent.getIntExtra(Local.COLUNA_id_Local, 0);

        listView_de_inspecoes.setLongClickable(true);
        listView_de_inspecoes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_inspecoes.getItemAtPosition(position);
                mostrarDialogoOpcoes(cursor);

                return true;
            }
        });

        listView_de_inspecoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_de_inspecoes.getItemAtPosition(position);
                try {
                    Cursor cursorResultado = RiscoControl.cursorlistarTodos(cursor.getInt(0));
                    if(cursorResultado.moveToFirst()) {
                        id_inspecao = cursor.getInt(0);
                        Intent intent1 = new Intent(View_07_lista_de_inspecoes.this,
                                View_08_lista_de_riscos.class);
                        intent1.putExtra(Inspecao.COLUNA_id_Inspecao, id_inspecao);
                        startActivity(intent1);
                    } else {
                        continuarInspecao(cursor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data_atual = simpleDateFormat.format(data);
    }


    public void continuarInspecao(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view_aviso_inspecao = getLayoutInflater().inflate(R.layout.dialog_continue_03_inspecao, null);

        final ListView listView_aviso_opcoes_inspecao =
                (ListView) view_aviso_inspecao.findViewById(R.id.listView_aviso_opcoes_inspecao);


        builder.setView(view_aviso_inspecao);
        final AlertDialog dialog = builder.create();

        listView_aviso_opcoes_inspecao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        dialog.dismiss();
                        Intent intent = new Intent(View_07_lista_de_inspecoes.this,
                                View_04_registrar_risco.class);
                        intent.putExtra(Inspecao.COLUNA_id_Inspecao, cursor.getInt(0));
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
        String[] vetorColunas = {Inspecao.COLUNA_id_Inspecao,Inspecao.COLUNA_data_Inicio_Inspecao,
        Inspecao.COLUNA_data_Fim_Inspecao, Tecnico.COLUNA_nome_Tecnico};

        int[] vetorCodigo ={R.id.txt_item_codigo_inspecao_tecnico,R.id.txt_item_data_inicio_inspecao,
        R.id.txt_item_data_fim_inspecao,R.id.txt_item_nome_tecnico_inspecao};

        SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                this,R.layout.table_layout_03_lista_de_inspecoes,
                InspecaoControl.CursorlistarTodos(id_setor), vetorColunas,vetorCodigo,1);

        listView_de_inspecoes.setAdapter(adapter);
        DataBaseManager.getInstance().closeDatabase();
    }
    private void mostrarDialogoOpcoes(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Opções").setItems(R.array.opcoes_alert_dialog_inspecaoes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int opcaoMenu) {

                        switch (opcaoMenu) {
                            case 0://Editar
                                editar(cursor);
                                break;
                            case 1://Remover
                                remover(cursor);
                                break;
                            case 2://Relatorio
                                Intent intent = new Intent(View_07_lista_de_inspecoes.this,
                                        View_11_relatorio.class);
                                intent.putExtra(Inspecao.COLUNA_id_Inspecao, cursor.getInt(0));
                                startActivity(intent);
                                break;
                            default:
                        }//fim switch
                    }//fim onClick()

                }//DialogInterface
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }//mostrarDialogoOpcoes

    /**
     * Método, editar inspeção.
     * */
    public void editar(final Cursor cursor){

        /*Criando layout AlertDialog, editar inspecção.*/
        AlertDialog.Builder builder_01 = new AlertDialog.Builder(this);
        View view_inspecao = getLayoutInflater().inflate(R.layout.dialog_05_editar_inspecao,null);

        /*EditText, campos de inputs do layout*/
        final EditText edt_escolher_editar_tecnico_inspecao =
                (EditText)view_inspecao.findViewById(R.id.edt_escolher_editar_tecnico_inspecao);
        final EditText edt_escolher_editar_setor_inspecao =
                (EditText)view_inspecao.findViewById(R.id.edt_escolher_editar_setor_inspecao);

        final EditText edt_editar_data_inicio_inspecao =
                (EditText)view_inspecao.findViewById(R.id.edt_editar_data_inicio_inspecao);
        final EditText edt_editar_data_fim_inspecao =
                (EditText)view_inspecao.findViewById(R.id.edt_editar_data_fim_inspecao);

        /**
         * Realizando consulta no banco, retorna um Cursor de dados
         * de uma inspeção.*/
        Cursor dadosInspecao = InspecaoControl.cursorPesquisa(cursor.getInt(0));

        /*povoando os campos do layout com o resultado da consulta.*/
        if (dadosInspecao.moveToFirst()){
            do{
                id_inspecao = dadosInspecao.getInt(0);
                edt_editar_data_inicio_inspecao.setText(dadosInspecao.getString(1));
                edt_editar_data_fim_inspecao.setText(dadosInspecao.getString(2));

                id_tecnico = dadosInspecao.getInt(3);
                edt_escolher_editar_tecnico_inspecao.setText(dadosInspecao.getString(4));
                id_setor = dadosInspecao.getInt(5);
                edt_escolher_editar_setor_inspecao.setText(dadosInspecao.getString(6));

            }while (dadosInspecao.moveToNext());
        }

        /*Fechando a conexão com o banco.*/
        DataBaseManager.getInstance().closeDatabase();


        /*Button, layout AlertDialog, editar inspecção.*/
        /*Para escolher técnico e setor*/
        final Button btn_escolher_editar_tecnico_inspecao =
                (Button)view_inspecao.findViewById(R.id.btn_escolher_editar_tecnico_inspecao);
        final Button btn_escolher_editar_setor_inspecao =
                (Button)view_inspecao.findViewById(R.id.btn_escolher_editar_setor_inspecao);

        /*Para escolher data inicio e data fim*/
        final Button btn_escolher_editar_data_inicio_inspecao =
                (Button)view_inspecao.findViewById(R.id.btn_escolher_editar_data_inicio_inspecao);
        final Button btn_escolher_editar_data_fim_inspecao =
                (Button)view_inspecao.findViewById(R.id.btn_escolher_editar_data_fim_inspecao);

        /*Para cancelar(fechar dialog_layout) e e conformar edição de inspeção*/
        final Button btn_cancelar_editar_inpecao =
                (Button)view_inspecao.findViewById(R.id.btn_cancelar_editar_inpecao);
        final Button btn_editar_inspecao =
                (Button)view_inspecao.findViewById(R.id.btn_editar_inspecao);

        builder_01.setView(view_inspecao);
        final AlertDialog dialog_01 = builder_01.create();

        /*Botão escolher técnico*/
        btn_escolher_editar_tecnico_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Criando ListView Dialog de técnico*/
                AlertDialog.Builder builder_02 = new AlertDialog.Builder(View_07_lista_de_inspecoes.this);
                View view_tecnico = getLayoutInflater().inflate(R.layout.list_dialog_02_tecnico, null);

                final ListView listView_dialog_tecnico =
                        (ListView) view_tecnico.findViewById(R.id.listView_dialog_tecnico);
                final Button btn_dialog_novo_tecnico =
                        (Button) view_tecnico.findViewById(R.id.btn_dialog_novo_tecnico);

                builder_02.setView(view_tecnico);
                final AlertDialog dialog_02 = builder_02.create();

                String[] vetorColunas = {Tecnico.COLUNA_id_Tecnico, Tecnico.COLUNA_nome_Tecnico};
                int[] vetorCodigo = {R.id.txt_item_codigo_tecnico, R.id.txt_item_nome_tecnico};

                try {
                    /*Montando ListView de técnico*/
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                            View_07_lista_de_inspecoes.this, R.layout.layout_dialog_01_tecnico,
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
                        edt_escolher_editar_tecnico_inspecao.setText(cursor.getString(1));
                        id_tecnico = cursor.getInt(0);
                        dialog_02.dismiss();

                    }
                });

                btn_dialog_novo_tecnico.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*Fechando ListView Dialog de técnico*/
                        dialog_02.dismiss();

                        /*Criando Dialog novo de técnico*/
                        AlertDialog.Builder builder_03 =
                                new AlertDialog.Builder(View_07_lista_de_inspecoes.this);
                        View view_tecnico =
                                getLayoutInflater().inflate(R.layout.dialog_03_novo_tecnico, null);

                        // configurando o campo input
                        final EditText edt_novo_nome_tecnico =
                                (EditText) view_tecnico.findViewById(R.id.edt_novo_nome_tecnico);
                        final Button btn_cancelar_novo_tecnico =
                                (Button) view_tecnico.findViewById(R.id.btn_cancelar_novo_tecnico);
                        final Button btn_salvar_novo_tecnico =
                                (Button) view_tecnico.findViewById(R.id.btn_salvar_novo_tecnico);

                        //configurando botões
                        builder_03.setView(view_tecnico);
                        final AlertDialog dialog_03 = builder_03.create();

                        btn_salvar_novo_tecnico.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (edt_novo_nome_tecnico.length() < 3) {
                                    edt_novo_nome_tecnico.requestFocus();
                                    Toast.makeText(View_07_lista_de_inspecoes.this,
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
                                            edt_escolher_editar_tecnico_inspecao.setText(
                                            edt_novo_nome_tecnico.getText().toString().trim());

                                            dialog_03.dismiss();
                                            Toast.makeText(View_07_lista_de_inspecoes.this,
                                                    "Registro inserido!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(View_07_lista_de_inspecoes.this,
                                                "Falha ao inserir Registro no Banco de Dados!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                        btn_cancelar_novo_tecnico.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_03.cancel();
                            }
                        });

                        dialog_03.show();

                    }

                });

                dialog_02.show();
            }
        });

        /*Botão escolher setor*/
        btn_escolher_editar_setor_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Criando ListView Dialog de setor*/
                AlertDialog.Builder builder_04 = new AlertDialog.Builder(View_07_lista_de_inspecoes.this);
                View view_setor = getLayoutInflater().inflate(R.layout.list_dialog_03_setor, null);

                final ListView listView_dialog_setor =
                        (ListView) view_setor.findViewById(R.id.listView_dialog_setor);
                final Button btn_dialog_novo_setor =
                        (Button) view_setor.findViewById(R.id.btn_dialog_novo_setor);

                builder_04.setView(view_setor);
                final AlertDialog dialog_04 = builder_04.create();

                String[] vetorColunas = {Setor.COLUNA_id_Setor, Setor.COLUNA_nome_Setor,
                        Local.COLUNA_nome_Local};
                int[] vetorCodigo = {R.id.txt_item_codigo_setor_02, R.id.txt_item_nome_setor_02,
                        R.id.txt_item_nome_local_02};
                try {
                    /*Montando ListView de setor*/
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                            View_07_lista_de_inspecoes.this, R.layout.table_layout_02_lista_de_setores,
                            SetorControl.cursorListarTodos2(id_local), vetorColunas, vetorCodigo, 1);

                    listView_dialog_setor.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                listView_dialog_setor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor cursor = (Cursor) listView_dialog_setor.getItemAtPosition(position);
                        edt_escolher_editar_setor_inspecao.setText(cursor.getString(1));
                        id_setor = cursor.getInt(0);
                        /*Fechando ListView Dialog de setor*/
                        dialog_04.dismiss();
                    }
                });

                btn_dialog_novo_setor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Fechando ListView Dialog de setor*/
                        dialog_04.dismiss();

                        /*Criando Dialog novo de setor*/
                        AlertDialog.Builder builder_05 = new AlertDialog.Builder(View_07_lista_de_inspecoes.this);
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

                        builder_05.setView(view_setor);
                        final AlertDialog dialog_05 = builder_05.create();

                        btn_salvar_novo_setor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (edt_novo_nome_setor.length() < 3) {
                                    edt_novo_nome_setor.requestFocus();
                                    Toast.makeText(View_07_lista_de_inspecoes.this,
                                            "Campo Setor, Mínimo de 3 Caracteres!",
                                            Toast.LENGTH_LONG).show();
                                } else if (edt_novo_tipo_de_pessoas_setor.length() < 3) {
                                    edt_novo_tipo_de_pessoas_setor.requestFocus();
                                    Toast.makeText(View_07_lista_de_inspecoes.this,
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
                                        setor.setId_Local(id_local);

                                        int resultado = SetorControl.insert(setor);

                                        if (resultado > 0) {
                                            edt_escolher_editar_setor_inspecao.setText(
                                                    edt_novo_nome_setor.getText().toString());
                                            id_setor = resultado;
                                            dialog_05.dismiss();
                                            Toast.makeText(View_07_lista_de_inspecoes.this,
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
                                dialog_05.dismiss();
                            }
                        });

                        dialog_05.show();
                    }
                });

                dialog_04.show();

            }
        });
        /*Botão escolher data início*/
        btn_escolher_editar_data_inicio_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*Botão escolher data fim*/
        btn_escolher_editar_data_fim_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*Botão confirmar edição da inspeção*/
        btn_editar_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Inspecao inspecao = new Inspecao();
                    inspecao.setId_Inspecao(id_inspecao);
                    inspecao.setData_Inicio_Inspecao(
                            edt_editar_data_inicio_inspecao.getText().toString());
                    inspecao.setData_Fim_Inspecao(edt_editar_data_fim_inspecao.getText().toString());
                    inspecao.setId_Tecnico(id_tecnico);
                    inspecao.setId_Setor(id_setor);


                    int resultado = InspecaoControl.update(inspecao);

                    if(resultado >0 ){
                        Toast.makeText(View_07_lista_de_inspecoes.this,
                                "Registro alterado!",
                                Toast.LENGTH_LONG).show();

                        /*Fechando layout AlertDialog, editar inspecção.*/
                        dialog_01.dismiss();
                        onResume();/*Atualizando listView de inspeções*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(View_07_lista_de_inspecoes.this,
                            "Falha ao alterado registro!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        /*Botão cancelar edição da inspeção*/
        btn_cancelar_editar_inpecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fechando layout AlertDialog, editar inspecção.*/
                dialog_01.dismiss();
            }
        });
        dialog_01.show();

    }//fim médodo editar();

    /**
     * Método, remover inspeção.
     * */
    public void remover(final Cursor cursor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_deletar_34dp);
        builder.setTitle("Essa ação apagará todos os registros" +
                "\nde riscos vinculados a essa inspeção." +
                "\nDeseja prosseguir?");
        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    int resultado = InspecaoControl.delete(cursor.getInt(0));
                    if (resultado > 0) {

                        /**
                         * REMOVENDO TODOS OS REGISTROS DE RISCOS VINCULADOS A
                         * INSPEÇÃO SELECIONADA!*/
                        RiscoControl.deletarTudos(cursor.getInt(0));
                        onResume();/*Atualizando listView de linspeções*/
                        Toast.makeText(View_07_lista_de_inspecoes.this, " Registro removido!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(View_07_lista_de_inspecoes.this, "Falha ao remover registro!",
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


    public void pesquisarInspecao(String dataInicio){
        try {
            String[] vetorColunas = {Inspecao.COLUNA_id_Inspecao,Inspecao.COLUNA_data_Inicio_Inspecao,
                    Inspecao.COLUNA_data_Fim_Inspecao, Tecnico.COLUNA_nome_Tecnico};

            int[] vetorCodigo ={R.id.txt_item_codigo_inspecao_tecnico,R.id.txt_item_data_inicio_inspecao,
                    R.id.txt_item_data_fim_inspecao,R.id.txt_item_nome_tecnico_inspecao};

            SimpleCursorAdapter adapter = new  SimpleCursorAdapter(
                    this,R.layout.table_layout_03_lista_de_inspecoes,
                    InspecaoControl.pesquisar(dataInicio,id_setor), vetorColunas,vetorCodigo,1);

            listView_de_inspecoes.setAdapter(adapter);

            DataBaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
