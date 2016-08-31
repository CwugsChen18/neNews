package com.m520it.neteasynews.service;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/19  0:23
 * @desc ${TODD}
 */
public class ShowTabEvent {
    private boolean isShowTab;

    public ShowTabEvent(boolean isShowTab) {
        this.isShowTab = isShowTab;
    }

    public boolean isShowTab() {
        return isShowTab;
    }

    public void setShowTab(boolean showTab) {
        isShowTab = showTab;
    }
}
