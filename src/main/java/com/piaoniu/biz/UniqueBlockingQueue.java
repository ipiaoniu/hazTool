package com.piaoniu.biz;

import io.netty.util.internal.ConcurrentSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO:线程安全验证
 * @author code4crafter@gmail.com
 *         Date: 16/8/25
 *         Time: 下午4:25
 */
public class UniqueBlockingQueue<E> {

	private LinkedBlockingQueue<E> queue = new LinkedBlockingQueue<E>();

	private ConcurrentSet<E> set = new ConcurrentSet<E>();

	public void add(E e) {
		if (set.add(e)) {
			queue.add(e);
		}
	}

	public E poll() {
		E e = queue.poll();
		if (e != null) {
			set.remove(e);
		}
		return e;
	}

}
