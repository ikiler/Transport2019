package com.example.ikiler.transport2019.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.example.ikiler.transport2019.R;

public class MapActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapsInitializer.sdcardDir = "/sdcard/aa/";

        map = (MapView) findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        AMap aMap = map.getMap();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext(),
                        com.amap.api.maps.offlinemap.OfflineMapActivity.class));
            }
        });
    }

}
