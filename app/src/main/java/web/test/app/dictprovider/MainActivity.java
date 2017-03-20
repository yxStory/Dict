package web.test.app.dictprovider;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    MyDatabaseHelper dbHelper;
    Button insert,search,show;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert=(Button)findViewById(R.id.insert);
        search=(Button)findViewById(R.id.search);
        show=(Button)findViewById(R.id.show);
        listView=(ListView)findViewById(R.id.list);
        //调用MyDatabaseHelper,新建名为myDict.db3的数据库
        dbHelper=new MyDatabaseHelper(this,"myDict.db3",1);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取用户输入
                String word=((EditText)findViewById(R.id.word)).getText().toString();
                String detail=((EditText)findViewById(R.id.detail)).getText().toString();
                //调用insertData方法插入数据
                insertData(dbHelper.getReadableDatabase(),word,detail);
                Toast.makeText(MainActivity.this,"插入数据成功！",Toast.LENGTH_SHORT).show();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行查询语句
                Cursor cursor=dbHelper.getReadableDatabase().rawQuery("select * from dict",null);
                //调用inflateList方法显示查到的全部数据
                inflateList(cursor);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=((EditText)findViewById(R.id.text)).getText().toString();
                //查询语句
                Cursor cursor=dbHelper.getReadableDatabase().rawQuery(
                        "select * from dict where word like ? or detail like ?",
                        new String[]{"%" +text+ "%","%"+text+"%"});
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",converCursorToList(cursor));
                Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected ArrayList<Map<String,String>> converCursorToList(Cursor cursor){
        ArrayList<Map<String,String>> result=new ArrayList<Map<String,String>>();
        //遍历Cursor结果集
        while (cursor.moveToNext()){
            //将结果集存入ArrayList
            Map<String,String> map=new HashMap<String,String>();
            //取出查询结构的第二、第三列
            map.put("word",cursor.getString(1));
            map.put("detail",cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    /**
     *插入数据到数据库
     * @param db
     * @param word
     * @param detail
     */
    private void insertData(SQLiteDatabase db, String word, String detail){
        db.execSQL("insert into dict values(null, ? , ? )",
            new String[]{word,detail});
    }
    private void inflateList(Cursor cursor){
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(MainActivity.this,R.layout.line,cursor,
                new String[]{"word","detail"},
                new int[]{R.id.my_word,R.id.my_detail});
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出程序时关闭MyDatabaseHelper中的SQLiteDataBase
        if(dbHelper!=null){
            dbHelper.close();
        }
    }
}
