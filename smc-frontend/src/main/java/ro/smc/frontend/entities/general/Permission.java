package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Permission")
@NamedNativeQuery(name = "Permission.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'Permission', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PermissionId")
	private Integer permissionId;

	@Column(name = "PermissionCode")
	private String permissionCode;

	@Column(name = "PermissionName")
	private String permissionName;

	public Permission() {
	}

	public Integer getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (permissionId != null ? permissionId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Permission))
			return false;

		Permission other = (Permission) object;
		if ((this.permissionId == null && other.permissionId != null)
				|| (this.permissionId != null && !this.permissionId.equals(other.permissionId)))
			return false;

		return true;
	}
}