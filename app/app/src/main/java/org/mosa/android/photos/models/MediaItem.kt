package org.mosa.android.photos.models

import android.net.Uri

sealed interface MediaItem<T : MediaItem<T>> : UniqueItem<T> {
    /**
     * The media type.
     */
    val mediaType: MediaType

    /**
     * A [Uri] identifying this media item.
     */
    val uri: Uri

    override fun areItemsTheSame(other: T) = this.uri == other.uri
}
