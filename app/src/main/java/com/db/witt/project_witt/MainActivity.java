package com.db.witt.project_witt;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_TOILET_ADD = "toilet_address2";
    private static final String TAG_TOILET_NAME = "toilet_name";
    private static final String TAG_OPEN_TIME = "open_time";
    private static final String TAG_RATING = "rating";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";

    JSONArray toilet_json_arr = null;

    ArrayList<HashMap<String,String>> toilet_info_list = new ArrayList<HashMap<String, String>>();

    Toilet_info_Adapter toilet_info_adapter;
    RecyclerView recyclerView;

    //GoogleMap 객체
    GoogleMap googleMap;
    MapFragment mapFragment;
    LocationManager locationManager;
    LinearLayout boxMap;
    //나의 위도 경도 고도
    double mLatitude;  //위도
    double mLongitude; //경도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boxMap = (LinearLayout)findViewById(R.id.boxMap);
        //LocationManager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //GPS가 켜져있는지 체크
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }

        //마시멜로 이상이면 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //권한이 없는 경우
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            }
            //권한이 있는 경우
            else{
                requestMyLocation();
            }
        }
        //마시멜로 아래
        else{
            requestMyLocation();
        }

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    toilet_json_arr = jsonObject.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < toilet_json_arr.length(); i++) {
                        JSONObject c = toilet_json_arr.getJSONObject(i);
                        String address = c.getString(TAG_TOILET_ADD);
                        String toilet_name = c.getString(TAG_TOILET_NAME);
                        String open_time = c.getString(TAG_OPEN_TIME);
                        String rating = c.getString(TAG_RATING);
                        String latitude = c.getString(TAG_LATITUDE);
                        String longitude = c.getString(TAG_LONGITUDE);

                        HashMap<String, String> toilets = new HashMap<String, String>();

                        toilets.put(TAG_TOILET_ADD, address);
                        toilets.put(TAG_TOILET_NAME, toilet_name);
                        toilets.put(TAG_OPEN_TIME, open_time);
                        toilets.put(TAG_RATING, rating);

                        toilet_info_list.add(toilets);

                        makeMaker(latitude,longitude);

                    }

                    toilet_info_adapter = new Toilet_info_Adapter(getApplicationContext(),toilet_info_list);
                    recyclerView.setAdapter(toilet_info_adapter);
                    toilet_info_adapter.notifyDataSetChanged();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        MainRequest mainRequest = new MainRequest("0", responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(mainRequest);
    }

    Menu menu_main;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu_main=menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MypageActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //권한 요청후 응답 콜백
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한
        if(requestCode==1){
            //권한받음
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                requestMyLocation();
            }
            //권한못받음
            else{
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //나의 위치 요청
    public void requestMyLocation(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, (android.location.LocationListener) locationListener);
    }

    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            //나의 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도

            FragmentManager fragmentManager = getFragmentManager();
            MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
            mapFragment.getMapAsync(MainActivity.this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("gps", "onStatusChanged");
            Log.e("longitude::", String.valueOf(mLongitude));
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        //지도타입 - 일반
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //나의 위치 설정
        LatLng position = new LatLng(mLatitude , mLongitude);
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                .position(new LatLng(Double.valueOf(mLatitude), Double.valueOf(mLongitude)))
                .title("마커"); // 타이틀.
        this.googleMap.addMarker(makerOptions);

        //화면중앙의 위치와 카메라 줌비율
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        //지도 보여주기
        boxMap.setVisibility(View.VISIBLE);
    }

    public void makeMaker(String latitude, String longitude){
        LatLng posision = new LatLng(Double.valueOf(latitude ), Double.valueOf(longitude));
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                .position(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                .icon(getMarkerIcon("#ffe100"))
                .title("마커"); // 타이틀.
        this.googleMap.addMarker(makerOptions);
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}