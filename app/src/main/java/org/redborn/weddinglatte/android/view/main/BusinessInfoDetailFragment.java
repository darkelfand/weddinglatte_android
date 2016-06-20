package org.redborn.weddinglatte.android.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.item.BusinessItem;

import java.util.ArrayList;

public class BusinessInfoDetailFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    public BusinessInfoDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_business_info_detail, null) ;
        return view;
    }
}