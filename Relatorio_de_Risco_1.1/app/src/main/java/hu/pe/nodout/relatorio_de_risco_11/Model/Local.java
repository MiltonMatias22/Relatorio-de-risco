package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 02/02/2017.
 */
public class Local {
    public static final String TAG = Local.class.getSimpleName();
    public static final String NOME_TABELA_LOCAL = "LOCAL";

    //colunas da tabela Local
    public static final String COLUNA_id_Local = "_id";
    public static final String COLUNA_nome_Local = "nome_Local";
    public static final String COLUNA_data_Local = "data_Local";

    public static String criarTabela(){
        return "CREATE TABLE IF NOT EXISTS LOCAL ( " +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome_Local VARCHAR (255) NOT NULL, " +
                "data_Local VARCHAR (11) NOT NULL " +
                ");";
    }

    //propriedades da clase Inspeção
    private Integer id_Local;
    private String nome_Local;
    private String data_Local;

    public Local() {
    }
    public Integer getId_Local() {
        return id_Local;
    }

    public void setId_Local(Integer id_Local) {
        this.id_Local = id_Local;
    }

    public String getNome_Local() {
        return nome_Local;
    }

    public void setNome_Local(String nome_Local) {
        this.nome_Local = nome_Local;
    }
    public String getData_Local() {
        return data_Local;
    }

    public void setData_Local(String data_Local) {
        this.data_Local = data_Local;
    }

    @Override
    public String toString() {
        return  "Código: "+id_Local+"\n"+
                "Local: " + nome_Local + "\n" +
                "Data: " + data_Local;
    }
}
