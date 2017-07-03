package tzbitnaja.walmartlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tzbitnaja.model.Product;
import tzbitnaja.walmartlabs.R;
import com.squareup.picasso.Picasso;

/**
 * Custom Adapter that is being used by the ListView in order to display items in a list layout
 * It specifies which views to populate with which data fields
 * @author tzbitnaja
 * */

public class CustomAdapter extends ArrayAdapter<Product>{//} implements {//View.OnClickListener {
    Context mContext;
    private ArrayList<Product> productList;
    MainActivity.ViewHolder holder;

    //constructor to save the context in order to later draw images using Picasso
    public CustomAdapter(Context context) {
        super(context, R.layout.row_item);
        productList = new ArrayList<Product>();
        mContext = context;
    }

    /**
     * the adapter's getview method where it inflates the view if there is none
     * or it populates the list view with items using the layout of this adapter
     * @param position of the item
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Product product = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);

            //holder is being used to hold data in order it needs to be easily accessed later on
            holder = new MainActivity.ViewHolder();
            holder.productTextView = (TextView)convertView.findViewById(R.id.productName);
            holder.priceTextView = (TextView)convertView.findViewById(R.id.price);
            holder.productImageView = (ImageView) convertView.findViewById(R.id.productImage);

            convertView.setTag(holder);
        }
        else{
            holder = (MainActivity.ViewHolder) convertView.getTag();
        }

        TextView productId = holder.productTextView;
        TextView price = holder.priceTextView;
        ImageView productImage = holder.productImageView;

        //Picasso is an image grabber that allows to easily convert a URL into a drawable (can also be done manually if necessary)
        Picasso.with(mContext).load(product.getProductImage()).into(productImage);

        //populate the views with data
        productId.setText(product.getProductName());
        price.setText(product.getPrice());

        return convertView;
    }

    public ArrayList<Product> getProductList(){
        return productList;
    }
}
