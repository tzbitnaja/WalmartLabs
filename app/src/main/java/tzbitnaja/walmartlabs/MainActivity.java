package tzbitnaja.walmartlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import tzbitnaja.model.*;
import tzbitnaja.utils.FetchData;
/**
 * Main activity responsible for showing the listView of items, scrolling through it, and then lazy-loading more
 * @author tzbitnaja
 */

public class MainActivity extends Activity {
    //product listView
    private ListView listView;
    //custom adapter that will hold each Product within the list
    private CustomAdapter adapter;
    //detailed product view, here to obtain context
    private ProductDetailActivity details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find products' listview by its id
        listView = (ListView) findViewById(R.id.product_list);
        //add a footer loading bar
        listView.addFooterView(new ProgressBar(this));

        //instantiate the adapter and the detail activity
        adapter = new CustomAdapter(this);
        details = new ProductDetailActivity(this);
        //set adapter for the listview
        listView.setAdapter(adapter);

        //give listview an on scroll litener to be able to lazy load more items when end of the list is reached
        listView.setOnScrollListener(new LazyLoader(){
            @Override
            public void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
                loadItems();
            }
        });

        final Context cont = this;
        //define what the listened does
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            /**
             * defines intents that will be sent to the detailed product activity
             * @param parent
             * @param view
             * @param position which item was clicked on
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = adapter.getItem(position);
                Intent detailedIntent = new Intent(cont, ProductDetailActivity.class);

                detailedIntent.putExtra(getString(R.string.product),
                                        selectedProduct.getProductName());
                detailedIntent.putExtra(getString(R.string.price),
                                        selectedProduct.getPrice());
                detailedIntent.putExtra(getString(R.string.shortInfo),
                                        selectedProduct.getShortDescription());
                detailedIntent.putExtra(getString(R.string.longInfo),
                                        selectedProduct.getLongDescription());
                detailedIntent.putExtra(getString(R.string.image),
                                        selectedProduct.getProductImage());
                detailedIntent.putExtra(getString(R.string.instock),
                                        selectedProduct.isInStock());
                detailedIntent.putExtra(getString(R.string.reviews),
                                        selectedProduct.getReviewCount());
                detailedIntent.putExtra(getString(R.string.rating),
                                        selectedProduct.getReviewRating());

                startActivity(detailedIntent);
            }
        });
    }

    //tiny ViewHolder class to hold the adapter's views
    static class ViewHolder {
        protected TextView productTextView;
        protected TextView priceTextView;
        protected ImageView productImageView;
    }

    /**
     * method that loads more items once end of the list is reached
     */
    private void loadItems() {
        // Index is required to fetch the next set of items
        int startIndex = adapter.getCount();

        // Fetch more items using Async FetchProduct class
        new FetchData(startIndex, listener).execute();
    }

    //add all new data set items to the adaptere
    private ResponseListener<ArrayList<Product>> listener = new ResponseListener<ArrayList<Product>>() {
        @Override
        public void onResponse(ArrayList<Product> responseItems) {
            adapter.addAll(responseItems);
        }
    };
}
