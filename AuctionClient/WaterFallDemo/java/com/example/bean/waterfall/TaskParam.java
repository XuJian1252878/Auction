package com.example.bean.waterfall;

/**
 * Created by Bean on 2016/4/5.
 */
import android.content.res.AssetManager;

public class TaskParam
{
    private String filename;
    private AssetManager assetManager;
    private int ItemWidth;

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }

    public int getItemWidth()
    {
        return ItemWidth;
    }

    public void setItemWidth(int itemWidth)
    {
        ItemWidth = itemWidth;
    }
}
