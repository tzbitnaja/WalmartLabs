package tzbitnaja.walmartlabs;

import java.util.ArrayList;

import tzbitnaja.model.Product;

/**
 * Short interface for a response listener in order to be able to use lazy loading
 * @author tzbitnaja
 * */

public interface ResponseListener<T> {
    public void onResponse(ArrayList<Product> responseItems);
}
