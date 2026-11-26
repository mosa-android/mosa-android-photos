package org.mosa.android.photos.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import org.mosa.android.photos.models.UniqueItem

class UniqueItemDiffCallback<T : UniqueItem<T>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.areContentsTheSame(newItem)
}
