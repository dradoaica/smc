package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "City")
@NamedNativeQuery(name = "City.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'City', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({
		@NamedQuery(name = "City.findAll", query = "SELECT c FROM City c ORDER BY c.cityType.dictionaryItemName, c.cityName"),
		@NamedQuery(name = "City.findAllCities", query = "SELECT c FROM City c WHERE c.cityType.dictionaryItemCode = '#City' ORDER BY c.cityName"),
		@NamedQuery(name = "City.findAllDistricts", query = "SELECT c FROM City c WHERE c.cityType.dictionaryItemCode = '#District' ORDER BY c.cityName"),
		@NamedQuery(name = "City.findAllCountries", query = "SELECT c FROM City c WHERE c.cityType.dictionaryItemCode = '#Country' ORDER BY c.cityName") })
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CityId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer cityId;

	@Column(name = "CityCode", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	private String cityCode;

	@Column(name = "CityName", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	private String cityName;

	@ManyToOne
	@JoinColumn(name = "CityTypeId", nullable = false)
	@NotNull
	private DictionaryItem cityType;

	public City() {
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public DictionaryItem getCityType() {
		return this.cityType;
	}

	public void setCityType(DictionaryItem cityType) {
		this.cityType = cityType;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cityId != null ? cityId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof City))
			return false;

		City other = (City) object;
		if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId)))
			return false;

		return true;
	}

}