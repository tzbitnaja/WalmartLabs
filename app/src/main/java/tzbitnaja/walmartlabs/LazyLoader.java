package tzbitnaja.walmartlabs;

import android.widget.AbsListView;

/**
 * lazy loader class that allows us to load more items as the user scrolls down the list
 * @author tzbitnaja
 */

public abstract class LazyLoader implements AbsListView.OnScrollListener{
    //set lazy loading to true by default, means that it's still waiting for data to load
    private boolean loading = true;
    //total number of items on the list
    private int previousTotal = 0;
    //min number of items below current position, before you can load more
    private int threshold = 5;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /**
     * the method is trying to determine wheter the user has scrolled past the first item on the list
     * @param view
     * @param firstVisibleItem first item that is currently visible
     * @param visibleItemCount
     * @param totalItemCount total count of the items on the list
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        //if currently loading, see if data set has changed
        //if it has, stop loading and update the current total item count
        if(loading){
            if(totalItemCount > previousTotal){
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        //load more items if currently not loading. check to see if threshold has been passed.
        //call loadMore if more needs to be loaded
        if (!loading && ((firstVisibleItem + visibleItemCount) >= (totalItemCount - threshold))){
            loading = true;

            loadMore(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    //abstract loadMore defines the loading process
    public abstract void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
