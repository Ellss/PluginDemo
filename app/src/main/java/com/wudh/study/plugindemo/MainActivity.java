package com.wudh.study.plugindemo;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wudh.study.pluginlib.PluginManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends FragmentActivity {

    private String apkPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/plugin.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load_apk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注意：使用运行时权限
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        });
        findViewById(R.id.btn_goto_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startApk();
            }
        });
    }

    public void startApk() {
        PluginManager.getmInstance().init(MainActivity.this);
        PluginManager.getmInstance().loadApk(apkPath);

        String otherApkMainActivityName = PluginManager.getmInstance().getPluginApk().getmPackageInfo().activities[0].name;
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", otherApkMainActivityName);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            InputStream inputStream=getAssets().open("plugin.apk");
            File file = new File(apkPath);
            if (!file.exists()){
                FileOutputStream out=new FileOutputStream(file);
                byte[] buffer=new byte[1024];
                int len=-1;
                while ((len=inputStream.read(buffer))!=-1){
                    out.write(buffer,0,len);
                }
                out.flush();//刷新缓存区
                inputStream.close();
                out.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
