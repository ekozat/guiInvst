package ePortfolio;
import ePortfolio.Investment;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * A Stock investment object. 
 * Child to Investment object.
 * Stock investment objects contains all attributes of only stock investments, its
 * own commission fee, as well as unique functionalities/calculations allowing the Porfolio access.
 */
public class Stock extends Investment{
    private static final double COMMISSION = 9.99;

    public Stock() throws Exception{
        this("", "", 0, 0.00);
    }
    /**
     * Stock constructor. 
     * Parameters:
     * @param symbol - short version of string name.
     * @param name - a string that describes the investment company.
     * @param quantity - the amount of shares a user can buy.
     * @param price - the amount one share is worth.
     * 
     * @param gain - the amount of money the user has gained.
     * @param bookVal - a book value of the amount of money is still in the system.
     */
    public Stock(String symbol, String name, int quantity, double price) throws Exception{
        super(symbol, name, quantity, price);
    }

    /**
     * Copy constructor. Assigns attributes of a different version
     * of a Stock to the current Stock object.
     * Parameters:
     * @param original - Gives constructor a version of a Stock 
     * to duplicate.
     */
    public Stock (Stock original) throws Exception{
        super(original);
    }

    /**
     * Returns the type of investment based on the child class.
     */
    @Override
    public String getType(){
        return "stock";
    }

    /**
     * Calculates the book value.
     */
    @Override
    public void calcBookVal(){
        bookVal += quantity * price + COMMISSION;
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
     */
    @Override
    public void sell(int q, double p){
        double payment = q * p - COMMISSION;

        gain = payment - (bookVal * (q/quantity));
        bookVal = bookVal * ((quantity-q)/(double)quantity);
    }

}