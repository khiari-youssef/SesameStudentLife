package com.youapps.users_management.ui.login

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

val LoginScreenConfigurationPortrait = ConstraintSet {

    val signUpAction = createRefFor("signUpAction")
    val loginScreenTop = createRefFor("LoginScreenTop")
    val loginFormRef = createRefFor("loginForm")
    val loginButtonRef = createRefFor("loginButton")
    val loginFooterRef = createRefFor("loginFooter")
    val appTitleLogo = createRefFor("AppTitleLogo")
    val loginToastRef = createRefFor("toast")

    constrain(signUpAction){
        top.linkTo(loginFormRef.bottom,32.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }
    constrain(loginToastRef){
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom,24.dp)
    }
    constrain(loginScreenTop){
        top.linkTo(parent.top,32.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(appTitleLogo){
        top.linkTo(loginScreenTop.bottom,48.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }
    constrain(loginFormRef){
        top.linkTo(appTitleLogo.bottom,48.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }
    constrain(loginButtonRef){
        top.linkTo(signUpAction.bottom,32.dp)
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
    val signUpAction = createRefFor("signUpAction")
    val loginScreenTop = createRefFor("LoginScreenTop")
    val loginFormRef = createRefFor("loginForm")
    val loginButtonRef = createRefFor("loginButton")
    val loginFooterRef = createRefFor("loginFooter")
    val appTitleLogo = createRefFor("AppTitleLogo")
    val loginToastRef = createRefFor("toast")
    val horizentalGuideline = createGuidelineFromBottom(0.3f)

    constrain(signUpAction){
        top.linkTo(loginFormRef.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }
    constrain(loginToastRef){
        start.linkTo(parent.start,16.dp)
        end.linkTo(parent.end,16.dp)
        bottom.linkTo(parent.bottom,24.dp)
    }
    constrain(loginScreenTop){
        top.linkTo(parent.top,16.dp)
        start.linkTo(parent.start)
    }
    constrain(appTitleLogo){
        top.linkTo(loginScreenTop.bottom,16.dp)
        start.linkTo(parent.start)
        end.linkTo(loginScreenTop.end)
        bottom.linkTo(horizentalGuideline)
        height = Dimension.fillToConstraints
    }
    constrain(loginFormRef){
        top.linkTo(parent.top,24.dp)
        start.linkTo(loginScreenTop.end,12.dp)
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