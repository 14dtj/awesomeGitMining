package edu.nju.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.nju.model.Pager;
import edu.nju.model.Repository;
import edu.nju.service.IMemberService;
import edu.nju.service.IRepoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by Dora on 2016/4/30.
 */
@Controller
@RequestMapping("/repo")
public class RepoController {
    @Resource
    private IRepoService repoService;
    @Resource
    private IMemberService memberService;

    @RequestMapping(value = "/repos", method = RequestMethod.GET)
    public ModelAndView listRepos(@RequestParam(value="pager.offset",required = false) Integer offset,
                                  @RequestParam(value="lan",required = false, defaultValue = "All")String lan,
                                  @RequestParam(value="key",required = false, defaultValue = "All") String key,
                                  @RequestParam(value="year",required = false, defaultValue = "All")String year,
                                  @RequestParam(value="sort",required = false,defaultValue = "General") String sort) {
        Pager<Repository> total;

        lan = lan.equals("All") ? "" : lan;
        key = key.equals("All") ? "" : key;
        year = year.equals("All") ? "" : year;
        total = repoService.getReposByLan_Key_Year(lan, key.toLowerCase(), year, sort);


        Map<String, Object> map = new HashMap<>();
        map.put("repos", total.getDatas());

        if(total.getDatas()==null||total.getDatas().isEmpty()){
            return null;
        }
        return new ModelAndView("/repo/list", map);
    }

    @RequestMapping(value = "/forks", method = RequestMethod.GET)
    public ModelAndView listForks(@RequestParam("pager.offset") int offset) {
        Pager<Repository> pager = repoService.getReposSortedByFork();
        Map<String, Object> map = new HashMap<>();
        map.put("forks", pager.getDatas());
        return new ModelAndView("/repo/forks",map);
    }

    @RequestMapping(value = "/stars", method = RequestMethod.GET)
    public ModelAndView listStars(@RequestParam("pager.offset") int offset) {
        Pager<Repository> pager = repoService.getReposSortedByStar();
        Map<String, Object> map = new HashMap<>();
        map.put("stars", pager.getDatas());
        return new ModelAndView("/repo/stars",map);
    }

    @RequestMapping(value = "/cons", method = RequestMethod.GET)
    public ModelAndView listContributers(@RequestParam("pager.offset") int offset) {
        Pager<Repository> pager = repoService.getReposSortedByContribute();
        Map<String, Object> map = new HashMap<>();
        map.put("cons", pager.getDatas());
        return new ModelAndView("/repo/cons",map);
    }

    @RequestMapping(value = "/{ownerName}/{repoName:.+}" , method = RequestMethod.GET)
    public ModelAndView showRepo(@PathVariable String ownerName, @PathVariable String repoName) {
        String fullName = ownerName + "/" + repoName;
        System.out.println(fullName);
        Repository repo = repoService.getRepoByFullname(fullName);
        System.out.println(repo);

        ObjectMapper mapper = new ObjectMapper();
        List<String> contributors = repoService.getContributors(fullName);
        List<String> collaborators = repoService.getCollaborators(fullName);

        Map<String,Integer> languages = null;
        try {
            if(repo.getLanguages()!=null)
                languages = mapper.readValue(repo.getLanguages(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Object> result = new HashMap<>();
        result.put("repo",repo);
        result.put("collaborators",collaborators);
        result.put("contributors",contributors);
        result.put("languages",languages);

        return new ModelAndView("/repo/show", result);
    }

    @RequestMapping(value="/{ownerName}/{repoName}/json",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object>  getSubscribe(@PathVariable String ownerName, @PathVariable String repoName) {
        String fullName = ownerName + "/" + repoName;
        Map<String,Object> result=repoService.getRelatedRepoViaSubscribers(fullName);
        return result;

    }

    @RequestMapping(value="/{ownerName}/{repoName}/code_frequency",method = RequestMethod.GET)
    public @ResponseBody
    String  getCodeFrequency(@PathVariable String ownerName, @PathVariable String repoName) {
        String fullName = ownerName + "/" + repoName;
        String result=repoService.getCodeFrequency(fullName);
        return result;

    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchRepos(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("loginMember");
        String param = request.getParameter("name");
        String condition = (String) session.getAttribute("condition");
        //先判断SESSION中的condition是否为空
        if (condition == null) {
            condition = new String();
            session.setAttribute("condition", condition);
            //如果Session中的condition为空，再判断传入的参数是否为空，如果为空就跳转到搜索结果页面
            if (param == null || "".equals(param)) {
                return new ModelAndView("/repo/search");
            }
        }
        //如果SESSION不为空，且传入的搜索条件param不为空，那么将param赋值给condition
        if (param != null && !("".equals(param))) {
            condition = param;
            session.setAttribute("condition", condition);
        }
        //使用session中的condition属性值来作为查询条件

        Pager<Repository> list = repoService.searchRepository(condition);
        Map<String, Object> map = new HashMap<>();
        map.put("repos", list.getDatas());
        if(userName!=null){
            memberService.addSearchRecord(condition,userName);
        }
        return new ModelAndView("/repo/search", map);
    }

    @RequestMapping(value = "/contrast",method = RequestMethod.POST)
    public void addContrast(HttpSession session,String full_name){
        List<String> list;
        if(session.getAttribute("contrast")==null){
             list = new ArrayList<>();

        }else{
            list = (List)session.getAttribute("contrast");
            if(list.contains(full_name))
                return;
        }
        list.add(full_name);
        session.setAttribute("contrast",list);
    }

    @RequestMapping(value = "/contrast",method = RequestMethod.GET)
    public @ResponseBody List<String> showContrast(HttpSession session){
        Object obj = session.getAttribute("contrast");
        return obj==null?null:(List)obj;
    }

}
