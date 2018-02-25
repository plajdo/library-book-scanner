package io.github.shardbytes.lbs.event;

import java.util.EventListener;

@FunctionalInterface
public interface TableRefreshEventListener extends EventListener{
	
	public void handleTableRefreshEvent(TableRefreshEvent evt);
	
}
