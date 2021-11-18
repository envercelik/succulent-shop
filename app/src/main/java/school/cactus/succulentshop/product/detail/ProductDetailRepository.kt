package school.cactus.succulentshop.product.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.common.Resource
import school.cactus.succulentshop.common.Resource.Error.*
import school.cactus.succulentshop.common.Resource.Success
import school.cactus.succulentshop.db.db
import school.cactus.succulentshop.product.ProductItem
import school.cactus.succulentshop.product.toProductItem

class ProductDetailRepository {
    suspend fun fetchProductDetail(productId: Int): Flow<Resource<ProductItem>> = flow {

        val cachedProduct = db.productDao().getById(productId)

        if (cachedProduct == null) {
            val response = try {
                api.getProductById(productId)
            } catch (ex: Exception) {
                null
            }

            emit(
                when (response?.code()) {
                    null -> Failure()
                    200 -> Success(response.body()!!.toProductItem())
                    401 -> TokenExpired()
                    else -> UnexpectedError()
                }
            )
        } else {
            emit(Success(cachedProduct.toProductItem()))
        }
    }
}