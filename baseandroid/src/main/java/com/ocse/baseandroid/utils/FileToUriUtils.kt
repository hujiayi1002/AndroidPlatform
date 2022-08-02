package com.ocse.baseandroid.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File

class FileToUriUtils {
    companion object {
        fun fileToUri(path: String): Uri {
            return Uri.fromFile(File(path))
        }

        fun uriToFilePath(
            selectedVideoUri: Uri,
            contentResolver: ContentResolver
        ): String? {
            var filePath = ""
            val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)
            val cursor: Cursor? =
                contentResolver.query(selectedVideoUri, filePathColumn, null, null, null)
            //      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
            cursor?.moveToFirst()
            if (cursor != null) {
                val columnIndex: Int? = cursor?.getColumnIndex(filePathColumn[0])
                filePath = columnIndex?.let { cursor?.getString(it).toString() }.toString()
            }

            cursor?.close()
            return filePath
        }

    }
}