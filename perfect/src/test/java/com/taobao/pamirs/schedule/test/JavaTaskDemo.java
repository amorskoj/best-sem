package com.taobao.pamirs.schedule.test;

import com.taobao.pamirs.schedule.strategy.IStrategyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaTaskDemo implements IStrategyTask,Runnable {
	protected static transient Logger log = LoggerFactory.getLogger(JavaTaskDemo.class);


    private String parameter;
    private boolean stop = false;
	public void initialTaskParameter(String strategyName,String taskParameter) {
		parameter = taskParameter;
		new Thread(this).start();
	}

	@Override
	public void stop(String strategyName) throws Exception {
		this.stop = true;
	}

	@Override
	public void run() {
		while(!stop){
			log.error("ִ������"  + this.parameter);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
