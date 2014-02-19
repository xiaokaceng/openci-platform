package com.xiaokaceng.openci.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;

@Entity
@Table(name = "project_developers")
public class ProjectDeveloper extends AbstractEntity {

	private static final long serialVersionUID = -5833807367997029745L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "project_developer_role_relations", joinColumns = { @JoinColumn(name = "project_developer_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	@OneToOne
	@JoinColumn(name = "developer_id")
	private Developer developer;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "is_notice_success")
	private boolean isNoticeSuccess = false;
	
	@Lob
	private String record;
	
	ProjectDeveloper() {

	}

	public ProjectDeveloper(Set<Role> roles, Developer developer, Project project) {
		this.roles = roles;
		this.developer = developer;
		this.project = project;
	}

	public void assignRole(Role role) {
		roles.add(role);
		save();
	}

	public String getDeveloper() {
		return developer.toString();
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public boolean isNoticeSuccess() {
		return isNoticeSuccess;
	}

	public String getRecord() {
		return record;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProjectDeveloper)) {
			return false;
		}
		ProjectDeveloper that = (ProjectDeveloper) other;
		return new EqualsBuilder().append(getDeveloper(), that.getDeveloper()).append(getRoles(), that.getRoles()).isEquals();

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDeveloper()).append(getRoles()).hashCode();
	}

	@Override
	public String toString() {
		return getDeveloper().toString();
	}

}
