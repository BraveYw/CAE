package com.yang.cae.function.setting;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.R;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ToastUtils.getInstance().initToast(this);
        initView();
        setListener();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
        }
    }

    private void initView(){
        back =(ImageView)findViewById(R.id.back);
    }
    private void setListener(){
        back.setOnClickListener(this);
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            PreferenceManager preferenceManager = getPreferenceManager();
            System.out.println("================="+preferenceManager.findPreference("theme"));
        }
    }
}