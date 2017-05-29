package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Relatorio;

/**
 * Created by Milton Matias on 13/02/2017.
 */
public class RelatorioControl {

    public static Relatorio dadosRelatorio_parte_01(int id_inspecao){

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        String sql = "SELECT I._id, " +
                     "T.nome_Tecnico, " +
                     "L.nome_Local, " +
                     "I.data_Inicio_Inspecao, I.data_Fim_Inspecao, " +
                     "S.nome_Setor, S.quantidade_de_pessoas_setor, S.tipo_de_pessoas_setor " +
                     "FROM INSPECAO I JOIN TECNICO T ON T._id = I.id_Tecnico " +
                     "JOIN SETOR S ON S._id = I.id_Setor " +
                     "JOIN LOCAL L ON L._id = S.id_Local " +
                     "WHERE I._id = "+id_inspecao;
        Cursor cursor = db.rawQuery(sql,null);
        Relatorio relatorio = new Relatorio();
        if(cursor.moveToFirst()) {
            do {
                relatorio.setId_Inspecao(cursor.getInt(0));
                relatorio.setNome_Tecnico(cursor.getString(1));
                relatorio.setNome_Local(cursor.getString(2));
                relatorio.setData_Inicio_Inspecao(cursor.getString(3));
                relatorio.setData_Fim_Inspecao(cursor.getString(4));
                relatorio.setNome_Setor(cursor.getString(5));
                relatorio.setQuantidade_de_pessoas_setor(cursor.getInt(6));
                relatorio.setTipo_de_pessoas_setor(cursor.getString(7));
            }while (cursor.moveToNext());
        }
        cursor.close();
        DataBaseManager.getInstance().closeDatabase();



        return relatorio;
    }
}
