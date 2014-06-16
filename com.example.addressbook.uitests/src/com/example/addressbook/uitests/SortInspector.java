package com.example.addressbook.uitests;

public class SortInspector {

	private Object lastObject = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void next(Object object) {
		if (lastObject != null) {
			if (lastObject instanceof Comparable<?> && object instanceof Comparable<?>) {
				if (((Comparable) lastObject).compareTo(object) > 0) {
					throw new RuntimeException("Wrong order: " + lastObject + ", " + object);
				}
			} else {
				throw new RuntimeException("Objects not comparable: " + lastObject + ", " + object);
			}
		}

		lastObject = object;
	}
}
