package com.shopping.web.bg.bean;

import java.util.List;

public class PagingBean<T> {
	private List<String> titleList;
	private Long totalCount;
	private List<T> dataList;
	
	public PagingBean(){
		
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public List<String> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
	
	
	
}
