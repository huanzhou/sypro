package sy.service;

import java.util.List;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Menu;

/**
 * 菜单Service
 * 
 * @author 孙宇
 * 
 */
public interface MenuServiceI extends BaseServiceI {

	/**
	 * 获得菜单树
	 * 
	 * @param id
	 * @return
	 */
	public List<EasyuiTreeNode> tree(String id);

	public List<Menu> treegrid(String id);

	public Menu add(Menu menu);

	public void del(Menu menu);

	public Menu edit(Menu menu);

}
