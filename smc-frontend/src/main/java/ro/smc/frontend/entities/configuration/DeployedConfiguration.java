package ro.smc.frontend.entities.configuration;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DeployedConfiguration")
@NamedNativeQuery(name = "DeployedConfiguration.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'DeployedConfiguration', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "DeployedConfiguration.findAll", query = "SELECT d FROM DeployedConfiguration d")
public class DeployedConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DeployedConfigurationId", nullable = false)
	@NotNull
	private Integer deployedConfigurationId;

	@Column(name = "DeployedConfigurationName", nullable = false, length = 2147483647)
	@Size(max = 2147483647)
	@NotNull
	private String deployedConfigurationName;

	@Column(name = "Type", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String type;

	@ManyToOne
	@JoinColumn(name = "DeployedPackageId", nullable = false)
	@NotNull
	private DeployedPackage deployedPackage;

	@Column(name = "DeployOrder", nullable = false)
	@NotNull
	private Integer deployOrder;

	@Column(name = "XmlData", nullable = false, length = 2147483647)
	@Size(max = 2147483647)
	@NotNull
	private String xmlData;

	public DeployedConfiguration() {
	}

	public Integer getDeployedConfigurationId() {
		return this.deployedConfigurationId;
	}

	public void setDeployedConfigurationId(Integer deployedConfigurationId) {
		this.deployedConfigurationId = deployedConfigurationId;
	}

	public String getDeployedConfigurationName() {
		return this.deployedConfigurationName;
	}

	public void setDeployedConfigurationName(String deployedConfigurationName) {
		this.deployedConfigurationName = deployedConfigurationName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DeployedPackage getDeployedPackage() {
		return this.deployedPackage;
	}

	public void setDeployedPackage(DeployedPackage deployedPackage) {
		this.deployedPackage = deployedPackage;
	}

	public Integer getDeployOrder() {
		return this.deployOrder;
	}

	public void setDeployOrder(Integer deployOrder) {
		this.deployOrder = deployOrder;
	}

	public String getXmlData() {
		return this.xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (deployedConfigurationId != null ? deployedConfigurationId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DeployedConfiguration))
			return false;

		DeployedConfiguration other = (DeployedConfiguration) object;
		if ((this.deployedConfigurationId == null && other.deployedConfigurationId != null)
				|| (this.deployedConfigurationId != null
						&& !this.deployedConfigurationId.equals(other.deployedConfigurationId)))
			return false;

		return true;
	}

}