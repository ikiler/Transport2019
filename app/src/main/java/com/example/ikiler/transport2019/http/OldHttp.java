package com.example.ikiler.transport2019.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ikiler on 19-5-7.
 */



public class OldHttp {
    public static void postWithJson(String url, String json, final Objectcallback objectcallback){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).post(RequestBody.create(MediaType.parse("application/json"),json)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        objectcallback.onFalia();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rest = response.body().string();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        objectcallback.onsuccess(rest);
                    }
                });

            }
        });
    }

    public interface Objectcallback {
        void onsuccess(String st);

        void onFalia();

    }
}