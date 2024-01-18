package tn.sesame.designsystem.components.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

/**
 * A custom modifier than will create a shimmer effect on the component when it is enabled
 * @param isEnabled activates shimmer effect if true.
 * @param animDuration duration of the shimmer animation
 * @author Khiari Youssef
  * it will not work if you override the methods below
 * @see Modifier.background
 * @see Modifier.onGloballyPositioned
 * @see Modifier.drawWithContent
 */
fun Modifier.shimmerEffect(
    isEnabled : Boolean= true,
    shape : Shape = RectangleShape,
    animDuration : Int = 800
) = composed{
    return@composed if (isEnabled){
        val layoutSizeState = remember {
            mutableStateOf(IntSize.Zero)
        }
        val transition = rememberInfiniteTransition()

        val transitionXOffsetState = transition.animateValue(
            initialValue = -2*layoutSizeState.value.width,
            targetValue = 2*layoutSizeState.value.width,
            animationSpec = infiniteRepeatable(
                animation = tween(animDuration),
                repeatMode = RepeatMode.Restart
            ),
            typeConverter = Int.Companion.VectorConverter, label = ""
        )

        val shimmerBackground = if (isSystemInDarkTheme()) Color(0xFFA5A5A5) else Color(0xFFC2C2C2)
        val shimmerGradientColor = if (isSystemInDarkTheme()) Color(0xFFC2C2C2)  else Color(0xFFA5A5A5)
        val shimmerBrush = Brush.linearGradient(
            colors = listOf(
                shimmerBackground,
                shimmerGradientColor,
                shimmerGradientColor,
                shimmerBackground
            ),
            start = Offset(transitionXOffsetState.value.toFloat(),0f),
            end = Offset(transitionXOffsetState.value+layoutSizeState.value.width.toFloat(),layoutSizeState.value.height.toFloat())
        )
        background(shimmerBrush)
            .onGloballyPositioned {
                layoutSizeState.value = it.size
            }
            .drawWithContent {
                if (isEnabled.not()) {
                    drawContent()
                }
            }
    } else this
}

