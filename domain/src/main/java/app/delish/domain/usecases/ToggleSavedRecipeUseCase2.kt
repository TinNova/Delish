package app.delish.domain.usecases

import app.delish.data.recipes.repository.RecipesRepository
import com.elbehiry.model.RecipesItem
import javax.inject.Inject

class ToggleSavedRecipeUseCase2 @Inject constructor(
    private val recipesRepository: RecipesRepository,
) {

    suspend fun execute(parameters: RecipesItem) {
        if (parameters.saved) {
            recipesRepository.saveRecipe(parameters)
        } else {
            recipesRepository.deleteRecipe(parameters.id)
        }
    }
}
