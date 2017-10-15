package com.piaoniu.biz;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/8/11
 *         Time: 上午10:29
 */
@Data
@AllArgsConstructor
public class SslConfig {

	private String certBase64;
	private String password;
}
