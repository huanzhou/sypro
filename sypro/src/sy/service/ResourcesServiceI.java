package sy.service;

import java.util.List;

import sy.httpModel.EasyuiTreeNode;
import sy.httpModel.Resources;

/**
 * 资源Service
 * 
 * @author 孙宇
 * 
 */
public interface ResourcesServiceI extends BaseServiceI {

	public List<EasyuiTreeNode> tree(String id);

	public List<Resources> treegrid(String id);

	public Resources add(Resources resources);

	public void del(Resources resources);

	public Resources edit(Resources resources);

}
