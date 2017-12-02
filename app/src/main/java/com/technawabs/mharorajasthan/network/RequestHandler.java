package com.technawabs.mharorajasthan.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestHandler {

    private OkHttpClient client = null;
    private Request request = null;
    private MediaType mediaType = MediaType.parse("application/json");
    private Response response;

    public RequestHandler(){
        client = new OkHttpClient();
    }

    public void addHeaders(Request request){
        request = new Request.Builder().addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "5363b8a1-3682-f710-caa5-a7286cf4db65").build();
    }

    public String makePostRequest(String url,String bodyStr) throws IOException {
        String res=new String();
        RequestBody body = RequestBody.create(mediaType,bodyStr);
        request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "5363b8a1-3682-f710-caa5-a7286cf4db65")
                .addHeader("Authorization", "Bearer ff8e02ccbf108f8a3379cfa85678d3a970e63810f977559b")
                .build();
        response = client.newCall(request).execute();
        if(response!=null){
            if(response.body()!=null){
                res=response.body().string();
            }
        }
        return res;
    }

    public String getUserDetailRequest(String url) throws IOException {
        String res=new String();
        request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "5363b8a1-3682-f710-caa5-a7286cf4db65")
                .addHeader("Authorization", "Bearer ff8e02ccbf108f8a3379cfa85678d3a970e63810f977559b")
                .build();
        response = client.newCall(request).execute();
        if(response!=null){
            if(response.body()!=null){
                res=response.body().string();
            }
        }
        return res;
    }

}
