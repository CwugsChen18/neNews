package com.m520it.neteasynews.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  18:41
 * @desc ${TODD}
 */
public class Ads implements Serializable {

    /**
     * result : 0
     * ads : [{"monitorReplaceDeviceId":0,"id":"51632","ratio":"20.0","show_num":3,"sub_title":"","main_title":"","vdeg":"","category":"STARTUP","thirdplat":0,"location":"1","action":"1","ad_type":0,"is_dsp_backup":0,"monitor":"","ad_loc":6,"action_params":{"link_url":"http://clickc.admaster.com.cn/c/a72763,b1227591,c369,i0,m101,h"},"monitorShowUrl":"","video_url":"","vdet":"","res_url":["http://img1.126.net/channel6/2016/024419/a1.jpg","","",""],"expired_time":1471017599000,"flight_id":"122","content":"","style":"2","is_sens":0,"show_time":"2.5","vdog":"","vdot":"","monitorClickUrl":""},{"category":"STARTUP","style":"13","res_url":["http://img1.126.net/channel6/2016/024273/1.jpg","","",""],"thirdplat":0,"action":"1","is_dsp_backup":0,"monitorShowUrl":"","video_height":"1.44","video_width":"1.0","content":"","video_point_y":"0.5","monitorReplaceDeviceId":0,"id":"51571","ratio":"20.0","show_num":3,"sub_title":"","main_title":"1","vdeg":"","detail_title":"","location":"1","vdet":"","ad_type":0,"video_url":"http://img6.126.net/channel6/024273.mp4","monitor":"","ad_loc":6,"usr_protect_time":"","detail_link":"","expired_time":1471017599000,"flight_id":"122","action_params":{"link_url":"http://dl.cocounion.com/weishangdian/activity/cr627/index05.html"},"video_point_x":"0.5","is_sens":0,"vdog":"","show_time":"5.0","vdot":"","monitorClickUrl":""},{"monitorReplaceDeviceId":0,"id":"51617","ratio":"20.0","show_num":3,"sub_title":"","main_title":"","vdeg":"","category":"STARTUP","thirdplat":0,"location":"1","action":"1","ad_type":0,"is_dsp_backup":0,"monitor":"","ad_loc":6,"action_params":{"link_url":"http://brand.wljhealth.com/activity/2016summer_v2/intro/wap/"},"monitorShowUrl":"","video_url":"","vdet":"","res_url":["http://img1.126.net/channel6/2016/023710/1.jpg","","",""],"expired_time":1471017599000,"flight_id":"122","content":"","style":"2","is_sens":0,"show_time":"2.5","vdog":"","vdot":"","monitorClickUrl":""},{"monitorReplaceDeviceId":0,"id":"51528","ratio":"20.0","show_num":3,"sub_title":"","main_title":"","vdeg":"","action_params":{},"thirdplat":0,"location":"1","action":"","ad_type":0,"is_dsp_backup":0,"monitor":"","ad_loc":6,"category":"STARTUP","monitorShowUrl":"","video_url":"","vdet":"","res_url":["http://img1.126.net/channel6/2016/024041/1.jpg","","",""],"expired_time":1471017599000,"flight_id":"122","content":"","style":"2","is_sens":0,"show_time":"2.5","vdog":"","vdot":"","monitorClickUrl":""},{"category":"STARTUP","style":"13","res_url":["http://img1.126.net/channel6/2016/024347/1a.jpg","","",""],"thirdplat":0,"action":"1","is_dsp_backup":0,"monitorShowUrl":"","video_height":"1.44","video_width":"1.0","content":"","video_point_y":"0.5","monitorReplaceDeviceId":0,"id":"51634","ratio":"20.0","show_num":3,"sub_title":"","main_title":"1","vdeg":"","detail_title":"","location":"1","vdet":"","ad_type":0,"video_url":"http://img6.126.net/channel6/024347.mp4","monitor":"","ad_loc":6,"usr_protect_time":"","detail_link":"","expired_time":1471017599000,"flight_id":"122","action_params":{"link_url":"http://e.cn.miaozhen.com/r/k=2026370&p=71Wfr&dx=__IPDX__&rt=2&ns=__IP__&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&o=http://weibo.com/p/100808d5841ade0b507ac386214662118c1ec9?k=%E5%AE%89%E6%85%95%E5%B8%8C%E5%A5%A5%E8%BF%90%E6%89%93%E6%B0%94%E5%A4%A9%E5%9B%A2&from=526&_from_=huati_topic"},"video_point_x":"0.5","is_sens":0,"vdog":"","show_time":"5.0","vdot":"","monitorClickUrl":""}]
     * next_req : 600
     * error :
     */

    private int result;
    private int next_req;
    private String error;
    /**
     * monitorReplaceDeviceId : 0
     * id : 51632
     * ratio : 20.0
     * show_num : 3
     * sub_title :
     * main_title :
     * vdeg :
     * category : STARTUP
     * thirdplat : 0
     * location : 1
     * action : 1
     * ad_type : 0
     * is_dsp_backup : 0
     * monitor :
     * ad_loc : 6
     * action_params : {"link_url":"http://clickc.admaster.com.cn/c/a72763,b1227591,c369,i0,m101,h"}
     * monitorShowUrl :
     * video_url :
     * vdet :
     * res_url : ["http://img1.126.net/channel6/2016/024419/a1.jpg","","",""]
     * expired_time : 1471017599000
     * flight_id : 122
     * content :
     * style : 2
     * is_sens : 0
     * show_time : 2.5
     * vdog :
     * vdot :
     * monitorClickUrl :
     */

    private List<AdsBean> ads;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }
}
