package org.redborn.weddinglatte.android.view.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.MainActivity;
import org.redborn.weddinglatte.android.view.SearchMapActivity;


public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_POSITION = "position";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(int position) {
        ProfileFragment f = new ProfileFragment();
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

    final int REQ_CODE_SELECT_IMAGE=100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragmentr, container, false);
        ImageView imgview = (ImageView)view.findViewById(R.id.imageView2);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //마이페이지 사진 업로드
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭시 처리로직
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        Button btn = (Button) view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭시 처리로직
                // 다이얼로그 바디
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());

                alert_confirm.setView(R.layout.password_dialog);

                // 확인 버튼 리스너
                alert_confirm.setPositiveButton("확인", null);
                alert_confirm.setNegativeButton("취소", null);
                // 다이얼로그 생성
                AlertDialog alert = alert_confirm.create();

                // 다이얼로그 타이틀
                alert.setTitle("비밀번호 변경");
                // 다이얼로그 보기
                alert.show();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
