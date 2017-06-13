package com.demo.quickindexbar;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private List<Friend> friends = new ArrayList<>();
    private MyAdapter mMyAdapter;
    private QuickIndexBar mQuickIndexBar;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mLv = (ListView) findViewById(R.id.listview);
        mQuickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        mTv = (TextView) findViewById(R.id.tv_ledrawabletter);
        //初始化数据源
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));

        //排序
        Collections.sort(friends);
        //设置适配器
        mMyAdapter = new MyAdapter();
        mLv.setAdapter(mMyAdapter);
        //给quickIndexBar设置监听器
        mQuickIndexBar.onTouchChange(new QuickIndexBar.onTouchLetterChangerListener() {
            @Override
            public void onLetterChange(String letter) {
                //根据letter找到和friends的条目里相同的条目,并置顶
                for (int i = 0; i < friends.size(); i++) {
                    String first = friends.get(i).pinyin.charAt(0) + "";
                    if (first.equalsIgnoreCase(letter)) {
                        //把这个条目置顶
                        mLv.setSelection(i);
                        //找到过后立即中断
                        break;
                    }
                }
                //展示字母
                showLetter(letter);
            }
        });
    }
    private Handler mHandler = new Handler();
    /**展示字母
     * @param letter
     */
    private void showLetter(String letter) {
        //移除之前的任务
        mHandler.removeCallbacksAndMessages(null);
        mTv.setText(letter);
        mTv.setVisibility(View.VISIBLE);
        //延迟消失
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTv.setVisibility(View.GONE);
            }
        },2000);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return friends != null ? friends.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.letter = (TextView) convertView.findViewById(R.id.letter);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Friend friend = friends.get(position);
            //拿到首字母
            String firstLetter = friend.pinyin.charAt(0) + "";
            //不是第一个条目
            if (position > 0) {
                //上一个条目的首字母
                String lastLetter = friends.get(position - 1).pinyin.charAt(0) + "";
                if (firstLetter.equalsIgnoreCase(lastLetter)) {
                    //隐藏后面的条目的标题字母
                    viewHolder.letter.setVisibility(View.GONE);
                } else {
                    //不相等的话,显示.首先是复用的view,然后显示
                    viewHolder.letter.setVisibility(View.VISIBLE);
                    viewHolder.letter.setText(firstLetter);
                }
            } else {
                //是第一个条目,显示
                viewHolder.letter.setVisibility(View.VISIBLE);
                viewHolder.letter.setText(firstLetter);
            }
            //拿到姓名
            String name = friend.name;
            viewHolder.name.setText(name);
            return convertView;
        }
    }

    static class ViewHolder {
        TextView letter;
        TextView name;
    }
}



