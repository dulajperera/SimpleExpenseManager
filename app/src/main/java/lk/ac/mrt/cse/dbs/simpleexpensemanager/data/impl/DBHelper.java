package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by D.ayeshan on 12/7/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "130430V";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountCreateQuery=
                "CREATE TABLE IF NOT EXISTS `account` ("+
                        " `accountNo` varchar(10) NOT NULL,"+
                        " `bankName` varchar(20) NOT NULL,"+
                        " `accountHolderName` varchar(30) NOT NULL,"+
                        " `balance` decimal(10,2) NOT NULL,"+
                        " PRIMARY KEY (`accountNo`)"+
                        ");";

        String transactionCreateQuery=
                "CREATE TABLE IF NOT EXISTS `transactions` ("+
                        " `accountNo` varchar(10) NOT NULL,"+
                        " `expenseType` varchar(10) NOT NULL,"+
                        " `amount` decimal(10,2) NOT NULL,"+
                        " `date` varchar(15) NOT NULL"+
                        ");";

        db.execSQL(accountCreateQuery);
        db.execSQL(transactionCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }

    public boolean insertAccount (Account acc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountHolderName", acc.getAccountHolderName());
        contentValues.put("accountNo", acc.getAccountNo());
        contentValues.put("bankName", acc.getBankName());
        contentValues.put("balance", acc.getBalance());
        db.insert("account", null, contentValues);

        return true;
    }

    public boolean insertTransaction (Transaction tc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", tc.getDate());
        contentValues.put("accountNo", tc.getAccountNo());
        contentValues.put("expenseType", tc.getExpenseType());
        contentValues.put("amount", tc.getAmount());
        db.insert("transactions", null, contentValues);
        return true;
    }

    public boolean updateBalance (String accNo,Double value,boolean dec)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String operator=" + ";
        if(dec)operator=" - ";
        db.execSQL("UPDATE account SET balance = balance"+operator+value.toString()+" where accountNo='"+accNo+"';");
        return true;
    }

    public Integer deleteAccount (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("account",
                "accountNo = ? ",
                new String[] { id });
    }

    public ArrayList<Account> getAllAccounts()
    {
        ArrayList<Account> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from account", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Account(res.getString(0),res.getString(1),res.getString(2),res.getDouble(3)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Transaction> getAllTransactions()
    {
        ArrayList<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from transactions", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Transaction(res.getString(3),res.getString(0),res.getString(1),res.getDouble(2)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Transaction> getAllTransactionsLimit(int limit)
    {
        ArrayList<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from transactions limit "+limit, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Transaction(res.getString(3),res.getString(0),res.getString(1),res.getDouble(2)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllAccountNumbers()
    {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select accountNo from account;", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(0));
            res.moveToNext();
        }
        return array_list;
    }

    public Account getAccount(String accountNo)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from account" + " where accountNo="+accountNo, null );
        res.moveToFirst();

        return new Account(res.getString(0),res.getString(1),res.getString(2),res.getDouble(3));
    }
}