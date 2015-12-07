package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistanceAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistanceTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by D.ayeshan on 12/7/2015..
 */
public class PersistanceExpenseManager extends ExpenseManager {
    private final Context context;

    public PersistanceExpenseManager(Context context){
        this.context=context;
        setup();
    }
    @Override
    public void setup() {

        DBHelper dbhelper=new DBHelper(this.context);

        TransactionDAO transactionDAO = new PersistanceTransactionDAO(dbhelper);
        setTransactionsDAO(transactionDAO);

        AccountDAO accountDAO = new PersistanceAccountDAO(dbhelper);
        setAccountsDAO(accountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}
