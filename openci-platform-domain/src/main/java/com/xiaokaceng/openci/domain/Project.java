package com.xiaokaceng.openci.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.xiaokaceng.openci.EntityNullException;

@Entity
@Table(name = "projects")
public class Project extends AbstractEntity {

	private static final long serialVersionUID = -1381157577442931544L;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	private Set<ProjectDeveloper> developers = new HashSet<ProjectDeveloper>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	private Set<Tool> tools = new HashSet<Tool>();

	@Temporal(TemporalType.TIME)
	@Column(name = "create_date")
	private Date createDate;

	public Project(String name) {
		this.name = name;
		this.createDate = new Date();
	}

	public void addTool(Tool tool) {
		if (tool == null) {
			throw new EntityNullException();
		}
		tools.add(tool);
		save();
	}
	
	public String getName() {
		return name;
	}

	public void setDevelopers(Set<ProjectDeveloper> developers) {
		this.developers = developers;
	}

	public void setTools(Set<Tool> tools) {
		this.tools = tools;
	}

	public Set<ProjectDeveloper> getDevelopers() {
		return developers;
	}

	public Set<Tool> getTools() {
		return tools;
	}

	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Project)) {
			return false;
		}
		Project that = (Project) other;
		return new EqualsBuilder().append(getName(), that.getName()).append(getCreateDate(), that.getCreateDate()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getCreateDate()).hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
