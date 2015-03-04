package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.RoleDaoI;

@Repository("roleDao")
public class RoleDaoImpl<T> extends BaseDaoImpl<T> implements RoleDaoI<T> {

}
