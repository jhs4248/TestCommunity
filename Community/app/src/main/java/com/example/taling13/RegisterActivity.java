package com.example.taling13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegisterNext; // 다음 버튼
    EditText enterId; // ID
    EditText enterPW; // PW
    EditText enterPW2; // PW 확인
    EditText enterNick; // 닉네임
    EditText enterNum; // 전화번호 인증

    String getId; // ID 저장
    String getPW; // PW 저장
    String getNick; // 닉네임 저장
    String getNum; // 전화번호 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //연동
        final FirebaseFirestore db = FirebaseFirestore.getInstance(); // FireBase 객체.

        btnRegisterNext = findViewById(R.id.btn_registerNext); // 다음 버튼
        enterId = findViewById(R.id.edt_enterID); // ID
        enterPW = findViewById(R.id.edt_enterPW); // PW
        enterPW2 = findViewById(R.id.edt_enterPW2); // PW 확인
        enterNick = findViewById(R.id.edt_enterNick); // 닉네임
        enterNum = findViewById(R.id.edt_enterNum); // 전화번호 인증

        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getId = enterId.getText().toString(); // 입력받은 ID를 저장.
                getPW = enterPW.getText().toString(); // 입력받은 PW를 저장.
                getNick = enterNick.getText().toString(); // 닉네임 저장.
                getNum = enterNum.getText().toString(); // 전화번호 저장.

                Map<String, Object> user = new HashMap<>(); // user 맵 객체 생성. user 배열에는 ID,PW,NICK,NUM이 있다.
                user.put("id", getId); // id
                user.put("pw", getPW); // pw
                user.put("nick", getNick); // nick
                user.put("num", getNum); // num

                db.collection("userInfo"). // userInfo collection 생성.
                        document(getId).set(user); // getId document 생성, user 데이터 추가함.

                Intent intent = new Intent(RegisterActivity.this,RegisterComplete.class);
                startActivity(intent);
                finish();
            }
        });
    }
}