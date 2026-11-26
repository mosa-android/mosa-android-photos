package org.mosa.android.photos.ui.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import org.mosa.android.photos.ext.px
import org.mosa.android.photos.viewmodels.AlbumViewModel

class ThumbnailLayoutManager(
    context: Context,
    adapter: RecyclerView.Adapter<*>,
) : DisplayAwareGridLayoutManager(context, 4, 4.px) {
    init {
        spanSizeLookup = ThumbnailSpanSizeLookup(adapter, spanCount)
    }

    private class ThumbnailSpanSizeLookup(
        private val adapter: RecyclerView.Adapter<*>,
        private val spanCount: Int,
    ) : SpanSizeLookup() {
        override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
            AlbumViewModel.AlbumContent.ViewType.THUMBNAIL.ordinal -> 1
            AlbumViewModel.AlbumContent.ViewType.DATE_HEADER.ordinal -> spanCount
            else -> throw Exception("Unknown view type")
        }
    }
}
