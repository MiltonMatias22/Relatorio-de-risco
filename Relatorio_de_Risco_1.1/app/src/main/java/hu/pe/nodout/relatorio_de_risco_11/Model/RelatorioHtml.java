package hu.pe.nodout.relatorio_de_risco_11.Model;

import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.Control.RelatorioControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.RiscoControl;
import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;

/**
 * Created by Milton Matias on 15/02/2017.
 */
public class RelatorioHtml {

    public static String relatorio(int id_inspecao){

        Relatorio relatorio = RelatorioControl.dadosRelatorio_parte_01(id_inspecao);

        List<Risco> listariscos = RiscoControl.listarTodos(id_inspecao);

        StringBuilder relatorioHtml = html(relatorio,listariscos);

        /*Retornando relatório*/
        return relatorioHtml.toString();
    }

    private static StringBuilder css(){
        StringBuilder css = new StringBuilder();
        css.append("body {" +
                "            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; " +
                "            font-size: 14px; " +
                "            line-height: 1.42857143; " +
                "            color: #333; " +
                "            background-color: #fff;} " +
                " " +
                "        hr {" +
                "            margin-top: 20px; " +
                "            margin-bottom: 20px; " +
                "            border: 0; " +
                "            border-top: 1px solid #eee;} " +
                " " +
                "        .table {" +
                "            border-collapse: collapse !important; " +
                "            width: 100%; " +
                "            font-size: 12px;} " +
                " " +
                "        .table-bordered th, " +
                "        .table-bordered td { " +
                "            border: 1px solid #ddd !important; " +
                "            padding: 4px;} " +
                " " +
                "        .table-responsive {" +
                "            min-height: .01%; " +
                "            overflow-x: auto;} " +
                " " +
                "        /*-- Tabelas responsivas */ " +
                "        @media screen and (max-width: 767px) { " +
                "            .table-responsive { " +
                "                width: 100%; " +
                "                margin-bottom: 15px; " +
                "                overflow-y: hidden; " +
                "                -ms-overflow-style: -ms-autohiding-scrollbar; " +
                "                border: 1px solid #ddd;} " +
                " " +
                "            .table-responsive > .table {margin-bottom: 0;} " +
                " " +
                "            .table-responsive > .table > thead > tr > th, " +
                "            .table-responsive > .table > tbody > tr > th, " +
                "            .table-responsive > .table > tfoot > tr > th, " +
                "            .table-responsive > .table > thead > tr > td, " +
                "            .table-responsive > .table > tbody > tr > td, " +
                "            .table-responsive > .table > tfoot > tr > td { " +
                "                white-space: nowrap;} " +
                " " +
                "            .table-responsive > .table-bordered {border: 0;} " +
                " " +
                "            .table-responsive > .table-bordered > thead > tr > th:first-child, " +
                "            .table-responsive > .table-bordered > tbody > tr > th:first-child, " +
                "            .table-responsive > .table-bordered > tfoot > tr > th:first-child, " +
                "            .table-responsive > .table-bordered > thead > tr > td:first-child, " +
                "            .table-responsive > .table-bordered > tbody > tr > td:first-child, " +
                "            .table-responsive > .table-bordered > tfoot > tr > td:first-child {" +
                "                border-left: 0;} " +
                " " +
                "            .table-responsive > .table-bordered > thead > tr > th:last-child, " +
                "            .table-responsive > .table-bordered > tbody > tr > th:last-child, " +
                "            .table-responsive > .table-bordered > tfoot > tr > th:last-child, " +
                "            .table-responsive > .table-bordered > thead > tr > td:last-child, " +
                "            .table-responsive > .table-bordered > tbody > tr > td:last-child, " +
                "            .table-responsive > .table-bordered > tfoot > tr > td:last-child { " +
                "                border-right: 0;} " +
                " " +
                "            .table-responsive > .table-bordered > tbody > tr:last-child > th, " +
                "            .table-responsive > .table-bordered > tfoot > tr:last-child > th, " +
                "            .table-responsive > .table-bordered > tbody > tr:last-child > td, " +
                "            .table-responsive > .table-bordered > tfoot > tr:last-child > td { " +
                "                border-bottom: 0;} } " +
                " " +
                "        .text-left {text-align: left;} " +
                "        .text-right {text-align: right;} " +
                "        .text-center {text-align: center;} " +
                "        .text-justify {text-align: justify;} " +
                " " +
                "        #th-titulo-01{ " +
                "            background-color: #1f1f1f; " +
                "            color: white; " +
                "            font-size: 15px; " +
                "            padding: 10px 0px 10px 0px;} " +
                " " +
                "        #th-titulo-02{ " +
                "            background-color: #343434; " +
                "            color: white; " +
                "            font-size: 13px; " +
                "            padding: 8px 0px 8px 0px;} " +
                " " +
                "        #th-titulo-03{ " +
                "            background-color: #caffe7;} " +
                " " +
                "        .c-fisico{background-color: #33d546; color: white;} " +
                "        .c-quimico{background-color: #dd1b1b;color: white;} " +
                "        .c-biologico{background-color: #870404;color: white;} " +
                "        .c-ergonomico{background-color: #fcff16;color:black} " +
                "        .c-mecanico{background-color: #4864f4;color:white}");

        /*Retornando StringBuilder CSS*/
        return css;
    }

