package com.thami.resolve.di

import com.thami.resolve.data.report.PdfReportGenerator
import com.thami.resolve.data.repository.ShoppingRepositoryImpl
import com.thami.resolve.domain.report.ReportGenerator
import com.thami.resolve.domain.repository.ShoppingRepository
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
    abstract fun bindShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository

    @Binds
    @Singleton
    abstract fun bindReportGenerator(impl: PdfReportGenerator): ReportGenerator
}
