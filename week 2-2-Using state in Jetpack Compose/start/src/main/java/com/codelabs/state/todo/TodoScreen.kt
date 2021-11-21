/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@ExperimentalComposeUiApi
@Composable
//fun TodoScreen(
//    items: List<TodoItem>,
//    onAddItem: (TodoItem) -> Unit,
//    onRemoveItem: (TodoItem) -> Unit
//)
fun TodoScreen(
    items: List<TodoItem>,
    currentlyEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit

) {
    Column {
//        TodoItemInputBackground(elevate = true, modifier = Modifier.fillMaxWidth()) {
//            TodoItemInput(onItemComplete = onAddItem)
//        }

        val enableTopSection = currentlyEditing == null
        TodoItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection) {
                TodoItemEntryInput(onAddItem)
            } else {
                Text(
                    "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

//        LazyColumn(
//            modifier = Modifier.weight(1f),
//            contentPadding = PaddingValues(top = 8.dp)
//        ) {
//            items(items = items) {
//                TodoRow(
//                    todo = it,
//                    onItemClicked = { onRemoveItem(it) },
//                    modifier = Modifier.fillParentMaxWidth()
//                )
//            }
//        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items) { todo ->
                if (currentlyEditing?.id == todo.id) {
                    TodoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(todo) }
                    )
                } else {
                    TodoRow(
                        todo,
                        { onStartEdit(it) },
                        Modifier.fillParentMaxWidth()
                    )
                }
            }
        }


        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() },
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)

        // (문제) 아이템을 추가할 때 마다 recomposition에 의해 계속 바뀜
//        val iconAlpha = randomTint() // 틴트 색깔을 랜덤하게 변경한다.
        // (해결) remember 를 이용해서 저장합니다. // 추가될때 기존 추가된 tint 값이 변하지 않음
        //        remember 값을 제어 가능하게 만들기 위해서 매개변수로 만들고 기본값을 넣음
        // (문제2) 아이템을 많이 넣어 스크롤을 하면 다시 tint 색이 바뀌는 문제 발견
//        val iconAlpha = remember(todo.id) { randomTint() }

        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

/*  [LocalContentColor]
    LocalContentColor는 아이콘 및 타이포그래피와 같은 콘텐츠에 대해 선호하는 색상을 제공합니다.
    배경을 그리는 Surface와 같은 컴포저블에 의해 변경됩니다.

    [Recomposition]
    Recomposition이란?
    데이터가 변경될 때 트리를 업데이트하기 위해 동일한 컴포저블을 다시 실행하는 프로세스입니다.


    목록이 변경될 때마다 재구성 프로세스가 화면의 각 행에 대해 randomTint를 다시 호출하는 것으로
    나타났습니다.

    [문제2의 원인]
    remember 컴포지션에 값을 저장하고 기억을 호출한 컴포저블이 제거되면 해당 값을 잊어버립니다.
    이것은 LazyColumn과 같은 자식을 추가하고 제거하는 컴포저블 내부에 중요한 것을 저장하는 것에
    의존해서는 안 된다는 것을 의미합니다.

    예를 들어 짧은 애니메이션의 애니메이션 상태는 LazyColumn의 자식에서 기억하는 것이 안전하지만
    Todo 작업 완료 애니메이션 같은 경우엔 스크롤시 잊어버릴 수 있기 때문에 주의해야합니다.



 */


private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
//    TodoScreen(items, {}, {})
    TodoScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}


/*
경고: 이 텍스트 필드는 상태를 끌어올려야 할 때 호이스트하지 않습니다.
      이 섹션의 뒷부분에서 우리는 이 기능을 제거할 것입니다.
 */
//@Composable
//fun TodoInputTextField(modifier: Modifier) {
//    val (text, setText) = remember { mutableStateOf("") }
//    TodoInputText(text, setText, modifier)
//}

// TodoInputTextField with hoisted state!
@ExperimentalComposeUiApi
@Composable
fun TodoInputTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
//    TodoInputText(text, onTextChange, modifier)

}


/*
MutableState object를 선언하는 3가지 방법

val state = remember { mutableStateOf(default) }
var value by remember { mutableStateOf(default) }
val (value, setValue) = remember { mutableStateOf(default) }
 */

@ExperimentalComposeUiApi
@Composable
fun TodoItemInput(onItemComplete: (TodoItem) -> Unit) {

    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    // onItemComplete is an event will fire when an item is completed by the user
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    )
}

//  TodoItemInput을 stateful과 2개로 stateless로 나눔
@ExperimentalComposeUiApi
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    // onItemComplete is an event will fire when an item is completed by the user
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    )
}


/*
    [State Hoisting 할 때 어디로 가야하는지 도움이 되는 세가지 규칙]
    1. State는 State를 사용하는 모든 Composable의 최소 공통 상위 항목으로
    2. State는 최소한 수정할 수 있는 최고 수준으로
    3. 동일한 이벤트에 대한 응답으로 두 State가 변경되면 함께


 */

// TodoItemInput을 stateful과 2개로 stateless로 나눔
@ExperimentalComposeUiApi
@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputTextField(
                text = text,
                onTextChange = onTextChange,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit // pass the submit callback to TodoInputText
            )
            TodoEditButton(
//                onClick = {
//                    onItemComplete(TodoItem(text)) // send onItemComplete event up
//                    setText("") // clear the internal text
//                },
                onClick = submit, // pass the submit callback to TodoEditButton
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank() // enable if text is not blank
            )
        }

        if (iconsVisible) {
            AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}


@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemInput(onItemComplete = { })


@ExperimentalComposeUiApi
@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = TodoItemInput(
    text = item.task,
    onTextChange = { onEditItemChange(item.copy(task = it)) },
    icon = item.icon,
    onIconChange = { onEditItemChange(item.copy(icon = it)) },
    submit = onEditDone,
    iconsVisible = true
)

/*  copy(task = it) 및 copy(icon = it)이란 무엇입니까?
 이러한 함수는 데이터 클래스의 값에 대해 Kotlin에서 자동 생성됩니다.
 copy를 호출하면 지정된 매개변수가 변경된 데이터 클래스의 복사본이 만들어집니다.
 */


