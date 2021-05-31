package model.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author CAILLEUX, GODIN, ILOO LIANDJA
 *
 */
public abstract class TabularObjectBuilder {

	public abstract Object[] toArray();
	
	/**
	 * Get table header
	 * @return table header
	 */
	public static String[] getHeader() {
		return null;
	}
	
	/**
	 * Get table columns width
	 * @return table columns width
	 */
	public static List<Integer> getColumnsWidth() {
		return null;
	}
	
	/**
	 * Get filter facets
	 * @return filter facets
	 */
	public static List<String> getFacets() {
		List<String> facets = new ArrayList<>();
		facets.add("Tout");
		return facets;
	}
}
