package com.mycompany.beans;

public class Score {
	private int id;
	private int idMatch;
	//on choisit de mettre les set en type Integer car ils peuvent être null
	private Integer set1;
	private Integer set2;
	private Integer set3;
	private Integer set4;
	private Integer set5;
	
	
	
	public Score() {
		super();
	}
	public Score(int id, int idMatch, Integer set1, Integer set2, Integer set3, Integer set4, Integer set5) {
		super();
		this.id = id;
		this.idMatch = idMatch;
		this.set1 = set1;
		this.set2 = set2;
		this.set3 = set3;
		this.set4 = set4;
		this.set5 = set5;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdMatch() {
		return idMatch;
	}
	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}
	public Integer getSet1() {
		return set1;
	}
	public void setSet1(Integer set1) {
		this.set1 = set1;
	}
	public Integer getSet2() {
		return set2;
	}
	public void setSet2(Integer set2) {
		this.set2 = set2;
	}
	public Integer getSet3() {
		return set3;
	}
	public void setSet3(Integer set3) {
		this.set3 = set3;
	}
	public Integer getSet4() {
		return set4;
	}
	public void setSet4(Integer set4) {
		this.set4 = set4;
	}
	public Integer getSet5() {
		return set5;
	}
	public void setSet5(Integer set5) {
		this.set5 = set5;
	}
	
	

}
