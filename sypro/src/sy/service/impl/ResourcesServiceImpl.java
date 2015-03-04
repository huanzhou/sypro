package sy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Resources;
import sy.model.Syresources;
import sy.model.SyroleSyresources;
import sy.service.ResourcesServiceI;
import sy.util.ResourcesComparator;

/**
 * 资源Service实现类
 * 
 * @author 孙宇
 * 
 */
@Service("resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl implements ResourcesServiceI {

	private static final Logger logger = Logger.getLogger(ResourcesServiceImpl.class);

	private BaseDaoI<Syresources> resourcesDao;
	private BaseDaoI<SyroleSyresources> syroleSyresourcesDao;

	public BaseDaoI<SyroleSyresources> getSyroleSyresourcesDao() {
		return syroleSyresourcesDao;
	}

	@Autowired
	public void setSyroleSyresourcesDao(BaseDaoI<SyroleSyresources> syroleSyresourcesDao) {
		this.syroleSyresourcesDao = syroleSyresourcesDao;
	}

	public BaseDaoI<Syresources> getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(BaseDaoI<Syresources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	@Cacheable(value = "syproResourcesCache", key = "'tree'+#id")
	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		String hql = "from Syresources t where t.syresources is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Syresources t where t.syresources.id ='" + id + "' order by t.seq";
		}
		List<Syresources> resourceses = resourcesDao.find(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (Syresources syresources : resourceses) {
			tree.add(tree(syresources, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(Syresources syresources, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(syresources.getId());
		node.setText(syresources.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", syresources.getSrc());
		node.setAttributes(attributes);
		if (syresources.getSyresourceses() != null && syresources.getSyresourceses().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Syresources> syresourcesList = new ArrayList<Syresources>(syresources.getSyresourceses());
				Collections.sort(syresourcesList, new ResourcesComparator());// 排序
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (Syresources r : syresourcesList) {
					EasyuiTreeNode t = tree(r, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Cacheable(value = "syproResourcesCache", key = "'treegrid'+#id")
	@Transactional(readOnly = true)
	public List<Resources> treegrid(String id) {
		List<Resources> treegrid = new ArrayList<Resources>();
		String hql = "from Syresources t where t.syresources is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Syresources t where t.syresources.id = '" + id + "' order by t.seq";
		}
		List<Syresources> syresourceses = resourcesDao.find(hql);
		for (Syresources syresources : syresourceses) {
			Resources r = new Resources();
			BeanUtils.copyProperties(syresources, r);
			if (syresources.getSyresources() != null) {
				r.setParentId(syresources.getSyresources().getId());
				r.setParentText(syresources.getSyresources().getText());
			}
			if (syresources.getSyresourceses() != null && syresources.getSyresourceses().size() > 0) {
				r.setState("closed");
			}
			treegrid.add(r);
		}
		return treegrid;
	}

	@CacheEvict(value = "syproResourcesCache", allEntries = true)
	public Resources add(Resources resources) {
		Syresources syresources = new Syresources();
		BeanUtils.copyProperties(resources, syresources);
		if (resources.getParentId() != null && !resources.getParentId().trim().equals("")) {
			syresources.setSyresources(resourcesDao.load(Syresources.class, resources.getParentId()));
		}
		resourcesDao.save(syresources);
		return resources;
	}

	@CacheEvict(value = "syproResourcesCache", allEntries = true)
	public void del(Resources resources) {
		Syresources syresources = resourcesDao.get(Syresources.class, resources.getId());
		if (resources != null) {
			recursiveDelete(syresources);
		}
	}

	private void recursiveDelete(Syresources syresources) {
		if (syresources.getSyresourceses() != null && syresources.getSyresourceses().size() > 0) {
			Set<Syresources> syresourceses = syresources.getSyresourceses();
			for (Syresources t : syresourceses) {
				recursiveDelete(t);
			}
		}

		List<SyroleSyresources> syroleSyresourcesList = syroleSyresourcesDao.find("from SyroleSyresources t where t.syresources=?", syresources);
		if (syroleSyresourcesList != null) {// 删除资源前,需要现将角色资源关系表中的相关记录删除
			for (SyroleSyresources syroleSyresources : syroleSyresourcesList) {
				syroleSyresourcesDao.delete(syroleSyresources);
			}
		}

		resourcesDao.delete(syresources);
	}

	@CacheEvict(value = "syproResourcesCache", allEntries = true)
	public Resources edit(Resources resources) {
		Syresources syresources = resourcesDao.get(Syresources.class, resources.getId());
		if (syresources != null) {
			BeanUtils.copyProperties(resources, syresources);
			if (!syresources.getId().equals("0")) {// 跟节点不可以修改上级节点
				syresources.setSyresources(resourcesDao.get(Syresources.class, resources.getParentId()));
			}
		}
		return resources;
	}

}
