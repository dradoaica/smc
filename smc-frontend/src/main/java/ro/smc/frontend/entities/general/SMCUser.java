package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import ro.smc.frontend.entities.general.Person;

import java.sql.Timestamp;

@Entity
@Table(name = "SMCUser")
@NamedNativeQuery(name = "SMCUser.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'SMCUser', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({ @NamedQuery(name = "SMCUser.findAll", query = "SELECT e FROM SMCUser e ORDER BY e.userName"),
		@NamedQuery(name = "SMCUser.findByUserName", query = "SELECT e FROM SMCUser e WHERE e.userName = :userName"),
		@NamedQuery(name = "SMCUser.findByCookieUUID", query = "SELECT e FROM SMCUser e WHERE e.cookieUUID = :cookieUUID") })
public class SMCUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer userId;

	@Column(name = "Inactive", length = 1)
	private Boolean inactive;

	@Column(name = "Password", length = 255)
	@Size(max = 255)
	private String password;

	@Transient
	private String newPassword;

	@Column(name = "CookieUUID", length = 255)
	@Size(max = 255)
	private String cookieUUID;

	@Column(name = "PasswordChangedByAdmin", length = 1)
	private Boolean passwordChangedByAdmin;

	@Column(name = "PasswordCreationDate", length = 8)
	private Timestamp passwordCreationDate;

	@Column(name = "UserName", nullable = false, length = 200)
	@Size(max = 200)
	private String userName;

	@ManyToOne
	@JoinColumn(name = "PasswordRuleId")
	private PasswordRule passwordRule;

	@ManyToOne
	@JoinColumn(name = "PersonId")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "UserGroupId")
	private UserGroup userGroup;

	public SMCUser() {
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getInactive() {
		return this.inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCookieUUID() {
		return this.cookieUUID;
	}

	public void setCookieUUID(String cookieUUID) {
		this.cookieUUID = cookieUUID;
	}

	public Boolean getPasswordChangedByAdmin() {
		return this.passwordChangedByAdmin;
	}

	public void setPasswordChangedByAdmin(Boolean passwordChangedByAdmin) {
		this.passwordChangedByAdmin = passwordChangedByAdmin;
	}

	public Timestamp getPasswordCreationDate() {
		return this.passwordCreationDate;
	}

	public void setPasswordCreationDate(Timestamp passwordCreationDate) {
		this.passwordCreationDate = passwordCreationDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PasswordRule getPasswordRule() {
		return this.passwordRule;
	}

	public void setPasswordRule(PasswordRule passwordRule) {
		this.passwordRule = passwordRule;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userId != null ? userId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof SMCUser))
			return false;

		SMCUser other = (SMCUser) object;
		if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId)))
			return false;

		return true;
	}

}