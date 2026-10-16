package com.example.lab09e1

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 100): ProductsResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductModel
}
