package ePortfolio;
import ePortfolio.Investment;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
 * A Portfolio Object.
 * A Portfolio Object contains the parameters and functionalities of
 * a portfolio containing two different types of investments.
 */
public class Portfolio {

    private ArrayList <Investment> invst = new ArrayList<Investment>();
    private HashMap <String, ArrayList<Integer>> index = new HashMap<String, ArrayList<Integer>>();

    /**Base constructor. */
    public Portfolio(){
        this(new ArrayList<Investment>(), new HashMap<String, ArrayList<Integer>>());
    }

    /**
     * Portfolio constructor. 
     * Parameters:
     * @param invst - An arraylist holding all investments in the ePortfolio.
     * @param index - A hashmap that maps strings to an arraylist of integers,
     * holding indices of keywords.
     */
    public Portfolio(ArrayList <Investment> invst, HashMap <String, ArrayList<Integer>> index){
        this.invst = invst;
        this.index = index;
    }

    /**
     * Fetches the current array list.
     */
    public ArrayList <Investment> getArr(){
        return invst;
    }

    /**
     * Assigns value to the arraylist.
     */
    public void setArr(ArrayList <Investment> arr){
        invst = arr;
    }

    /**
     * Fetches the current hash map.
     */
    public HashMap <String, ArrayList<Integer>> getHashMap(){
        return index;
    }

    /**
     * Assigns value to the hash map.
     */
    public void setHashMap(HashMap <String, ArrayList<Integer>> hMap){
        index = hMap;
    }

    /**
     * Method for buying an investment and adding it to the Arraylist or Hashmap.
     * The investment added will either be a Stock or a MutualFund.
     * Parameters:
     * @param type - determines whether investment will be Stock or MutualFund.
     * @param symbol - short version of string name.
     * @param name - a string that describes the investment company.
     * @param quant - the amount of units a user can buy.
     * @param p - the amount one units is worth.
     * @return - string containing output to the message box.
     */
    public String buyInvst(String type, String symbol, String name, String quant, String p){
        boolean existingInv = false;
        
        int quantity = 0;
        double price = 0;

        if (symbol.isEmpty() || name.isEmpty() || quant.isEmpty() || p.isEmpty()){
            return "All mandatory text fields are not filled in. Please fill them.";
        }

        try{
            quantity = Integer.parseInt(quant);
            price = Double.parseDouble(p);
        } catch (NumberFormatException e){
            return "Inputted numeric value could not be converted.";
        }

        //finds if symbol exists in a previous investment
        for (int i = 0; i < invst.size(); i++){
            //If the symbol does exist, update the investment
            if (symbol.compareTo(invst.get(i).getSmbl()) == 0){
                try{
                    invst.get(i).setQuant(quantity);
                    invst.get(i).setPrice(price);
                    invst.get(i).calcBookVal();
                    invst.get(i).calcGain();
                } catch (Exception e){
                    return e.getMessage();
                }
                
                existingInv = true;
                return "Investment has same symbol as one already stored. Updated quantity and price.";
            }
        }

        //if entered symbol does not exist, create a new object
        if (existingInv == false){
            if (type.compareTo("stock") == 0){
                try{
                    Stock sNew = new Stock(symbol, name, quantity, price);
                    invst.add(sNew);
                } catch (Exception e){
                    return e.getMessage();
                }
            }
            else if (type.compareTo("mutualfund") == 0){
                try{
                    MutualFund mNew = new MutualFund(symbol, name, quantity, price);
                    invst.add(mNew);
                } catch (Exception e){
                    return e.getMessage();
                }
            }
            //calculate the bookVal for the investment just added
            invst.get(invst.size()-1).calcBookVal();
            invst.get(invst.size()-1).calcGain();
            
            //Add name to HashMap
            String words[] = name.split("[ ]+");
            for (int j = 0; j < words.length; j++){
                ArrayList<Integer> arr = new ArrayList<Integer>();
                
                //If index contains word
                if (index.containsKey(words[j])){
                    //retrieved list from keyword
                    arr = index.get(words[j]);
                }

                //Adds the current index onto the list and links
                arr.add(invst.size()-1);
                index.put(words[j], arr);
            }
        }
        return "";
    }

    /**
     * Method for selling an investment, calculating gain/loss, and/or
     * removing it to the Arraylist or Hashmap.
     * The investment sold will either be a Stock or a MutualFund.
     * Parameters:
     * @param symbol - short version of string name. Determines the investment.
     * @param quant - the amount of units a user can buy.
     * @param p - the amount one units is worth.
     * @return - string containing output to the message box.
     */
    public String sellInvst (String symbol, String quant, String p){

        int quantity = 0;
        double price = 0;

        if (symbol.isEmpty() || quant.isEmpty() || p.isEmpty()){
            return "All mandatory text fields are not filled in. Please fill them.";
        }

        try{
            quantity = Integer.parseInt(quant);
            price = Double.parseDouble(p);
        } catch (NumberFormatException e){
            return "Inputted numeric value could not be converted.";
        }
        //Checks if investment exists via symbol
        for (int i = 0; i < invst.size(); i++){
            if (symbol.compareTo(invst.get(i).getSmbl()) == 0){
                
                //Breaks out of the loop if too much quantity entered
                if (quantity > invst.get(i).getQuant()){
                    return "Amount of quantity entered not available to sell.";
                }

                int remainder = invst.get(i).getQuant() - quantity;
                
                //Checks if some quantity of share is left over
                if (remainder <= 0){
                    //Remove index from hash before removing investment
                    String words[] = invst.get(i).getName().split("[ ]+");
                    
                    for (int j = 0; j < words.length; j++){
                        ArrayList<Integer> arr = new ArrayList<Integer>();
                        arr = index.get(words[j]);

                        //If the key is only mapped to one value, remove it from hash
                        if (arr.size() == 1){
                            index.remove(words[j]);
                        }
                        else{
                            //If key is mapped to more than one value, delete position
                            arr.remove(i);
                            index.put(words[j], arr);
                        }
                    }

                    //remove the investment
                    invst.remove(i);
                    return "Removed investment.";
                }
                //Sells the share and sets new quantity and price
                else if (invst.get(i).getQuant() >= quantity){
                    try{
                        invst.get(i).sell(quantity, price);
                        invst.get(i).setQuant(remainder);
                        invst.get(i).setPrice(price);
                    } catch (Exception e){
                        return e.getMessage();
                    }

                    return "";
                }
                //Error message
                else{
                    return "Could not sell.";
                }
            }

            return "Symbol does not exist.";
        }

        return "No investments exist to be sold.";
    }

