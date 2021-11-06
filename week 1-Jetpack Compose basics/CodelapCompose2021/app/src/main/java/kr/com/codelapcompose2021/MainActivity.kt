package kr.com.codelapcompose2021

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

//    val extraPadding = if (expanded.value) 48.dp else 0.dp
    // Animating list(리스트에 애니매이션 적용)
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more))
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
    // OnboardingScreen 컴포저블에서 var shouldShowOnboarding을 'state hoisting' 함
    // var shouldShowOnboarding by remember { mutableStateOf(true) }

    // 회전등 구성변경으로부터 상태 값 저장을 위해서 remember 대신 rememberSaveable 함수로 변경
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
//        Greetings()
        LazyGreetings()
    }

}


/*  7. State In Compose
    초기 Composition으로 처음 뷰를 구성하게 되고 각각의 뷰는 데이터가 변경될 때
    ReComposition이라는 것을 통해서 변경된 뷰만 업데이트를 하게됩니다.

    이때 '데이터가 변경될 때'의 의미는 State<T>가 변경될 때 Remember 컴포저블로 객체 저장
    수정될 때 mutableStateOf<T>로 접근해서 데이터를 변경하게 되면서 갱신됨

    Composition: Jetpack Compose가 composable function을 실행시켜서 만들어진 UI를 말합니다.
    Initial composition: composable function이 수행되어 처음으로 만들어진 Composition을 말합니다.
    Recomposition: 데이터가 변경되어 composable을 재실행하는 걸 말합니다.

    Compose 상태관리 : https://developer.android.com/jetpack/compose/state?hl=ko
    Compose 이해 : https://developer.android.com/jetpack/compose/mental-model?hl=ko
 */


/*  8. State hoisting
    Composable 함수에서 다른 여러 함수에서 읽히거나 수정되는 state는 공통 조상에 있어야합니다.
    이런 프로세스를 'state hoisting' 이라고 부릅니다.
    반대로 컴포저블의 부모에 의해 제어될 필요가 없는 상태는 hoisting되면 안됩니다.

    'state hoisting'을 다른 말로 표현하면 stateful한 컴포저블 함수를 stateless하게 만드는 것
    이라고도 할 수 있습니다.

    'state hoisting'을 하게 되면 얻는 이점
    - 상태 중복을 피함
    - 버그 방지(Single source of truth)
    - 컴포저블 재사용에 도움
    - 테스트를 좀 더 쉽게 만듦
    - 캡슐화(Encapsulated)
    - 비결합화(Decoupled)
    - 가로채기가능(Interceptable)

    Single source of truth : state를 여러 곳에서 복제해서 사용하지 않고 state를 나타내는 source가
                             하나 이므로 버그를 방지할 수 있습니다.
    Encapsulated : stateful composable만 상태를 변경할 수 있습니다. 상태가 caller로 끌어올려졌더라도
                   해당 상태는 위치한 composable의 내부 속성이 되므로 캡슐화됩니다.
    Decoupled : state가 hoisting 되면 composable과 state는 의존관계가 없어집니다.
                즉 state는 어디에도 저장될 수 있으며, Viewmodel 같은 곳으로 옮겨져서 처리할 수도
                있습니다.
    Interceptable : 상태가 caller로 옮겨졌으니 해당 상태를 if문 등을 통해서 제어할 수 있습니다.
                    즉, 상태가 바뀌는걸 caller가 제어할 수 있습니다.


    'state hoisting'을 할 때 주의 사항
    - state와 event의 이동은 그림처럼 항상 단방향으로만 흘러야합니다.(https://developer.android.com/images/jetpack/compose/udf-hello-screen.png)
      state는 항상 위에서 아래로, event는 아래서 위로 전달되도록 구성해야만 UI에 상태를 표현하는
      부분과, 상태를 저장/변경하는 부분을 서로 분리할 수 있습니다.


    'state hoisting'을 할 때 state를 어디까지 끌어올려야 할지에 대한 위치를 파악하는 3가지 규칙
        1. 여러 Composable이 하나의 state를 공용으로 읽어 간다면 그 Composable들 중 가장 낮은 공통 상위
           요소에 위치시킵니다. 즉 예제에서 사용한 name을 A, B, C composable에서 사용한다면, A, B, C의
           공통 부모 composable 중에 depth가 가장 낮은 (A, B, C와 가장 근접한) 공통 부모 composable에 state를
           위치시켜야 합니다. 최소 공배수의 개념이라고 보면 될 것 같네요.

           State should be hoisted to at least the lowest common parent of all composables that use the
           state (read).

        2. State는 변경될 수 있는 가장 높은 수준으로 끌어올려야 합니다.
           State에 쓰는 작업은 가장 상위 레벨에 위치해야 합니다. 쉽게 말해 MVVM 패턴이라면 ViewModel에
           state가 위치해야 합니다. 최대 공약수의 개념이라고 하면 될 것 같습니다. (더 헷갈리려나요?)

           State should be hoisted to at least the highest level it may be changed (write).


        3. 어떤 이벤트에 대한 응답으로 변경되는 state가 한 개가 아니라면, 같이 변경되는 state들은 같이
           hoisting 되어야 합니다.

           If two states change in response to the same events they should be hoisted together.


    hoisting된 state 위치가 적절하지 않으면 event와 state의 단반향 흐름이 깨질 수 있습니다.
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
private fun LazyGreetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        // (Note) androidx.compose.foundation.lazy.items로 import 되어야합니다.
        items(items = names) { name ->
//            Greeting(name = name)
            CardGreeting(name)
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


/*  10. Persisting state
        remeber 함수는 컴포저블이 컴포지션에 유지되는 동안에만 작동합니다.
        회전하면 전체 활동이 다시 시작되므로 모든 상태가 손실됩니다.
        이는 구성 변경(회전, 다크모드) 및 프로세스 종료시에도 발생합니다.
 */

/*  myApp2()를 실행시킨 후 continue 버튼을 눌러서 UI가 바뀐 상태에서 화면 회전을 하면
    상태가 손실되어 처음 continue 버튼이 보이는 화면으로 되돌아가는 문제점이 있다.
    -> remember 대신 rememberSaveable을 사용하면 구성 변경(예: 회전, 다크모드) 및 프로세스 종료에서
       상태가 저장되어 살아남습니다.

    Composable function은 recompose 되기 때문에 일반적인 변수를 함수 내에 선언해서는 안됩니다.
    함수가 재시작될 때마다 초기화되기 때문에 제 역할을 못하는 거죠

    따라서 remember라는 composable을 이용하여 변수를 선언해야 하며,
    이 변수는 initial composition에서 메모리에 저장되어,
    recompose때에 값을 반환받아 사용할 수 있습니다.
    즉 recompose로 인한 함수의 재호출과 상관없이 변숫값이 유지될 수 있습니다.
    단, remeber 변수는 recompose시에는 저장되어 값을 유지할 수 있으나, configuration change가
    발생하면 (화면이 회전하거나 해서 다시 그려지는 경우) 값이 유지되지 않습니다.
    따라서 이를 유지하기 위해서는 remeberSaveable을 사용해야 합니다.

 */

@Composable
private fun CardGreeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}