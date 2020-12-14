package com.example.emotion_dairy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ResGetGroup;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface api;

    private AppBarConfiguration mAppBarConfiguration;
    List<ResGetGroup> list = new ArrayList<ResGetGroup>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        api= HttpClient.getRetrofit().create(ApiInterface.class);
 //       getData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //회원정보 ( 닉네임, 그룹 1,2,3 가져옴 )

        //Fragment Manager

    }
    public void getData(){
        List<GroupList> gList=new ArrayList<GroupList>();
        String auth = PreferenceManager.getString(this,"Auth");
        Call<List<ResGetGroup>> call=api.getGroup(auth);
        call.enqueue(new Callback<List<ResGetGroup>>() {
            @Override
            public void onResponse(Call<List<ResGetGroup>> call, Response<List<ResGetGroup>> response) {
                list=response.body();
                for(int i=0;i<list.size();i++){
                    GroupList group = new GroupList(list.get(i).getTogether().getTno(),list.get(i).getTogether().getTname());
                    gList.add(group);
                }
                Log.d("log","그룹데이터 : "+gList.get(0));
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<GroupList>>(){}.getType();
                String jsonGroup = gson.toJson(gList,listType);
                PreferenceManager.setString(MainActivity.this,"Group",jsonGroup);
                Log.d("log","json data : "+jsonGroup);
                Log.d("log","통신성공");
            }

            @Override
            public void onFailure(Call<List<ResGetGroup>> call, Throwable t) {
                Log.d("log","통신실패: "+t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}