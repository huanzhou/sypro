package sy.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Symenu;
import sy.model.Syportal;
import sy.model.Syresources;
import sy.model.Syrole;
import sy.model.Syuser;
import sy.service.RepairServiceI;
import sy.util.Encrypt;

/**
 * 修复数据库Service
 * 
 * @author 孙宇
 * 
 */
@Service("repairService")
public class RepairServiceImpl extends BaseServiceImpl implements RepairServiceI {

	private BaseDaoI<Syrole> roleDao;
	private BaseDaoI<Syresources> resourcesDao;
	private BaseDaoI<Symenu> menuDao;
	private BaseDaoI<Syuser> userDao;
	private BaseDaoI<Syportal> portalDao;

	public BaseDaoI<Syportal> getPortalDao() {
		return portalDao;
	}

	@Autowired
	public void setPortalDao(BaseDaoI<Syportal> portalDao) {
		this.portalDao = portalDao;
	}

	public BaseDaoI<Syuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Syuser> userDao) {
		this.userDao = userDao;
	}

	public BaseDaoI<Symenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(BaseDaoI<Symenu> menuDao) {
		this.menuDao = menuDao;
	}

	public BaseDaoI<Syresources> getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(BaseDaoI<Syresources> resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	public BaseDaoI<Syrole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Syrole> roleDao) {
		this.roleDao = roleDao;
	}

	@CacheEvict(value = { "syproMenuCache", "syproResourcesCache", "syproAuthCache", "syproUserCache", "syproRoleCache", "syproPortalCache" }, allEntries = true)
	public void repair() {

		// 修复菜单
		deleteMenuParent();
		repairMenu();

		// 修复资源
		deleteResourcesParent();
		repairResources();

		// 修复角色
		deleteRoleParent();
		repairRole();

		// 修复用户
		repairUser();

		// 修复门户
		repairPortal();

	}

	public void repairPortal() {
		Syportal syportal1 = new Syportal();
		syportal1.setId("syportal1");
		syportal1.setTitle("测试");
		syportal1.setSrc("/portal/use.jsp");
		syportal1.setHeight(BigDecimal.valueOf(150));
		syportal1.setClosable("1");
		syportal1.setCollapsible("1");
		syportal1.setSeq(BigDecimal.valueOf(1));
		portalDao.saveOrUpdate(syportal1);

		Syportal syportal2 = new Syportal();
		syportal2.setId("syportal2");
		syportal2.setTitle("关于SyPro");
		syportal2.setSrc("/portal/about.jsp");
		syportal2.setHeight(BigDecimal.valueOf(150));
		syportal2.setClosable("1");
		syportal2.setCollapsible("1");
		syportal2.setSeq(BigDecimal.valueOf(2));
		portalDao.saveOrUpdate(syportal2);

		Syportal syportal3 = new Syportal();
		syportal3.setId("syportal3");
		syportal3.setTitle("组织");
		syportal3.setSrc("/portal/ad.jsp");
		syportal3.setHeight(BigDecimal.valueOf(160));
		syportal3.setClosable("0");
		syportal3.setCollapsible("1");
		syportal3.setSeq(BigDecimal.valueOf(3));
		portalDao.saveOrUpdate(syportal3);

		Syportal syportal4 = new Syportal();
		syportal4.setId("syportal4");
		syportal4.setTitle("群");
		syportal4.setSrc("/portal/qun.jsp");
		syportal4.setHeight(BigDecimal.valueOf(100));
		syportal4.setClosable("0");
		syportal4.setCollapsible("1");
		syportal4.setSeq(BigDecimal.valueOf(4));
		portalDao.saveOrUpdate(syportal4);

		Syportal syportal5 = new Syportal();
		syportal5.setId("syportal5");
		syportal5.setTitle("源码");
		syportal5.setSrc("/portal/source.jsp");
		syportal5.setHeight(BigDecimal.valueOf(60));
		syportal5.setClosable("0");
		syportal5.setCollapsible("0");
		syportal5.setSeq(BigDecimal.valueOf(5));
		portalDao.saveOrUpdate(syportal5);

		Syportal syportal6 = new Syportal();
		syportal6.setId("syportal6");
		syportal6.setTitle("已实现功能");
		syportal6.setSrc("/portal/impl.jsp");
		syportal6.setHeight(BigDecimal.valueOf(150));
		syportal6.setClosable("1");
		syportal6.setCollapsible("1");
		syportal6.setSeq(BigDecimal.valueOf(6));
		portalDao.saveOrUpdate(syportal6);
	}

	public void deleteMenuParent() {
		menuDao.executeHql("update Symenu t set t.symenu = null ");
	}

	public void deleteResourcesParent() {
		resourcesDao.executeHql("update Syresources t set t.syresources = null ");
	}

	public void deleteRoleParent() {
		roleDao.executeHql("update Syrole t set t.syrole = null ");
	}

	private void repairRole() {
		Syrole admin = new Syrole();
		admin.setId("0");
		admin.setText("超管角色");
		admin.setDescript("超级管理员角色");
		admin.setSyrole(null);
		admin.setSeq(BigDecimal.valueOf(0));
		roleDao.saveOrUpdate(admin);
	}

	private void repairResources() {
		Syresources xtgl = new Syresources();
		xtgl.setId("xtgl");
		xtgl.setText("系统管理");
		xtgl.setSrc("");
		xtgl.setDescript("系统管理");
		xtgl.setSyresources(null);
		xtgl.setSeq(BigDecimal.valueOf(1));
		resourcesDao.saveOrUpdate(xtgl);
		repairResourcesXtgl(xtgl);// 修复系统管理子菜单

		Syresources sy = new Syresources();
		sy.setId("sy");
		sy.setText("首页");
		sy.setSrc("/userController.do?home");
		sy.setDescript("首页");
		sy.setSyresources(null);
		sy.setSeq(BigDecimal.valueOf(1));
		sy.setOnoff("0");
		resourcesDao.saveOrUpdate(sy);
		repairSyresourcesSy(sy);
	}

	private void repairSyresourcesSy(Syresources sy) {
		Syresources north = new Syresources();
		north.setId("north");
		north.setText("north");
		north.setSrc("/userController.do?north");
		north.setDescript("north");
		north.setSyresources(sy);
		north.setSeq(BigDecimal.valueOf(1));
		north.setOnoff("0");
		resourcesDao.saveOrUpdate(north);

		Syresources west = new Syresources();
		west.setId("west");
		west.setText("west");
		west.setSrc("/userController.do?west");
		west.setDescript("west");
		west.setSyresources(sy);
		west.setSeq(BigDecimal.valueOf(1));
		west.setOnoff("0");
		resourcesDao.saveOrUpdate(west);

		Syresources center = new Syresources();
		center.setId("center");
		center.setText("center");
		center.setSrc("/userController.do?center");
		center.setDescript("center");
		center.setSyresources(sy);
		center.setSeq(BigDecimal.valueOf(1));
		center.setOnoff("0");
		resourcesDao.saveOrUpdate(center);

		Syresources south = new Syresources();
		south.setId("south");
		south.setText("south");
		south.setSrc("/userController.do?south");
		south.setDescript("south");
		south.setSyresources(sy);
		south.setSeq(BigDecimal.valueOf(1));
		south.setOnoff("0");
		resourcesDao.saveOrUpdate(south);
	}

	private void repairResourcesXtgl(Syresources xtgl) {
		Syresources zygl = new Syresources();
		zygl.setId("zygl");
		zygl.setText("资源管理");
		zygl.setSrc("");
		zygl.setSyresources(xtgl);
		zygl.setSeq(BigDecimal.valueOf(3));
		zygl.setOnoff("0");
		resourcesDao.saveOrUpdate(zygl);
		repairZyglResources(zygl);// 修复资源管理下级

		Syresources yhgl = new Syresources();
		yhgl.setId("yhgl");
		yhgl.setText("用户管理");
		yhgl.setSrc("");
		yhgl.setSyresources(xtgl);
		yhgl.setSeq(BigDecimal.valueOf(2));
		yhgl.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl);
		repairResourcesUser(yhgl);

		Syresources xttz = new Syresources();
		xttz.setId("xttz");
		xttz.setText("系统探针");
		xttz.setSrc("/userController.do?jspinfo");
		xttz.setSyresources(xtgl);
		xttz.setSeq(BigDecimal.valueOf(1));
		resourcesDao.saveOrUpdate(xttz);

		Syresources cdgl = new Syresources();
		cdgl.setId("cdgl");
		cdgl.setText("菜单管理");
		cdgl.setSrc("");
		cdgl.setSyresources(xtgl);
		cdgl.setSeq(BigDecimal.valueOf(0));
		cdgl.setOnoff("0");
		resourcesDao.saveOrUpdate(cdgl);
		repairMenuResources(cdgl);

		Syresources jsgl = new Syresources();
		jsgl.setId("role");
		jsgl.setText("角色管理");
		jsgl.setSrc("");
		jsgl.setSyresources(xtgl);
		jsgl.setSeq(BigDecimal.valueOf(0));
		jsgl.setOnoff("0");
		resourcesDao.saveOrUpdate(jsgl);
		repairRoleResources(jsgl);

		Syresources portal = new Syresources();
		portal.setId("portal");
		portal.setText("门户管理");
		portal.setSrc("");
		portal.setSyresources(xtgl);
		portal.setSeq(BigDecimal.valueOf(0));
		portal.setOnoff("0");
		resourcesDao.saveOrUpdate(portal);
		repairPortalResources(portal);
	}

	private void repairPortalResources(Syresources portal) {
		Syresources portal_page = new Syresources();
		portal_page.setId("portal_page");
		portal_page.setText("门户管理页面");
		portal_page.setSrc("/portalController.do?portal");
		portal_page.setSyresources(portal);
		portal_page.setSeq(BigDecimal.valueOf(0));
		portal_page.setOnoff("1");
		resourcesDao.saveOrUpdate(portal_page);

		Syresources portal_datagrid = new Syresources();
		portal_datagrid.setId("portal_datagrid");
		portal_datagrid.setText("查询门户表格");
		portal_datagrid.setSrc("/portalController.do?datagrid");
		portal_datagrid.setSyresources(portal);
		portal_datagrid.setSeq(BigDecimal.valueOf(3));
		portal_datagrid.setOnoff("1");
		resourcesDao.saveOrUpdate(portal_datagrid);

		Syresources portal_show = new Syresources();
		portal_show.setId("portal_show");
		portal_show.setText("显示门户内容");
		portal_show.setSrc("/portalController.do?show");
		portal_show.setSyresources(portal);
		portal_show.setSeq(BigDecimal.valueOf(3));
		portal_show.setOnoff("0");
		resourcesDao.saveOrUpdate(portal_show);

		Syresources portal_del = new Syresources();
		portal_del.setId("portal_del");
		portal_del.setText("删除门户内容");
		portal_del.setSrc("/portalController.do?del");
		portal_del.setSyresources(portal);
		portal_del.setSeq(BigDecimal.valueOf(3));
		portal_del.setOnoff("1");
		resourcesDao.saveOrUpdate(portal_del);

		Syresources portal_add = new Syresources();
		portal_add.setId("portal_add");
		portal_add.setText("添加门户内容");
		portal_add.setSrc("/portalController.do?add");
		portal_add.setSyresources(portal);
		portal_add.setSeq(BigDecimal.valueOf(3));
		portal_add.setOnoff("1");
		resourcesDao.saveOrUpdate(portal_add);

		Syresources portal_edit = new Syresources();
		portal_edit.setId("portal_edit");
		portal_edit.setText("编辑门户内容");
		portal_edit.setSrc("/portalController.do?edit");
		portal_edit.setSyresources(portal);
		portal_edit.setSeq(BigDecimal.valueOf(3));
		portal_edit.setOnoff("1");
		resourcesDao.saveOrUpdate(portal_edit);
	}

	private void repairRoleResources(Syresources jsgl) {
		Syresources jsgl_page = new Syresources();
		jsgl_page.setId("jsgl_page");
		jsgl_page.setText("角色管理页面");
		jsgl_page.setSrc("/roleController.do?role");
		jsgl_page.setSyresources(jsgl);
		jsgl_page.setSeq(BigDecimal.valueOf(0));
		jsgl_page.setOnoff("1");
		resourcesDao.saveOrUpdate(jsgl_page);

		Syresources jsgl_add = new Syresources();
		jsgl_add.setId("jsgl_add");
		jsgl_add.setText("添加角色");
		jsgl_add.setSrc("/roleController.do?add");
		jsgl_add.setSyresources(jsgl);
		jsgl_add.setSeq(BigDecimal.valueOf(3));
		jsgl_add.setOnoff("1");
		resourcesDao.saveOrUpdate(jsgl_add);

		Syresources jsgl_del = new Syresources();
		jsgl_del.setId("jsgl_del");
		jsgl_del.setText("删除角色");
		jsgl_del.setSrc("/roleController.do?del");
		jsgl_del.setSyresources(jsgl);
		jsgl_del.setSeq(BigDecimal.valueOf(3));
		jsgl_del.setOnoff("1");
		resourcesDao.saveOrUpdate(jsgl_del);

		Syresources jsgl_edit = new Syresources();
		jsgl_edit.setId("jsgl_edit");
		jsgl_edit.setText("修改角色");
		jsgl_edit.setSrc("/roleController.do?edit");
		jsgl_edit.setSyresources(jsgl);
		jsgl_edit.setSeq(BigDecimal.valueOf(3));
		jsgl_edit.setOnoff("1");
		resourcesDao.saveOrUpdate(jsgl_edit);

		Syresources jsgl_treegrid = new Syresources();
		jsgl_treegrid.setId("jsgl_treegrid");
		jsgl_treegrid.setText("查询角色表格");
		jsgl_treegrid.setSrc("/roleController.do?treegrid");
		jsgl_treegrid.setSyresources(jsgl);
		jsgl_treegrid.setSeq(BigDecimal.valueOf(3));
		jsgl_treegrid.setOnoff("1");
		resourcesDao.saveOrUpdate(jsgl_treegrid);

		Syresources jsgl_tree = new Syresources();
		jsgl_tree.setId("jsgl_tree");
		jsgl_tree.setText("查询角色树");
		jsgl_tree.setSrc("/roleController.do?tree");
		jsgl_tree.setSyresources(jsgl);
		jsgl_tree.setSeq(BigDecimal.valueOf(3));
		jsgl_tree.setOnoff("0");
		resourcesDao.saveOrUpdate(jsgl_tree);

	}

	private void repairMenuResources(Syresources cdgl) {
		Syresources cdgl_page = new Syresources();
		cdgl_page.setId("cdgl_page");
		cdgl_page.setText("菜单管理页面");
		cdgl_page.setSrc("/menuController.do?menu");
		cdgl_page.setSyresources(cdgl);
		cdgl_page.setSeq(BigDecimal.valueOf(0));
		cdgl_page.setOnoff("1");
		resourcesDao.saveOrUpdate(cdgl_page);

		Syresources cdgl_add = new Syresources();
		cdgl_add.setId("cdgl_add");
		cdgl_add.setText("添加菜单");
		cdgl_add.setSrc("/menuController.do?add");
		cdgl_add.setSyresources(cdgl);
		cdgl_add.setSeq(BigDecimal.valueOf(3));
		cdgl_add.setOnoff("1");
		resourcesDao.saveOrUpdate(cdgl_add);

		Syresources cdgl_del = new Syresources();
		cdgl_del.setId("cdgl_del");
		cdgl_del.setText("删除菜单");
		cdgl_del.setSrc("/menuController.do?del");
		cdgl_del.setSyresources(cdgl);
		cdgl_del.setSeq(BigDecimal.valueOf(3));
		cdgl_del.setOnoff("1");
		resourcesDao.saveOrUpdate(cdgl_del);

		Syresources cdgl_edit = new Syresources();
		cdgl_edit.setId("cdgl_edit");
		cdgl_edit.setText("修改菜单");
		cdgl_edit.setSrc("/menuController.do?edit");
		cdgl_edit.setSyresources(cdgl);
		cdgl_edit.setSeq(BigDecimal.valueOf(3));
		cdgl_edit.setOnoff("1");
		resourcesDao.saveOrUpdate(cdgl_edit);

		Syresources cdgl_treegrid = new Syresources();
		cdgl_treegrid.setId("cdgl_treegrid");
		cdgl_treegrid.setText("查询菜单表格");
		cdgl_treegrid.setSrc("/menuController.do?treegrid");
		cdgl_treegrid.setSyresources(cdgl);
		cdgl_treegrid.setSeq(BigDecimal.valueOf(3));
		cdgl_treegrid.setOnoff("1");
		resourcesDao.saveOrUpdate(cdgl_treegrid);

		Syresources cdgl_tree = new Syresources();
		cdgl_tree.setId("cdgl_tree");
		cdgl_tree.setText("查询菜单树");
		cdgl_tree.setSrc("/menuController.do?tree");
		cdgl_tree.setSyresources(cdgl);
		cdgl_tree.setSeq(BigDecimal.valueOf(3));
		cdgl_tree.setOnoff("0");
		resourcesDao.saveOrUpdate(cdgl_tree);
	}

	private void repairResourcesUser(Syresources yhgl) {
		Syresources yhgl_page = new Syresources();
		yhgl_page.setId("yhgl_page");
		yhgl_page.setText("用户管理页面");
		yhgl_page.setSrc("/userController.do?user");
		yhgl_page.setSyresources(yhgl);
		yhgl_page.setSeq(BigDecimal.valueOf(0));
		yhgl_page.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_page);

		Syresources yhgl_add = new Syresources();
		yhgl_add.setId("yhgl_add");
		yhgl_add.setText("添加用户");
		yhgl_add.setSrc("/userController.do?add");
		yhgl_add.setSyresources(yhgl);
		yhgl_add.setSeq(BigDecimal.valueOf(2));
		yhgl_add.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_add);

		Syresources yhgl_edit = new Syresources();
		yhgl_edit.setId("yhgl_edit");
		yhgl_edit.setText("编辑用户");
		yhgl_edit.setSrc("/userController.do?edit");
		yhgl_edit.setSyresources(yhgl);
		yhgl_edit.setSeq(BigDecimal.valueOf(2));
		yhgl_edit.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_edit);

		Syresources yhgl_editUsersRole = new Syresources();
		yhgl_editUsersRole.setId("yhgl_editUsersRole");
		yhgl_editUsersRole.setText("批量编辑用户角色");
		yhgl_editUsersRole.setSrc("/userController.do?editUsersRole");
		yhgl_editUsersRole.setSyresources(yhgl);
		yhgl_editUsersRole.setSeq(BigDecimal.valueOf(2));
		yhgl_editUsersRole.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_editUsersRole);

		Syresources yhgl_userInfo = new Syresources();
		yhgl_userInfo.setId("yhgl_userInfo");
		yhgl_userInfo.setText("个人信息页面");
		yhgl_userInfo.setSrc("/userController.do?userInfo");
		yhgl_userInfo.setSyresources(yhgl);
		yhgl_userInfo.setSeq(BigDecimal.valueOf(2));
		yhgl_userInfo.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_userInfo);

		Syresources yhgl_getUserInfo = new Syresources();
		yhgl_getUserInfo.setId("yhgl_getUserInfo");
		yhgl_getUserInfo.setText("获得个人信息");
		yhgl_getUserInfo.setSrc("/userController.do?getUserInfo");
		yhgl_getUserInfo.setSyresources(yhgl);
		yhgl_getUserInfo.setSeq(BigDecimal.valueOf(2));
		yhgl_getUserInfo.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_getUserInfo);

		Syresources yhgl_editUserInfo = new Syresources();
		yhgl_editUserInfo.setId("yhgl_editUserInfo");
		yhgl_editUserInfo.setText("编辑个人信息");
		yhgl_editUserInfo.setSrc("/userController.do?editUserInfo");
		yhgl_editUserInfo.setSyresources(yhgl);
		yhgl_editUserInfo.setSeq(BigDecimal.valueOf(2));
		yhgl_editUserInfo.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_editUserInfo);

		Syresources yhgl_del = new Syresources();
		yhgl_del.setId("yhgl_del");
		yhgl_del.setText("删除用户");
		yhgl_del.setSrc("/userController.do?del");
		yhgl_del.setSyresources(yhgl);
		yhgl_del.setSeq(BigDecimal.valueOf(2));
		yhgl_del.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_del);

		Syresources yhgl_datagrid = new Syresources();
		yhgl_datagrid.setId("yhgl_datagrid");
		yhgl_datagrid.setText("用户表格");
		yhgl_datagrid.setSrc("/userController.do?datagrid");
		yhgl_datagrid.setSyresources(yhgl);
		yhgl_datagrid.setSeq(BigDecimal.valueOf(2));
		yhgl_datagrid.setOnoff("1");
		resourcesDao.saveOrUpdate(yhgl_datagrid);

		Syresources yhgl_loginCombobox = new Syresources();
		yhgl_loginCombobox.setId("yhgl_loginCombobox");
		yhgl_loginCombobox.setText("用户登录");
		yhgl_loginCombobox.setSrc("/userController.do?loginCombobox");
		yhgl_loginCombobox.setSyresources(yhgl);
		yhgl_loginCombobox.setSeq(BigDecimal.valueOf(2));
		yhgl_loginCombobox.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_loginCombobox);

		Syresources yhgl_loginDatagrid = new Syresources();
		yhgl_loginDatagrid.setId("yhgl_loginDatagrid");
		yhgl_loginDatagrid.setText("用户登录");
		yhgl_loginDatagrid.setSrc("/userController.do?loginDatagrid");
		yhgl_loginDatagrid.setSyresources(yhgl);
		yhgl_loginDatagrid.setSeq(BigDecimal.valueOf(2));
		yhgl_loginDatagrid.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_loginDatagrid);

		Syresources yhgl_login = new Syresources();
		yhgl_login.setId("yhgl_login");
		yhgl_login.setText("用户登录");
		yhgl_login.setSrc("/userController.do?login");
		yhgl_login.setSyresources(yhgl);
		yhgl_login.setSeq(BigDecimal.valueOf(2));
		yhgl_login.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_login);

		Syresources yhgl_reg = new Syresources();
		yhgl_reg.setId("yhgl_reg");
		yhgl_reg.setText("用户注册");
		yhgl_reg.setSrc("/userController.do?reg");
		yhgl_reg.setSyresources(yhgl);
		yhgl_reg.setSeq(BigDecimal.valueOf(2));
		yhgl_reg.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_reg);

		Syresources yhgl_logout = new Syresources();
		yhgl_logout.setId("yhgl_logout");
		yhgl_logout.setText("注销");
		yhgl_logout.setSrc("/userController.do?logout");
		yhgl_logout.setSyresources(yhgl);
		yhgl_logout.setSeq(BigDecimal.valueOf(2));
		yhgl_logout.setOnoff("0");
		resourcesDao.saveOrUpdate(yhgl_logout);
	}

	private void repairZyglResources(Syresources zygl) {
		Syresources zygl_page = new Syresources();
		zygl_page.setId("zygl_page");
		zygl_page.setText("资源管理页面");
		zygl_page.setSrc("/resourcesController.do?resources");
		zygl_page.setSyresources(zygl);
		zygl_page.setSeq(BigDecimal.valueOf(0));
		zygl_page.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_page);

		Syresources zygl_add = new Syresources();
		zygl_add.setId("zygl_add");
		zygl_add.setText("添加资源");
		zygl_add.setSrc("/resourcesController.do?add");
		zygl_add.setSyresources(zygl);
		zygl_add.setSeq(BigDecimal.valueOf(3));
		zygl_add.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_add);

		Syresources zygl_del = new Syresources();
		zygl_del.setId("zygl_del");
		zygl_del.setText("删除资源");
		zygl_del.setSrc("/resourcesController.do?del");
		zygl_del.setSyresources(zygl);
		zygl_del.setSeq(BigDecimal.valueOf(3));
		zygl_del.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_del);

		Syresources zygl_edit = new Syresources();
		zygl_edit.setId("zygl_edit");
		zygl_edit.setText("修改资源");
		zygl_edit.setSrc("/resourcesController.do?edit");
		zygl_edit.setSyresources(zygl);
		zygl_edit.setSeq(BigDecimal.valueOf(3));
		zygl_edit.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_edit);

		Syresources zygl_treegrid = new Syresources();
		zygl_treegrid.setId("zygl_treegrid");
		zygl_treegrid.setText("查询资源表格");
		zygl_treegrid.setSrc("/resourcesController.do?treegrid");
		zygl_treegrid.setSyresources(zygl);
		zygl_treegrid.setSeq(BigDecimal.valueOf(3));
		zygl_treegrid.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_treegrid);

		Syresources zygl_tree = new Syresources();
		zygl_tree.setId("zygl_tree");
		zygl_tree.setText("查询资源树");
		zygl_tree.setSrc("/resourcesController.do?tree");
		zygl_tree.setSyresources(zygl);
		zygl_tree.setSeq(BigDecimal.valueOf(3));
		zygl_tree.setOnoff("1");
		resourcesDao.saveOrUpdate(zygl_tree);
	}

	private void repairMenu() {
		Symenu root = new Symenu();
		root.setId("0");
		root.setText("首页");
		root.setIconcls("icon-tip");
		root.setSrc("/userController.do?home");
		root.setSymenu(null);
		root.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(root);
		repairMenuLevel2(root);// 二级菜单
	}

	private void repairMenuLevel2(Symenu root) {
		Symenu xzcf = new Symenu();
		xzcf.setId("xzcf");
		xzcf.setText("行政处罚");
		xzcf.setSrc("");
		xzcf.setSymenu(root);
		xzcf.setSeq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(xzcf);
		repairMenuXzcf(xzcf);// 修复行政处罚子菜单

		Symenu xtgl = new Symenu();
		xtgl.setId("xtgl");
		xtgl.setText("系统管理");
		xtgl.setSrc("");
		xtgl.setSymenu(root);
		xtgl.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(xtgl);
		repairMenuXtgl(xtgl);// 修复系统管理子菜单

		Symenu userInfo = new Symenu();
		userInfo.setId("userInfo");
		userInfo.setText("个人信息");
		userInfo.setSrc("/userController.do?userInfo");
		userInfo.setSymenu(root);
		userInfo.setSeq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(userInfo);

		Symenu baidu = new Symenu();
		baidu.setId("baidu");
		baidu.setText("百度");
		baidu.setSrc("http://baidu.com");
		baidu.setSymenu(root);
		baidu.setSeq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(baidu);
	}

	private void repairMenuXtgl(Symenu xtgl) {
		Symenu zygl = new Symenu();
		zygl.setId("zygl");
		zygl.setText("资源管理");
		zygl.setSrc("/resourcesController.do?resources");
		zygl.setSymenu(xtgl);
		zygl.setSeq(BigDecimal.valueOf(3));
		menuDao.saveOrUpdate(zygl);

		Symenu yhgl = new Symenu();
		yhgl.setId("yhgl");
		yhgl.setText("用户管理");
		yhgl.setSrc("/userController.do?user");
		yhgl.setSymenu(xtgl);
		yhgl.setSeq(BigDecimal.valueOf(2));
		menuDao.saveOrUpdate(yhgl);

		Symenu xttz = new Symenu();
		xttz.setId("xttz");
		xttz.setText("系统探针");
		xttz.setSrc("/userController.do?jspinfo");
		xttz.setSymenu(xtgl);
		xttz.setSeq(BigDecimal.valueOf(1));
		menuDao.saveOrUpdate(xttz);

		Symenu cdgl = new Symenu();
		cdgl.setId("cdgl");
		cdgl.setText("菜单管理");
		cdgl.setSrc("/menuController.do?menu");
		cdgl.setSymenu(xtgl);
		cdgl.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(cdgl);

		Symenu jsgl = new Symenu();
		jsgl.setId("role");
		jsgl.setText("角色管理");
		jsgl.setSrc("/roleController.do?role");
		jsgl.setSymenu(xtgl);
		jsgl.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(jsgl);

		Symenu mhgl = new Symenu();
		mhgl.setId("portal");
		mhgl.setText("门户管理");
		mhgl.setSrc("/portalController.do?portal");
		mhgl.setSymenu(xtgl);
		mhgl.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(mhgl);
	}

	private void repairMenuXzcf(Symenu xzcf) {
		Symenu zfgl = new Symenu();
		zfgl.setId("zfgl");
		zfgl.setText("执法管理");
		zfgl.setSrc("");
		zfgl.setSymenu(xzcf);
		zfgl.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(zfgl);

		Symenu ajcx = new Symenu();
		ajcx.setId("ajcx");
		ajcx.setText("案件查询");
		ajcx.setSrc("");
		ajcx.setSymenu(xzcf);
		ajcx.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(ajcx);

		Symenu ajtj = new Symenu();
		ajtj.setId("ajtj");
		ajtj.setText("案件统计");
		ajtj.setSrc("");
		ajtj.setSymenu(xzcf);
		ajtj.setSeq(BigDecimal.valueOf(0));
		menuDao.saveOrUpdate(ajtj);
	}

	private void repairUser() {
		Syuser admin = new Syuser();
		admin.setId("0");
		admin.setName("admin");
		admin.setPassword(Encrypt.e("admin"));
		admin.setCreatedatetime(new Date());
		admin.setModifydatetime(admin.getCreatedatetime());
		userDao.saveOrUpdate(admin);
	}

}
