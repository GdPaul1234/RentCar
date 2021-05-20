package model.enums;

public enum TypeCarburant {
	GAZOLE,
	ESSENCE_SP95,
	ESSENCE_SP98,
	GPL,
	ELECTRIQUE;

	public static TypeCarburant[] getValues() {
		return TypeCarburant.GPL.getDeclaringClass().getEnumConstants();
	}
}
