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

import com.codelabs.state.util.generateRandomTodoItem
import org.junit.Test
import com.google.common.truth.Truth.assertThat

/*
 경고: MutableState에 대한 쓰기가 다른 스레드에서 수행되면 테스트에서 즉시 표시되지 않습니다.
 변경 사항을 표시하기 위한 저수준 API는 Snapshot.sendApplyNotifications()입니다.
 이를 처리하기 위한 높은 수준의 API는 현재 작업 중이며 이 코드랩은 완료되면 업데이트됩니다.
 */
class TodoViewModelTest {
    @Test
    fun whenRemovingItem_updatesList() {
        // before
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // during
        viewModel.removeItem(item1)

        // after
        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }

}
