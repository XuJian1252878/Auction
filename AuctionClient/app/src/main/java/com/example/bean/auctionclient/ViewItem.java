package com.example.bean.auctionclient;


import com.example.bean.auctionclient.util.HttpUtil;
import com.example.bean.auctionclient.util.DialogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ViewItem extends Activity
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

        if (Login.userID == 0)
        {
            DialogUtil.showDialog(ViewItem.this, "请您先登陆。", true);
        }
        else
        {
            succList = (ListView) findViewById(R.id.succList);
            viewTitle = (TextView) findViewById(R.id.view_titile);
            String action = getIntent().getStringExtra("action");
            Map<String, String> map = new HashMap<String, String>();
            map.put("userID", Integer.toString(Login.userID));
            // 定义发送请求的URL
            String url = HttpUtil.BASE_URL + action;
            // 如果是查看流拍物品，修改标题
            if (action.equals("product/failed_biding"))
            {
                viewTitle.setText(R.string.view_fail);
            }
            try
            {
                // 向指定URL发送请求，并把服务器响应转换成JSONArray对象
                JSONArray jsonArray = new JSONArray(HttpUtil.postRequest(url, map));
                /*String result = "[{\n" +
                        "    \"id\" : 9,\n" +
                        "    \"name\": \"productname\",\n" +
                        "    \"describe\": \"productdesc\",\n" +
                        "    \"categoryName\": \"categoryName\",\n" +
                        "    \"maxBid\": \"maxBid\",\n" +
                        "    \"myBid\": \"myBid\",\n" +
                        "    \"bidId\": \"bidId\"\n" +
                        "  }]";
                JSONArray jsonArray = new JSONArray(result);*/
                // 将JSONArray包装成Adapter
                JSONArrayAdapter adapter = new JSONArrayAdapter(this
                        , jsonArray, "name", true);
                succList.setAdapter(adapter);
            } catch (Exception e)
            {
                DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
                e.printStackTrace();
            }
            succList.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)
                {
                    // 查看指定物品的详细情况。
                    viewItemDetail(position);
                }
            });
        }
    }

    private void viewItemDetail(int position)
    {
        // 加载detail.xml界面布局代表的视图
        View detailView = getLayoutInflater().inflate(R.layout.activity_detail, null);
        // 获取detail.xml界面布局中的文本框
        EditText itemName = (EditText) detailView
                .findViewById(R.id.itemName);
        EditText itemKind = (EditText) detailView
                .findViewById(R.id.itemKind);
        EditText maxPrice = (EditText) detailView
                .findViewById(R.id.maxPrice);
        EditText itemRemark = (EditText) detailView
                .findViewById(R.id.itemRemark);
        // 获取被单击的列表项
        JSONObject jsonObj = (JSONObject) succList.getAdapter().getItem(
                position);
        try
        {
            // 通过文本框显示物品详情
            itemName.setText(jsonObj.getString("name"));
            itemKind.setText(jsonObj.getString("categoryName"));
            maxPrice.setText(jsonObj.getString("maxBid"));
            itemRemark.setText(jsonObj.getString("desc"));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        DialogUtil.showDialog(ViewItem.this, detailView);
    }
}
