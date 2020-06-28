package com.example.tappinginthemap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.tappinginthemap.model.CoronaInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1000;
    public MutableLiveData<CoronaInfo> coronaInfos = new MutableLiveData<>();
    private double mlat, mlon;
    private String mAddress, coronaPatientInfo;
    private CoronaViewModel viewModel;
    private Polyline mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main2);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.tapmap2);
//        mapView = findViewById(R.id.google_map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Toast.makeText(MainActivity2.this, "위치 갱신 됨", Toast.LENGTH_SHORT).show();
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13f));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(currentLocation);
                    markerOptions.title(mAddress);
                    markerOptions.snippet("우리집");
                    mMap.addMarker(markerOptions);
                }
            }
        };
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                List<Address> addresses;
                Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    mAddress = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String coutry = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String KnownNmae = addresses.get(0).getFeatureName();
                    mlat = latLng.latitude;
                    mlon = latLng.longitude;
                    mMap.clear();
                    MarkerOptions add = new MarkerOptions().position(latLng).title(mAddress);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(mAddress + coronaPatientInfo));

                    mPath = getmPath(latLng);
                    // Style the polyline
                    mPath.setWidth(10);
                    mPath.setColor(Color.parseColor("#FF0000"));
//                    SavedLocation savedLocationInfo = new SavedLocation(mAddress, mlat, mlon);
//                    saveLocationList.add(savedLocationInfo);
//                    EventBus.getDefault().post(new EventSavedLocationListInfoForWeather(saveLocationList));

//                    EventBus.getDefault().post(new EventIpWeather(saveLocation));
//                    EventBus.getDefault().post(new EventItem(mlat, mlon, mAddress));

                    viewModel = ViewModelProviders.of(MainActivity2.this).get(CoronaViewModel.class);
                    viewModel.fetchCorona(mlat, mlon, 5000, 7);
                    viewModel.coronaInfos.observe(MainActivity2.this, new Observer<CoronaInfo>() {
                        @Override
                        public void onChanged(@Nullable CoronaInfo coronaInfos) {
                            String today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(System.currentTimeMillis()));
                            coronaPatientInfo = "=> " + (coronaInfos.getPatient().getId());
                        }

                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        // Position the map's camera
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.439801, 127.12773), 16));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            } else {
                //퍼미션 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            //권한이 있으면
            mMap.setMyLocationEnabled(true);
        }
        //====================================================
        LatLng SEOUL = new LatLng(38.439801, 127.12773);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

    }

    private Polyline getmPath(LatLng latLng) {
        return mMap.addPolyline(new PolylineOptions()
                .add(
                        new LatLng(latLng.latitude, latLng.longitude),
                        new LatLng(38.439802, 127.12773),
                        new LatLng(38.439803, 127.12773),
                        new LatLng(38.439804, 127.12773),
                        new LatLng(38.439805, 127.12773)
                )
        );
    }

    private void changeSelectedMarker(Marker marker) {
        if (marker != null) {
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //허락됨
//                    mMap.setMyLocationEnabled(true);
                    startLocationUpdates();
                } else {
                    //거부됨
                    //다이얼로그 띄우기
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한")
                            .setMessage("설정에서 언제든지 권한을 변경 할 수 있습니다.")
                            .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("pakage", MainActivity2.this.getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                }
                            }).setNegativeButton("닫기", null)
                            .show();
                }
                ;
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            //허락됨
            LocationRequest request = new LocationRequest();
            request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            request.setInterval(1000000);
            request.setFastestInterval(500000);
            mFusedLocationClient.requestLocationUpdates(request, mLocationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
