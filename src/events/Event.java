package events;

import java.util.ArrayList;
import boardobject.Cezmi;

/**
 * 
 * @author UC This interface gives a signature of 2 methods that must be
 *         implemented by the classing which implements Event interface.
 */
public interface Event {

	public void applyEvent(ArrayList<Cezmi> cezmis);

	public void finishEvent();
}
