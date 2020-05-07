package ro.smc.frontend.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "DictionaryItem")
@NamedNativeQuery(name = "DictionaryItem.getNextId", query = "DECLARE @RC int; DECLARE @NextId int; EXECUTE @RC = usp_GetNextId 'DictionaryItem', @NextId OUTPUT; SELECT @NextId")
@NamedQueries({
		@NamedQuery(name = "DictionaryItem.findAll", query = "SELECT d FROM DictionaryItem d ORDER BY d.dictionaryItemCode"),
		@NamedQuery(name = "DictionaryItem.findAllGenders", query = "SELECT d FROM DictionaryItem d WHERE d.dictionary.dictionaryCode = '-100' ORDER BY d.dictionaryItemName"),
		@NamedQuery(name = "DictionaryItem.findAllCityTypes", query = "SELECT d FROM DictionaryItem d WHERE d.dictionary.dictionaryCode = '-200' ORDER BY d.dictionaryItemName"),
		@NamedQuery(name = "DictionaryItem.findAllActivityLogTypes", query = "SELECT d FROM DictionaryItem d WHERE d.dictionary.dictionaryCode = '-300' ORDER BY d.dictionaryItemName"),
		@NamedQuery(name = "DictionaryItem.findAllPackageStates", query = "SELECT d FROM DictionaryItem d WHERE d.dictionary.dictionaryCode = '-400' ORDER BY d.dictionaryItemName") })
public class DictionaryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DictionaryItemId", nullable = false, length = 4, precision = 10, scale = 0)
	@NotNull
	private Integer dictionaryItemId;

	@Column(name = "DictionaryItemCode", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	private String dictionaryItemCode;

	@Column(name = "DictionaryItemName", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	private String dictionaryItemName;

	@ManyToOne
	@JoinColumn(name = "DictionaryId", nullable = false)
	@NotNull
	private Dictionary dictionary;

	public DictionaryItem() {
	}

	public Integer getDictionaryItemId() {
		return this.dictionaryItemId;
	}

	public void setDictionaryItemId(Integer dictionaryItemId) {
		this.dictionaryItemId = dictionaryItemId;
	}

	public String getDictionaryItemCode() {
		return this.dictionaryItemCode;
	}

	public void setDictionaryItemCode(String dictionaryItemCode) {
		this.dictionaryItemCode = dictionaryItemCode;
	}

	public String getDictionaryItemName() {
		return this.dictionaryItemName;
	}

	public void setDictionaryItemName(String dictionaryItemName) {
		this.dictionaryItemName = dictionaryItemName;
	}

	public Dictionary getDictionary() {
		return this.dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (dictionaryItemId != null ? dictionaryItemId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DictionaryItem))
			return false;

		DictionaryItem other = (DictionaryItem) object;
		if ((this.dictionaryItemId == null && other.dictionaryItemId != null)
				|| (this.dictionaryItemId != null && !this.dictionaryItemId.equals(other.dictionaryItemId)))
			return false;

		return true;
	}

}