package hu.pe.nodout.relatorio_de_risco_11.DataBase;

import android.app.Application;
import android.content.Context;

/**
 * Created by Milton Matias on 13/02/2017.
 */
public class App extends Application{
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DataBaseManager.inicializarInstance(dbHelper);
    }
    public static Context getContext(){
        return context;
    }
}
