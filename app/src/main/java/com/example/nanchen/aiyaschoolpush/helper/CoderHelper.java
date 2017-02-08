package com.example.nanchen.aiyaschoolpush.helper;

import java.io.Closeable;

/**
 * 语法简化器
 * 
 * */
public class CoderHelper {
	public static void close(Closeable closable) {
		if (closable != null) {
			try {
				closable.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
