package com.example.ikiler.transport2019.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EX_11 extends AppCompatActivity {
    private Spinner sp;
    private Button sel;
    private ListView listView;
    private Button sets;
    List<String> load_set = new ArrayList<String>();
    List<String> list = new ArrayList<String>();
    List<Info> info = new ArrayList<Info>();
    List<String> sel_urls = new ArrayList<String>();
    List<String> sel_jsons = new ArrayList<String>();
    List<String> save_urls = new ArrayList<String>();
    List<String> save_jsons = new ArrayList<String>();
    ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_11);
        sp = findViewById(R.id.sp);
        sel = findViewById(R.id.sel);
        listView = findViewById(R.id.list);
        Button sets = findViewById(R.id.sets);
        list.add("路口升序");
        list.add("路口降序");
        list.add("红灯升序");
        list.add("红灯降序");
        list.add("黄灯升序");
        list.add("黄灯降序");
        list.add("绿灯升序");
        list.add("绿灯降序");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EX_11.this,android.R.layout.simple_spinner_item,list);
        sp.setAdapter(adapter);

        for (int i=1;i<=3;i++){
            String url = "http://192.168.139.4:8890/type/jason/action/GetTrafficLightConfigAction";
            String json = "{\"TrafficLightId\":"+i+"}";
            Log.i("info_json", json);
            sel_urls.add(url);
            sel_jsons.add(json);
        }
        final ProgressDialog progressDialog = new ProgressDialog(EX_11.this);
        progressDialog.setMessage("网络请求中");
        progressDialog.show();
        NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
            @Override
            public void success(List<String> results) {
                try {
                    int i = 1;
                    for (String load : results) {
                        load = results.get(0);
                        Log.i("info1", load);
                        Info info1 = new Info();
                        JSONObject jsonObject = new JSONObject(load);
                        info1.load=i+"";
                        info1.red = jsonObject.getString("RedTime");
                        info1.green = jsonObject.getString("GreenTime");
                        info1.yellow = jsonObject.getString("YellowTime");
                        info.add(info1);
                        i++;
                    }
                    listAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }progressDialog.cancel();
            }
        });
        listView.setAdapter(listAdapter);
        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(info, new Comparator<Info>() {
                    @Override
                    public int compare(Info o1, Info o2) {
                        if(sp.getSelectedItem().toString().equals("路口升序")){

                            return Integer.parseInt(o1.load)-Integer.parseInt(o2.load);

                        }else if(sp.getSelectedItem().toString().equals("路口降序")){

                            return Integer.parseInt(o2.load)-Integer.parseInt(o1.load);

                        }else if(sp.getSelectedItem().toString().equals("红灯升序")){

                            return Integer.parseInt(o1.red)-Integer.parseInt(o2.red);

                        }else if(sp.getSelectedItem().toString().equals("红灯降序")){

                            return Integer.parseInt(o2.red)-Integer.parseInt(o1.red);

                        }
                        else if(sp.getSelectedItem().toString().equals("黄灯升序")){

                            return Integer.parseInt(o1.yellow)-Integer.parseInt(o2.yellow);

                        }else if(sp.getSelectedItem().toString().equals("黄灯降序")){

                            return Integer.parseInt(o2.yellow)-Integer.parseInt(o1.yellow);

                        }
                        else if(sp.getSelectedItem().toString().equals("绿灯升序")){

                            return Integer.parseInt(o1.green)-Integer.parseInt(o2.green);

                        }else if(sp.getSelectedItem().toString().equals("绿灯降序")){

                            return Integer.parseInt(o2.green)-Integer.parseInt(o1.green);

                        }
                        return 0;
                    }
                });
                listAdapter.notifyDataSetChanged();
            }
        });



    }
    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public Object getItem(int position) {
            return info.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(EX_11.this).inflate(R.layout.ex_11_item,null);
            final TextView load = v.findViewById(R.id.load);
            TextView red = v.findViewById(R.id.red);
            final TextView yellow = v.findViewById(R.id.yellow);
            final TextView green = v.findViewById(R.id.green);
            CheckBox ck = v.findViewById(R.id.ck);
            Button set = v.findViewById(R.id.set);
            load.setText(info.get(position).load);
            red.setText(info.get(position).red);
            yellow.setText(info.get(position).yellow);
            green.setText(info.get(position).green);

            ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        load_set.add(info.get(position).load);
                    }else if (!b){
                        load_set.remove(info.get(position).load);
                    }
                }
            });

            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(EX_11.this);
                    View dia_v = LayoutInflater.from(EX_11.this).inflate(R.layout.ex_11_dialog,null);
                    final TextView red_time = dia_v.findViewById(R.id.red_set);
                    final TextView yellow_time = dia_v.findViewById(R.id.yellow_set);
                    final TextView green_time = dia_v.findViewById(R.id.green_set);
                    Button save = dia_v.findViewById(R.id.save);
                    Button back = dia_v.findViewById(R.id.back);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (red_time.getText()!=null&&yellow_time.getText()!=null&&green_time.getText()!=null){
                                save_urls.clear();
                                save_jsons.clear();
                                String url = "http://192.168.139.4:8890/type/jason/action/SetTrafficLightConfig";
                                String json = "{\"TrafficLightId\":"+info.get(position).load+",\"RedTime\":"+red_time.getText().toString()+",\"GreenTime\":"+green.getText().toString()+",\"YellowTime\":"+yellow.getText().toString()+"}";
                                save_urls.add(url);
                                save_jsons.add(json);
                                final ProgressDialog progressDialog = new ProgressDialog(EX_11.this);
                                progressDialog.setMessage("网络连接中");
                                progressDialog.show();
                                NetConnection.post(save_urls, save_jsons, new NetConnection.CallBack() {
                                    @Override
                                    public void success(List<String> results) {
                                        progressDialog.cancel();
                                        Toast.makeText(EX_11.this,"设置成功",Toast.LENGTH_SHORT).show();
                                        final ProgressDialog progressDialog = new ProgressDialog(EX_11.this);
                                        progressDialog.setMessage("网络请求中");
                                        progressDialog.show();
                                        NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
                                            @Override
                                            public void success(List<String> results) {
                                                try {
                                                    info.clear();
                                                    int i = 1;
                                                    for (String load : results) {
                                                        Log.i("info1", load);
                                                        load = results.get(0);
                                                        Log.i("info1", load);
                                                        Info info1 = new Info();
                                                        JSONObject jsonObject = new JSONObject(load);
                                                        info1.load=i+"";
                                                        info1.red = jsonObject.getString("RedTime");
                                                        info1.green = jsonObject.getString("GreenTime");
                                                        info1.yellow = jsonObject.getString("YellowTime");
                                                        info.add(info1);
                                                        i++;
                                                    }
                                                    listAdapter.notifyDataSetChanged();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }progressDialog.cancel();
                                            }
                                        });
                                        dialog.cancel();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(EX_11.this,"不能有空值",Toast.LENGTH_SHORT).show();
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
    class Info{
        String load;
        String red;
        String yellow;
        String green;
    }
}
