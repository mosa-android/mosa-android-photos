package org.mosa.android.photos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.mosa.android.photos.GlimpseApplication

open class GlimpseViewModel(application: Application) : AndroidViewModel(application) {
    protected val mediaRepository = getApplication<GlimpseApplication>().mediaRepository

    @Suppress("EmptyMethod")
    final override fun <T : Application> getApplication() = super.getApplication<T>()
}
