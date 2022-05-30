package com.inter.stockmarketapp.data.remote.dto

import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {

    //to download CSV file
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @retrofit2.http.Query("apiKey") apiKey: String
    ): ResponseBody

    companion object {
        //https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo
        const val API_KEY = "FVC0EPTFICGETGHE"
        const val BASE_URL = "https://alphavantage.co"
    }
}