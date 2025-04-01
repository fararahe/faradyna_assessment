package com.faradyna.assessment.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.navigation.NavScreen
import com.faradyna.assessment.ui.common.HandleErrorStates
import com.faradyna.assessment.ui.common.NetworkError
import com.faradyna.assessment.ui.components.CustomBackground
import com.faradyna.assessment.ui.components.CustomButton
import com.faradyna.assessment.ui.components.cards.CardShimmer
import com.faradyna.assessment.ui.components.cards.DefaultCard
import com.faradyna.assessment.ui.screens.cart.RoomCartViewModel
import com.faradyna.assessment.utility.enums.ButtonStyle
import com.faradyna.assessment.utility.extensions.compose.getColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: RoomCartViewModel = hiltViewModel(),
) {
    val pageState by homeViewModel.pageState.collectAsState()
    val productsState by homeViewModel.resultProducts.collectAsState()
    val showProfileSheet by homeViewModel.showProfileSheet.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            homeViewModel.fetchProducts()
            homeViewModel.updateIsRefreshing(false)
        }
    }

    HandleProductsSuccess(
        homeViewModel = homeViewModel,
        productState = productsState
    )

    HandleErrorStates(
        navController = navController,
        productsState
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        pageState
            .onLoading {
                PullToRefreshBox(
                    state = refreshState,
                    onRefresh = { homeViewModel.updateIsRefreshing(true) },
                    isRefreshing = isRefreshing,
                ) {
                    CustomBackground {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(16.dp)
                        ) {
                            TopSectionSkeleton()
                            Spacer(modifier = Modifier.height(16.dp))
                            CategorySectionSkeleton()
                            Spacer(modifier = Modifier.height(16.dp))
                            ProductsSkeleton()
                        }
                    }
                }
            }
            .onSuccessData { data ->
                homeViewModel.setResultProducts(ResultState.Success(data))
            }
            .onSuccess {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    TopSection(
                        homeViewModel = homeViewModel,
                        navController = navController,
                        onProfileClicked = { homeViewModel.setShowProfileSheet(true) }
                    )
                    CustomBackground(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CategorySection(
                                homeViewModel = homeViewModel
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            PullToRefreshBox(
                                modifier = Modifier.weight(1f),
                                state = refreshState,
                                onRefresh = { homeViewModel.updateIsRefreshing(true) },
                                isRefreshing = isRefreshing,
                            ) {
                                ProductsSection(
                                    modifier = Modifier.fillMaxSize(),
                                    homeViewModel = homeViewModel,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
            .onError {
                homeViewModel.setResultProducts(ResultState.Error(it))
                NetworkError(
                    isRefreshing = isRefreshing,
                    refreshState = refreshState,
                    onRefresh = { homeViewModel.fetchProducts() }
                )
            }
    }

    if (showProfileSheet) {
        ProfileSection(
            onLogout = {
                cartViewModel.clearCart()
                homeViewModel.logout()
                navController.navigate(NavScreen.Login.route) {
                    popUpTo(0)
                }
            },
            onDismiss = {
                homeViewModel.setShowProfileSheet(false)
            }
        )
    }
}

@Composable
fun TopSectionSkeleton() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CardShimmer(
            modifier = Modifier
                .width(120.dp)
                .height(28.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            CardShimmer(
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            CardShimmer(
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun TopSection(
    homeViewModel: HomeViewModel,
    navController: NavController,
    onProfileClicked: () -> Unit
) {
    val user by homeViewModel.user.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = user?.getName() ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onProfileClicked) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.desc_profile),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { navController.navigate(NavScreen.Cart.route) }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = stringResource(R.string.desc_cart),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

@Composable
fun CategorySectionSkeleton() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CardShimmer(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(48.dp)
        )
    }
}

@Composable
fun CategorySection(
    homeViewModel: HomeViewModel
) {
    val categories by homeViewModel.categories.collectAsState()
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()
    val expanded by homeViewModel.expanded.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
        Button (
            colors = ButtonStyle.SECONDARY.getColors(),
            onClick = { homeViewModel.setExpanded(true) }
        ) {
            Text(selectedCategory ?: stringResource(R.string.label_all))
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.desc_category)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { homeViewModel.setExpanded(false) }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        homeViewModel.setSelectedCategory(category)
                        homeViewModel.setExpanded(false)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSection(
    onLogout: () -> Unit = {},
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.desc_profile),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("John Doe", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("john.doe@example.com", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                text = stringResource(R.string.label_logout),
                style = ButtonStyle.SECONDARY,
                onClick = onLogout,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProductsSection(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val products by homeViewModel.filteredProducts.collectAsState()

    if(products.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                DefaultCard(
                    modifier = Modifier.clickable {
                        navController.navigate(NavScreen.Product.createRoute(product.id))
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(5.dp)
                        ) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = product.title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(100.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = product.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = product.category,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "$${product.price}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.label_no_products_found),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun ProductsSkeleton() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) {
            CardShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .height(100.dp)
            )
        }
    }
}

@Composable
fun HandleProductsSuccess(
    homeViewModel: HomeViewModel,
    productState: ResultState<List<ProductDomain>?>,
) {
    LaunchedEffect(productState) {
        (productState as? ResultState.Success)?.data?.let { data ->
            homeViewModel.setProducts(data)
        }
    }
}