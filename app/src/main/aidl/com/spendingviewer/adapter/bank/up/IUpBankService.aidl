// IUpBankService.aidl
package com.spendingviewer.adapter.bank.up;

import com.spendingviewer.adapter.bank.up.model.UpAccountsResponse;
import com.spendingviewer.adapter.bank.up.model.CategoriesResponse;
import com.spendingviewer.adapter.bank.up.model.TransactionResponse;
import com.spendingviewer.adapter.bank.up.model.TransactionsResponse;
import com.spendingviewer.adapter.bank.up.model.UpTransactionId;

interface IUpBankService {
    TransactionsResponse getTransactionsPage(String link);
    TransactionResponse getTransaction(in UpTransactionId id);
    TransactionsResponse getTransactions(int pageSize, String tag, String status, String since, String until, String category);
    CategoriesResponse getCategories();
    UpAccountsResponse getAccounts(int pageSize, String accountType, String ownershipType);
    UpAccountsResponse getAccountsPage(String link);
}
