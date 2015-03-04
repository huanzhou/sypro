package sy.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.model.Syresources;
import sy.model.Syrole;
import sy.model.SyroleSyresources;
import sy.model.Syuser;
import sy.model.SyuserSyrole;
import sy.service.AuthServiceI;

/**
 * 权限Service
 * 
 * @author 孙宇
 * 
 */
@Service("authService")
public class AuthServiceImpl extends BaseServiceImpl implements AuthServiceI {

	private BaseDaoI<Syresources> resourcesDao;
	private BaseDaoI<Syuser> userDao;

	public BaseDaoI<Syuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Syuser> userDao) {
		this.userDao = userDao;
	}

	public BaseDaoI<Syresources> getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(BaseDaoI<Syresources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	@Cacheable(value = "syproAuthCache", key = "'offResourcesList'")
	@Transactional(readOnly = true)
	public List<Syresources> offResourcesList() {
		return resourcesDao.find("from Syresources t where t.onoff != '1'");
	}

	@Cacheable(value = "syproAuthCache", key = "'getSyresourcesByRequestPath'+#requestPath")
	@Transactional(readOnly = true)
	public Syresources getSyresourcesByRequestPath(String requestPath) {
		return resourcesDao.get("from Syresources t where t.src=?", requestPath);
	}

	@Cacheable(value = "syproAuthCache", key = "'checkAuth'+#userId+#requestPath")
	@Transactional(readOnly = true)
	public boolean checkAuth(String userId, String requestPath) {
		boolean b = false;
		Syuser syuser = userDao.get(Syuser.class, userId);
		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();// 用户和角色关系
		for (SyuserSyrole syuserSyrole : syuserSyroles) {
			Syrole syrole = syuserSyrole.getSyrole();
			Set<SyroleSyresources> syroleSyreources = syrole.getSyroleSyresourceses();// 角色和资源关系
			for (SyroleSyresources syroleSyreource : syroleSyreources) {
				Syresources syresources = syroleSyreource.getSyresources();
				if (syresources.getSrc() != null && requestPath.equals(syresources.getSrc())) {
					return true;
				}
			}
		}
		return b;
	}

}
