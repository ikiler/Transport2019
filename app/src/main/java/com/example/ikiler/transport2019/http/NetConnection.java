package com.example.ikiler.transport2019.http;

import android.arch.lifecycle.Lifecycle;

import java.io.IOException;

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

    public static void post(String url, String json, final CallBack callBack){

        final Request request2 = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request2).execute();
                    if (response.isSuccessful()){
                        callBack.success(response.body().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public interface CallBack{
        void success(String result);
    }
}
