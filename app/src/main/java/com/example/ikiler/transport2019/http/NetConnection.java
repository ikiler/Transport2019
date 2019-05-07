package com.example.ikiler.transport2019.http;

import android.arch.lifecycle.Lifecycle;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by ikiler on 19-5-7.
 */

public class NetConnection {

    static OkHttpClient client = new OkHttpClient();
    static Handler handler = new Handler(Looper.getMainLooper());

    public static void post(final List<String> urls, final String json, final CallBack callBack){
        final List<String> jsons = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String url:urls){
                    final Request request2 = new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(MediaType.parse("application/json"),json))
                            .build();
                    try {
                        Response response = client.newCall(request2).execute();
                        if (response.isSuccessful()){
                            jsons.add(response.body().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                callBack.success(jsons);
            }
        }).start();
    }
    public interface CallBack{
        void success(List<String> results);
    }
}