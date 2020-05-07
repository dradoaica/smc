package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "UserGroup")
@NamedNativeQuery(name = "UserGroup.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'UserGroup', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u ORDER BY u.userGroupName")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserGroupId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer userGroupId;

	@Column(name = "UserGroupName", nullable = false, length = 40)
	@NotNull
	@Size(max = 40)
	private String userGroupName;

	@OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserGroupXPermission> userGroupXPermissions;

	public UserGroup() {
	}

	public Integer getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public List<UserGroupXPermission> getUserGroupXPermissions() {
		return this.userGroupXPermissions;
	}

	public void setUserGroupXPermissions(List<UserGroupXPermission> userGroupXPermissions) {
		this.userGroupXPermissions = userGroupXPermissions;
	}

	public UserGroupXPermission addUserGroupXPermission(UserGroupXPermission userGroupXPermission) {
		getUserGroupXPermissions().add(userGroupXPermission);
		userGroupXPermission.setUserGroup(this);

		return userGroupXPermission;
	}

	public UserGroupXPermission removeUserGroupXPermission(UserGroupXPermission userGroupXPermission) {
		getUserGroupXPermissions().remove(userGroupXPermission);
		userGroupXPermission.setUserGroup(null);

		return userGroupXPermission;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userGroupId != null ? userGroupId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserGroup))
			return false;

		UserGroup other = (UserGroup) object;
		if ((this.userGroupId == null && other.userGroupId != null)
				|| (this.userGroupId != null && !this.userGroupId.equals(other.userGroupId)))
			return false;

		return true;
	}
}