package com.mit.gate.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mit.gate.utils.DBLog;
/**
* 启动日志保存线程
* @author shuyy
* @date 2018年8月8日
 */
@Component
public class LogThreadStartCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		DBLog.getInstance().start();
	}

}
