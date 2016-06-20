package org.redborn.weddinglatte.android.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.CommentActivity;
import org.redborn.weddinglatte.android.view.main.item.CommentAdapter;
import org.redborn.weddinglatte.android.view.main.item.CommentItem;

import java.util.ArrayList;

public class CommentFragment extends Fragment {
    ArrayList<CommentItem> arDessert;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arDessert = new ArrayList<CommentItem>();
        CommentItem commentItem;

        commentItem = new CommentItem(R.drawable.redborn, "in1.테이블을 닦고 서빙을 해야해 친구에게");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "in2.말했지 자꾸내 앞길을 테트리스처럼 가로 막아");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "in3.다 등돌릴 수가 없잖아 ");
        arDessert.add(commentItem);
        commentItem = new CommentItem(R.drawable.redborn, "in4.난 여전히 마이크 앞이야 엠씨");
        arDessert.add(commentItem);

        View view = inflater.inflate(R.layout.fragment_comment, null) ;
        CommentAdapter Adapter = new CommentAdapter(getActivity(), R.layout.comment_item, arDessert) ;

        ListView listview = (ListView)view.findViewById(R.id.listView) ;
        listview.setAdapter(Adapter) ;
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
