package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "PasswordRule")
@NamedNativeQuery(name = "PasswordRule.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'PasswordRule', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "PasswordRule.findAll", query = "SELECT p FROM PasswordRule p ORDER BY p.passwordRuleName")
public class PasswordRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PasswordRuleId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer passwordRuleId;

	@Column(name = "AllowChangePassword", length = 1)
	private Boolean allowChangePassword;

	@Column(name = "MinAlphaCharNumber", length = 4, precision = 10, scale = 0)
	private Integer minAlphaCharNumber;

	@Column(name = "MinDigitNumber", length = 4, precision = 10, scale = 0)
	private Integer minDigitNumber;

	@Column(name = "MinLowerCaseNumber", length = 4, precision = 10, scale = 0)
	private Integer minLowerCaseNumber;

	@Column(name = "MinSpecialCharNumber", length = 4, precision = 10, scale = 0)
	private Integer minSpecialCharNumber;

	@Column(name = "MinUpperCaseNumber", length = 4, precision = 10, scale = 0)
	private Integer minUpperCaseNumber;

	@Column(name = "PasswordMinLen", length = 4, precision = 10, scale = 0)
	private Integer passwordMinLen;

	@Column(name = "PasswordRuleCode", length = 25)
	@Size(max = 25)
	private String passwordRuleCode;

	@Column(name = "PasswordRuleDescription", length = 1000)
	@Size(max = 1000)
	private String passwordRuleDescription;

	@Column(name = "PasswordRuleName", length = 100)
	@Size(max = 100)
	private String passwordRuleName;

	@Column(name = "SpecialCharList", length = 100)
	@Size(max = 100)
	private String specialCharList;

	public PasswordRule() {
	}

	public Integer getPasswordRuleId() {
		return this.passwordRuleId;
	}

	public void setPasswordRuleId(Integer passwordRuleId) {
		this.passwordRuleId = passwordRuleId;
	}

	public Boolean getAllowChangePassword() {
		return this.allowChangePassword;
	}

	public void setAllowChangePassword(Boolean allowChangePassword) {
		this.allowChangePassword = allowChangePassword;
	}

	public Integer getMinAlphaCharNumber() {
		return this.minAlphaCharNumber;
	}

	public void setMinAlphaCharNumber(Integer minAlphaCharNumber) {
		this.minAlphaCharNumber = minAlphaCharNumber;
	}

	public Integer getMinDigitNumber() {
		return this.minDigitNumber;
	}

	public void setMinDigitNumber(Integer minDigitNumber) {
		this.minDigitNumber = minDigitNumber;
	}

	public Integer getMinLowerCaseNumber() {
		return this.minLowerCaseNumber;
	}

	public void setMinLowerCaseNumber(Integer minLowerCaseNumber) {
		this.minLowerCaseNumber = minLowerCaseNumber;
	}

	public Integer getMinSpecialCharNumber() {
		return this.minSpecialCharNumber;
	}

	public void setMinSpecialCharNumber(Integer minSpecialCharNumber) {
		this.minSpecialCharNumber = minSpecialCharNumber;
	}

	public Integer getMinUpperCaseNumber() {
		return this.minUpperCaseNumber;
	}

	public void setMinUpperCaseNumber(Integer minUpperCaseNumber) {
		this.minUpperCaseNumber = minUpperCaseNumber;
	}

	public Integer getPasswordMinLen() {
		return this.passwordMinLen;
	}

	public void setPasswordMinLen(Integer passwordMinLen) {
		this.passwordMinLen = passwordMinLen;
	}

	public String getPasswordRuleCode() {
		return this.passwordRuleCode;
	}

	public void setPasswordRuleCode(String passwordRuleCode) {
		this.passwordRuleCode = passwordRuleCode;
	}

	public String getPasswordRuleDescription() {
		return this.passwordRuleDescription;
	}

	public void setPasswordRuleDescription(String passwordRuleDescription) {
		this.passwordRuleDescription = passwordRuleDescription;
	}

	public String getPasswordRuleName() {
		return this.passwordRuleName;
	}

	public void setPasswordRuleName(String passwordRuleName) {
		this.passwordRuleName = passwordRuleName;
	}

	public String getSpecialCharList() {
		return this.specialCharList;
	}

	public void setSpecialCharList(String specialCharList) {
		this.specialCharList = specialCharList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (passwordRuleId != null ? passwordRuleId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PasswordRule))
			return false;

		PasswordRule other = (PasswordRule) object;
		if ((this.passwordRuleId == null && other.passwordRuleId != null)
				|| (this.passwordRuleId != null && !this.passwordRuleId.equals(other.passwordRuleId)))
			return false;

		return true;
	}

}