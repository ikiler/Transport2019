package com.example.ikiler.transport2019.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EX_21 extends AppCompatActivity {
    private Spinner sp;
    private ListView listView;
    List<String> sp_list = new ArrayList<String>();
    List<String> urls = new ArrayList<String>();
    List<String> jsons = new ArrayList<String>();
    List<Info> infos = new ArrayList<Info>();
    ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_21);
        listView = findViewById(R.id.list);
        sp = findViewById(R.id.sp);
        sp_list.add("路口升序");
        sp_list.add("路口降序");
        sp_list.add("红灯升序");
        sp_list.add("红灯降序");
        sp_list.add("黄灯升序");
        sp_list.add("黄灯降序");
        sp_list.add("绿灯升序");
        sp_list.add("绿灯降序");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EX_21.this,android.R.layout.simple_spinner_item,sp_list);
        sp.setAdapter(arrayAdapter);

        for (int i = 1 ; i<=3;i++){
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
                        for(String result : results){
                            Info info = new Info();
                            JSONObject jsonObject = new JSONObject(result);
                            Log.i("info_1", result);
                            String red = jsonObject.getString("RedTime");
                            String yellow = jsonObject.getString("YellowTime");
                            String green = jsonObject.getString("GreenTime");
                            info.red = red;
                            info.green = green;
                            info.yellow = yellow;
                            info.load = i+"";
                            infos.add(info);
                            i++;
                        }
                        listAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        });
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Collections.sort(infos, new Comparator<Info>() {
                    @Override
                    public int compare(Info o1, Info o2) {

                        if (sp.getSelectedItem().toString().equals("路口升序")){
                            return Integer.parseInt(o1.load)-Integer.parseInt(o2.load) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("路口降序")){
                            return Integer.parseInt(o2.load)-Integer.parseInt(o1.load) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("红灯升序")){
                            return Integer.parseInt(o1.red)-Integer.parseInt(o2.red) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("红灯降序")){
                            return Integer.parseInt(o2.red)-Integer.parseInt(o1.red) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("黄灯升序")){
                            return Integer.parseInt(o1.yellow)-Integer.parseInt(o2.yellow) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("黄灯降序")){
                            return Integer.parseInt(o2.yellow)-Integer.parseInt(o1.yellow) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("绿灯升序")){
                            return Integer.parseInt(o1.green)-Integer.parseInt(o2.green) ;
                        }
                        else if(sp.getSelectedItem().toString().equals("红灯降序")){
                            return Integer.parseInt(o2.green)-Integer.parseInt(o1.green) ;
                        }
                        return 0;
                    }
                });

                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int i) {
            return infos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v  = LayoutInflater.from(EX_21.this).inflate(R.layout.ex_21_item,null);
            TextView load = v.findViewById(R.id.load);
            TextView red = v.findViewById(R.id.red);
            TextView yellow = v.findViewById(R.id.yellow);
            TextView green = v.findViewById(R.id.green);
            load.setText(infos.get(i).load);
            red.setText(infos.get(i).red);
            yellow.setText(infos.get(i).yellow);
            green.setText(infos.get(i).green);

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
