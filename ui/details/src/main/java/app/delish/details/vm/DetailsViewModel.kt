package app.delish.details.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.delish.domain.usecases.ToggleSavedRecipeUseCase2
import app.delish.domain.usecases.recipes.information.GetRecipeInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val RECIPE_ID = "recipeId"
private const val DEFAULT_RECIPE_ID = -1

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRecipeInformationUseCase2: GetRecipeInformationUseCase,
    private val toggleSavedRecipeUseCase2: ToggleSavedRecipeUseCase2,
    savedStateHandle: SavedStateHandle
) : DetailsContract.ViewModel() {

    private val recipeId: Int = savedStateHandle[RECIPE_ID] ?: DEFAULT_RECIPE_ID
    override val _uiState: MutableStateFlow<DetailsContract.UiState> =
        MutableStateFlow(initialUiState())

    private val recipeExceptionHandler = CoroutineExceptionHandler { _, _ ->
        updateUiState { it.copy(isLoading = false, hasError = true) }
    }

    init {
        getRecipes(recipeId)
    }

    override fun onUiEvent(event: DetailsContract.UiEvents) {
        when (event) {
            is DetailsContract.UiEvents.ToggleBookMark -> {
                viewModelScope.launch {
                    toggleSavedRecipeUseCase2.execute(event.recipesItem)
                }
            }
            is DetailsContract.UiEvents.GetRecipe -> TODO()
        }
    }

    private fun getRecipes(recipeId: Int) {
        viewModelScope.launch(recipeExceptionHandler) {
            val recipe = getRecipeInformationUseCase2.execute(recipeId)

            updateUiState {
                it.copy(
                    isLoading = false,
                    recipe = recipe
                )
            }
        }
    }

    private companion object {
        fun initialUiState() = DetailsContract.UiState(
            isLoading = true,
            hasError = false,
            recipe = null
        )

        private const val DEFAULT_RECIPE_ID = -1
    }
}
