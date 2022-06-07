package com.inter.stockmarketapp.di

import com.inter.stockmarketapp.data.csv.CSVParser
import com.inter.stockmarketapp.data.csv.CompanyListingsParser
import com.inter.stockmarketapp.data.csv.IntradayInfoParser
import com.inter.stockmarketapp.data.repository.StockRepositoryImpl
import com.inter.stockmarketapp.domain.model.CompanyListing
import com.inter.stockmarketapp.domain.model.IntradayInfo
import com.inter.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ):StockRepository

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

}