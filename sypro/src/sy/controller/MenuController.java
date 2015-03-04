package sy.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Json;
import sy.httpModel.Menu;
import sy.service.MenuServiceI;

/**
 * 菜单控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/menuController")
public class MenuController extends BaseController {

	private static final Logger logger = Logger.getLogger(MenuController.class);

	private MenuServiceI menuService;

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "menu")
	public String menu() {
		return "/admin/menu";
	}

	/**
	 * 获取菜单树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<EasyuiTreeNode> tree(String id) {
		return menuService.tree(id);
	}

	/**
	 * 获取菜单treegrid
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "treegrid")
	@ResponseBody
	public List<Menu> treegrid(String id) {
		return menuService.treegrid(id);
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(Menu menu) {
		Json j = new Json();
		Menu m = menuService.add(menu);
		j.setSuccess(true);
		j.setMsg("添加成功!");
		return j;
	}

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public Json del(Menu menu) {
		Json j = new Json();
		menuService.del(menu);
		j.setSuccess(true);
		j.setMsg("删除成功!");
		return j;
	}

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(Menu menu) {
		Json j = new Json();
		Menu m = menuService.edit(menu);
		j.setSuccess(true);
		j.setMsg("编辑成功!");
		return j;
	}

}
