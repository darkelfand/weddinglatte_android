package org.redborn.weddinglatte.android.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.BusinessInfoFragment;
import org.redborn.weddinglatte.android.view.main.CommunityFragment;
import org.redborn.weddinglatte.android.view.mypage.ProfileFragment;
import org.redborn.weddinglatte.android.view.mypage.ScrapFragment;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MaterialDialog mMaterialDialog = new MaterialDialog(this);

    public Boolean tabChange(){
        Boolean tabType = false;
        Intent intent = getIntent();
        String gubun = intent.getExtras().getString("gubun");
        if("myPage".equals(gubun)){
            tabType = true;
        }
        return tabType;
    }

    TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!tabChange()) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    ArrayList<String> mSelectedItems;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //상단 툴메 메뉴 클릭시(지도)
        int id = item.getItemId();

        if (id == R.id.business_type) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final String data []   = { "전체", "웨딩홀", "스튜디오", "드레스", "메이크업" };
            final boolean checked[]= {false, false, false, false, false};

            builder.setTitle(R.string.business_type)
                   .setPositiveButton("선택완료",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String str = "";
                                    for (int i = 0; i < checked.length; i++) {
                                        if (checked[i]) {
                                            str = str + data[i] +", ";
                                        }
                                    }
                                    String checkedData [] = str.split(",");
                                    Intent intent = new Intent(MainActivity.this, SearchMapActivity.class);
                                    intent.putExtra("businessType", checkedData);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("취소", null)
                    .setMultiChoiceItems
                            (data, // 체크박스 리스트 항목
                                    checked, // 초기값(선택여부) 배열
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which, boolean isChecked) {
                                            checked[which] = isChecked;
                                        }
                                    }
                            );
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //커뮤니티
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("gubun", "communityBoard");
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            //업체정보
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("gubun", "BuinessBoard");
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            //마이페이지
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("gubun", "myPage");
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            mMaterialDialog.setTitle(R.string.business_type)
                    .setTitle("로그아웃 하시겠습니까?")
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                        }
                    })
                    .setNegativeButton("CANCEL", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });

            mMaterialDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment firstFragment =  CommunityFragment.newInstance(position);
            Fragment secondFragment = BusinessInfoFragment.newInstance(position);
            if(tabChange()) {
                firstFragment = ProfileFragment.newInstance(position);
                secondFragment = ScrapFragment.newInstance(position);
            }
            switch (position) {
                // Open FragmentTab1.java
                case 0:
                    return firstFragment;
                case 1:
                    return secondFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabNameOne =  "커뮤니티";
            String tabNameTwo =  "업체정보";

            if(tabChange()){
                tabNameOne =  "프로필";
                tabNameTwo =  "스크랩";
            }
            switch (position) {

                case 0:
                    return tabNameOne;
                case 1:
                    return tabNameTwo;
            }
            return null;
        }
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        //탭메뉴 구현부
        private FragmentManager mFm;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    /*mFm = getFragmentManager();
                    mFm.beginTransaction()
                            .replace(R.id.container, new CommunityFragment())
                            .commit();*/
                    break;
                case 2:
                   /* mFm = getFragmentManager();
                    mFm.beginTransaction()
                            .replace(R.id.container, new BusinessInfoFragment())
                            .commit();*/
                    break;
            }
            return rootView;
        }

    }
}
