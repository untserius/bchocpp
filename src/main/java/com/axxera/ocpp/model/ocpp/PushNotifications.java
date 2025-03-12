package com.axxera.ocpp.model.ocpp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pushNotification")
public class PushNotifications extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long userId;

	private boolean flag;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "PushNotifications [userId=" + userId + ", flag=" + flag + "]";
	}

}
