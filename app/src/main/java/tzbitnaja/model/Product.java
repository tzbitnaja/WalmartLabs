package tzbitnaja.model;

/**
 * Part of the data model, the product is what stores product information obtained from JSON
 * products can be aggregated on a page
 * @author tzbitnaja
 */


public class Product {
    String productID;
    String productName;
    String shortDescription;
    String longDescription;
    String price;
    String productImage;
    String reviewRating;
    String reviewCount;
    String inStock;

    public Product(String id, String name, String shortInfo, String longInfo, String cost, String image, String rating, String reviews, String available){
        productID = id;
        productName = name;
        shortDescription = shortInfo;
        longDescription = longInfo;
        price = cost;
        productImage = image;
        reviewRating = rating;
        reviewCount = reviews;
        inStock = available;
    }

    //setters
    protected void setProductID(String id){
        productID = id;
    }
    protected void setProductName(String name){
        productName = name;
    }
    protected void setShortDescription(String shortInfo){
        shortDescription = shortInfo;
    }
    protected void setLongDescription(String longInfo){
        longDescription = longInfo;
    }
    protected void setPrice(String cost){
        price = cost;
    }
    protected void setProductImage(String image){
        productImage = image;
    }
    protected void setReviewRating(String rating){
        reviewRating = rating;
    }
    protected void setReviewCount(String reviews){
        reviewCount = reviews;
    }
    protected void setInStock(String available){
        inStock = available;
    }


    //getters
    public String getProductID(){
        return productID;
    }
    public String getProductName(){
        return productName;
    }
    public String getShortDescription(){
        return shortDescription;
    }
    public String getLongDescription(){
        return longDescription;
    }
    public String getPrice(){
        return price;
    }
    public String getProductImage(){
        return productImage;
    }
    public String getReviewRating(){
        return reviewRating;
    }
    public String getReviewCount(){
        return reviewCount;
    }
    public String isInStock(){
        return inStock;
    }
}
