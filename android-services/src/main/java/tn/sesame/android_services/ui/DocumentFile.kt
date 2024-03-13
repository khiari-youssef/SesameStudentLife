package tn.sesame.android_services.ui

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class DocumentFile(
    val uri : Uri
) {
    companion object {
        val EMPTY = DocumentFile(Uri.EMPTY)
    }

    fun isEmpty() : Boolean = uri == Uri.EMPTY
}

@Immutable
data class Documents(
    val data : List<DocumentFile>
) {
    companion object {
        val EMPTY = Documents(listOf())
    }
}