package com.example.ikiler.transport2019.UI;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.SQLitLifeNum;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EX_5 extends AppCompatActivity {
    private LinearLayout wendu_layout;
    private LinearLayout shidu_layout;
    private LinearLayout sun_layout;
    private LinearLayout co_layout;
    private LinearLayout pm_layout;
    private LinearLayout load_layout;
    private TextView wendu;
    private TextView shidu;
    private TextView sun;
    private TextView co;
    private TextView pm;
    private TextView load;
    List<String> urls = new ArrayList<String>();
    List<String> jsons = new ArrayList<String>();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_5);
        wendu_layout = findViewById(R.id.wendu_layout);
        shidu_layout = findViewById(R.id.shidu_layout);
        sun_layout = findViewById(R.id.sun_layout);
        co_layout = findViewById(R.id.co_layout);
        pm_layout = findViewById(R.id.pm_layout);
        load_layout = findViewById(R.id.load_layout);
        wendu = findViewById(R.id.wendu);
        shidu = findViewById(R.id.shidu);
        sun = findViewById(R.id.sun);
        co = findViewById(R.id.co);
        pm = findViewById(R.id.pm);
        load = findViewById(R.id.load);
        String url1 = "http://192.168.139.4:8890/type/jason/action/GetAllSense";
        String url2 = "http://192.168.139.4:8890/type/jason/action/GetRoadStatus";
        urls.add(url1);
        urls.add(url2);
        String json1 = "{}";
        String json2 = "{\"RoadId\":1}";
        jsons.add(json1);
        jsons.add(json2);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                NetConnection.post(urls, jsons, new NetConnection.CallBack() {
                    @Override
                    public void success(List<String> results) {
                        try {

                                String o1 = results.get(0);
                                JSONObject jsonObject = new JSONObject(o1);
                                Log.i("info_o1", o1);
                                String wendu1 = jsonObject.getString("temperature");
                                String shidu1 = jsonObject.getString("humidity");
                                String sun1 = jsonObject.getString("LightIntensity");
                                String co1 = jsonObject.getString("co2");
                                String pm1 = jsonObject.getString("pm2.5");
                                wendu.setText(wendu1);
                                shidu.setText(shidu1);
                                sun.setText(sun1);
                                co.setText(co1);
                                pm.setText(pm1);
                                String o2 = results.get(1);
                                JSONObject jsonObject1 = new JSONObject(o2);
                                Log.i("info_o2", o2);
                                String load1 = jsonObject1.getString("Status");
                                load.setText(load1);


                                if (Integer.parseInt(wendu1) > 35) {

                                    wendu_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    wendu_layout.setBackgroundColor(0xff8bc34a);
                                }

                                if (Integer.parseInt(shidu1) > 1000) {

                                    shidu_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    shidu_layout.setBackgroundColor(0xff8bc34a);
                                }

                                if (Integer.parseInt(sun1) > 1000) {

                                    sun_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    sun_layout.setBackgroundColor(0xff8bc34a);
                                }

                                if (Integer.parseInt(co1) > 1000) {

                                    co_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    co_layout.setBackgroundColor(0xff8bc34a);
                                }

                                if (Integer.parseInt(pm1) > 1000) {

                                    pm_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    pm_layout.setBackgroundColor(0xff8bc34a);
                                }

                                if (Integer.parseInt(load1) > 1000) {

                                    load_layout.setBackgroundColor(0xFFFF0000);

                                } else {
                                    load_layout.setBackgroundColor(0xff8bc34a);
                                }

                                SQLitLifeNum sqLitLifeNum = new SQLitLifeNum(EX_5.this);
                                SQLiteDatabase db = sqLitLifeNum.getWritableDatabase();
                                String sql = "insert into lifeNum values(null,'" + wendu1 + "','" + shidu1 + "','" + sun1 + "','" + co1 + "','" + pm1 + "','" + load1 + "')";
                                db.execSQL(sql);
                                Toast.makeText(EX_5.this, "存储成功", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 3 * 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
