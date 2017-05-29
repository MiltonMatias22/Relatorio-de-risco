package hu.pe.nodout.relatorio_de_risco_11.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hu.pe.nodout.relatorio_de_risco_11.Control.InspecaoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.RiscoControl;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Risco;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_04_registrar_risco extends AppCompatActivity {

    EditText edt_tipo_agentes_causadores_risco,
             edt_grau_risco,edt_recomendacoes_risco;
    TextView txt_titulo_risco;
    String data_atual = "";
    Button btn_salvar_risco,btn_escolher_grau_risco;
    int count = 0, id_inspecao, resultado = 0, ativador = 0, resultado_contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_04_registrar_risco);

        txt_titulo_risco = (TextView)findViewById(R.id.txt_titulo_risco);
        edt_tipo_agentes_causadores_risco = (EditText)findViewById(R.id.edt_tipo_agentes_causadores_risco);
        edt_grau_risco = (EditText)findViewById(R.id.edt_grau_risco);
        edt_recomendacoes_risco = (EditText)findViewById(R.id.edt_recomendacoes_risco);
        btn_salvar_risco = (Button)findViewById(R.id.btn_salvar_risco);
        btn_escolher_grau_risco = (Button)findViewById(R.id.btn_escolher_grau_risco);

        btn_escolher_grau_risco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialogGrauRisco();
            }
        });


        //pegando a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data_atual =simpleDateFormat.format(data);

        btn_salvar_risco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_tipo_agentes_causadores_risco.length() < 3) {

                    edt_tipo_agentes_causadores_risco.requestFocus();
                    Toast.makeText(View_04_registrar_risco.this,
                            "Campo Tipo Agente, Mínimo 3 Caracteres!",
                            Toast.LENGTH_LONG).show();

                } else if (edt_recomendacoes_risco.length() < 3) {

                    edt_recomendacoes_risco.requestFocus();
                    Toast.makeText(View_04_registrar_risco.this,
                            "Campo Recomendações, Mínimo 3 caracteres!",
                            Toast.LENGTH_LONG).show();

                }else {
                    inserir_riscos();
            }
            }
        });

        Intent intent = getIntent();
        id_inspecao = intent.getIntExtra(Inspecao.COLUNA_id_Inspecao, 0);

        ativador = intent.getIntExtra("ativador", 0);
        resultado_contador = intent.getIntExtra("resultado_contador", 0);

        if(ativador == 1){
            if(resultado_contador == 1){
                count = 1;
                txt_titulo_risco.setText("Químico");

            }else if (resultado_contador == 2){
                count = 2;
                txt_titulo_risco.setText("Biológico");

            }else if (resultado_contador == 3){
                count = 3;
                txt_titulo_risco.setText("Ergométrico");

            }else if (resultado_contador == 4){
                count = 4;
                txt_titulo_risco.setText("Mecânico");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_05_registrar_risco,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_01_nova_inpecoes:
                /*Cancelando inspeção*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inserir_riscos(){

          if(count == 0){

            resultado = insert();//salvando risco Físico

            if(resultado>0){

                txt_titulo_risco.setText("Químico");
                limparCampos();/*limpando campos*/
                Toast.makeText(this,"Risco Físico Adicionado!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(count == 1){

            resultado = insert();//salvando risco Químico

            if(resultado>0){

                txt_titulo_risco.setText("Biológico");
                limparCampos();
                Toast.makeText(this,"Risco Químico Adicionado!",Toast.LENGTH_SHORT).show();
            }

        }
        else if(count == 2){

            resultado = insert();//salvando risco Bilógico

            if(resultado>0){

                txt_titulo_risco.setText("Ergonômico");
                limparCampos();
                Toast.makeText(this,"Físico Biológico Adicionado!",Toast.LENGTH_SHORT).show();
            }

        }
        else if(count == 3){

            resultado = insert();//salvando risco Ergonômico

            if(resultado>0){
                txt_titulo_risco.setText("Mecânico");
                limparCampos();
                Toast.makeText(this,"Risco Ergonômico Adicionado!",Toast.LENGTH_SHORT).show();
            }

        } else if (count == 4){

            resultado = insert();//salvando risco mecânico

            if (resultado>0){

                limparCampos();
                Toast.makeText(this,"Risco Mecânico Adicionado!",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Inspeção Realizada Com Sucesso!",Toast.LENGTH_LONG).show();

                /*Atualizando data fim da inspeção*/
                try {
                    Inspecao inspecao = new Inspecao();
                    inspecao.setId_Inspecao(id_inspecao);
                    inspecao.setData_Fim_Inspecao(data_atual);
                    InspecaoControl.atualizarDataFim_inspecao(inspecao);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, View_11_relatorio.class);
                intent.putExtra(Inspecao.COLUNA_id_Inspecao, id_inspecao);
                startActivity(intent);
                finish();//destroi a activity liberando espaço na memória do dispositivo
            }
        }
            count++;//conntador

    }// fim titulo_nome_de_riscos()

    public int insert(){

        try {
            Risco risco = new Risco();
            risco.setTipo_Risco(txt_titulo_risco.getText().toString());
            risco.setNome_Agentes_Causadores(edt_tipo_agentes_causadores_risco.getText().toString().trim());
            risco.setGrau_risco(edt_grau_risco.getText().toString().trim());
            risco.setRecomendacoes_risco(edt_recomendacoes_risco.getText().toString().trim());
            risco.setId_inspecao(id_inspecao);
            risco.setData_risco(data_atual);
            resultado = RiscoControl.insert(risco);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }//fim insert()

    public void showListDialogGrauRisco(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View listDialogGrauRisco = getLayoutInflater().inflate(
                R.layout.list_dialog_01_grau_risco,null);

        final ListView listViewDialog_grauRisco =
                (ListView)listDialogGrauRisco.findViewById(R.id.listViewDialog_grauRisco);

        builder.setView(listDialogGrauRisco);
        final AlertDialog dialog = builder.create();

        listViewDialog_grauRisco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String grau_risco = (String)listViewDialog_grauRisco.getItemAtPosition(position);
                edt_grau_risco.setText(grau_risco);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Método limpar Campos
     * */
    public void limparCampos(){
        edt_tipo_agentes_causadores_risco.setText("");
        edt_grau_risco.setText("");
        edt_recomendacoes_risco.setText("");
        edt_tipo_agentes_causadores_risco.requestFocus();
    }

}
