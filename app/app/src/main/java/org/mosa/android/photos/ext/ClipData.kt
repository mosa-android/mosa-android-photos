package org.mosa.android.photos.ext

import android.content.ClipData

fun ClipData.asArray() = buildList {
    for (i in 0 until itemCount) {
        add(getItemAt(i))
    }
}
