package uk.co.taniakolesnik.adn_bakingapp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uk.co.taniakolesnik.adn_bakingapp.IngredientsListFragment;
import uk.co.taniakolesnik.adn_bakingapp.InstructionsListFragment;
import uk.co.taniakolesnik.adn_bakingapp.R;

/**
 * Created by tetianakolesnik on 09/09/2018.
 */

public class DetailsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] tabs;
    private static final int TABS_NUMBER = 2;
    private static final int POSITION_ZERO = 0;
    private static final int POSITION_ONE = 1;

    public DetailsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        tabs = new String[] {context.getResources().getString(R.string.instructions_tab_name),
                mContext.getResources().getString(R.string.ingredients_tab_name)} ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_ZERO:
                return new InstructionsListFragment();
            case POSITION_ONE:
                return new IngredientsListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
