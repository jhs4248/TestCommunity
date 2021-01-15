package com.example.taling13;

// 데이터들을 담는 목적의 클래스 만들 때 규칙
// private으로 변수 선언
// 빈 생성자 만들기
// 매개변수받는 생성자 만들기
// getter/setter 자동생성
// 옵션 (toString오버라이딩)

// alt + insert 단축키

// 기본생성자, 생성자 오버라이딩 (생성자를 눈에 보이게 하는 것)

public class BoardData {

    private String ID = null;
    private String PW = null;
    private String type = null;
    private String title = null;
    private String date = null;
    private String name = null;
    private String text = null;
    private String board = null;
    private int contract = 0;
    private int love = 0;

    public BoardData() {

    }

    public BoardData(String ID, String PW, String type, String title, String date, String name, String text, String board, int contract, int love) {
        this.ID = ID;
        this.PW = PW;
        this.type = type;
        this.title = title;
        this.date = date;
        this.name = name;
        this.text = text;
        this.board = board;
        this.contract = contract;
        this.love = love;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getContract() {
        return contract;
    }

    public void setContract(int contract) {
        this.contract = contract;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    @Override
    public String toString() {
        return "BoardData{" +
                "ID='" + ID + '\'' +
                ", PW='" + PW + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", board='" + board + '\'' +
                ", contract=" + contract +
                ", love=" + love +
                '}';
    }
}
