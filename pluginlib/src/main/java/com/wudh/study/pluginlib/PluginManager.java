package com.wudh.study.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by wudh on 2019/3/22.
 **/
public class PluginManager {

    private final static PluginManager mInstance=new PluginManager();

    private PluginManager(){

    }

    public static PluginManager getmInstance() {
        return mInstance;
    }

    private PluginApk pluginApk;
    private Context mContext;

    public void init(Context context){
        mContext=context.getApplicationContext();

    }
    public void loadApk(String apkPath){
        PackageInfo packageInfo= mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);

        if (packageInfo==null){
            return;
        }
        DexClassLoader classLoader=createDexClassLoader(apkPath);
        Resources resources=createResources(createAssetManager(apkPath));
        pluginApk=new PluginApk(packageInfo,resources,classLoader);
    }

    public PluginApk getPluginApk() {
        return pluginApk;
    }

    private Resources createResources(AssetManager assetManager) {
        return new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {

        try {
            AssetManager assetManager=AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        return new DexClassLoader(apkPath,
                mContext.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(),
                null, mContext.getClassLoader());
    }

}
