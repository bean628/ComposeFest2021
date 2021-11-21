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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) {
                TodoRow(
                    todo = it,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
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

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
