package com.example.ikiler.transport2019;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EX_2 extends AppCompatActivity {
    private Spinner sp;
    private Button sel;
    private ListView listView;
    List<String> list = new ArrayList<String>();
    List<Info> info = new ArrayList<Info>();
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

        for (int i=0;i<=3;i++){
            String url = "";
        }

    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    class Info{
        String load;
        String red;
        String yellow;
        String green;
    }

}
