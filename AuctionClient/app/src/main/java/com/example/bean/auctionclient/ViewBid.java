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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ViewBid extends Activity
{
    ListView bidList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        if (Login.userID == 0)
        {
            DialogUtil.showDialog(ViewBid.this, "请您先登陆。", true);
        } else
        {
            bidList = (ListView) findViewById(R.id.bidList);
            Map<String, String> map = new HashMap<String, String>();
            map.put("userID", Integer.toString(Login.userID));
            // 定义发送请求的URL
            String url = HttpUtil.BASE_URL + "product/goingon_biding";
            try
            {
                // 向指定URL发送请求，并把服务器响应包装成JSONArray对象
                JSONArray jsonArray = new JSONArray(HttpUtil.postRequest(url, map));
                /*String result = "[{\n" +
                        "    \"id\" : 9,\n" +
                        "    \"name\": \"productname\",\n" +
                        "    \"desc\": \"productdesc\",\n" +
                        "    \"categoryName\": \"categoryName\",\n" +
                        "    \"maxBid\": \"maxBid\",\n" +
                        "    \"myBid\": \"myBid\",\n" +
                        "    \"bidId\": \"bidId\",\n" +
                        "\t\"endDate\": 1466222400000,\n" +
                        "\t\"bidDate\": 1466222403333\n" +
                        "  }]";
                JSONArray jsonArray = new JSONArray(result);*/
                // 将JSONArray包装成Adapter
                JSONArrayAdapter adapter = new JSONArrayAdapter(this, jsonArray,
                        "name", true);
                bidList.setAdapter(adapter);
            } catch (Exception e)
            {
                DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
                e.printStackTrace();
            }
            bidList.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)
                {
                    // 查看竞价详情
                    viewBidDetail(position);
                }
            });
        }
    }

    private void viewBidDetail(int position)
    {
        // 加载bid_detail.xml界面布局代表的视图
        View detailView = getLayoutInflater()
                .inflate(R.layout.bid_detail, null);
        //Button btnAdd = (Button) findViewById(R.id.bnAdd);

        // 获取bid_detail界面中的文本框
        EditText itemName = (EditText) detailView
                .findViewById(R.id.itemName);
        EditText bidPrice = (EditText) detailView
                .findViewById(R.id.bidPrice);
        EditText bidTime = (EditText) detailView
                .findViewById(R.id.bidTime);
        EditText bidUser = (EditText) detailView
                .findViewById(R.id.bidUser);
        //final EditText bidNewPrice = (EditText) detailView
        //       .findViewById(R.id.bidNewPrice);
        // 获取被单击项目所包装的JSONObject
        final JSONObject jsonObj = (JSONObject) bidList.getAdapter()
                .getItem(position);
        /*btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String bidPrice = bidNewPrice.getText().toString();
                try
                {
                    // 使用Map封装请求参数
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("bidId", jsonObj.getString("bidId"));
                    map.put("newPrice", bidPrice);
                    // 定义发送请求的URL
                    String url = HttpUtil.BASE_URL + "product/upload";
                    // 发送请求
                    String result = HttpUtil.postRequest(url, map);

                    if (Boolean.getBoolean(result))
                    {
                        DialogUtil.showDialog(AddItem.this, "添加拍卖物品成功！", true);
                    }
                    else
                    {
                        DialogUtil.showDialog(AddItem.this, "添加拍卖物品失败！", true);
                    }

                } catch (Exception e)
                {
                    DialogUtil.showDialog(AddItem.this
                            , "服务器响应异常，请稍后再试！", false);
                    e.printStackTrace();
                }
            }
        });*/

        try
        {
            // 使用文本框来显示竞价详情。
            itemName.setText(jsonObj.getString("name"));
            bidPrice.setText(jsonObj.getString("myBid"));
            Date time = new Date(Long.parseLong(jsonObj.getString("bidDate")));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            bidTime.setText(dateString);
            time = new Date(Long.parseLong(jsonObj.getString("endDate")));
            dateString = formatter.format(time);
            bidUser.setText(dateString);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        DialogUtil.showDialog(ViewBid.this, detailView);
    }
}
