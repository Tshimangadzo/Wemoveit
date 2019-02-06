package com.example.a800361.shifterapp.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.a800361.shifterapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    View myView;

    public CustomInfoWindow(Context context){
        myView = LayoutInflater.from(context).inflate(R.layout.custom_rider_info_window,null);


    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView  txtPickUpTitle = ((TextView)myView.findViewById(R.id.txtPickupInfo));
        txtPickUpTitle.setText(marker.getSnippet());

        TextView  txtPickUpSnippet = ((TextView)myView.findViewById(R.id.txtPickupSnippet));
        txtPickUpSnippet.setText(marker.getSnippet());


        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
