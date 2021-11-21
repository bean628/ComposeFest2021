package kr.com.layoutsinjetpackcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// https://developer.android.com/codelabs/jetpack-compose-layouts#0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}