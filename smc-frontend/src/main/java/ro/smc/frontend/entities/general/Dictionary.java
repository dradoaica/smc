package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Dictionary")
@NamedNativeQuery(name = "Dictionary.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'Dictionary', @NextId OUTPUT; SELECT @NextId")
@NamedQuery(name = "Dictionary.findAll", query = "SELECT d FROM Dictionary d ORDER BY d.dictionaryCode")
public class Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DictionaryId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer dictionaryId;

	@Column(name = "DictionaryCode", nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	private String dictionaryCode;

	@Column(name = "DictionaryName", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	private String dictionaryName;

	@OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
	private List<DictionaryItem> dictionaryItems;

	public Dictionary() {
	}

	public Integer getDictionaryId() {
		return this.dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionaryCode() {
		return this.dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

	public String getDictionaryName() {
		return this.dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public List<DictionaryItem> getDictionaryItems() {
		return this.dictionaryItems;
	}

	public void setDictionaryItems(List<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}

	public DictionaryItem addDictionaryItem(DictionaryItem dictionaryItem) {
		getDictionaryItems().add(dictionaryItem);
		dictionaryItem.setDictionary(this);

		return dictionaryItem;
	}

	public DictionaryItem removeDictionaryItem(DictionaryItem dictionaryItem) {
		getDictionaryItems().remove(dictionaryItem);
		dictionaryItem.setDictionary(null);

		return dictionaryItem;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (dictionaryId != null ? dictionaryId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Dictionary))
			return false;

		Dictionary other = (Dictionary) object;
		if ((this.dictionaryId == null && other.dictionaryId != null)
				|| (this.dictionaryId != null && !this.dictionaryId.equals(other.dictionaryId)))
			return false;

		return true;
	}

}