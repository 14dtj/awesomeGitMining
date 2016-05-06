package edu.nju.dao;

import edu.nju.model.Repository;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface RepositoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Repository record);

    int insertSelective(Repository record);

    Repository selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Repository record);

    int updateByPrimaryKeyWithBLOBs(Repository record);

    int updateByPrimaryKey(Repository record);

    List<Repository> selectAll();

    Repository selectByFullName(String full_name);

    List<Repository> searchRepository(Map<String,Object> map);

    List<Repository> selectReposSortedByFork(Map<String,Object> map);

    List<Repository> selectReposSortedByStar(Map<String,Object> map);

    List<Repository> selectReposSortedByContribute(Map<String,Object> map);

    List<Repository> selectAllPaged(Map<String,Object> map);

    List<Repository> selectReposByYear(Map<String,Object> map);

    List<Repository> selectReposByLanguage(Map<String,Object> map);

    List<Repository> selectReposByKey(Map<String,Object> map);

    int countAll();

    int countSearch(String fullName);

    int countYear(int year);

    int countLanguage(String language);

    int countKey(String keyword);
}