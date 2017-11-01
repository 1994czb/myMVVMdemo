package com.example.mvvmde;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mvvmde.adapter.MyListViewAdapter;
import com.example.mvvmde.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyListViewAdapter<Food> adapter = new MyListViewAdapter<>(MainActivity.this, R.layout.item, foods,BR.food);
            lv.setAdapter(adapter);

        }
    };
    private List<Food> foods;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        //MVVM加载布局的方式
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        UserEntity user = new UserEntity();
        user.setAge(34);
        user.setUsername("zhangsan");
        user.setNickname("张三");
        user.setUserface("http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg");
        activityMainBinding.setUser(user);


        lv = ((ListView) findViewById(R.id.lv));

        initData();
    }

    private void initData() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("http://www.tngou.net/api/food/list?id=1").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    parseJson(response.body().string());
                }

            }

        });
    }

    private void parseJson(String jsonStr) {
        foods = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(jsonStr);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject item = tngou.getJSONObject(i);
                String description = item.getString("description");
                String img = "http://tnfs.tngou.net/image"+item.getString("img");
                String keywords = "【关键词】 "+item.getString("keywords");
                String summary = item.getString("summary");
                foods.add(new Food(description, img, keywords, summary));
            }
            mHandler.sendEmptyMessage(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
