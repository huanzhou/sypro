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
import sy.httpModel.Menu;
import sy.model.Symenu;
import sy.service.MenuServiceI;
import sy.util.MenuComparator;

/**
 * 菜单Service实现类
 * 
 * @author 孙宇
 * 
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuServiceI {

	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

	private BaseDaoI<Symenu> menuDao;

	public BaseDaoI<Symenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Symenu> menuDao) {
		this.menuDao = menuDao;
	}

	@Cacheable(value = "syproMenuCache", key = "'tree'+#id")
	@Transactional(readOnly = true)
	public List<EasyuiTreeNode> tree(String id) {
		String hql = "from Symenu t where t.symenu is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Symenu t where t.symenu.id ='" + id + "' order by t.seq";
		}
		List<Symenu> symenus = menuDao.find(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (Symenu symenu : symenus) {
			tree.add(tree(symenu, false));
		}
		return tree;
	}

	private EasyuiTreeNode tree(Symenu symenu, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(symenu.getId());
		node.setText(symenu.getText());
		node.setIconCls(symenu.getIconcls());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", symenu.getSrc());
		node.setAttributes(attributes);
		if (symenu.getSymenus() != null && symenu.getSymenus().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Symenu> symenuList = new ArrayList<Symenu>(symenu.getSymenus());
				Collections.sort(symenuList, new MenuComparator());// 排序
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (Symenu m : symenuList) {
					EasyuiTreeNode t = tree(m, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Cacheable(value = "syproMenuCache", key = "'treegrid'+#id")
	@Transactional(readOnly = true)
	public List<Menu> treegrid(String id) {
		List<Menu> treegrid = new ArrayList<Menu>();
		String hql = "from Symenu t where t.symenu is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			hql = "from Symenu t where t.symenu.id = '" + id + "' order by t.seq";
		}
		List<Symenu> symenus = menuDao.find(hql);
		for (Symenu symenu : symenus) {
			Menu m = new Menu();
			BeanUtils.copyProperties(symenu, m);
			if (symenu.getSymenu() != null) {
				m.setParentId(symenu.getSymenu().getId());
				m.setParentText(symenu.getSymenu().getText());
			}
			m.setIconCls(symenu.getIconcls());
			if (symenu.getSymenus() != null && symenu.getSymenus().size() > 0) {
				m.setState("closed");
			}
			treegrid.add(m);
		}
		return treegrid;
	}

	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public Menu add(Menu menu) {
		Symenu symenu = new Symenu();
		BeanUtils.copyProperties(menu, symenu);
		symenu.setIconcls(menu.getIconCls());
		if (menu.getParentId() != null && !menu.getParentId().trim().equals("")) {
			symenu.setSymenu(menuDao.load(Symenu.class, menu.getParentId()));
		}
		menuDao.save(symenu);
		return menu;
	}

	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public void del(Menu menu) {
		Symenu symenu = menuDao.get(Symenu.class, menu.getId());
		if (symenu != null) {
			recursiveDelete(symenu);
		}
	}

	private void recursiveDelete(Symenu symenu) {
		if (symenu.getSymenus() != null && symenu.getSymenus().size() > 0) {
			Set<Symenu> symenus = symenu.getSymenus();
			for (Symenu t : symenus) {
				recursiveDelete(t);
			}
		}
		menuDao.delete(symenu);
	}

	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public Menu edit(Menu menu) {
		Symenu symenu = menuDao.get(Symenu.class, menu.getId());
		if (symenu != null) {
			BeanUtils.copyProperties(menu, symenu);
			symenu.setIconcls(menu.getIconCls());
			if (!symenu.getId().equals("0")) {// 根节点不可以修改上级节点
				symenu.setSymenu(menuDao.get(Symenu.class, menu.getParentId()));
			}
		}
		return menu;
	}

}
