package edu.nju.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import edu.nju.dao.IRepoDao;
import edu.nju.model.Pager;
import edu.nju.model.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by Dora on 2016/4/30.
 */
@Service("repoService")
public class RepoServiceImpl implements IRepoService {

    @Resource
    private IRepoDao repoDao;


    @Override
    public Pager<Repository> getAllRepos() {
        return repoDao.getAllPaged();
    }

    @Override
    public Repository getRepoByFullname(String fullName) {
        return this.repoDao.getReposByFullName(fullName);
    }

    @Override
    public Pager<Repository> searchRepository(String name) {
        return repoDao.searchRepository(name);
    }

    @Override
    public Pager<Repository> getReposSortedByFork() {
        return repoDao.getReposSortedByFork();
    }

    @Override
    public Pager<Repository> getReposSortedByStar() {
        return repoDao.getReposSortedByStar();
    }

    @Override
    public Pager<Repository> getReposSortedByContribute() {
        return repoDao.getReposSortedByContribute();
    }

    @Override
    public Pager<Repository> getReposByYear(int year) {
        return repoDao.getReposByYear(year);
    }

    @Override
    public Pager<Repository> getReposByLanguage(String language) {
        return repoDao.getReposByLanguage(language);
    }

    @Override
    public Pager<Repository> getReposByKey(String key) {
        return repoDao.getReposByKey(key);
    }

    @Override
    public Pager<Repository> getReposByLan_Key_Year(String language, String keyword, String year, String sort){
        switch (sort){
            case "General":sort = "pushed_at";break;
            case "Fork":sort="forks_count";break;
            case "Star":sort="stargazers_count";break;
            case "Contributor":sort="subscribers_count";break;
        }
      return repoDao.getReposByLan_Key_Year(language,keyword,year,sort);
    };

    @Override
    public List<String> getContributors(String repo_fullname) {
        return repoDao.getContributors(repo_fullname);
    }

    @Override
    public List<String> getCollaborators(String repo_fullname) {
        return repoDao.getCollaborators(repo_fullname);
    }


    private List<Node> recursion(String repo_fullname,int limit){
        List<Node> temp = new ArrayList<>();
        for(String name:repoDao.enlargeViaSubscribers(repo_fullname,limit))
            if(!nodes.contains(new Node(name)))
                temp.add(new Node(name));
        int n = temp.size()>=limit?limit:temp.size();
        temp = temp.subList(0,n);
        return temp;
    }

    List<Node> nodes;
    List<Line> lines;
    @Override
    public Map<String,Object> getRelatedRepoViaSubscribers(String repo_fullname){
        Map<String,Object> result = new HashMap<>();


        nodes = new ArrayList<>();
        nodes.add(new Node(repo_fullname));

        lines = new ArrayList<>();



            List<Node> list = recursion(repo_fullname,3);
            nodes.addAll(list);
            for(int i=1;i<=list.size();i++) {
                lines.add(new Line(0,i));
                List<Node> tmp = recursion(nodes.get(i).name,2);
                for(int j=1;j<=tmp.size();j++)
                    lines.add(new Line(i,j+nodes.size()-1));
                nodes.addAll(tmp);
            }

        result.put("nodes",nodes);
        result.put("lines",lines);
        return result;
    }

    @Override
    public String getCodeFrequency(String full_name) {
        try {
            return repoDao.getCodeFrequency(full_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    class Node{
        String name;
        Node(String name){this.name=name;}
        public String getName(){return name;}

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && name.equals(((Node) obj).name);
        }
    }

    class Line{
        int source;
        int target;

        Line(int source,int target){
            this.source=source;
            this.target=target;
        }

        public int getSource() {
            return source;
        }

        public int getTarget() {
            return target;
        }
    }


    private List<Node> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        List<Node> sortedMap = new ArrayList<>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.add(new Node(tmpEntry.getKey()));
        }
        return sortedMap;




    }

    static class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

            return me2.getValue().compareTo(me1.getValue());
        }
    }


}
