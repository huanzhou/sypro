package sy.interceptors;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sy.httpModel.SessionInfo;
import sy.httpModel.User;
import sy.model.Syresources;
import sy.service.AuthServiceI;
import sy.util.RequestUtil;
import sy.util.ResourceUtil;

/**
 * 权限拦截器
 * 
 * @author 孙宇
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	private AuthServiceI authService;

	public AuthServiceI getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(AuthServiceI authService) {
		this.authService = authService;
	}

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址

		if (requestPath.equals("/repairController.do?repair")) {// 修复数据库不需要验证权限
			return true;
		}

		boolean b = false;
		List<Syresources> offResourcesList = authService.offResourcesList(); // 不需要权限验证的资源集合
		for (Syresources syresources : offResourcesList) {
			if (syresources.getSrc() != null && syresources.getSrc().equals(requestPath)) {
				b = true;
				break;
			}
		}
		if (b) {
			return true;// 当前访问资源地址是不需要验证的资源
		}

		Syresources syresources = authService.getSyresourcesByRequestPath(requestPath);
		if (syresources == null) {// 当前访问资源地址没有在数据库中存在
			forward("请执行【" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/init.jsp" + "】页面修复数据库！数据库缺失【" + requestPath + "】资源！", request, response);
			return false;
		}

		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo == null) {// 没有登录系统，或登录超时
			forward("您没有登录或登录超时，请重新登录！", request, response);
			return false;
		}

		User user = sessionInfo.getUser();
		if (user.getId().equals("0")) {// 超级管理员不需要验证权限
			return true;
		}

		if (authService.checkAuth(user.getId(), requestPath)) {// 验证当前用户是否有权限访问此资源
			return true;
		} else {
			forward("您没有【" + syresources.getText() + "】权限，请联系管理员给您赋予相应权限！", request, response);
			return false;
		}

	}

	private void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("error/authMsg.jsp").forward(request, response);
	}

}
