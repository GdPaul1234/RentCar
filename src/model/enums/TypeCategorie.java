package model.enums;

import java.util.Arrays;

public enum TypeCategorie {
	ECONOMIQUE, CONFORT, LUXE;

	public static TypeCategorie[] getValues() {
		return TypeCategorie.ECONOMIQUE.getDeclaringClass().getEnumConstants();
	}

	public static TypeCategorie get(String categorie) {
		return Arrays.stream(getValues()).filter(v -> v.toString().equals(categorie)).findFirst().orElse(null);
	}

}
