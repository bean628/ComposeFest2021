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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import com.codelabs.state.ui.StateCodelabTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    TodoActivityScreen(todoViewModel = todoViewModel)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    /* todo아이템 observing, LiveData를 mutableState로 대체해서 제거하기 전  */
//    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
//    TodoScreen(
//        items = items,
//        onAddItem = { todoViewModel.addItem(it) }, // onAddItem = todoViewModel::addItem, 메서드 참조 표현식도 가능
//        onRemoveItem = { todoViewModel.removeItem(it) }
//    )


    /* todo아이템 observing, LiveData를 mutableState로 대체해서 제거함 */
//    TodoScreen(
//        items = todoViewModel.todoItems,
//        onAddItem = todoViewModel::addItem,
//        onRemoveItem = todoViewModel::removeItem
//    )

    /* pass state */
    TodoScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )

}