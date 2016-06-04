package com.example.bean.auctionclient;

/**
 * Created by Bean on 2016/3/26.
 */

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;


// 定义一个结束当前Activity的监听器
public class FinishListener implements OnClickListener
{
    private Activity activity;

    public FinishListener(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(View source)
    {
        // 结束当前Activity
        activity.finish();
    }
}

