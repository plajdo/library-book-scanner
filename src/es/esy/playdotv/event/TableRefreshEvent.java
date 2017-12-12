package es.esy.playdotv.event;

import java.util.EventObject;

public class TableRefreshEvent extends EventObject{
	
	private static final long serialVersionUID = 1L;
	DataDialogEventOperation o;
	
	public TableRefreshEvent(Object arg0, DataDialogEventOperation o){
		super(arg0);
		this.o = o;
	}
	
	public DataDialogEventOperation getOperation(){
		return o;
	}
	
}
