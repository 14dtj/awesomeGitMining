package main.business.impl.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import main.business.dto.Converter;
import main.business.service.UserService;
import main.business.utility.ScoreCalculator;
import main.dao.DataFactory;
import main.dao.entity.User;
import main.dao.impl.IUserDao;
import main.vo.SimpleUserVO;
import main.vo.UserCollaReposNumVO;
import main.vo.UserCompanyVO;
import main.vo.UserCreateReposNumVO;
import main.vo.UserRateVO;
import main.vo.UserRegisTimeVO;
import main.vo.UserVO;

/**
 * @author tj
 * @date 2016年2月29日
 */
public class UserServiceImpl implements UserService {
	private static UserServiceImpl instance;
	private IUserDao daoImpl;
	private int pageNums;

	private UserServiceImpl() {
		daoImpl = DataFactory.getUserDataInstance();
		if (daoImpl != null) {
			pageNums = (int) (daoImpl.getAllUser().size() / (1.0 * 10));
		}
	}

	public static UserServiceImpl getInstance() {
		if (instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}

	@Override
	public List<UserVO> searchUser(String id) {
		List<UserVO> vos = new ArrayList<UserVO>();
		if (daoImpl != null) {
			List<String> pos = null;
			try {
				pos = daoImpl.searchUser(id);
				if (pos != null) {
					for (String name : pos) {
						User po = daoImpl.getUser(name);
						vos.add((UserVO) Converter.convert("UserVO", po));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return vos;
	}

	@Override
	public UserVO getUser(String id) {
		User po = null;
		UserVO vo = null;
		try {
			po = daoImpl.getUser(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (po != null) {
			vo = (UserVO) Converter.convert("UserVO", po);

		}
		return vo;
	}

	@Override
	public List<String> getContributeRepos(String id) {
		User po = null;
		List<String> lists = new ArrayList<String>();
		try {
			po = daoImpl.getUser(id);
			if (po != null) {
				lists = po.getContributions_fullname();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lists;
	}

	@Override
	public List<String> getCreateRepos(String id) {
		List<String> lists = new ArrayList<String>();
		User po = null;
		try {
			po = daoImpl.getUser(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (po != null) {
			lists = po.getRepos_fullname();
		}
		return lists;
	}

	@Override
	public List<SimpleUserVO> searchUser(String id, int pageIndex) {
		List<SimpleUserVO> vos = new ArrayList<SimpleUserVO>();
		if (daoImpl != null) {
			List<String> names = daoImpl.searchUser(id);
			if (names != null) {
				for (int i = pageIndex * 5; i < 5 + pageIndex * 5; i++) {
					if (i < names.size() && i >= 0) {
						String login = names.get(i);
						SimpleUserVO vo = new SimpleUserVO();
						vo.setLogin(login);
						vo.setLocation(daoImpl.getLocation(login));
						vo.setCompany(daoImpl.getCompany(login));
						vo.setFollowers(daoImpl.getFollowers(login));
						vos.add(vo);
					}
				}
			}
		}
		return vos;

	}

	@Override
	public List<String> searchUserInfo(String id, int pageIndex) {
		List<String> names = daoImpl.searchUser(id);
		List<String> result = new ArrayList<String>();
		if (names != null) {
			for (int i = pageIndex * 10; i < 10 + pageIndex * 10; i++) {
				if (i < names.size() && i >= 0) {
					result.add(names.get(i));
				}
			}
		}
		return result;
	}

	@Override
	public List<SimpleUserVO> showUsers(int pageIndex) {
		List<String> names = daoImpl.getAllUser();
		List<SimpleUserVO> vos = new ArrayList<SimpleUserVO>();
		if (names != null) {
			for (int i = pageIndex * 10; i < 10 + pageIndex * 10; i++) {
				if (i < names.size() && i >= 0) {
					String name = names.get(i);
					User po = null;
					try {
						po = daoImpl.getUser(name);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (po != null) {
						SimpleUserVO vo = new SimpleUserVO();
						vo.setLocation(daoImpl.getLocation(name));
						vo.setLogin(name);
						vo.setCompany(daoImpl.getCompany(name));
						vo.setFollowers(daoImpl.getFollowers(name));
						vos.add(vo);
					}
				}
			}
		}
		return vos;
	}

	@Override
	public UserRateVO getEvaluation(String id) {
		UserRateVO vo = new UserRateVO();
		User po = null;
		try {
			po = daoImpl.getUser(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (po != null) {
			Map<String, Integer> map = ScoreCalculator.getUserScore();
			vo.setRates(map);
		}
		return vo;
	}

	@Override
	public Image getAvatar(String id) {
		Image avatar = null;
		try {
			avatar = daoImpl.getAvatar(id);
		} catch (IOException e) {
			System.out.println("获取头像超时");
			e.printStackTrace();
		}
		return avatar;
	}

	@Override
	public int[] getTypeStatistic() {
		int[] types = new int[2];
		types[0] = daoImpl.getAllUser().size();
		types[1] = 0;
		return types;
	}

	@Override
	public UserRegisTimeVO getRegisTimeStatistics() {
		UserRegisTimeVO vo = new UserRegisTimeVO();
		vo.setNums(daoImpl.getCreatedTimeStatistics());
		return vo;
	}

	@Override
	public UserCompanyVO getUserCompanyStatistics() {
		UserCompanyVO vo = new UserCompanyVO();
		vo.setNums(daoImpl.getCompanyStatistics());
		return vo;
	}

	@Override
	public int getPageNums() {
		return pageNums;
	}

	@Override
	public UserCreateReposNumVO getUserCreateReposNum() {
		UserCreateReposNumVO vo = new UserCreateReposNumVO();
		List<Integer> list = daoImpl.getRepoCreatedStatistics();
		int[] range = { 0, 1, 4 };
		int[] nums = new int[range.length];
		String[] types = new String[range.length];
		for (int i = 0; i < range.length - 1; i++) {
			types[i] = range[i] + "~" + range[i + 1];
		}
		types[range.length - 1] = range[range.length - 1] + "~" + list.get(list.size() - 1);
		nums[0] = list.lastIndexOf(range[1]) + 1;
		nums[range.length - 1] = list.size() - list.lastIndexOf(range[range.length - 1]);
		for (int i = 1; i < range.length - 1; i++) {
			nums[i] = list.lastIndexOf(range[i + 1]) - list.lastIndexOf(range[i]);
		}
		vo.setNums(nums);
		vo.setRanges(types);
		return vo;
	}

	@Override
	public UserCollaReposNumVO getUserCollaReposNum() {
		UserCollaReposNumVO vo = new UserCollaReposNumVO();
		List<Integer> list = daoImpl.getRepoCollabortedStatistics();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return vo;
	}

	@Override
	public int getSearchPageNums(String id) {
		if (daoImpl != null) {
			if (daoImpl.searchUser(id) != null) {
				return daoImpl.searchUser(id).size();
			}
		}
		return 0;
	}

	@Override
	public List<String> getLanguageSkills(String login) {
		List<String> result = new ArrayList<String>();
		if (daoImpl != null) {
			result = daoImpl.getLanguageSkills(login);
		}
		return result;
	}

}
