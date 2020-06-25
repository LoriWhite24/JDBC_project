package com.cognixia.jump.jdbc.project;

public class StudentInfo {
	public enum Standing {
		FRESHMAN(0, 29), SOPHMORE(30, 59), JUNIOR(60, 89), SENIOR(90, 120), SUPER_SENIOR(121, 150), ADVANCED_SENIOR(151, Integer.MAX_VALUE);
		
		public final int maxCredits, minCredits;
		
		private Standing(int min, int max) {
			this.minCredits = min;
			this.maxCredits = max;
		}
	}
}


