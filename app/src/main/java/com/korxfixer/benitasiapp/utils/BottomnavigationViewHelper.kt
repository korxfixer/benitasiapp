package com.korxfixer.benitasiapp.utils

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.korxfixer.benitasiapp.Home.HomeActivity
import com.korxfixer.benitasiapp.News.NewsActivity
import com.korxfixer.benitasiapp.Profile.ProfileActivity
import com.korxfixer.benitasiapp.R
import com.korxfixer.benitasiapp.Search.SearchActivity
import com.korxfixer.benitasiapp.Share.ShareActivity
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx


class BottomnavigationViewHelper {

    companion object {

        fun setupBottomNavigationView(bottomnavigationViewEx: BottomNavigationViewEx){
            bottomnavigationViewEx.enableAnimation(false)
            //bottomnavigationViewEx.enableItemShiftingMode(false)
           //
            //  bottomnavigationViewEx.enableShiftingMode(false)
            bottomnavigationViewEx.setTextVisibility(false)
        }

        fun setupNavigation(context: Context, bottomnavigationViewEx: BottomNavigationViewEx, hangiActivity:Int){

            bottomnavigationViewEx.onNavigationItemSelectedListener=object : BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {

                    when(item.itemId){

                        R.id.ic_home -> {
                            if (hangiActivity != 0) {
                                val intent = Intent(context, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                context.startActivity(intent)
                                (context as AppCompatActivity).overridePendingTransition(0,0)
                                return true
                            }

                        }

                        R.id.ic_search -> {
                            if (hangiActivity != 1) {
                                val intent = Intent(context, SearchActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                context.startActivity(intent)
                                (context as AppCompatActivity).overridePendingTransition(0,0)
                                return true
                            }
                        }

                        R.id.ic_share -> {
                            if (hangiActivity != 2) {
                                val intent = Intent(context, ShareActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                context.startActivity(intent)
                                (context as AppCompatActivity).overridePendingTransition(0,0)
                                return true
                            }

                        }

                        R.id.ic_news -> {
                            if (hangiActivity != 3) {
                                val intent = Intent(context, NewsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                context.startActivity(intent)
                                (context as AppCompatActivity).overridePendingTransition(0,0)
                                return true
                            }

                        }

                        R.id.ic_profile -> {
                            if (hangiActivity != 4) {
                                val intent = Intent(context, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                context.startActivity(intent)
                                (context as AppCompatActivity).overridePendingTransition(0,0)
                                return true
                            }
                        }



                    }

                    return false
                }


            }

        }


    }

}