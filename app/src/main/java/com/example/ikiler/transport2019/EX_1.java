package com.example.ikiler.transport2019;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EX_1 extends AppCompatActivity {
    private TextView mon_his;
    private Spinner sp;
    private EditText et;
    private Button sel;
    private Button save;
    String[] car_id = {"1","2","3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_1);
        mon_his = findViewById(R.id.mon_his);
        sp = findViewById(R.id.sp);
        et = findViewById(R.id.et);
        sel = findViewById(R.id.sel);
        save = findViewById(R.id.save);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EX_1.this,android.R.layout.simple_spinner_item,car_id);
        sp.setAdapter(arrayAdapter);

        String url = "http://192.168.139.4:8890/type/jason/action/GetCarAccountBalance";
        String json = "{\"CarId\":1}";
//        OkhttpUtil.postWithJson(url,json,"1");
//
//        LiveBus.getInstance().subscribe("1").observe(EX_1.this, new Observer<Object>() {
//            @Override
//            public void onChanged(Object o) {
//                try {
//                    JSONObject jsonObject = new JSONObject((String)o);
//                    //Log.i("info", (String)o);
//                    String s = jsonObject.getString("Balance");
//                    mon_his.setText(s+"元");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.139.4:8890/type/jason/action/GetCarAccountBalance";
                String json = "{\"CarId\":"+sp.getSelectedItem().toString()+"}";
                //OkhttpUtil.postWithJson(url,json,"1");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText()!=null){
                    if (Integer.parseInt(et.getText().toString())<1&&Integer.parseInt(et.getText().toString())>999){
                        et.setError("请输入1-999之间的数");
                    }
                    else {
                        String url = "http://192.168.139.4:8890/type/jason/action/SetCarAccountRecharge";
                        String json = "{\"CarId\":"+sp.getSelectedItem().toString()+",\"Money\":"+et.getText().toString()+"}";
//                        OkhttpUtil.postWithJson(url,json,"two");
//                        LiveBus.getInstance().subscribe("two").observe(EX_1.this, new Observer<Object>() {
//                            @Override
//                            public void onChanged(Object o) {
//
//                                String url = "http://192.168.139.4:8890/type/jason/action/GetCarAccountBalance";
//                                String json = "{\"CarId\":"+sp.getSelectedItem().toString()+"}";
//                                OkhttpUtil.postWithJson(url,json,"1");
//                                Toast.makeText(EX_1.this,"充值成功",Toast.LENGTH_SHORT).show();
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                Date date = new Date();
//                                String nowTime = format.format(date);
//                                SQLitMoney sqLitMoney = new SQLitMoney(EX_1.this);
//                                SQLiteDatabase db = sqLitMoney.getWritableDatabase();
//                                db.execSQL("insert into saveHis values(null,'"+sp.getSelectedItem().toString()+"','"+et.getText().toString()+"','admin','"+nowTime+"')");
//                                Toast.makeText(EX_1.this,"保存记录成功"+nowTime,Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                }
            }
        });




    }
}
