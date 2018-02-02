package com.nenton.testresult.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stocks {

    @SerializedName("stock")
    @Expose
    public List<Stock> stock = null;
    @SerializedName("as_of")
    @Expose
    public String asOf;

    public List<Stock> getStock() {
        return stock;
    }

    public String getAsOf() {
        return asOf;
    }

    public class Price {

        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("amount")
        @Expose
        public float amount;

        public String getCurrency() {
            return currency;
        }

        public float getAmount() {
            return amount;
        }
    }

    public class Stock {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("price")
        @Expose
        public Price price;
        @SerializedName("percent_change")
        @Expose
        public float percentChange;
        @SerializedName("volume")
        @Expose
        public int volume;
        @SerializedName("symbol")
        @Expose
        public String symbol;

        public String getName() {
            return name;
        }

        public Price getPrice() {
            return price;
        }

        public float getPercentChange() {
            return percentChange;
        }

        public int getVolume() {
            return volume;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
