package com.clock50.pryce.SRC;

/**
 * Created by pc on 2017-09-05.
 */

public class PriceAlert {

    //
    private String name;
    private String price;
    private String target_price;

    public PriceAlert(String name, String price, String target_price){
        this.name = name;
        this.price = price;
        this.target_price = target_price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getTarget_price() {
        return target_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceAlert that = (PriceAlert) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return target_price != null ? target_price.equals(that.target_price) : that.target_price == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (target_price != null ? target_price.hashCode() : 0);
        return result;
    }
}
