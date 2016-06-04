package com.example.bean.auctionclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Bean
 *         时间：2016年4月4日
 *         内容：添加拍卖商品
 */
public class Manage extends Activity
{
    ListView manageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        manageMenu = (ListView) findViewById(R.id.manageMenu);
        //为ListView的各项绑定单击事件监听器
        manageMenu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = null;
                switch ((int) id)
                {
                    case 0:
                        intent = new Intent(Manage.this, ManageItem.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
