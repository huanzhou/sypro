package sy.service;

import sy.httpModel.EasyuiDataGrid;
import sy.httpModel.EasyuiDataGridJson;
import sy.httpModel.Portal;

/**
 * portal Service
 * 
 * @author 孙宇
 * 
 */
public interface PortalServiceI extends BaseServiceI {

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, Portal portal);

	public void del(String ids);

	public void edit(Portal portal);

	public void add(Portal portal);

}
