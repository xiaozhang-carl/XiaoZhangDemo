package com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 11:20
 * ToDo:
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.github_btn, R.id.jiashi_btn, R.id.download_btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.github_btn:
                startActivity(GitHubActivity.class);
                break;

            case R.id.jiashi_btn:
                startActivity(JiaShiActivity.class);
                break;

            case R.id.download_btn:
                startActivity(DownloadFileActivity.class);
                break;
        }
    }
    public  <T> void startActivity(Class<T> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
    }
}
