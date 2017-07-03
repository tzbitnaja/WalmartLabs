package tzbitnaja.utils;

import android.os.AsyncTask;

import java.util.ArrayList;

import tzbitnaja.model.Product;
import tzbitnaja.walmartlabs.ResponseListener;

/**
 * FetchData class of the utils package provides the wrapper to get JSON data.
 * AsynTask is created to pull data in the background, to avoid trying to use networking within a main thread
 * @author tzbitnaja
 * */
public class FetchData extends AsyncTask<Void, Void, ArrayList<Product>> {
    //essentially a counter to keep track which cumulative index the list is at
    private int pageOffset = 0;
    //instantiate GetProducts object to extract JSON data from the API call to WalmartLabs
    GetProducts fd = new GetProducts();
    //make sure response listener exists in order to communicate with the main activity
    private ResponseListener<ArrayList<Product>> responseListener;

    //constructor that allows to pass the current page offset,
    // order to lazy-load the next appropriate set of items
    public FetchData(int si, ResponseListener listener){
        pageOffset = si;
        responseListener = listener;
    }

    /**
     * Background task to fetch data with the given pageoffset
     * the number of items per page is hardcoded,
     * but in future version it's possible to add a clickable object & method to show(10_20_30) items per page instead
     */
    @Override
    protected ArrayList<Product> doInBackground(Void... params) {
        String jsonStr = fd.getJSON(Integer.toString(pageOffset), "10");
        //extracts products array out of the JSON object
        return fd.parsePage(jsonStr).getProducts();
    }

    //notify the listener that new items have arrived
    protected void onPostExecute(ArrayList<Product> newItems){
        responseListener.onResponse(newItems);
    }
}
