package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 02/02/2017.
 */
public class Risco {
    public static final String TAG = Risco.class.getSimpleName();
    public static final String NOME_TABELA_RISCO = "RISCO";

    //colunas da tabela Local
    public static final String COLUNA_id_Risco = "_id";
    public static final String COLUNA_tipo_Risco = "tipo_Risco";
    public static final String COLUNA_nome_Agentes_Causadores = "nome_Agentes_Causadores";
    public static final String COLUNA_grau_risco = "grau_risco";
    public static final String COLUNA_recomendacoes_risco = "recomendacoes_risco";
    public static final String COLUNA_data_risco= "data_risco";
    public static final String COLUNA_id_Inspecao = "id_Inspecao";

    public static String criarTabela(){
        return "CREATE TABLE IF NOT EXISTS RISCO (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "tipo_Risco VARCHAR (20) NOT NULL CHECK (tipo_Risco IN ('Ergonômico', 'Biológico', 'Químico', 'Físico', 'Mecânico')), " +
                "nome_Agentes_Causadores TEXT NOT NULL, " +
                "grau_risco VARCHAR (10) NOT NULL CHECK (grau_risco IN ('PEQUENO', 'MÉDIO', 'GRANDE')), " +
                "recomendacoes_risco TEXT NOT NULL, " +
                "data_risco VARCHAR (11) NOT NULL, " +
                "id_Inspecao INT NOT NULL, " +
                "CONSTRAINT FK_ID_INSPECAO FOREIGN KEY (id_Inspecao) REFERENCES INSPECAO (_id) ON DELETE CASCADE" +
                ");";
    }

    //propriedades da clase Inspeção
    private Integer id_Risco;
    private Integer id_inspecao;
    private String tipo_Risco;
    private String nome_Agentes_Causadores;
    private String grau_risco;
    private String recomendacoes_risco;
    private String data_risco;

    public Integer getId_Risco() {
        return id_Risco;
    }

    public void setId_Risco(Integer id_Risco) {
        this.id_Risco = id_Risco;
    }

    public Integer getId_inspecao() {
        return id_inspecao;
    }

    public void setId_inspecao(Integer id_inspecao) {
        this.id_inspecao = id_inspecao;
    }

    public String getTipo_Risco() {
        return tipo_Risco;
    }

    public void setTipo_Risco(String tipo_Risco) {
        this.tipo_Risco = tipo_Risco;
    }

    public String getNome_Agentes_Causadores() {
        return nome_Agentes_Causadores;
    }

    public void setNome_Agentes_Causadores(String nome_Agentes_Causadores) {
        this.nome_Agentes_Causadores = nome_Agentes_Causadores;
    }

    public String getGrau_risco() {
        return grau_risco;
    }

    public void setGrau_risco(String grau_risco) {
        this.grau_risco = grau_risco;
    }

    public String getRecomendacoes_risco() {
        return recomendacoes_risco;
    }

    public void setRecomendacoes_risco(String recomendacoes_risco) {
        this.recomendacoes_risco = recomendacoes_risco;
    }

    public String getData_risco() {
        return data_risco;
    }

    public void setData_risco(String data_risco) {
        this.data_risco = data_risco;
    }

    public Risco() {
    }

}
