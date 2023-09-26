package app.delish.details.vm

import CoroutineTestExtension
import CoroutineTestRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.delish.domain.usecases.ToggleSavedRecipeUseCase2
import app.delish.domain.usecases.recipes.information.GetRecipeInformationUseCase
import com.elbehiry.model.RecipesItem
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.rules.TestRule
import kotlin.test.assertEquals
@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    // This extension isn't working because it's Junit5, whilst the rest of the App is using Junit4
    // Ideally we will migrate fully to Junit5, but it's out of scope of this test
    @RegisterExtension
    @JvmField
    val coroutineTestExtension = CoroutineTestExtension()

    // Using a Junit4 Rule instead of a Junit5 Extension
    @get:Rule
    val rule: TestRule = CoroutineTestRule()

    private val getRecipeInformationUseCase: GetRecipeInformationUseCase = mockk(relaxed = true)
    private val toggleSavedRecipeUseCase2: ToggleSavedRecipeUseCase2 = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    private lateinit var sut: DetailsViewModel

    private fun createSut() {
        sut = DetailsViewModel(
            getRecipeInformationUseCase2 = getRecipeInformationUseCase,
            toggleSavedRecipeUseCase2 = toggleSavedRecipeUseCase2,
            savedStateHandle = savedStateHandle,
        )
    }

    @Test
    fun `GIVEN showVoucher is true, WHEN sut is created, THEN update ui state with toolbar and tabs title`() =
        runTest {
            //GIVEN
            val recipeId = 1
            val recipesItem = mockk<RecipesItem>(relaxed = true)
            every { savedStateHandle.get<Int>(DetailsViewModel.RECIPE_ID) } returns recipeId
            coEvery { getRecipeInformationUseCase.execute(recipeId) } returns recipesItem

            // WHEN
            createSut()

            // THEN
            sut.uiState.test {
                assertEquals(
                    expected = DetailsContract.UiState(
                        isLoading = false,
                        hasError = false,
                        recipe = recipesItem,
                        isBookmarked = false

                    ),
                    actual = awaitItem()
                )
            }
        }
}
