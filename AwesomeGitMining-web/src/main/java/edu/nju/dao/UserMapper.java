package edu.nju.dao;

import edu.nju.model.EmailSta;
import edu.nju.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface UserMapper {
//    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertOwn(String login,String full_name);

//    User selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKey(User record);

    List<User> selectAll();

    User selectByLogin(String login);

    List<User> selectAllWithPager(Map<String,Object> params);

    List<User> searchUser(Map<String,Object> params);

    //int countAll();

    int countSearch(String name);

    List<LinkedHashMap> countFirst30Companys();

    List<LinkedHashMap> countTypes();

    List<LinkedHashMap> countCreate_Year();

    List<User> selectUserByLanguage(Map<String,Object> params);

    List<User> selectUserByCompany(Map<String,Object> params);

    List<User> selectLan_Com(String language, String company, int pageSize, int pageOffset);

    int countLanguage(String language);

    int countCompany(String company);

    int countLan_Com(String language,String company);


    List<String> selectContriRepo(String login);

    List<String> selectCollaRepo(String login);

    List<String> selectOwn(String login);

    List<String> selectEmail();

    List<LinkedHashMap> countEmail();

    int countBlog(String blog);

    List<Integer>countFollowers();

    List<Integer>countFollowings();

    int countFans(int first,int last);

    int countFollow(int first,int last);

    List<String> getUserLocation();

    List<String> selectLanguages();

    List<String> selectLanguageByUser(String login);

}