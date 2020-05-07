package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "EnvDatabase")
@NamedNativeQuery(name = "EnvDatabase.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'EnvDatabase', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({ @NamedQuery(name = "EnvDatabase.findAll", query = "SELECT e FROM EnvDatabase e"),
		@NamedQuery(name = "EnvDatabase.findByEnvironment", query = "SELECT e FROM EnvDatabase e WHERE e.environment.environmentId = :environmentId") })
public class EnvDatabase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "AdditionalInfo", length = 2147483647)
	@Size(max = 2147483647)
	private String additionalInfo;

	@Column(name = "DatabaseName", length = 256)
	@Size(max = 256)
	private String databaseName;

	@Column(name = "EnvDatabaseCode", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String envDatabaseCode;

	@Id
	@Column(name = "EnvDatabaseId", nullable = false)
	@NotNull
	private Integer envDatabaseId;

	@Column(name = "EnvDatabaseName", length = 256)
	@Size(max = 256)
	private String envDatabaseName;

	@ManyToOne
	@JoinColumn(name = "EnvironmentId", nullable = false)
	@NotNull
	private Environment environment;

	@Column(name = "Mask", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String mask;

	@Column(name = "Password", length = 2147483647)
	@Size(max = 2147483647)
	private String password;

	@Column(name = "ServerName", length = 256)
	@Size(max = 256)
	private String serverName;

	@Column(name = "UserName", length = 256)
	@Size(max = 256)
	private String userName;

	public EnvDatabase() {
	}

	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getEnvDatabaseCode() {
		return this.envDatabaseCode;
	}

	public void setEnvDatabaseCode(String envDatabaseCode) {
		this.envDatabaseCode = envDatabaseCode;
	}

	public Integer getEnvDatabaseId() {
		return this.envDatabaseId;
	}

	public void setEnvDatabaseId(Integer envDatabaseId) {
		this.envDatabaseId = envDatabaseId;
	}

	public String getEnvDatabaseName() {
		return this.envDatabaseName;
	}

	public void setEnvDatabaseName(String envDatabaseName) {
		this.envDatabaseName = envDatabaseName;
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String getMask() {
		return this.mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (envDatabaseId != null ? envDatabaseId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EnvDatabase))
			return false;

		EnvDatabase other = (EnvDatabase) object;
		if ((this.envDatabaseId == null && other.envDatabaseId != null)
				|| (this.envDatabaseId != null && !this.envDatabaseId.equals(other.envDatabaseId)))
			return false;

		return true;
	}

}