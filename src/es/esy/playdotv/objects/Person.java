package es.esy.playdotv.objects;

import java.util.ArrayList;

public interface Person{
	public int getID();
	public String getName();
	public String getGroup();
	public int getSchoolYear();
	public ArrayList<Paper> getPapers();
	public boolean hasPaper(Paper p);
	public void addPaper(Paper p);
	public void removePaper(Paper p);
	public void setName(String name);
	public void setGroup(String group);
	public void setSchoolYear(int schoolYear);
	
}
