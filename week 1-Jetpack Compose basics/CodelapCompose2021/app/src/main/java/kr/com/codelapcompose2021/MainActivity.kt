package kr.com.codelapcompose2021

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.com.codelapcompose2021.ui.theme.CodelapCompose2021Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent 안에 xml 대신 레이아웃을 구성할 Composable 함수가 들어갈 수 있습니다.
        setContent {
            CodelapCompose2021Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}


/*  @Composable 어노테이션을 사용하면 Composable function으로 만들 수 있습니다.
    Composable function에는 TextView 대신 사용할 수 있는 Text 같은 Composable function들을
    넣을 수 있습니다. */
/*  대부분의 Compose UI 요소는 modifier 매개변수를 허용합니다.
    modifier는 UI 요소에 상위 레이아웃 내에서 레이아웃, 표시 또는 동작하는 방법을 알려줍니다. */
@Composable
private fun Greeting(name: String) {

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { /* TODO */ }
            ) {
                Text("Show more")
            }
        }
    }
}

/*  @Preview 어노테이션을 통해 다양한 프리뷰를 볼 수 있습니다. */
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    CodelapCompose2021Theme {
        Greeting("Android")
    }
}

/*  @Composable 어노테이션을 추가하여 함수를 분리하거나 독립적으로 편집이 가능합니다.

    UI에 더 많은 구성 요소를 추가할수록 더 많은 중첩 수준이 생성됩니다.
    함수가 정말 커지면 가독성에 영향을 줄 수 있습니다.
    재사용 가능한 작은 구성 요소를 만들어 앱에서 사용되는 UI 요소 라이브러리를 쉽게
    구축할 수 있습니다. 각각은 화면의 작은 부분을 담당하며 독립적으로 편집할 수 있습니다. */

/*  xml로 복잡한 레이아웃을 그릴 경우에 리니어레아웃 등의 뷰 depth가 깊어지면 레이아웃을 전반적으로
    다시 그리기 때문에 성능 이슈가 발생할 수 있었다. 이를 해결하기 위해 ConstraintLayout을 활용했다.
    그러나 Jetpack Compose의 경우엔 갱신될 때 변경된 부분만 갱신되기 때문에
    다시 이 방식으로 사용되고있다. */

/*  Column : 리니어레이아웃의 vertical과 비슷
    Row : 리니어레이아웃의 horizontal과 비슷
    Box : 프레임레이아웃과 비슷 */

@Composable
fun MyApp(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

