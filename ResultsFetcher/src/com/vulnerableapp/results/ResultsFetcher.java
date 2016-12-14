package com.vulnerableapp.results;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultsFetcher extends HttpServlet {

	private static final long serialVersionUID = -7268029217754403743L;
	private ZipHelper zipArchive;
	private final String USER_AGENT = "Mozilla/5.0 - RF";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("usr");
		String password = request.getParameter("pwd");
		System.out.print("Request Received");

		if(ConfigurationUtil.getPassword().equals(password) && ConfigurationUtil.getUsername().equals(username)){
			System.out.print("Credentials Valid");
			zipArchive = new ZipHelper();

			// selfcheck, this could fail if eclipse/tomcat is not running
			String json;
			try {		
				json = sendGet("http://localhost:8050/check");
				File selfcheck = new File("/home/rtf/Desktop/RTF/selfcheck.json");

				if (!selfcheck.exists()) {
					selfcheck.createNewFile();
				}
				FileWriter fw = new FileWriter(selfcheck.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(json);
				bw.close();
				zipArchive.addToFileList(new File("/home/rtf/Desktop/RTF/selfcheck.json"));
			} catch (Exception e) {
				e.printStackTrace();
			}


			// instance info

			String instanceId = retrieveInstanceId();
			String instanceIp = retrieveInstancePublicIP();

			File instanceInfo = new File("/home/rtf/Desktop/RTF/instanceInfo.txt");

			if (!instanceInfo.exists()) {
				instanceInfo.createNewFile();
			}

			FileWriter fw = new FileWriter(instanceInfo.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("InstanceId:"+instanceId+"\nInstanceIP:"+instanceIp);
			bw.close();

			zipArchive.addToFileList(new File("/home/rtf/Desktop/RTF/instanceInfo.txt"));

			// log
			zipArchive.addToFileList(new File("/home/rtf/Desktop/RTF/rtf.log"));
			
			// diff
			performSourceDiff();
			
			//prepare archive
			zipArchive.zipIt("/tmp/rtf_"+instanceId+"_"+instanceIp.replace(".", "-"));

			
			//send archive
			File zippedFile = new File("/tmp/rtf_"+instanceId+"_"+instanceIp.replace(".", "-"));

			response.setContentType("application/octet-stream");
			response.setContentLength((int) zippedFile.length());
			response.setHeader( "Content-Disposition", String.format("attachment; filename=\"%s\"", zippedFile.getName()+".zip"));

			OutputStream out = response.getOutputStream();
			try (FileInputStream in = new FileInputStream(zippedFile)) {
				byte[] buffer = new byte[4096];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			}
			out.flush();
		}
	}
	
	@SuppressWarnings("resource")
	private void performSourceDiff(){
		StringBuilder strOut = new StringBuilder();
		try {
			String [] command = new String[]{"sh", "-c", "diff -r -u /home/rtf/Desktop/RTF/VApp/ /home/rtf/Desktop/RTF/Reset/ | egrep -vE '(^Only in|^Binary files)'"};
			Process proc = Runtime.getRuntime().exec(command);
			int result = proc.waitFor();
			if (result != 0) {
				System.out.println("process error: " + result);
			}
			InputStream in = (result == 0) ? proc.getInputStream() : proc.getErrorStream();
			int c;
			while ((c = in.read()) != -1) {
				strOut.append((char) c);
			}
			File sourceDiff = new File("/home/rtf/Desktop/RTF/sourceDiff.txt");

			if (!sourceDiff.exists()) {
				sourceDiff.createNewFile();
			}

			FileWriter fw = new FileWriter(sourceDiff.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(strOut.toString());
			bw.close();
			zipArchive.addToFileList(new File("/home/rtf/Desktop/RTF/sourceDiff.txt"));

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	private static String retrieveInstanceId() {
		String ec2Id = null;
		String inputLine;
		URL ec2MetaData;
		try {
			ec2MetaData = new URL("http://169.254.169.254/latest/meta-data/instance-id");
			URLConnection ec2Md = ec2MetaData.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(ec2Md.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				ec2Id = inputLine;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ec2Id;
	}

	private static String retrieveInstancePublicIP() throws IOException {
		String ec2IPv4 = null;
		String inputLine;
		URL ec2MetaData = new URL("http://169.254.169.254/latest/meta-data/public-ipv4");
		URLConnection ec2Md = ec2MetaData.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(ec2Md.getInputStream()));
		while ((inputLine = in.readLine()) != null) {
			ec2IPv4 = inputLine;
		}
		in.close();
		return ec2IPv4;
	}
}