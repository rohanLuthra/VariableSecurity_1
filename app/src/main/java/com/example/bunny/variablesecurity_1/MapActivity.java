package com.example.bunny.variablesecurity_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.getUiSettings().setLogoEnabled(false);
                mapboxMap.getUiSettings().setAttributionEnabled(false);

                GeoJsonSource source = null;
                //                    source = new GeoJsonSource("source" , loadJsonFromAsset("temp.geojson"));
//                    mapboxMap.addSource(source);

                ArrayList<LatLng> latLngs =  new ArrayList<>();
                latLngs.add(new LatLng(12.841478, 80.156647));
                latLngs.add(new LatLng(12.792100, 80.092335));
                latLngs.add(new LatLng(14.623801, 75.621788));
                latLngs.add(new LatLng(12.798328, 80.198972));
                latLngs.add(new LatLng(12.947672, 80.137133));

                for(LatLng lng:latLngs)
                    mapboxMap.addMarker(new MarkerOptions().title("Alert").position(lng));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(12.841478, 80.156647)).zoom(9).build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000);


            }
        });
    }

    private String loadJsonFromAsset(String nameOfLocalFile) throws IOException {
        InputStream is = getAssets().open(nameOfLocalFile);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}
