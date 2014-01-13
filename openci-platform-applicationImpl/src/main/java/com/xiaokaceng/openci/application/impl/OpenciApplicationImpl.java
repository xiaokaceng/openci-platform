package com.xiaokaceng.openci.application.impl;

import com.dayatang.domain.AbstractEntity;
import com.xiaokaceng.openci.EntityNullException;
import com.xiaokaceng.openci.application.OpenciApplication;

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
