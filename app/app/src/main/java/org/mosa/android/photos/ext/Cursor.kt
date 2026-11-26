package org.mosa.android.photos.ext

import android.database.Cursor
import org.mosa.android.photos.models.ColumnIndexCache

fun <T> Cursor?.mapEachRow(
    mapping: (ColumnIndexCache) -> T,
) = this?.use { cursor ->
    if (!cursor.moveToFirst()) {
        return@use emptyList()
    }

    val columnIndexCache = ColumnIndexCache(cursor)

    val data = buildList {
        do {
            add(mapping(columnIndexCache))
        } while (cursor.moveToNext())
    }

    data.toList()
} ?: emptyList()
