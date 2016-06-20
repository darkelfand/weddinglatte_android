package org.redborn.weddinglatte.android.view.mypage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.item.CommunityAdapter;
import org.redborn.weddinglatte.android.view.main.item.CommunityItem;

import java.util.ArrayList;


public class ScrapFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_POSITION = "position";
    private String mParam1;
    private String mParam2;
    ArrayList<CommunityItem> arDessert;

    private OnFragmentInteractionListener mListener;

    public ScrapFragment() {
        // Required empty public constructor
    }

    public static ScrapFragment newInstance(int position) {
        ScrapFragment f = new ScrapFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arDessert = new ArrayList<CommunityItem>();
        CommunityItem communityItem;
        communityItem = new CommunityItem(R.drawable.weddinghall1, "라프로메사 웨딩홀");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "1.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "2.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.weddinghall1, "초코파이 웨딩홀");
        arDessert.add(communityItem);
        communityItem = new CommunityItem(R.drawable.redborn, "4.커뮤니티 \n" + "\n" + "이건우리 안의 소리 다시말하지만\n" + "\n" + "나의 소원은 독립임  ");
        arDessert.add(communityItem);


        View view = inflater.inflate(R.layout.fragment_scrap, null) ;
        CommunityAdapter Adapter = new CommunityAdapter(getActivity(), R.layout.community_item, arDessert) ;

        ListView listview = (ListView) view.findViewById(R.id.listView1) ;
        listview.setAdapter(Adapter) ;
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
