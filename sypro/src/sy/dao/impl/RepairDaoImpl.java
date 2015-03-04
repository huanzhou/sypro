package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.RepairDaoI;

@Repository("repairDao")
public class RepairDaoImpl<T> extends BaseDaoImpl<T> implements RepairDaoI<T> {

}
