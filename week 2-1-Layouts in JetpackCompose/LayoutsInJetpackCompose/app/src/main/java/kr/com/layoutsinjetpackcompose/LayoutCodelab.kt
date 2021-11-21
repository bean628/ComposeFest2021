package kr.com.layoutsinjetpackcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.com.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme


/*
   Scaffold를 사용하면 기본 Material Design 레이아웃 구조로 UI를 구현할 수 있습니다.
   TopAppBar, BottomAppBar, FloatingActionButton 및 Drawer와 같은 가장 일반적인 최상위 Material
   구성 요소에 대한 슬롯을 제공합니다.
   Scaffold를 사용하면 이러한 구성 요소가 올바르게 배치되고 함께 작동하는지 확인합니다.
 */

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutsInJetpackComposeTheme {
        LayoutsCodelab()
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(8.dp)) {
//        Text(text = "Hi there!")
//        Text(text = "Thanks for going through the Layouts codelab")
//    }
    MyOwnColumn(modifier.padding(8.dp)) { // Layout을 이용한 커스텀 컴포저블
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}