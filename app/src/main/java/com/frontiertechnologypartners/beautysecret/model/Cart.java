package com.frontiertechnologypartners.beautysecret.model;

public class Cart {
    private String pid,pname, quantity, price;

    public Cart() {
    }

    public Cart(String pname, String quantity, String price) {
        this.pname = pname;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
