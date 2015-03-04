package sy.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Json;
import sy.httpModel.Resources;
import sy.service.ResourcesServiceI;

/**
 * 资源控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/resourcesController")
public class ResourcesController extends BaseController {

	private static final Logger logger = Logger.getLogger(ResourcesController.class);

	private ResourcesServiceI resourcesService;

	public ResourcesServiceI getResourcesService() {
		return resourcesService;
	}

	@Autowired
	public void setResourcesService(ResourcesServiceI resourcesService) {
		this.resourcesService = resourcesService;
	}

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "resources")
	public String resources() {
		return "/admin/resources";
	}

	/**
	 * 获取资源管理树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<EasyuiTreeNode> tree(String id) {
		return resourcesService.tree(id);
	}

	/**
	 * 资源管理treegrid
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "treegrid")
	@ResponseBody
	public List<Resources> treegrid(String id) {
		return resourcesService.treegrid(id);
	}

	/**
	 * 添加资源
	 * 
	 * @param resources
	 * @return
	 */
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(Resources resources) {
		Json j = new Json();
		Resources r = resourcesService.add(resources);
		j.setSuccess(true);
		j.setMsg("添加成功!");
		return j;
	}

	/**
	 * 删除资源
	 * 
	 * @param resources
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public Json del(Resources resources) {
		Json j = new Json();
		resourcesService.del(resources);
		j.setSuccess(true);
		j.setMsg("删除成功!");
		return j;
	}

	/**
	 * 编辑资源
	 * 
	 * @param resources
	 * @return
	 */
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(Resources resources) {
		Json j = new Json();
		Resources r = resourcesService.edit(resources);
		j.setSuccess(true);
		j.setMsg("编辑成功!");
		return j;
	}

}
