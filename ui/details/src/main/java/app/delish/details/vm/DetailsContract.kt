package app.delish.details.vm

import androidx.compose.runtime.Immutable
import app.delish.base.vm.BaseViewModel
import app.delish.base.vm.BaseViewModel.*
import com.elbehiry.model.RecipesItem

interface DetailsContract {

    abstract class ViewModel: BaseViewModel<UiEvents, UiState>()

    @Immutable
    data class UiState(
        val isLoading: Boolean,
        val hasError: Boolean,
        val recipe: RecipesItem?,
        val isBookmarked: Boolean
    ) : BaseUiState

    sealed class UiEvents: BaseUiEvent {
        data class GetRecipe(val recipeId : Int) : UiEvents()
        data class ToggleBookMark(val recipesItem: RecipesItem, val bookmarked: Boolean) : UiEvents()
    }
}
