package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 02/02/2017.
 */
public class Tecnico {
    public static final String TAG = Tecnico.class.getSimpleName();
    public static final String NOME_TABELA_TECNICO = "TECNICO";

    //colunas da tabela Local
    public static final String COLUNA_id_Tecnico = "_id";
    public static final String COLUNA_nome_Tecnico = "nome_Tecnico";
    public static final String COLUNA_data_Tecnico = "data_Tecnico";

    public static String criarTabela(){
        return "CREATE TABLE IF NOT EXISTS TECNICO ( " +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome_Tecnico VARCHAR (255) NOT NULL," +
                "data_Tecnico VARCHAR (11) NOT NULL" +
                ");";
    }

    //propriedades da clase Inspeção
    private Integer id_Tecnico;
    private String nome_Tecnico;
    private  String data_Tecnico;


    public Integer getId_Tecnico() {
        return id_Tecnico;
    }

    public void setId_Tecnico(Integer id_Tecnico) {
        this.id_Tecnico = id_Tecnico;
    }

    public String getNome_Tecnico() {
        return nome_Tecnico;
    }

    public void setNome_Tecnico(String nome_Tecnico) {
        this.nome_Tecnico = nome_Tecnico;
    }

    public String getData_Tecnico() {
        return data_Tecnico;
    }

    public void setData_Tecnico(String data_Tecnico) {
        this.data_Tecnico = data_Tecnico;
    }
}
