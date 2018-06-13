package com.android.launcher.integration.data;

/**
 * @author huguojin Function Key for app entry
 */
public class AppTile extends Tile {

    private String mPkgName;

    private String mClsName;

    private String mAppName;

    public String getPkgName() {
        return mPkgName;
    }

    public void setPkgName(String pkgName) {
        mPkgName = pkgName;
    }

    public String getClsName() {
        return mClsName;
    }

    public void setClsName(String clsName) {
        mClsName = clsName;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }
}
