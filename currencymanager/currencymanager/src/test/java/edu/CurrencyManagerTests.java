//package edu.uom;
package edu;

import edu.uom.currencymanager.CurrencyManager;
import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;

import edu.uom.currencymanager.currencies.ExchangeRate;
import edu.uom.currencymanager.currencyfactory.CurrencyFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CurrencyManagerTests {

    CurrencyFactory currencyFactory = new CurrencyFactory();
    CurrencyManager manager;
    CurrencyDatabase currencyDatabase;

    @Before
    public void setup() throws Exception {
        manager = currencyFactory.createCurrencyManager();
        currencyDatabase = currencyFactory.createCurrencyDatabase();
    }

    @After
    public void teardown() {
        currencyFactory = null;
        manager = null;
        currencyDatabase = null;
    }

    @Test
    public void testListCurrencies() throws Exception {
        //Setup
        List<Currency> currencies = currencyDatabase.getCurrencies();
        String listCurrencies = new String();

        //Exercise
        for (Currency currency : currencies) {
            listCurrencies += currency.toString() + '\n';
        }

        //Verify
        String exceptedOutput =
                "AUD - Australian Dollar\n" +
                        "EUR - Euro\n" +
                        "GBP - British Pound\n" +
                        "TRY - Turkish Lira\n" +
                        "USD - US Dollar\n" +
                        "RUB - Russian Ruble\n";

        assertEquals(exceptedOutput, listCurrencies);
    }

    @Test
    public void testListExchangeRatesBetweenMajorCurrencies() throws Exception {
        //Setup
        List<ExchangeRate> exchangeRates = new CurrencyManager(new CurrencyDatabase()).getMajorCurrencyRates();
        boolean switcher = true;

        //Exercise
        for (ExchangeRate temp : exchangeRates) {
//            System.out.println(temp.toString());
            if(temp.rate < 0.0 || temp.rate > 1.5)
                switcher = false;
        }

        //Verify
        assert(switcher);
    }

    @Test
    public void testCheckExchangeRate() throws Exception {
        //Setup
        List<Currency> currencies = currencyDatabase.getMajorCurrencies();

        //Exercise
        for (Currency src : currencies) {
            for (Currency dst : currencies) {
                if (src != dst) {
                    try {
                        ExchangeRate rate = manager.getExchangeRate(src.code, dst.code);
//                        System.out.println(rate.toString());
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                }
            }
        }
    }

    @Test
    public void testAddCurrency() throws Exception {
        //Exercise
        try {
            manager.addCurrency("RUB", "Russian Ruble", true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testDeleteCurrency() throws Exception {
        //Exercise
        try {
            manager.deleteCurrencyWithCode("RUB");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testQuitFromList() throws Exception {
        //Setup

        //Exercise
        try {
            CurrencyManager.exit = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGetCurrencyByCode(){
        //Setup
        Currency currency = null;

        //Exercise
        try {
            currency = currencyDatabase.getCurrencyByCode("EUR");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Verify
        assertEquals(currency.toString(), "EUR - Euro");
    }

    @Test
    public void testGetMajorCurrencies(){
        //Setup
        List<Currency> majorCurrencies = new ArrayList<Currency>();

        //Exercise
        try {
            majorCurrencies = currencyDatabase.getMajorCurrencies();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Verify
        assertEquals(majorCurrencies.toString(), "[EUR - Euro, " +
                                                        "GBP - British Pound, " +
                                                        "USD - US Dollar, " +
                                                        "RUB - Russian Ruble]");
    }
}
