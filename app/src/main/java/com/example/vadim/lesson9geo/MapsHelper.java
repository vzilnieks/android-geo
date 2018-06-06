package com.example.vadim.lesson9geo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsHelper  implements
        GoogleMap.OnMarkerClickListener{

    private Context context;
    private GoogleMap mMap;

    public MapsHelper(Context context, GoogleMap map) {
        this.context = context;
        this.mMap = map;

        setupMap();
    }

    private void setupMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng myPlace = new LatLng(40.73, -73.99);  // this is New York
        mMap.addMarker(new MarkerOptions().position(myPlace).title("My Favorite City"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public String getAddress( LatLng latLng ) {

        Geocoder geocoder = new Geocoder(context);
        String addressText = "";
        List<Address> addresses = null;
        Address address = null;
        try {
            addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses.get(0);
                for (int i = 0; i < addresses.size(); i++) {
                    addressText += (i == 0)?address.getAddressLine(i):("\n" + address.getAddressLine(i));
                }
            }
        } catch (IOException e ) {
            Log.v("",e.getMessage());
        }
        return addressText;
    }

    public void placeMarkerOnMap(LatLng location) {

        MarkerOptions markerOptions = new MarkerOptions().position(location);
        String titleStr = getAddress(location);  // add these two lines
        markerOptions.title(titleStr);
        putCustomMark(location);
    }

    public void centerMapOnLocation(LatLng location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }

    protected void putCustomMark(LatLng location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (this.context.getResources(), R.mipmap.ic_launcher)));
        mMap.addMarker(markerOptions);
    }

    public void drawPoliline() {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(40.73, -73.99), new LatLng(42.73, -73.99))
                .width(5)
                .color(Color.RED));
    }


}
