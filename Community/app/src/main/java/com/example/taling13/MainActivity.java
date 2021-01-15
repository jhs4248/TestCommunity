package com.example.taling13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText edtId; // 아이디 입력
    EditText edtPW; // 비밀번호 입력
    Button btnLogin; // 로그인 버튼
    Button btnRegister; // 가입 버튼
    TextView tvCheck; // 아이디 및 비밀번호가 일치한지 체크해주는 변수.
    String loginGetID; // 입력받은 ID
    String loginGetPW; // 입력받은 PW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseFirestore db = FirebaseFirestore.getInstance(); // FireBase 객체 생성.

        edtId = findViewById(R.id.edt_id);
        edtPW = findViewById(R.id.edt_pw);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        tvCheck = findViewById(R.id.tv_checkIDPW);

        tvCheck.setText(""); // IDPW 체커 메세지 비활성화.

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, policy.class);
                startActivity(intent);
            }
        });

        // 로그인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginGetID = edtId.getText().toString(); // 입력된 ID를 저장.
                loginGetPW = edtPW.getText().toString(); // 입력된 PW를 저장.
                Log.i("TAGmain", "ID 검증:" + loginGetID);
                Log.i("TAGmain", "PW 검증:" + loginGetPW);

                // 로그인 검증
                db.collection("userInfo") // userInfo의 Collection 호출하기.
                        .get() // 받아오기.
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) { // document의 길이만큼 document의 정보를 가져온다.(userInfo의 모든 정보들)

                                    // 반복되는 값은 변수에 빼놓자
                                    // 반복되는 코드는 메소드로 빼놓자
                                    String serverId = document.getId();
                                    String serverPW = (String)document.getData().get("pw");

                                    if (!serverId.equals(loginGetID) && !serverPW.equals(loginGetPW)) { // 따로 추가해줘야 뜨지 않음.
                                        tvCheck.setText("아이디 및 비밀번호가 일치하지 않습니다."); // VISIBLE
                                    }

                                    // ID, PW가 일치하면 메인 화면으로, 아니면 로그인 취소.
                                    if (serverId.equals(loginGetID) && serverPW.equals(loginGetPW)) { // getId.get()는 userInfo의 docment 정보를 가져오고, getData.get("필드 이름")는 field의 정보를 가져온다.
                                        tvCheck.setText("");
                                        Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                                        intent.putExtra("id", loginGetID); // 내가 보기에 intent를 실행하면 여기 데이터들은 다른데에서 null이므로 Extra를 해야 하는 듯.
                                        intent.putExtra("pw", loginGetPW);
                                        startActivity(intent);
                                        finish();
                                    }
                                    // DB에서 검증하는 지연시간이 있으믈 맞는지 먼저 확인하게 만들면 메세지 없이 가능하다.
                                }
                            }
                        });
            }
        });
    }

    // ID와 PW가 필요한 클래스에서 불러오기.
    public String getId() {
        String getId = this.loginGetID;
        return getId;
    }

    public String getPw() {
        String getPw = this.loginGetPW;
        return getPw;
    }
}