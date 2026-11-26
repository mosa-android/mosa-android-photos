package org.mosa.android.photos.models

import java.nio.ByteBuffer

data class MotionPhoto(
    val metadata: Metadata,
    val videoBuffer: ByteBuffer,
) {
    data class Metadata(
        val version: Int,
        val presentationTimestampUs: Long? = null,
        val videoLength: Int,
        val videoMimeType: String,
    )
}
