package com.streamit.others.jdbc;

import java.io.Serializable;

import com.streamit.application.utils.StringType;


@SuppressWarnings("serial")
public class Pagging implements Serializable{
    private int pageNo = 1;
    private int pageSize;
    private int[] rowsPerPageOption = {5,10,15,20,50,100};
    
    private int totalPage;
    private int totalRow;
    
    public Pagging(){
    }
	
    public Pagging(int pageNo, int pageSize){
    	this.pageNo = pageNo;
    	this.pageSize = pageSize;
    }
    
    public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public int getPageSize() {
		if(pageSize==0)
			return 10;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int[] getRowsPerPageOption() {
		return rowsPerPageOption;
	}
	public void setRowsPerPageOption(int[] rowsPerPageOption) {
		this.rowsPerPageOption = rowsPerPageOption;
	}
	public String getPageNoStr(){
		return StringType.getIntegerNumberPercentFormatted((double) pageNo);
	}
	public String getPageSizeStr(){
		return StringType.getIntegerNumberPercentFormatted((double) pageSize);
	}
	public String getTotalPageStr(){
		return StringType.getIntegerNumberPercentFormatted((double) totalPage);
	}
	public String getTotalRowStr(){
		return StringType.getIntegerNumberPercentFormatted((double) totalRow);
	}
}
