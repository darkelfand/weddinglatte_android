package org.redborn.weddinglatte.android.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.WriteBoardActivity;
import org.redborn.weddinglatte.android.view.main.item.CommunityAdapter;
import org.redborn.weddinglatte.android.view.main.item.CommunityItem;

import java.util.ArrayList;


public class CommunityFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    ArrayList<CommunityItem> arDessert;
    public CommunityFragment() {
        // Required empty public constructor
    }
    public static CommunityFragment newInstance(int position) {
        CommunityFragment f = new CommunityFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arDessert = new ArrayList<CommunityItem>();
        CommunityItem communityItem;

        communityItem = new CommunityItem(R.drawable.redborn, "1.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "2.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "3.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "4.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);


        View view = inflater.inflate(R.layout.fragment_community_list, null) ;
        CommunityAdapter Adapter = new CommunityAdapter(getActivity(), R.layout.community_item, arDessert) ;

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteBoardActivity.class);
                startActivity(intent);
            }
        });

        ListView listview = (ListView) view.findViewById(R.id.listView1) ;
        listview.setAdapter(Adapter) ;

        return view;
    }
    private ArrayList<String> item;

}