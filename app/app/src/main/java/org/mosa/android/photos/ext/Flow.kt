package org.mosa.android.photos.ext

import android.database.Cursor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mosa.android.photos.models.ColumnIndexCache

fun <T> Flow<Cursor?>.mapEachRow(
    mapping: (ColumnIndexCache) -> T,
) = map { it.mapEachRow(mapping) }
