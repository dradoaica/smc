package ro.smc.frontend.entities.configuration;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ro.smc.frontend.entities.general.DictionaryItem;
import ro.smc.frontend.entities.general.Environment;

@Entity
@Table(name = "AvailablePackage")
@NamedNativeQuery(name = "AvailablePackage.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'AvailablePackage', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({ @NamedQuery(name = "AvailablePackage.findAll", query = "SELECT a FROM AvailablePackage a"),
		@NamedQuery(name = "AvailablePackage.findByEnvironment", query = "SELECT a FROM AvailablePackage a WHERE a.environment.environmentId = :environmentId") })
public class AvailablePackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "AvailablePackageCode", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String availablePackageCode;

	@Id
	@Column(name = "AvailablePackageId", nullable = false)
	@NotNull
	private Integer availablePackageId;

	@Column(name = "AvailablePackageName", length = 256)
	@Size(max = 256)
	private String availablePackageName;

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

	@OneToMany(mappedBy = "availablePackage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AvailableConfiguration> configurations;

	public AvailablePackage() {
	}

	public String getAvailablePackageCode() {
		return this.availablePackageCode;
	}

	public void setAvailablePackageCode(String availablePackageCode) {
		this.availablePackageCode = availablePackageCode;
	}

	public int getAvailablePackageId() {
		return this.availablePackageId;
	}

	public void setAvailablePackageId(int availablePackageId) {
		this.availablePackageId = availablePackageId;
	}

	public String getAvailablePackageName() {
		return this.availablePackageName;
	}

	public void setAvailablePackageName(String availablePackageName) {
		this.availablePackageName = availablePackageName;
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

	public List<AvailableConfiguration> getConfigurations() {
		return this.configurations;
	}

	public void setConfigurations(List<AvailableConfiguration> configurations) {
		this.configurations = configurations;
	}

	public AvailableConfiguration addConfiguration(AvailableConfiguration configuration) {
		getConfigurations().add(configuration);
		configuration.setAvailablePackage(this);

		return configuration;
	}

	public AvailableConfiguration removeConfiguration(AvailableConfiguration configuration) {
		getConfigurations().remove(configuration);
		configuration.setAvailablePackage(null);

		return configuration;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (availablePackageId != null ? availablePackageId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AvailablePackage))
			return false;

		AvailablePackage other = (AvailablePackage) object;
		if ((this.availablePackageId == null && other.availablePackageId != null)
				|| (this.availablePackageId != null && !this.availablePackageId.equals(other.availablePackageId)))
			return false;

		return true;
	}

}