package com.cheetahlou.careu.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.cheetahlou.careu.ProfileActivity;
import com.cheetahlou.careu.R;

import cn.leancloud.chatkit.LCChatKit;

/**
 * 登陆页面
 */
public class Login1Activity extends AppCompatActivity {

  protected EditText nameView;
  protected Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login1);

    nameView = (EditText) findViewById(R.id.activity_login_et_username);
    loginButton = (Button) findViewById(R.id.activity_login_btn_login);
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onLoginClick();
      }
    });
  }

  public void onLoginClick() {
    String clientId = nameView.getText().toString();
    Log.i("mycareu",clientId);
    if (TextUtils.isEmpty(clientId.trim())) {
      Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
      return;
    }

    LCChatKit.getInstance().open(clientId, new AVIMClientCallback() {
      @Override
      public void done(AVIMClient avimClient, AVIMException e) {
        if (null == e) {
          finish();
          Intent intent = new Intent(Login1Activity.this, ProfileActivity.class);
          startActivity(intent);
        } else {
          Toast.makeText(Login1Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
