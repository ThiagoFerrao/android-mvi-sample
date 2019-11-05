package rxbase

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.reactivex.Observer

interface RxListAdapting<Command, ItemType> {
    val diffUtil: DiffUtil.ItemCallback<ItemType>
    val viewOutput: Observer<Command>

    fun updateData(data: List<ItemType>)
}

abstract class RxListAdapter<Command, ItemType, RxVH : RxViewHolder<Command, ItemType>>(
    override val diffUtil: DiffUtil.ItemCallback<ItemType>
) : ListAdapter<ItemType, RxVH>(diffUtil), RxListAdapting<Command, ItemType> {

    override fun onBindViewHolder(holder: RxVH, position: Int) {
        holder.bind(getItem(position))
        holder.bindOutputTo(viewOutput)
    }

    override fun updateData(data: List<ItemType>) {
        submitList(data)
    }
}