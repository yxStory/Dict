package web.test.app.dictprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by yx on 2017/3/15.
 */
public class ResultActivity extends Activity{
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        listView=(ListView)findViewById(R.id.show);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        List<Map<String,String>> list=(List<Map<String,String>>) bundle.getSerializable("data");
        SimpleAdapter adapter=new SimpleAdapter(ResultActivity.this,list,R.layout.line,
                new String[]{"word","detail"},
                new int[]{R.id.my_word,R.id.my_detail});
        listView.setAdapter(adapter);
    }
}
