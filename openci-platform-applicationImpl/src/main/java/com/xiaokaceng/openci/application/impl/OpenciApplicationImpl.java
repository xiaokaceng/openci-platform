package com.xiaokaceng.openci.application.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.AbstractEntity;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.OpenciApplication;

@Named
@Transactional("transactionManager_opencis")
public class OpenciApplicationImpl implements OpenciApplication {

	public void saveEntity(AbstractEntity entity) {
		if (entity == null) {
			throw new EntityNullException();
		}
		entity.save();
	}

	public void removeEntity(AbstractEntity entity) {
		if (entity == null) {
			throw new EntityNullException();
		}
		entity.remove();
	}

}
