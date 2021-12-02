/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelab.animation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android.codelab.animation.ui.AnimationCodelabTheme
import com.example.android.codelab.animation.ui.home.Home
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationCodelabTheme {
                Home()
            }
        }

        Log.i("test","0300신한카드".substring(0,4))
        Log.i("test","0300신한카드".substring(4))
        val now = System.currentTimeMillis()
        val date = Date(now)
        val curDateFormat = SimpleDateFormat("yyyy")
        val currentYear = curDateFormat.format(date)

        Log.i("test",currentYear.substring(0, 2))
    }
}
