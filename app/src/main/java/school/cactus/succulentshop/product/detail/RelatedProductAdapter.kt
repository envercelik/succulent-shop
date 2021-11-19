package school.cactus.succulentshop.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import school.cactus.succulentshop.databinding.ItemRelatedProductBinding
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.list.ProductAdapter.Companion.DIFF_CALLBACK


class RelatedProductAdapter :
    ListAdapter<ProductItem, RelatedProductAdapter.ProductHolder>(DIFF_CALLBACK) {

    var itemClickListener: (ProductItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ItemRelatedProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductHolder(
        private val binding: ItemRelatedProductBinding,
        private val itemClickListener: (ProductItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductItem) {
            binding.titleText.text = product.title
            binding.priceText.text = product.price

            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .centerInside()
                .into(binding.imageView)

            binding.root.setOnClickListener {
                itemClickListener(product)
            }
        }
    }
}