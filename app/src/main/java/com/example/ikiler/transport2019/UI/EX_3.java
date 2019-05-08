package com.example.ikiler.transport2019.UI;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.SQLitMoney;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EX_3 extends AppCompatActivity {
    private Spinner sp;
    private ListView listView;
    private Button sel;
    String[] sp_item = {"时间升序","时间降序"};
    List<Info> list = new ArrayList<Info>();
    ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_3);
        sp = findViewById(R.id.sp);
        listView = findViewById(R.id.list);
        sel = findViewById(R.id.sel);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EX_3.this,android.R.layout.simple_spinner_item,sp_item);
        sp.setAdapter(arrayAdapter);
        SQLitMoney sqLitMoney = new SQLitMoney(EX_3.this);
        SQLiteDatabase db = sqLitMoney.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from saveHis",null);
        while (cursor.moveToNext()){
            Info info = new Info();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.car_id = cursor.getString(cursor.getColumnIndex("car_id"));
            info.money = cursor.getString(cursor.getColumnIndex("saveMoney"));
            info.peo = cursor.getString(cursor.getColumnIndex("peo"));
            info.time = cursor.getString(cursor.getColumnIndex("time"));
            list.add(info);
        }
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(list, new Comparator<Info>() {
                    @Override
                    public int compare(Info o1, Info o2) {
                        if (sp.getSelectedItem().toString().equals("时间升序")){
                            return o1.time.compareTo(o2.time);
                        }else if(sp.getSelectedItem().toString().equals("时间降序")){
                            return o2.time.compareTo(o1.time);
                        }
                        return 0;
                    }
                });
                listAdapter.notifyDataSetChanged();
            }
        });
    }
    public class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(EX_3.this).inflate(R.layout.ex_3_item,null);
            TextView id = v.findViewById(R.id.id);
            TextView car_id = v.findViewById(R.id.car_id);
            TextView money = v.findViewById(R.id.money);
            TextView peo = v.findViewById(R.id.peo);
            TextView time = v.findViewById(R.id.time);
            id.setText(list.get(i).id);
            car_id.setText(list.get(i).car_id);
            money.setText(list.get(i).money);
            peo.setText(list.get(i).peo);
            time.setText(list.get(i).time);
            return v;
        }
    }
    class Info{
        String id;
        String car_id;
        String money;
        String peo;
        String time;
    }
}
