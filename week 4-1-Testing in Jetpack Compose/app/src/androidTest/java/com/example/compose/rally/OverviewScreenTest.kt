package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    // onNodeWithText와 같은 파인더를 사용하는 경우 테스트는 시맨틱 트리를 쿼리하기 전에
    // 앱이 유휴 상태가 될 때까지 기다립니다. 동기화가 없으면 테스트에서 표시되기 전에
    // 요소를 찾거나 불필요하게 기다릴 수 있습니다.


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun overviewScreen_alertsDisplayed() {
        composeTestRule.setContent {
            OverviewBody()
        }

        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()

        // 위 상태로 테스트를 돌려보면
        //  androidx.compose.ui.test.junit4.android.ComposeNotIdleException: Idling resource timed out: possibly due to compose being busy.
        //  IdlingResourceRegistry has the following idling resources registered:
        //  - [busy] androidx.compose.ui.test.junit4.android.ComposeIdlingResource@7bd2505
        // 에러 메시지를 확인할 수 있는데 기본적으로 Compose가 영구적으로 사용 중이므로 앱을 테스트와 동기화할 방법이 없다는 것을 의미합니다.
        // 여기서는 무한 깜박이는 애니메이션이 문제라는 것을 이미 짐작했을 수 있습니다.


        // (참고) Infinite animations은 Compose 테스트가 이해하는 특별한 경우이므로 테스트를 바쁘게 유지하지 않습니다.

    }



}