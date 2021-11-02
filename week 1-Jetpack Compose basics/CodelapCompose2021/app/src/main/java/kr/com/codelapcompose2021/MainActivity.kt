package kr.com.codelapcompose2021

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kr.com.codelapcompose2021.ui.theme.CodelapCompose2021Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent 안에 xml 대신 레이아웃을 구성할 Composable 함수가 들어갈 수 있습니다.
        setContent {
            CodelapCompose2021Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}


/*  @Composable 어노테이션을 사용하면 Composable function으로 만들 수 있습니다.
    Composable function에는 TextView 대신 사용할 수 있는 Text 같은 Composable function들을
    넣을 수 있습니다. */
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

/*  @Preview 어노테이션을 통해 다양한 프리뷰를 볼 수 있습니다. */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodelapCompose2021Theme {
        Greeting("Android")
    }
}