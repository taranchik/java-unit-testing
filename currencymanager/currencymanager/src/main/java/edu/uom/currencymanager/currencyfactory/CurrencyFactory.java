package edu.uom.currencymanager.currencyfactory;

import edu.uom.currencymanager.CurrencyManager;
import edu.uom.currencymanager.currencies.CurrencyDatabase;

public class CurrencyFactory {
    public CurrencyManager createCurrencyManager(CurrencyDatabase currencyDatabase) throws Exception {
        return new CurrencyManager(currencyDatabase);
    }

    public CurrencyManager createCurrencyManager() throws Exception {
        return new CurrencyManager(this.createCurrencyDatabase());
    }

    public CurrencyDatabase createCurrencyDatabase() throws Exception {
        return new CurrencyDatabase();
    }
}
