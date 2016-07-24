package com.android.androidannotactions.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.androidannotactions.MainActivity;
import com.android.androidannotactions.R;
import com.android.androidannotactions.model.ResponseGoogle;
import com.android.androidannotactions.model.Result;
import com.android.androidannotactions.rest.RestV;

import java.util.ArrayList;

/**
 * Created by vinicius on 7/21/16.
 */
public class GoogleAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> resultList;
    private Context context;

    public GoogleAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index).toString();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.item_autocomplete, null);
        }

        String x = getItem(position);
        if (x != null) {
            TextView tt = (TextView) v.findViewById(R.id.t1);

            if (tt != null)
                tt.setText(x);

        }

        return v;


    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.


                    MainActivity.responseEnderecoGoogle = new ResponseGoogle();
                    if (!TextUtils.isEmpty(constraint))
                        MainActivity.responseEnderecoGoogle = (ResponseGoogle) RestV.getToObject(RestV.URL_GEOCODING_ADDRESS+constraint.toString(), MainActivity.responseEnderecoGoogle, true, true);
                    if (MainActivity.responseEnderecoGoogle != null) {
                        MainActivity.listEnderecoResult = new ArrayList<String>();
                        if (MainActivity.responseEnderecoGoogle != null && MainActivity.responseEnderecoGoogle.getResults() != null && MainActivity.responseEnderecoGoogle.getResults().size() > 0) {
                            for (Result result : MainActivity.responseEnderecoGoogle.getResults()) {
                                MainActivity.listEnderecoResult.add(result.getFormattedAddress());
                                filterResults.values = MainActivity.listEnderecoResult;
                                filterResults.count = resultList.size();

                            }
                        }

                    }

                    //if (MainActivity.listEnderecoResult != null && MainActivity.listEnderecoResult.size() > 0)


                    // Assign the data to the FilterResults

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

}