package com.example.biye.utils;

import android.util.Log;
import com.example.biye.config.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Call;
import okhttp3.Callback;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class NetworkHelper {
    private static final String TAG = "NetworkHelper";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient client;
    
    public NetworkHelper() {
        client = new OkHttpClient.Builder()
            .connectTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build();
    }
    
    public interface NetworkCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
    
    public void sendRequest(String endpoint, JSONObject data, NetworkCallback<JSONObject> callback) {
        try {
            RequestBody body = RequestBody.create(data.toString(), JSON);
            Request request = new Request.Builder()
                .url(AppConfig.API_BASE_URL + endpoint)
                .post(body)
                .build();
                
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    try (Response r = response) {
                        String responseData = r.body().string();
                        JSONObject jsonResponse = new JSONObject(responseData);
                        callback.onSuccess(jsonResponse);
                    } catch (Exception e) {
                        callback.onError(e.getMessage());
                    }
                }
                
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }
            });
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }
} 