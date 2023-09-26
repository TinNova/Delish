import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import app.delish.details.DetailScreen
import app.delish.details.vm.DetailsContract
import app.delish.details.vm.DetailsContract.UiState
import app.delish.details.vm.DetailsViewModel
import com.elbehiry.model.RecipesItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.every
import org.junit.Rule
import org.robolectric.annotation.Config
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class DetailsScreenRobolectricTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val mockViewModel: DetailsViewModel = mockk(relaxed = true)
    private val mockNavController: NavController = mockk(relaxed = true)

    private fun givenUiStateReturns(uiState: UiState = defaultUiState) {
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)
    }

    @Test
    fun `GIVEN DinersCountFormScreen, WHEN screen is resumed, THEN onUiEvent ScreenResumed is triggered`() {
        //  given
        val recipesItem = mockk<RecipesItem>()
        givenUiStateReturns(
            UiState(
                isLoading = false,
                hasError = false,
                recipe = recipesItem,
                isBookmarked = false,
            )
        )

        composeTestRule.setContent {
//            MeerkatAppTheme {
                DetailScreen(
                    navController = mockNavController,
                    viewModel = mockViewModel
                )
//            }
        }

        //  when
        composeTestRule.onNodeWithContentDescription("bookMark").performClick()

        //  then
        verify {
            mockViewModel.onUiEvent(DetailsContract.UiEvents.ToggleBookMark(recipesItem, false))
        }
    }

    private val defaultUiState = UiState(
        isLoading = true,
        hasError = false,
        recipe = null,
        isBookmarked = false,
    )
}
