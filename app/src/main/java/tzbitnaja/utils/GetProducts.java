package tzbitnaja.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tzbitnaja.model.*;
import tzbitnaja.utils.*;


import static android.content.ContentValues.TAG;

/**
 * GetProducts is the class that is responsible for constructing the connection that is used to make calls to the API
 * It constructs a URL, opens the connection, and then extracts data from the input stream
 * In addition to that, getProducts populates the Page and Product objects with the extracted values.
 * @author tzbitnaja
 */

public class GetProducts {
    private static final String API_KEY = "c86bbded-3988-463f-94a5-6443ed7cec34";
    private static final String WALMART_LABS_TEST_API = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts/" + API_KEY + "/%s/%s";

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";

    private String pageNum;
    private String pageSize;

    Page page = new Page();

    //constructor to enforce default values (irrelevant right now,
    // but could come in handy if show X items per page functionality gets added
    public GetProducts(){
        pageNum = DEFAULT_PAGE_NUMBER;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * method that is responsible for opening the connection and then passing the input stream to the method convertJsonToString
     * @param page current page offset
     * @param size number of items per page
     * @return data gets return in a form of a string
     */
    public String getJSON(String page, String size){
        pageNum = page;
        pageSize = size;
        String data = null;

        try{
            //create url
            URL url = new URL(String.format(WALMART_LABS_TEST_API, pageNum, pageSize));
            //open connection using the constructed url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //get hte input stream as a response to an API query
            InputStream is = new BufferedInputStream(connection.getInputStream());

            //convert json to string
            data = convertJsonToString(is);
            return data;
        } catch (MalformedURLException e) {
            Log.e(TAG,"Exception: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG,"Exception: " + e.getMessage());
        }
        return "0";
    }

    /**
     * helper method that parses json into a string
     * reads buffer line by line and constructs a string based on JSON tags/values
     * @param is inputstream that constains all the data
     * @return parsed String is returned
     */
    private String convertJsonToString(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG,"Exception: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG,"Exception: " + e.getMessage());
            }
        }
        return sb.toString();

    }

    /**
     * helper method that Parses the json string into a model/object representaion.
     * @param jsonStr the json string
     * @return returns a Page object that has all the info contained in the original JSON but organized into a data structure
     */
    public Page parsePage(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            //get the json array that respresents the product list
            JSONArray productList = jsonObj.getJSONArray("products");
            //one by one loop through each element of the array and extract json fields that would construct the Product object
            for(int i=0; i<productList.length(); i++){
                JSONObject temp = productList.getJSONObject(i);
                page.addProduct(new Product(getTag(temp, "productId"),
                                            getTag(temp, "productName"),
                                            getTag(temp, "shortDescription"),
                                            getTag(temp, "longDescription"),
                                            getTag(temp, "price"),
                                            getTag(temp, "productImage"),
                                            getTag(temp, "reviewRating"),
                                            getTag(temp, "reviewCount"),
                                            getTag(temp, "inStock")));
            }

            //the following fields are within the page object, but do not belong to Product
            page.setTotal(getTag(jsonObj, "totalProducts"));
            page.setPageNum(getTag(jsonObj, "pageNumber"));
            page.setPageSize(getTag(jsonObj, "pageSize"));
            page.setStatus(getTag(jsonObj, "status") );

        } catch(JSONException e){
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return page;
    }

    /**
     * helper method that checks that given json tag exists in order to avoid the JSONException
     * the data has several tags missing in certain JSON objects,
     * so it's important to make sure that no Product/Page fields are null and that the JSON exception doesn't crash the app
     * @param jsonObj the json object that we're trying to extract from
     * @param tag the tag to search within the json
     * @return requested json value if it exists, or return an empty string if it doesn't
     */
    private String getTag(JSONObject jsonObj, String tag){
        if(jsonObj.has(tag)) {
            try {
                return jsonObj.getString(tag);
            } catch (JSONException e) {
                Log.e(TAG,"Exception: " + e.getMessage());
            }
        }
            return "";
    }
}

