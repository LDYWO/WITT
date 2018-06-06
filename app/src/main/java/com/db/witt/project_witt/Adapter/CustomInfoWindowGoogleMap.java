package com.db.witt.project_witt.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.db.witt.project_witt.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);

        TextView open_time_tv = view.findViewById(R.id.open_time);
        TextView rating_tv = view.findViewById(R.id.rating_text);

        name_tv.setText(marker.getTitle());
        details_tv.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        if(infoWindowData.getOpen_time()!="none"||infoWindowData.getRating()!="none"){
            open_time_tv.setText(infoWindowData.getOpen_time());
            rating_tv.setText(infoWindowData.getRating());
        }
        else{
            details_tv.setHeight(0);
            open_time_tv.setHeight(0);
            rating_tv.setHeight(0);
        }


        return view;
    }
}
