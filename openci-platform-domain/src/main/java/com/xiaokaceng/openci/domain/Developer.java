package com.xiaokaceng.openci.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;

@Entity
@Table(name = "developers")
public class Developer extends AbstractEntity {

	private static final long serialVersionUID = -3733063134487603001L;

	@Column(name = "developer_id", nullable = false, unique = true)
	private String developerId;

	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	public Developer(String developerId, String name, String email) {
		this.developerId = developerId;
		this.name = name;
		this.email = email;
	}

	public String getDeveloperId() {
		return developerId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
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
		return new EqualsBuilder().append(getName(), that.getName()).append(getDeveloperId(), that.getDeveloperId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getDeveloperId()).hashCode();
	}

	@Override
	public String toString() {
		return getDeveloperId();
	}

}
