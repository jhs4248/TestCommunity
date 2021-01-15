package com.example.taling13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TouchPost extends AppCompatActivity {

    TextView tvPostBoard; // 카테고리

    TextView tvPostNick; // 닉네임
    TextView tvPostTime; // 시간
    TextView tvPostTitle; // 제목
    TextView tvPostText; // 내용

    TextView tvContract; // 조회수
    TextView tvComment; // 댓글
    TextView tvLove; // 좋아요
    ListView commentView; // 댓글의 리스트 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_post);

        tvPostBoard = findViewById(R.id.tv_postBoard);
        tvPostNick = findViewById(R.id.tv_postNick);
        tvPostTime = findViewById(R.id.tv_postTime);
        tvPostTitle = findViewById(R.id.tv_postTitle);
        tvPostText = findViewById(R.id.tv_postText);
        tvContract = findViewById(R.id.tv_contract);
        tvLove = findViewById(R.id.tv_love);

        Intent intent = getIntent();
        String getTitle = intent.getStringExtra("getTitle");
        String getTime = intent.getStringExtra("getDate");
        String getNick = intent.getStringExtra("getNick");
        String getText = intent.getStringExtra("getText");
        String getBoard = intent.getStringExtra("getBoard");
        int getContract = intent.getIntExtra("getContract",0);
        int getLove = intent.getIntExtra("getLove",0);

        tvPostTitle.setText(getTitle);
        tvPostTime.setText(getTime);
        tvPostNick.setText(getNick);
        tvPostText.setText(getText);
        tvPostBoard.setText(getBoard);
        tvContract.setText(String.valueOf(getContract));
        tvLove.setText(String.valueOf(getLove));

        PostData postData = new PostData();
        postData.contract = getContract;
        postData.love = getLove;

        Map<String, PostData> putPostData = new HashMap<>();
        putPostData.put(getTitle + ":" + getTime, postData);
        Log.i("purht",getTitle+" "+getTime+" "+postData+" "+getBoard);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection(getBoard).document(getId).set(putPostData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        });
    }
}