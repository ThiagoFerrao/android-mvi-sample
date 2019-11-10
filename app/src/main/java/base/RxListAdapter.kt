package base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.reactivex.Observer

interface RxListAdapting<Command, ItemType> {
    val diffUtil: DiffUtil.ItemCallback<ItemType>
    val viewOutput: Observer<Command>

    fun updateData(data: List<ItemType>)
}

abstract class RxListAdapter<Command, ItemType, RxVH : BaseViewHolder<Command, ItemType>>(
    override val diffUtil: DiffUtil.ItemCallback<ItemType>
) : ListAdapter<ItemType, RxVH>(diffUtil), RxListAdapting<Command, ItemType> {

    override fun onBindViewHolder(holder: RxVH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun updateData(data: List<ItemType>) {
        submitList(data)
    }
}