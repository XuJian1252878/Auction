package com.example.bean.auctionclient;

import com.example.bean.auctionclient.util.HttpUtil;
import com.example.bean.auctionclient.util.DialogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
* @author Bean
 *         时间：2016年4月29日
 *         内容：展示物品
 */
public class ChooseItem extends Activity
{
    ListView succList;
    TextView viewTitle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        succList = (ListView) findViewById(R.id.succList);
        viewTitle = (TextView) findViewById(R.id.view_titile);
        long kindId = getIntent().getLongExtra("kindId", -1);
        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "category/products/" + kindId;
        try
        {
            // 根据种类ID获取该种类对应的所有物品
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            /*String result = "[ \n" +
                    "  {\n" +
                    "    \"id\" : 4,\n" +
                    "    \"name\" : \"a test food name\"\n" +
                    "  }\n" +
                    "]";
            JSONArray jsonArray = new JSONArray(result);*/
            JSONArrayAdapter adapter = new JSONArrayAdapter(
                    this, jsonArray, "name", true);
            // 使用ListView显示当前种类的所有物品
            succList.setAdapter(adapter);
        } catch (Exception e1)
        {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
            e1.printStackTrace();
        }
        // 修改标题
        viewTitle.setText(R.string.item_list);
        succList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // 设置启动AddBid的Intent
                Intent intent = new Intent(ChooseItem.this, AddBid.class);
                JSONObject jsonObj = (JSONObject) succList
                        .getAdapter().getItem(position);
                try
                {
                    // 将当前物品ID作为参数传给下一个Activity
                    intent.putExtra("itemId", jsonObj.getInt("id"));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }
}
