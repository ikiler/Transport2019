package com.example.ikiler.transport2019;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EX_2 extends AppCompatActivity {
    private Spinner sp;
    private Button sel;
    private ListView listView;
    List<String> list = new ArrayList<String>();
    List<Info> info = new ArrayList<Info>();
    List<String> urls = new ArrayList<String>();
    List<String> jsons = new ArrayList<String>();
    ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_2);
        sp = findViewById(R.id.sp);
        sel = findViewById(R.id.sel);
        listView = findViewById(R.id.list);

        list.add("路口升序");
        list.add("路口降序");
        list.add("红灯升序");
        list.add("红灯降序");
        list.add("黄灯升序");
        list.add("黄灯降序");
        list.add("绿灯升序");
        list.add("绿灯降序");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EX_2.this,android.R.layout.simple_spinner_item,list);
        sp.setAdapter(adapter);

        for (int i=1;i<=3;i++){
            String url = "http://192.168.139.4:8890/type/jason/action/GetTrafficLightConfigAction";
            String json = "{\"TrafficLightId\":"+i+"}";
            urls.add(url);
            jsons.add(json);
        }
        NetConnection.post(urls, jsons, new NetConnection.CallBack() {
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
                }
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(EX_2.this).inflate(R.layout.ex_2_item,null);
            TextView load = v.findViewById(R.id.load);
            TextView red = v.findViewById(R.id.red);
            TextView yellow = v.findViewById(R.id.yellow);
            TextView green = v.findViewById(R.id.green);
            load.setText(info.get(position).load);
            red.setText(info.get(position).red);
            yellow.setText(info.get(position).yellow);
            green.setText(info.get(position).green);
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
