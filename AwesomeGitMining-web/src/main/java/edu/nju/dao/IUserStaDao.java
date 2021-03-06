package edu.nju.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dora on 2016/5/13.
 */
public interface IUserStaDao {

    /**
     * 前30大公司及它们的人数
     * @return
     */
    List<LinkedHashMap> countFirst30Companys();

    /**
     * 每种类型的用户的人数
     * @return
     */
    List<LinkedHashMap> countTypes();

    /**
     * 每种类型的用户的人数
     * @return
     */
    List<LinkedHashMap> countCreate_Year();

    /**
     * 统计邮件情况
     * @return
     */
    List<LinkedHashMap> countEmail();
    /**
     * 统计博客情况。顺序按照写的顺序。顺序："twitter","github","blogspot","linkedin","wordpress","about","google","tumblr","hatenablog","koverflow"
     * @return
     */
    List<Integer> countBlog();
    /**
     * 统计粉丝数 {0,10,20,30,40,50,60,70,80,90,100,18727};
     * @return
     */
    List<Integer>countFollowers();
    /**
     * 统计关注数 {0,10,20,30,40,50,60,70,80,90,100,114999}
     * @return
     */
    List<Integer>countFollowings();

    /**
     * 得到user的位置国家分布情况
     * @return
     */
    List<LinkedHashMap> getUserLocationDistribute();

    int[][] getLanguageRelation();
}
