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

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


// Removing XML code
// Let's start with the migration! Open fragment_plant_detail.xml and do the following:
//
//  Switch to the Code view
//  Remove the ConstraintLayout code and nested TextViews inside the NestedScrollView (the codelab will compare and reference the XML code when migrating individual items, having the code commented out will be useful)
//  Add a ComposeView that will host Compose code instead with compose_view as view id
//  fragment_plant_detail.xml


@Composable
fun PlantDetailDescription() {
    Surface {
        Text("Hello Compose")
    }
}
