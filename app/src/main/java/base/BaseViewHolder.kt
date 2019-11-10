package base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

interface BaseViewHolding<Command, ItemType> {
    val containerView: View

    fun bind(data: ItemType)
}

abstract class BaseViewHolder<Command, ItemType>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), BaseViewHolding<Command, ItemType>, LayoutContainer