package com.wudh.study.plugindemo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.wudh.study.pluginlib.PluginInterface;
import com.wudh.study.pluginlib.PluginManager;


/**
 * Created by wudh on 2019/3/22.
 **/
public class ProxyActivity extends FragmentActivity {

    private PluginInterface pluginInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("className");
        try {
            Class<?> clazz= PluginManager.getmInstance().getPluginApk().getmClassLoader().loadClass(className);
            Object newInstance = clazz.newInstance();
            //程序健壮性检查
            if (newInstance instanceof PluginInterface) {
                pluginInterface = (PluginInterface) newInstance;
                //将代理Activity的实例传递给三方Activity
                pluginInterface.attachContext(this);
                //创建bundle用来与三方apk传输数据
                Bundle bundle = new Bundle();
                //调用三方Activity的onCreate，
                pluginInterface.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getmInstance().getPluginApk().getmResources();
    }
    @Override
    public void startActivity(Intent intent) {
        Intent newIntent = new Intent(this, ProxyActivity.class);
        newIntent.putExtra("className", intent.getComponent().getClassName());
        super.startActivity(newIntent);
    }

    @Override
    public void onStart() {
        pluginInterface.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        pluginInterface.onResume();
        super.onResume();
    }

    @Override
    public void onRestart() {
        pluginInterface.onRestart();
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        pluginInterface.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        pluginInterface.onStop();
        super.onStop();
    }

    @Override
    public void onPause() {
        pluginInterface.onPause();
        super.onPause();
    }

}
