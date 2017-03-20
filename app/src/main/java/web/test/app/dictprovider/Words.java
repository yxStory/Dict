package web.test.app.dictprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yx on 2017/3/20.
 */
public final class Words {
    //定义ContentProvider的Authority
    public static final String AUTHORITY="web.test.provider.dictprovider";
    //定义一个静态内部类
    public static final class Word implements BaseColumns {
        //定义Content所允许的三个数据列
        public static final String _ID = "_id";
        public static final String WORD= "word";
        public static final String DETAIL="detail";
        //定义该Content提供服务的两个Uri
        public final Uri DICT_CONETNT_URI= Uri.parse("content://"+AUTHORITY+"words");
        public final Uri WORD_CONTENT_URI= Uri.parse("content://"+AUTHORITY+"word");
    }
}
