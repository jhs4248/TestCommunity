package com.example.taling13;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficialBoardFragment extends Fragment {

    ArrayList<BoardData> data;
    String loginId;
    Context context;

    ArrayList<BoardData> dataList;
    ListView listView;
    ListAdapter listAdapter;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        context = getContext();
        dataList= new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        listAdapter = new ListAdapter(); // 어댑터 연결
        final View v = inflater.inflate(R.layout.official_board_fragment, container, false);
        listView = v.findViewById(R.id.listView_official);

        // BoardActivity에서 ID를 Bundle로 가져오기.
        Bundle bundle = getArguments();
        loginId = bundle.getString("id");


        db.collection("공지 사항").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        // 가져온 값들을 임시 리스트에 전부 넣어둠

                        // 파이어스토에서 공지사항 글 전부 읽어오기


                        // 로그 찍을 떄 팁
                        // Map이나 ArrayList를 로그로 찍고싶다면  변수명.toString()
                        Map<String, Object> forms = snapshot.getData();

                        // 읽어온 값들을 time값으로 정렬하기 (내림차순)
                        for (Map.Entry<String, Object> form : forms.entrySet()) {
                            String key = (String) form.getKey();
                            Map<Object, Object> values = (Map<Object, Object>) form.getValue();
                            String ID = (String) values.get("ID");
                            final String title = (String) values.get("title");
                            String time = (String) values.get("time");
                            String nick = (String) values.get("nick");
                            String text = (String) values.get("text");
                            String board = (String) values.get("board");
                            String contract = String.valueOf(values.get("contract"));
                            String love = String.valueOf(values.get("love"));
                            String pw = (String) values.get("PW");
                            final String type = (String) values.get("Type");

                            Log.i("ALL_DATAS",
                                    "ID: " + ID +
                                            "제목:" + title +
                                            "/시간:" + time +
                                            "/닉네임:" + nick +
                                            "/카테고리:" + board +
                                            "/내용:" + text +
                                            "/조회수:" + contract +
                                            "/좋아요:" + love);

                            int convertContract = Integer.parseInt(contract);
                            int convertLove = Integer.parseInt(love);


                            BoardData getDataList = new BoardData(ID, pw, type, title, time, nick, text, board, convertContract, convertLove); // BoardData 객체를 만들어서 가져온 값들을 넣는다. 생성자로 추가해서 매번 새로 생성한다.
                            dataList.add(getDataList); // Array를 만들어서 BoardData 객체를 넣는다. 그리고 다시 데이터를 받아오고 넣는 행위를 반복한다.
                        }
                    }

                    boardSort();
                    goSetting();
                }
            }
        });
        return v;
    }

    private void boardSort() {
        //  dataList 에서 time값들 기준으로 정렬 진행
        // ArrayList에서 객체들 뽑아오는데, time만 출력해보기

        // 실제 정렬

//        Comparator<String> cmpAsc = new Comparator<String>() {
//
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2) ;
//            }
//        } ;

        // ArrayList를 정렬할 때 사용하는 방법


        Comparator<BoardData> cpmAsc = new Comparator<BoardData>() { // 비교할 타입을 넣는다.
            @Override
            public int compare(BoardData o1, BoardData o2) { //
                return o2.getDate().compareTo(o1.getDate()); // 매개변수로 BoardDat고, 그 안에 있는 getDate를 통해 날짜를 받아온다.
            }
        };

        Collections.sort(dataList, cpmAsc); // ?

        for(int i = 0; i<5; i++){ // 위에서 반목문으로 데이터를 받아오고 추가했다면, 따로 반복문을 만들어서 ListView를 생성한다.
            BoardData data = dataList.get(i);
            Log.i("Board Sort",data.getDate());
        }

    }

    private void goSetting() {
        // String으로 가져온 조회수와 좋아요 수를 정수형으로 변환(오류로 인한 추가)

        // https://stackoverrun.com/ko/q/13131666

        // 해당 내용을 List에 전시하고 데이터를 저장한다.
//                            final BoardData boardData = new BoardData();
//                            boardData.ID = ID;
//                            boardData.title = title;
//                            boardData.date = time;
//                            boardData.name = nick;
//                            boardData.text = text;
//                            boardData.board = board;
//                            boardData.contract = convertContract;
//                            boardData.love = convertLove;
//                            boardData.PW = pw;
//                            boardData.type = type;
//        listAdapter.addData(boardData);
//        listView.setAdapter(listAdapter);
//
//
        for(int i=0; i<dataList.size(); i++) {
            BoardData data = dataList.get(i);
            listAdapter.addData(data);
        }
        listView.setAdapter(listAdapter);

        // ListView의 아이템을 터치하면 비밀글 유무 체크하고 전시함.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<BoardData> getAdapterData = listAdapter.saveDataFromListAdapter; // 우선은 Adapter에 있는 배열 데이터를 여기로 가져온다.
                final BoardData getDataInfo = getAdapterData.get(position); // position(터치하면 숫자로 나오는 list의 번호)로 배열의 위치를 가져온 다음, 그 데이터들을 boardData에 저장한다.
                //다시 말하면 어댑터(Array)를 현재의 다른 Array에 저장하고, 그 Array를 position 값을 넣어서 그 것을 가져오는 것이다. get 메소드는 숫자를 넣어서 해당위치의 배열 값을 가져오는 것.
                // 그리고 그 값들은 전부 다 BoardData 클래스로 저장되어 있고 그걸 다시 풀어서 String로 Return하는 것이다.

                final String getType = getDataInfo.getType(); // 비밀글을 체크할 타입.
                final String getPW = getDataInfo.getPW(); // 비밀글에 사용된 비밀번호.
                Log.i("TAGofficial", "비밀글 비번: " + getPW + "/" + "타입: " + getType);


                // 비밀글
                if (getType.equals("true")) { // true면 비밀글 생성.

                    // 빌더 레이아웃 생성
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final AlertDialog ad = builder.create(); // https://m.blog.naver.com/PostView.nhn?blogId=wlsdml1103&logNo=220592626399&proxyReferer=https:%2F%2Fwww.google.com%2F

                    // 비밀번호를 입력할 수 있는 인플레이터.
                    LayoutInflater inflater = getLayoutInflater();
                    LinearLayout laySecretContainer = view.findViewById(R.id.lay_secretContainer);
                    view = inflater.inflate(R.layout.secret_dialog, laySecretContainer, false);
                    ad.setView(view);

                    final Button btnSecretOk = view.findViewById(R.id.btn_secretOk);
                    final EditText edtSecretPw = view.findViewById(R.id.edt_secretPw);

                    // 확인 버튼을 누르면 일치할 경우 포스트 전시, 다르면 그대로.
                    btnSecretOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String getSecretPw = edtSecretPw.getText().toString();
                            if (getSecretPw.equals(getPW)) {
                                ad.dismiss();

                                String getId = getDataInfo.getID();
                                String getTitle = getDataInfo.getTitle();
                                String getDate = getDataInfo.getDate();
                                String getNick = getDataInfo.getName();
                                String getText = getDataInfo.getText();
                                String getBoard = getDataInfo.getBoard();
                                int getContract = getDataInfo.getContract();
                                int getLove = getDataInfo.getLove();

                                getContract = getContract + 1;

                                PostData postData = new PostData(getId, getPW, getType, getTitle, getNick, getDate, getText, getBoard);//                                    postData.getPostData(getId,getTitle, getNick, getDate, getText, getBoard);
                                postData.contract = getContract;
                                postData.love = getLove;
                                Map<String, PostData> putPostData = new HashMap<>();
                                putPostData.put(getTitle + ":" + getDate, postData); // 제목 + 시간으로 Key값을 설정하여 실시간 업데이트 및 확인이 가능하다.

                                db.collection("공지 사항").document(getId).set(putPostData, SetOptions.merge());

                                Intent intent = new Intent(context, TouchPost.class);
                                intent.putExtra("getTitle", getTitle);
                                intent.putExtra("getDate", getDate);
                                intent.putExtra("getNick", getNick);
                                intent.putExtra("getText", getText);
                                intent.putExtra("getBoard", getBoard);
                                intent.putExtra("getContract", getContract);
                                intent.putExtra("getLove", getLove);
                                startActivity(intent);

                                Log.i("TAGofficial", "타이틀: " + getTitle + "/날짜: " + getDate + "/카테고리: " + getBoard + "/닉네임: " + getNick + "/내용: " + getText);

                            } else if (!getSecretPw.equals(getPW)) {
                                Toast.makeText(context, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    ad.show();
                    // 보통 Activty에서 DialogBuilder를 사용하면 따로 메소드를 뽑아서 사용해야 한다.
                    // 하지만 Activity가 아닌 것은 Context 객체를 따로 불러와서 가능하다.
                    // 이유는 액티비티에는 이미 View가 같이 있기 때문에 그 View를 불러오게 되면 이상한 화면을 불러일으키는 것이라고 생각된다.

                } else { // 일반글
                    String getId = getDataInfo.getID();
                    String getTitle = getDataInfo.getTitle();
                    String getDate = getDataInfo.getDate();
                    String getNick = getDataInfo.getName();
                    String getText = getDataInfo.getText();
                    String getBoard = getDataInfo.getBoard();
                    int getContract = getDataInfo.getContract();
                    int getLove = getDataInfo.getLove();

                    getContract = getContract + 1;

                    PostData postData = new PostData(getId, getPW, getType, getTitle, getNick, getDate, getText, getBoard);//                                    postData.getPostData(getId,getTitle, getNick, getDate, getText, getBoard);
                    postData.contract = getContract;
                    postData.love = getLove;
                    Map<String, PostData> putPostData = new HashMap<>();
                    putPostData.put(getTitle + ":" + getDate, postData); // 제목 + 시간으로 Key값을 설정하여 실시간 업데이트 및 확인이 가능하다.

                    db.collection("공지 사항").document(getId).set(putPostData, SetOptions.merge());

                    Intent intent = new Intent(context, TouchPost.class);
                    intent.putExtra("getTitle", getTitle);
                    intent.putExtra("getDate", getDate);
                    intent.putExtra("getNick", getNick);
                    intent.putExtra("getText", getText);
                    intent.putExtra("getBoard", getBoard);
                    intent.putExtra("getContract", getContract);
                    intent.putExtra("getLove", getLove);
                    startActivity(intent);

                    Log.i("tag", "타이틀: " + getTitle + "/날짜: " + getDate + "/카테고리: " + getBoard + "/닉네임: " + getNick + "/내용: " + getText);
                }
            }
        });
    }

