package com.m520it.neteasynews.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/16  19:04
 * @desc ${TODD}
 */
public class Artitle implements Serializable {

    /**
     * body : <p>　　8月11日中午12时左右，家住常州武进牛塘镇的66岁唐大妈，携10万现金骑电动三轮车外出，钱用一个篮子装着，放在三轮车脚踏板左侧。沿延政路由东向西往常武路拐弯时，篮子从车上掉下。等她发现异常回去寻找时，篮子和10万现金全没了。</p><p>　　唐大妈告诉民警，3年前她从朋友处借了10万元应急。据唐大妈介绍，自己平时在家做家务，老伴则在厂里做保洁员，这10万元钱是老两口攒了三年的积蓄。</p><p>　　警方第一时间带着唐大妈查看路面监控，不巧丢失篮子的地方正处于道路监控的盲区。8月12日上午10点左右，警方终于在万泽附近一高空探头中看到：事发当天中午11点47分，失主骑行一辆电动三轮车经过常武路延政路时，踏板处一物品掉落。三五分钟后，依稀可见另一名骑三轮车的女子经过将其捡走。</p><p>　　警方沿路视频追踪了一天一夜，终于掌握了捡拾物品女子的基本特征。根据这些特征，警方找到了她的暂住地湖塘镇东庄村委东庄村。46岁的魏女士老家在山东，在常州做一些干货零售和硬纸板回收的小生意。</p><p>　　昨天早上，警方进门询问。魏女士说确实捡到了竹篮，随后她从床底下将竹篮拿出交给民警，并强调钱一分都没动。在派出所里，魏女士看到唐大妈还连连表示歉意，“我真不知道怎么找人还钱，老公又回老家了，害得你着急了这么多天。”唐大妈也很感激，拿出3000元钱塞给魏女士。</p><p>　　通讯员孔方扬子晚报记者毕俊星</p><p>原标题：10万元还债钱已经丢了5天，照样找回来</p>
     * users : []
     * replyCount : 207
     * ydbaike : []
     * source_url : http://epaper.yzwb.net/html_t/2016-08/16/content_302872.htm?div=-1
     * link : []
     * shareLink : http://c.m.163.com/news/a/BUIUL1N600014AED.html?spss=newsapp&spsw=1
     * votes : []
     * img : []
     * digest :
     * topiclist_news : []
     * dkeys : 还债,照样,找回
     * ec : 何雨芳_NN5632
     * topiclist : []
     * docid : BUIUL1N600014AED
     * picnews : true
     * title : 大妈用篮子装10万元还债钱 外出弄丢5天后找回
     * tid :
     * template : normal
     * threadVote : 2
     * threadAgainst : 5
     * boboList : []
     * replyBoard : news3_bbs
     * source : 新华报业网-扬子晚报
     * huati : [{"topicId":"SJ4752746377282123570","topicName":"民警"}]
     * hasNext : false
     * voicecomment : off
     * apps : []
     * relative_sys : [{"id":"BUF324IA00011229","title":"天空降下\"钞票雨\" 路人捡钱归还失主","source":"北青网","imgsrc":"http://cms-bucket.nosdn.127.net/bbf674910e0f485894e1f0be9a6f3e2720160814203226.jpeg","docID":"BUF324IA00011229","type":"doc","ptime":"2016-08-14 19:58:29","href":""},{"id":"BUIU5D7V00964LDL","title":"超20万个\u201c电子眼\u201d在沈城 违法的跑不了","source":"沈阳","imgsrc":"http://cms-bucket.nosdn.127.net/aaf94e5b89fe4998a83e565f4d44a23820160816082414.jpeg","docID":"BUIU5D7V00964LDL","type":"doc","ptime":"2016-08-16 06:58:00","href":""},{"id":"BU2CIFU200964J4O","title":"男子盗刷妹妹23万元存款 携夜店情人旅游挥霍","source":"现代快报","imgsrc":"http://img3.cache.netease.com/m/2016/8/9/20160809213428b14e5.jpg","docID":"BU2CIFU200964J4O","type":"doc","ptime":"2016-08-09 21:34:34","href":""}]
     * ptime : 2016-08-16 07:58:16
     */


        private String body;
        private int replyCount;
        private String title;
        private String source;
        private ArrayList<DocImage> img;
        private String ptime;

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public ArrayList<DocImage> getImg() {
            return img;
        }

        public void setImg(ArrayList<DocImage> img) {
            this.img = img;
        }


}
