package com.example.bill_gates_project

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.bill_gates_project.databinding.BbbFragmentFirstGradeBinding
import com.example.bill_gates_project.databinding.BbbFragmentFourthGradeBinding
import com.example.bill_gates_project.databinding.BbbFragmentSecondGradeBinding
import com.example.bill_gates_project.databinding.BbbFragmentThirdGradeBinding
import com.google.android.material.navigation.NavigationView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FirstGrade : Fragment() {
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

        myHelper = myDBHelper(requireContext())
        var contentview=inflater.inflate(R.layout.aaa_firstgrade, container, false)

        var checktext1=contentview.findViewById<CheckedTextView>(R.id.checktext1)
        var checktext2=contentview.findViewById<CheckedTextView>(R.id.checktext2)
        var checktext3=contentview.findViewById<CheckedTextView>(R.id.checktext3)
        var checktext4=contentview.findViewById<CheckedTextView>(R.id.checktext4)
        var checktext5=contentview.findViewById<CheckedTextView>(R.id.checktext5)
        var checktext6=contentview.findViewById<CheckedTextView>(R.id.checktext6)
        var checktext7=contentview.findViewById<CheckedTextView>(R.id.checktext7)

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
            navigationView.inflateMenu(R.menu.math1)
            navigationView.inflateHeaderView(R.layout.contents_grade1_math1)
        }
        checktext2.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.computersciencecoding)
            navigationView.inflateHeaderView(R.layout.contents_grade1_computersciencecoding)
        }
        checktext3.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.cbasic)
            navigationView.inflateHeaderView(R.layout.contents_grade1_cbasic)
        }
        checktext4.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.introducecomputerengineering)
            navigationView.inflateHeaderView(R.layout.contents_grade1_introducecomputerengineering)
        }
        checktext5.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.math2)
            navigationView.inflateHeaderView(R.layout.contents_grade1_math2)
            if(checktext1.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade1_txt1).text="(수강함)"
            }
        }
        checktext6.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.cplusprograming)
            navigationView.inflateHeaderView(R.layout.contents_grade1_cplusprograming)
            if(checktext3.isChecked)
            {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.contents_grade1_txt1).text="(수강함)"
            }
        }
        checktext7.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.menu.clear()
            navigationView.removeHeaderView(navigationView.getHeaderView(0))
            navigationView.inflateMenu(R.menu.discretemathematics)
            navigationView.inflateHeaderView(R.layout.contents_grade1_discretemathmatics)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    val itemName = menuItem.title
                    var uri= Uri.parse(itemName.toString())
                    var intent= Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                R.id.menu_item2 -> {
                    val itemName = menuItem.title
                    var uri= Uri.parse(itemName.toString())
                    var intent= Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                R.id.menu_item3 -> {
                    val itemName = menuItem.title
                    var uri= Uri.parse(itemName.toString())
                    var intent= Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            true
        }

        return contentview
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            FirstGrade().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}