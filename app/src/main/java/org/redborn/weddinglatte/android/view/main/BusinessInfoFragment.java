package org.redborn.weddinglatte.android.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.item.BusinessAdapter;
import org.redborn.weddinglatte.android.view.main.item.BusinessItem;

import java.util.ArrayList;


public class BusinessInfoFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    public BusinessInfoFragment() {
        // Required empty public constructor
    }
    public static BusinessInfoFragment newInstance(int position) {
        BusinessInfoFragment f = new BusinessInfoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    ArrayList<BusinessItem> arDessert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arDessert = new ArrayList<BusinessItem>();
        BusinessItem businessItem;

        businessItem = new BusinessItem(R.drawable.weddinghall1, "라프로메사 웨딩홀");
        arDessert.add(businessItem);
        businessItem = new BusinessItem(R.drawable.weddinghall2, "포시즌 웨딩홀");
        arDessert.add(businessItem);
        businessItem = new BusinessItem(R.drawable.weddinghall3, "안드로메다 웨딩홀");
        arDessert.add(businessItem);
        businessItem = new BusinessItem(R.drawable.weddinghall1, "초코파이 웨딩홀");
        arDessert.add(businessItem);
        businessItem = new BusinessItem(R.drawable.weddinghall3, "안드로메다 웨딩홀");
        arDessert.add(businessItem);

        View view = inflater.inflate(R.layout.fragment_business_info_list, null) ;
        BusinessAdapter Adapter = new BusinessAdapter(getActivity(), R.layout.business_item, arDessert) ;

        ListView listview = (ListView) view.findViewById(R.id.listView1) ;
        listview.setAdapter(Adapter) ;

        return view;
    }
    private ArrayList<String> item;

}