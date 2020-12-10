package org.AimEx.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import org.AimEx.data.addresses.AddressBookDAO;
import org.AimEx.data.addresses.AddressBookEntry;
import org.AimEx.data.balances.Balance;
import org.AimEx.data.balances.BalanceDAO;
import org.AimEx.data.chaininfo.ChainInfo;
import org.AimEx.data.chaininfo.ChainInfoDAO;
import org.AimEx.data.tokens.Token;
import org.AimEx.data.tokens.TokenDAO;
import org.AimEx.data.transactions.TransactionDAO;
import org.AimEx.data.transactions.TransactionEntity;

@Database(entities = {AddressBookEntry.class, Balance.class, ChainInfo.class, Token.class, TransactionEntity.class}, version = 6)
@TypeConverters({RoomTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AddressBookDAO getAddressBook();

    public abstract TokenDAO getTokens();

    public abstract TransactionDAO getTransactions();

    public abstract BalanceDAO getBalances();

    public abstract ChainInfoDAO getChainInfo();

}
