package hu.pe.nodout.relatorio_de_risco_11.View;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.RelatorioHtml;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.R;

public class View_11_relatorio extends AppCompatActivity {
    private static final String NOME_PASTA_APP = "hu.pe.nodout.relatorio_de_risco_1.1";
    private static final String GERADOS = "Retorios";
    private String relatorio;
    private WebView webView;
    int id_inspecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_11_relatorio);

        this.webView = (WebView)findViewById(R.id.webView);

        exibirRelatorioDeInspecao();
    }

    private void exibirRelatorioDeInspecao() {
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setSupportZoom(false);

        Intent intent = getIntent();

        id_inspecao = intent.getIntExtra(Inspecao.COLUNA_id_Inspecao,0);

        if(id_inspecao >0) {
            relatorio = RelatorioHtml.relatorio(id_inspecao);
            this.webView.loadData(relatorio, "text/html; charset=utf-8", "utf-8");
        }
    }

    public void gerarPDF(){//View view
        Document document = new Document(PageSize.A4);
        String nomeArquivo = "meuArquivoPDF.pdf";
        String cartaoSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(cartaoSD + File.separator + NOME_PASTA_APP);

        if(!pdfDir.exists()){
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GERADOS);//cria pasta
        if(!pdfSubDir.exists()){
            pdfSubDir.mkdir();
        }
        String nome_completo = Environment.getExternalStorageDirectory() + File.separator +
                NOME_PASTA_APP + File.separator + GERADOS + File.separator + nomeArquivo;

        File outPutFile = new File(nome_completo);
        if (outPutFile.exists()){
            outPutFile.delete();
        }

        // importado => import com.itextpdf.text.pdf.PdfWriter;
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nome_completo));
            //Criar documento para escrever
            document.open();
            document.addAuthor("Milton Matias");
            document.addCreator("Criar");
            document.addSubject("MapRisk");
            document.addCreationDate();
            document.addTitle("Olá Mundo!");

            XMLWorkerHelper xmlWorker = XMLWorkerHelper.getInstance();

            try {
                xmlWorker.parseXHtml(pdfWriter,document, new StringReader(relatorio));//classe RelatorioHtml
                document.close();
                Toast.makeText(this, "Gerando PDF", Toast.LENGTH_LONG).show();//alerta, corregando
                mostrarPDF(nome_completo,this);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }//fim try catch

    }//fim método gerarPDF()

    public void mostrarPDF(String arquivo,Context context){
        Toast.makeText(context, "Lendo Arquivo!", Toast.LENGTH_LONG).show();
        File file = new File(arquivo);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "appLocalizacao/pdf");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try{
            context.startActivity(intent);

        }catch(ActivityNotFoundException erro){
            Toast.makeText(context, " Não há aplicativo para esse executar o arquivo!", Toast.LENGTH_LONG).show();
        }

    }//fim método mostrarPDF()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_03_relatorio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            /*Voltar para o início*/
            case R.id.menu_item_09_inicio:
                Intent intent_01 = new Intent(View_11_relatorio.this, View_01_Inicio.class);
                startActivity(intent_01);
                finish();
               return true;

            /*Imprimir relatório de risco*/
            case R.id.menu_item_09_relatorio:gerarPDF();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}//fim class View_09_realatorio
