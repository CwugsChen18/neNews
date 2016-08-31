package com.m520it.neteasynews.beans;

import java.io.Serializable;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/16  20:55
 * @desc ${TODD}
 */
public class DocImage implements Serializable {
    /**
     * ref : <!--IMG#0-->
     * pixel : 410*315
     * alt : 王宝强离婚案法院已受理
     * src : http://img3.cache.netease.com/photo/0003/2016-08-15/BUGNLB3T00AJ0003.png
     * photosetID : 0003|607405
     */

    private String ref;
    private String pixel;
    private String alt;
    private String src;
    private String photosetID;


    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getRef() {
        return ref;
    }

    public String getPixel() {
        return pixel;
    }

    public String getAlt() {
        return alt;
    }

    public String getSrc() {
        return src;
    }

    public String getPhotosetID() {
        return photosetID;
    }
}
