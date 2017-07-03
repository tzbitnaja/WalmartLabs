package tzbitnaja.walmartlabs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Second Activity to represent the detailed product view
 * It waits for intent to arrive and then populated all of its views with the data
 * @author tzbitnaja
 */
public class ProductDetailActivity extends AppCompatActivity {
    private RelativeLayout mRelativeLayout;
    private Context mContext;

    //constructor that allows us to obtain the context in order to later draw the image using Picasso
    public ProductDetailActivity(Context context){
        mContext = context;
    }

    public ProductDetailActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //get the intent in order to get the info about the clicked item from the main activity
        Intent intent = getIntent();

        //just get all the intent's data
        String product = intent.getExtras().getString(getString(R.string.product));
        String inStock = intent.getExtras().getString(getString(R.string.instock));
        String price = intent.getExtras().getString(getString(R.string.price));
        String image = intent.getExtras().getString(getString(R.string.image));
        String rating = intent.getExtras().getString(getString(R.string.rating));
        String reviews =intent.getExtras().getString(getString(R.string.reviews));
        String shortInfo = intent.getExtras().getString(getString(R.string.shortInfo));
        String longInfo = intent.getExtras().getString(getString(R.string.longInfo));

        //get all the views in order to populate them
        TextView viewName = (TextView) findViewById(R.id.productNameD);
        TextView viewPrice = (TextView) findViewById(R.id.priceD);
        ImageView imgView = (ImageView) findViewById(R.id.productImageD);
        TextView viewReviews = (TextView) findViewById(R.id.reviewsD);
        TextView viewAvailable = (TextView) findViewById(R.id.instockD);
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar);
        TextView shortView = (TextView) findViewById(R.id.shortInfoD);
        TextView longView = (TextView) findViewById(R.id.longInfoD);

        //populate the views with the data
        viewName.setText(product);
        viewPrice.setText(price);
        bar.setRating(Float.valueOf(rating));
        Picasso.with(mContext).load(image).into(imgView);
        viewReviews.setText(reviews + " reviews");
        viewAvailable.setText(isInStock(inStock));
        shortView.setText(stripDescription(shortInfo));
        longView.setText(stripDescription(longInfo));
        longView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * helper method to check if product is in stock and then return the correct marker
     * @param bool
     * @return
     */
    public String isInStock(String bool){
        if (bool.equals("true"))
            return "In Stock";
        else
            return "Out of Stock";
    }

    /**
     * strip HTML formatting from the short/long descriptions
     * @param html
     * @return
     */
    public String stripDescription(String html){
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else{
            return Html.fromHtml(html).toString();
        }
    }
}
