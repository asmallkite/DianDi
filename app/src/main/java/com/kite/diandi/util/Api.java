package com.kite.diandi.util;

/**
 * Created by 10648 on 2017/3/16 0016.
 */

public class Api {
    // 豆瓣一刻
    // 根据日期查询消息列表
    // eg:https://moment.douban.com/api/stream/date/2016-08-11
    public static final String DOUBAN_MOMENT = "https://moment.douban.com/api/stream/date/";

    // 获取文章具体内容
    // eg:https://moment.douban.com/api/post/100484
    public static final String DOUBAN_ARTICLE_DETAIL = "https://moment.douban.com/api/post/";

    // 获取果壳精选的文章列表,通过组合相应的参数成为完整的url
    // Guokr handpick articles. make complete url by combining params
    public static final String GUOKR_ARTICLES = "http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=25&ad=1";

    // 获取果壳文章的具体信息 V1
    // specific information of guokr post V1
    public static final String GUOKR_ARTICLE_LINK_V1 = "http://jingxuan.guokr.com/pick/";

    // 消息内容获取与离线下载
    // content of post and download offline
    // 在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容
    // add the id that you got from latest post to ZHIHU_NEWS and you will get the content as json format
    public static final String ZHIHU_NEWS = "http://news-at.zhihu.com/api/4/news/";

    // 过往消息
    // past posts
    // 若要查询的11月18日的消息，before后面的数字应该为20161118
    // if you want to select the posts of November 11th, the number after 'before' should be 20161118
    // 知乎日报的生日为2013 年 5 月 19 日，如果before后面的数字小于20130520，那么只能获取到空消息
    // the birthday of ZhiHuDaily is May 19th, 2013. So if the number is lower than 20130520, you will get a null value of post
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";

}
