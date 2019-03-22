package com.wudh.study.pluginlib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by wudh on 2019/3/22.
 * 宿主和插件都需要依赖的共同标准
 **/
public interface PluginInterface {

    void onCreate(Bundle saveBundleInstance);

    void attachContext(FragmentActivity context);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
