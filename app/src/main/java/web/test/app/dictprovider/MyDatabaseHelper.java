package web.test.app.dictprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yx on 2017/3/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{
    final String CREATE_TABLE_SQL =
            "create table dict(_id integer primary key autoincrement , word , detail)";

    public MyDatabaseHelper(Context context,String name,int version){
        super(context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表dict
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("----onUpgradeCalled----"+oldVersion+"---->>>"+newVersion);
    }
}
