package sy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Role;
import sy.model.Syresources;
import sy.model.Syrole;
import sy.model.SyroleSyresources;
import sy.model.SyuserSyrole;
import sy.service.RoleServiceI;
import sy.util.RoleComparator;

/**
 * 角色Service实现类
 * 
 * @author 孙宇
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleServiceI {

	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);

	private BaseDaoI<Syrole> roleDao;
	private BaseDaoI<SyroleSyresources> syroleSyresourcesDao;
	private BaseDaoI<SyuserSyrole> syuserSyroleDao;
	private BaseDaoI<Syresources> resourcesDao;

	public BaseDaoI<Syresources> getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(BaseDaoI<Syresources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	public BaseDaoI<SyuserSyrole> getSyuserSyroleDao() {
		return syuserSyroleDao;
	}

	@Autowired
	public void setSyuserSyroleDao(BaseDaoI<SyuserSyrole> syuserSyroleDao) {
		this.syuserSyroleDao = syuserSyroleDao;
	}

	public BaseDaoI<SyroleSyresources> getSyroleSyresourcesDao() {
		return syroleSyresourcesDao;
	}

	@Autowired
	public void setSyroleSyresourcesDao(BaseDaoI<SyroleSyresources> syroleSyresourcesDao) {
		this.syroleSyresourcesDao = syroleSyresourcesDao;
	}

	public BaseDaoI<Syrole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Syrole> roleDao) {
		this.roleDao = roleDao;
	}

	@Cacheable(value = "syproRoleCache", key = "'tree'+#id")
	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		String hql = "from Syrole t where t.syrole is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Syrole t where t.syrole.id ='" + id + "' order by t.seq";
		}
		List<Syrole> syroleList = roleDao.find(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (Syrole syrole : syroleList) {
			tree.add(tree(syrole, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(Syrole syrole, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(syrole.getId());
		node.setText(syrole.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (syrole.getSyroles() != null && syrole.getSyroles().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Syrole> syroleList = new ArrayList<Syrole>(syrole.getSyroles());
				Collections.sort(syroleList, new RoleComparator());// 排序
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (Syrole r : syroleList) {
					EasyuiTreeNode t = tree(r, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Cacheable(value = "syproRoleCache", key = "'treegrid'+#id")
	@Transactional(readOnly = true)
	public List<Role> treegrid(String id) {
		List<Role> treegrid = new ArrayList<Role>();
		String hql = "from Syrole t where t.syrole is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Syrole t where t.syrole.id = '" + id + "' order by t.seq";
		}
		List<Syrole> syroleList = roleDao.find(hql);
		for (Syrole syrole : syroleList) {
			Role r = new Role();
			BeanUtils.copyProperties(syrole, r);
			if (syrole.getSyrole() != null) {
				r.setParentId(syrole.getSyrole().getId());
				r.setParentText(syrole.getSyrole().getText());
			}
			if (syrole.getSyroles() != null && syrole.getSyroles().size() > 0) {
				r.setState("closed");
			}
			if (syrole.getSyroleSyresourceses() != null && syrole.getSyroleSyresourceses().size() > 0) {
				String resourcesTextList = "";
				String resourcesIdList = "";
				boolean b = false;
				Set<SyroleSyresources> syroleSyresourcesSet = syrole.getSyroleSyresourceses();
				for (SyroleSyresources syroleSyresources : syroleSyresourcesSet) {
					Syresources syresources = syroleSyresources.getSyresources();
					if (!b) {
						b = true;
					} else {
						resourcesTextList += ",";
						resourcesIdList += ",";
					}
					resourcesTextList += syresources.getText();
					resourcesIdList += syresources.getId();
				}
				r.setResourcesId(resourcesIdList);
				r.setResourcesText(resourcesTextList);
			}
			treegrid.add(r);
		}
		return treegrid;
	}

	@CacheEvict(value = "syproRoleCache", allEntries = true)
	public Role add(Role role) {
		Syrole syrole = new Syrole();
		BeanUtils.copyProperties(role, syrole);
		if (role.getParentId() != null && !role.getParentId().trim().equals("")) {
			syrole.setSyrole(roleDao.load(Syrole.class, role.getParentId()));
		}
		roleDao.save(syrole);
		return role;
	}

	@CacheEvict(value = "syproRoleCache", allEntries = true)
	public void del(Role role) {
		Syrole syrole = roleDao.get(Syrole.class, role.getId());
		if (role != null) {
			recursiveDelete(syrole);
		}
	}

	private void recursiveDelete(Syrole syrole) {
		if (syrole.getSyroles() != null && syrole.getSyroles().size() > 0) {
			Set<Syrole> syroleSet = syrole.getSyroles();
			for (Syrole t : syroleSet) {
				recursiveDelete(t);
			}
		}

		List<SyroleSyresources> syroleSyresourcesList = syroleSyresourcesDao.find("from SyroleSyresources t where t.syrole=?", syrole);
		if (syroleSyresourcesList != null && syroleSyresourcesList.size() > 0) {
			for (SyroleSyresources syroleSyresources : syroleSyresourcesList) {
				syroleSyresourcesDao.delete(syroleSyresources);
			}
		}

		List<SyuserSyrole> syuserSyroleList = syuserSyroleDao.find("from SyuserSyrole t where t.syrole=?", syrole);
		if (syuserSyroleList != null && syuserSyroleList.size() > 0) {
			for (SyuserSyrole syuserSyrole : syuserSyroleList) {
				syuserSyroleDao.delete(syuserSyrole);
			}
		}

		roleDao.delete(syrole);
	}

	@CacheEvict(value = "syproRoleCache", allEntries = true)
	public Role edit(Role role) {
		Syrole syrole = roleDao.get(Syrole.class, role.getId());
		if (syrole != null) {
			BeanUtils.copyProperties(role, syrole);
			if (!syrole.getId().equals("0")) {// 跟节点不可以修改上级节点
				syrole.setSyrole(roleDao.get(Syrole.class, role.getParentId()));
			}

			List<SyroleSyresources> syroleSyresourcesList = syroleSyresourcesDao.find("from SyroleSyresources t where t.syrole=?", syrole);
			for (SyroleSyresources syroleSyresources : syroleSyresourcesList) {
				syroleSyresourcesDao.delete(syroleSyresources);
			}

			if (role.getResourcesId() != null && !role.getResourcesId().equals("")) {// 保存角色和资源的关系
				String[] resourceIds = role.getResourcesId().split(",");
				for (String resourceId : resourceIds) {
					SyroleSyresources syroleSyresources = new SyroleSyresources();// 关系
					Syresources syresources = resourcesDao.get(Syresources.class, resourceId);// 资源
					syroleSyresources.setId(UUID.randomUUID().toString());
					syroleSyresources.setSyrole(syrole);
					syroleSyresources.setSyresources(syresources);
					syroleSyresourcesDao.save(syroleSyresources);
				}
			}
		}
		return role;
	}

}
