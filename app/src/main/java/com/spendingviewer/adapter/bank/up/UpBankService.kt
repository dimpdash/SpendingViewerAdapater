package com.spendingviewer.adapter.bank.up

import com.spendingviewer.adapter.bank.up.model.AccountType
import com.spendingviewer.adapter.bank.up.model.AidlError
import com.spendingviewer.adapter.bank.up.model.CategoriesResponseResult
import com.spendingviewer.adapter.bank.up.model.OwnershipType
import com.spendingviewer.adapter.bank.up.model.TransactionResponseResult
import com.spendingviewer.adapter.bank.up.model.TransactionsResponseResult
import com.spendingviewer.adapter.bank.up.model.UpAccountsResponseResult
import com.spendingviewer.adapter.bank.up.model.UpTransactionId
import kotlinx.coroutines.runBlocking

class UpBankService(private val upBankApi: UpBankApi) : IUpBankService.Stub() {

    override fun getTransactions(pageSize: Int, tag: String?, status: String?, since: String?, until: String?, category: String?): TransactionsResponseResult = runBlocking {
        try {
            val result = upBankApi.getTransactions(pageSize, tag, status, since, until, category).getResult().getOrThrow()
            TransactionsResponseResult(result, null)
        } catch (e: Exception) {
            TransactionsResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }

    override fun getTransactionsPage(link: String): TransactionsResponseResult = runBlocking {
        try {
            val result = upBankApi.getTransactionsPage(link).getResult().getOrThrow()
            TransactionsResponseResult(result, null)
        } catch (e: Exception) {
            TransactionsResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }

    override fun getTransaction(id: UpTransactionId): TransactionResponseResult = runBlocking {
        try {
            val result = upBankApi.getTransaction(id).getResult().getOrThrow()
            TransactionResponseResult(result, null)
        } catch (e: Exception) {
            TransactionResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }

    override fun getCategories(): CategoriesResponseResult = runBlocking {
        try {
            val result = upBankApi.getCategories().getResult().getOrThrow()
            CategoriesResponseResult(result, null)
        } catch (e: Exception) {
            CategoriesResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }

    override fun getAccounts(pageSize: Int, accountType: String?, ownershipType: String?): UpAccountsResponseResult = runBlocking {
        try {
            val upAccountType = accountType?.let { AccountType.valueOf(it) }
            val upOwnershipType = ownershipType?.let { OwnershipType.valueOf(it) }
            val result = upBankApi.getAccounts(pageSize, upAccountType, upOwnershipType).getResult().getOrThrow()
            UpAccountsResponseResult(result, null)
        } catch (e: Exception) {
            UpAccountsResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }

    override fun getAccountsPage(link: String): UpAccountsResponseResult = runBlocking {
        try {
            val result = upBankApi.getAccountsPage(link).getResult().getOrThrow()
            UpAccountsResponseResult(result, null)
        } catch (e: Exception) {
            UpAccountsResponseResult(null, AidlError(e.message, e.stackTraceToString()))
        }
    }
}
