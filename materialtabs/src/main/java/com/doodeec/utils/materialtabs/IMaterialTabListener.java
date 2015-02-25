package com.doodeec.utils.materialtabs;

/**
 * Listener for material tab selection
 */
public interface IMaterialTabListener {
    /**
     * Fired when tab is selected
     *
     * @param tab selected tab
     */
    public void onTabSelected(MaterialTab tab);

    /**
     * Fired when tab is reselected
     *
     * @param tab reselected tab
     */
    public void onTabReselected(MaterialTab tab);

    /**
     * Fired when tab is unselected
     *
     * @param tab unselected tab
     */
    public void onTabUnselected(MaterialTab tab);
}
