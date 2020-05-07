package ro.smc.frontend.entities.configuration;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "AvailableConfiguration")
@NamedNativeQuery(name = "AvailableConfiguration.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'AvailableConfiguration', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "AvailableConfiguration.findAll", query = "SELECT a FROM AvailableConfiguration a")
public class AvailableConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AvailableConfigurationId", nullable = false)
	@NotNull
	private Integer availableConfigurationId;

	@Column(name = "AvailableConfigurationName", nullable = false, length = 2147483647)
	@Size(max = 2147483647)
	@NotNull
	private String availableConfigurationName;

	@Column(name = "Type", nullable = false, length = 50)
	@Size(max = 50)
	@NotNull
	private String type;

	@ManyToOne
	@JoinColumn(name = "AvailablePackageId", nullable = false)
	@NotNull
	private AvailablePackage availablePackage;

	@Column(name = "DeployOrder", nullable = false)
	@NotNull
	private Integer deployOrder;

	@Column(name = "XmlData", nullable = false, length = 2147483647)
	@Size(max = 2147483647)
	@NotNull
	private String xmlData;

	public AvailableConfiguration() {
	}

	public Integer getAvailableConfigurationId() {
		return this.availableConfigurationId;
	}

	public void setAvailableConfigurationId(Integer availableConfigurationId) {
		this.availableConfigurationId = availableConfigurationId;
	}

	public String getAvailableConfigurationName() {
		return this.availableConfigurationName;
	}

	public void setAvailableConfigurationName(String availableConfigurationName) {
		this.availableConfigurationName = availableConfigurationName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AvailablePackage getAvailablePackage() {
		return this.availablePackage;
	}

	public void setAvailablePackage(AvailablePackage availablePackage) {
		this.availablePackage = availablePackage;
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
		hash += (availableConfigurationId != null ? availableConfigurationId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AvailableConfiguration))
			return false;

		AvailableConfiguration other = (AvailableConfiguration) object;
		if ((this.availableConfigurationId == null && other.availableConfigurationId != null)
				|| (this.availableConfigurationId != null
						&& !this.availableConfigurationId.equals(other.availableConfigurationId)))
			return false;

		return true;
	}

}