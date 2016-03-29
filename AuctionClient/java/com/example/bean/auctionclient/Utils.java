package com.example.bean.auctionclient;

import android.content.Context;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Bean on 2016/3/28.
 */


public class Utils
{

    // 字符串是否为空（全是不可见字符的字符串认为是空）
    public static boolean isStrEmpty(Editable poStr)
    {
        String lsStr = poStr.toString();
        return isStrEmpty(lsStr);
    }

    // 字符串是否为空（全是不可见字符的字符串认为是空）
    public static boolean isStrEmpty(String psStr)
    {
        return psStr == null || psStr.trim().length() == 0;
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText输入框
     * @param mContext上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}

