package com.test.myumengtest.pac;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.myumengtest.MainActivity;
import com.test.myumengtest.R;
import com.test.myumengtest.adapter.ShareAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;

/**
 * Created by Chris on 2017/6/14.
 * 第一步，分享的列表（微信，支付宝，短信。。。。）
 */
public class ShareActivity extends Activity {
    private ListView listView;
    private ShareAdapter shareAdapter;
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umeng_share);

        initView();

        initPlatforms();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
        shareAdapter  = new ShareAdapter(this,platforms);
        listView.setAdapter(shareAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShareActivity.this,ShareDetailActivity.class);
                intent.putExtra("platform",platforms.get(position).mPlatform);
                ShareActivity.this.startActivity(intent);
            }
        });
    }

    private void initPlatforms(){
        platforms.clear();
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SMS.toSnsPlatform());

    }
}
