package ePortfolio;

/**
 * An Investment object.
 * Parent to Stock investment and MutualFund investment objects.
 * Investment objects contains all attributes of any investment
 * and unique functionalities that are inherited by its children allowing
 * manipulation of the Portfolio.
 */
public abstract class Investment {
    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;

    protected double bookVal;
    protected double gain;

    //Base Constructor
    public Investment() throws Exception{
        this("", "", 0, 0.00);
    }

    /**
     * Investment constructor. 
     * Parameters:
     * @param symbol - short version of string name.
     * @param name - a string that describes the investment company.
     * @param quantity - the amount of units a user can buy.
     * @param price - the amount one units is worth.
     * 
     * @param gain - the amount of money the user has gained.
     * @param bookVal - a book value of the amount of money is still in the system.
     */
    public Investment(String symbol, String name, int quantity, double price) throws Exception{
        try{
            this.symbol = symbol;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        } catch (Exception e){
            throw new Exception("Attributes of investment are unable to be stored.");
        }

        gain = 0;
        bookVal = 0;
    }

    /**
     * Copy constructor. Assigns attributes of a different version
     * of Investment to the current Investment object.
     * Parameters:
     * @param original - Gives constructor a version of Investment 
     * to duplicate.
     */
    public Investment (Investment original) throws Exception{
        this(original.symbol, original.name, original.quantity, original.price);
        this.quantity = original.quantity;
        this.bookVal = original.bookVal;
        this.gain = original.gain;
    }

    /**
     * Gets the symbol of a share.
     */
    public String getSmbl(){
        return symbol;
    }

    /**
     * Sets the symbol of a share.
     */
    public void setSmbl(String smbl){
        symbol = smbl;
    }

    /**
     * Gets the named company of a share.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of a company owning a share.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the quantity of shares in one investment.
     */
    public int getQuant(){
        return quantity;
    }

    /**
     * Changes the quantity of shares.
     * @param q - quantity input 
     */
    public void setQuant(int q) throws Exception{
        if (q < 0){
            throw new Exception("Quantity is not valid as negative.");
        }
        else{
            quantity = q;
        }
    }

    /**
     * Gets the price of an individual share.
     */
    public double getPrice(){
        return price;
    }

    /**
     * Changes the price per share.
     * @param p - price input 
     */
    public void setPrice(double p) throws Exception{
        if (p < 0){
            throw new Exception("Price is not valid as negative.");
        }
        else{
            price = p;
        }
    }

    /**
     * Gets the gain of the investment.
     */
    public double getGain(){
        return gain;
    }

    /**
     * Returns the Book Value of the investment.
     */
    public double getbookVal(){
        return bookVal;
    }

    /**
     * Returns the Book Value of the investment.
     */
    public String getType(){
        return "";
    }

    /**
     * Allows display of all attributes of investment.
     */
    public String toString(){
        return "Name: " + name + 
                "\nSymbol: " + symbol + 
                "\nQuantity: " + quantity +
                "\nPrice: $" + price + 
                "\nBook Value: $" + bookVal +
                "\n\n";
    }

    /**
     * Assigns the book value.
     */
    public void setBookVal(double bookVal) throws Exception{
        if (bookVal < 0){
            throw new Exception("Book Value entered was below zero.");
        }
        else{
            this.bookVal = bookVal;
        }
    }

    /**
     * Calculates the book value.
     */
    public void calcBookVal(){
        bookVal += quantity * price;
    }

    /**
     * Calculates the gain after a price change.
     */
    public void calcGain(){
        gain = 0;
    }

    /**
     * Calculates the gain after selling a quantity of shares.
     * Changes the book value according to the remaining amount of shares.
     * @param q - quantity input
     * @param p - price input
     */
    public void sell(int q, double p){
        double payment = -1;

        gain = payment;
        bookVal = 100;
    }

    /**
     * Equals method. Checks if one object is equivalent to the other.
     * @param other - An object wanted to be compared against.
     * @return - A boolean that will be true if objects are equivalent,
     * and false if they are not.
     */
    public boolean equals (Object other){
        if (other == null){
            return false;
        }
        else if (getClass() != other.getClass()){
            return false;
        }
        else{
            Investment i = (Investment)other;
            return i.toString().equals(other.toString());
        }
    }
}
