package kr.com.codelapcompose2021

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
                    MyApp2()
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
    // Expanding the item 하는 부분
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

/*  @Preview 어노테이션을 통해 다양한 프리뷰를 볼 수 있습니다. */
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    CodelapCompose2021Theme {
        MyApp2()
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
fun MyApp2(names: List<String> = listOf("World", "Compose")) {
    // 'state hoisting' 함
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }

}


/*  7. State In Compose
    - remember :
    - mutableStateOf :

    초기 Composition으로 처음 뷰를 구성하게 되고 각각의 뷰는 데이터가 변경될 때
    ReComposition이라는 것을 통해서 변경된 뷰만 업데이트를 하게됩니다.

    이때 '데이터가 변경될 때'의 의미는 State<T>가 변경될 때 Remember 컴포저블로 객체 저장
    수정될 때 mutableStateOf<T>로 접근해서 데이터를 변경하게 되면서 갱신됨

    Compose 상태관리 : https://developer.android.com/jetpack/compose/state?hl=ko
    Compose 이해 : https://developer.android.com/jetpack/compose/mental-model?hl=ko
 */


/*  8. State hoisting
    Composable 함수에서 다른 여러 함수에서 읽히거나 수정되는 state는 공통 조상에 있어야합니다.
    이런 프로세스를 'state hoisting' 이라고 부릅니다.
    반대로 컴포저블의 부모에 의해 제어될 필요가 없는 상태는 hoisting되면 안됩니다.


    'state hoisting'을 하게 되면 얻는 이점
    - 상태 중복을 피함
    - 버그 방지
    - 컴포저블 재사용에 도움
    - 테스트를 좀 더 쉽게 만듦
 */

@Composable
private fun Greetings(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}
@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

//     TODO: This state should be hoisted
//    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
//                꼭 필요한 상황이 아니라면 이런식으로 상태를 가지고 있는 것 보다는
//                부모에 의해 컨트롤 되는게(state hoisting) 많은 이점을 가지고 있습니다.
//                onClick = { shouldShowOnboarding = false }
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    CodelapCompose2021Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}


/*  9. Createing a performant lazy list
    (Note) LazyColumn와 LazyRow는 RecyclerView와 동등합니다. */

@Composable
private fun LazyGreetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        // (Note) androidx.compose.foundation.lazy.items로 import 되어야합니다.
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun LazyGreetingsPreview() {
    CodelapCompose2021Theme {
        LazyGreetings()
    }
}