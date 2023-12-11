package tn.sesame.spm.android.ui.login

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

val LoginScreenConfigurationPortrait = ConstraintSet {

    val loginBrandRef = createRefFor("loginAppBrand")
    val loginFormRef = createRefFor("loginForm")
    val loginButtonRef = createRefFor("loginButton")
    val loginFooterRef = createRefFor("loginFooter")

    constrain(loginBrandRef){
        top.linkTo(parent.top,32.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
    constrain(loginFormRef){
        top.linkTo(loginBrandRef.bottom,24.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginButtonRef){
        top.linkTo(loginFormRef.bottom,24.dp)
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
}