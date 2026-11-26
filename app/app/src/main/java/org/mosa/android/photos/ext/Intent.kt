package org.mosa.android.photos.ext

import android.content.Intent
import org.mosa.android.photos.models.Media
import org.mosa.android.photos.models.MediaType

fun buildShareIntent(vararg medias: Media) = Intent().apply {
    assert(medias.isNotEmpty()) { "No media" }

    if (medias.size == 1) {
        action = Intent.ACTION_SEND
        medias[0].let {
            putExtra(Intent.EXTRA_STREAM, it.uri)
            type = it.mimeType
        }
    } else {
        action = Intent.ACTION_SEND_MULTIPLE
        putParcelableArrayListExtra(
            Intent.EXTRA_STREAM,
            medias.map { it.uri }.toCollection(ArrayList())
        )
        type = when {
            medias.all { it.mediaType == MediaType.IMAGE } -> "image/*"
            medias.all { it.mediaType == MediaType.VIDEO } -> "video/*"
            else -> {
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
                "*/*"
            }
        }
    }
    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
}

fun buildEditIntent(media: Media) = Intent().apply {
    action = Intent.ACTION_EDIT
    setDataAndType(media.uri, media.mimeType)
    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
}

fun buildUseAsIntent(media: Media) = Intent().apply {
    action = Intent.ACTION_ATTACH_DATA
    setDataAndType(media.uri, media.mimeType)
    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
}
