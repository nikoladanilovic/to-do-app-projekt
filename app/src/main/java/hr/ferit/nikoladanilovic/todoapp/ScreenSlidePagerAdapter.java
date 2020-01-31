package hr.ferit.nikoladanilovic.todoapp;

//import android.app.FragmentManager;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Locale;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 4;


    private static final String[] baseName = new String[4];
    private void populateStringArray(){
        baseName[0] = "TO-DO";
        baseName[1] = "NEW";
        baseName[2] = "OLD";
        baseName[3] = "FUTURE";
    }


    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return ListOfToDoFragment.newInstance("todo");

        case 1:
            return InputToDoFragment.newInstance(" ");

        case 2:
            return ListOfToDoFragment.newInstance("old");

        default:
            return ListOfToDoFragment.newInstance("future");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        populateStringArray();
        return String.format(Locale.getDefault(), baseName[position], position + 1);
    }


    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
