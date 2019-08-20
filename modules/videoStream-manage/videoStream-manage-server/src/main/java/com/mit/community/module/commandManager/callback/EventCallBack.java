package com.mit.community.module.commandManager.callback;

import com.mit.community.module.commandManager.callback.EventCallBackType;
import com.mit.community.module.commandManager.data.CommandTasker;

/**
 * 事件回调
 * @author eguid
 *
 */
public interface EventCallBack {
	
	/**
	 * 命令行执行开始事件
	 * @param t -事件类型
	 * @param tasker -任务信息
	 */
	boolean callback(EventCallBackType t, CommandTasker tasker);
}
