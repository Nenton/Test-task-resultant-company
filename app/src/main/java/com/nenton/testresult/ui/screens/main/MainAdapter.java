package com.nenton.testresult.ui.screens.main;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenton.testresult.R;
import com.nenton.testresult.data.network.res.Stocks;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Stocks.Stock> mCurrencies;

    public MainAdapter() {
        mCurrencies = new ArrayList<>();
    }

    public void updateListCurrencies(List<Stocks.Stock> currencies) {
        mCurrencies = currencies;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mCurrencies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCurrencies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCurrencyName;
        private TextView mCurrencyVolume;
        private TextView mCurrencyAmount;

        ViewHolder(View itemView) {
            super(itemView);
            mCurrencyName = itemView.findViewById(R.id.currency_name_tv);
            mCurrencyVolume = itemView.findViewById(R.id.currency_volume_tv);
            mCurrencyAmount = itemView.findViewById(R.id.currency_amount_tv);
        }

        @SuppressLint("DefaultLocale")
        void bind(Stocks.Stock currency) {
            mCurrencyName.setText(currency.getName());
            mCurrencyVolume.setText(String.valueOf(currency.getVolume()));
            mCurrencyAmount.setText(String.format("%.2f", currency.getPrice().getAmount()));
        }
    }
}
