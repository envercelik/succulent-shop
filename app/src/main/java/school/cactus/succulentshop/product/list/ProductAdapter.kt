package school.cactus.succulentshop.product.list

import androidx.recyclerview.widget.DiffUtil
import school.cactus.succulentshop.R
import school.cactus.succulentshop.infra.BaseAdapter
import school.cactus.succulentshop.infra.BaseViewHolder
import school.cactus.succulentshop.product.ProductItem

class ProductAdapter : BaseAdapter<ProductItem>(DiffCallback()) {
    var itemClickListener: (ProductItem) -> Unit = {}

    class DiffCallback : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem) =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int) = R.layout.item_product

    override fun onBindViewHolder(holder: BaseViewHolder<ProductItem>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            itemClickListener(getItem(position))
        }
    }
}