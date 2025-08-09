// IUpBankService.aidl
package com.spendingviewer.adapter.bank.up;

/**
 * Note: This is a simplified AIDL interface.
 * The complex data types (TransactionsResponse, TransactionResponse, etc.)
 * have been replaced with String. You will need to create Parcelable versions
 * of these data classes and update this interface accordingly.
 */
interface IUpBankService {
    /**
     * Corresponds to UpBankApi.getTransactions
     * Returns a JSON string representing TransactionsResponse.
     */
    String getTransactions(int pageSize, String tag, String status, String since, String until, String category);

    /**
     * Corresponds to UpBankApi.getTransactionsPage
     * Returns a JSON string representing TransactionsResponse.
     */
    String getTransactionsPage(String link);

    /**
     * Corresponds to UpBankApi.getTransaction
     * Returns a JSON string representing TransactionResponse.
     */
    String getTransaction(String id);

    /**
     * Corresponds to UpBankApi.getCategories
     * Returns a JSON string representing CategoriesResponse.
     */
    String getCategories();

    /**
     * Corresponds to UpBankApi.getAccounts
     * Returns a JSON string representing UpAccountsResponse.
     */
    String getAccounts(int pageSize, String accountType, String ownershipType);

    /**
     * Corresponds to UpBankApi.getAccountsPage
     * Returns a JSON string representing UpAccountsResponse.
     */
    String getAccountsPage(String link);
}
