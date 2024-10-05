package com.example.bill_gates_project

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ThirdGrade : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var navigationView:NavigationView
    lateinit var  drawerLayout:DrawerLayout
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

        var contentview=inflater.inflate(R.layout.aaa_thirdgrade, container, false)

        var contentviewfirst=inflater.inflate(R.layout.aaa_firstgrade, container, false)
        var contentviewsecond=inflater.inflate(R.layout.aaa_secondgrade, container, false)

        myHelper = myDBHelper(requireContext())
        var checktext1=contentview.findViewById<CheckedTextView>(R.id.checktext1)
        var checktext2=contentview.findViewById<CheckedTextView>(R.id.checktext2)
        var checktext3=contentview.findViewById<CheckedTextView>(R.id.checktext3)
        var checktext4=contentview.findViewById<CheckedTextView>(R.id.checktext4)
        var checktext5=contentview.findViewById<CheckedTextView>(R.id.checktext5)
        var checktext6=contentview.findViewById<CheckedTextView>(R.id.checktext6)
        var checktext7=contentview.findViewById<CheckedTextView>(R.id.checktext7)
        var checktext8=contentview.findViewById<CheckedTextView>(R.id.checktext8)
        var checktext9=contentview.findViewById<CheckedTextView>(R.id.checktext9)
        var checktext10=contentview.findViewById<CheckedTextView>(R.id.checktext10)
        var checktext11=contentview.findViewById<CheckedTextView>(R.id.checktext11)
        var checktext12=contentview.findViewById<CheckedTextView>(R.id.checktext12)
        var checktext13=contentview.findViewById<CheckedTextView>(R.id.checktext13)
        var checktext14=contentview.findViewById<CheckedTextView>(R.id.checktext14)

        fun editToCheck(str : String) {
            if(checktext1.text == str) {
                checktext1.isChecked = true
            }
            if(checktext2.text == str) {
                checktext2.isChecked = true
            }
            if(checktext3.text == str) {
                checktext3.isChecked = true
            }
            if(checktext4.text == str) {
                checktext4.isChecked = true
            }
            if(checktext5.text == str) {
                checktext5.isChecked = true
            }
            if(checktext6.text == str) {
                checktext6.isChecked = true
            }
            if(checktext7.text == str) {
                checktext7.isChecked = true
            }
            if(checktext8.text == str) {
                checktext8.isChecked = true
            }
            if(checktext9.text == str) {
                checktext9.isChecked = true
            }
            if(checktext10.text == str) {
                checktext10.isChecked = true
            }
            if(checktext11.text == str) {
                checktext11.isChecked = true
            }
            if(checktext12.text == str) {
                checktext12.isChecked = true
            }
            if(checktext13.text == str) {
                checktext13.isChecked = true
            }
            if(checktext14.text == str) {
                checktext14.isChecked = true
            }

        }

        sqlDB = myHelper.readableDatabase
        var cursor : Cursor = sqlDB.rawQuery("SELECT stId FROM student WHERE stLogging = 1",null)
        cursor.moveToFirst()
        var stId=cursor.getString(0)
        cursor = sqlDB.rawQuery("SELECT crId FROM grade WHERE stId = '" +
                stId + "';",null)
        var crId = ArrayList<String>()
        var i = 0
        if(cursor.moveToFirst()) {
            crId.add(cursor.getString(0))
            i=1
            while (cursor.moveToNext()) {
                crId.add(cursor.getString(0))
                i++
            }
        }
        for(j in 0..i-1) {
            cursor = sqlDB.rawQuery("SELECT crName FROM course WHERE crId = '" +
                    crId[j] + "';",null)
            if(cursor.moveToFirst()) {
                editToCheck(cursor.getString(0))
                while (cursor.moveToNext()) {
                    editToCheck(cursor.getString(0))
                }
            }
        }

        drawerLayout=contentview.findViewById(R.id.drawer_layout)

        navigationView=contentview.findViewById(R.id.nav_view)

        checktext1.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.numericalanalysis)
            navigationView.inflateHeaderView(R.layout.contents_grade3_numericalanalysis)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext1).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext2.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.ai)
            navigationView.inflateHeaderView(R.layout.contents_grade3_ai)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext7).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext1).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
            if(contentviewfirst.findViewById<CheckedTextView>(R.id.checktext2).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt3).text="(수강함)"
            }
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext8).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt4).text="(수강함)"
            }
        }
        checktext3.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.database)
            navigationView.inflateHeaderView(R.layout.contents_grade3_database)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext8).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext1).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }
        checktext4.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.programinglanguage)
            navigationView.inflateHeaderView(R.layout.contents_grade3_programinglanguage)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext10).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext5.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.operatingsystem)
            navigationView.inflateHeaderView(R.layout.contents_grade3_operatingsystem)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext11).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext6).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }
        checktext6.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.computernetwork)
            navigationView.inflateHeaderView(R.layout.contents_grade3_computernetwork)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext12).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext7.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.computergraphics)
            navigationView.inflateHeaderView(R.layout.contents_grade3_computergraphics)
            if(checktext1.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext8.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.machinepractice)
            navigationView.inflateHeaderView(R.layout.contents_grade3_machinepractice)
            if(checktext2.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext9.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.datamining)
            navigationView.inflateHeaderView(R.layout.contents_grade3_datamining)
            if(checktext2.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(checktext3.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }
        checktext10.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.webservice)
            navigationView.inflateHeaderView(R.layout.contents_grade3_webservice)
            if(checktext3.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext9).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }
        checktext11.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.software)
            navigationView.inflateHeaderView(R.layout.contents_grade3_software)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext4).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext12.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.digitalsystem)
            navigationView.inflateHeaderView(R.layout.contents_grade3_digitalsystem)
            if(contentviewsecond.findViewById<CheckedTextView>(R.id.checktext11).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
        }
        checktext13.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.cloudcomputing)
            navigationView.inflateHeaderView(R.layout.contents_grade3_cloudcomputing)
            if(checktext6.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(checktext5.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }
        checktext14.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.cryptography)
            navigationView.inflateHeaderView(R.layout.contents_grade3_cryptography)
            if(contentviewfirst.findViewById<CheckedTextView>(R.id.checktext6).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt1).text="(수강함)"
            }
            if(contentviewfirst.findViewById<CheckedTextView>(R.id.checktext7).isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade3_txt2).text="(수강함)"
            }
        }

        return contentview
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            SecondGrade().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}