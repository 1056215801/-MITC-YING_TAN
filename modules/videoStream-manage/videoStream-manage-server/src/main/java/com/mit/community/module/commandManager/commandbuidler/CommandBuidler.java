package com.mit.community.module.commandManager.commandbuidler;

/**
 * 流式命令行构建器
 * 
 * @author eguid
 */
public interface CommandBuidler {

	/**
	 * 创建命令行
	 * 
	 * @param root
	 *            -命令行运行根目录或FFmpeg可执行文件安装目录
	 * @return
	 */
	com.mit.community.module.commandManager.commandbuidler.CommandBuidler create(String root);

	/**
	 * 创建默认根目录或默认FFmpeg可执行文件安装目录
	 * 
	 * @return
	 */
	com.mit.community.module.commandManager.commandbuidler.CommandBuidler create();

	/**
	 * 累加键-值命令
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	com.mit.community.module.commandManager.commandbuidler.CommandBuidler add(String key, String val);

	/**
	 * 累加命令
	 * 
	 * @param val
	 * @return
	 */
	com.mit.community.module.commandManager.commandbuidler.CommandBuidler add(String val);

	/**
	 * 生成完整命令行
	 * 
	 * @return
	 */
	com.mit.community.module.commandManager.commandbuidler.CommandBuidler build();
	
	/**
	 * 获取已经构建好的命令行
	 * @return
	 */
	String get();
}
