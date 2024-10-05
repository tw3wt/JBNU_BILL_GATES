package com.example.bill_gates_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class myDBHelper (context: Context) : SQLiteOpenHelper(context,"groupDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE student(stId VARCHAR(15) PRIMARY KEY NOT NULL ,stName VARCHAR(10),stPw VARCHAR(25) NOT NULL,stLogging INTEGER);")
        db!!.execSQL("CREATE TABLE course(crId VARCHAR(15) PRIMARY KEY,crName VARCHAR(30),crPost1 VARCHAR(20),crPost2 VARCHAR(20), crPost3 VARCHAR(20),crType VARCHAR(20),crScore INTEGER);")
        db!!.execSQL("CREATE TABLE grade(stId VARCHAR(15),crId VARCHAR(15),grade VARCHAR(5),score INTEGER,semester VARCHAR(20),FOREIGN KEY (stId) REFERENCES student(stId),FOREIGN KEY (crId) REFERENCES course(crId));")
        db!!.execSQL("CREATE VIEW rank AS SELECT s.stId,s.stName AS student_Name,AVG(g.score) AS average_gpa,(SELECT COUNT(DISTINCT g2.stId)+1 FROM grade g2 JOIN student s2 ON g2.stId = s2.stId WHERE AVG(g2.score) > AVG(g.score))AS ranking FROM grade g JOIN student s ON g.stId = s.stId GROUP BY s.stId, s.stName")
        db!!.execSQL("CREATE TABLE graduate(toeic INTEGER,cbt INTERGER,pbt INTEGER,ibt INTEGER,teps INTEGER,toeicSpeaking INTEGER,opic INTEGER,gtelp INTEGER,newteps INTEGER)")
        db!!.execSQL("CREATE TABLE tmp(tmpStr VARCHAR(50));")
        db!!.execSQL("CREATE TABLE tmpindex(tmpi INTEGER)")
        db!!.execSQL("INSERT INTO tmpindex VALUES(0);")
        db!!.execSQL("INSERT INTO graduate VALUES(0,0,0,0,0,0,0,0,0);")
        db!!.execSQL("INSERT INTO tmp VALUES('');")
        db!!.execSQL("INSERT INTO course VALUES ('2023111','수학1','수학2',null,null,'교양',3.0)," +
                "('2023112','컴퓨터과학과코딩','인공지능','초급프로젝트',null,'교양',3.0)," +
                "('2023113','C언어기초','C++프로그래밍','리눅스프로그래밍',null,'교양',3.0)," +
                "('2023121','컴퓨터공학총론',null,null,null,'전공선택',3.0)," +
                "('2023122','수학2','확률과통계','선형대수학',null,'교양',3.0)," +
                "('2023123','C++프로그래밍','자료구조','암호론',null,'전공선택',3.0)," +
                "('2023124','이산수학','암호론',null,null,'전공선택',3.0)," +
                "('2023211','선형대수학','수치해석및최적화','인공지능','데이터베이스','전공선택',3.0)," +
                "('2023212','초급프로젝트','인간-컴퓨터상호작용',null,null,'전공선택',3.0)," +
                "('2023213','자료구조','알고리즘',null,null,'전공필수',3.0)," +
                "('2023214','객체지향프로그래밍','모바일프로그래밍','소프트웨어공학',null,'전공선택',3.0)," +
                "('2023215','논리설계','컴퓨터구조',null,null,'전공선택',3.0)," +
                "('2023216','리눅스프로그래밍','운영체제',null,null,'전공선택',3.0)," +
                "('2023221','확률과통계','인공지능',null,null,'전공선택',3.0)," +
                "('2023222','알고리즘','인공지능','데이터베이스','정보검색','전공필수',3.0)," +
                "('2023223','모바일프로그래밍','웹서비스설계',null,null,'전공선택',3.0)," +
                "('2023224','프로그램구조와해석','프로그래밍언어론',null,null,'전공선택',3.0)," +
                "('2023225','컴퓨터구조','임베디드시스템','디지털시스템설계','운영체제','전공필수',3.0)," +
                "('2023226','데이터통신','컴퓨터네트워크',null,null,'전공선택',3.0)," +
                "('2023311','수치해석및최적화','컴퓨터그래픽스',null,null,'전공선택',3.0)," +
                "('2023312','인공지능','기계학습','데이터마이닝',null,'전공필수',3.0)," +
                "('2023313','데이터베이스','데이터마이닝','웹서비스설계',null,'전공필수',3.0)," +
                "('2023314','프로그래밍언어론','컴파일러',null,null,'전공선택',3.0)," +
                "('2023315','운영체제','시스템보안','클라우드컴퓨팅',null,'전공필수',3.0)," +
                "('2023316','컴퓨터네트워크','클라우드컴퓨팅','센서네트워크','네트워크보안','전공선택',3.0)," +
                "('2023321','컴퓨터그래픽스','게임및혼합현실',null,null,'전공선택',3.0)," +
                "('2023322','기계학습','컴퓨터비전','자연어처리',null,'전공선택',3.0)," +
                "('2023323','데이터마이닝',null,null,null,'전공선택',3.0)," +
                "('2023324','웹서비스설계',null,null,null,'전공선택',3.0)," +
                "('2023325','소프트웨어공학',null,null,null,'전공선택',3.0)," +
                "('2023326','디지털시스템설계',null,null,null,'전공선택',3.0)," +
                "('2023327','클라우드컴퓨팅','오픈소스SW 개발',null,null,'전공선택',3.0)," +
                "('2023328','암호론',null,null,null,'전공선택',3.0)," +
                "('2023411','게임및혼합현실',null,null,null,'전공선택',3.0)," +
                "('2023412','컴퓨터비전','딥러닝',null,null,'전공선택',3.0)," +
                "('2023413','자연어처리','딥러닝',null,null,'전공선택',3.0)," +
                "('2023414','인간-컴퓨터상호작용',null,null,null,'전공선택',3.0)," +
                "('2023415','컴파일러',null,null,null,'전공선택',3.0)," +
                "('2023416','임베디드시스템',null,null,null,'전공선택',3.0)," +
                "('2023417','오픈소스SW 개발',null,null,null,'전공선택',3.0)," +
                "('2023418','센서네트워크',null,null,null,'전공선택',3.5)," +
                "('2023419','네트워크보안',null,null,null,'전공선택',3.0)," +
                "('2023421','딥러닝',null,null,null,'전공선택',3.0)," +
                "('2023422','정보검색',null,null,null,'전공선택',3.0)," +
                "('2023423','시스템보안',null,null,null,'전공선택',3.0);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS student")
        db!!.execSQL("DROP TABLE IF EXISTS subject")
        onCreate(db)
    }
}