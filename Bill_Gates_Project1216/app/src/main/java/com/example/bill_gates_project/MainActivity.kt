package com.example.bill_gates_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val frame: FrameLayout by lazy{
        findViewById(R.id.main_frm)
    }

    lateinit var sqlDB : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var FragmentManager=supportFragmentManager
        var gradefragment=FragmentManager.findFragmentById(R.id.grade)
        var spinner=gradefragment?.activity?.findViewById<Spinner>(R.id.spinner)
        spinner?.adapter = ArrayAdapter.createFromResource(this, R.array.Subject, android.R.layout.simple_spinner_item)

        val bnv_main=findViewById<BottomNavigationView>(R.id.main_bnv)
        bnv_main.setOnItemSelectedListener{item->
            changeFragment(
                when(item.itemId)
                {
                    R.id.roadmap->
                    {
                        MainView()
                    }
                    R.id.grade->
                    {
                        GradeActivity()
                    }
                    R.id.menu3->
                    {
                        Rank()
                    }
                    else->{
                       Option()
                    }
                }
            )
            true
        }
        bnv_main.selectedItemId=R.id.roadmap
        }


    private fun changeFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, fragment).commit()
    }
}