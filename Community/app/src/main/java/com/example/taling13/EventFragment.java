package com.example.taling13;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class EventFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ListAdapter listAdapter = new ListAdapter(); // 어댑터 연결

        View view = inflater.inflate(R.layout.event_board_fragment, container, false);
        listView = view.findViewById(R.id.listView_event);

//        db.collection("이벤트").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot snapshot : task.getResult()) {
//                        Map<String, Object> forms = snapshot.getData();
//
//                        for (Map.Entry<String, Object> form : forms.entrySet()) {
//                            String key = (String) form.getKey();
//                            Map<Object, Object> values = (Map<Object, Object>) form.getValue();
//                            String title = (String) values.get("title");
//                            String time = (String) values.get("time");
//                            String nick = (String) values.get("nick");
//                            String text = (String) values.get("text");
//                            Log.i("asdf", "제목:" + title + "/시간:" + time + "/닉네임:" + nick + "/내용:" + text);
//                            // https://stackoverrun.com/ko/q/13131666
//
//                            BoardData boardData = new BoardData();
//                            boardData.title = title;
//                            boardData.date = time;
//                            boardData.name = nick;
//                            listAdapter.addData(boardData);
//                            listView.setAdapter(listAdapter);
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
}
