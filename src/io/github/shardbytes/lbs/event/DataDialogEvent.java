package io.github.shardbytes.lbs.event;

import java.util.EventObject;

public class DataDialogEvent extends EventObject{
	
	private static final long serialVersionUID = 1L;
	DataDialogEventOperation o;
	
	public DataDialogEvent(Object arg0, DataDialogEventOperation o){
		super(arg0);
		this.o = o;
	}
	
	public DataDialogEventOperation getOperation(){
		return o;
	}
	
}
