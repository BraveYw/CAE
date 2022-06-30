package com.yang.cae.function.about;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.yang.cae.R;

public class AboutCAEActivity extends AppCompatActivity implements View.OnClickListener {
    private DialogFragment dialogFragment;
    private TextView suggestionFeedback;
    private TextView advertiseAccess;
    private Button caeUpdate;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cae);
        initView();
        setListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //反馈与建议
            case R.id.suggestion_feedback:
                dialogFragment = new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("建议与反馈")
                        .setSubTitle("请在输入框中输入您的建议与反馈200字以内")
                        .setInputHint("请输入内容200字")
                        .setInputHeight(115)
                        .setInputShowKeyboard(true)
                        .setInputEmoji(true)
                        .setInputCounter(200)
                        //.setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
                            //文字加粗
                            //params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("提交", (text, v1) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(AboutCAEActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                                return false;
                            } else {
                                //Toast.makeText(AboutCAEActivity.this, text, Toast.LENGTH_SHORT).show();
                                Toast.makeText(AboutCAEActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
             //广告入驻
            case R.id.advertise_access:
                dialogFragment = new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("广告入驻")
                        .setSubTitle("请输入您的联系方式，工作人员将于24小时内联系您！")
                        .setInputHint("联系方式")
                        .setInputHeight(90)
                        .setInputShowKeyboard(true)
                        .setInputEmoji(true)
                        .setInputCounter(100)
                        //.setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
                            //文字加粗
                            //params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("提交", (text, v1) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(AboutCAEActivity.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
                                return false;
                            } else {
                                //Toast.makeText(AboutCAEActivity.this, text, Toast.LENGTH_SHORT).show();
                                Toast.makeText(AboutCAEActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            //检车更新
            case R.id.cae_update:
                dialogFragment = new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setWidth(0.7f)
                        .setProgressText("检测中...")
                        .configProgress(new ConfigProgress() {
                            @Override
                            public void onConfig(ProgressParams params) {
                                params.indeterminateColor = Color.parseColor("#E9AD44");
                            }
                        })
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
                        .setProgressColor(getColor(R.color.colorAccent))
                        // 图标x关闭按钮
                        .setCloseResId(R.mipmap.ic_close, 18)
                        .setCloseGravity(CloseParams.CLOSE_TOP_RIGHT)
                        .setClosePadding(new int[]{0, 0, 3, 3})
//                        .setOnCancelListener(
//                                dialog ->
//                                        Toast.makeText(AboutCAEActivity.this, "检查完成", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        caeUpdate.setText("暂无最新版本");
                        dialogFragment.dismiss();
                    }
                }, 3000);

                break;
        }
    }
    private void initView(){
        suggestionFeedback=(TextView)findViewById(R.id.suggestion_feedback);
        advertiseAccess=(TextView)findViewById(R.id.advertise_access);
        caeUpdate=(Button)findViewById(R.id.cae_update);
        back =(ImageView)findViewById(R.id.back);
    }
    private void setListener(){
        suggestionFeedback.setOnClickListener(this);
        advertiseAccess.setOnClickListener(this);
        caeUpdate.setOnClickListener(this);
        back.setOnClickListener(this);
    }
}