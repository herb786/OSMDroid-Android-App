package com.hacaller.androidplayground;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityFloor4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor4);


        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint home = new GeoPoint(-12.0716, -77.0803);
        GeoPoint vermont = new GeoPoint(44.4781, -73.2141);

        Button btnHome = (Button) findViewById(R.id.btn_home);
        Button btnVermont = (Button) findViewById(R.id.btn_boston);
        btnHome.setOnClickListener(new LaunchOnClickListener(map, home));
        btnVermont.setOnClickListener(new LaunchOnClickListener(map, vermont));

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextOnClickListener());


    }

    private class LaunchOnClickListener implements View.OnClickListener {

        MapView mapView;
        GeoPoint geoPoint;

        public LaunchOnClickListener(MapView mapView, GeoPoint geoPoint){
            this.mapView = mapView;
            this.geoPoint = geoPoint;
        }

        @Override
        public void onClick(View v) {
            IMapController mapController = mapView.getController();
            mapController.setZoom(18);
            mapController.setCenter(geoPoint);
        }
    }


    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor5.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
