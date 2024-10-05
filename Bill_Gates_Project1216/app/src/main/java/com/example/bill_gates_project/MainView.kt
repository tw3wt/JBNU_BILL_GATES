package com.example.bill_gates_project

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.bill_gates_project.databinding.MainviewBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainView : Fragment() {


    private var param1: String? = null
    private var param2: String? = null

    lateinit var sqlDB : SQLiteDatabase
    lateinit var myHelper : myDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding=MainviewBinding.inflate(inflater,container,false)

        myHelper = myDBHelper(requireContext())

        val linear: LinearLayout by lazy{
            binding.linearlayout
        }

        fun transFrom(str : String) : Double {
            if(str == "A+") {
                return 4.5
            }else if(str == "A") {
                return 4.0
            }else if(str == "B+") {
                return 3.5
            }else if(str == "B") {
                return 3.0
            }else if(str == "C+") {
                return 2.5
            }else if(str == "D+") {
                return 2.0
            }else if(str == "D") {
                return 1.5
            }else{
                return -1.0
            }
        }

        sqlDB = myHelper.readableDatabase
        var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
        cursor.moveToFirst()
        var stId = cursor.getString(0)
        cursor = sqlDB.rawQuery("SELECT stName FROM student WHERE stId = '" +
                stId + "';",null)
        cursor.moveToFirst()
        binding.textusername.text = "유저 이름 : " + cursor.getString(0)

        cursor = sqlDB.rawQuery("SELECT grade FROM grade WHERE stId = '" +
                stId + "';",null)
        var i = 0
        if(cursor.moveToFirst()) {
            var totalGrade : Double = 0.0
            totalGrade += transFrom(cursor.getString(0))
            i=1
            while (cursor.moveToNext()) {
                totalGrade += transFrom(cursor.getString(0))
                i++
            }
            binding.texttotalgrade.text = "전체 평점 : " + (totalGrade/i.toDouble()).toString()
        }

        cursor = sqlDB.rawQuery("SELECT crId FROM grade WHERE stId = '" +
                stId + "';",null)
        if(cursor.moveToFirst()) {
            var crId = ArrayList<String>()
            var crIdNeed = ArrayList<String>()
            var totalGradeMajor = 0.0
            crId.add(cursor.getString(0))
            while (cursor.moveToNext()) {
                crId.add(cursor.getString(0))
            }
            for(j in 0..crId.size-1) {
                cursor = sqlDB.rawQuery("SELECT crId FROM course WHERE (crType = '전공필수' OR crType = '전공선택') AND crId = '" +
                        crId[j] + "';",null)
                if(cursor.moveToFirst()) {
                    crIdNeed.add(cursor.getString(0))
                }
            }
            for(j in 0..crIdNeed.size-1) {
                cursor = sqlDB.rawQuery("SELECT grade FROM grade WHERE crId = '" +
                        crIdNeed[j] + "' AND stId = '" +
                        stId + "';",null)
                cursor.moveToFirst()
                totalGradeMajor += transFrom(cursor.getString(0))
            }
            binding.textmajorgrade.text = "전공 평점 : " + (totalGradeMajor/crIdNeed.size.toDouble()).toString()

        }

        cursor.close()
        sqlDB.close()

        binding.btn1grade.setOnClickListener{
                changeFragment(FirstGrade())
        }

        binding.btn2grade.setOnClickListener {
            changeFragment(SecondGrade())
        }

        binding.btn3grade.setOnClickListener {
            changeFragment(ThirdGrade())
        }

        binding.btn4grade.setOnClickListener {
            changeFragment(FourthGrade())
        }

        binding.btngraduate.setOnClickListener {
            changeFragment(Graduate())
        }



        return binding.root

    }

    private fun changeFragment(fragment : Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.linearlayout, fragment)?.commit()
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            MainView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}