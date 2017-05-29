package hu.pe.nodout.relatorio_de_risco_11.Model;

/**
 * Created by Milton Matias on 13/02/2017.
 */
public class Relatorio {
    private Integer id_Inspecao;
    private String nome_Tecnico;
    private String nome_Local;
    private String data_Inicio_Inspecao;
    private String data_Fim_Inspecao;
    private String nome_Setor;
    private int Quantidade_de_pessoas_setor;
    private String tipo_de_pessoas_setor;

    public Relatorio() {
    }

    public String getTipo_de_pessoas_setor() {
        return tipo_de_pessoas_setor;
    }

    public void setTipo_de_pessoas_setor(String tipo_de_pessoas_setor) {
        this.tipo_de_pessoas_setor = tipo_de_pessoas_setor;
    }

    public Integer getId_Inspecao() {
        return id_Inspecao;
    }

    public void setId_Inspecao(Integer id_Inspecao) {
        this.id_Inspecao = id_Inspecao;
    }

    public String getNome_Tecnico() {
        return nome_Tecnico;
    }

    public void setNome_Tecnico(String nome_Tecnico) {
        this.nome_Tecnico = nome_Tecnico;
    }

    public String getNome_Local() {
        return nome_Local;
    }

    public void setNome_Local(String nome_Local) {
        this.nome_Local = nome_Local;
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
}
