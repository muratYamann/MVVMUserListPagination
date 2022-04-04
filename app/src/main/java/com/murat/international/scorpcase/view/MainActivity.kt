package com.murat.international.scorpcase.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.murat.international.scorpcase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}