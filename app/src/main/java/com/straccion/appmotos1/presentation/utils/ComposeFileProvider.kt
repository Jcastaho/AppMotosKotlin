package com.straccion.appmotos1.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.straccion.appmotos1.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import org.apache.commons.io.FileUtils


class ComposeFileProvider: FileProvider(R.xml.file_paths) {
    companion object{
        fun createFileFromuri(context: Context, uri: Uri): File?{
            try {
                val stram = context.contentResolver.openInputStream(uri)
                val file = File.createTempFile(
                    "${System.currentTimeMillis()}",
                    ".png",
                    context.cacheDir
                )
                FileUtils.copyInputStreamToFile(stram, file)
                return file
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun getImageUri(context:Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file
            )
        }
        fun getPathFromBitmap(context: Context, bitmap: Bitmap): String {
            val wrapper = ContextWrapper(context)
            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file,"${UUID.randomUUID()}.jpg")
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
            return file.path
        }
    }
}