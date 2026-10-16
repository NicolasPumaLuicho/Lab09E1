# Lab09 - Ejercicio 1: Tienda de Productos

Aplicación Android con Jetpack Compose que consume el recurso **products** de la API
pública de DummyJSON: `https://dummyjson.com/`

## Funcionalidad

- Pantalla de inicio con banner, resumen de la tienda (productos y categorías),
  carrusel de productos destacados (mejor calificados) y accesos rápidos por categoría.
- Catálogo completo de productos con buscador en vivo (por título, marca o categoría).
- Detalle del producto: imagen, marca, calificación con estrellas, precio con descuento,
  stock, descripción, etiquetas, garantía, envío y botón "Agregar al carrito" (demostración).

## Tecnologías usadas

- Kotlin + Jetpack Compose (Material 3)
- Retrofit 2.11.0 + converter-gson
- Navigation Compose (rutas "inicio", "productos" y "productoVer/{id}")
- Coil 2.2.2 para cargar las imágenes de los productos
- Corrutinas de Kotlin (`suspend` + `LaunchedEffect`)

## Cómo ejecutar

1. Abrir el proyecto en Android Studio.
2. Ejecutar en un emulador o dispositivo con conexión a internet.
3. Explorar el catálogo y tocar cualquier producto para ver su detalle.

Curso: Programación en Móviles - Tecsup (Laboratorio 09, Ejercicio 1)
