package sy.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Json;
import sy.httpModel.Role;
import sy.service.RoleServiceI;

/**
 * 角色控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public String role() {
		return "/admin/role";
	}

	/**
	 * 角色树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<EasyuiTreeNode> tree(String id) {
		return roleService.tree(id);
	}

	/**
	 * 角色treegrid
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "treegrid")
	@ResponseBody
	public List<Role> treegrid(String id) {
		return roleService.treegrid(id);
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(Role role) {
		Json j = new Json();
		Role r = roleService.add(role);
		j.setSuccess(true);
		j.setMsg("添加成功!");
		return j;
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public Json del(Role role) {
		Json j = new Json();
		roleService.del(role);
		j.setSuccess(true);
		j.setMsg("删除成功!");
		return j;
	}

	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(Role role) {
		Json j = new Json();
		Role r = roleService.edit(role);
		j.setSuccess(true);
		j.setMsg("编辑成功!");
		return j;
	}

}
