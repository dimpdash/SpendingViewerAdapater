// IUpBankService.aidl
package com.spendingviewer.adapter.bank.up;

import com.spendingviewer.adapter.bank.up.model.UpAccountsResponseResult;
import com.spendingviewer.adapter.bank.up.model.CategoriesResponseResult;
import com.spendingviewer.adapter.bank.up.model.TransactionResponseResult;
import com.spendingviewer.adapter.bank.up.model.TransactionsResponseResult;
import com.spendingviewer.adapter.bank.up.model.UpTransactionId;

interface IUpBankService {
    TransactionsResponseResult getTransactions(int pageSize, String tag, String status, String since, String until, String category);
    TransactionsResponseResult getTransactionsPage(String link);
    TransactionResponseResult getTransaction(in UpTransactionId id);
    CategoriesResponseResult getCategories();
    UpAccountsResponseResult getAccounts(int pageSize, String accountType, String ownershipType);
    UpAccountsResponseResult getAccountsPage(String link);
}

