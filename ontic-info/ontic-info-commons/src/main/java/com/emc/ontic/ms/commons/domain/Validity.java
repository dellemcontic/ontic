package com.emc.ontic.ms.commons.domain;

public class Validity {
	
	private String start;
	
	private String end;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Validity [start=" + start + ", end=" + end + "]";
	}

}
