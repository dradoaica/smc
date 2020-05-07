package ro.smc.frontend.entities.configuration;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.entities.general.Environment;

@Entity
@Table(name = "DeployedPackage")
@NamedNativeQuery(name = "DeployedPackage.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'DeployedPackage', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({ @NamedQuery(name = "DeployedPackage.findAll", query = "SELECT d FROM DeployedPackage d"),
		@NamedQuery(name = "DeployedPackage.findByEnvironment", query = "SELECT d FROM DeployedPackage d WHERE d.environment.environmentId = :environmentId") })
public class DeployedPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "DeployedPackageCode", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String deployedPackageCode;

	@Id
	@Column(name = "DeployedPackageId", nullable = false)
	@NotNull
	private Integer deployedPackageId;

	@Column(name = "DeployedPackageName", length = 256)
	@Size(max = 256)
	private String deployedPackageName;

	@ManyToOne
	@JoinColumn(name = "EnvironmentId", nullable = false)
	@NotNull
	private Environment environment;

	@ManyToOne
	@JoinColumn(name = "PackageStateId", nullable = false)
	@NotNull
	private DictionaryItem packageState;

	@Column(name = "TargetMask", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String targetMask;

	@OneToMany(mappedBy = "deployedPackage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeployedConfiguration> configurations;

	public DeployedPackage() {
	}

	public String getDeployedPackageCode() {
		return this.deployedPackageCode;
	}

	public void setDeployedPackageCode(String deployedPackageCode) {
		this.deployedPackageCode = deployedPackageCode;
	}

	public int getDeployedPackageId() {
		return this.deployedPackageId;
	}

	public void setDeployedPackageId(int deployedPackageId) {
		this.deployedPackageId = deployedPackageId;
	}

	public String getDeployedPackageName() {
		return this.deployedPackageName;
	}

	public void setDeployedPackageName(String deployedPackageName) {
		this.deployedPackageName = deployedPackageName;
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public DictionaryItem getPackageState() {
		return this.packageState;
	}

	public void setPackageState(DictionaryItem packageState) {
		this.packageState = packageState;
	}

	public String getTargetMask() {
		return this.targetMask;
	}

	public void setTargetMask(String targetMask) {
		this.targetMask = targetMask;
	}

	public List<DeployedConfiguration> getConfigurations() {
		return this.configurations;
	}

	public void setConfigurations(List<DeployedConfiguration> configurations) {
		this.configurations = configurations;
	}

	public DeployedConfiguration addConfiguration(DeployedConfiguration configuration) {
		getConfigurations().add(configuration);
		configuration.setDeployedPackage(this);

		return configuration;
	}

	public DeployedConfiguration removeConfiguration(DeployedConfiguration configuration) {
		getConfigurations().remove(configuration);
		configuration.setDeployedPackage(null);

		return configuration;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (deployedPackageId != null ? deployedPackageId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DeployedPackage))
			return false;

		DeployedPackage other = (DeployedPackage) object;
		if ((this.deployedPackageId == null && other.deployedPackageId != null)
				|| (this.deployedPackageId != null && !this.deployedPackageId.equals(other.deployedPackageId)))
			return false;

		return true;
	}

}