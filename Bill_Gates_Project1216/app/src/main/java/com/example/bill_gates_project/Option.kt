package com.example.bill_gates_project

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bill_gates_project.databinding.FragmentOptionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Option.newInstance] factory method to
 * create an instance of this fragment.
 */
class Option : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var obinding : FragmentOptionBinding
    lateinit var btnset : Button
    lateinit var dialogView : View
    lateinit var dlgSetting1Edt : EditText
    lateinit var myHelper : myDBHelper
    lateinit var sqlDB : SQLiteDatabase
    lateinit var dlgEdtName : EditText
    lateinit var dlgEdtPw : EditText
    lateinit var btnLogOut : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myHelper = myDBHelper(requireContext())
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        obinding =  FragmentOptionBinding.inflate(inflater, container, false)

        var tmpBoolean : Boolean = false

        obinding.setbtn.setOnClickListener {
            dialogView = View.inflate(activity,R.layout.usersetting,null)
            var dlg = AlertDialog.Builder(activity)
            dlg.setTitle("사용자 확인")
            dlg.setView(dialogView)
            dlg.setPositiveButton("확인") {dialog,which ->
                dlgSetting1Edt = dialogView.findViewById<EditText>(R.id.dlgSetting1Edt)
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery("SELECT stPw FROM student WHERE stLogging = 1;",null)
                cursor.moveToFirst()
                if(dlgSetting1Edt.text.toString() == cursor.getString(0)) {
                    val parentView = view?.parent as? ViewGroup
                    parentView?.removeView(view)
                    dialogView = View.inflate(activity,R.layout.editusersetting,null)
                    dlg = AlertDialog.Builder(activity)
                    dlg.setTitle("사용자 정보 변경")
                    dlg.setView(dialogView)
                    dlg.setPositiveButton("확인") {dialog,which ->
                        dlgEdtName = dialogView.findViewById<EditText>(R.id.digEdtName)
                        dlgEdtPw = dialogView.findViewById<EditText>(R.id.dlgEdtPw)
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT stName FROM student WHERE stLogging = 1;",null)
                        cursor.moveToFirst()
                        var boolNamePast : Boolean = false
                        var boolName : Boolean = false
                        if(cursor.getString(0) == dlgEdtName.text.toString()) {
                            boolNamePast = true
                        }
                        cursor = sqlDB.rawQuery("SELECT * FROM student WHERE stName = '" +
                                dlgEdtName + "';",null)
                        if(cursor.moveToFirst() && boolNamePast == false) {
                            boolName = true
                        }
                        cursor = sqlDB.rawQuery("SELECT stPw FROM student WHERE stLogging = 1;",null)
                        var boolPw : Boolean = false
                        cursor.moveToFirst()
                        if(cursor.getString(0) == dlgEdtPw.text.toString()) {
                            boolPw = true
                        }
                        cursor.close()
                        sqlDB = myHelper.writableDatabase
                        if(!dlgEdtName.text.isEmpty()) {
                            if(boolNamePast == true) {
                                Toast.makeText(activity,"이전과 같은 닉네임을 사용할 수 없습니다",Toast.LENGTH_SHORT).show()
                            }
                            if(boolName == true) {
                                Toast.makeText(activity,"해당 닉네임이 이미 존재합니다",Toast.LENGTH_SHORT).show()
                            }
                            if(boolName == false && boolNamePast == false) {
                                sqlDB.execSQL("UPDATE student SET stName = '" +
                                        dlgEdtName.text.toString() +"' WHERE stLogging = 1;")
                            }
                        }
                        if(!dlgEdtPw.text.isEmpty()) {
                            if(boolPw == true) {
                                Toast.makeText(activity,"이전과 같은 비밀번호를 사용할 수 없습니다",Toast.LENGTH_SHORT).show()
                            }else{
                                sqlDB.execSQL("UPDATE student SET stPw = '" +
                                        dlgEdtPw.text.toString() + "' WHERE stLogging = 1;")
                            }
                        }
                    }
                    dlg.setNegativeButton("취소",null)
                    dlg.show()
                }
                cursor.close()
                sqlDB.close()
            }
            dlg.setNegativeButton("취소",null)
            dlg.show()
        }
        obinding.btnLogOut.setOnClickListener {
            var dlg = AlertDialog.Builder(activity)
            dlg.setTitle("로그아웃 하시겠습니까?")
            dlg.setMessage("확인을 누르면 로그아웃 됩니다")
            dlg.setPositiveButton("확인") {dialog,which ->
                var intent = Intent(activity,Login::class.java)
                startActivity(intent)
            }
            dlg.setNegativeButton("취소",null)
            dlg.show()
        }

        return obinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Option.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Option().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}