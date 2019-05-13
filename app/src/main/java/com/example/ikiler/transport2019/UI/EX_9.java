package com.example.ikiler.transport2019.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikiler.transport2019.EX_1;
import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.SQLitMoney;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class EX_9 extends AppCompatActivity {
    private TextView save_his;
    private ListView listView;
    List<String> sel_urls = new ArrayList<String>();
    List<String> sel_jsons = new ArrayList<String>();
    List<String> sel_money = new ArrayList<String>();
    List<String> save_urls = new ArrayList<String>();
    List<String> save_jsons = new ArrayList<String>();
    List<String> car_id_list = new ArrayList<String>();
    ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_9);
        save_his = findViewById(R.id.save_his);
        listView = findViewById(R.id.list);
        save_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EX_9.this,EX_3.class));
            }
        });

        for (int i = 1;i<=3;i++){
            String url = "http://192.168.139.4:8890/type/jason/action/GetCarAccountBalance";
            String json = "{\"CarId\":"+i+"}";
            sel_urls.add(url);
            sel_jsons.add(json);
        }
        final ProgressDialog dialog = new ProgressDialog(EX_9.this);
        dialog.setMessage("网络请求中");
        dialog.show();
        NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
            @Override
            public void success(List<String> results) {
                    try {
                        for (String o : results){

                            JSONObject j = new JSONObject(o);
                            Log.i("info_sel", o);
                            sel_money.add(j.getString("Balance"));
                        }
                        listAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } dialog.cancel();

            }
        });

        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    public class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sel_money.size();
        }

        @Override
        public Object getItem(int i) {
            return sel_money.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(EX_9.this).inflate(R.layout.ex_9_item,null);

            final TextView car_id = v.findViewById(R.id.car_id);
            TextView car_p = v.findViewById(R.id.car_p);
            TextView car_n = v.findViewById(R.id.car_n);
            TextView money_his = v.findViewById(R.id.money_his);
            final CheckBox check = v.findViewById(R.id.check);
            Button save = v.findViewById(R.id.save);
            LinearLayout layout = v.findViewById(R.id.layout);
            car_id.setText(i+1+"");
            car_p.setText("辽A1000"+(i+1));

            if ((i+1) == 1){
                car_n.setText("张三");
            }else if((i+1) == 2){
                car_n.setText("李四");
            }else if((i+1) == 3){
                car_n.setText("王五");
            }
            if (Integer.parseInt(sel_money.get(i).toString())<200) {
                layout.setBackgroundColor(0xffffcc00);
            }

            money_his.setText("余额："+sel_money.get(i).toString()+"元");

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        car_id_list.add(i+1+"");
                    }else if(!b){
                        car_id_list.remove(i+1+"");
                    }
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(EX_9.this);
                    final View dia_v = LayoutInflater.from(EX_9.this).inflate(R.layout.ex_9_dialog,null);
                    final TextView car_pai = dia_v.findViewById(R.id.car_p);
                    final EditText ed = dia_v.findViewById(R.id.money);
                    final Button save = dia_v.findViewById(R.id.save);
                    Button back = dia_v.findViewById(R.id.back);
                    String pai = "";
                    if (car_id_list.size()==0){
                        car_pai.setText("辽A1000"+(i+1)+"    ");
                    }else {
                        for (int i = 0; i < car_id_list.size(); i++) {
                            pai += "辽A1000" + (Integer.parseInt(car_id_list.get(i).toString())) + "    ";
                        }
                        car_pai.setText(pai);
                    }
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ed.getText()!=null){
                                if (Integer.parseInt(ed.getText().toString())>=1&&Integer.parseInt(ed.getText().toString())<=999){
                                    if (car_id_list.size()==0){
                                        car_id_list.add(i+1+"");
                                    }
                                    save_urls.clear();
                                    save_jsons.clear();
                                    for (int i=0 ; i<car_id_list.size() ; i++){
                                        String url = "http://192.168.139.4:8890/type/jason/action/SetCarAccountRecharge";
                                        String json = "{\"CarId\":"+car_id_list.get(i).toString()+",\"Money\":"+ed.getText().toString()+"}";
                                        save_urls.add(url);
                                        save_jsons.add(json);
                                    }
                                    final ProgressDialog progressDialog = new ProgressDialog(EX_9.this);
                                    progressDialog.setMessage("网络请求中");
                                    progressDialog.show();
                                    NetConnection.post(save_urls, save_jsons, new NetConnection.CallBack() {
                                        @Override
                                        public void success(List<String> results) {

                                            Toast.makeText(EX_9.this,"充值成功",Toast.LENGTH_SHORT).show();
                                            progressDialog.cancel();
                                            for(int i=0;i<car_id_list.size();i++){
                                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date date = new Date();
                                                String nowTime = format.format(date);
                                                SQLitMoney sqLitMoney = new SQLitMoney(EX_9.this);
                                                SQLiteDatabase db = sqLitMoney.getWritableDatabase();
                                                db.execSQL("insert into saveHis values(null,'"+car_id_list.get(i).toString()+"','"+ed.getText().toString()+"','admin','"+nowTime+"')");
                                                Toast.makeText(EX_9.this,"保存记录成功"+nowTime,Toast.LENGTH_SHORT).show();
                                            }

                                            NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
                                                @Override
                                                public void success(List<String> results) {

                                                    try {
                                                        sel_money.clear();
                                                        for (String o : results){

                                                            JSONObject j = new JSONObject(o);
                                                            Log.i("info_sel", o);
                                                            sel_money.add(j.getString("Balance"));
                                                        }
                                                        listAdapter.notifyDataSetChanged();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });
                                            car_id_list.clear();
                                            dialog.cancel();
                                        }
                                    });
                                }else{
                                    ed.setError("充值金额必须是1-999元");
                                }


                            }else{
                                ed.setError("充值金额不能为空");
                            }
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    dialog.setContentView(dia_v);
                    dialog.show();

                }
            });


            return v;
        }
    }

}
