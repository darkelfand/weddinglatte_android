package org.redborn.weddinglatte.android.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.item.CommentAdapter;
import org.redborn.weddinglatte.android.view.main.item.CommentItem;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    ArrayList<CommentItem> arDessert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        arDessert = new ArrayList<CommentItem>();
        CommentItem commentItem;

        commentItem = new CommentItem(R.drawable.redborn, "1.테이블을 닦고 서빙을 해야해 친구에게");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "2.말했지 자꾸내 앞길을 테트리스처럼 가로 막아");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "3.다 등돌릴 수가 없잖아 ");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "4.난 여전히 마이크 앞이야 엠씨");
        arDessert.add(commentItem);

        CommentAdapter Adapter = new CommentAdapter(this, R.layout.comment_item, arDessert) ;

        ListView listview = (ListView)findViewById(R.id.listView) ;
        listview.setAdapter(Adapter) ;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
