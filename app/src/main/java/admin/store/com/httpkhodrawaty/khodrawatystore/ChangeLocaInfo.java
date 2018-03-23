package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class ChangeLocaInfo extends FragmentActivity implements OnMapReadyCallback  , GoogleApiClient.ConnectionCallbacks ,GoogleApiClient.OnConnectionFailedListener ,com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    String id;
    String token ;
    String lat;
    String lan;
    String dis;
    GoogleApiClient mGoogleApiClient ;
    LocationRequest locationRequest ;
    MarkerOptions markerOptions ;
    Location lastLocation =null;
    EditText editText;
    String des;

    ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_loca_info);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mProgressDialog = new ProgressDialog(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        markerOptions = new MarkerOptions().position(sydney).title("المتجر").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.shop));
        mMap.addMarker(markerOptions);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
        init();
        setUpLocIndo();
        buildGooGleApiClient();
    }


    private void init()
    {

        SharedPreferences prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        id = prefs.getString("id",null);
        token = prefs.getString("token",null);

        editText = (EditText) findViewById(R.id.des);


    }




    private void  setUpLocIndo()
    {

        mProgressDialog.setTitle("استرجاع المعلومات");
        mProgressDialog.setCancelable(false);


        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);

        final MakeRequest makeRequest = new MakeRequest("/Requests/store_location", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        String status = jsonObject.getString("status");
                        if(status.equals("ok"))
                        {
                            String data = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(data);
                            JSONObject jsonObject1 =jsonArray.getJSONObject(0);
                            lastLocation = new Location("nnn");
                            lastLocation.setLatitude(Double.valueOf(jsonObject1.getString("lat")));
                            lastLocation.setLongitude(Double.valueOf(jsonObject1.getString("lng")));
                            des = jsonObject1.getString("address");
                            mMap.clear();
                            editText.setText(des);
                            markerOptions.position(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()));
                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude())) );
                            mMap.moveCamera(CameraUpdateFactory.zoomTo(14));


                        }else if (status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                            finish();
                        }else if (status.equals("error"))
                        {
                            Toast.makeText(getApplicationContext() , "تأكد من الدخلات وحاول مره اخرى  " , Toast.LENGTH_LONG).show();
                            // finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });
        mProgressDialog.dismiss();

    }



    private void request()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("lat",String.valueOf(markerOptions.getPosition().latitude));
        params.put("lng", String.valueOf(markerOptions.getPosition().longitude));
        params.put("address",des );

        MakeRequest makeRequest = new MakeRequest("/Requests/changelocation", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        String status = jsonObject.getString("status");
                        if(status.equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم تغير بيانات الموقع بنجاح بنجاح" , Toast.LENGTH_LONG).show();
                            finish();
                        }else if (status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                            finish();
                        }else if (status.equals("error"))
                        {
                            Toast.makeText(getApplicationContext() , "تأكد من الدخلات وحاول مره اخرى  " , Toast.LENGTH_LONG).show();
                           // finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void changeLocInfo(View view)
    {
        mProgressDialog.setTitle("حفظ المعلومات الجديده");
        des = editText.getText().toString();
        request();
        mProgressDialog.dismiss();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient , locationRequest ,  this);


    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location)
    {

        if(lastLocation == null)
        {
            LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
            lastLocation= location;
            markerOptions.position(latLng);
            mMap.clear();
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng) );
            mMap.moveCamera(CameraUpdateFactory.zoomTo(14));



        }
    }

    protected synchronized void buildGooGleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

}