    /**
     * Method for updating any investment, by inputting a new price.
     * Calculates gain and changes bookValue based on the price change.
     * Parameters:
     * @param p - the amount one units is worth.
     * @param count - index passed to determine which item on the
     * Arraylist must be updated.
     * @return - string containing output to the message box.
     */
    public String updateInvst (String p, int count){
        double price = 0;

        if (p.isEmpty()){
            return "All mandatory text fields are not filled in. Please fill them.";
        }

        try{
            price = Double.parseDouble(p);
        } catch (NumberFormatException e){
            return "Inputted numeric value could not be converted.";
        }
        
        try{
            invst.get(count).setPrice(price);
            invst.get(count).calcGain();
        } catch (Exception e){
            return e.getMessage();
        }

        return "";
    }

    /**
     * Method for outputting the gain of any investments.
     * @param count - index passed to determine from which item on the
     * Arraylist the user wants the gain value.
     * @return - double containing the gain of the chosen investment.
     */
    public double getGainInvst (int count){
        double gain = 0;

        //Search through all investments and add up their gains to the total
        gain += invst.get(count).getGain();

        return gain;
    }

    /**
     * Method for search implementation throughout the Arraylist.
     * The investment will be found based on a single or combination of inputs
     * containing a symbol, keywords, and/or price ranges.
     * Parameters:
     * @param symbol - short version of string name. Investment identified through symbol.
     * @param keywords - a string that describes the investment company.
     * @param lPrice - the lower end of a price range.
     * @param hPrice - the higher end of a price range.
     * @return - The Arraylist containing all objects that have satisfied search requirements.
     */
    public ArrayList<Investment> searchInvst (String symbol, String keywords, double lPrice, double hPrice){
        ArrayList<Investment> tmpInvst = new ArrayList<Investment>();

        //If all search requests are empty
        if (symbol.isEmpty() && keywords.isEmpty() && lPrice < 0 && hPrice < 0){
            return invst;
        }

        //If user entered keywords 
        if (!keywords.isEmpty()){
            String userWords[] = keywords.split("[ ]+");
            ArrayList<Integer> arrFinal = new ArrayList<Integer>();

            for (int i = 0; i < userWords.length; i++){
                //if the user word is a key in the hash
                if(index.containsKey(userWords[i])){
                    if (i == 0){
                        arrFinal = index.get(userWords[i]);
                    }
                    else{
                        ArrayList<Integer> arr = new ArrayList<Integer>();
                        arr = index.get(userWords[i]);

                        boolean notFound = false;

                        //Compare the two integer lists for cross-overs
                        for (int j = 0; j < arrFinal.size(); j++){
                            for (int k = 0; k < arr.size(); k++){
                                if (arr.get(k) == arrFinal.get(j)){
                                    //increment j - NOT REMOVING element from arrFinal
                                    break;
                                }
                                //If the whole array was searched without finding match
                                if (k == arr.size()-1){
                                    notFound = true;
                                }
                            }
                            //if the element does not exist on both lists - remove element
                            if (notFound){
                                arrFinal.remove(j);
                                j--;
                                notFound = false;
                            }
                        }
                    }
                }
            }

            //Add all of the investment with the indices 
            for (int j = 0; j < arrFinal.size(); j++){
                tmpInvst.add(invst.get(arrFinal.get(j)));
            }
        }
        
        //If user entered a smbl
        if (!symbol.isEmpty()){
            for (int i = 0; i < invst.size(); i++){
                //compares user inputted smbl to all smbls in the invsts
                if(invst.get(i).getSmbl().compareToIgnoreCase(symbol) == 0){
                    if (!(tmpInvst.contains(invst.get(i)))){
                        tmpInvst.add(invst.get(i));
                    }
                }
            }
        }

        if (lPrice >= 0 || hPrice >= 0){
            if (lPrice < 0){
                lPrice = 0;
            }
            if (hPrice < 0){
                hPrice = Double.MAX_VALUE;
            }

            //Storing all investments based on ranges in tmp to be displayed
            for (int i = 0; i < invst.size(); i++){
                //checks if the price in invst is above lower range
                if(invst.get(i).getPrice() >= lPrice &&
                    invst.get(i).getPrice() < hPrice){
                    //If the temporary list already contains an investment - prevents duplicates
                    if (!(tmpInvst.contains(invst.get(i)))){
                        tmpInvst.add(invst.get(i));
                    }
                }
            }
        }

        return tmpInvst;
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
            Portfolio p = (Portfolio)other;
            return invst.equals(p.invst);
        }
    }
}
