package school.cactus.succulentshop.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.common.Resource
import school.cactus.succulentshop.db.db


class ProductRepository {
    suspend fun fetchProducts(): Flow<Resource<List<ProductItem>>> = flow {

        val cachedProducts = db.productDao().getAll()

        if (cachedProducts.isNotEmpty()) {
            emit(Resource.Success(cachedProducts.toProductItemList()))
        }

        val response = try {
            api.listAllProducts()
        } catch (ex: Exception) {
            null
        }

        when (response?.code()) {
            null -> emit(Resource.Error.Failure())
            200 -> {
                val productItems = response.body()!!.toProductItemList()
                emit(Resource.Success(productItems))
                db.productDao().insertAll(productItems.toProductEntityList())
            }
            401 -> emit(Resource.Error.TokenExpired())
            else -> emit(Resource.Error.UnexpectedError())
        }
    }

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
                    null -> Resource.Error.Failure()
                    200 -> Resource.Success(response.body()!!.toProductItem())
                    401 -> Resource.Error.TokenExpired()
                    else -> Resource.Error.UnexpectedError()
                }
            )
        } else {
            emit(Resource.Success(cachedProduct.toProductItem()))
        }
    }
}
