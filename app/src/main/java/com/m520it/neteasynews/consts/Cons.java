package com.m520it.neteasynews.consts;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  15:16
 * @desc ${TODD}
 */
public class Cons {
    /**
     * 广告界面url
     */
    public static final String SPLASH_URL="http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1&timestamp=1462779408364&uid=OaBKRDb%2B9FBz%2FXnwAuMBWF38KIbARZdnRLDJ6Kkt9ZMAI3VEJ0RIR9SBSPvaUWjrFtfw1N%2BgxquT0B2pjMN5zsxz13RwOIZQqXxgjCY8cfS8XlZuu2bJj%2FoHqOuDmccGyNEtV%2FX%2FnBofofdcXyudJDmBnAUeMBtnIzHPha2wl%2FQnUPI4%2FNuAdXkYqX028puyLDhnigFtrX1oiC2F7UUuWhDLo0%2BE0gUyeyslVNqLqJCLQ0VeayQa%2BgbsGetk8JHQ";


    public static final String AD_RESPOSE_URL = "respose_url";
    public static final String AD_LAST_GET = "last_time";
    public static final String AD_TIME_OUT = "time_out";
    public static final String AD_LAST_INDEX = "last_index";

    /**
     * 头条页面
     */
    public static final String HOT_URL =
            "http://c.m.163.com/nc/article/headline/T1348647909107/$S-$E.html?from=toutiao&size=20&passport=&devId=bMo6EQztO2ZGFBurrbgcMQ%3D%3D&lat=YO6p1koFB04ckeiATuYaGw%3D%3D&lon=SQIt%2FB7%2BSqySYsiVHI%2FDiQ%3D%3D&version=7.0&net=wifi&ts=1463198253&sign=VHsiElahM1HTWFL0pnd52EoxE3w9piu1mp9jiCwGatd48ErR02zJ6%2FKXOnxX046I&encryption=1&canal=goapk_news";

    //获取刷新数据
    public static String getHotUrl(int start, int end) {
        String url = HOT_URL.replace("$S", start + "");
        return url.replace("$E", end+"");
    }

    //详情页面
    public static final String DETAIL_URL =  "http://c.m.163.com/nc/article/%S/full.html";

    public static String getDetailUrl(String docid) {
        return DETAIL_URL.replace("%S", docid);
    }

    //评论页
    public static String FEED_BACK_URL = "http://comment.api.163.com/api/json/post/list/new/hot/news3_bbs/%S/0/10/10/2/2";
    public static String getFeedBackUrl(String docid) {
        return FEED_BACK_URL.replaceFirst("%S", docid);
    }

}
