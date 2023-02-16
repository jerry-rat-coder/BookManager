package com.wangpeng.bms.model;

import java.io.Serializable;

public class BookType implements Serializable {
    private Integer booktypeid;

    private String booktypename;

    private String booktypedesc;
    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getBooktypeid() {
        return booktypeid;
    }

    public void setBooktypeid(Integer booktypeid) {
        this.booktypeid = booktypeid;
    }

    public String getBooktypename() {
        return booktypename;
    }

    public void setBooktypename(String booktypename) {
        this.booktypename = booktypename;
    }

    public String getBooktypedesc() {
        return booktypedesc;
    }

    public void setBooktypedesc(String booktypedesc) {
        this.booktypedesc = booktypedesc;
    }
}