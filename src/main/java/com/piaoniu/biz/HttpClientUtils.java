package com.piaoniu.biz;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * @author code4crafter@gmail.com
 *         Date: 15/11/18
 *         Time: 下午2:54
 */
public class HttpClientUtils {

	public static final int TIMEOUT = 3000;
	public static final int RETRY_COUNT = 3;

	public static HttpClient newHttpClient() {
		return HttpClientBuilder.create()
				.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(false).setTcpNoDelay(true).build())
				.setDefaultRequestConfig(
						RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
								.setSocketTimeout(TIMEOUT).build())
				.setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true))
				.build();
	}

	public static HttpClient newHttpClient(int timeOut) {
		return HttpClientBuilder.create()
				.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(false).setTcpNoDelay(true).build())
				.setDefaultRequestConfig(
						RequestConfig.custom().setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut)
								.setSocketTimeout(timeOut).build())
				.setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true))
				.build();
	}

	public static HttpClient newHttpClient(int timeOut, SslConfig sslConfig)
			throws Exception {
		if (sslConfig == null) {
			return newHttpClient(timeOut);
		}
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decodeBase64(sslConfig.getCertBase64()));
		try {
			keyStore.load(stream, sslConfig.getPassword().toCharArray());
		} finally {
			stream.close();
		}
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, sslConfig.getPassword().toCharArray())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1" },
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		return httpclient;
	}

	public static PnHttpClient newPnHttpClient(int timeOut) {
		return new PnHttpClient(newHttpClient(timeOut));
	}

	public static PnHttpClient newPnHttpClient(int timeOut, SslConfig sslConfig) throws Exception {
		return new PnHttpClient(newHttpClient(timeOut, sslConfig));
	}

	public static PnHttpClient newPnHttpClient() {
		return newPnHttpClient(TIMEOUT);
	}

}