//    public void secretAlertDialog(final String getPw) {
//
//        // 빌더 레이아웃 생성
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        final AlertDialog ad = builder.create(); // https://m.blog.naver.com/PostView.nhn?blogId=wlsdml1103&logNo=220592626399&proxyReferer=https:%2F%2Fwww.google.com%2F
//        LayoutInflater inflater = getLayoutInflater();
//        View view = new View(context);
//        LinearLayout laySecretContainer = view.findViewById(R.id.lay_secretContainer);
//
//        view = inflater.inflate(R.layout.secret_dialog, laySecretContainer, false);
//        ad.setView(view);
//
//        final Button btnSecretOk = view.findViewById(R.id.btn_secretOk);
//        final EditText edtSecretPw = view.findViewById(R.id.edt_secretPw);
//
//        btnSecretOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String getSecretPw = edtSecretPw.getText().toString();
//                if (getSecretPw.equals(getPw)) {
//                    secretLogicChecker = 1; // 외부에서 많은 문자열을 불러들이키기 어려우므로 if로 사용할 체커기다.
//                    secretPW = getSecretPw;
//                    ad.dismiss();
//
//                } else if (!getSecretPw.equals(getPw)) {
//                    Toast.makeText(context, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
//                    secretLogicChecker = 0;
//                }
//            }
//        });
//        ad.show();
//    }
}
