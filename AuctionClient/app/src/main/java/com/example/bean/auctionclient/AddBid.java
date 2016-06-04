package com.example.bean.auctionclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.bean.auctionclient.util.DialogUtil;
import com.example.bean.auctionclient.util.HttpUtil;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Bean
 *         时间：2016年4月29日
 *         内容：添加竞价
 */
public class AddBid extends Activity
{
    // 定义界面中文本框
    EditText itemName, itemDesc, itemRemark, itemKind, initPrice, maxPrice, endTime, bidPrice;
    // 定义界面中两个按钮
    Button bnAdd, bnCancel;
    // 定义当前正在拍卖的物品
    JSONObject jsonObj;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        // 获取界面中编辑框
        itemName = (EditText) findViewById(R.id.itemName);
        itemDesc = (EditText) findViewById(R.id.itemDesc);
        itemRemark = (EditText) findViewById(R.id.itemRemark);
        itemKind = (EditText) findViewById(R.id.itemKind);
        initPrice = (EditText) findViewById(R.id.initPrice);
        //maxPrice = (EditText) findViewById(R.id.maxPrice);
        endTime = (EditText) findViewById(R.id.endTime);
        bidPrice = (EditText) findViewById(R.id.bidPrice);
        // 获取界面中的两个按钮
        bnAdd = (Button) findViewById(R.id.bnAdd);
        bnCancel = (Button) findViewById(R.id.bnCancel);
        // 为取消按钮的单击事件绑定事件监听器
        bnCancel.setOnClickListener(new FinishListener(this));
        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "product/"
                + getIntent().getIntExtra("itemId", -1);
        try
        {
            // 获取指定的拍卖物品
            jsonObj = new JSONObject(HttpUtil.getRequest(url));
            /*String result = "{\n" +
                    "    \"id\" : 9,\n" +
                    "    \"kindName\" : \"食品\",\n" +
                    "    \"name\" : \"a test food name\",\n" +
                    "    \"describe\" : \"a test food desc\",\n" +
                    "    \"endDate\" : 1466222400000,\n" +
                    "    \"basicPrice\" : 99.99,\n" +
                    "    \"maxPrice\" : 999,\n" +
                    "    \"productTags\" : [ ]\n" +
                    "  }";
            jsonObj = new JSONObject(result);*/
            // 使用文本框来显示拍卖物品的详情
            itemName.setText(jsonObj.getString("name"));
            itemDesc.setText(jsonObj.getString("describe"));
            itemRemark.setText(jsonObj.getString("productTags"));
            itemKind.setText(jsonObj.getString("kindName"));
            initPrice.setText(jsonObj.getString("basicPrice"));
            //maxPrice.setText(jsonObj.getString("maxPrice"));
            Date time = new Date(Long.parseLong(jsonObj.getString("endDate")));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            endTime.setText(dateString);
        } catch (Exception e1)
        {
            DialogUtil.showDialog(AddBid.this, "服务器响应出现异常！", false);
            e1.printStackTrace();
        }
        bnAdd.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (Login.userID == 0)
                {
                    DialogUtil.showDialog(AddBid.this, "请您登陆后再参与竞价。", false);
                } else
                {
                    try
                    {
                        // 执行类型转换
                        double curPrice = Double.parseDouble(
                                bidPrice.getText().toString());
                        // 执行输入校验
                        if (curPrice < jsonObj.getDouble("basicPrice"))
                        {
                            DialogUtil.showDialog(AddBid.this, "您输入的竞价必须高于当前竞价", false);
                        } else
                        {
                            // 添加竞价
                            String result = addBid(jsonObj.getString("id")
                                    , curPrice + "");
                            if (Boolean.getBoolean(result))
                            {
                                // 显示对话框
                                DialogUtil.showDialog(AddBid.this, "竞价成功！", true);
                            }
                            else
                            {
                                DialogUtil.showDialog(AddBid.this, "竞价成功！", true);
                            }

                        }
                    } catch (NumberFormatException ne)
                    {
                        DialogUtil.showDialog(AddBid.this, "您输入的竞价必须是数值", false);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        DialogUtil.showDialog(AddBid.this, "服务器响应出现异常，请重试！", false);
                    }
                }
            }
        });
    }

    private String addBid(String itemId, String bidPrice)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<String, String>();
        map.put("itemID", itemId);
        map.put("bidPrice", bidPrice);
        map.put("userID", Integer.toString(Login.userID));
        //还需要传送当前用户id
        // 定义请求将会发送到addKind.jsp页面
        String url = HttpUtil.BASE_URL + "bid/commit";
        // 发送请求
        return HttpUtil.postRequest(url, map);
    }
}