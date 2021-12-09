/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel


// Removing XML code
// Let's start with the migration! Open fragment_plant_detail.xml and do the following:
//
//  Switch to the Code view
//  Remove the ConstraintLayout code and nested TextViews inside the NestedScrollView (the codelab will compare and reference the XML code when migrating individual items, having the code commented out will be useful)
//  Add a ComposeView that will host Compose code instead with compose_view as view id
//  fragment_plant_detail.xml


//@Composable
//fun PlantDetailDescription() {
//    Surface {
//        Text("Hello Compose")
//    }
//}

// PlantDetailViewModel의 인스턴스가 Fragment에서 사용되므로
// PlantDetailDescription에 매개변수로 전달할 수 있습니다.
@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // Observes values coming from the VM's LiveData<Plant> field
    val plant by plantDetailViewModel.plant.observeAsState()

    // If plant is not null, display the content
    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5, // 텍스트 스타일은 XML 코드에서 textAppearanceHeadline5에 매핑되는 MaterialTheme.typography.h5입니다.
        modifier = Modifier
            .fillMaxWidth() // fillMaxWidth 수정자는 XML 코드의 android:layout_width="match_parent"에 해당합니다.
            .padding(horizontal = dimensionResource(R.dimen.margin_small)) // dimensionResource 도우미 함수를 사용하는 View 시스템의 값인 margin_small의 수평 패딩.
            .wrapContentWidth(Alignment.CenterHorizontally) //wrapContentWidth를 사용하여 텍스트를 수평으로 정렬합니다.
    )
    // Compose는 dimens.xml 및 strings.xml 파일,
    // 즉 dimensionResource(id) 및 stringResource(id)에서 값을 가져오는 편리한 방법을 제공합니다.
}

//@Composable
//fun PlantDetailContent(plant: Plant) {
//    PlantName(plant.name)
//}
@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(R.dimen.margin_normal))) {
            PlantName(plant.name)
            PlantWatering(plant.wateringInterval)
        }
    }
}

@Preview
@Composable
private fun PlantNamePreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")
    MaterialTheme {
        PlantDetailContent(plant)
    }
}


@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // Same modifier used by both Texts
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        // 경고: Compose 1.0.4 현재 버전에서는 수량화된 문자열(quantified string from dimensions)을 가져오는 것이 지원되지 않습니다.
        // LocalContext.current.resources를 통해 액세스해야 합니다
        // 간단하게 하기 위해 함수를 인라인으로 호출했지만 앱에서 이 작업을 수행하는 경우
        // 재사용할 수 있도록 다른 함수로 추출합니다.
        val wateringIntervalText = LocalContext.current.resources.getQuantityString(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    MaterialTheme {
        PlantWatering(7)
    }
}