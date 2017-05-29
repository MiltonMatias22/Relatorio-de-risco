package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 02/02/2017.
 */
public class Inspecao {

    public static final String TAG =Inspecao.class.getSimpleName();
    public static final String NOME_TABELA_INSPECAO = "INSPECAO";
    //colunas da tabela inspeção
    public static final String COLUNA_id_Inspecao = "_id";
    public static final String COLUNA_data_Inicio_Inspecao = "data_Inicio_Inspecao";
    public static final String COLUNA_data_Fim_Inspecao = "data_Fim_Inspecao";
    public static final String COLUNA_id_Tecnico = "id_Tecnico";
    public static final String COLUNA_id_Setor = "id_Setor";

    public static String criarTabela(){
        return "CREATE TABLE IF NOT EXISTS INSPECAO (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "data_Inicio_Inspecao VARCHAR (11) NOT NULL, " +
                "data_Fim_Inspecao VARCHAR (11), " +
                "id_Tecnico INT NOT NULL, " +
                "id_Setor INT NOT NULL, " +
                "CONSTRAINT FK_ID_TECNICO FOREIGN KEY (id_Tecnico) REFERENCES TECNICO (_id), " +
                "CONSTRAINT FK_ID_SETOR FOREIGN KEY (id_Setor) REFERENCES SETOR (_id) ON DELETE CASCADE" +
                ");";
    }

    //propriedades da clase Inspeção
    private Integer id_Inspecao;
    private String data_Inicio_Inspecao;
    private String data_Fim_Inspecao;
    private Integer id_Tecnico;
    private Integer id_Setor;


    public Inspecao() {
    }

    public Integer getId_Inspecao() {
        return id_Inspecao;
    }

    public void setId_Inspecao(Integer id_Inspecao) {
        this.id_Inspecao = id_Inspecao;
    }

    public String getData_Inicio_Inspecao() {
        return data_Inicio_Inspecao;
    }

    public void setData_Inicio_Inspecao(String data_Inicio_Inspecao) {
        this.data_Inicio_Inspecao = data_Inicio_Inspecao;
    }

    public String getData_Fim_Inspecao() {
        return data_Fim_Inspecao;
    }

    public void setData_Fim_Inspecao(String data_Fim_Inspecao) {
        this.data_Fim_Inspecao = data_Fim_Inspecao;
    }

    public Integer getId_Tecnico() {
        return id_Tecnico;
    }

    public void setId_Tecnico(Integer id_Tecnico) {
        this.id_Tecnico = id_Tecnico;
    }

    public Integer getId_Setor() {
        return id_Setor;
    }

    public void setId_Setor(Integer id_Setor) {
        this.id_Setor = id_Setor;
    }

}
