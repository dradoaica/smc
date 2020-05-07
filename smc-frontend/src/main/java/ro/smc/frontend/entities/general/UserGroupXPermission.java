package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "UserGroupXPermission")
@NamedNativeQuery(name = "UserGroupXPermission.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'UserGroupXPermission', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "UserGroupXPermission.findAll", query = "SELECT u FROM UserGroupXPermission u")
public class UserGroupXPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserGroupXPermissionId")
	private Integer userGroupXPermissionId;

	@ManyToOne
	@JoinColumn(name = "PermissionId")
	private Permission permission;

	@ManyToOne
	@JoinColumn(name = "UserGroupId")
	private UserGroup userGroup;

	public UserGroupXPermission() {
	}

	public Integer getUserGroupXPermissionId() {
		return this.userGroupXPermissionId;
	}

	public void setUserGroupXPermissionId(Integer userGroupXPermissionId) {
		this.userGroupXPermissionId = userGroupXPermissionId;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
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
		hash += (userGroupXPermissionId != null ? userGroupXPermissionId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserGroupXPermission))
			return false;

		UserGroupXPermission other = (UserGroupXPermission) object;
		if ((this.userGroupXPermissionId == null && other.userGroupXPermissionId != null)
				|| (this.userGroupXPermissionId != null
						&& !this.userGroupXPermissionId.equals(other.userGroupXPermissionId)))
			return false;

		return true;
	}
}