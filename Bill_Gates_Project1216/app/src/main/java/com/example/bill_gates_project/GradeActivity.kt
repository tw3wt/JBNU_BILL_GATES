package com.example.bill_gates_project

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bill_gates_project.databinding.GradeactivityBinding
import com.example.bill_gates_project.databinding.NewsubjectaddBinding
import java.util.Timer
import java.util.TimerTask


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GradeActivity : DialogFragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var mBinding: GradeactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //semester = ArrayAdapter(requireActivity().applicationContext,android.R.layout.simple_spinner_item,resources.getStringArray(R.array.semester)).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //gradeactivity 바인딩 구현
        mBinding = GradeactivityBinding.inflate(inflater, container, false)

        var checkarraysubject = arguments?.getStringArrayList("checkarraysubject")
        var checkarrayselect = arguments?.getStringArrayList("checkarrayselect")
        var checkarraygrade = arguments?.getStringArrayList("checkarraygrade")
        var checkarrayscore = arguments?.getIntegerArrayList("checkarrayscore")

        myHelper = myDBHelper(requireContext())

        fun transForm(str : String) : Double {
            if(str == "A+") {
                return 4.5
            }else if(str == "A") {
                return  4.0
            }else if(str == "B+") {
                return  3.5
            }else if(str == "B") {
                return  3.0
            }else if(str == "C+") {
                return  2.5
            }else if(str == "C") {
                return  2.0
            }else if(str == "D+") {
                return  1.5
            }else if(str == "D") {
                return  1.0
            }else if(str == "F") {
                return 0.0
            }else{
                return -1.0
            }
        }

        fun calculTotal() {
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
            cursor.moveToFirst()
            var stId = cursor.getString(0)
            cursor  = sqlDB.rawQuery("SELECT crId,score FROM grade WHERE stId = '" +
                    stId+ "';",null)
            var arrCrId = ArrayList<String>()
            if(cursor.moveToFirst()) {
                var i : Int = 0
                var totalSum : Double = 0.0;
                arrCrId.add(cursor.getString(0))
                totalSum += cursor.getInt(1).toDouble()
                i=1
                while (cursor.moveToNext()) {
                    arrCrId.add(cursor.getString(0))
                    totalSum += cursor.getInt(1).toDouble()
                    i++
                }
                mBinding.selectscore.text = "수강학점 : " + totalSum.toString()
            }
            cursor.close()
            sqlDB.close()
        }

        fun calculMajor() {
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
            cursor.moveToFirst()
            var stId = cursor.getString(0)
            cursor  = sqlDB.rawQuery("SELECT crId,score FROM grade WHERE stId = '" +
                    stId+ "';",null)
            var arrCrId = ArrayList<String>()
            if(cursor.moveToFirst()) {
                var i : Int = 0
                var totalSumMajorNeed : Double = 0.0
                var totalSumMajorSelect : Double = 0.0
                arrCrId.add(cursor.getString(0))
                i=1
                while (cursor.moveToNext()) {
                    arrCrId.add(cursor.getString(0))
                    i++
                }
                for(j in 0..i-1) {
                    cursor = sqlDB.rawQuery("SELECT crScore FROM course WHERE crType = '전공필수' AND crId = '" +
                            arrCrId[j] + "';",null)
                    if(cursor.moveToFirst()) {
                        totalSumMajorNeed += cursor.getInt(0).toDouble()
                    }
                }
                for(j in 0..i-1) {
                    cursor = sqlDB.rawQuery("SELECT crScore FROM course WHERE crType = '전공선택' AND crId = '" +
                            arrCrId[j] + "';",null)
                    if(cursor.moveToFirst()) {
                        totalSumMajorSelect += cursor.getInt(0).toDouble()
                    }
                }
                mBinding.subjectscore.text = "전공학점 : " + (totalSumMajorNeed+totalSumMajorSelect).toString()
                mBinding.subjectneedscore.text = "전필학점 : " + totalSumMajorNeed.toString()
                mBinding.subjectselectscore.text = "전선학점 : " + totalSumMajorSelect.toString()
            }
            cursor.close()
            sqlDB.close()
        }

        fun calculCulture() {
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
            cursor.moveToFirst()
            var stId = cursor.getString(0)
            cursor  = sqlDB.rawQuery("SELECT crId,score FROM grade WHERE stId = '" +
                    stId+ "';",null)
            var arrCrId = ArrayList<String>()
            if(cursor.moveToFirst()) {
                var i : Int = 0
                var totalCulture : Double = 0.0
                arrCrId.add(cursor.getString(0))
                i=1
                while (cursor.moveToNext()) {
                    arrCrId.add(cursor.getString(0))
                    i++
                }
                for(j in 0..i-1) {
                    cursor = sqlDB.rawQuery("SELECT crScore FROM course WHERE crType = '교양' AND crId = '" +
                            arrCrId[j] + "';",null)
                    if(cursor.moveToFirst()) {
                        totalCulture += cursor.getInt(0).toDouble()
                    }
                }
                mBinding.culturescore.text = "교양학점 : " + totalCulture.toString()
            }
            cursor.close()
            sqlDB.close()
        }

        fun insertGrade(str: String, n: Int) {
            if (n == 0) {
                mBinding.text13.text = str
            } else if (n == 1) {
                mBinding.text23.text = str
            } else if (n == 2) {
                mBinding.text33.text = str
            } else if (n == 3) {
                mBinding.text43.text = str
            } else if (n == 4) {
                mBinding.text53.text = str
            } else if (n == 5) {
                mBinding.text63.text = str
            } else if (n == 6) {
                mBinding.text73.text = str
            } else if (n == 7) {
                mBinding.text83.text = str
            }
        }

        fun insertElse(strName: String, strType: String, strScore: String, n: Int) {
            if (n == 0) {
                mBinding.glayout1.visibility = View.VISIBLE
                mBinding.text11.text = strName
                mBinding.text12.text = strType
                mBinding.text14.text = strScore
            } else if (n == 1) {
                mBinding.glayout2.visibility = View.VISIBLE
                mBinding.text21.text = strName
                mBinding.text22.text = strType
                mBinding.text24.text = strScore
            } else if (n == 2) {
                mBinding.glayout3.visibility = View.VISIBLE
                mBinding.text31.text = strName
                mBinding.text32.text = strType
                mBinding.text34.text = strScore
            } else if (n == 3) {
                mBinding.glayout4.visibility = View.VISIBLE
                mBinding.text41.text = strName
                mBinding.text42.text = strType
                mBinding.text44.text = strScore
            } else if (n == 4) {
                mBinding.glayout5.visibility = View.VISIBLE
                mBinding.text51.text = strName
                mBinding.text52.text = strType
                mBinding.text54.text = strScore
            } else if (n == 5) {
                mBinding.glayout6.visibility = View.VISIBLE
                mBinding.text61.text = strName
                mBinding.text62.text = strType
                mBinding.text64.text = strScore
            } else if (n == 6) {
                mBinding.glayout7.visibility = View.VISIBLE
                mBinding.text71.text = strName
                mBinding.text72.text = strType
                mBinding.text74.text = strScore
            } else if (n == 7) {
                mBinding.glayout8.visibility = View.VISIBLE
                mBinding.text81.text = strName
                mBinding.text82.text = strType
                mBinding.text84.text = strScore
            }
        }

        fun shortcut(n : Int) {
            mBinding.glayout1.visibility = View.INVISIBLE
            mBinding.glayout2.visibility = View.INVISIBLE
            mBinding.glayout3.visibility = View.INVISIBLE
            mBinding.glayout4.visibility = View.INVISIBLE
            mBinding.glayout5.visibility = View.INVISIBLE
            mBinding.glayout6.visibility = View.INVISIBLE
            mBinding.glayout7.visibility = View.INVISIBLE
            mBinding.glayout8.visibility = View.INVISIBLE
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1;",null)
            cursor.moveToFirst()
            var stId = cursor.getString(0)
            when(n) {
                1 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '1학년 1학기' AND stId ='" +
                                stId + "';",
                        null
                    )
                }
                2 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '1학년 2학기' AND stId = '" +
                                stId+ "';",
                        null
                    )
                }
                3 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '2학년 1학기' AND stId = '" +
                                stId + "';",
                        null
                    )
                }
                4 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '2학년 2학기' AND stId = '" +
                                stId + "';",
                        null
                    )
                }
                5 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '3학년 1학기' AND stId = '" +
                                stId + "';",
                        null
                    )
                }
                6 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '3학년 2학기' AND stId = '" +
                                stId+ "';",
                        null
                    )
                }
                7 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '4학년 1학기' AND stId = '" +
                                stId + "';",
                        null
                    )
                }
                8 -> {
                    cursor = sqlDB.rawQuery(
                        "SELECT crId,grade FROM grade WHERE semester = '4학년 2학기' AND stId = '" +
                                stId+ "';",
                        null
                    )
                }
            }
            var arrCrId = ArrayList<String>()
            var i: Int = 0
            if (cursor.moveToFirst()) {
                insertGrade(cursor.getString(1), i)
                arrCrId.add(cursor.getString(0))
                i = 1
                while (cursor.moveToNext()) {
                    insertGrade(cursor.getString(1), i)
                    arrCrId.add(cursor.getString(0))
                    i++
                }
            }
            var flag: Int = 0
            for (j in 0..i - 1) {

                cursor = sqlDB.rawQuery(
                    "SELECT crName, crType, crScore FROM course WHERE crId = '" +
                            arrCrId[j] + "';", null
                )
                if (!cursor.moveToFirst()) {
                    break
                } else {
                    insertElse(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2).toString(),
                        j
                    )
                }
            }
            cursor.close()
            sqlDB.close()
        }

        mBinding.semesterspinner.setSelection(0)
        mBinding.semesterspinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '...';")
                            sqlDB.close()
                        }

                        1 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '1학년 1학기';")
                            sqlDB.close()
                        }

                        2 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '1학년 2학기';")
                            sqlDB.close()
                        }

                        3 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '2학년 1학기';")
                            sqlDB.close()
                        }

                        4 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '2학년 2학기';")
                            sqlDB.close()
                        }

                        5 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '3학년 1학기';")
                            sqlDB.close()
                        }

                        6 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '3학년 2학기';")
                            sqlDB.close()
                        }

                        7 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '4학년 1학기';")
                            sqlDB.close()
                        }

                        8 -> {
                            calculTotal()
                            calculMajor()
                            calculCulture()
                            shortcut(position)
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("UPDATE tmp SET tmpStr = '4학년 2학기';")
                            sqlDB.close()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


        if (checkarraysubject != null) {
            for (i in 0..checkarraysubject!!.size - 1) {
                if (i == 0) {
                    mBinding.text11.text = checkarraysubject!![i].toString()
                    mBinding.text12.text = checkarrayselect!![i].toString()
                    mBinding.text13.text = checkarraygrade!![i].toString()
                    mBinding.text14.text = checkarrayscore!![i].toString()
                }
                if (i == 1) {
                    mBinding.text21.text = checkarraysubject!![i].toString()
                    mBinding.text22.text = checkarrayselect!![i].toString()
                    mBinding.text23.text = checkarraygrade!![i].toString()
                    mBinding.text24.text = checkarrayscore!![i].toString()
                }
                if (i == 2) {
                    mBinding.text31.text = checkarraysubject!![i].toString()
                    mBinding.text32.text = checkarrayselect!![i].toString()
                    mBinding.text33.text = checkarraygrade!![i].toString()
                    mBinding.text34.text = checkarrayscore!![i].toString()
                }
                if (i == 3) {
                    mBinding.text41.text = checkarraysubject!![i].toString()
                    mBinding.text42.text = checkarrayselect!![i].toString()
                    mBinding.text43.text = checkarraygrade!![i].toString()
                    mBinding.text44.text = checkarrayscore!![i].toString()
                }
            }
        }

        //수강과목추가 다이얼로그 구현
        mBinding.btnadddialog.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor = sqlDB.rawQuery("SELECT tmpStr FROM tmp;", null)
            cursor.moveToFirst()
            var semester: String = cursor.getString(0)
            cursor.close()
            sqlDB.close()
            if (semester != "...") {
                val dialogBinding = NewsubjectaddBinding.inflate(layoutInflater)
                val dialog = NewSubjectAdd()
                dialog.setOnDismissListener(object : NewSubjectAdd.OnDismissListener {
                    override fun onDialogDismissed() {
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT * FROM tmp",null)
                        cursor.moveToFirst()
                        var str : String = cursor.getString(0)
                        calculTotal()
                        calculMajor()
                        calculCulture()
                        if(str == "1학년 1학기") {
                            shortcut(1)
                        }else if(str == "1학년 2학기") {
                            shortcut(2)
                        }else if(str == "2학년 1학기") {
                            shortcut(3)
                        }else if(str == "2학년 2학기") {
                            shortcut(4)
                        }else if(str == "3학년 1학기") {
                            shortcut(5)
                        }else if(str == "3학년 2학기") {
                            shortcut(6)
                        }else if(str == "4학년 1학기") {
                            shortcut(7)
                        }else if(str == "4학년 2학기") {
                            shortcut(8)
                        }
                    }
                })
                dialog.show(activity?.supportFragmentManager!!, "")
            } else {
                Toast.makeText(activity, "학기를 선택하세요", Toast.LENGTH_SHORT).show()
            }
        }

        //학기 스피너
        mBinding.semesterspinner.adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.semester)
        )

        return mBinding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            GradeActivity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}