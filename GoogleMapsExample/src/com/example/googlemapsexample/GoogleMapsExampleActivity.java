package com.example.googlemapsexample;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;

public class GoogleMapsExampleActivity extends FragmentActivity {
	
	GoogleMap g_map;
	
    private LocationManager locationManager;

    boolean gps_enabled = false;
    boolean network_enabled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_example);
        
        g_map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        g_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 50,
                    locationListenerGps);
        if (!gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 50,
                    locationListenerNetwork);
    }
    
    LocationListener locationListenerGps = new LocationListener() {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            g_map.animateCamera(cameraUpdate);
            //locationManager.removeUpdates(this);
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    LocationListener locationListenerNetwork = new LocationListener() {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            g_map.animateCamera(cameraUpdate);
            
            
            g_map.addMarker(new MarkerOptions()
            .position(new LatLng(location.getLatitude(), location.getLongitude()))
            .title("Current Location"));
            
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_google_maps_example, menu);
        return true;
    }

    
}
