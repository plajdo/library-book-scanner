package es.esy.playdotv.objects;

import java.util.ArrayList;

public interface Person{
	int getID();
	String getName();
	String getGroup();
	int getSchoolYear();
	ArrayList<Paper> getPapers();
	boolean hasPaper(Paper p);
	void addPaper(Paper p);
	void removePaper(Paper p);
	void setName(String name);
	void setGroup(String group);
	void setSchoolYear(int schoolYear);
	
}
