package tn.sesame.spm.test.configuration

import android.content.Context
import androidx.startup.Initializer

class ComponentsInitializer : Initializer<TestApplication> {
    override fun create(context: Context): TestApplication {
        TODO("Not yet implemented")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}