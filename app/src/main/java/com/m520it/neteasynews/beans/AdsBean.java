package com.m520it.neteasynews.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  18:44
 * @desc ${TODD}
 */
public class AdsBean implements Serializable {
        private int monitorReplaceDeviceId;
        private String id;
        private String ratio;
        private int show_num;
        private String sub_title;
        private String main_title;
        private String vdeg;
        private String category;
        private int thirdplat;
        private String location;
        private String action;
        private int ad_type;
        private int is_dsp_backup;
        private String monitor;
        private int ad_loc;
        /**
         * link_url : http://clickc.admaster.com.cn/c/a72763,b1227591,c369,i0,m101,h
         */

        private ActionParamsBean action_params;
        private String monitorShowUrl;
        private String video_url;
        private String vdet;
        private long expired_time;
        private String flight_id;
        private String content;
        private String style;
        private int is_sens;
        private String show_time;
        private String vdog;
        private String vdot;
        private String monitorClickUrl;
        private List<String> res_url;

        public int getMonitorReplaceDeviceId() {
            return monitorReplaceDeviceId;
        }

        public void setMonitorReplaceDeviceId(int monitorReplaceDeviceId) {
            this.monitorReplaceDeviceId = monitorReplaceDeviceId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public int getShow_num() {
            return show_num;
        }

        public void setShow_num(int show_num) {
            this.show_num = show_num;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getMain_title() {
            return main_title;
        }

        public void setMain_title(String main_title) {
            this.main_title = main_title;
        }

        public String getVdeg() {
            return vdeg;
        }

        public void setVdeg(String vdeg) {
            this.vdeg = vdeg;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getThirdplat() {
            return thirdplat;
        }

        public void setThirdplat(int thirdplat) {
            this.thirdplat = thirdplat;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getAd_type() {
            return ad_type;
        }

        public void setAd_type(int ad_type) {
            this.ad_type = ad_type;
        }

        public int getIs_dsp_backup() {
            return is_dsp_backup;
        }

        public void setIs_dsp_backup(int is_dsp_backup) {
            this.is_dsp_backup = is_dsp_backup;
        }

        public String getMonitor() {
            return monitor;
        }

        public void setMonitor(String monitor) {
            this.monitor = monitor;
        }

        public int getAd_loc() {
            return ad_loc;
        }

        public void setAd_loc(int ad_loc) {
            this.ad_loc = ad_loc;
        }

        public ActionParamsBean getAction_params() {
            return action_params;
        }

        public void setAction_params(ActionParamsBean action_params) {
            this.action_params = action_params;
        }

        public String getMonitorShowUrl() {
            return monitorShowUrl;
        }

        public void setMonitorShowUrl(String monitorShowUrl) {
            this.monitorShowUrl = monitorShowUrl;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVdet() {
            return vdet;
        }

        public void setVdet(String vdet) {
            this.vdet = vdet;
        }

        public long getExpired_time() {
            return expired_time;
        }

        public void setExpired_time(long expired_time) {
            this.expired_time = expired_time;
        }

        public String getFlight_id() {
            return flight_id;
        }

        public void setFlight_id(String flight_id) {
            this.flight_id = flight_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public int getIs_sens() {
            return is_sens;
        }

        public void setIs_sens(int is_sens) {
            this.is_sens = is_sens;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public String getVdog() {
            return vdog;
        }

        public void setVdog(String vdog) {
            this.vdog = vdog;
        }

        public String getVdot() {
            return vdot;
        }

        public void setVdot(String vdot) {
            this.vdot = vdot;
        }

        public String getMonitorClickUrl() {
            return monitorClickUrl;
        }

        public void setMonitorClickUrl(String monitorClickUrl) {
            this.monitorClickUrl = monitorClickUrl;
        }

        public List<String> getRes_url() {
            return res_url;
        }

        public void setRes_url(List<String> res_url) {
            this.res_url = res_url;
        }
    }
