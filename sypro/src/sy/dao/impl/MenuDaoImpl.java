package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.MenuDaoI;

@Repository("menuDao")
public class MenuDaoImpl<T> extends BaseDaoImpl<T> implements MenuDaoI<T> {

}
