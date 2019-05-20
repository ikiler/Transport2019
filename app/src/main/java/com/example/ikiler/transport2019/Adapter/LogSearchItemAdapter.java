package com.example.ikiler.transport2019.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.bean.Sense;

public class LogSearchItemAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public LogSearchItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.log_search_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        Sense sense= (Sense) object;
        holder.tvCo2.setText(sense.getCo2()+"");
        holder.tvHum.setText(sense.getHumidity()+"");
        holder.tvLight.setText(sense.getLightIntensity()+"");
        holder.tvPm254.setText(sense.getPm25()+"");
        holder.tvTemp.setText(sense.getTemperature()+"");
        holder.tvTime.setText(sense.getTime());
    }

    protected class ViewHolder {
        private ImageView imgCo2;
    private TextView tvCo2;
    private ImageView imgHum;
    private TextView tvHum;
    private ImageView imgPm25;
    private TextView tvPm254;
    private ImageView imgLight;
    private TextView tvLight;
    private ImageView imgTemp;
    private TextView tvTemp;
    private TextView tvTime;

        public ViewHolder(View view) {
            imgCo2 = (ImageView) view.findViewById(R.id.img_co2);
            tvCo2 = (TextView) view.findViewById(R.id.tv_co2);
            imgHum = (ImageView) view.findViewById(R.id.img_hum);
            tvHum = (TextView) view.findViewById(R.id.tv_hum);
            imgPm25 = (ImageView) view.findViewById(R.id.img_pm25);
            tvPm254 = (TextView) view.findViewById(R.id.tv_pm254);
            imgLight = (ImageView) view.findViewById(R.id.img_light);
            tvLight = (TextView) view.findViewById(R.id.tv_light);
            imgTemp = (ImageView) view.findViewById(R.id.img_temp);
            tvTemp = (TextView) view.findViewById(R.id.tv_temp);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
