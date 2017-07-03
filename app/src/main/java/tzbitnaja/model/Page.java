package tzbitnaja.model;

import java.util.ArrayList;

/**
 * Part of the data model, the page is what stores an array list of all products within that page,
 * and other misc data that belongs to the returned JSON object
 * @author tzbitnaja
 */

public class Page {
    //arraylist for products
    ArrayList<Product> products = new ArrayList<>();
    int total;
    int pageNum;
    int pageSize;
    String status;

    public Page(){};

    //construct a page
    public Page(ArrayList<Product> p, int t, int pageN, int pageS, String s){
        products = p;
        total = t;
        pageNum = pageN;
        pageSize = pageS;
        status = s;
    }

    //setters
    protected void setProducts(ArrayList<Product> p){
        products = p;
    }
    public void addProduct(Product item){
        products.add(item);
    }
    public void setTotal(String t){
        total = Integer.parseInt(t);
    }
    public void setPageNum(String pageN){
        pageNum = Integer.parseInt(pageN);
    }
    public void setPageSize(String pageS){
        pageSize = Integer.parseInt(pageS);
    }
    public void setStatus(String s){
        status = s;
    }

    //getters
    public ArrayList<Product> getProducts(){
        return products;
    }
    public int getTotal(){
        return total;
    }
    public int getPageNum(){
        return pageNum;
    }
    public int getPageSize(){
        return pageSize;
    }
    public String getStatus(){
        return status;
    }
}

