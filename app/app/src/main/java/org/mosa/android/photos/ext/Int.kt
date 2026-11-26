package org.mosa.android.photos.ext

import android.content.res.Resources.getSystem
import kotlin.math.roundToInt

/**
 * dp -> px.
 */
val Int.px
    get() = (this * getSystem().displayMetrics.density).roundToInt()
