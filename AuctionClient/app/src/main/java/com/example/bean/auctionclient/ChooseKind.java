package com.example.bean.auctionclient;

import com.example.bean.auctionclient.util.HttpUtil;
import com.example.bean.auctionclient.util.DialogUtil;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Bean
 *         时间：2016年4月4日
 *         内容：查看物品种类
 */
public class ChooseKind extends Activity
{
    ListView kindList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_kind);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        kindList = (ListView) findViewById(R.id.kindList);

        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "category/all";
        try
        {
            // 向指定URL发送请求，并将服务器响应包装成JSONArray对象。
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            /*String result = "[ {\n" +
                    "    \"id\" : 1,\n" +
                    "    \"name\" : \"食品\",\n" +
                    "    \"cdesc\" : \"美味的食品\"\n" +
                    "  }, {\n" +
                    "    \"id\" : 2,\n" +
                    "    \"name\" : \"服饰\",\n" +
                    "    \"cdesc\" : \"各种各样的服饰\"\n" +
                    "  } \n" +
                    "]";
            JSONArray jsonArray = new JSONArray(result);*/
            // 使用ListView显示所有物品准种类
            kindList.setAdapter(new KindArrayAdapter(jsonArray
                    , ChooseKind.this));
        } catch (Exception e)
        {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
            e.printStackTrace();
        }
        kindList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // 启动ChooseItem Activity。
                Intent intent = new Intent(ChooseKind.this
                        , ChooseItem.class);
                // 将种类ID作为额外参数传过去
                intent.putExtra("kindId", id);
                startActivity(intent);
            }
        });
    }
}
