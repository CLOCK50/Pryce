package com.clock50.pryce.SRC.managers;


/**
 * Manages all the PriceAlerts by allowing the user to add, modify, delete them. Provides access to
 * notification methods for the PriceAlerts.
 */
public class PriceAlertManager {


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Constructs the extractor class and its instance variables.
     *
     */
    public void construct(){
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Checks whether the price of the product is below or at the target price. If it is, then it
     * notifies the end-user.
     *
     * @param price
     * @param old_price
     * @param target_price
     */
    public void checkPriceAlert(String price, String old_price, String target_price){
        //If price that is extracted, is below target price for the first time, send notification
        if(!price.matches(old_price) && isBelowPrice(price, target_price)){
            //TODO: Notification pops up
        }
    }

    /**
     * Returns whether the given price is below the target price.
     *
     * @param price the price of the product
     * @param target the target price of the product
     * @return true if the price is below the target
     */
    public static boolean isBelowPrice(String price, String target){
        Double p = Double.parseDouble(price.substring(5));
        Double t = Double.parseDouble(target.substring(5));
        return p <= t;
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static PriceAlertManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PriceAlertManager INSTANCE = new PriceAlertManager();
    }

}
