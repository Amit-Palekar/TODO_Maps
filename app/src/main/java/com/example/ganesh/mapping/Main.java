package com.example.ganesh.mapping;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class Main extends AppCompatActivity {

    private Spinner spinner;
    private TextView introduction;
    private EditText destination_location;
    private String destinationLocationString;
    private String distanceEnteredString;
    private Button submitButton;
    private EditText distanceEntered;
    private String transportMode;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLocationService();
        requestLocationService();


        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);

//        spinner.setOnItemSelectedListener(
//                new Spinner.OnItemSelectedListener(){
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        switch (view.toString()){
//                            case "Car":
//                                transportMode = "driving";
//                                System.out.println("one");
//                                break;
//                            case "Bike":
//                                transportMode = "bicycling";
//                                System.out.println("two");
//                                break;
//                            case "Walk":
//                                transportMode = "walking";
//                                System.out.println("three");
//                                break;
//                            default:
//                                transportMode = null;
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {}
//                });
        final Bundle bundle = new Bundle();

        bundle.putString("userLat", userLocation.getLatitude() + "");
        bundle.putString("userLong", userLocation.getLongitude() + "");

        introduction = (TextView)findViewById(R.id.introduction);
        //Typeface font = new Typeface(getAssets(), "@res/");
        //introduction.setTypeface(font);

        destination_location = (EditText)findViewById(R.id.destination);


        distanceEntered = (EditText)findViewById(R.id.distance);


        submitButton = (Button)findViewById(R.id.button);
        submitButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        destinationLocationString = destination_location.getText().toString();
                        distanceEnteredString = distanceEntered.getText().toString();
                        if(destinationLocationString != null ||
                                distanceEnteredString != null) {

//                            if(destinationLocationString == null) {
//                                System.out.println("Hi");
//                                Intent i = getIntent();
//                                bundle.putString("Transport", distanceEnteredString);
//                                i.putExtras(bundle);
//                                startActivity(i);
/////                            } else if(distanceEnteredString == null) {
                                System.out.println("Hello");
                                Intent i = getIntent();
                                String text = spinner.getSelectedItem().toString();
                                System.out.println(text);
                                bundle.putString("Distance", distanceEnteredString);
                                bundle.putString("Transport", text);
                                i.putExtras(bundle);
                                startActivity(i);
//                            } else {
//                                new AlertDialog.Builder(getApplicationContext())
//                                        .setTitle("WARNING!")
//                                        .setMessage("You cannot type in both text fields!!")
//                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {}
//                                        })
//                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
//
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {}
//                                        })
//                                        .setIcon(android.R.drawable.ic_dialog_alert)
//                                        .show();

//                            }
                        }
                    }
                }
        );
    }

    public Intent getIntent() {
        return new Intent(this, Maps.class);
    }

    public void startLocationService() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    public void requestLocationService() {
        // String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Or, use GPS location data:
        String locationProvider = LocationManager.GPS_PROVIDER;
        userLocation = locationManager.getLastKnownLocation(locationProvider);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
        userLocation = locationManager.getLastKnownLocation(locationProvider);
    }
}


/*
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Location userLocation;
    protected TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = (TextView)(findViewById(R.id.locationText));
        startLocationService();
        requestLocationService();
    }

    public void sendNext(View view) {
        Intent intent = new Intent(this, Maps.class);
        Bundle bundleIt = new Bundle();
        bundleIt.putString("userLat", userLocation.getLatitude() + "");
        bundleIt.putString("userLong", userLocation.getLongitude() + "");
        intent.putExtras(bundleIt);
        startActivity(intent);
    }

    public void startLocationService() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    public void requestLocationService() {
        // String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Or, use GPS location data:
        String locationProvider = LocationManager.GPS_PROVIDER;
        userLocation = locationManager.getLastKnownLocation(locationProvider);
        locationText.setText(userLocation.getLatitude() + " " + userLocation.getLongitude());
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
    }
}
*/