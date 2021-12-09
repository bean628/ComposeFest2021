package com.example.compose.rally


import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
//        Thread.sleep(5000)
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()

        // 테스팅 치트 시트
        // https://developer.android.com/jetpack/compose/testing-cheatsheet
        // test package reference documentation
        // https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary

        // 적절한 assertion 종류를 찾아 테스트할 수 있다.
        // onNodeWithText, onNodeWithContentDescription, isSelected,
        // hasContentDescription, assertIsSelected...
        //

    }
}