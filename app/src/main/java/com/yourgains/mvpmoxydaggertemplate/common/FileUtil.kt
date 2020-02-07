package com.yourgains.mvpmoxydaggertemplate.common

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object FileUtil {

    const val FILE_PROVIDER = "com.yourgains.mvpmoxydaggertemplate.fileprovider"
    const val FILE_TYPE = ".jpg"
    const val CHILD_DIR = "images/"

    private const val BUFFER_SIZE = 1024
    private const val EMPTY_CURSOR_COLUMNS_COUNT = 0

    private const val PATTERN_FILE_NAME = "yyyyMMdd_HHmmss"
    private const val MEDIA_STORE_IMAGE = "image"
    private const val MEDIA_STORE_VIDEO = "video"
    private const val MEDIA_STORE_AUDIO = "audio"

    private const val DIVIDER = ":"
    private const val DIVIDER_SLASH = "/"
    private const val DIR_TEMP = "temp"
    private const val PRIMARY = "primary"
    private const val CONTENT = "content"
    private const val FILE = "file"
    private const val URI_STRING = "content://downloads/public_downloads"
    private const val SELECTION_ID = "_id=?"
    private const val COLUMN_DATA = "_data"
    private const val COLUMN_DISPLAY_NAME = "_display_name"
    private const val RAW = "raw:"

    private const val GOOGLE_PROVIDERS = "com.android.providers"
    private const val EXTERNAL_STORAGE_DOCUMENT = "com.android.externalstorage.documents"
    private const val DOWNLOADS_DOCUMENT = "$GOOGLE_PROVIDERS.downloads.documents"
    private const val MEDIA_DOCUMENT = "$GOOGLE_PROVIDERS.media.documents"
    private const val GOOGLE_PHOTOS_URI = "$GOOGLE_PROVIDERS.apps.photos.content"
    private const val MEIZU_URI = "com.google.android.apps.docs.storage"

    @SuppressLint("SimpleDateFormat")
    fun getTemplateFileName(): String =
        "JPEG_${SimpleDateFormat(PATTERN_FILE_NAME).format(Date())}"


    fun getPath(context: Context?, uri: Uri?): String? {
        if (uri == null) return null
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split =
                    docId.split(DIVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if (PRIMARY.equals(type, true)) {
                    return Environment.getExternalStorageDirectory().absolutePath + "/" + split[1]
                }
            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                return getPathOfCreatedFileFromInputStream(context, uri)
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split =
                    docId.split(DIVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                val contentUri: Uri? = when (split[0]) {
                    MEDIA_STORE_IMAGE -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    MEDIA_STORE_VIDEO -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    MEDIA_STORE_AUDIO -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> null
                }
                val selection = SELECTION_ID
                val selectionArgs = arrayOf(split[1])
                if (contentUri != null)
                    return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }

        // MediaStore (and general)
        else if (CONTENT.equals(uri.scheme, true)) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.lastPathSegment

            return getDataColumn(context, uri, null, null)
        }
        // File
        else if (FILE.equals(uri.scheme, true)) {
            return uri.path
        }

        return null
    }

    private fun getDataColumn(
        context: Context?,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?,
        column: String = COLUMN_DATA
    ): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(column)
        try {
            cursor =
                context?.contentResolver?.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun getPathOfCreatedFileFromInputStream(context: Context?, uri: Uri): String? {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        val dirPath: String? = context?.filesDir?.path + DIVIDER_SLASH + DIR_TEMP + DIVIDER_SLASH
        if (dirPath.isNullOrEmpty().not()) {
            try {
                inputStream = context?.contentResolver?.openInputStream(uri)
                val dir = File(dirPath)
                dir.mkdirs()
                val filename = getDataColumn(context, uri, null, null, COLUMN_DISPLAY_NAME)?.let {
                    it
                } ?: let {
                    val pathSegments = uri.encodedPath?.split(DIVIDER_SLASH)?.toTypedArray()
                    pathSegments?.last()
                }
                val file = File(dir, filename)
                file.createNewFile()
                outputStream = FileOutputStream(file)
                inputStream?.let {
                    copyStream(it, outputStream)
                    return file.absolutePath
                }
            } catch (e: IOException) {
                Timber.e(e)
            } finally {
                try {
                    inputStream?.close()
                    outputStream?.close()
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }
        }
        return null
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream) {
        try {
            val bytes = ByteArray(BUFFER_SIZE)
            while (true) {
                val count = inputStream.read(bytes, 0, BUFFER_SIZE)
                if (count == -1) break
                outputStream.write(bytes, 0, count)
            }
        } catch (ex: Exception) {
        }
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean =
        EXTERNAL_STORAGE_DOCUMENT == uri.authority

    private fun isDownloadsDocument(uri: Uri): Boolean = DOWNLOADS_DOCUMENT == uri.authority

    private fun isMediaDocument(uri: Uri): Boolean = MEDIA_DOCUMENT == uri.authority

    private fun isGooglePhotosUri(uri: Uri): Boolean = GOOGLE_PHOTOS_URI == uri.authority

    private fun isMeizuUri(uri: Uri): Boolean = MEIZU_URI == uri.authority
}