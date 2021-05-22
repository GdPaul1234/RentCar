package model.enums;

import java.util.Arrays;

public enum TypeBoite {
	MANUELLE, AUTOMATIQUE;
	
	public static TypeBoite[] getValues() {
		return TypeBoite.MANUELLE.getDeclaringClass().getEnumConstants();
	}
	
	public static TypeBoite get(String typeBoite) {
		return Arrays.stream(getValues()).filter(v -> v.toString().equals(typeBoite)).findFirst().orElse(null);
	}
}
