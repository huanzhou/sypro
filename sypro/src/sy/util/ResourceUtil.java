package sy.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author 孙宇
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

}
