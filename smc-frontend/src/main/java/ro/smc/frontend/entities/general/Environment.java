package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Environment")
@NamedNativeQuery(name = "Environment.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'Environment', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "Environment.findAll", query = "SELECT e FROM Environment e")
public class Environment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EnvironmentCode", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String environmentCode;

	@Id
	@Column(name = "EnvironmentId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer environmentId;

	@Column(name = "EnvironmentName", length = 256)
	@Size(max = 256)
	private String environmentName;

	@ManyToOne
	@JoinColumn(name = "UpstreamEnvironmentId")
	private Environment upstreamEnvironment;

	public Environment() {
	}

	public String getEnvironmentCode() {
		return this.environmentCode;
	}

	public void setEnvironmentCode(String environmentCode) {
		this.environmentCode = environmentCode;
	}

	public Integer getEnvironmentId() {
		return this.environmentId;
	}

	public void setEnvironmentId(Integer environmentId) {
		this.environmentId = environmentId;
	}

	public String getEnvironmentName() {
		return this.environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public Environment getUpstreamEnvironment() {
		return this.upstreamEnvironment;
	}

	public void setUpstreamEnvironment(Environment upstreamEnvironment) {
		this.upstreamEnvironment = upstreamEnvironment;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (environmentId != null ? environmentId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Environment))
			return false;

		Environment other = (Environment) object;
		if ((this.environmentId == null && other.environmentId != null)
				|| (this.environmentId != null && !this.environmentId.equals(other.environmentId)))
			return false;

		return true;
	}

}