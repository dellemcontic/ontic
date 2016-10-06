package com.ontic.pgf.mitigation.interfaces;

import java.util.List;

public class TimingContraints
{
	private long activation_time;
	private long deactivation_time;
	private List<Boolean> recurrence;
	private List<String> time_frames;
	
	public long getActivation_time() {
		return activation_time;
	}
	public void setActivation_time(long activation_time) {
		this.activation_time = activation_time;
	}
	public long getDeactivation_time() {
		return deactivation_time;
	}
	public void setDeactivation_time(long deactivation_time) {
		this.deactivation_time = deactivation_time;
	}
	public List<Boolean> getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(List<Boolean> recurrence) {
		this.recurrence = recurrence;
	}
	public List<String> getTime_frames() {
		return time_frames;
	}
	public void setTime_frames(List<String> time_frames) {
		this.time_frames = time_frames;
	}
	
	
	
	
	
	
	
	
}