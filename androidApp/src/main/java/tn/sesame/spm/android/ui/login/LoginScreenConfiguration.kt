package tn.sesame.spm.android.ui.login

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

val LoginScreenConfigurationPortrait = ConstraintSet {

    val loginBrandRef = createRefFor("loginAppBrand")
    val loginFormRef = createRefFor("loginForm")
    val loginButtonRef = createRefFor("loginButton")
    val loginFooterRef = createRefFor("loginFooter")
    val loginAnimation = createRefFor("loginAnim")

    constrain(loginBrandRef){
        top.linkTo(parent.top,32.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(loginAnimation){
        top.linkTo(loginBrandRef.bottom,24.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(loginFormRef){
        top.linkTo(loginAnimation.bottom,24.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginButtonRef){
        top.linkTo(loginFormRef.bottom,32.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginFooterRef){
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom,8.dp)
    }
}

val LoginScreenConfigurationLandscape = ConstraintSet {
    val loginBrandRef = createRefFor("loginAppBrand")
    val loginFormRef = createRefFor("loginForm")
    val loginButtonRef = createRefFor("loginButton")
    val loginFooterRef = createRefFor("loginFooter")
    val loginAnimation = createRefFor("loginAnim")
    val horizentalGuideline = createGuidelineFromBottom(0.3f)
    constrain(loginBrandRef){
        top.linkTo(parent.top,16.dp)
        start.linkTo(parent.start)
    }
    constrain(loginAnimation){
        top.linkTo(loginBrandRef.bottom,16.dp)
        start.linkTo(parent.start)
        end.linkTo(loginBrandRef.end)
        bottom.linkTo(horizentalGuideline)
        height = Dimension.fillToConstraints
    }
    constrain(loginFormRef){
        top.linkTo(parent.top,24.dp)
        start.linkTo(loginBrandRef.end,12.dp)
        end.linkTo(parent.end,12.dp)
        bottom.linkTo(horizentalGuideline,8.dp)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginButtonRef){
        top.linkTo(horizentalGuideline,8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(loginFooterRef.top,8.dp)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginFooterRef){
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom,8.dp)
    }
}