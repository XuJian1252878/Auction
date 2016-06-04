package com.example.bean.auctionclient;

import android.app.LocalActivityManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Bean
 *         时间：2016年4月4日
 *         内容：主界面
 */

public class MainActivity extends FragmentActivity
{
    //打log时的TAG
    //private static final String TAG = "MainActivity";
    private ViewPager mPager;
    //private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private LocalActivityManager manager = null;
    private TextView tvTabActivity, tvTabGroups, tvTabFriends;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //取消app上方标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //获取系统存在资源
        resources = getResources();
        //初始化LocalActivityManager
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        ImageView headImage = (ImageView) findViewById(R.id.headImage);
        TextView userName = (TextView) findViewById(R.id.userName);

        //判断是否为游客
        if (Login.userID == 0)
        {
            headImage.setImageDrawable(resources.getDrawable(R.drawable.default_photo));
            userName.setText("游客");
        } else
        {
            headImage.setImageDrawable(resources.getDrawable(R.drawable.default_photo));
            userName.setText(Login.userName);
        }

        InitWidth();
        InitTextView();
        InitViewPager();
    }

    /**
     * 初始化滑动小块的宽度（距离）
     */
    private void InitWidth()
    {
        //获取滑动小块
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        //Log.d(TAG, "cursor imageview width=" + bottomLineWidth);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取屏幕的宽度
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 3.0 - bottomLineWidth) / 2);
        //Log.i("MainActivity", "offset=" + offset);

        //滑动的距离
        position_one = (int) (screenW / 3.0);
        position_two = position_one * 2;
    }

    /**
     * 获取滑动栏并设置监听事件
     */
    private void InitTextView()
    {
        tvTabActivity = (TextView) findViewById(R.id.tv_tab_activity);
        tvTabGroups = (TextView) findViewById(R.id.tv_tab_groups);
        tvTabFriends = (TextView) findViewById(R.id.tv_tab_friends);

        //设置监听事件
        tvTabActivity.setOnClickListener(new MyOnClickListener(0));
        tvTabGroups.setOnClickListener(new MyOnClickListener(1));
        tvTabFriends.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 设置ViewPager的滑动事件
     */
    private void InitViewPager()
    {
        mPager = (ViewPager) findViewById(R.id.vPager);

        final ArrayList<View> list = new ArrayList<View>();
        Intent intent = new Intent(MainActivity.this, ChooseKind.class);
        list.add(getView("A", intent));
        Intent intent2 = new Intent(MainActivity.this, Manage.class);
        list.add(getView("B", intent2));
        Intent intent3 = new Intent(MainActivity.this, MyInfo.class);
        list.add(getView("C", intent3));

        /*
        fragmentsList = new ArrayList<Fragment>();
        LayoutInflater mInflater = getLayoutInflater();
        View activityView = mInflater.inflate(R.layout.lay1, null);

        Fragment activityfragment = TestFragment.newInstance("Hello Activity.");
        Fragment groupFragment = TestFragment.newInstance("Hello Group.");
        Fragment friendsFragment = TestFragment.newInstance("Hello Friends.");

        fragmentsList.add(activityfragment);
        fragmentsList.add(groupFragment);
        fragmentsList.add(friendsFragment);
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        */

        mPager.setAdapter(new MyPagerAdapter(list));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 通过activity获取视图
     *
     * @param id
     * @param intent
     * @return
     */
    private View getView(String id, Intent intent)
    {
        //getDecorView()就是把intent里面的View给调出来
        return manager.startActivity(id, intent).getDecorView();
    }

    /**
     * Pager适配器
     */
    public class MyPagerAdapter extends PagerAdapter
    {
        List<View> list = new ArrayList<View>();

        public MyPagerAdapter(ArrayList<View> list)
        {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object)
        {
            ViewPager pViewPager = ((ViewPager) container);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1)
        {
            ViewPager pViewPager = ((ViewPager) arg0);
            pViewPager.addView(list.get(arg1));
            return list.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1)
        {

        }

        @Override
        public Parcelable saveState()
        {
            return null;
        }

        @Override
        public void startUpdate(View arg0)
        {
        }
    }

    /**
     * 设置滑动栏的点击事件
     */
    public class MyOnClickListener implements View.OnClickListener
    {
        private int index = 0;

        public MyOnClickListener(int i)
        {
            index = i;
        }

        @Override
        public void onClick(View v)
        {
            mPager.setCurrentItem(index);
        }
    }

    /**
     * 滑动事件
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageSelected(int arg0)
        {
            //实现滑块滑动的动画效果
            Animation animation = null;
            switch (arg0)
            {
                case 0:
                    if (currIndex == 1)
                    {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        tvTabGroups.setTextColor(resources.getColor(R.color.black));
                    } else if (currIndex == 2)
                    {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        tvTabFriends.setTextColor(resources.getColor(R.color.black));
                    }
                    tvTabActivity.setTextColor(resources.getColor(R.color.snow));
                    break;
                case 1:
                    if (currIndex == 0)
                    {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTabActivity.setTextColor(resources.getColor(R.color.black));
                    } else if (currIndex == 2)
                    {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        tvTabFriends.setTextColor(resources.getColor(R.color.black));
                    }
                    tvTabGroups.setTextColor(resources.getColor(R.color.snow));
                    break;
                case 2:
                    if (currIndex == 0)
                    {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        tvTabActivity.setTextColor(resources.getColor(R.color.black));
                    } else if (currIndex == 1)
                    {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        tvTabGroups.setTextColor(resources.getColor(R.color.black));
                    }
                    tvTabFriends.setTextColor(resources.getColor(R.color.snow));
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
    }
}
