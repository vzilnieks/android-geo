package com.example.vadim.lesson9geo;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationHelper {

    public static void requestLocationUpdate(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) context);
    }
}
