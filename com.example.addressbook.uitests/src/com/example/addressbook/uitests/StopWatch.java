package com.example.addressbook.uitests;

public class StopWatch {

	private final String name;
	private final long startTime;

	public StopWatch(String name) {
		this.name = name;
		this.startTime = System.currentTimeMillis();
	}

	public void assertDurationLessThanMs(long maxDuration) {
		long duration = getDuration();
		if (duration > maxDuration) {
			throw new AssertionError(name + " was " + duration + " ms, > than " + maxDuration
					+ " ms");
		}
	}

	public long getDuration() {
		return System.currentTimeMillis() - startTime;
	}

}
