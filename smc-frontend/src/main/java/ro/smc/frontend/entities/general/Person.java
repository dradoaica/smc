package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import ro.smc.frontend.rules.Email;

@Entity
@Table(name = "Person")
@NamedNativeQuery(name = "Person.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'Person', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p ORDER BY p.firstName, p.lastName")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PersonId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer personId;

	@Column(name = "E_Mail", length = 255)
	@Size(max = 255)
	@Email
	private String e_Mail;

	@Column(name = "FirstName", nullable = false, length = 150)
	@NotNull
	@Size(max = 150)
	private String firstName;

	@Column(name = "LastName", nullable = false, length = 150)
	@NotNull
	@Size(max = 150)
	private String lastName;

	@Column(name = "MobilePhone", length = 50)
	@Size(max = 50)
	private String mobilePhone;

	@ManyToOne
	@JoinColumn(name = "GenderId")
	private DictionaryItem gender;

	@ManyToOne
	@JoinColumn(name = "RegisteredCityId")
	private City registeredCity;

	@ManyToOne
	@JoinColumn(name = "RegisteredDistrictId")
	private City registeredDistrict;

	@ManyToOne
	@JoinColumn(name = "CitizenshipId")
	private City citizenship;

	public Person() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getE_Mail() {
		return this.e_Mail;
	}

	public void setE_Mail(String e_Mail) {
		this.e_Mail = e_Mail;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public DictionaryItem getGender() {
		return this.gender;
	}

	public void setGender(DictionaryItem gender) {
		this.gender = gender;
	}

	public City getRegisteredCity() {
		return this.registeredCity;
	}

	public void setRegisteredCity(City registeredCity) {
		this.registeredCity = registeredCity;
	}

	public City getRegisteredDistrict() {
		return this.registeredDistrict;
	}

	public void setRegisteredDistrict(City registeredDistrict) {
		this.registeredDistrict = registeredDistrict;
	}

	public City getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(City citizenship) {
		this.citizenship = citizenship;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (personId != null ? personId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Person))
			return false;

		Person other = (Person) object;
		if ((this.personId == null && other.personId != null)
				|| (this.personId != null && !this.personId.equals(other.personId)))
			return false;

		return true;
	}

}