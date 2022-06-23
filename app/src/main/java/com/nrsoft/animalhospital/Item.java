package com.nrsoft.animalhospital;

public class Item{

    String name; //병원명
    String addrgi; //지번주소
    String addrdo; //도로명주소
    String tel; //전화번호
    String condition; //영업 상태
    String mgtNo; //관리 번호


    public Item(String name, String addrgi, String addrdo, String tel, String condition, String mgtNo) {
        this.name = name;
        this.addrgi = addrgi;
        this.addrdo = addrdo;
        this.tel = tel;
        this.condition = condition;
        this.mgtNo= mgtNo;
    }

    public Item() {
    }
}
