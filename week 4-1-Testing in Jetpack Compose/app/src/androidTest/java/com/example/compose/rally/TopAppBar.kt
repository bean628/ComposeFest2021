package com.example.compose.rally


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.toUpperCase
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test
import java.util.*

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
    }


    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
        // 작성 테스트는 시멘틱 트리라는 구조를 사용하여 화면에서 요소를 찾고 해당 속성을 읽습니다.
        // 이는 TalkBack과 같은 서비스에서 읽을 수 있도록 되어 있으므로 접근성 서비스에서도 사용하는 구조입니다.
        // 로그캣으로 로그를 확인할 수 있습니다.
        // 경고: Semantics 속성에 대한 Layout Inspector 지원은 아직 사용할 수 없습니다.
//        composeTestRule.onRoot().printToLog("currentLabelExists")

        // 탭 안의 텍스트가 표시되는지 여부를 확인하기 위해
        // 병합되지 않은 시맨틱 트리에 useUnmergedTree = true를 전달하여 onRoot 파인더에 쿼리할 수 있습니다.
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()

//        composeTestRule
//            .onNodeWithText(RallyScreen.Accounts.name.uppercase())
//            .assertExists() // . "ACCOUNTS" 값을 가진 텍스트 속성이 없으며 이것이 테스트가 실패한 이유입니다.

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name) // onNodeWithText 대신 onNodeWithContentDescription로 대체
            .assertExists()


    }


}