package model.enums;

import java.util.Arrays;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public enum TypeCarburant {
	GAZOLE, ESSENCE_SP95, ESSENCE_SP98, GPL, ELECTRIQUE;

	public static TypeCarburant[] getValues() {
		return TypeCarburant.GPL.getDeclaringClass().getEnumConstants();
	}

	public static TypeCarburant get(String typeCarburant) {
		return Arrays.stream(getValues()).filter(v -> v.toString().equals(typeCarburant)).findFirst().orElse(null);
	}
}
