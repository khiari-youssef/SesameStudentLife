import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import tn.sesame.designsystem.R

@Composable
fun LoginAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.login_anim),
    )
    val progress by animateLottieCompositionAsState(composition)
LottieAnimation(
    modifier =modifier,
    composition = composition,
    progress = {progress}
)

}