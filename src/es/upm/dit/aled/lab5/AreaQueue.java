package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	
	private Queue <Patient> waitQueue = new LinkedList<>();
	
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
	}
	
	@Override
	public synchronized void enter(Patient p) {
		waitQueue.add(p);
		boolean haEsperado = false;
		if(numPatients >= capacity  || waitQueue.peek() != p) {
			waiting++;
			haEsperado = true;
		}
		while(numPatients >= capacity || waitQueue.peek() != p) {
			try {
				wait();
			}catch(InterruptedException e) {}
		}
		if(haEsperado) {
			waiting--;
		}
		waitQueue.remove(p);
		notifyAll();
		numPatients++;
	}
}
