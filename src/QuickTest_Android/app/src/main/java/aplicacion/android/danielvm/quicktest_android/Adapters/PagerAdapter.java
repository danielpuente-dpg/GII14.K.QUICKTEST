package aplicacion.android.danielvm.quicktest_android.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import aplicacion.android.danielvm.quicktest_android.Fragments.SecondFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.CuestionarioFragment;
import aplicacion.android.danielvm.quicktest_android.Fragments.ThirdFragment;

/**
 * Created by Daniel on 23/03/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CuestionarioFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
