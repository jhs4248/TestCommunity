package com.example.taling13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<BoardData> saveDataFromListAdapter = new ArrayList<>(); // 2.getDataFromBoardActivity(M)에서 받은 데이터를 ArrayList로 저장.


    @Override
    public int getCount() { // 3.ListView에 전시될 데이터파일 갯수들. 이름,날짜,제목까지 포함시켜 총 3개의 ArrayList.
        return saveDataFromListAdapter.size();
    }

    @Override
    public Object getItem(int position) {

        return saveDataFromListAdapter.get(position); // 이건 그냥 arrayList 정보를 따라하자.
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            Context context = parent.getContext(); // Activity에서 Inflate를 실행하면 getLAyout이 가능하지만 아닌 경우는 Context 호출.
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.textTitle);
        TextView tvDateAndName = convertView.findViewById(R.id.textDateAndName);

        BoardData boardData = saveDataFromListAdapter.get(position);
        final String title = boardData.getTitle();
        final String name = boardData.getName();
        final String date = boardData.getDate();
        final String text = boardData.getText();


        tvTitle.setText(title);
        tvDateAndName.setText(date + " " + name);

        return convertView;
    }

    public void addData(BoardData boardData) { //1. MainActivity에서 받아오긴 하지만 BoardData 변수로 받아오는건가?
        saveDataFromListAdapter.add(boardData);
    }
}
