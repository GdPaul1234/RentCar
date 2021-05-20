package model.enums;

public enum TypeBoite {
	MANUELLE, AUTOMATIQUE;
	
	public static TypeBoite[] getValues() {
		return TypeBoite.MANUELLE.getDeclaringClass().getEnumConstants();
	}
}
