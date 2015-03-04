package sy.service;

import java.util.List;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Role;

/**
 * 角色Service
 * 
 * @author 孙宇
 * 
 */
public interface RoleServiceI extends BaseServiceI {

	public List<EasyuiTreeNode> tree(String id);

	public List<Role> treegrid(String id);

	public Role add(Role role);

	public void del(Role role);

	public Role edit(Role role);

}
