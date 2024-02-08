package tn.sesame.spm

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import tn.sesame.spm.ui.base.TestApplication

class ApplicationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}