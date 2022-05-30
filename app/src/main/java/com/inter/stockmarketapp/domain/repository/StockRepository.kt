package com.inter.stockmarketapp.domain.repository

import com.inter.stockmarketapp.domain.model.CompanyListing
import com.inter.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        //get data from api, if it false we get data from cash
        fetchFromRemote: Boolean,
        //what we search for
        query: String
        //Resource allows to display the correct answer or error information
        // we work with local cash return Flow coroutines
    ): Flow<Resource<List<CompanyListing>>>
}