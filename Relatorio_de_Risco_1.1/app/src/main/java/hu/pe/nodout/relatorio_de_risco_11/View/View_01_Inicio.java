package hu.pe.nodout.relatorio_de_risco_11.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_01_Inicio extends AppCompatActivity {
    /*Inicialização de variáveis. Componentes que irão sofrer mudanças dinânicas
    * */
    TextView text_logo_relatorio;
    Button btn_nova_inspecao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_01_inicio);

        btn_nova_inspecao = (Button)findViewById(R.id.btn_nova_inspecao);

        btn_nova_inspecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(View_01_Inicio.this,View_02_registrar_local.class);
                startActivity(intent);

            }
        });

        /*Logo do app*/
        Typeface font_lato_thin = Typeface.createFromAsset(getAssets(),"lato_thin.ttf");
        text_logo_relatorio = (TextView)findViewById(R.id.text_logo_relatorio_de_risco);
        text_logo_relatorio.setTypeface(font_lato_thin);

        /*Demais frases da tale*/
        Typeface font_lato_hairline = Typeface.createFromAsset(getAssets(),"lato_light.ttf");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_01_inicio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_01_nova_inpecoes:
                Intent intent_01 = new Intent(this,View_02_registrar_local.class);
                startActivity(intent_01);
                return true;

            case R.id.menu_item_02_consultar:
                Intent intent_02 = new Intent(this,View_05_lista_de_locais.class);
                startActivity(intent_02);
                return true;

            case R.id.menu_item_03_consultar_tecnico:
                Intent intent_03 = new Intent(this,View_09_lista_de_tecnicos.class);
                startActivity(intent_03);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
