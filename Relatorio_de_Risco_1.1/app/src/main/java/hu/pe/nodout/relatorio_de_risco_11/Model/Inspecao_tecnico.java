package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 10/02/2017.
 */
public class Inspecao_tecnico {

    public static final String COLUNA_data_Inicio_Inspecao = "data_Inicio_Inspecao";
    public static final String COLUNA_data_Fim_Inspecao = "data_Fim_Inspecao";
    public static final String COLUNA_nome_Tecnico = "nome_Tecnico";
    public static final String COLUNA_id_Setor = "id_Setor";
    public static final String COLUNA_nome_Setor = "nome_Setor";

    //propriedades da clase Inspeção
    private Integer id_Inspecao;
    private String data_Inicio_Inspecao;
    private String data_Fim_Inspecao;
    private String nome_Tecnico;

    public Inspecao_tecnico() {
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

    public String getNome_Tecnico() {
        return nome_Tecnico;
    }

    public void setNome_Tecnico(String nome_Tecnico) {
        this.nome_Tecnico = nome_Tecnico;
    }

    @Override
    public String toString() {
        return  "Código: " + id_Inspecao +"\n"+
                "Inicio: " + data_Inicio_Inspecao + "\n" +
                "Fim: " + data_Fim_Inspecao + "\n" +
                "Tecnico: " + nome_Tecnico;
    }
}
