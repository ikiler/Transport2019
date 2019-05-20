package com.example.ikiler.transport2019.UI;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ikiler.transport2019.Adapter.LogSearchItemAdapter;
import com.example.ikiler.transport2019.DB.SensorDB;
import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.UI.fragement.MyLogItemRecyclerViewAdapter;
import com.example.ikiler.transport2019.View.MyNetd;
import com.example.ikiler.transport2019.bean.LogSearchBean;
import com.example.ikiler.transport2019.bean.Sense;
import com.example.ikiler.transport2019.http.API;
import com.example.ikiler.transport2019.http.NetConnection;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogSearchActivity extends AppCompatActivity {

    private RecyclerView list;
    private List<Sense> objects = new ArrayList();
    private MyNetd myNetd;
    private MyLogItemRecyclerViewAdapter adapter;
    private List<String> urls = new ArrayList<>();
    private List<String> jsons = new ArrayList<>();
    private SensorDB db;
    private ImageView er;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_search);
        initView();
        adapter = new MyLogItemRecyclerViewAdapter();
        db = new SensorDB(getApplicationContext());

        QueryBuilder<Sense> builder = db.getSession().queryBuilder(Sense.class);
        objects = builder.build().list();

        adapter.setObjects(objects);
        list.setAdapter(adapter);

        urls.add(API.AllSensor);
        jsons.add("{}");

        urls.add(API.AllSensor);
        jsons.add("{}");


        createER();
        myNetd.setListener(new MyNetd.OnRefershListener() {
            @Override
            public void onRefersh() {

                NetConnection.post2(urls, jsons, new NetConnection.CallBack2() {
                    @Override
                    public void success(List<String> results,boolean is) {
                        if (!is){
                            myNetd.setRefersh(false);
                            return;
//                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }
                        for (String json:results){
                            Gson gson = new Gson();
                            Sense sense = gson.fromJson(json,Sense.class);
                            Date date = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sense.setTime(format.format(date));
                            objects.add(sense);
                            adapter.setObjects(objects);

                            db.getSession().insert(sense);

                            adapter.notifyDataSetChanged();
                            myNetd.setRefersh(false);
                            Toast.makeText(getApplicationContext(),"Succcessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//        sw_s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });


    }

    private void createER() {
        Map<EncodeHintType,String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        try {
            Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
            BitMatrix bitMatrix = new QRCodeWriter().encode("你个傻吊", BarcodeFormat.QR_CODE,100,100,hints);
//            int pixs[] = new int[100*100];
            for (int i = 0; i<100;i++){
                for (int j = 0;j<100;j++){
//                    pixs[i*100+j] = ;
                    bitmap.setPixel(i,j,bitMatrix.get(i,j)?0xff000000:0xffffffff);
                }
            }
            er.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        er = findViewById(R.id.er);
        myNetd = findViewById(R.id.netd);
        list =  findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        sw_s = (SwipeRefreshLayout) findViewById(R.id.sw_s);
     }
}
