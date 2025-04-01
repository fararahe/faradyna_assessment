package com.faradyna.assessment.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.domain.room.cart.CartEntityDomain
import com.faradyna.assessment.domain.room.cart.RoomCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomCartViewModel @Inject constructor(
    private val roomCartRepository: RoomCartRepository
) : ViewModel() {

    private val _resultAddCart by lazy { MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)}
    val resultAddCart: StateFlow<ResultState<Boolean>> = _resultAddCart.asStateFlow()

    fun addToCart(product: ProductDomain) {
        viewModelScope.launch {
            val cartItem = roomCartRepository.getCartItemByProductId(product.id)
            cartItem?.let {
                roomCartRepository.updateCartItemQty(it.productId, it.qty.plus(1))
            } ?: run {
               roomCartRepository.addToCart(
                   CartEntityDomain(
                       productId = product.id,
                       title = product.title,
                       category = product.category,
                       description = product.description,
                       image = product.image,
                       price = product.price,
                       qty = 1
                   )
               )
            }
        }
        _resultAddCart.value = ResultState.Success(true)
    }

    private val _resultCart by lazy { MutableStateFlow<List<CartEntityDomain>>(emptyList())}
    val resultCart: StateFlow<List<CartEntityDomain>> = _resultCart.asStateFlow()

    fun getCartItems() {
        viewModelScope.launch {
            roomCartRepository.getCartItems().collect {
                if (it != null) {
                    _resultCart.value = it
                }
            }
        }
    }

    fun getCartItemByProductId(productId: Int, onResult: (CartEntityDomain?) -> Unit) {
        viewModelScope.launch {
            val item = roomCartRepository.getCartItemByProductId(productId)
            onResult(item)
        }
    }

    fun updateCartItemQty(productId: Int, quantity: Int) {
        viewModelScope.launch {
            roomCartRepository.updateCartItemQty(productId, quantity)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            roomCartRepository.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            roomCartRepository.clearCart()
        }
    }
}