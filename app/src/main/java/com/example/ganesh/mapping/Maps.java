package com.example.ganesh.mapping;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    protected double userLat, userLong, userDistance;
    protected MarkerOptions markers;
    protected LatLng[] latlngarray = new LatLng[3];
    protected String transport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle get = getIntent().getExtras();
        userLat = Double.parseDouble(get.getString("userLat"));
        userLong = Double.parseDouble(get.getString("userLong"));
        userDistance = Double.parseDouble(get.getString("Distance"));
        transport = get.getString("Transport");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_back) {
//            // Mark the currently visible content (e.g. article) in the activity as a favorite
//            // - requires app specific code which is omitted here.
//            Intent intent = new Intent(this, Main.class);
//            startActivity(intent);
//            // Tell Android that the item click has been handled.
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng user = new LatLng(userLat, userLong);
        latlngarray[2] = user;
        mMap.addMarker(new MarkerOptions().position(user).title("Start/Finish! ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
        processWaypoints();
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
    }

    public void processWaypoints() {
        double[] onelatlngdeg = makeWaypointOne();
        LatLng one = new LatLng(onelatlngdeg[0], onelatlngdeg[1]);
        latlngarray[0] = one;
        mMap.addMarker(new MarkerOptions().position(one).title("One-Third Done!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        double[] twolatlngdeg = makeWaypointTwo(onelatlngdeg);
        LatLng two = new LatLng(twolatlngdeg[0], twolatlngdeg[1]);
        mMap.addMarker(new MarkerOptions().position(two).title("Final Strech!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        latlngarray[1] = two;
        PathGoogleMapActivity paths = new PathGoogleMapActivity();
        paths.setMap(mMap);
        String url = paths.getMapsApiDirectionsUrl(latlngarray, transport);
        PathGoogleMapActivity.ReadTask downloadTask = paths.makeTask();
        downloadTask.execute(url);
    }

    public double[] makeWaypointOne() {
        double[] latlngdeg = new double[3];
        double degrees = 2 * (Math.random() * Math.PI);
        latlngdeg[2] = degrees;
        // lat = 110.574 km (68.70749821 mi)
        // long = 111.320 * cos(lat) km (69.1710411 mi)
        double oneLat = 1 / 68.707;
        double oneLng = 1 / ((69.171 * (Math.cos(userLat * Math.PI / 180))));
        double sine = Math.cos(degrees);
        double cosine = Math.sin(degrees);
        double newLat = userLat + oneLat * userDistance * 1 / 3 * cosine;
        double newLng = userLong + oneLng * userDistance * 1 / 3 * sine;

        latlngdeg[0] = newLat;
        latlngdeg[1] = newLng;

        return latlngdeg;
    }

    public double[] makeWaypointTwo(double[] array) {
        double[] latlngdeg = new double[3];

        double degrees = array[2] + Math.PI / 3.0;
        latlngdeg[2] = degrees;
        double oneLat = 1 / 68.707;
        double oneLng = 1 / ((69.171 * (Math.cos(userLat * Math.PI / 180))));
        double sine = Math.cos(degrees);
        double cosine = Math.sin(degrees);
        double newLat = userLat + oneLat * userDistance * 1 / 3 * cosine;
        double newLng = userLong + oneLng * userDistance * 1 / 3 * sine;

        latlngdeg[0] = newLat;
        latlngdeg[1] = newLng;

        return latlngdeg;
    }
}
