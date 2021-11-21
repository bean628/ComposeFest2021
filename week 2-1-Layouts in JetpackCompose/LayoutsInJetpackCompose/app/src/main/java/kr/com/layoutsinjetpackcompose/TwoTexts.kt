package kr.com.layoutsinjetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.com.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme

/*
[ Intrinsics ]
Compose의 규칙 중 하나는 자녀를 한 번만 측정해야 한다는 것입니다.
자식을 두 번 측정하면 런타임 예외가 발생합니다.
그러나 자녀를 측정하기 전에 자녀에 대한 정보가 필요할 때가 있습니다.
Intrinsics를 사용하면 실제로 측정되기 전에 자식을 쿼리할 수 있습니다.
컴포저블에 내장 너비 또는 내장 높이를 요청할 수 있습니다.

(min|max)IntrinsicWidth: 이 높이가 주어지면 콘텐츠를 적절하게 칠할 수 있는 최소/최대 너비가 얼마입니까?
(min|max)IntrinsicHeight: 이 너비가 주어지면 콘텐츠를 적절하게 칠할 수 있는 최소/최대 높이입니다.

 예를 들어 무한 너비의 텍스트에 minIntrinsicHeight를 요청하면 텍스트가 한 줄에 그려진 것처럼
 텍스트의 높이가 반환됩니다.
 */

@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),

            text = text2
        )
    }
}

@Preview
@Composable
fun TwoTextsPreview() {
    LayoutsInJetpackComposeTheme() {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}