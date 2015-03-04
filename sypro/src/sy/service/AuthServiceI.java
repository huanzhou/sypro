package sy.service;

import java.util.List;

import sy.model.Syresources;

/**
 * 权限Service
 * 
 * @author 孙宇
 * 
 */
public interface AuthServiceI extends BaseServiceI {

	/**
	 * 查询所有不需要验证的资源
	 * 
	 * @return
	 */
	public List<Syresources> offResourcesList();

	/**
	 * 通过资源路径获得资源对象
	 * 
	 * @param requestPath
	 * @return
	 */
	public Syresources getSyresourcesByRequestPath(String requestPath);

	/**
	 * 验证用户是否有访问此资源的权限
	 * 
	 * @param userId
	 * @param requestPath
	 * @return
	 */
	public boolean checkAuth(String userId, String requestPath);

}
