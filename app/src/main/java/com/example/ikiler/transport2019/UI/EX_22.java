package com.example.ikiler.transport2019.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.http.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EX_22 extends AppCompatActivity {
    private TextView saves;
    private TextView his;
    private ListView listView;
    List<String> car = new ArrayList<String>();
    List<String> sel_urls = new ArrayList<String>();
    List<String> sel_jsons = new ArrayList<String>();
    List<String> money = new ArrayList<String>();
    List<String> save_urls = new ArrayList<String>();
    List<String> save_jsons = new ArrayList<String>();
    ListAdapter listAdapter = new ListAdapter();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_22);
        saves = findViewById(R.id.saves);
        his = findViewById(R.id.his);
        listView = findViewById(R.id.list);
        builder = new AlertDialog.Builder(EX_22.this);
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EX_22.this,EX_3.class));
            }
        });

        for (int i = 1;i<=3;i++){
            String url = "http://192.168.139.4:8890/type/jason/action/GetCarAccountBalance";
            String json = "{\"CarId\":"+i+"}";
            sel_urls.add(url);
            sel_jsons.add(json);
        }

        NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
            @Override
            public void success(List<String> results) {
                    try {
                        for (String result : results){
                            JSONObject jsonObject = new JSONObject(result);
                            money.add(jsonObject.getString("Balance"));
                        }
                        listAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

            saves.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (car.size() == 0) {
                        Toast.makeText(EX_22.this, "请选择要充值的小车", Toast.LENGTH_SHORT).show();
                    } else {
                        save_urls.clear();
                        save_jsons.clear();
                        View dia = LayoutInflater.from(EX_22.this).inflate(R.layout.ex_22_dia, null);
                        builder.setView(dia);
                        final Dialog dialog = builder.create();
                        dialog.show();

                        TextView car_i = dia.findViewById(R.id.car_i);
                        final EditText save_money = dia.findViewById(R.id.save_money);
                        Button save = dia.findViewById(R.id.save);
                        Button back = dia.findViewById(R.id.back);
                        String car_p = "";
                        for (String id : car) {
                            car_p += id+"    ";
                        }
                        car_i.setText(car_p);
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                save_urls.clear();
                                save_jsons.clear();
                                if (save_money.getText() != null) {
                                    if (Integer.parseInt(save_money.getText().toString()) > 1 && Integer.parseInt(save_money.getText().toString()) < 999) {
                                        for (String id : car) {
                                            save_urls.add("http://192.168.139.4:8890/type/jason/action/SetCarAccountRecharge");
                                            save_jsons.add("{\"CarId\":" + id + ",\"Money\":" + save_money.getText().toString() + "}");
                                        }
                                        final ProgressDialog progressDialog = new ProgressDialog(EX_22.this);
                                        progressDialog.setMessage("网络请求中");
                                        progressDialog.show();
                                        NetConnection.post(save_urls, save_jsons, new NetConnection.CallBack() {
                                            @Override
                                            public void success(List<String> results) {

                                                try {
                                                    String res = results.get(0);
                                                    JSONObject jsonObject = new JSONObject(res);
                                                    String ok = jsonObject.getString("result");
                                                    if (ok.equals("ok")) {
                                                        Toast.makeText(EX_22.this, "充值成功", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(EX_22.this, "充值失败", Toast.LENGTH_SHORT).show();
                                                    }
                                                    NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
                                                        @Override
                                                        public void success(List<String> results) {
                                                            try {
                                                                money.clear();
                                                                for (String result : results) {
                                                                    JSONObject jsonObject = new JSONObject(result);
                                                                    money.add(jsonObject.getString("Balance"));
                                                                }
                                                                listAdapter.notifyDataSetChanged();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                    car.clear();
                                                    dialog.cancel();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                progressDialog.cancel();
                                            }
                                        });
                                    } else {
                                        save_money.setError("充值金额不正确");
                                    }
                                } else {
                                    save_money.setError("不可以为空");
                                }
                            }
                        });


                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });

                    }
                }
            });

    }

    public class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return money.size();
        }

        @Override
        public Object getItem(int i) {
            return money.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(EX_22.this).inflate(R.layout.ex_22_item,null);
            final TextView car_id = v.findViewById(R.id.car_id);
            TextView money_his = v.findViewById(R.id.money_his);
            CheckBox ck = v.findViewById(R.id.ck);
            final Button save = v.findViewById(R.id.save);
            car_id.setText((i+1)+"");
            money_his.setText(money.get(i));
            ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        car.add((i+1)+"");
                    }else if(!b){
                        car.remove((i+1)+"");
                    }
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View dia = LayoutInflater.from(EX_22.this).inflate(R.layout.ex_22_dia,null);
                    builder.setView(dia);
                    final Dialog dialog = builder.create();
                    dialog.show();

                    TextView car_i = dia.findViewById(R.id.car_i);
                    final EditText save_money = dia.findViewById(R.id.save_money);
                    Button save = dia.findViewById(R.id.save);
                    Button back = dia.findViewById(R.id.back);
                    car_i.setText((i+1)+"");
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            save_urls.clear();
                            save_jsons.clear();
                            if (save_money.getText()!=null){
                                if (Integer.parseInt(save_money.getText().toString())>1&&Integer.parseInt(save_money.getText().toString())<999){
                                    save_urls.add("http://192.168.139.4:8890/type/jason/action/SetCarAccountRecharge");
                                    save_jsons.add("{\"CarId\":"+(i+1)+",\"Money\":"+save_money.getText().toString()+"}");
                                    final ProgressDialog progressDialog = new ProgressDialog(EX_22.this);
                                    progressDialog.setMessage("网络请求中");
                                    progressDialog.show();
                                    NetConnection.post(save_urls, save_jsons, new NetConnection.CallBack() {
                                        @Override
                                        public void success(List<String> results) {

                                            try {
                                                String res = results.get(0);
                                                JSONObject jsonObject = new JSONObject(res);
                                                String ok = jsonObject.getString("result");
                                                if (ok.equals("ok")){
                                                    Toast.makeText(EX_22.this,"充值成功",Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(EX_22.this,"充值失败",Toast.LENGTH_SHORT).show();
                                                }
                                                NetConnection.post(sel_urls, sel_jsons, new NetConnection.CallBack() {
                                                    @Override
                                                    public void success(List<String> results) {
                                                        try {
                                                            money.clear();
                                                            for (String result : results){
                                                                JSONObject jsonObject = new JSONObject(result);
                                                                money.add(jsonObject.getString("Balance"));
                                                            }
                                                            listAdapter.notifyDataSetChanged();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });

                                                dialog.cancel();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }   progressDialog.cancel();
                                        }
                                    });
                                }
                                else {
                                    save_money.setError("充值金额不正确");
                                }
                            }else {
                                save_money.setError("不可以为空");
                            }
                        }
                    });


                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                }
            });

            return v;
        }
    }

}
