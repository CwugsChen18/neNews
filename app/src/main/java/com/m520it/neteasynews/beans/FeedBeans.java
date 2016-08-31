package com.m520it.neteasynews.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/17  18:51
 * @desc ${TODD}
 */
public class FeedBeans {
    ArrayList<FeedBack> feedBacks;

    boolean isTtile =false;
    String titleName;

    public boolean isTtile() {
        return isTtile;
    }

    public void setTtile(boolean ttile) {
        isTtile = ttile;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public FeedBeans() {
        this.feedBacks = new ArrayList<>();
    }

    public ArrayList<FeedBack> getFeedBacks() {
        return feedBacks;
    }

    public void addFeedbackDetail(FeedBack feedBack){
        feedBacks.add(feedBack);
    }


    public void setFeedBacks(ArrayList<FeedBack> feedBacks) {
        this.feedBacks = feedBacks;
    }

    public void sort(){
        FeedBackSort sort = new FeedBackSort();
        Collections.sort(feedBacks,sort);
    }

    @Override
    public String toString() {
        return "FeedBacks{" +
                "feedBacks=" + feedBacks +
                '}';
    }

    class FeedBackSort implements Comparator<FeedBack> {

        @Override
        public int compare(FeedBack lhs, FeedBack rhs) {
            if(lhs.getIndex()>rhs.getIndex()){
                //大于
                return 1;
            }else if(lhs.getIndex()<rhs.getIndex()){
                //小于
                return -1;
            }
            //相等
            return 0;
        }
    }
}
