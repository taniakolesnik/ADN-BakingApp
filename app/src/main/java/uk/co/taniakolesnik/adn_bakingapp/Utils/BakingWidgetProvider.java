package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import uk.co.taniakolesnik.adn_bakingapp.MainActivity;
import uk.co.taniakolesnik.adn_bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent startAppIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_title_textView, startAppIntent);

        CharSequence titleText = getRecipeNameFromTinyDB(context);
        if (titleText == "" || titleText == null) {
            titleText = context.getString(R.string.not_recent_recipes_text_message);
        }

        views.setImageViewResource(R.id.widget_imageView, R.drawable.ic_baking);
        views.setTextViewText(R.id.widget_title_textView, titleText);

        Intent listViewIntent = new Intent(context, WidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_listView, listViewIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String getRecipeNameFromTinyDB(Context context) {
        TinyDB tinydb = new TinyDB(context);
        String recipeName = tinydb.getString(context.getString(R.string.widget_recipe_name_for_widget_key));
        return recipeName;

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
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

