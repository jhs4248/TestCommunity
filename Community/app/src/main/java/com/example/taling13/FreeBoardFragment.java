package com.example.taling13;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeBoardFragment extends Fragment {

    ArrayList<BoardData> data;
    String getId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context context = getContext();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ListAdapter listAdapter = new ListAdapter(); // 어댑터 연결

        View view = inflater.inflate(R.layout.free_board_fragment, container, false); // 독립 뷰를 불러온다.onCreateView와 함께 공부하기
        final ListView listView = view.findViewById(R.id.listView_free);

        Bundle bundle = getArguments();
        getId = bundle.getString("id");

//        db.collection("자유 게시판").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot snapshot : task.getResult()) {
//                        Map<String, Object> forms = snapshot.getData();
//
//                        for (Map.Entry<String, Object> form : forms.entrySet()) {
//                            String key = (String) form.getKey();
//                            Map<Object, Object> values = (Map<Object, Object>) form.getValue();
//                            String ID = (String) values.get("ID");
//                            String title = (String) values.get("title");
//                            String time = (String) values.get("time");
//                            String nick = (String) values.get("nick");
//                            String text = (String) values.get("text");
//                            String board = (String) values.get("board");
//                            String contract = String.valueOf(values.get("contract"));
//                            String love = String.valueOf(values.get("love"));
//
//                            int convertContract = Integer.parseInt(contract);
//                            int convertLove = Integer.parseInt(love);
//
//                            Log.i("TAGofficial",
//                                    "ID: " + ID +
//                                            "제목:" + title +
//                                            "/시간:" + time +
//                                            "/닉네임:" + nick +
//                                            "/카테고리:" + board +
//                                            "/내용:" + text +
//                                            "/조회수:" + contract +
//                                            "/좋아요:" + love);
//                            // https://stackoverrun.com/ko/q/13131666
//
//                            final BoardData boardData = new BoardData();
//                            boardData.ID = ID;
//                            boardData.title = title;
//                            boardData.board = board;
//                            boardData.date = time;
//                            boardData.name = nick;
//                            boardData.text = text;
//                            boardData.contract = convertContract;
//                            boardData.love = convertLove;
//                            listAdapter.addData(boardData);
//                            listView.setAdapter(listAdapter);
//
//                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    ArrayList<BoardData>getAdapterData = listAdapter.saveDataFromListAdapter;
//                                    BoardData getDataInfo = getAdapterData.get(position);
//
//                                    String getId = getDataInfo.getID();
//                                    String getTitle = getDataInfo.getTitile();
//                                    String getDate = getDataInfo.getDate();
//                                    String getNick = getDataInfo.getName();
//                                    String getText = getDataInfo.getText();
//                                    String getBoard = getDataInfo.getBoard();
//                                    int getContract = getDataInfo.getContract();
//                                    int getLove = getDataInfo.getLove();
//
//                                    getContract = getContract++;
//
//                                    PostData postData = new PostData(getId,getTitle, getNick, getDate, getText, getBoard);
////                                    postData.getPostData(getId,getTitle, getNick, getDate, getText, getBoard);
//                                    postData.contract = getContract;
//                                    postData.love = getLove;
//                                    Map<String, PostData> putPostData = new HashMap<>();
//                                    putPostData.put(getTitle + ":" + getDate, postData); // 제목 + 시간으로 Key값을 설정하여 실시간 업데이트 및 확인이 가능하다.
//                                }
//                            });
//                        }
//
////                        for(Map.Entry<String, Object> elem : getData.entrySet()){
////                            System.out.println("키 : " + elem.getKey() + "값 : " + elem.getValue());
////                            //https://mine-it-record.tistory.com/233
////
////                        }
//                    }
//                }
//            }
//        });

        return view;
    }

    public void secretAlertDialog(){

    }
}
