package com.example.ikiler.transport2019;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ikiler.transport2019.UI.EX_11;
import com.example.ikiler.transport2019.UI.EX_21;
import com.example.ikiler.transport2019.UI.EX_22;
import com.example.ikiler.transport2019.UI.EX_3;
import com.example.ikiler.transport2019.UI.EX_5;
import com.example.ikiler.transport2019.UI.EX_7;
import com.example.ikiler.transport2019.UI.EX_9;
import com.example.ikiler.transport2019.UI.LogSearchActivity;
import com.example.ikiler.transport2019.UI.RoadStateAnalyseActivity;
import com.example.ikiler.transport2019.UI.ViolationActivity;
import com.example.ikiler.transport2019.UI.VlanalyseActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button a1;
    private Button a2;
    private Button a3;
    private Button a4;
    private Button a5;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, EX_1.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, EX_2.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, EX_3.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, EX_5.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, EX_7.class));
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, EX_9.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        a4 = (Button) findViewById(R.id.a4);
        a5 = (Button) findViewById(R.id.a5);

        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        a4.setOnClickListener(this);
        a5.setOnClickListener(this);
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a1:
                startActivity(new Intent(MainActivity.this, RoadStateAnalyseActivity.class));
                break;
            case R.id.a2:
                startActivity(new Intent(MainActivity.this, EX_11.class));
                break;
            case R.id.a3:
                startActivity(new Intent(MainActivity.this, EX_21.class));
                break;
            case R.id.a4:
                startActivity(new Intent(MainActivity.this, EX_22.class));
                break;
            case R.id.a5:

                break;
            case R.id.b1:
                startActivity(new Intent(MainActivity.this, ViolationActivity.class));
                break;
            case R.id.b2:
                startActivity(new Intent(MainActivity.this, LogSearchActivity.class));
                break;
            case R.id.b3:
                startActivity(new Intent(MainActivity.this, VlanalyseActivity.class));
                break;
            case R.id.b4:
                break;
        }
    }
}
