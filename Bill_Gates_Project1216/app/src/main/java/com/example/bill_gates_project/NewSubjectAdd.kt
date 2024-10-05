package com.example.bill_gates_project

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.example.bill_gates_project.databinding.GradeactivityBinding
import com.example.bill_gates_project.databinding.NewsubjectaddBinding
import com.example.bill_gates_project.myDBHelper


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewSubjectAdd : DialogFragment() {

    private var _binding: NewsubjectaddBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var param2: String? = null
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    interface OnDismissListener {
        fun onDialogDismissed()
    }

    private var dismissListener: OnDismissListener? = null

    fun setOnDismissListener(listener: OnDismissListener) {
        dismissListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var checkarraysubject = ArrayList<String>() //과목 저장
        var checkarrayselect = ArrayList<String>() //구분 저장
        var checkarraygrade = ArrayList<String>() //학점 저장
        var checkarrayscore = ArrayList<Int>() //점수 저장

        _binding = NewsubjectaddBinding.inflate(inflater, container, false)
        myHelper = myDBHelper(requireContext())

        var gradebinding = GradeactivityBinding.inflate(inflater, container, false)
        val view = binding.root

        sqlDB = myHelper.readableDatabase
        var cursor : Cursor = sqlDB.rawQuery("SELECT * FROM tmp",null)
        cursor.moveToFirst()
        var semester : String = cursor.getString(0)
        cursor.close()
        sqlDB.close()



        //완료 버튼
        binding.btncompleteadd.setOnClickListener {
            if (binding.CheckText1.isChecked) {
                sqlDB = myHelper.readableDatabase
                 var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text11.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1

                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text11.text.toString() + "','','','','" +
                            binding.text12.text.toString() + "','" +
                            binding.text13.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text14.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1;", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.readableDatabase
                cursor = sqlDB.rawQuery("SELECT * FROM grade WHERE crId = '" +
                        cr_Id + "' AND stId = '" +
                        st_Id + "';",null)
                if(!cursor.moveToFirst()) {
                    cursor.close()
                    sqlDB.close()
                    sqlDB = myHelper.writableDatabase

                    sqlDB.execSQL(
                        "INSERT INTO grade VALUES('" +
                                st_Id+ "','" +
                                cr_Id + "','" +
                                binding.text14.text.toString() + "','" +
                                binding.text13.text.toString() + "','" +
                                semester + "');"
                    )
                }else{
                    cursor.close()
                    sqlDB.close()
                }

                checkarraysubject.add(binding.text11.text.toString())
                checkarrayselect.add(binding.text12.text.toString())
                checkarrayscore.add(binding.text13.text.toString().toInt())
                sqlDB.close()
            }
            if (binding.CheckText2.isChecked) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text21.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text21.text.toString() + "','','','','" +
                            binding.text22.text.toString() + "','" +
                            binding.text23.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text24.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text24.text.toString() + "','" +
                            binding.text23.text.toString() + "','" +
                            semester +"');"
                )
                checkarraysubject.add(binding.text21.text.toString())
                checkarrayselect.add(binding.text22.text.toString())
                checkarrayscore.add(binding.text23.text.toString().toInt())
                sqlDB.close()
            }
            if (binding.CheckText3.isChecked) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text31.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text31.text.toString() + "','','','','" +
                            binding.text32.text.toString() + "','" +
                            binding.text33.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text34.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text34.text.toString() + "','" +
                            binding.text33.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text31.text.toString())
                checkarrayselect.add(binding.text32.text.toString())
                checkarrayscore.add(binding.text33.text.toString().toInt())
                sqlDB.close()
            }
            if (!binding.text41.text.isEmpty()) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text41.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text41.text.toString() + "','','','','" +
                            binding.text42.text.toString() + "','" +
                            binding.text43.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text44.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text44.text.toString() + "','" +
                            binding.text43.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text41.text.toString())
                checkarrayselect.add(binding.text42.text.toString())
                checkarrayscore.add(binding.text43.text.toString().toInt())
                sqlDB.close()
            }
            if (!binding.text51.text.isEmpty()) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text51.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text51.text.toString() + "','','','','" +
                            binding.text52.text.toString() + "','" +
                            binding.text53.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text54.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text54.text.toString() + "','" +
                            binding.text53.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text51.text.toString())
                checkarrayselect.add(binding.text52.text.toString())
                checkarrayscore.add(binding.text53.text.toString().toInt())
                sqlDB.close()
            }
            if (!binding.text61.text.isEmpty()) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text61.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text61.text.toString() + "','','','','" +
                            binding.text62.text.toString() + "','" +
                            binding.text63.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text64.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text64.text.toString() + "','" +
                            binding.text63.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text61.text.toString())
                checkarrayselect.add(binding.text62.text.toString())
                checkarrayscore.add(binding.text63.text.toString().toInt())
                sqlDB.close()
            }
            if (!binding.text71.text.isEmpty()) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text71.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text71.text.toString() + "','','','','" +
                            binding.text72.text.toString() + "','" +
                            binding.text73.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text74.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text74.text.toString() + "','" +
                            binding.text73.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text71.text.toString())
                checkarrayselect.add(binding.text72.text.toString())
                checkarrayscore.add(binding.text73.text.toString().toInt())
                sqlDB.close()
            }
            if (!binding.text81.text.isEmpty()) {
                sqlDB = myHelper.readableDatabase
                var cursor : Cursor = sqlDB.rawQuery(
                    "SELECT crId FROM course WHERE crName = '" +
                            binding.text81.text.toString() + "';", null
                )
                var cr_Id : String = ""
                if(cursor.moveToFirst()) {
                    cr_Id = cursor.getString(0)
                }else{
                    cursor = sqlDB.rawQuery("SELECT tmpi FROM tmpindex;",null)
                    cursor.moveToFirst()
                    var tmpIndex : Int = cursor.getInt(0) + 1
                    cr_Id = tmpIndex.toString()
                    sqlDB.execSQL("UPDATE tmpindex SET tmpi = " +
                            tmpIndex + ";")
                    sqlDB.execSQL("INSERT INTO course VALUES('" +
                            cr_Id + "','" +
                            binding.text81.text.toString() + "','','','','" +
                            binding.text82.text.toString() + "','" +
                            binding.text83.text.toString().toInt()+ "');")
                }
                checkarraygrade.add(binding.text84.text.toString())
                cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1", null)
                cursor.moveToFirst()
                var st_Id = cursor.getString(0)
                cursor.close()
                sqlDB.close()

                sqlDB = myHelper.writableDatabase

                sqlDB.execSQL(
                    "INSERT INTO grade VALUES('" +
                            st_Id+ "','" +
                            cr_Id + "','" +
                            binding.text84.text.toString() + "','" +
                            binding.text83.text.toString() + "','" +
                            semester + "');"
                )
                checkarraysubject.add(binding.text81.text.toString())
                checkarrayselect.add(binding.text82.text.toString())
                checkarrayscore.add(binding.text83.text.toString().toInt())
                sqlDB.close()
            }

            var passGradeActivity = GradeActivity()
            var bundle = Bundle()
            bundle.putStringArrayList("checkarraysubject", checkarraysubject)
            bundle.putStringArrayList("checkarrayselect", checkarrayselect)
            bundle.putStringArrayList("checkarraygrade", checkarraygrade)
            bundle.putIntegerArrayList("checkarrayscore", checkarrayscore)
            passGradeActivity.arguments = bundle

            activity?.supportFragmentManager!!.beginTransaction().commit()

            dismiss()
        }

        fun reSort(inputText1: CharSequence, inputText2: CharSequence, inputText3: CharSequence) : Int{
            if (binding.text11.text.isEmpty()) {
                binding.layout1.visibility = View.VISIBLE
                binding.text11.text = inputText1
                binding.text12.text = inputText2
                binding.text13.text = inputText3
                return 1
            } else if (binding.text21.text.isEmpty()) {
                binding.layout2.visibility = View.VISIBLE
                binding.text21.text = inputText1
                binding.text22.text = inputText2
                binding.text23.text = inputText3
                return 1
            } else if (binding.text31.text.isEmpty()) {
                binding.layout3.visibility = View.VISIBLE
                binding.text31.text = inputText1
                binding.text32.text = inputText2
                binding.text33.text = inputText3
                return 1
            } else if (binding.text41.text.isEmpty()) {
                binding.layout4.visibility = View.VISIBLE
                binding.text41.text = inputText1
                binding.text42.text = inputText2
                binding.text43.text = inputText3
                return 1
            } else if (binding.text51.text.isEmpty()) {
                binding.layout5.visibility = View.VISIBLE
                binding.text51.text = inputText1
                binding.text52.text = inputText2
                binding.text53.text = inputText3
                return 1
            } else if (binding.text61.text.isEmpty()) {
                binding.layout6.visibility = View.VISIBLE
                binding.text61.text = inputText1
                binding.text62.text = inputText2
                binding.text63.text = inputText3
                return 1
            } else if (binding.text71.text.isEmpty()) {
                binding.layout7.visibility = View.VISIBLE
                binding.text71.text = inputText1
                binding.text72.text = inputText2
                binding.text73.text = inputText3
                return 1
            }else{
                return 0
            }
        }

        //과목 삭제 버튼
        binding.btndeletesubject.setOnClickListener {
            if (binding.CheckText1.isChecked) {
                binding.layout1.visibility = View.INVISIBLE
                binding.text11.text = ""
                binding.text12.text = ""
                binding.text13.text = ""
            }
            if (binding.CheckText2.isChecked) {
                binding.layout2.visibility = View.INVISIBLE
                binding.text21.text = ""
                binding.text22.text = ""
                binding.text23.text = ""
            }
            if (binding.CheckText3.isChecked) {
                binding.layout3.visibility = View.INVISIBLE
                binding.text31.text = ""
                binding.text32.text = ""
                binding.text33.text = ""
            }
            if (binding.CheckText4.isChecked) {
                binding.layout4.visibility = View.INVISIBLE
                binding.text41.text = ""
                binding.text42.text = ""
                binding.text43.text = ""
            }
            if (binding.CheckText5.isChecked) {
                binding.layout5.visibility = View.INVISIBLE
                binding.text51.text = ""
                binding.text52.text = ""
                binding.text53.text = ""
            }
            if (binding.CheckText6.isChecked) {
                binding.layout6.visibility = View.INVISIBLE
                binding.text61.text = ""
                binding.text62.text = ""
                binding.text63.text = ""
            }
            if (binding.CheckText7.isChecked) {
                binding.layout7.visibility = View.INVISIBLE
                binding.text71.text = ""
                binding.text72.text = ""
                binding.text73.text = ""
            }
            if (binding.CheckText8.isChecked) {
                binding.layout8.visibility = View.INVISIBLE
                binding.text81.text = ""
                binding.text82.text = ""
                binding.text83.text = ""
            }

            binding.CheckText1.isChecked = false
            binding.CheckText2.isChecked = false
            binding.CheckText3.isChecked = false
            binding.CheckText4.isChecked = false
            binding.CheckText5.isChecked = false
            binding.CheckText6.isChecked = false
            binding.CheckText7.isChecked = false
            binding.CheckText8.isChecked = false

            if (!binding.text21.text.isEmpty()&&
                binding.text11.text.isEmpty()) {
                var flag : Int = reSort(binding.text21.text, binding.text22.text, binding.text23.text)
                if(flag == 1) {
                    binding.layout2.visibility = View.INVISIBLE
                    binding.text21.text = ""
                    binding.text22.text = ""
                    binding.text23.text = ""
                }
            }
            if (!binding.text31.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text31.text, binding.text32.text, binding.text33.text)
                if(flag == 1) {
                    binding.layout3.visibility = View.INVISIBLE
                    binding.text31.text = ""
                    binding.text32.text = ""
                    binding.text33.text = ""
                }
            }
            if (!binding.text41.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()||
                    binding.text31.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text41.text, binding.text42.text, binding.text43.text)
                if(flag == 1) {
                    binding.layout4.visibility = View.INVISIBLE
                    binding.text41.text = ""
                    binding.text42.text = ""
                    binding.text43.text = ""
                }
            }
            if (!binding.text51.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()||
                    binding.text31.text.isEmpty()||
                    binding.text41.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text51.text, binding.text52.text, binding.text53.text)
                if(flag == 1) {
                    binding.layout5.visibility = View.INVISIBLE
                    binding.text51.text = ""
                    binding.text52.text = ""
                    binding.text53.text = ""
                }
            }
            if (!binding.text61.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()||
                    binding.text31.text.isEmpty()||
                    binding.text41.text.isEmpty()||
                    binding.text51.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text61.text, binding.text62.text, binding.text63.text)
                if(flag == 1) {
                    binding.layout6.visibility = View.INVISIBLE
                    binding.text61.text = ""
                    binding.text62.text = ""
                    binding.text63.text = ""
                }
            }
            if (!binding.text71.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()||
                    binding.text31.text.isEmpty()||
                    binding.text41.text.isEmpty()||
                    binding.text51.text.isEmpty()||
                    binding.text61.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text71.text, binding.text72.text, binding.text73.text)
                if(flag == 1) {
                    binding.layout7.visibility = View.INVISIBLE
                    binding.text71.text = ""
                    binding.text72.text = ""
                    binding.text73.text = ""
                }
            }
            if(!binding.text81.text.isEmpty()&&(
                    binding.text11.text.isEmpty()||
                    binding.text21.text.isEmpty()||
                    binding.text31.text.isEmpty()||
                    binding.text41.text.isEmpty()||
                    binding.text51.text.isEmpty()||
                    binding.text61.text.isEmpty()||
                    binding.text71.text.isEmpty()
                    )) {
                var flag : Int = reSort(binding.text81.text, binding.text82.text, binding.text83.text)
                if(flag == 1) {
                    binding.layout8.visibility = View.INVISIBLE
                    binding.text81.text = ""
                    binding.text82.text = ""
                    binding.text83.text = ""
                }
            }
        }

        //과목 스피너
        binding.subjectspinner.adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.project)
        )

        //구분 스피너
        binding.selectspinner.adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.select)
        )

        //학점 스피너
        binding.gradespinner.adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.grade)
        )

        //다이얼로그 과목 추가 버튼
        binding.btnaddsubject.setOnClickListener {

            var subjecttext = binding.subjectspinner.getSelectedItem().toString()
            var selecttext = binding.selectspinner.getSelectedItem().toString()
            var gradetext = binding.gradespinner.getSelectedItem().toString()
            var textAddSubject = binding.textaddsubject.text.toString()

            if (!binding.textaddsubject.text.isEmpty()) {
                if (binding.text11.text.isEmpty()) {
                    binding.layout1.visibility = View.VISIBLE
                    binding.text11.text = textAddSubject
                    binding.text12.text = selecttext
                    binding.text13.text = binding.textnumscore.text.toString()
                    binding.text14.text = gradetext
                } else if (binding.text21.text.isEmpty() &&
                    binding.text11.text != textAddSubject
                ) {
                    binding.layout2.visibility = View.VISIBLE
                    binding.text21.text = textAddSubject
                    binding.text22.text = selecttext
                    binding.text23.text = binding.textnumscore.text.toString()
                    binding.text24.text = gradetext
                } else if (binding.text31.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject
                ) {
                    binding.layout3.visibility = View.VISIBLE
                    binding.text31.text = textAddSubject
                    binding.text32.text = selecttext
                    binding.text33.text = binding.textnumscore.text.toString()
                    binding.text34.text = gradetext
                } else if (binding.text41.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject &&
                    binding.text31.text != textAddSubject
                ) {
                    binding.layout4.visibility = View.VISIBLE
                    binding.text41.text = textAddSubject
                    binding.text42.text = selecttext
                    binding.text43.text = binding.textnumscore.text.toString()
                    binding.text44.text = gradetext
                } else if (binding.text51.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject &&
                    binding.text31.text != textAddSubject &&
                    binding.text41.text != textAddSubject
                ) {
                    binding.layout5.visibility = View.VISIBLE
                    binding.text51.text = textAddSubject
                    binding.text52.text = selecttext
                    binding.text53.text = binding.textnumscore.text.toString()
                    binding.text54.text = gradetext
                } else if (binding.text61.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject &&
                    binding.text31.text != textAddSubject &&
                    binding.text41.text != textAddSubject &&
                    binding.text51.text != textAddSubject
                ) {
                    binding.layout6.visibility = View.VISIBLE
                    binding.text61.text = textAddSubject
                    binding.text62.text = selecttext
                    binding.text63.text = binding.textnumscore.text.toString()
                    binding.text64.text = gradetext
                } else if (binding.text71.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject &&
                    binding.text31.text != textAddSubject &&
                    binding.text41.text != textAddSubject &&
                    binding.text51.text != textAddSubject &&
                    binding.text61.text != textAddSubject
                ) {
                    binding.layout7.visibility = View.VISIBLE
                    binding.text71.text = textAddSubject
                    binding.text72.text = selecttext
                    binding.text73.text = binding.textnumscore.text.toString()
                    binding.text74.text = gradetext
                } else if (binding.text81.text.isEmpty() &&
                    binding.text11.text != textAddSubject &&
                    binding.text21.text != textAddSubject &&
                    binding.text31.text != textAddSubject &&
                    binding.text41.text != textAddSubject &&
                    binding.text51.text != textAddSubject &&
                    binding.text61.text != textAddSubject &&
                    binding.text71.text != textAddSubject
                ) {
                    binding.layout8.visibility = View.VISIBLE
                    binding.text81.text = textAddSubject
                    binding.text82.text = selecttext
                    binding.text83.text = binding.textnumscore.text.toString()
                    binding.text84.text = gradetext
                } else {
                    if (!binding.text11.text.isEmpty() &&
                        !binding.text21.text.isEmpty() &&
                        !binding.text31.text.isEmpty() &&
                        !binding.text41.text.isEmpty() &&
                        !binding.text51.text.isEmpty() &&
                        !binding.text61.text.isEmpty() &&
                        !binding.text71.text.isEmpty() &&
                        !binding.text81.text.isEmpty()
                    ) {
                        Toast.makeText(activity, "더 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, "이미 추가된 과목입니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                var isinput = false
                if (isinput == false) {
                    if (binding.text11.text.isEmpty()) {
                        binding.layout1.visibility = View.VISIBLE
                        binding.text11.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text12.text = cursor.getString(0)
                            binding.text13.text = cursor.getString(1)
                        }else{
                            binding.text12.text = selecttext
                            binding.text13.text = binding.textnumscore.text.toString()
                        }
                        binding.text14.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text21.text.isEmpty() &&
                        binding.text11.text != subjecttext
                    ) {
                        binding.layout2.visibility = View.VISIBLE
                        binding.text21.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text22.text = cursor.getString(0)
                            binding.text23.text = cursor.getString(1)
                        }else{
                            binding.text22.text = selecttext
                            binding.text23.text = binding.textnumscore.text.toString()
                        }
                        binding.text24.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text31.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext
                    ) {
                        binding.layout3.visibility = View.VISIBLE
                        binding.text31.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text32.text = cursor.getString(0)
                            binding.text33.text = cursor.getString(1)
                        }else{
                            binding.text32.text = selecttext
                            binding.text33.text = binding.textnumscore.text.toString()
                        }
                        binding.text34.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text41.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext &&
                        binding.text31.text != subjecttext
                    ) {
                        binding.layout4.visibility = View.VISIBLE
                        binding.text41.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text42.text = cursor.getString(0)
                            binding.text43.text = cursor.getString(1)
                        }else{
                            binding.text42.text = selecttext
                            binding.text43.text = binding.textnumscore.text.toString()
                        }
                        binding.text44.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text51.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext &&
                        binding.text31.text != subjecttext &&
                        binding.text41.text != subjecttext
                    ) {
                        binding.layout5.visibility = View.VISIBLE
                        binding.text51.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text52.text = cursor.getString(0)
                            binding.text53.text = cursor.getString(1)
                        }else{
                            binding.text52.text = selecttext
                            binding.text53.text = binding.textnumscore.text.toString()
                        }
                        binding.text54.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text61.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext &&
                        binding.text31.text != subjecttext &&
                        binding.text41.text != subjecttext &&
                        binding.text51.text != subjecttext
                    ) {
                        binding.layout6.visibility = View.VISIBLE
                        binding.text61.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text62.text = cursor.getString(0)
                            binding.text63.text = cursor.getString(1)
                        }else{
                            binding.text62.text = selecttext
                            binding.text63.text = binding.textnumscore.text.toString()
                        }
                        binding.text64.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text71.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext &&
                        binding.text31.text != subjecttext &&
                        binding.text41.text != subjecttext &&
                        binding.text51.text != subjecttext &&
                        binding.text61.text != subjecttext
                    ) {
                        binding.layout7.visibility = View.VISIBLE
                        binding.text71.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text72.text = cursor.getString(0)
                            binding.text73.text = cursor.getString(1)
                        }else{
                            binding.text72.text = selecttext
                            binding.text73.text = binding.textnumscore.text.toString()
                        }
                        binding.text74.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else if (binding.text81.text.isEmpty() &&
                        binding.text11.text != subjecttext &&
                        binding.text21.text != subjecttext &&
                        binding.text31.text != subjecttext &&
                        binding.text41.text != subjecttext &&
                        binding.text51.text != subjecttext &&
                        binding.text61.text != subjecttext &&
                        binding.text71.text != subjecttext
                    ) {
                        binding.layout8.visibility = View.VISIBLE
                        binding.text81.text = subjecttext
                        sqlDB = myHelper.readableDatabase
                        var cursor : Cursor = sqlDB.rawQuery("SELECT crType,crScore FROM course WHERE crName = '" +
                                subjecttext + "'",null)
                        if(cursor.moveToFirst()) {
                            binding.text82.text = cursor.getString(0)
                            binding.text83.text = cursor.getString(1)
                        }else{
                            binding.text82.text = selecttext
                            binding.text83.text = binding.textnumscore.text.toString()
                        }
                        binding.text84.text = gradetext
                        isinput = true
                        cursor.close()
                        sqlDB.close()
                    } else {
                        if (!binding.text11.text.isEmpty() &&
                            !binding.text21.text.isEmpty() &&
                            !binding.text31.text.isEmpty() &&
                            !binding.text41.text.isEmpty() &&
                            !binding.text51.text.isEmpty() &&
                            !binding.text61.text.isEmpty() &&
                            !binding.text71.text.isEmpty() &&
                            !binding.text81.text.isEmpty()
                        ) {
                            Toast.makeText(activity, "더 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(activity, "이미 추가된 과목입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDialogDismissed()
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            NewSubjectAdd().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}