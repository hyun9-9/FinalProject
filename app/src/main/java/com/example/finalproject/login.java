package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login extends AppCompatActivity {

    private EditText login_email, login_password;
    private Button login_button, join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login_email = findViewById( R.id.login_email );
        login_password = findViewById( R.id.login_password );

        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( login.this, join.class );
                startActivity( intent );
            }
        });


        login_button = findViewById( R.id.login_button );
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserEmail = login_email.getText().toString();
                String UserPwd = login_password.getText().toString();
                jsonRequest(UserEmail,UserPwd);


            }
        });
    }
    private void jsonRequest(String UserEmail,String UserPwd){
        String pwd=getHash(UserPwd);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    JSONObject data_json=jsonObject.getJSONObject("data");
                    JSONObject User_json=data_json.getJSONObject("user");
                    JSONObject Token_json=data_json.getJSONObject("token");

                    String Email=User_json.getString("email");


                    if(UserEmail.equals(Email)) {//로그인 성공시

                        String access_token=Token_json.getString("access_token");
                        String refresh_token=Token_json.getString("refresh_token");
                        String User_mobile = User_json.getString( "mobile" );
                        String UserName = User_json.getString( "name" );

                        Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT ).show();
                        Intent intent = new Intent( login.this, MainActivity.class );

                        intent.putExtra( "UserEmail", Email );
                        intent.putExtra( "User_mobile", User_mobile );
                        intent.putExtra( "UserName", UserName );
                        intent.putExtra( "access_token", access_token );
                        intent.putExtra( "refresh_token", refresh_token );
                        startActivity( intent );

                    } else {//로그인 실패시
                        Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                        return;
                    }

                } catch (JSONException e) {
                    jsonRequest(UserEmail,UserPwd);
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest( UserEmail, pwd, responseListener );
        RequestQueue queue = Volley.newRequestQueue( login.this );
        queue.add( loginRequest );
    }
    public String getHash(String str) {
        String digest = "";
        try{

            //암호화
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); // SHA-256 해시함수를 사용
            sh.update(str.getBytes()); // str의 문자열을 해싱하여 sh에 저장
            byte byteData[] = sh.digest(); // sh 객체의 다이제스트를 얻는다.

            //얻은 결과를 string으로 변환
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            digest = sb.toString();
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace(); digest = null;
        }
        return digest;
    }
}