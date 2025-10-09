package tn.sesame.designsystem.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


enum class CustomSheetExpandState{
    EXPANDED,HALF_EXPANDED,HIDDEN
}

data class CustomBottomSheetState(
   private val expandState: MutableState<CustomSheetExpandState>
) {

    companion object{
        @Composable
        fun rememberCustomBottomSheetState(
            expandState: MutableState<CustomSheetExpandState> = remember {
                mutableStateOf(CustomSheetExpandState.HIDDEN)
            }
        ) : CustomBottomSheetState = remember(expandState) {
            CustomBottomSheetState(expandState)
        }
    }
    fun expand() {
        expandState.value = CustomSheetExpandState.EXPANDED
    }

    fun halfExpand() {
        expandState.value = CustomSheetExpandState.EXPANDED
    }

    fun hide() {
        expandState.value = CustomSheetExpandState.EXPANDED
    }

    val currentState : State<CustomSheetExpandState> = expandState
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomModalBottomSheetDialog(
    sheetState : CustomBottomSheetState = CustomBottomSheetState.rememberCustomBottomSheetState(),
    sheetShape : Shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
    sheetBackgroundColor : Color = Color.White,
    sheetElevation : Dp = 0.dp,
    parentContent : @Composable ()->Unit,
    sheetContent :  @Composable ()->Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
    val anchors = mapOf(0f to 0, screenHeight to 1) // Maps anchor points (in px) to states
 Box(modifier = Modifier.fillMaxSize()){
     parentContent()
     Box(
         modifier = Modifier
             .swipeable(
                 state = swipeableState,
                 anchors = anchors,
                 orientation = Orientation.Vertical,
                 thresholds = { from, to ->
                     FractionalThreshold(0.4f)
                 }
             )
             .background(
                 color = sheetBackgroundColor,
                 shape = sheetShape
             )
             .shadow(sheetElevation)
             .align(Alignment.BottomCenter)
     ){
        parentContent()
     }
 }
}



@Preview
@Composable
fun Test() {
    val sheetState = CustomBottomSheetState.rememberCustomBottomSheetState(
        expandState =  remember {
            mutableStateOf(CustomSheetExpandState.HIDDEN)
        }
    )

    CustomModalBottomSheetDialog(
        sheetState = sheetState,
        parentContent = {
          Column(
              modifier = Modifier
                  .padding(
                      top = 16.dp
                  )
                  .fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
          ) {
             Button(
                 onClick = {
                     sheetState.halfExpand()
                 }) {
                 Text(text = "Expand")
             }
          }
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
               LazyColumn(content = {
                   items(20) {
                       Text(text = "text$it")
                   }
               })
            }
        }
    )
}