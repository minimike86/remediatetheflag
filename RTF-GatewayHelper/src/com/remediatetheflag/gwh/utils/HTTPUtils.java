/*
 *  
 * REMEDIATE THE FLAG
 * Copyright 2018 - Andrea Scaduto 
 * remediatetheflag@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.remediatetheflag.gwh.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.remediatetheflag.gwh.model.FileResponse;

public class HTTPUtils {

	public static String sendGet(String url){
		StringBuffer response = new StringBuffer();
		try{
			String USER_AGENT = "Mozilla/5.0 - RF";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			return null;
		}
		return response.toString();
	}
	public static String sendPost(String baseHost, String body) {
		URL obj;
		StringBuffer response = new StringBuffer();
		try {
			obj = new URL("http://"+baseHost+"/results/handler");

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json"); 
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();
			System.out.println("#Sending 'POST' request to URL : " + obj.toString());
			System.out.println("Post parameters : " + body);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			return null;
		}
		return response.toString();
	}
	public static FileResponse sendFilePost(String baseHost, String body) {
		URL obj;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			obj = new URL("http://"+baseHost+"/results/handler");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json"); 
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();
			System.out.println("#Sending 'POST' request to URL : " + obj.toString());
			System.out.println("Post parameters : " + body);
			InputStream is = con.getInputStream();
            String disposition = con.getHeaderField("Content-Disposition");
            String contentType = con.getContentType();
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			FileResponse fr = new FileResponse();
			fr.setContentDisposition(disposition);
			fr.setContentType(contentType);
			fr.setFileContent(buffer.toByteArray());
			return fr;
		} catch (IOException e) {
			return null;
		}
		
	}
}
