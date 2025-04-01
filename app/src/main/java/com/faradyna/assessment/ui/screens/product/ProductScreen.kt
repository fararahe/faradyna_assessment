package com.faradyna.assessment.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.product.resp.ProductDomain
import com.faradyna.assessment.ui.common.HandleErrorStates
import com.faradyna.assessment.ui.components.CustomButton
import com.faradyna.assessment.ui.components.cards.CardShimmer
import com.faradyna.assessment.ui.screens.cart.RoomCartViewModel
import com.faradyna.assessment.utility.extensions.toastShortExt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController,
    productId: Int,
    productViewModel: ProductViewModel = hiltViewModel(),
    roomCartViewModel: RoomCartViewModel = hiltViewModel(),
) {

    val pageState by productViewModel.pageState.collectAsState()
    val productState by productViewModel.resultProducts.collectAsState()
    val productDetail by productViewModel.productDetail.collectAsState()
    val addCartState by roomCartViewModel.resultAddCart.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.fetchProduct(productId)
    }

    HandleProductSuccess(
        productViewModel = productViewModel,
        productState = productState
    )

    HandleCartSuccess(
        navController = navController,
        addCartState = addCartState
    )

    HandleErrorStates(
        navController = navController,
        productState,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.label_product_detail)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        pageState
            .onLoading {
                ProductSkeleton(paddingValues)
            }
            .onSuccess {
                ProductSection(
                    roomCartViewModel,
                    productDetail,
                    paddingValues
                )
            }
            .onSuccessData {
                productViewModel.setResultProduct(ResultState.Success(it))
            }
            .onError {
                productViewModel.setResultProduct(ResultState.Error(it))
            }
    }
}

@Composable
fun ProductSection(
    roomCartViewModel: RoomCartViewModel,
    productDetail: ProductDomain,
    paddingValues: PaddingValues
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = productDetail.image,
            contentDescription = productDetail.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = productDetail.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = productDetail.category,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = productDetail.description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$${productDetail.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(
            text = stringResource(R.string.label_add_to_cart),
            onClick = {
                roomCartViewModel.addToCart(productDetail)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProductSkeleton(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(23.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Composable
fun HandleProductSuccess(
    productViewModel: ProductViewModel,
    productState: ResultState<ProductDomain?>,
) {
    LaunchedEffect(productState) {
        (productState as? ResultState.Success)?.data?.let { data ->
            productViewModel.setProduct(data)
        }
    }
}

@Composable
fun HandleCartSuccess(
    navController: NavController,
    addCartState: ResultState<Boolean?>
) {
    val context = LocalContext.current
    LaunchedEffect(addCartState) {
        (addCartState as? ResultState.Success)?.let {
            context.toastShortExt(context.getString(R.string.labele_success_add_to_cart))
            navController.popBackStack()
        }
    }
}