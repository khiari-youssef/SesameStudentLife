package tn.sesame.spm.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val datastoreModule : Module = module {
    factory(dataStorePathTag) {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
}