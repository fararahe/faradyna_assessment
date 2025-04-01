package com.faradyna.assessment.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.faradyna.assessment.R
import com.faradyna.assessment.domain.room.cart.CartEntityDomain
import com.faradyna.assessment.navigation.NavScreen
import com.faradyna.assessment.ui.components.CustomBackground
import com.faradyna.assessment.ui.components.CustomButton
import com.faradyna.assessment.ui.components.cards.DefaultCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    roomCartViewModel: RoomCartViewModel = hiltViewModel(),
) {

    val carts by roomCartViewModel.resultCart.collectAsState()

    LaunchedEffect(Unit) {
        roomCartViewModel.getCartItems()
    }

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
        CustomBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (carts.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(carts) { cart ->
                            CartItem(
                                cart = cart,
                                onQuantityChange = { newQty ->
                                    roomCartViewModel.updateCartItemQty(cart.productId, newQty)
                                },
                                onDelete = {
                                    roomCartViewModel.removeFromCart(cart.productId)
                                }
                            )
                        }
                    }
                    Spacer(Modifier.height(5.dp))
                    CustomButton(
                        text = stringResource(R.string.label_checkout_detail),
                        onClick = {
                            navController.navigate(NavScreen.CheckOut.route) {
                                popUpTo(NavScreen.Cart.route) { inclusive = true }
                            }
                        }
                    )
                    Spacer(Modifier.height(5.dp))
                } else {
                    Text(
                        text = stringResource(R.string.label_your_cart_is_empty),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun CartItem(
    cart: CartEntityDomain,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    DefaultCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = cart.image,
                contentDescription = cart.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = cart.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Price: ${cart.price}", style = MaterialTheme.typography.bodyMedium)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (cart.qty > 1) onQuantityChange(cart.qty.minus(1)) }) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = stringResource(R.string.desc_decrease)
                        )
                    }

                    Text(
                        text = cart.qty.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(onClick = { onQuantityChange(cart.qty.plus(1)) }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.desc_increase)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(R.string.desc_delete),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}