package io.eldon.representapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eldon on 3/5/2016.
 */
public class CongressionalDataAdapter extends FragmentGridPagerAdapter {
    private final Context mContext;
    ArrayList<PageData> mPageData = new ArrayList<PageData>();

    private class PageData implements Serializable {
        public String mHeader;
        public String mBody;
        public String mColor;
        protected Object mReferenceObject;

        public PageData(String mHeader, String mFooter, String mColor, Object mReferenceObject) {
            this.mHeader = mHeader;
            this.mBody = mFooter;
            this.mColor = mColor;
            this.mReferenceObject = mReferenceObject;
        }
    }

    public CongressionalDataAdapter(final Context ctx, FragmentManager fm, ArrayList<SimpleCongressPerson> mCongressPeople, String mCountyState, String mObamaVote, String mRomneyVote) {
        super(fm);
        this.mContext = ctx;

        for (SimpleCongressPerson cp : mCongressPeople) {
            mPageData.add(new PageData(cp.getName(), cp.getParty(), cp.getPartyColor(), cp));
        }

        mPageData.add(new PageData(mCountyState, "2012 Presidential Vote", "#FAFAFA", null));  // lol
        if (Float.parseFloat(mObamaVote) >= Float.parseFloat(mRomneyVote)) {
            mPageData.add(new PageData("Obama: " + mObamaVote + "\nRomney: " + mRomneyVote, "", "#02bfe7", null));  // lol
        } else {
            mPageData.add(new PageData("Obama: " + mObamaVote + "\nRomney: " + mRomneyVote, "", "#e31c3d", null));  // lol
        }
    }

    // Override methods in FragmentGridPagerAdapter

    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        CardFragment fragment = CardFragment.create(mPageData.get(col).mHeader, mPageData.get(col).mBody);
        return fragment;
    }

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return 1;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return mPageData.size();
    }
}