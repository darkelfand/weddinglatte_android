package org.redborn.weddinglatte.android.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.BusinessInfoDetailFragment;
import org.redborn.weddinglatte.android.view.main.CommentFragment;
import org.redborn.weddinglatte.android.view.main.SearchMapFragment;
import org.redborn.weddinglatte.android.vo.PlaceVO;

import java.util.ArrayList;

import static org.redborn.weddinglatte.R.id.container;

public class SearchMapActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation; //현재위치를 담고 있는 객체

    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private MarkerOptions currentOpt;
    private AQuery mAq;
    private final String PLACES_API = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyAjrXnxmyM1go9fYrxO2CgDxvAQx7_vD8A&location=%f,%f&radius=15000&name=%s";
    private ArrayList<PlaceVO> mPlaceList = new ArrayList<PlaceVO>();
    double latitude;
    double longitude;
    EditText etSearch;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            AlertDialog.Builder builder = new AlertDialog.Builder(SearchMapActivity.this);

            /*Spinner spinner = (Spinner)findViewById(R.id.spinner);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.area_select, android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapter);
            */
            builder.setTitle(R.string.area_type)
                    .setPositiveButton("선택완료",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                    .setNegativeButton("취소", null);
        builder.setView(R.layout.area_select_dialog);
            builder.show();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        findViewById(R.id.slide).setVisibility(View.GONE);

        mPlaceList.clear();
        displayMap();

        mAq = new AQuery(this);
        //구글 api 엑세스

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplication())
                    .addConnectionCallbacks(mConnectionCallbacks)
                    .addOnConnectionFailedListener(mOnConnectionFailedListener)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        mLocationRequest = new LocationRequest();
        //위치를 가져오는 주기 설정
        mLocationRequest.setInterval(1000 * 60 * 1); //1 minutes
        mLocationRequest.setFastestInterval(1000 * 60 * 10);
        //4가지 배터리 소모와 위치 정확성 설정
        //위치 정확도를 높이면 배터리 소모가 많아지고 정확도를 낮추면 배터리 소모가 낮아짐
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Intent intent = getIntent();
        String businessType[] = intent.getExtras().getStringArray("businessType");
        latitude = 37.56;
        longitude = 126.97;
        //37.56, 126.97
        //mLastLocation.getLatitude(), mLastLocation.getLongitude()

        for(int i = 0 ;  i < businessType.length ; i++ ){
            Log.d("businessType", "---------------------------------");
            Log.d("businessType", "businessType :"+businessType.length);
            Log.d("businessType", "businessType["+i+"]:"+businessType[i]);
            Log.d("businessType", "authUtil 위도:"+latitude);
            Log.d("businessType", "authUtil 경도:"+longitude);
            Log.d("businessType", "---------------------------------");
            if(businessType[i].length() > 0) {
                makeMapBusinessInfo(String.format(PLACES_API, latitude, longitude, businessType[i]));
            }
        }
    }
    private void makeMapBusinessInfo(String url) {
        mPlaceList.clear();
        displayMap();

        Log.d("LDK", url);
        mAq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    Log.d("LDK", object.toString(1));
                    //Log.d("LDK", object.toString(1));
                    //결과를 파싱해서 지도에 뿌려주기 & 리스트 뷰 만들기
                    JSONArray array = object.getJSONArray("results");
                    for (int i = 0; i < array.length(); ++i) {
                        JSONObject json = array.getJSONObject(i);
                        String name = json.getString("name");
                        //String address = json.getString("formatted_address");
                        String placeId = json.getString("id");
                        //String weekdayText = json.getString("weekday_text");
                        //String photos = json.getString("photos");
                        String vicinity = json.getString("vicinity");
                        double lat = json.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double lng = json.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        Log.d("businessType", "-----------------result------------------");
                        Log.d("businessType", "name :"+name);
                        //Log.d("businessType", "address:"+address);
                        Log.d("businessType", "placeId:"+placeId);
                        //Log.d("businessType", "weekdayText:"+weekdayText);
                        //Log.d("businessType", "photos:"+photos);
                        Log.d("businessType", "-----------------------------------------");
                        //객체에 담기
                        PlaceVO place = new PlaceVO();
                        place.name = name;
                        place.vicinity = vicinity;
                        place.lat = lat;
                        place.lng = lng;
                        mPlaceList.add(place);
                    }

                    //지도에 검색결과를 아이콘으로 오버레이
                    overlayMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void overlayMap() {
        //파싱된 결과를 맵에 표시하기
        for (PlaceVO place : mPlaceList) {
            MarkerOptions opt = new MarkerOptions();
            opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_on_1_light));
            opt.position(new LatLng(place.lat, place.lng));// 위도 • 경도
            opt.title(place.name); // 제목 미리보기
            opt.snippet(place.vicinity);
            mGoogleMap.addMarker(opt);
            // 마커클릭 이벤트 처리
            // GoogleMap 에 마커클릭 이벤트 설정 가능.
            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // 마커 클릭시 호출되는 콜백 메서드
                    Toast.makeText(getApplicationContext(),
                            marker.getTitle() + " 클릭했음"
                            , Toast.LENGTH_SHORT).show();
                    TextView textView = (TextView)findViewById(R.id.handle);
                    textView.setText(marker.getTitle());
                    findViewById(R.id.slide).setVisibility(View.VISIBLE);
                    /*<Button
                    android:id="@+id/handle"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:background="@color/colorAccent"
                    android:text="요약정보" />*/
                    findViewById(R.id.handle).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),
                                    "바로가기 클릭했음"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                }
            });

        }
    }

    private void displayMap() {
        //authUtil.checkPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mGoogleMap = mapFragment.getMap();
        mGoogleMap =  ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        //현재위치로 지도를 이동하고 zoom을 16레벨로 설정
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));

        mGoogleMap.clear();

        //현재 위치 아이콘 표시
        currentOpt = new MarkerOptions();
        currentOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        //currentOpt.position(new LatLng(latitude, longitude));
        currentOpt.position(new LatLng(latitude, longitude));
        currentOpt.title("현재위치"); // 제목 미리보기
        currentOpt.snippet(""); //위도 경도를 지오코딩으로 주소로 가져와서 뿌려준다.

        mGoogleMap.addMarker(currentOpt);
    }

    GoogleApiClient.ConnectionCallbacks mConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            Log.d("LDK", "connected");
            //가장 최근 위치 가져오기

            if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Log.d("LDK", mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
                displayMap();
            }
            //실시간 위치추적
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, mLocationListener);
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.d("LDK", "connectionSuspended");
        }
    };

    GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.d("LDK", "connect fail");
        }
    };

    //위치를 업데이트하는 리스너
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            displayMap();
        }
    };
}