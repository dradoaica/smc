package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ActivityLog")
@NamedNativeQuery(name = "ActivityLog.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'ActivityLog', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "ActivityLog.findAll", query = "SELECT a FROM ActivityLog a")
public class ActivityLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ActivityLogId", nullable = false)
	@NotNull
	private Integer activityLogId;

	@ManyToOne
	@JoinColumn(name = "ActivityLogTypeId", nullable = false)
	@NotNull
	private DictionaryItem activityLogType;

	@Column(name = "Message", length = 2147483647)
	@Size(max = 2147483647)
	private String message;

	public ActivityLog() {
	}

	public Integer getActivityLogId() {
		return this.activityLogId;
	}

	public void setActivityLogId(Integer activityLogId) {
		this.activityLogId = activityLogId;
	}

	public DictionaryItem getActivityLogType() {
		return this.activityLogType;
	}

	public void setActivityLogType(DictionaryItem activityLogType) {
		this.activityLogType = activityLogType;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (activityLogId != null ? activityLogId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ActivityLog))
			return false;

		ActivityLog other = (ActivityLog) object;
		if ((this.activityLogId == null && other.activityLogId != null)
				|| (this.activityLogId != null && !this.activityLogId.equals(other.activityLogId)))
			return false;

		return true;
	}

}