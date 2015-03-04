package sy.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.httpModel.Json;
import sy.service.RepairServiceI;

/**
 * 修复数据控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/repairController")
public class RepairController extends BaseController {

	private static final Logger logger = Logger.getLogger(RepairController.class);

	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	/**
	 * 修复数据
	 * 
	 * @return json
	 */
	@RequestMapping(params = "repair")
	@ResponseBody
	synchronized public Json repair() {
		Json j = new Json();
		repairService.repair();
		j.setSuccess(true);
		return j;
	}

}
