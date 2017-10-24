package es.esy.playdotv.objects;

import java.util.ArrayList;

public class Student implements Person{
	
	private static final long serialVersionUID = 1L;
	private String ID;
	private String name;
	private String group;
	private int schoolYear;
	private ArrayList<Paper> papers;
	
	public Student(String id){
		this.ID = id;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public int getSchoolYear() {
		return schoolYear;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Override
	public ArrayList<Paper> getPapers() {
		return papers;
	}

	@Override
	public boolean hasPaper(Paper p) {
		return papers.contains(p);
	}

	@Override
	public void addPaper(Paper p) {
		papers.add(p);
	}

	@Override
	public void removePaper(Paper p) {
		papers.remove(p);
	}
	
}