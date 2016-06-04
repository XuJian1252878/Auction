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
 *         内容：用户个人信息界面
 */
public class MyInfo extends Activity
{
    ListView userInfoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        userInfoMenu = (ListView) findViewById(R.id.userInfoMenu);

        userInfoMenu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = null;
                switch ((int) id)
                {
                    case 0:
                        intent = new Intent(MyInfo.this, ViewBid.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MyInfo.this, ViewItem.class);
                        intent.putExtra("action", "product/deal_biding");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MyInfo.this, ViewItem.class);
                        intent.putExtra("action", "product/failed_biding");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MyInfo.this, Login.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
