package es.esy.playdotv.event;

import java.util.EventListener;

public interface DDEventListener extends EventListener{
	
	public void handleDataDialogEvent(DataDialogEvent evt);
	
}