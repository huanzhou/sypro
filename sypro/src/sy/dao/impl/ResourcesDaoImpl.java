package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.ResourcesDaoI;

@Repository("resourcesDao")
public class ResourcesDaoImpl<T> extends BaseDaoImpl<T> implements ResourcesDaoI<T> {

}
