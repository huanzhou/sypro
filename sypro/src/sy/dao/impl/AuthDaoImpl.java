package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.AuthDaoI;

@Repository("authDao")
public class AuthDaoImpl<T> extends BaseDaoImpl<T> implements AuthDaoI<T> {

}
