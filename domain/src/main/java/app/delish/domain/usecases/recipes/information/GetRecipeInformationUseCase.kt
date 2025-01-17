/*
 * Copyright 2021 The Android Open Source Project
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

package app.delish.domain.usecases.recipes.information

import app.delish.data.recipes.repository.RecipesRepository
import app.delish.domain.SuspendUseCase
import app.delish.inject.IoDispatcher
import com.elbehiry.model.RecipesItem
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetRecipeInformationUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(parameters: Int): RecipesItem =
        recipesRepository.getRecipeInformation(parameters)
}
