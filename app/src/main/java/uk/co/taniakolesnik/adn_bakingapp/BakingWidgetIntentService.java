package uk.co.taniakolesnik.adn_bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import uk.co.taniakolesnik.adn_bakingapp.Utils.BakingWidgetProvider;

/**
 * Created by tetianakolesnik on 03/09/2018.
 */

public class BakingWidgetIntentService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET_RECIPE = "uk.co.taniakolesnik.adn_bakingapp.Utils.action.update_widget";

    public BakingWidgetIntentService() {
        super(BakingWidgetIntentService.class.getSimpleName());
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, BakingWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_WIDGET_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET_RECIPE.equals(action)){
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_title_textView);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listView);
                BakingWidgetProvider.updateWidgets(this, appWidgetManager ,appWidgetIds);
            }
        }
    }

}
