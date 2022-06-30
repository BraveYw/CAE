package com.yang.cae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mengpeng.mphelper.ToastUtils;
import com.mylhyl.circledialog.CircleDialog;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.function.about.AboutCAEActivity;
import com.yang.cae.function.login.LoginActivity;
import com.yang.cae.bean.PictureTypeEntity;
import com.yang.cae.function.search.SearchActivity;
import com.yang.cae.function.setting.SettingsActivity;
import com.yang.cae.function.setting.UserSettingsActivity;
import com.yang.cae.navigation.home.HomeFragment;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout drawerLayout;//侧滑抽屉布局
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Intent intent;
    /*创建一个Drawerlayout和Toolbar联动的开关*/
    //菜单图标
    private ImageView menu;
    //菜单图标
    private ImageView toolbarSearch;
    //头像点击
    private ImageView iconImage;
    private TextView nickName;
    RequestAPI requestApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        ToastUtils.getInstance().initToast(this);
        View layout = factory.inflate(R.layout.header, null);

        iconImage = (ImageView) layout.findViewById(R.id.icon_image);
        nickName = (TextView) layout.findViewById(R.id.nick_name);

        nickName.setText("fndsjkjfiklasdfoia");
        initViews();
        setSupportActionBar(toolbar);
        setListener();
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        Call<ResponseBody> call = requestApi.getNickName();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String resp = new String(response.body().bytes());
                        Result result = JsonUtil.respBodyParse(resp);
//                        nickName.setText(result.getMessage());
//                        ToastUtils.onErrorShowToast(result.getMessage());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    ToastUtils.onWarnShowToast("请求失败！");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }});

        AppBarConfiguration appBarConfiguration
                = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_recommend,
                R.id.navigation_collect,
                R.id.navigation_expand).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //头像
            case R.id.icon_image:

                final List<PictureTypeEntity> items = new ArrayList<>();
                items.add(new PictureTypeEntity("1", "拍照"));
                items.add(new PictureTypeEntity("2", "从相册选择"));
//                final String[] items = {"拍照", "从相册选择", "小视频"};
                new CircleDialog.Builder()
                        .configDialog(params -> {
                            params.backgroundColorPress = Color.CYAN;
                            //增加弹出动画
                            params.animStyle = R.style.dialogWindowAnim;
                            //params.gravity = Gravity.END;
                        })
                        .setTitle("头像更改")
                        .setTitleColor(Color.BLUE)
                        .configTitle(params -> {
                            //params.backgroundColor = Color.WHITE;
                        })
                        .setSubTitle("请选择头像上传方式")
                        .configSubTitle(params -> {
//                                params.backgroundColor = Color.YELLOW;
                        })
                        .setItems(items, (parent, view1, position1, id) -> {
                                    ToastUtils.onWarnShowToast("熬夜开发中,敬请期待！");
                            nickName.setText("456456465465");
                                    return true;
                                }
                        )

                        .setNegative("取消", null)
                        .show(getSupportFragmentManager());
                break;
            //菜单
            case R.id.menu:
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawers();
                    //menu.setColorFilter(0);
                }else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;
                //搜索
            case R.id.toolbar_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

        }
    }

    /*设置监听器*/
    private void setListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_setting:
                        intent = new Intent(MainActivity.this, UserSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.system_settings:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about_cae:
                        intent = new Intent(MainActivity.this, AboutCAEActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logout:
                        Intent intent =new Intent(MainActivity.this, LoginActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("login_auto", false);
                        editor.putBoolean("login_remember", false);
                        editor.putString("password", "");
                        editor.putString("token", "null");
                        editor.commit();
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.exit:
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /*初始化View*/
    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        menu = (ImageView)findViewById(R.id.menu);
        menu.setOnClickListener(this);
        toolbarSearch = (ImageView)findViewById(R.id.toolbar_search);
        toolbarSearch.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
}