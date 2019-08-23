package com.mit.community.entity;

import java.util.List;

public class FaceComparisonInterfaceData {
	private List<ComparisonInterfaceData> comparisonInterfaceData;
	private int count;
	
	
	
	public List<ComparisonInterfaceData> getComparisonInterfaceData() {
		return comparisonInterfaceData;
	}
	public void setComparisonInterfaceData(
			List<ComparisonInterfaceData> comparisonInterfaceData) {
		this.comparisonInterfaceData = comparisonInterfaceData;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
