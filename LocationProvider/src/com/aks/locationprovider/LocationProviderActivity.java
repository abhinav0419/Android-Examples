package com.aks.locationprovider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LocationProviderActivity extends Activity implements OnClickListener, LocationListener {

	Button btnGPS, btnNetwork, btnStop;
	
	LocationManager locationManager;
	
	Geocoder geo_coder;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        geo_coder = new Geocoder(this, Locale.getDefault());
        
        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnNetwork = (Button) findViewById(R.id.btnNetwork);
        btnStop = (Button) findViewById(R.id.btnStop);
        
        btnGPS.setOnClickListener(this);
        btnNetwork.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {

		if(view == btnGPS) {
			
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 2, this);
			
		}
		
		if(view == btnNetwork) {
			
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 2, this);
			
		}
		
		if(view == btnStop) {
			
			locationManager.removeUpdates(this);
		}
		
	}

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		location.distanceTo(dest)
		
		Toast.makeText(LocationProviderActivity.this, "Latitude = "+latitude + "longitude = " + longitude, Toast.LENGTH_SHORT).show();
		
		try {
			
			Log.i("Text", "%%%%%%%%%%%%%%%%%%% 1 %%%%%%%%%%%%%%%");
			List<Address> list = geo_coder.getFromLocation(latitude, longitude, 1);
			
			if(list != null && list.size() > 0) {
			
				Log.i("Text", "%%%%%%%%%%%%%%%%%%% 2 %%%%%%%%%%%%%%%");
				
				Address address = list.get(0);
				
				Log.i("Text", "%%%%%%%%%%%%%%%%%%% 3 %%%%%%%%%%%%%%%");
				
				String result = address.getLocality();
				
				Toast.makeText(this, "Address: " + result, Toast.LENGTH_LONG).show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}