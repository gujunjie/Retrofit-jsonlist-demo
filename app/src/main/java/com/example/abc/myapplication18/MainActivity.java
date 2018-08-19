package com.example.abc.myapplication18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private  String[] data=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imageView=(ImageView)findViewById(R.id.image);

         ListView listView=(ListView)findViewById(R.id.listview);

         ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);

        Glide.with(this).load("http://192.168.42.16:8080/1.jpg").into(imageView);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.42.16:8080/")
                .build();

        JsonService jsonService=retrofit.create(JsonService.class);

        Call<ResponseBody> call=jsonService.getJsonList();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().string();
                    Log.d("text",s);
                    Gson gson=new Gson();
                    List<DataBean> list=gson.fromJson(s,new TypeToken<List<DataBean>>(){}.getType());

                    for(int i=0;i<list.size();i++)
                    {
                        data[i]=list.get(i).getName()+" "+list.get(i).getId()+" "+list.get(i).getVersion();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                 Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
            }
        });


        listView.setAdapter(adapter);


    }
}
