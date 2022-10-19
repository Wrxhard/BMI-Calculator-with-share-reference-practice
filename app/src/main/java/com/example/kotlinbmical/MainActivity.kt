package com.example.kotlinbmical

import android.R.attr.button
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackgroundTintList


class MainActivity : AppCompatActivity() {
    lateinit var rescore:TextView
    lateinit var weight:TextView
    lateinit var height:TextView
    lateinit var maleButton:Button
    lateinit var femaleButton:Button
    var hvalue=""
    var wvalue=""
    var MBcolor=R.color.notSelected
    var FBcolor=R.color.notSelected
    lateinit var sf:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    enum class GENDER{
        MALE,FEMALE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sf=getSharedPreferences("BMI_score", MODE_PRIVATE)
        editor=sf.edit()
        var selectedGENDER : GENDER?=null
        maleButton=findViewById<Button>(R.id.MaleButton)
        femaleButton=findViewById<Button>(R.id.FemaleButton)
        val Wadd=findViewById<ImageButton>(R.id.addbuttonWeight)
        val Wminus=findViewById<ImageButton>(R.id.minusbuttonWeight)
        val Hadd=findViewById<ImageButton>(R.id.addbuttonHeight)
        val Hminus=findViewById<ImageButton>(R.id.minusbuttonHeight)
        height=findViewById<TextView>(R.id.height)
        height.text=height.text.toString()
        weight=findViewById<TextView>(R.id.weight)
        weight.text=weight.text.toString()
        val res=findViewById<Button>(R.id.CalculateButton)
        rescore=findViewById<TextView>(R.id.resScore)
        val overall=findViewById<TextView>(R.id.overall)
        val advise=findViewById<TextView>(R.id.advise)
        maleButton.setOnClickListener {
            selectedGENDER=GENDER.MALE
            if (selectedGENDER==GENDER.MALE)
            {
                MBcolor=R.color.selected
                FBcolor=R.color.notSelected
                maleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,MBcolor));
                femaleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,FBcolor));
            }
        }
        femaleButton.setOnClickListener {
            selectedGENDER=GENDER.FEMALE
            if (selectedGENDER==GENDER.FEMALE)
            {
                FBcolor=R.color.selected
                MBcolor=R.color.notSelected
                femaleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,FBcolor));
                maleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,MBcolor));
            }
        }
        Wadd.setOnClickListener {
            if (wvalue.toInt()<999){
                wvalue=(wvalue.toInt()+1).toString()
                weight.text = (wvalue.toInt()).toString()
            }
            else
            {
                Toast.makeText(this,"Can't add more",Toast.LENGTH_SHORT).show()
            }
        }
        Wminus.setOnClickListener {
            if (wvalue.toInt()>0){
                wvalue=(wvalue.toInt()-1).toString()
                weight.text = (wvalue.toInt()).toString()
            }
            else
            {
                Toast.makeText(this,"Weight must bigger than zero",Toast.LENGTH_SHORT).show()
            }
        }
        Hadd.setOnClickListener {
            if (hvalue.toInt()<300){
                hvalue=(hvalue.toInt()+1).toString()
                height.text = (hvalue.toInt()).toString()
            }
            else
            {
                Toast.makeText(this,"Can't add more height",Toast.LENGTH_SHORT).show()
            }
        }
        Hminus.setOnClickListener {
            if (hvalue.toInt()>0){
                hvalue=(hvalue.toInt()-1).toString()
                height.text = (hvalue.toInt()).toString()
            }
            else
            {
                Toast.makeText(this,"Height must bigger than zero",Toast.LENGTH_SHORT).show()
            }
        }
        res.setOnClickListener {
            val score=wvalue.toInt()/Math.pow(hvalue.toInt().toDouble()/100,2.0)
            println(wvalue.toDouble())
            println(hvalue.toDouble())
            rescore.text=(Math.floor(score*10)/10).toString()
        }

    }

    override fun onPause() {
        super.onPause()
        val mBcolor= MBcolor
        val fBcolor=FBcolor
        val res=rescore.text.toString()
        val w=weight.text.toString()
        val h=height.text.toString()
        editor.apply()
        {
            putString("sf_res",res)
            putString("sf_w",w)
            putString("sf_h",h)
            putInt("sf_mBcolor",mBcolor)
            putInt("sf_fBcolor",fBcolor)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        val res=sf.getString("sf_res","24.2")
        val w=sf.getString("sf_w","70")
        val h=sf.getString("sf_h","170")
        val McolorB=sf.getInt("sf_mBcolor",R.color.notSelected)
        val FcolorB=sf.getInt("sf_fBcolor",R.color.notSelected)
        maleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,McolorB));
        femaleButton.setBackgroundTintList(ContextCompat.getColorStateList(this,FcolorB));
        MBcolor=McolorB
        FBcolor=FcolorB
        rescore.text=res
        weight.text=w
        height.text=h
        wvalue=w.toString()
        hvalue=h.toString()
    }
}