package com.xiaokaceng.openci.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.QuerySettings;

@Entity
@Table(name = "developers")
public class Developer extends TimeIntervalEntity {

	private static final long serialVersionUID = -3733063134487603001L;

	@Column(name = "developer_id", nullable = false, unique = true)
	private String developerId;

	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	public Developer(String developerId, String name, String password, String email) {
		super(new Date());
		this.developerId = developerId;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	Developer() {
	}

	public static boolean checkDeveloperIdIsExist(String developerId) {
		Date now = new Date();
		List<Developer> developers = getRepository().find(QuerySettings.create(Developer.class).le("createDate", now).gt("abolishDate", now).eq("developerId", developerId));
		return !developers.isEmpty();
	}
	
	public String getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Developer)) {
			return false;
		}
		Developer that = (Developer) other;
		return new EqualsBuilder().append(getName(), that.getName()).append(getDeveloperId(), that.getDeveloperId())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getDeveloperId()).hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
