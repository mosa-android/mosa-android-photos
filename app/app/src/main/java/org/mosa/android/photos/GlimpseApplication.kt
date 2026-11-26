package org.mosa.android.photos

import android.app.Application
import com.google.android.material.color.DynamicColors
import org.mosa.android.photos.repository.MediaRepository

class GlimpseApplication : Application() {
    val mediaRepository by lazy { MediaRepository(applicationContext) }

    override fun onCreate() {
        super.onCreate()

        // Observe dynamic colors changes
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
