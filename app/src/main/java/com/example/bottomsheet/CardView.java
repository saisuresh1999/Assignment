package com.example.bottomsheet;


public class CardView {
private String cost,symbol,bookName,sNo;

    public CardView(String cost, String symbol, String bookName, String sNo) {
        this.cost = cost;
        this.symbol = symbol;
        this.bookName = bookName;
        this.sNo = sNo;
    }

    public String getCost() {
        return cost;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBookName() {
        return bookName;
    }

    public String getsNo() {
        return sNo;
    }
}
