package org.redborn.weddinglatte.android.view.main;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.redborn.weddinglatte.R;
import org.redborn.weddinglatte.android.view.main.item.CommunityItem;

import java.util.ArrayList;


public class SearchMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_POSITION = "position";

    MapView mMapView;
    private GoogleMap googleMap;

    static final LatLng SEOUL = new LatLng( 37.56, 126.97);

    private LocationManager locationManager = null;
    private Double longitude;
    private Double latitude;


    public SearchMapFragment() {
        // Required empty public constructor
    }
    public static SearchMapFragment newInstance(int position) {
        SearchMapFragment f = new SearchMapFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    ArrayList<CommunityItem> arDessert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_map,container, false) ;
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(this);
        //MapsInitializer.initialize(getActivity().getApplicationContext());
        MapsInitializer.initialize(getActivity().getApplicationContext());

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap map) {

        LatLng myLocation = new LatLng(latitude, longitude);
        Log.d("mr", "la: "+latitude);
        Log.d("mr", "lo: "+longitude);
        //map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13));
        //마커로 현재 위치 표시
        map.addMarker(new MarkerOptions()
                .title("현재 위치")
                .snippet("innoaus.")
                .position(myLocation));
    }
}
