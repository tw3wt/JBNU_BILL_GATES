package com.example.bill_gates_project

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import java.util.Objects

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Graduate : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var myHelper : myDBHelper
    lateinit var sqlDB : SQLiteDatabase
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
        // Inflate the layout for this fragment
    
        val contentView: View = inflater.inflate(R.layout.graduate, container, false)

        var spinner=contentView.findViewById<Spinner>(R.id.spinner)

        var toeicComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.Toeic_700)
        var cbtComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.CBT_197)
        var pbtComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.PBT_529)
        var ibtComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.IBT_82)
        var tepsComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.TEPS_494)
        var speakingComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.Speakinglevel6)
        var opicComplete : CheckBox =contentView.findViewById<CheckBox>(R.id.OPIc)
        var gtelpComplete : CheckBox =contentView.findViewById<CheckBox>(R.id.G_TELP)
        var newtepsComplete : CheckBox = contentView.findViewById<CheckBox>(R.id.NEW_TEPS)
        
        spinner.adapter = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Subject))

        spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var englishLayout=contentView.findViewById<LinearLayout>(R.id.englishlayout)
                var certificateLayout=contentView.findViewById<LinearLayout>(R.id.certificatelayout)
                var practiceLayout=contentView.findViewById<LinearLayout>(R.id.practicelayout)
                
                when(position){
                    0->{
                        englishLayout.isVisible=true
                        certificateLayout.isVisible=false
                        practiceLayout.isVisible=false
                    }
                    1->{
                        englishLayout.isVisible=false
                        certificateLayout.isVisible=true
                        practiceLayout.isVisible=false
                    }
                    2->{
                        englishLayout.isVisible=false
                        certificateLayout.isVisible=false
                        practiceLayout.isVisible=true
                    }
                }
                
            }
        }

        var btnNotification=contentView.findViewById<Button>(R.id.notification)
        var btnEmployment=contentView.findViewById<Button>(R.id.employment)
        var btnEtc=contentView.findViewById<Button>(R.id.etc)

        btnNotification.setOnClickListener{
            var uri= Uri.parse("https://csai.jbnu.ac.kr/csai/29107/subview.do")
            var intent=Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        btnEmployment.setOnClickListener{
            var uri= Uri.parse("https://csai.jbnu.ac.kr/csai/29108/subview.do")
            var intent=Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        
        myHelper = myDBHelper(requireContext())

        sqlDB = myHelper.readableDatabase

        var cursor : Cursor = sqlDB.rawQuery("SELECT * FROM graduate",null)

        cursor.moveToFirst()
        if(cursor.getInt(0) == 1) {
            toeicComplete.isChecked = true
        }else{
            toeicComplete.isChecked = false
        }
        //cursor.moveToNext()
        if(cursor.getInt(1) == 1) {
            cbtComplete.isChecked = true
        }else{
            cbtComplete.isChecked = false
        }
        if(cursor.getInt(2) == 1) {
            pbtComplete.isChecked = true
        }else{
            pbtComplete.isChecked = false
        }
        if(cursor.getInt(3) == 1) {
            ibtComplete.isChecked = true
        }else {
            ibtComplete.isChecked = false
        }
        if(cursor.getInt(4) == 1) {
            tepsComplete.isChecked = true
        }else {
            tepsComplete.isChecked = false
        }
        if(cursor.getInt(5) == 1) {
            speakingComplete.isChecked = true
        }else{
            speakingComplete.isChecked = false
        }
        if(cursor.getInt(6) == 1) {
            opicComplete.isChecked = true
        }else{
            opicComplete.isChecked = false
        }
        if(cursor.getInt(7) == 1) {
            gtelpComplete.isChecked = true
        }else{
            gtelpComplete.isChecked = false
        }
        if(cursor.getInt(8) == 1) {
            newtepsComplete.isChecked = true
        }else{
            newtepsComplete.isChecked = false
        }
        cursor.close()
        sqlDB.close()
        
        toeicComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(toeicComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET toeic = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET toeic = 0")
            }
            sqlDB.close()
        }

        cbtComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(cbtComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET cbt = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET cbt = 0")
            }
            sqlDB.close()
        }

        pbtComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(pbtComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET pbt = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET pbt = 0")
            }
            sqlDB.close()
        }

        ibtComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(ibtComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET ibt = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET ibt = 0")
            }
            sqlDB.close()
        }

        tepsComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(tepsComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET teps = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET teps = 0")
            }
            sqlDB.close()
        }

        speakingComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(speakingComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET toeicSpeaking = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET toeicSpeaking = 0")
            }
            sqlDB.close()
        }

        opicComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(opicComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET opic = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET opic = 0")
            }
            sqlDB.close()
        }

        gtelpComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(gtelpComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET gtelp = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET gtelp = 0")
            }
            sqlDB.close()
        }

        newtepsComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            sqlDB = myHelper.writableDatabase
            if(newtepsComplete.isChecked) {
                sqlDB.execSQL("UPDATE graduate SET newteps = 1")
            }else{
                sqlDB.execSQL("UPDATE graduate SET newteps = 0")
            }
            sqlDB.close()
        }



        return contentView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Menu4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Graduate().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}