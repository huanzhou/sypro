package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.PortalDaoI;

@Repository("portalDao")
public class PortalDaoImpl<T> extends BaseDaoImpl<T> implements PortalDaoI<T> {

}
