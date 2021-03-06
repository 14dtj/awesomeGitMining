package org.Server.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

public class HttpRequest {

	private static String token = "b6d4d30ba55a5f2166af787f1cacb762c235aaea";

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 ?name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;

		// 合成url
		URL realUrl = new URL(url + param);

		// System.out.println(url + param);

		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();

		/*
		 * connection.setConnectTimeout(10000);
		 * connection.setReadTimeout(10000);
		 */
		// System.out.println("Connected: "+realUrl);

		// 读取URL的响应
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result.append(line);
		}

		// System.out.println("Read: "+realUrl);
		in.close();

		return result.toString();
	}

	/**
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String sendGetWithAuth(String url, String param) throws IOException {

		String newUrl = "https://" + url + param;
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;

		URL myURL = null;
		try {
			myURL = new URL(newUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (myURL != null) {


			int times = 3;
			int responseCode = 0;
			while (true) {
				HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
				String authString = "Basic " + Base64.encodeBase64String((token + ":x-oauth-basic").getBytes());
				connection.setRequestProperty("Authorization", authString);
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setConnectTimeout(2000);
				connection.connect();
				responseCode = connection.getResponseCode();
				if (responseCode == 200) {
					in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line;
					while ((line = in.readLine()) != null) {
						result.append(line);
					}
					break;
				}
				times--;
				System.out.println(responseCode);
			}

		}
		return result.toString();

	}

	public static InputStream sendGetforStream(String url) throws IOException {

		// System.out.println(url + param);

		// 打开和URL之间的连接
		URLConnection connection = new URL(url).openConnection();

		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);

		InputStream inStream = connection.getInputStream();

		return inStream;
	}

	public static String sendGetViaAcceptHeader(String url, String param) throws IOException {
		String newUrl = "https://" + url + param;

		String result = "";
		BufferedReader in = null;

		URL myURL = null;
		try {
			myURL = new URL(newUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (myURL != null) {
			URLConnection connection = myURL.openConnection();
			String authString = "Basic " + Base64.encodeBase64String((token + ":x-oauth-basic").getBytes());
			connection.setRequestProperty("Authorization", authString);
			connection.setRequestProperty("Accept", "application/vnd.github.v3.star+json");

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}
		return result;
	}
}
