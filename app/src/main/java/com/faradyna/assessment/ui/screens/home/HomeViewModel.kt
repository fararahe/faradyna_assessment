package com.faradyna.assessment.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.core.configs.state.ViewState
import com.faradyna.assessment.data.room.user.UserEntity
import com.faradyna.assessment.domain.datastore.DatastoreManager
import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.domain.product.usecase.FetchProductsUseCase
import com.faradyna.assessment.domain.room.user.UserEntityDomain
import com.faradyna.assessment.utility.qualifiers.DataStoreManagerQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @DataStoreManagerQualifier private val dataStore: DatastoreManager,
    private val fetchProductsUseCase: FetchProductsUseCase,
) : ViewModel() {

    init {
        getUser()
        fetchProducts()
    }

    private val _user by lazy { MutableStateFlow<UserEntityDomain?>(null) }
    val user : StateFlow<UserEntityDomain?> = _user.asStateFlow()

    private fun getUser() {
        viewModelScope.launch {
            dataStore.getUser().collect {
                _user.value = it
            }
        }
    }

    private val _pageState by lazy { MutableStateFlow<ViewState<List<ProductDomain>?>>(ViewState.Loading) }
    val pageState : StateFlow<ViewState<List<ProductDomain>?>> = _pageState.asStateFlow()

    fun setPageViewState(state: ViewState<List<ProductDomain>?>) {
        _pageState.value = state
    }

    private val _isRefreshing by lazy { MutableStateFlow(false) }
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()

    fun updateIsRefreshing(isRefreshing: Boolean) {
        _isRefreshing.value = isRefreshing
    }

    private val _showProfileSheet by lazy { MutableStateFlow<Boolean>(false) }
    val showProfileSheet : StateFlow<Boolean> = _showProfileSheet.asStateFlow()

    fun setShowProfileSheet(show: Boolean) {
        _showProfileSheet.value = show
    }

    private val _resultProducts by lazy { MutableStateFlow<ResultState<List<ProductDomain>?>>(ResultState.Idle) }
    val resultProducts : StateFlow<ResultState<List<ProductDomain>?>> = _resultProducts.asStateFlow()

    fun setResultProducts(result: ResultState<List<ProductDomain>?>) {
        _resultProducts.value = result
    }

    fun fetchProducts() {
        viewModelScope.launch {
            fetchProductsUseCase.execute(FetchProductsUseCase.RequestValues()).collect {
                it.result.onSuccess { data ->
                    setPageViewState(ViewState.Success(data))
                }.onError { error ->
                    setPageViewState(ViewState.Error(error))
                }
            }
        }
    }

    private val defaultCategoryName = context.getString(R.string.label_all)
    private val defaultCategory = setOf(defaultCategoryName)
    private val _categories by lazy { MutableStateFlow(defaultCategory) }
    val categories : StateFlow<Set<String>> = _categories.asStateFlow()

    private val _products by lazy { MutableStateFlow<List<ProductDomain>>(listOf()) }

    fun setProducts(data: List<ProductDomain>) {
        _categories.value = defaultCategory + data.map { it.category }.toSet()
        _products.value = data
    }

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    val filteredProducts: StateFlow<List<ProductDomain>> =
        combine(_products, _selectedCategory) { productList, category ->
            if (category.isNullOrEmpty() || category == defaultCategoryName) productList
            else productList.filter { it.category == category }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _expanded = MutableStateFlow<Boolean>(false)
    val expanded = _expanded.asStateFlow()

    fun setExpanded(expanded: Boolean) {
        _expanded.value = expanded
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.clearDataStore()
            _showProfileSheet.value = false
        }
    }
}