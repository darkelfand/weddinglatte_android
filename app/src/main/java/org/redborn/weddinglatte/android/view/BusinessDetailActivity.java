package org.redborn.weddinglatte.android.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.TitlePageIndicator;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.CommentFragment;

public class BusinessDetailActivity extends AppCompatActivity{

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap map;

    // 초기화
    GoogleMap mGoogleMap;
    // 구글맵 객체
    LatLng loc = new LatLng(0, 0);
    // 위치 좌표 설정
    CameraPosition cp = new CameraPosition.Builder().target((loc)).zoom(7)
            .build();
    RatingBar rating;
    TextView tv01;
    Button btnClose;
    private final int CONTENTS_VIEW_POSITION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("라프로메사 웨딩홀");
        setSupportActionBar(toolbar);

        //미리보기 생성
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);

        viewPager.setAdapter(adapter);
        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(viewPager);
        // 구글 맵 객체 생성
        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_map)).getMap();

        moveDisplay(35.864144, 129.194698, 13);
        addMarker(35.864144, 129.194698);

        //별점 표시
        rating = (RatingBar) findViewById(R.id.ratingBar1);
        tv01 = (TextView) findViewById(R.id.star_score);

        rating.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
        rating.setRating((float) 2.5);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
        rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CommentFragment fragment = new CommentFragment();
        fragmentTransaction.add(R.id.comment_list, fragment );
        fragmentTransaction.commit();
    }

    // 마커 추가하기
    void addMarker(double x, double y) {
        LatLng loc = new LatLng(x, y);
    // 위치 좌표 설정
        MarkerOptions marker = new MarkerOptions().position(loc);
        mGoogleMap.addMarker(marker);
    // 마커표시
    }
    // 화면 이동하기
    void moveDisplay(double x, double y, int zoom) {
        LatLng loc = new LatLng(x, y);
        // 위치 좌표 설정
        CameraPosition cp = new CameraPosition.Builder().target((loc))
                .zoom(zoom).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }
}
