package ePortfolio;
import ePortfolio.Investment;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * A Mutual Fund investment object. 
 * Child to Investment object.
 * Mutual Fund investment objects contains attributes of only mutual fund investments, its
 * own commission fee, as well as unique functionalities/calculations allowing the Porfolio access.
 */
public class MutualFund extends Investment{
    private static final double COMMISSION = 45;

    public MutualFund() throws Exception{
        this("", "", 0, 0.00);
    }

    /**
     * Mutual Fund constructor. 
     * Parameters:
     * @param symbol - short version of string name.
     * @param name - a string that describes the investment company.
     * @param quantity - the amount of units a user can buy.
     * @param price - the amount one units is worth.
     * 
     * @param gain - the amount of money the user has gained.
     * @param bookVal - a book value of the amount of money is still in the system.
     */
    public MutualFund(String symbol, String name, int quantity, double price) throws Exception{
        super(symbol, name, quantity, price);
    }

    /**
     * Copy constructor. Assigns attributes of a different version
     * of a Mutual Fund to the current Mutual Fund object.
     * Parameters:
     * @param original - Gives constructor a version of a Mutual Fund 
     * to duplicate.
     */
    public MutualFund (MutualFund original) throws Exception{
        super(original);
    }

    /**
     * Returns the type of investment based on the child class.
     */
    @Override
    public String getType(){
        return "mutualfund";
    }

    /**
     * Calculates the gain after a price change.
     */
    @Override
    public void calcGain(){
        gain = (quantity * price - COMMISSION) - bookVal;
    }

    /**
     * Calculates the gain after selling a quantity of shares.
     * Changes the book value according to the remaining amount of shares.
     * @param q - quantity input
     * @param p - price input
     */
    @Override
    public void sell(int q, double p){ 
        double payment = q * p - COMMISSION;

        gain = payment - (bookVal * (q/quantity));
        bookVal = bookVal * ((quantity-q)/(double)quantity);
    }
}
