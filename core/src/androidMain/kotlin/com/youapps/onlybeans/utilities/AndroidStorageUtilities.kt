package com.youapps.onlybeans.utilities

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream




suspend fun Uri.toOBFile(context : Context,fileName : String,fileExt: String) : File = withContext(Dispatchers.IO) {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this@toOBFile)
    val outputFile: File = File(context.cacheDir, "$fileName.$fileExt")
     inputStream.use {
         it?.copyTo(outputFile.outputStream())
     }//
  inputStream?.close()//
  outputFile// Example for a temporary file
}



