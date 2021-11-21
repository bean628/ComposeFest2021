package kr.com.layoutsinjetpackcompose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kr.com.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme


/*
 참조는 createRefs()(또는 createRef())를 사용하여 생성되며 ConstraintLayout의 각 구성 가능에는
 연결된 참조가 있어야 합니다. 제약 조건은 참조를 매개 변수로 사용하고 본문 람다에서 제약 조건을
 지정할 수 있도록 하는 제약 조건 수정자를 사용하여 제공됩니다.
 제약 조건은 linkTo 또는 기타 유용한 방법을 사용하여 지정됩니다.
 부모는 ConstraintLayout 구성 가능 자체에 대한 제약 조건을 지정하는 데 사용할 수 있는 기존 참조입니다.
 */

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {

        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}

@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

/*
[ Dimension behaviors ]
preferredWrapContent - 레이아웃은 해당 차원의 제약 조건이 적용되는 랩 콘텐츠입니다.
wrapContent - 제약 조건에서 허용하지 않는 경우에도 레이아웃은 랩 콘텐츠입니다.
fillToConstraints - 해당 차원의 제약 조건에 의해 정의된 공간을 채우도록 레이아웃이 확장됩니다.
preferredValue - 레이아웃은 해당 차원의 제약 조건에 따라 고정된 dp 값입니다.
value - 레이아웃은 해당 차원의 제약 조건에 관계없이 고정 dp 값입니다.
 */
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(guideline, parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}


@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsInJetpackComposeTheme() {
        ConstraintLayoutContent()
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview2() {
    LayoutsInJetpackComposeTheme() {
        ConstraintLayoutContent2()
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreView() {
    LayoutsInJetpackComposeTheme() {
        LargeConstraintLayout()
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreView() {
    LayoutsInJetpackComposeTheme() {
        DecoupledConstraintLayout()
    }
}