package com.example.lab09e1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lab09e1.ui.theme.Ambar
import com.example.lab09e1.ui.theme.AmbarClaro
import com.example.lab09e1.ui.theme.MarronSuave
import com.example.lab09e1.ui.theme.NaranjaOscuro
import com.example.lab09e1.ui.theme.NaranjaPrincipal
import com.example.lab09e1.ui.theme.RojoDescuento
import com.example.lab09e1.ui.theme.VerdeStock
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenInicio(navController: NavHostController, servicio: ProductApiService) {
    var productos by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    LaunchedEffect(Unit) {
        productos = servicio.getProducts().products
    }
    val destacados = productos.sortedByDescending { it.rating }.take(8)
    val categorias = productos.map { it.category }.distinct().sorted()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.horizontalGradient(listOf(NaranjaPrincipal, Ambar)))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    "Tienda Dummy",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Catálogo de productos reales consumido desde la API de DummyJSON.",
                    color = Color.White.copy(alpha = 0.92f)
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { navController.navigate("productos") { launchSingleTop = true } },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = NaranjaPrincipal
                    )
                ) {
                    Text("Ver catalogo", fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ResumenCard(productos.size.toString(), "Productos", Modifier.weight(1f))
            ResumenCard(categorias.size.toString(), "Categorías", Modifier.weight(1f))
        }
        Spacer(Modifier.height(20.dp))
        Text(
            "Productos destacados",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Los mejor calificados de la tienda",
            style = MaterialTheme.typography.bodyMedium,
            color = MarronSuave
        )
        Spacer(Modifier.height(10.dp))
        if (destacados.isEmpty()) {
            Text("Cargando productos...", color = MarronSuave)
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(destacados) { p ->
                    TarjetaDestacado(p) {
                        navController.navigate("productoVer/${p.id}")
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(
            "Categorías",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categorias.forEach { c ->
                AssistChip(
                    onClick = { navController.navigate("productos") { launchSingleTop = true } },
                    label = { Text(c.replaceFirstChar { it.uppercase() }) }
                )
            }
        }
    }
}

@Composable
fun ResumenCard(valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Card(
        modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AmbarClaro)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                valor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = NaranjaOscuro
            )
            Text(etiqueta, color = NaranjaOscuro)
        }
    }
}

@Composable
fun TarjetaDestacado(producto: ProductModel, onClick: () -> Unit) {
    Card(
        Modifier
            .width(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = producto.thumbnail,
                contentDescription = producto.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(10.dp)) {
                Text(
                    producto.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Ambar,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(" ${"%.1f".format(producto.rating)}", fontSize = 13.sp)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "$ " + "%.2f".format(producto.price),
                    color = NaranjaPrincipal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ScreenProductos(navController: NavHostController, servicio: ProductApiService) {
    var productos by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var texto by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        productos = servicio.getProducts().products
    }
    val filtrados = remember(texto, productos) {
        if (texto.isBlank()) productos
        else productos.filter {
            it.title.contains(texto, ignoreCase = true) ||
                    (it.brand ?: "").contains(texto, ignoreCase = true) ||
                    it.category.contains(texto, ignoreCase = true)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            "Catálogo de productos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Buscar producto") },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )
        Text(
            "${filtrados.size} productos encontrados",
            style = MaterialTheme.typography.labelLarge,
            color = MarronSuave
        )
        Spacer(Modifier.height(8.dp))
        if (productos.isEmpty()) {
            Text("Cargando productos...", color = MarronSuave)
        } else {
            LazyColumn(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filtrados) { p ->
                    TarjetaProducto(p) {
                        navController.navigate("productoVer/${p.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaProducto(producto: ProductModel, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(Modifier.padding(10.dp)) {
            AsyncImage(
                model = producto.thumbnail,
                contentDescription = producto.title,
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    producto.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    producto.brand ?: producto.category,
                    color = MarronSuave,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Ambar,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(" ${"%.1f".format(producto.rating)}", fontSize = 13.sp)
                    Spacer(Modifier.width(8.dp))
                    SelloDescuento(producto.discountPercentage)
                }
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "$ " + "%.2f".format(producto.price),
                        color = NaranjaPrincipal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "$ " + "%.2f".format(precioOriginal(producto)),
                        color = MarronSuave,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

@Composable
fun SelloDescuento(descuento: Double, modifier: Modifier = Modifier) {
    if (descuento > 0) {
        Box(
            modifier
                .clip(RoundedCornerShape(8.dp))
                .background(RojoDescuento)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                "-${"%.0f".format(descuento)}%",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun precioOriginal(producto: ProductModel): Double {
    val factor = 1 - producto.discountPercentage / 100
    if (factor <= 0) return producto.price
    return producto.price / factor
}

@Composable
fun Estrellas(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (i <= rating.roundToInt()) Ambar else Color(0xFFE0D6D0),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun ScreenProducto(navController: NavHostController, servicio: ProductApiService, id: Int) {
    var producto by remember { mutableStateOf<ProductModel?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        producto = servicio.getProductById(id)
    }
    Box(Modifier.fillMaxSize()) {
        val p = producto
        if (p == null) {
            Text(
                "Cargando producto...",
                Modifier.align(Alignment.Center),
                color = MarronSuave
            )
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                    Text(
                        "Detalle del producto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(8.dp))
                Box(Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = p.thumbnail,
                        contentDescription = p.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    SelloDescuento(
                        p.discountPercentage,
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    p.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    (p.brand ?: "Genérico") + " - " + p.category,
                    color = MarronSuave
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Estrellas(p.rating)
                    Spacer(Modifier.width(8.dp))
                    Text("${"%.1f".format(p.rating)} de 5", color = MarronSuave)
                }
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "$ " + "%.2f".format(p.price),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NaranjaPrincipal
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "$ " + "%.2f".format(precioOriginal(p)),
                        color = MarronSuave,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(Modifier.width(10.dp))
                    SelloDescuento(p.discountPercentage)
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    if (p.stock > 0) "Disponible: ${p.stock} unidades" else "Agotado",
                    color = if (p.stock > 0) VerdeStock else RojoDescuento,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
                Text(p.description, style = MaterialTheme.typography.bodyMedium)
                if (!p.tags.isNullOrEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(p.tags) { t ->
                            AssistChip(onClick = {}, label = { Text(t) })
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AmbarClaro)
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text("- Garantía: ${p.warrantyInformation ?: "No especificada"}")
                        Text("- Envío: ${p.shippingInformation ?: "No especificado"}")
                        Text("- Estado: ${p.availabilityStatus ?: "No disponible"}")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Producto agregado al carrito (demo)")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Agregar al carrito",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        SnackbarHost(
            snackbarHostState,
            Modifier.align(Alignment.BottomCenter)
        )
    }
}
