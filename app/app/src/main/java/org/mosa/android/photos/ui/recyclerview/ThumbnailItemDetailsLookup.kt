package org.mosa.android.photos.ui.recyclerview

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import org.mosa.android.photos.models.Media
import kotlin.reflect.safeCast

class ThumbnailItemDetailsLookup(
    private val recyclerView: RecyclerView,
) : ItemDetailsLookup<Media>() {
    override fun getItemDetails(e: MotionEvent) =
        recyclerView.findChildViewUnder(e.x, e.y)?.let { childView ->
            recyclerView.getChildViewHolder(childView)?.let { viewHolder ->
                ThumbnailAdapter.ThumbnailViewHolder::class.safeCast(viewHolder)?.itemDetails
            }
        }
}
