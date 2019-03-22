package com.wudh.study.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by wudh on 2019/3/22.
 **/
public class PluginApk {
    //插件实体对象

    public PackageInfo mPackageInfo;
    public Resources mResources;
    public AssetManager mAssetManager;
    public ClassLoader mClassLoader;

    public PluginApk(PackageInfo mPackageInfo, Resources mResources, ClassLoader mClassLoader) {
        this.mPackageInfo = mPackageInfo;
        this.mResources = mResources;
        this.mAssetManager = mResources.getAssets();
        this.mClassLoader = mClassLoader;
    }

    public PackageInfo getmPackageInfo() {
        return mPackageInfo;
    }

    public Resources getmResources() {
        return mResources;
    }

    public AssetManager getmAssetManager() {
        return mAssetManager;
    }

    public ClassLoader getmClassLoader() {
        return mClassLoader;
    }
}
