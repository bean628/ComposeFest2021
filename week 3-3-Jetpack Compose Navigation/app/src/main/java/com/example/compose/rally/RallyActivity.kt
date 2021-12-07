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

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import com.example.compose.rally.ui.bills.BillsBody
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewBody
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
//        var currentScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }
        val allScreens = RallyScreen.values().toList()
        val navController = rememberNavController() // 네이게이션컨트롤러 추가
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )
        Scaffold(
            topBar = {
//                RallyTabRow(
//                    allScreens = allScreens,
//                    onTabSelected = { screen -> currentScreen = screen },
//                    currentScreen = currentScreen
//                )
                // 참고: 코드를 테스트 가능하게 만들려면 navController를
                //       전달하지 않는 것이 좋습니다.
                //       이 코드랩에서는 단일 책임 지점에서 탐색할 수 있도록 콜백을 제공합니다.
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
                    },
                    currentScreen = currentScreen,
                )

            }
        ) { innerPadding ->
//            Box(Modifier.padding(innerPadding)) {
//                currentScreen.content(
//                    onScreenChange = { screen ->
//                        currentScreen = RallyScreen.valueOf(screen)
//                    }
//                )
//            }
            NavHost( // Scaffold {} 안에서 NavHost로 대체
                navController = navController,
                startDestination = RallyScreen.Overview.name,
                modifier = Modifier.padding(innerPadding)
            ) {
//                composable(RallyScreen.Overview.name) {
//                    Text(RallyScreen.Overview.name)
//                }
//                composable(RallyScreen.Accounts.name) {
//                    Text(RallyScreen.Accounts.name)
//                }
//                composable(RallyScreen.Bills.name) {
//                    Text(RallyScreen.Bills.name)
//                }
                composable(RallyScreen.Overview.name) {
                    OverviewBody(
                        onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) }, // 클릭 동작 추가
                        onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) } // 클릭 동작 추가
                    )
                }
                composable(RallyScreen.Accounts.name) {
                    AccountsBody(accounts = UserData.accounts)
                }
                composable(RallyScreen.Bills.name) {
                    BillsBody(bills = UserData.bills)
                }


            }
        }
    }
}
