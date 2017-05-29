package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 10/02/2017.
 */
public class Risco_setor {

    private String tipo_Risco;
    private String nome_Agentes_Causadores;
    private String grau_risco;
    private String recomendacoes_risco;
    private String data_risco;

    public Risco_setor() {
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

    @Override
    public String toString() {
        return
                "Risco: " + tipo_Risco + "\n" +
                "Agentes Causadores: " + nome_Agentes_Causadores + "\n" +
                "Grau: " + grau_risco + "\n" +
                "Recomendacoes: " + recomendacoes_risco + "\n" +
                "Data:" + data_risco;
    }
}
