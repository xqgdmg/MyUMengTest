package com.test.myumengtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.test.myumengtest.pac.ShareActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 *  测试友盟第三方分享
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         // 自己写分享页面，调用友盟的分享方法
        findViewById(R.id.tvGoToShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent(MainActivity.this,ShareActivity.class);
                startActivity(shareintent);
            }
        });

         // 调用 umeng 的分享面板
         // setDisplayList中设置的枚举参数就是最终分享面板中显示的平台，所传入参数的顺序即为最终面板分享平台的排列顺序
        findViewById(R.id.tvUmengShareBoard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(MainActivity.this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.ALIPAY,SHARE_MEDIA.SMS,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener).open();
            }
        });


    }


    /**
     *  调用 umeng 的分享面板，需要在 activity 中接收分享的结果
     *  注意不可在fragment中实现，如果在fragment中调用分享，在fragment依赖的Activity中实现，如果不实现onActivityResult方法，会导致分享或回调无法正常进行
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    /**
     *  umeng 的分享面板的监听
     * */
    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e("plat","platform"+share_media);

            Toast.makeText(MainActivity.this, share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(MainActivity.this,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(throwable != null){
                Log.e("throw","throw:"+throwable.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(MainActivity.this,share_media + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
