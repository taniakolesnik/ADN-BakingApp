package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by tetianakolesnik on 03/09/2018.
 */

public class AppWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    public AppWidgetViewsFactory(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
