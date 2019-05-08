package com.example.ikiler.transport2019.UI;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikiler.transport2019.EX_7_item;
import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EX_7 extends AppCompatActivity {
    private EditText wendu;
    private EditText shidu;
    private EditText sun;
    private EditText co;
    private EditText pm;
    private EditText load;
    private TextView sw_s;
    private Switch sw;
    private Button save;
    SharedPreferences sharedPreferences;
    List<String> urls = new ArrayList<String>();
    List<String> jsons = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_7);
        wendu = findViewById(R.id.wendu);
        shidu = findViewById(R.id.shidu);
        sun = findViewById(R.id.sun);
        co = findViewById(R.id.co);
        pm = findViewById(R.id.pm);
        load = findViewById(R.id.load);
        sw_s = findViewById(R.id.sw_s);
        sw = findViewById(R.id.sw);
        save = findViewById(R.id.save);
        sharedPreferences = EX_7.this.getSharedPreferences("info",getApplication().MODE_PRIVATE);

        wendu.setHint(sharedPreferences.getString("wendu","default"));
        shidu.setHint(sharedPreferences.getString("shidu","default"));
        sun.setHint(sharedPreferences.getString("sun","default"));
        co.setHint(sharedPreferences.getString("co","default"));
        pm.setHint(sharedPreferences.getString("pm","default"));
        load.setHint(sharedPreferences.getString("load","default"));

        sw.setChecked(true);
        sw_s.setText("开");
        wendu.setEnabled(false);
        shidu.setEnabled(false);
        sun.setEnabled(false);
        co.setEnabled(false);
        pm.setEnabled(false);
        load.setEnabled(false);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sw_s.setText("开");
                    wendu.setEnabled(false);
                    shidu.setEnabled(false);
                    sun.setEnabled(false);
                    co.setEnabled(false);
                    pm.setEnabled(false);
                    load.setEnabled(false);

                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            String url1 = "http://192.168.139.4:8890/type/jason/action/GetAllSense";
                            String url2 = "http://192.168.139.4:8890/type/jason/action/GetRoadStatus";
                            urls.add(url1);
                            urls.add(url2);
                            String json1 = "{}";
                            String json2 = "{\"RoadId\":1}";
                            jsons.add(json1);
                            jsons.add(json2);
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

                                        String o2 = results.get(1);
                                        JSONObject jsonObject1 = new JSONObject(o2);
                                        Log.i("info_o2", o2);
                                        String load1 = jsonObject1.getString("Status");


                                        if (wendu1.compareTo(sharedPreferences.getString("wendu","0"))>0){

                                            EX_7_item.notify(EX_7.this,"温度报警",1);

                                        }
                                        if (shidu1.compareTo(sharedPreferences.getString("shidu","0"))>0){

                                            EX_7_item.notify(EX_7.this,"湿度报警",2);

                                        }
                                        if (sun1.compareTo(sharedPreferences.getString("sun","0"))>0){

                                            EX_7_item.notify(EX_7.this,"光照报警",3);

                                        }
                                        if (co1.compareTo(sharedPreferences.getString("co","0"))>0){

                                            EX_7_item.notify(EX_7.this,"co报警",4);

                                        }
                                        if (pm1.compareTo(sharedPreferences.getString("pm","0"))>0){

                                            EX_7_item.notify(EX_7.this,"pm2.5报警",5);

                                        }
                                        if (load1.compareTo(sharedPreferences.getString("load","0"))>0){

                                            EX_7_item.notify(EX_7.this,"道路状态报警",6);

                                        }



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    };

                    timer.schedule(task,0,10*1000);



                }else if (!b){
                    sw_s.setText("关");
                    wendu.setEnabled(true);
                    shidu.setEnabled(true);
                    sun.setEnabled(true);
                    co.setEnabled(true);
                    pm.setEnabled(true);
                    load.setEnabled(true);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sw.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (wendu.getText()!=null){
                        editor.putString("wendu",wendu.getText().toString());
                    }
                    if (shidu.getText()!=null){
                        editor.putString("shidu",shidu.getText().toString());
                    }
                    if (sun.getText()!=null){
                        editor.putString("sun",sun.getText().toString());
                    }
                    if (co.getText()!=null){
                        editor.putString("co",co.getText().toString());
                    }
                    if (pm.getText()!=null){
                        editor.putString("pm",pm.getText().toString());
                    }
                    if (load.getText()!=null){
                        editor.putString("load",load.getText().toString());
                    }
                    editor.commit();
                    Toast.makeText(EX_7.this, "save success", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
