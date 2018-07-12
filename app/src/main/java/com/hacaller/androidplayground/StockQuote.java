package com.hacaller.androidplayground;

/**
 * Created by Herbert Caller on 27/04/2016.
 */
public class StockQuote {
    String Name;
    String Symbol;
    float LastPrice;
    float Change;
    float ChangePercent;
    String Timestamp;
    float MSdate;
    float MarketCap;
    int Volume;
    float ChangeYTD;
    float ChangePercentYTD;
    float High;
    float Low;
    float Open;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public float getLastPrice() {
        return LastPrice;
    }

    public void setLastPrice(float lastPrice) {
        LastPrice = lastPrice;
    }

    public float getChange() {
        return Change;
    }

    public void setChange(float change) {
        Change = change;
    }

    public float getChangePercent() {
        return ChangePercent;
    }

    public void setChangePercent(float changePercent) {
        ChangePercent = changePercent;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public float getMSdate() {
        return MSdate;
    }

    public void setMSdate(float MSdate) {
        this.MSdate = MSdate;
    }

    public float getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(float marketCap) {
        MarketCap = marketCap;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }

    public float getChangeYTD() {
        return ChangeYTD;
    }

    public void setChangeYTD(float changeYTD) {
        ChangeYTD = changeYTD;
    }

    public float getChangePercentYTD() {
        return ChangePercentYTD;
    }

    public void setChangePercentYTD(float changePercentYTD) {
        ChangePercentYTD = changePercentYTD;
    }

    public float getHigh() {
        return High;
    }

    public void setHigh(float high) {
        High = high;
    }

    public float getLow() {
        return Low;
    }

    public void setLow(float low) {
        Low = low;
    }

    public float getOpen() {
        return Open;
    }

    public void setOpen(float open) {
        Open = open;
    }
}
