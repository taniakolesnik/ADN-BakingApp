package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import uk.co.taniakolesnik.adn_bakingapp.MainActivity;
import uk.co.taniakolesnik.adn_bakingapp.R;
import uk.co.taniakolesnik.adn_bakingapp.TinyDB;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG = BakingWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent startAppIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_title_textView, startAppIntent);

        views.setImageViewResource(R.id.widget_imageView, R.drawable.ic_baking);
        views.setTextViewText(R.id.widget_title_textView, getRecipeNameFromTinyDB(context));

        Intent listViewIntent = new Intent(context, WidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_listView, listViewIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String getRecipeNameFromTinyDB(Context context) {
        Log.i(TAG, "getRecipeNameFromTinyDB");
        TinyDB tinydb = new TinyDB(context);
        String recipeName = tinydb.getString(context.getString(R.string.widget_recipe_name_for_widget_key));
        Log.i(TAG, "getRecipeNameFromTinyDB recipeName " + recipeName);
        return recipeName;

    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        //TODO remove widget preference
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

