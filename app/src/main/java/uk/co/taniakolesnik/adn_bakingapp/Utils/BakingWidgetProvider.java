package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import uk.co.taniakolesnik.adn_bakingapp.MainActivity;
import uk.co.taniakolesnik.adn_bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG = BakingWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        views.setImageViewResource(R.id.widget_imageView, R.drawable.ic_baking);
        views.setTextViewText(R.id.widget_title_textView, getRecipeNameFromSharedPreferences(context));
        views.setOnClickPendingIntent(R.layout.baking_widget_provider, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String getRecipeNameFromSharedPreferences(Context context) {
        Log.i(TAG, "WIDGET_UEEE getRecipeNameFromSharedPreferences started");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeName = sharedPreferences.getString(context.getString(R.string.widget_recipe_shared_pref),
                context.getString(R.string.widget_recipe_name_default));
        Log.i(TAG, "WIDGET_UEEE getRecipeNameFromSharedPreferences recipeName" + recipeName);
        return recipeName;

    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "WIDGET_UEEE updateWidgets started");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Log.i(TAG, "WIDGET_UEEE updateWidgets started " + appWidgetId);
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

