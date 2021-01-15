package com.example.taling13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WritePost extends AppCompatActivity {

    LinearLayout container; // 다이어로그 뷰그룹
    EditText tvEditTitle; // 제목 입력
    TextView tvEditBoard; // 카테고리 설정
    EditText tvEditText; // 글 작성
    Button btnCompletePost; // 작성 완료
    ImageView btnFontSize; // 글자 크기 변경
    CheckBox checkSecret;
    EditText edtPostPW;

    String getId;
    String getPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        tvEditTitle = findViewById(R.id.tv_editTitle); // 제목 입력
        tvEditBoard = findViewById(R.id.tv_editBoard); // 카테고리 설정
        tvEditText = findViewById(R.id.edt_editText); // 글 작성
        btnFontSize = findViewById(R.id.btn_fontSize); // 글자 크기 변경
        btnCompletePost = findViewById(R.id.btn_completePost); // 작성 완료
        checkSecret = findViewById(R.id.checkSecret);
        edtPostPW = findViewById(R.id.edt_postPW);

        final FirebaseFirestore db = FirebaseFirestore.getInstance(); // Db 불러오기

        // 카테고리 선택 텍스트를 선택하면 boardDialog 불러오기
        tvEditBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardDialog();
            }
        });

        // 보내기 버튼을 누르면 게시글 업로드.
        btnCompletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ID와 PW 가져오기.
                Intent getIntent = getIntent();
                getId = getIntent.getStringExtra("id");
                getPw = getIntent.getStringExtra("pw");
                Log.i("TAGwritepost", "ID 검증:" + getId);
                Log.i("TAGwritepost", "PW 검증: " + getPw);

                // 비밀글 체크박스 값 가져오기
                if (checkSecret.isChecked()) {
                    final String getSecretPW = edtPostPW.getText().toString();
                    final String getType = "true";


                    // 제목, 내용, 카테고리 가져오기
                    final String getEditTitle = tvEditTitle.getText().toString(); // 제목을 저장
                    final String getEditText = tvEditText.getText().toString(); // 내용을 저장
                    final String getBoard = tvEditBoard.getText().toString();

                    // 날짜 가져오기
                    long now = System.currentTimeMillis(); // 기계 내에서 시간을 가져옴.
                    Date date = new Date(); // 기계 시간을 날짜 형식으로 변환.
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 포맷.
                    final String getTime = simpleDateFormat.format(date); // 날짜를 저장.

                    // 닉네임 가져오기
                    DocumentReference docRef = db.collection("userInfo").document(getId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            // 성공하면 닉네임을 받아오고, 실패하면 실패 로그를 전시
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String getNick = (String) documentSnapshot.getData().get("nick"); // 캐스트해서 닉네임을 저장.

                                if (getNick.equals(getNick)) { // 닉네임끼리 일치하면 해당 닉네임에 있는 내용들을 전시하기.(이런 방법보다 표준적인 방법이 있는 건가요?)
                                    Log.i("TAGwritepost", "닉네임 검증:" + getNick);
                                    Log.i("TAGwritepost", "제목 검증:" + getEditTitle);
                                    Log.i("TAGwritepost", "카테고리 검증:" + getBoard);
                                    Log.i("TAGwritepost", "날짜 검증:" + getTime);
                                    Log.i("TAGwritepost", "내용 검증:" + getEditText);
                                    Log.i("TAGwritepost", "비밀번호 검증: " + getSecretPW);
                                    Log.i("TAGwritepost", "비밀글 타입: " + getType);

                                    // postData 클래스를 생성하여 무한대로 생성할 저장소 생성. 리턴을 쓰지않고 클래스로 깔끔하게 대응.
                                    PostData postData = new PostData(getId, getSecretPW, getType, getEditTitle, getNick, getTime, getEditText, getBoard);

                                    if (getEditTitle.equals(null)) {
                                        Toast.makeText(WritePost.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                                    } else if (getEditText.equals(null)) {
                                        Toast.makeText(WritePost.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                                    } else if (getSecretPW.equals(null)) {
                                        Toast.makeText(WritePost.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();

                                    } else if (!getEditTitle.equals(null) && !getEditText.equals(null) || !getSecretPW.equals(null)) {
                                        // Map으로 객체화.
                                        Map<String, PostData> putPostData = new HashMap<>();
                                        putPostData.put(getEditTitle + ":" + getTime, postData); // 제목 + 시간으로 Key값을 설정하여 실시간 업데이트 및 확인이 가능하다.

                                        // 업로드 시 카테고리(collection), ID(document) field(포스트 정보)
                                        db.collection(getBoard).document(getId).set(putPostData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) { // set에 setOption 하면 업데이트된다.
                                                Toast.makeText(WritePost.this, "성공적으로 작성완료 하였습니다", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(WritePost.this, BoardActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }

                            } else {
                                Log.d("tag", "get failed with ", task.getException());
                            }
                        }
                    });


                } else {

                    final String getSecretPW;
                    final String getType = "false";

                    // 제목, 내용, 카테고리 가져오기
                    final String getEditTitle = tvEditTitle.getText().toString(); // 제목을 저장
                    final String getEditText = tvEditText.getText().toString(); // 내용을 저장
                    final String getBoard = tvEditBoard.getText().toString();

                    // 날짜 가져오기
                    long now = System.currentTimeMillis(); // 기계 내에서 시간을 가져옴.
                    Date date = new Date(); // 기계 시간을 날짜 형식으로 변환.
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 포맷.
                    final String getTime = simpleDateFormat.format(date); // 날짜를 저장.

                    // 닉네임 가져오기
                    DocumentReference docRef = db.collection("userInfo").document(getId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            // 성공하면 닉네임을 받아오고, 실패하면 실패 로그를 전시
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String getNick = (String) documentSnapshot.getData().get("nick"); // 캐스트해서 닉네임을 저장.

                                if (getNick.equals(getNick)) { // 닉네임끼리 일치하면 전시하기.(이런 방법보다 표준적인 방법이 있는 건가요?)
                                    Log.i("TAGwritepost", "닉네임 검증:" + getNick);
                                    Log.i("TAGwritepost", "제목 검증:" + getEditTitle);
                                    Log.i("TAGwritepost", "카테고리 검증:" + getBoard);
                                    Log.i("TAGwritepost", "날짜 검증:" + getTime);
                                    Log.i("TAGwritepost", "내용 검증:" + getEditText);

                                    // postData 클래스를 생성하여 무한대로 생성할 저장소 생성. 리턴을 쓰지않고 클래스로 깔끔하게 대응.
                                    PostData postData = new PostData(getId, "no", getType, getEditTitle, getNick, getTime, getEditText, getBoard);
//                                    postData.getPostData2(getId, getEditTitle, getNick, getTime, getEditText, getBoard);

                                    if (getEditTitle.equals(null)) {
                                        Toast.makeText(WritePost.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                                    } else if (getEditText.equals(null)) {
                                        Toast.makeText(WritePost.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                                    } else if (!getEditTitle.equals(null) && !getEditText.equals(null)) {

                                        // Map으로 객체화.
                                        Map<String, PostData> putPostData = new HashMap<>();
                                        putPostData.put(getEditTitle + ":" + getTime, postData); // 제목 + 시간으로 Key값을 설정하여 실시간 업데이트 및 확인이 가능하다.

                                        // 업로드 시 카테고리(collection), ID(document) field(포스트 정보)
                                        db.collection(getBoard).document(getId).set(putPostData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) { // set에 setOption 하면 업데이트된다.
                                                Toast.makeText(WritePost.this, "성공적으로 작성완료 하였습니다", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(WritePost.this, BoardActivity.class);
                                                intent.putExtra("id", getId);
                                                intent.putExtra("pw", getPw);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            } else {
                                Log.d("tag", "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }

    // 카테고리 선택 텍스트를 누르면 다이어로그 형식의 카테고리 선택.
    public void boardDialog() {

        final BoardActivity boardActivity = new BoardActivity(); // BoardActivity에 있는 게시판 이름들을 가져오기 위한 클래스.
        final AlertDialog.Builder builder = new AlertDialog.Builder(this); // 빌더
        final LayoutInflater layoutInflater = getLayoutInflater(); // 인플레이터

        final View view = layoutInflater.inflate(R.layout.select_board_dialog, container, false); // View 독립객체로 사용되는 아이콘들 객체 등록?

        // 카테고리 선택하는 체크 박스
        final CheckBox cbBoard1 = view.findViewById(R.id.cb_officialboard); // 공시 사항
        final CheckBox cbBoard2 = view.findViewById(R.id.cb_freeBoard); // 자유 게시판
        final CheckBox cbBoard3 = view.findViewById(R.id.cb_eventBoard); // 이벤트

        // 글 작성시 선택하는 카테고리
        cbBoard1.setText((CharSequence) boardActivity.board1); // board1에는 공지 사항 게시판 이름이 저장되어 있다.
        cbBoard2.setText((CharSequence) boardActivity.board2); // board2에는 자유 게시판 이름이 저장되어 있다.
        cbBoard3.setText((CharSequence) boardActivity.board3); // board2에는 이벤트 게시판 이름이 저장되어 있다.

        //cb1을 선택하면 나머지 박스 삭제.
        cbBoard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbBoard2.setChecked(false);
                cbBoard3.setChecked(false);
            }
        });

        //cb2을 선택하면 나머지 박스 삭제.
        cbBoard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbBoard1.setChecked(false);
                cbBoard3.setChecked(false);
            }
        });

        //cb3을 선택하면 나머지 박스 삭제.
        cbBoard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbBoard2.setChecked(false);
                cbBoard1.setChecked(false);
            }
        });

        // 확인
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override

            // 체크박스의 내용에 따라 카테고리가 카테고리 변경 텍스트에 적용.
            // 클릭할 경우에는 무조건 Listener로 불러야 함.
            public void onClick(DialogInterface dialog, int which) {
                if (cbBoard1.isChecked() == true) {
                    tvEditBoard.setText(boardActivity.board1);
                } else if (cbBoard2.isChecked() == true) {
                    tvEditBoard.setText(boardActivity.board2);
                } else if (cbBoard3.isChecked() == true) {
                    tvEditBoard.setText(boardActivity.board3);
                }
            }
        });

        // 취소
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setView(view);
        builder.show();
    }
}