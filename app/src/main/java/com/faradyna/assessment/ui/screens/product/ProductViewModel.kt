package com.faradyna.assessment.ui.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.core.configs.state.ViewState
import com.faradyna.assessment.domain.cart.request.CartRequest
import com.faradyna.assessment.domain.cart.resp.CartDomain
import com.faradyna.assessment.domain.cart.usecase.StoreCartUsecase
import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.domain.product.resp.RatingDomain
import com.faradyna.assessment.domain.product.usecase.FetchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchProductUsecase: FetchProductUseCase,
    private val storeCartUsecase: StoreCartUsecase,
) : ViewModel() {

    private val _pageState by lazy { MutableStateFlow<ViewState<ProductDomain?>>(ViewState.Loading) }
    val pageState: StateFlow<ViewState<ProductDomain?>> = _pageState.asStateFlow()

    fun setPageViewState(state: ViewState<ProductDomain?>) {
        _pageState.value = state
    }

    private val _resultProducts by lazy { MutableStateFlow<ResultState<ProductDomain?>>(ResultState.Idle) }
    val resultProducts : StateFlow<ResultState<ProductDomain?>> = _resultProducts.asStateFlow()

    fun setResultProduct(result: ResultState<ProductDomain?>) {
        _resultProducts.value = result
    }

    private val _productDetail = MutableStateFlow<ProductDomain>(
        ProductDomain(
            id = 0,
            title = "",
            price = 0.0,
            description = "",
            category = "",
            image = "",
            rating = RatingDomain(
                rate = 0.0,
                count = 0
            )
        )
    )
    val productDetail: StateFlow<ProductDomain> = _productDetail.asStateFlow()

    fun setProduct(products: ProductDomain) {
        _productDetail.value = products
    }

    fun fetchProduct(id: Int) {
        viewModelScope.launch {
            fetchProductUsecase.execute(FetchProductUseCase.RequestValues(id)).collect {
                it.result.onSuccess { data ->
                    setPageViewState(ViewState.Success(data))
                }.onError { error ->
                    setPageViewState(ViewState.Error(error))
                }
            }
        }
    }
}