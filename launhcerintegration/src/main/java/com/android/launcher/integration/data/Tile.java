package com.android.launcher.integration.data;

import android.graphics.drawable.Icon;

/**
 * @author huguojin Function Key for special
 */
public class Tile extends Dashboard implements Constant.ViewType {

    private Icon mIcon;
    private FunctionType mType;

    @Override
    public int getViewType() {
        return Constant.TILE;
    }

    public Icon getIcon() {
        return mIcon;
    }

    public void setIcon(Icon icon) {
        mIcon = icon;
    }

    public FunctionType getType() {
        return mType;
    }

    public void setType(FunctionType type) {
        mType = type;
    }

    public void setType(int typeIndex) {

    }
}
