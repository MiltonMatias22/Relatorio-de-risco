package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 02/02/2017.
 */
public class Setor {
    public static final String TAG = Setor.class.getSimpleName();
    public static final String NOME_TABELA_SETOR = "SETOR";

    //colunas da tabela Local
    public static final String COLUNA_id_Setor = "_id";
    public static final String COLUNA_nome_Setor = "nome_Setor";
    public static final String COLUNA_quantidade_de_pessoas_setor= "quantidade_de_pessoas_setor";
    public static final String COLUNA_tipo_de_pessoas_setor = "tipo_de_pessoas_setor";
    public static final String COLUNA_id_Local = "id_Local";

    public static String criarTabela(){
        return "CREATE TABLE IF NOT EXISTS SETOR (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome_Setor VARCHAR (255) NOT NULL, " +
                "quantidade_de_pessoas_setor INTEGER NOT NULL, " +
                "tipo_de_pessoas_setor TEXT NOT NULL, " +
                "id_Local INT NOT NULL, " +
                "CONSTRAINT FK_ID_LOCAL FOREIGN KEY (id_Local) REFERENCES lOCAL (_id) ON DELETE CASCADE" +
                ");";
    }

    //propriedades da clase Inspeção
    private Integer id_Setor;
    private String nome_Setor;
    private int Quantidade_de_pessoas_setor;
    private String tipo_de_pessoas_setor;
    private Integer id_Local;

    public Setor() {
    }

    public Integer getId_Setor() {
        return id_Setor;
    }

    public void setId_Setor(Integer id_Setor) {
        this.id_Setor = id_Setor;
    }

    public String getNome_Setor() {
        return nome_Setor;
    }

    public void setNome_Setor(String nome_Setor) {
        this.nome_Setor = nome_Setor;
    }

    public int getQuantidade_de_pessoas_setor() {
        return Quantidade_de_pessoas_setor;
    }

    public void setQuantidade_de_pessoas_setor(int quantidade_de_pessoas_setor) {
        Quantidade_de_pessoas_setor = quantidade_de_pessoas_setor;
    }

    public String getTipo_de_pessoas_setor() {
        return tipo_de_pessoas_setor;
    }

    public void setTipo_de_pessoas_setor(String tipo_de_pessoas_setor) {
        this.tipo_de_pessoas_setor = tipo_de_pessoas_setor;
    }

    public Integer getId_Local() {
        return id_Local;
    }

    public void setId_Local(Integer id_Local) {
        this.id_Local = id_Local;
    }

    @Override
    public String toString() {
        return
                "id: " + id_Setor +"\n"+
                "Setor: " + nome_Setor;
    }
}
