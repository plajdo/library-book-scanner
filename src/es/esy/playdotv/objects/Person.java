package es.esy.playdotv.objects;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String group;
	private String address;
	private int schoolYear;
	private ArrayList<Integer> books;
	
	Person(){
		
	}
	
	Person(String name, String group, String address, int schoolYear, int ID){
		this.name = name;
		this.group = group;
		this.address = address;
		this.schoolYear = schoolYear;
	}
	
	String getName(){
		return name;
	}
	
	String getGroup(){
		return group;
	}
	
	String getAddress(){
		return address;
	}
	
	int getSchoolYearP1(){
		return schoolYear;
	}
	
	int getSchoolYearP2(){
		return schoolYear + 1;
	}
	
	void setName(String name){
		this.name = name;
	}
	
	void setGroup(String group){
		this.group = group;
	}
	
	void setAddress(String address){
		this.address = address;
	}
	
	void setSchoolYear(int schoolYearP1){
		this.schoolYear = schoolYearP1;
	}
	
	void addBook(int bookID){
		books.add(bookID);
	}
	
	void removeBook(int bookID){
		books.remove(bookID);
	}
	
	ArrayList<Integer> getBooks(){
		return books;
	}
	
	int getID(){
		return ID;
	}
	
	void setID(int ID){
		this.ID = ID;
	}
	
}
