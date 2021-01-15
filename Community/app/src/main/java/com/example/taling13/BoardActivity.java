package com.example.taling13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BoardActivity extends AppCompatActivity {

    // 레이아웃
    ImageView imgBoardImage; // 보드를 선택하면 나오는 이미지
    TextView tvBoard1; // 공지사항
    TextView tvBoard2; // 자유게시판
    TextView tvBoard3; // 이벤트
    ImageButton btnWtite; // 글쓰기 버튼

    // 게시판 이름
    String board1 = "공지 사항";
    String board2 = "자유 게시판";
    String board3 = "이벤트";

    // ID,PW
    String getId;
    String getPw;

    // 프래그먼트
    FragmentManager fragmentManager; // 프래그먼트 매니저
    FragmentTransaction fragmentTransaction; // 프래그먼트 전환
    OfficialBoardFragment officialBoardFragment; // 공지 사항 프래그먼트 클래스
    FreeBoardFragment freeBoardFragment; // 자유 게시판 프래그먼트 클래스
    EventFragment eventBoardFragment; // 이벤트 게시판 프래그먼트 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // 기본
        imgBoardImage = findViewById(R.id.img_boardImage); // 보드를 선택하면 나오는 이미지
        tvBoard1 = findViewById(R.id.tv_officialBoard); // 공지 사항
        tvBoard2 = findViewById(R.id.tv_freeBoard); // 자유 게시판
        tvBoard3 = findViewById(R.id.tv_eventBoard); // 이벤트
        btnWtite = findViewById(R.id.btn_write);  // 글쓰기 버튼

        // 메인 화면의 카테고리
        tvBoard1.setText(board1); // 공지 사항
        tvBoard2.setText(board2); // 자유 게시판
        tvBoard3.setText(board3); // 이벤트

        // ID PW 가져오기(WritePost, MainActivity에서 intent로 가져올 수 있다.)
        final Intent getIntent = getIntent();
        getId = getIntent.getStringExtra("id");
        getPw = getIntent.getStringExtra("pw");
        Log.i("TAGboard", "ID 검증:" + getId);
        Log.i("TAGboard", "PW 검증: " + getPw);

        // 첫 프래그먼트는 공지 사항.
        officialBoardFragment = new OfficialBoardFragment(); // 왜 만드는 지는 모르지만 null을 제외하기 위해 만든 것으로 추정.
        fragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저 권한 가져오기.
        fragmentTransaction = fragmentManager.beginTransaction(); // 프래그먼트 매니저 안의 전환 기능 가져오기.
        fragmentTransaction.add(R.id.v_boardcontainer, officialBoardFragment); // 프래그먼트 추가.
        fragmentTransaction.commit(); // 까먹으면 안되는 commit.

        // Bundle로 officialFragment에 Id값 전달, Activity가 없으면 따로 객체를 생성해서 보내야 한다.
        Bundle bundle = new Bundle();
        bundle.putString("id", getId);
        officialBoardFragment.setArguments(bundle);
        // https://everyshare.tistory.com/23

        // board1 터치 시 공시 사항
        tvBoard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                officialBoardFragment = new OfficialBoardFragment(); // 왜 만드는 지는 모르지만 null을 제외하기 위해 만든 것으로 추정.
                fragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저 권한 가져오기.
                fragmentTransaction = fragmentManager.beginTransaction(); // 프래그먼트 매니저 안의 전환 기능 가져오기.
                fragmentTransaction.replace(R.id.v_boardcontainer, officialBoardFragment); // 프래그먼트 추가.
                fragmentTransaction.commit(); // 까먹으면 안되는 commit.

                // Bundle로 officialFragment에 Id값 전달.
                Bundle bundle = new Bundle();
                bundle.putString("id", getId);
                officialBoardFragment.setArguments(bundle);
            }
        });

        // board2 터치 시 자유 게시판
        tvBoard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeBoardFragment = new FreeBoardFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.v_boardcontainer, freeBoardFragment).commit();

                // Bundle로 freeFragment에 Id값 전달.
                Bundle bundle = new Bundle();
                bundle.putString("id", getId);
                freeBoardFragment.setArguments(bundle);
            }
        });

        // board3 터치 시 이벤트 게시판
        tvBoard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBoardFragment = new EventFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.v_boardcontainer, eventBoardFragment).commit();

                // Bundle로 eventFragment에 Id값 전달.
                Bundle bundle = new Bundle();
                bundle.putString("id", getId);
                eventBoardFragment.setArguments(bundle);
            }
        });

        btnWtite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writePost();
            }
        });
    }

    public void writePost() { // 원래는 로그인 유지 여부 확인하는 거임.
        Intent intent = new Intent(BoardActivity.this, WritePost.class);
        intent.putExtra("id", getId);
        intent.putExtra("pw", getPw);
        startActivity(intent);
        finish();
    }
}

