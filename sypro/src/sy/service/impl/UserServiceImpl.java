package sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.httpModel.EasyuiDataGrid;
import sy.httpModel.EasyuiDataGridJson;
import sy.httpModel.User;
import sy.model.Syresources;
import sy.model.Syrole;
import sy.model.SyroleSyresources;
import sy.model.Syuser;
import sy.model.SyuserSyrole;
import sy.service.UserServiceI;
import sy.util.Encrypt;

/**
 * 用户Service
 * 
 * @author 孙宇
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserServiceI {

	private BaseDaoI<Syuser> userDao;
	private BaseDaoI<SyuserSyrole> syuserSyroleDao;
	private BaseDaoI<Syrole> roleDao;

	public BaseDaoI<Syrole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Syrole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<SyuserSyrole> getSyuserSyroleDao() {
		return syuserSyroleDao;
	}

	@Autowired
	public void setSyuserSyroleDao(BaseDaoI<SyuserSyrole> syuserSyroleDao) {
		this.syuserSyroleDao = syuserSyroleDao;
	}

	public BaseDaoI<Syuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Syuser> userDao) {
		this.userDao = userDao;
	}

	@CacheEvict(value = "syproUserCache", allEntries = true)
	public User reg(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setPassword(Encrypt.e(user.getPassword()));
		user.setCreatedatetime(new Date());
		user.setModifydatetime(new Date());
		Syuser u = new Syuser();
		BeanUtils.copyProperties(user, u);
		userDao.save(u);
		return user;
	}

	@Cacheable(value = "syproUserCache", key = "'login'+#user.name+#user.password")
	@Transactional(readOnly = true)
	public User login(User user) {
		Syuser u = userDao.get("from Syuser u where u.name=? and u.password=?", user.getName(), Encrypt.e(user.getPassword()));
		if (u != null) {
			BeanUtils.copyProperties(u, user);
			return user;
		}
		return null;
	}

	@Transactional(readOnly = true)
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from Syuser t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if (user != null) {// 添加查询条件
			if (user.getName() != null && !user.getName().trim().equals("")) {
				hql += " and t.name like '%%" + user.getName().trim() + "%%' ";
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime>=? ";
				values.add(user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime<=? ";
				values.add(user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime>=? ";
				values.add(user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime<=? ";
				values.add(user.getModifydatetimeEnd());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(userDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syuser> syusers = userDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		List<User> users = new ArrayList<User>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (Syuser syuser : syusers) {
				User u = new User();
				BeanUtils.copyProperties(syuser, u);

				Set<SyuserSyrole> syuserSyroleSet = syuser.getSyuserSyroles();
				if (syuserSyroleSet != null && syuserSyroleSet.size() > 0) {
					boolean b = false;
					String roleId = "";
					String roleText = "";
					for (SyuserSyrole syuserSyrole : syuserSyroleSet) {
						if (!b) {
							b = true;
						} else {
							roleId += ",";
							roleText += ",";
						}
						roleId += syuserSyrole.getSyrole().getId();
						roleText += syuserSyrole.getSyrole().getText();
					}
					u.setRoleId(roleId);
					u.setRoleText(roleText);
				}

				users.add(u);
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}

	@Cacheable(value = "syproUserCache", key = "'combobox'+#q")
	@Transactional(readOnly = true)
	public List<User> combobox(String q) {
		if (q == null) {
			q = "";
		}
		String hql = " from Syuser t where name like '%%" + q.trim() + "%%'";
		List<Syuser> l = userDao.find(hql);
		List<User> ul = new ArrayList<User>();
		if (l != null && l.size() > 0) {
			for (Syuser syuser : l) {
				User u = new User();
				BeanUtils.copyProperties(syuser, u);
				ul.add(u);
			}
		}
		return ul;
	}

	@CacheEvict(value = "syproUserCache", allEntries = true)
	public User add(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setPassword(Encrypt.e(user.getPassword()));
		if (user.getCreatedatetime() == null) {
			user.setCreatedatetime(new Date());
		}
		if (user.getModifydatetime() == null) {
			user.setModifydatetime(new Date());
		}
		Syuser syuser = new Syuser();
		BeanUtils.copyProperties(user, syuser);
		userDao.save(syuser);

		if (user.getRoleId() != null && !user.getRoleId().equals("")) {
			for (String id : user.getRoleId().split(",")) {
				SyuserSyrole syuserSyrole = new SyuserSyrole();
				syuserSyrole.setId(UUID.randomUUID().toString());
				syuserSyrole.setSyrole(roleDao.get(Syrole.class, id));
				syuserSyrole.setSyuser(syuser);
				syuserSyroleDao.save(syuserSyrole);
			}
		}

		return user;
	}

	@CacheEvict(value = { "syproAuthCache", "syproUserCache" }, allEntries = true)
	public void editUsersRole(String userIds, String roleId) {
		for (String userId : userIds.split(",")) {
			Syuser syuser = userDao.get(Syuser.class, userId);
			if (syuser != null) {
				List<SyuserSyrole> syuserSyroleList = syuserSyroleDao.find("from SyuserSyrole t where t.syuser=?", syuser);
				if (syuserSyroleList != null && syuserSyroleList.size() > 0) {
					for (SyuserSyrole syuserSyrole : syuserSyroleList) {
						syuserSyroleDao.delete(syuserSyrole);
					}
				}
				if (roleId != null && !roleId.equals("")) {
					for (String id : roleId.split(",")) {
						Syrole syrole = roleDao.get(Syrole.class, id);
						if (syrole != null) {
							SyuserSyrole syuserSyrole = new SyuserSyrole();
							syuserSyrole.setId(UUID.randomUUID().toString());
							syuserSyrole.setSyrole(syrole);
							syuserSyrole.setSyuser(syuser);
							syuserSyroleDao.save(syuserSyrole);
						}
					}
				}

			}
		}
	}

	@CacheEvict(value = { "syproAuthCache", "syproUserCache" }, allEntries = true)
	public User edit(User user) {
		Syuser syuser = userDao.get(Syuser.class, user.getId());
		if (syuser != null) {
			if (user.getPassword() != null && !user.getPassword().trim().equals("")) {
				syuser.setPassword(Encrypt.e(user.getPassword()));
			}
			if (user.getCreatedatetime() == null) {
				syuser.setCreatedatetime(new Date());
			}
			if (user.getModifydatetime() == null) {
				syuser.setModifydatetime(new Date());
			}
			BeanUtils.copyProperties(syuser, user);

			List<SyuserSyrole> syuserSyroleList = syuserSyroleDao.find("from SyuserSyrole t where t.syuser=?", syuser);
			if (syuserSyroleList != null && syuserSyroleList.size() > 0) {
				for (SyuserSyrole syuserSyrole : syuserSyroleList) {
					syuserSyroleDao.delete(syuserSyrole);
				}
			}
			if (user.getRoleId() != null && !user.getRoleId().equals("")) {
				for (String id : user.getRoleId().split(",")) {
					Syrole syrole = roleDao.get(Syrole.class, id);
					if (syrole != null) {
						SyuserSyrole syuserSyrole = new SyuserSyrole();
						syuserSyrole.setId(UUID.randomUUID().toString());
						syuserSyrole.setSyrole(syrole);
						syuserSyrole.setSyuser(syuser);
						syuserSyroleDao.save(syuserSyrole);
					}
				}
			}
		}
		return user;
	}

	@CacheEvict(value = "syproUserCache", allEntries = true)
	public void del(String ids) {
		for (String id : ids.split(",")) {
			Syuser syuser = userDao.get(Syuser.class, id);
			if (syuser != null) {
				Set<SyuserSyrole> syuserSyroleSet = syuser.getSyuserSyroles();
				if (syuserSyroleSet != null && syuserSyroleSet.size() > 0) {
					List<SyuserSyrole> syuserSyroleList = syuserSyroleDao.find("from SyuserSyrole t where t.syuser=?", syuser);
					if (syuserSyroleList != null && syuserSyroleList.size() > 0) {
						for (SyuserSyrole syuserSyrole : syuserSyroleList) {
							syuserSyroleDao.delete(syuserSyrole);
						}
					}
				}
				userDao.delete(syuser);
			}
		}
	}

	@Cacheable(value = "syproUserCache", key = "'getUserInfo'+#user.id")
	public User getUserInfo(User user) {
		Syuser syuser = userDao.get(Syuser.class, user.getId());
		BeanUtils.copyProperties(syuser, user);
		String roleText = "";
		String resourcesText = "";
		Set<SyuserSyrole> syuserSyroleSet = syuser.getSyuserSyroles();
		if (syuserSyroleSet != null && syuserSyroleSet.size() > 0) {
			for (SyuserSyrole syuserSyrole : syuserSyroleSet) {
				if (!roleText.equals("")) {
					roleText += ",";
				}
				Syrole syrole = syuserSyrole.getSyrole();
				roleText += syrole.getText();

				Set<SyroleSyresources> syroleSyresourcesSet = syrole.getSyroleSyresourceses();
				if (syroleSyresourcesSet != null && syroleSyresourcesSet.size() > 0) {
					for (SyroleSyresources syroleSyresources : syroleSyresourcesSet) {
						if (!resourcesText.equals("")) {
							resourcesText += ",";
						}
						Syresources syresources = syroleSyresources.getSyresources();
						resourcesText += syresources.getText();
					}
				}
			}
		}
		user.setRoleText(roleText);
		user.setResourcesText(resourcesText);
		return user;
	}

	@CacheEvict(value = "syproUserCache", allEntries = true)
	public User editUserInfo(User user) {
		if (user.getOldPassword() != null && !user.getOldPassword().trim().equals("") && user.getPassword() != null && !user.getPassword().trim().equals("")) {
			Syuser syuser = userDao.get("from Syuser t where t.id=? and t.password=?", user.getId(), Encrypt.e(user.getOldPassword()));
			if (syuser != null) {
				syuser.setPassword(Encrypt.e(user.getPassword()));
				return user;
			}
		}
		return null;
	}
}
