package com.m520it.neteasynews.beans;

import java.util.List;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/14  0:17
 * @desc ${TODD}
 */
public class T1348647909107Bean {
    private String img;
    private String imgsrc;
    private String recSource;
    private int replyCount;
    private String title;
    private String source;
    private String specialID;
    private String id;
    private String docid;
    /**
     * imgsrc : http://cms-bucket.nosdn.127.net/5a011863097a4d1bb80c82eeb602b58b20160813095836.jpeg
     * subtitle :
     * tag : photoset
     * title : 一周精选：漫展现葛优"北京瘫"
     * url : 19BR0001|2191153
     */
    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    public String getSpecialID() {
        return specialID;
    }


    private List<HotAds> ads;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getRecSource() {
        return recSource;
    }

    public void setRecSource(String recSource) {
        this.recSource = recSource;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HotAds> getAds() {
        return ads;
    }

    public void setAds(List<HotAds> ads) {
        this.ads = ads;
    }


}
