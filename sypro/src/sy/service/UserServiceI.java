package sy.service;

import java.util.List;

import sy.httpModel.EasyuiDataGrid;
import sy.httpModel.EasyuiDataGridJson;
import sy.httpModel.User;

/**
 * 用户Service
 * 
 * @author 孙宇
 * 
 */
public interface UserServiceI extends BaseServiceI {

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User reg(User user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public User login(User user);

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user);

	public List<User> combobox(String q);

	public User add(User user);

	public User edit(User user);

	public void del(String ids);

	public void editUsersRole(String userIds, String roleId);

	public User getUserInfo(User user);

	public User editUserInfo(User user);

}
