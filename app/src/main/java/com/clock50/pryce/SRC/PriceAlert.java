package com.clock50.pryce.SRC;


public class PriceAlert {

    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    private String name;
    private String price;
    private String target_price;
    private String url;
    private String temp_key;

    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public PriceAlert(){
        this.name = "";
        this.price = "";
        this.target_price = "";
        this.url = "";
    }

    public PriceAlert(String name, String price, String target_price, String url){
        this.name = name;
        this.price = price;
        this.target_price = target_price;
        this.url = url;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Getters & Setters                                                         *
     *                                                                           *
     * ************************************************************************* */

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTarget_price(String target_price) {
        this.target_price = target_price;
    }

    public void setTemp_key(String temp_key) {
        this.temp_key = temp_key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTemp_key() {
        return temp_key;
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


    /* ************************************************************************* *
     *                                                                           *
     * Basic Object Instance Methods                                             *
     *                                                                           *
     * ************************************************************************* */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceAlert that = (PriceAlert) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return temp_key != null ? temp_key.equals(that.temp_key) : that.temp_key == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (temp_key != null ? temp_key.hashCode() : 0);
        return result;
    }
}
