package com.example.bean.auctionclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bean.auctionclient.util.DialogUtil;
import com.example.bean.auctionclient.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewBidDetailActivity extends Activity
{

    ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid_detail);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        itemList = (ListView) findViewById(R.id.itemList);

        long productId = getIntent().getLongExtra("productId", -1);

        String url = HttpUtil.BASE_URL + "product/all_bid_" + productId;
        try
        {
            // 向指定URL发送请求
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            /*String result = "[{\n" +
                    "    \"bidId\" : 9,\n" +
                    "    \"kindName\" : \"食品\",\n" +
                    "    \"name\" : \"张三\",\n" +
                    "    \"describe\" : \"a test food desc\",\n" +
                    "    \"endDate\" : 1466222400000,\n" +
                    "    \"basicPrice\" : 99.99,\n" +
                    "    \"maxPrice\" : 999,\n" +
                    "    \"productTags\" : [ ]\n" +
                    "  }]";
            JSONArray jsonArray = new JSONArray(result);*/
            // 将服务器响应包装成Adapter
            JSONArrayAdapter adapter = new JSONArrayAdapter(this, jsonArray,
                    "userInfo", true);
            itemList.setAdapter(adapter);
        } catch (Exception e)
        {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
            e.printStackTrace();
        }
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                viewItemInBid(position);
            }
        });
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
        EditText itemRemark = (EditText) detailView.findViewById(R.id.itemRemark);
        Button endBtn = (Button) detailView.findViewById(R.id.endBid);
        // 获取被单击列表项所包装的JSONObject
        final JSONObject jsonObj = (JSONObject) itemList.getAdapter().getItem(
                position);
        endBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Map<String, String> map = new HashMap<String, String>();
                String bidId = null;
                try
                {
                    bidId = jsonObj.getString("bidId");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                map.put("bidId", bidId);
                String url = HttpUtil.BASE_URL + "bid/deal";
                try
                {
                    String result = HttpUtil.postRequest(url, map);
                    if (Boolean.getBoolean(result))
                    {
                        DialogUtil.showDialog(ViewBidDetailActivity.this, "交易成功！", true);
                    }
                    else
                    {
                        DialogUtil.showDialog(ViewBidDetailActivity.this, "交易成功！", true);
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                    DialogUtil.showDialog(ViewBidDetailActivity.this, "服务器响应出现异常，请重试！", false);
                }
            }
        });
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
        DialogUtil.showDialog(ViewBidDetailActivity.this, detailView);
    }
}
