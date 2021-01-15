package com.example.taling13;

public class PostData {
    public String ID = null;
    public String PW = null;
    public String Type = null;
    public String title = null; // 제목 // public이 공용이란 뜻인데 이걸 붙이지 않으면 기본적으로 프로젝트까지만 사용가능하다는 의미인가?
    public String board = null; //
    public String nick = null;// 닉네임
    public String time = null; // 시간
    public String text = null;// 내용
    public int contract = 0; // 조회수
    public int love = 0; // 좋아요

    public PostData() {

    }

    public PostData(String ID,String PW,String Type,String title, String nick,String time,String text,String board) {
        this.ID = ID;
        this.PW = PW;
        this.Type = Type;
        this.title = title;
        this.nick = nick;
        this.time = time;
        this.text = text;
        this.board = board;
    }

    public PostData(String ID, String title, String nick,String time,String text,String board) {
        this.ID = ID;
        this.title = title;
        this.nick = nick;
        this.time = time;
        this.text = text;
        this.board = board;
    }

}
