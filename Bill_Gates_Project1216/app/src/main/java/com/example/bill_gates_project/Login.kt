package com.example.bill_gates_project

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB : SQLiteDatabase
    lateinit var st_Id : String
    lateinit var st_Name : String
    lateinit var editid : EditText
    lateinit var editpw : EditText
    lateinit var btnlogin : Button
    lateinit var btnsignup : Button
    lateinit var st_Pw : String
    lateinit var dlgEdtName : EditText
    lateinit var dlgEdtId : EditText
    lateinit var dlgEdtPw : EditText
    lateinit var dialogView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        myHelper = myDBHelper(this)
        editid=findViewById<EditText>(R.id.editid) //아이디 입력부분
        editpw=findViewById<EditText>(R.id.editpw) //패스워드 입력부분
        btnlogin=findViewById<Button>(R.id.btnlogin) //로그인버튼
        btnsignup=findViewById<Button>(R.id.btnsignup) //회원가입버튼

        btnsignup.setOnClickListener {
            dialogView = View.inflate(this@Login,R.layout.signup,null)
            var dlg = AlertDialog.Builder(this@Login)
            dlg.setTitle("회원 정보 등록")
            dlg.setView(dialogView)
            dlg.setPositiveButton("확인") {dialog, which ->
                dlgEdtName = dialogView.findViewById(R.id.dlgEdt1)
                dlgEdtId = dialogView.findViewById(R.id.dlgEdt2)
                dlgEdtPw = dialogView.findViewById(R.id.dlgEdt3)
                st_Id = dlgEdtId.text.toString()
                st_Pw = dlgEdtPw.text.toString()
                st_Name = dlgEdtName.text.toString()
                if(st_Pw.isEmpty() || st_Name.isEmpty() || st_Id.isEmpty()) {
                    Toast.makeText(applicationContext,"빈칸을 모두 입력해주세요",Toast.LENGTH_SHORT).show()
                }else{
                    sqlDB = myHelper.readableDatabase
                    var cursor : Cursor =sqlDB.rawQuery("SELECT * FROM student WHERE stId='" +
                            st_Id + "';",null)
                    if(cursor.moveToFirst()) {
                        cursor.close()
                        sqlDB.close()
                        Toast.makeText(applicationContext, "이미 등록된 id 입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        cursor.close()
                        sqlDB.close()
                        sqlDB = myHelper.writableDatabase
                        sqlDB.execSQL(
                            "INSERT INTO student Values('" +
                                    st_Id + "','" +
                                    st_Name + "','" +
                                    st_Pw+"',0);")
                        sqlDB.close()
                        Toast.makeText(applicationContext, "등록 완료", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            dlg.setNegativeButton("취소",null)
            dlg.show()
        }

        btnlogin.setOnClickListener{
            st_Id = editid.text.toString()
            st_Pw = editpw.text.toString()
            sqlDB = myHelper.readableDatabase

            var cursor : Cursor= sqlDB.rawQuery("SELECT stId FROM student WHERE stId = '" +
                    st_Id +"';",null)
            if(cursor.moveToFirst()) {
                cursor.close()
                cursor = sqlDB.rawQuery("SELECT stPw FROM student WHERE stId = '" +
                        st_Id+"';",null)
                cursor.moveToFirst()
                if(st_Pw == cursor.getString(0)) {
                    cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
                    if(cursor.moveToFirst()) {
                        var stId = ArrayList<String>()
                        var i : Int = 0
                        stId.add(cursor.getString(0))
                        i = 1
                        while (cursor.moveToNext()) {
                            stId.add(cursor.getString(0))
                            i++
                        }
                        cursor.close()
                        sqlDB = myHelper.writableDatabase
                        for(j in 0..i-1) {
                            sqlDB.execSQL("UPDATE student SET stLogging = 0 WHERE stId = '" +
                                    stId[j] + "';")
                        }
                        sqlDB.close()
                    }
                    sqlDB = myHelper.writableDatabase
                    sqlDB.execSQL("UPDATE student SET stLogging = 1 WHERE stId = '" +
                            st_Id + "';")
                    sqlDB.close()
                    intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,"비밀번호 오류.",Toast.LENGTH_SHORT).show()
                }
            }else{
                cursor.close()
                Toast.makeText(applicationContext,"등록되지 않은 id 입니다.",Toast.LENGTH_SHORT).show()
            }
            sqlDB.close()
        }
    }
}