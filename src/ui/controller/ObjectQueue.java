package ui.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ObjectQueue {

	protected final List<Object> objectQueue=new LinkedList<Object>();
	protected volatile boolean consuming=false;
	protected volatile boolean stopRequested=false;
	protected ThreadPoolExecutor consumerExecutor=null;
	
	synchronized protected void startConsuming(){
		
		if(consuming==false) {
		
			consuming=true;
			stopRequested=false;		
		}
		
		if(consumerExecutor==null){
			consumerExecutor=new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS	, new LinkedBlockingQueue<Runnable>());
		}
		 consumerExecutor.execute(new Runnable (){

				@Override
				public void run() {
					consumeObjects();			
				}
		 });	
	}
//********************************************************************************************************************

	protected void consumeObjects(){
	
		Object popObject=null;
		while(stopRequested==false){
			synchronized(objectQueue){
				while(objectQueue.isEmpty()&&stopRequested==false){
					
					try{
						objectQueue.wait();
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
				if(stopRequested==true) break;
				popObject=objectQueue.remove(0);
				
			}
			if(popObject!=null){
				try{
					processObject(popObject);
				} catch(Exception ex){
					ex.printStackTrace();
				}
			}
			popObject=null;
		}
		consuming=false;
	}
//********************************************************************************************************************
	
	abstract protected void processObject(Object object);
	
	protected void queueObject(Object object){
		if(object==null) return;
		
		synchronized(objectQueue){
			objectQueue.add(object);
			objectQueue.notifyAll();
		}
	}
//********************************************************************************************************************
	
	protected void stopConsumption(){
		synchronized(objectQueue){
			stopRequested=true;
			objectQueue.notifyAll();
		}
	}	
//********************************************************************************************************************
	
	protected void clearQueue(){
		synchronized (objectQueue) {
			objectQueue.clear();
		}
	}
//********************************************************************************************************************
	
	protected void shutdown(){
		consumerExecutor.shutdown();
		stopConsumption();
		clearQueue();
	}
//********************************************************************************************************************
}
