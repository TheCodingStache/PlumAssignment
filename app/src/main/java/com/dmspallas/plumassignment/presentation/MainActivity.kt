package com.dmspallas.plumassignment.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.presentation.character.CharacterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterFragment.newInstance())
                .commit()
        }
    }
}