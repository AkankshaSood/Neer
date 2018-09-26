package com.google.sample.cloudvision;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    public static final int MY_PERMISSION_REQUEST_LOCATION=99;
    GoogleApiClient mgoogleApiclient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationrequest;
    private GoogleMap mMap;
    MapFragment mapFragment;
    private List<Marker> markerList;
    double longitude;
    String label="Position";
    double latitude;
    public MapActivity(){
        if(markerList==null){
            markerList=new ArrayList<Marker>();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoadPrefereneces();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            checkLocationpermission();

        //SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MarkerOptions mo=new MarkerOptions().position(new LatLng(latitude,longitude)).title(label);
//    markerList.add(mMap.addMarker(mo));



    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                builGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            builGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void builGoogleApiClient(){
        mgoogleApiclient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mgoogleApiclient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationrequest=new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){

            LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiclient,mLocationrequest,this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
        if(mCurrLocationMarker!=null){
            mCurrLocationMarker.remove();
        }

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);

        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider=locationManager.getBestProvider(new Criteria(),true);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        Location locations=locationManager.getLastKnownLocation(provider);
        List<String> providerList=locationManager.getAllProviders();
        if(null !=locations && null!=providerList &&providerList.size()>0){

            longitude=locations.getLongitude();
            latitude=locations.getLatitude();
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
            try{
                List<Address>  listaddress=geocoder.getFromLocation(latitude,longitude,1);
                if(null!=listaddress && listaddress.size()>0){

                    String state =listaddress.get(0).getAdminArea();
                    String country =listaddress.get(0).getCountryName();
                    String Sublocality=listaddress.get(0).getSubLocality();
                    markerOptions.title("" +latLng+ "," +state+" ," +country);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mCurrLocationMarker=mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


            if(mgoogleApiclient!=null){
                LocationServices.FusedLocationApi.removeLocationUpdates(mgoogleApiclient, this);

            }
        }
    }


    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    public void onProviderEnabled(String s) {

    }


    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public boolean checkLocationpermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){


                    if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){

                        if(mgoogleApiclient==null){
                            builGoogleApiClient();;
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else{
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void SavePreference(){


        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("listSize",markerList.size());

        for(int i=0;i<markerList.size();i++){

            editor.putFloat("lat"+i,(float)markerList.get(i).getPosition().latitude);
            editor.putFloat("long"+i,(float)markerList.get(i).getPosition().longitude);
            editor.putString("title"+i,markerList.get(i).getTitle());
        }
        editor.commit();
    }
    private void LoadPrefereneces(){

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        int siez=sharedPreferences.getInt("LISTsIZE",0);
        for (int i=0;i<siez;i++){

            double lat=(double)sharedPreferences.getFloat("lat"+i,0);
            double longit=(double) sharedPreferences.getFloat("long"+i,0);
            String title=sharedPreferences.getString("title"+i,"NULL");
            markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(title)));
        }
    }

}

