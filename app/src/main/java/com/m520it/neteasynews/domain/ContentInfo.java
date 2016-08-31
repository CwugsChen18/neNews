package com.m520it.neteasynews.domain;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/13  21:09
 * @desc ${TODD}
 */
public class ContentInfo {
    //顶部的标题
    public String title;
    //顶部标题对应的类
    public Class aClass;

    public ContentInfo(String title, Class aClass) {
        this.title = title;
        this.aClass = aClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
