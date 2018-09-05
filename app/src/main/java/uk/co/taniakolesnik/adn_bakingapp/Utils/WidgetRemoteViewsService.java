package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import uk.co.taniakolesnik.adn_bakingapp.R;

/**
 * Created by tetianakolesnik on 04/09/2018.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        Context mContext;
        ArrayList<String> mIngredients;

        public GridRemoteViewsFactory(Context context) {
            mContext = context;
        }

        private void updateWidgetList(){
            TinyDB tinyDB = new TinyDB(mContext);
            mIngredients = tinyDB.getListString(mContext.getString(R.string.widget_ingredients_list_for_widget_key));
        }

        @Override
        public void onCreate() {
            updateWidgetList();
        }

        @Override
        public void onDataSetChanged() {
            updateWidgetList();
        }

        @Override
        public void onDestroy() {
            mIngredients.clear();
        }

        @Override
        public int getCount() {
            return mIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mIngredients == null || mIngredients.size() == 0) {
                return null;
            }
            RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            itemView.setTextViewText(R.id.widget_item, mIngredients.get(position));
            return itemView;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
