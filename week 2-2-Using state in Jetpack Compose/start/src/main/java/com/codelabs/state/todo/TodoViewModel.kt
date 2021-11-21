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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    /**
     * State는 Compose에서 사용하기 위한 것입니다.
     * Compose 외부에서 사용되는 애플리케이션 상태는 State를 사용하여 상태를 유지해서는 안 됩니다.
     */

    //LiveData를 제거하고 mutableStateListOf로 대체


    //private var _todoItems = MutableLiveData(listOf<TodoItem>())
    //val todoItems: LiveData<List<TodoItem>> = _todoItems
//    fun addItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!! + listOf(item)
//    }
//
//    fun removeItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!!.toMutableList().also {
//            it.remove(item)
//        }
//    }

    /* todoItems의 선언은 짧고 LiveData 버전과 동일한 동작을 캡처합니다.
        private set을 지정함으로써 우리는 ViewModel 내부에서만 볼 수 있는
        private setter로 이 state 객체에 대한 쓰기를 제한하고 있습니다.
     */
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

//    fun removeItem(item: TodoItem) {
//        todoItems.remove(item)
//    }


    /*
    이제 편집기의 상태를 추가할 차례입니다.
    할 일 텍스트가 중복되는 것을 방지하기 위해 목록을 직접 편집할 것입니다.
    그렇게 하려면 현재 편집 중인 텍스트를 유지하는 대신 현재 편집기 항목에 대한 목록 인덱스를 유지합니다.
    TodoViewModel.kt를 열고 편집기 상태를 추가하십시오.
    현재 편집 위치를 유지하는 새 private var currentEditPosition을 정의합니다.
    현재 편집 중인 목록 색인을 보유합니다.
    그런 다음 getter를 사용하여 작성하도록 currentEditItem을 노출합니다.
    이것은 일반적인 Kotlin 함수이지만 currentEditPosition은 State처럼 Compose에서 관찰할 수 있습니다.
     */
    // private state
    private var currentEditPosition by mutableStateOf(-1)
    /*
        State 변환이 작동하려면 State 개체에서 상태를 읽어야 합니다.
        currentEditPosition을 일반 Int(private var currentEditPosition = -1)로 정의한 경우
        compose는 변경 사항을 관찰할 수 없습니다.
        위와 같은 특징을 onEditDone()으로 활용함
     */

    // state
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)


    /* 이벤트 정의 */

    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    // event: onEditDone
    fun onEditDone() {
        currentEditPosition = -1
    }

    // event: onEditItemChange
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems[currentEditPosition] = item
    }

    // event: removeItem
    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone() // don't keep the editor open when removing items
    }


}
