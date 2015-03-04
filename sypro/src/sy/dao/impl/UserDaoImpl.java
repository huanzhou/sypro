package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.UserDaoI;

@Repository("userDao")
public class UserDaoImpl<T> extends BaseDaoImpl<T> implements UserDaoI<T> {

}
