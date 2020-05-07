package ro.smc.engine.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbObjectName implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Pattern REGEX_1 = Pattern.compile("^\\s*\\[(?<name>[^\\]]*)\\]\\s*");
	private static final Pattern REGEX_2 = Pattern.compile("^\\s*\\w+\\s*");
	private static final Pattern REGEX_DOT = Pattern.compile("^\\s*\\.\\s*");

	private String fragmentName;
	private String table;
	private String schema;

	public DbObjectName(String table) {
		if (table == "inserted" || table == "deleted") {
			schema = "";
			this.table = table;
		} else {
			schema = "dbo";
			this.table = table;
		}
	}

	public DbObjectName(String table, String fragmentName) {
		if (table == "inserted" || table == "deleted") {
			schema = "";
			this.table = table;
		} else {
			schema = "dbo";
			this.table = table;
		}
		this.fragmentName = fragmentName;
	}

	public DbObjectName(String schema, String table, String fragmentName) {
		if (table == "inserted" || table == "deleted") {
			this.schema = "";
			this.table = table;
		} else {
			this.schema = schema;
			this.table = table;
		}
		this.fragmentName = fragmentName;
	}

	public String getFragmentName() {
		return fragmentName;
	}

	public String getTable() {
		return table;
	}

	public String getSchema() {
		return schema;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof DbObjectName) {
			DbObjectName dbObjectName = (DbObjectName) obj;

			return dbObjectName.schema == schema && dbObjectName.table == table
					&& dbObjectName.fragmentName == fragmentName;
		} else
			return false;
	}

	@Override
	public String toString() {
		if (StringUtil.isNullOrEmpty(schema))
			return table;
		else
			return String.format("[%1$s].[%2$s]", schema, table);
	}

	public static DbObjectName Convert(String name) {
		if (name == null)
			return null;

		if (name.indexOf('[') < 0 && name.length() > 0) {
			String[] dotparts = name.split("\\.");
			if (dotparts.length == 2) {
				return new DbObjectName(dotparts[0], dotparts[1]);
			} else
				return new DbObjectName("dbo", name, null);
		}

		String[] parts = SplitObjectName(name);

		if (parts == null)
			return new DbObjectName("dbo", name);

		switch (parts.length) {
		case 1:
			return new DbObjectName("dbo", parts[0], null);
		case 2:
			return new DbObjectName(parts[0], parts[1], null);
		default:
			return new DbObjectName("dbo", name, null);
		}
	}

	public static String[] SplitObjectName(String name) {
		ArrayList<String> parts = new ArrayList<>();

		boolean expectDot = false;
		boolean expectPart = false;
		while (true) {
			Matcher m;
			if (!expectDot) {
				m = REGEX_1.matcher(name);
				if (m.find()) {
					parts.add(m.group("name").trim());
					if (name.length() == m.group("name").length() + 2)
						break;
					name = name.substring(m.group("name").length() + 2);
					expectDot = true;
					expectPart = false;
					continue;
				}

				m = REGEX_2.matcher(name);
				if (m.find()) {
					parts.add(m.group().trim());
					if (name.length() == m.group(0).length())
						break;
					name = name.substring(m.group(0).length());
					expectDot = true;
					expectPart = false;
					continue;
				}

				if (expectPart)
					return null;
			}

			if (expectDot) {
				m = REGEX_DOT.matcher(name);
				if (!m.find())
					return null;

				name = name.substring(m.group().length());
				expectPart = true;
				expectDot = false;
				continue;
			}

			return null;
		}

		return parts.stream().toArray(size -> new String[size]);
	}
}
