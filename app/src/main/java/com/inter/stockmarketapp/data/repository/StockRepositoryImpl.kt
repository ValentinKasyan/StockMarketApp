package com.inter.stockmarketapp.data.repository

import com.inter.stockmarketapp.data.csv.CSVParser
import com.inter.stockmarketapp.data.local.StockDatabase
import com.inter.stockmarketapp.data.mapper.toCompanyListing
import com.inter.stockmarketapp.data.mapper.toCompanyListingEntity
import com.inter.stockmarketapp.data.remote.dto.StockApi
import com.inter.stockmarketapp.domain.model.CompanyListing
import com.inter.stockmarketapp.domain.repository.StockRepository
import com.inter.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingsParser: CSVParser<CompanyListing>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            //emit success resource
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            //if we actually want to make an api call
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                //to read csv file use byteStream
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data,invalid response"))
                null
            }

            //after we finish api call, we put data in local cash
            remoteListings?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(
                    listings.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(
                    Resource.Success(
                        data = dao.searchCompanyListing("").map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))
            }
        }
    }

}