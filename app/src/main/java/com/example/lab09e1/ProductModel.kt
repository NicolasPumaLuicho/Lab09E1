package com.example.lab09e1

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("brand") val brand: String?,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("warrantyInformation") val warrantyInformation: String?,
    @SerializedName("shippingInformation") val shippingInformation: String?,
    @SerializedName("availabilityStatus") val availabilityStatus: String?
)

data class ProductsResponse(
    @SerializedName("products") val products: List<ProductModel>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)