    private static StringBuilder html(Relatorio r ,List<Risco> listariscos){
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html> " +
                "<html> " +
                "    <head><meta charset='UTF-8'/> " +
                "        <title>Mapa de Risco</title> " +
                "    </head> " +
                "    <style> " +
                " " +css()+ /*Pegando css*/
                "    </style> " +
                "    <body> " +
                "        <table class='table table-bordered text-center table-responsive'> <!-- --inicio table - -->" +
                "            <tr><!-- --> " +
                "                <th colspan='5' id='th-titulo-01'>Relatório de Inspeção</th> " +
                "            </tr><!-- --> " +
                "            <tr><!-- --> " +
                "                <th>Técnico:</th> " +
                "                <td colspan='4'>"+r.getNome_Tecnico()+"</td> " +
                "            </tr><!-- --> " +
                "            <tr><!-- --> " +
                "                <th colspan='3'>Local/Área</th> " +
                "                <th>Início</th>  " +
                "                <th>Fim</th> " +
                "            </tr><!-- --> " +
                "            <tr><!-- --> " +
                "                <td colspan='3'>"+r.getNome_Local()+"</td> " +
                "                <td>"+r.getData_Inicio_Inspecao()+"</td>  " +
                "                <td>"+r.getData_Fim_Inspecao()+"</td> " +
                "            </tr><!-- --> " +
                " " +
                "            <tr><td  colspan='5'><br/></td></tr><!-- --------Fim Cabeçalho --> " +
                " " +
                "            <tr><!-- --> " +
                "                <th colspan='5' id='th-titulo-02'>Tabela de Riscos</th> " +
                "            </tr><!-- --> " +
                " " +
                "            <tr id='th-titulo-03'><!-- Título Tabela de Riscos --> " +
                "                <th>Tipo</th> " +
                "                <th>Causadores</th>  " +
                "                <th>Grau</th> " +
                "                <th>Recomendações</th> " +
                "                <th>Data</th>  " +
                "            </tr><!-- --> " );

        for (Risco risco: listariscos ) {

        html.append("<tr> <td class=");

            if(risco.getTipo_Risco().equals("Físico")){html.append("'c-fisico'");
            }else if(risco.getTipo_Risco().equals("Químico")){html.append("'c-quimico'");

            }else if(risco.getTipo_Risco().equals("Biológico")){html.append("'c-biologico'");

            }else if(risco.getTipo_Risco().equals("Ergonômico")){html.append("'c-ergonomico'");

            }else if(risco.getTipo_Risco().equals("Mecânico")){html.append("'c-mecanico'");}

        html.append(">"+risco.getTipo_Risco()+"</td> ");

        html.append("<td class='text-justify'>"+risco.getNome_Agentes_Causadores()+"</td>  ");
        html.append("<td>"+risco.getGrau_risco()+"</td> " +
                    "<td class='text-justify'>"+risco.getRecomendacoes_risco()+"</td>  " +
                    "<td>"+risco.getData_risco()+"</td>" +
                    "</tr><!-- --> ");
        }

        html.append("<tr><!-- --> " +
                "                <td colspan='5'><br/></td> <!-- --------Fim tabela risco --> " +
                "            </tr><!-- --> " +
                " " +
                "            <tr><!-- --> " +
                "                <th class='text-left'>SETOR:</th> " +
                "                <td colspan='4'>"+r.getNome_Setor()+"</td> " +
                "            </tr><!-- --> " +
                "            <tr><!-- --> " +
                "                <th class='text-left'>TIPO/PESSOAS:</th> " +
                "                <td colspan='4'>"+r.getTipo_de_pessoas_setor()+"</td>  " +
                "            </tr><!-- --> " +
                "            <tr><!-- --> " +
                "                <th class='text-left'>Nª:</th> " +
                "                <td colspan='4'>"+r.getQuantidade_de_pessoas_setor()+"</td>  " +
                "            </tr><!-- --> " +
                " " +
                "        </table><!-- ------------------------Fim table --------------------- --> " +
                "        <hr/> " +
                "    </body> " +
                "</html>");

        /*Retornando StringBuilder html*/
        return html;
    }


}
