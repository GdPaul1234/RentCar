package model.interfaces;

import java.util.List;

public abstract class TabularObjectBuilder {

	public abstract Object[] toArray();
	
	public static String[] getHeader() {
		return null;
	}
	
	public static List<Integer> getColumnsWidth() {
		return null;
	}
}
