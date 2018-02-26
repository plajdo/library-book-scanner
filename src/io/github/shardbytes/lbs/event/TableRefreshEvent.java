package io.github.shardbytes.lbs.event;

import java.util.EventObject;

public class TableRefreshEvent extends EventObject{
	
	private static final long serialVersionUID = 1L;
	TableRefreshEventOperation o;
	
	public TableRefreshEvent(Object arg0, TableRefreshEventOperation o){
		super(arg0);
		this.o = o;
	}
	
	public TableRefreshEventOperation getOperation(){
		return o;
	}
	
}
