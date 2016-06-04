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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bean
 *         时间：2016年4月4日
 *         内容：添加拍卖商品
 */
public class ManageItem extends Activity
{
    Button bnHome, bnAdd;
    ListView itemList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_item);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        if (Login.userID == 0)
        {
            DialogUtil.showDialog(ManageItem.this, "请您先登陆。", true);
        } else
        {
            bnAdd = (Button) findViewById(R.id.bnAdd);
            itemList = (ListView) findViewById(R.id.itemList);
            bnAdd.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View source)
                {
                    // 启动AddItem Activity。
                    Intent intent = new Intent(ManageItem.this, AddItem.class);
                    startActivity(intent);
                }
            });
            Map<String, String> map = new HashMap<String, String>();
            map.put("userID", Integer.toString(Login.userID));
            // 定义发送请求的URL
            String url = HttpUtil.BASE_URL + "product/user_upload";
            try
            {
                // 向指定URL发送请求
                JSONArray jsonArray = new JSONArray(HttpUtil.postRequest(url, map));
                /*String result =  "[{\n" +
                        "    \"id\" : 9,\n" +
                        "    \"kindName\" : \"食品\",\n" +
                        "    \"name\" : \"a test food name\",\n" +
                        "    \"describe\" : \"a test food desc\",\n" +
                        "    \"endDate\" : 1466222400000,\n" +
                        "    \"basicPrice\" : 99.99,\n" +
                        "    \"maxPrice\" : 999,\n" +
                        "    \"productTags\" : [ ]\n" +
                        "  }]";
                JSONArray jsonArray = new JSONArray(result);*/
                // 将服务器响应包装成Adapter
                JSONArrayAdapter adapter = new JSONArrayAdapter(this, jsonArray,
                        "name", true);
                itemList.setAdapter(adapter);
            } catch (Exception e)
            {
                DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
                e.printStackTrace();
            }
            itemList.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)
                {
                    // viewItemInBid(position);
                    Intent intent = new Intent(ManageItem.this
                            , ViewBidDetailActivity.class);
                    // 将种类ID作为额外参数传过去
                    intent.putExtra("productId", id);
                    startActivity(intent);
                }
            });
        }

    }

    private void viewItemInBid(int position)
    {
        // 加载detail_in_bid.xml界面布局代表的视图
        View detailView = getLayoutInflater().inflate(R.layout.detail_in_bid,
                null);
        // 获取detail_in_bid.xml界面中的文本框
        EditText itemName = (EditText) detailView.findViewById(R.id.itemName);
        EditText itemKind = (EditText) detailView.findViewById(R.id.itemKind);
        EditText maxPrice = (EditText) detailView.findViewById(R.id.maxPrice);
        EditText initPrice = (EditText) detailView.findViewById(R.id.initPrice);

        EditText endTime = (EditText) detailView.findViewById(R.id.endTime);
        EditText itemRemark = (EditText) detailView
                .findViewById(R.id.itemRemark);
        // 获取被单击列表项所包装的JSONObject
        JSONObject jsonObj = (JSONObject) itemList.getAdapter().getItem(
                position);
        try
        {
            // 通过文本框显示物品详情
            itemName.setText(jsonObj.getString("name"));
            itemKind.setText(jsonObj.getString("kindName"));
            maxPrice.setText(jsonObj.getString("maxPrice"));
            itemRemark.setText(jsonObj.getString("describe"));
            initPrice.setText(jsonObj.getString("basicPrice"));
            Date time = new Date(Long.parseLong(jsonObj.getString("endDate")));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            endTime.setText(dateString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        DialogUtil.showDialog(ManageItem.this, detailView);
    }
}
