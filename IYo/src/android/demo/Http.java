package android.demo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class Http
{
	private final static String Charset = "utf-8";
	
	public static String HttpGet(String url)
	{
		String res = null;
		
		try
		{
			HttpGet httpRequest = new HttpGet(url);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode()==200)
			{
				res = EntityUtils.toString(httpResponse.getEntity(),Charset);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String HttpPost(String url, String data)
	{
		String res = null;

		try
		{
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httpRequest.setEntity(new StringEntity(data, Charset));
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode()==200)
			{
				res = EntityUtils.toString(httpResponse.getEntity(),Charset);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String HttpPost(String url, byte[] data)
	{
		String res = null;

		try
		{
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new ByteArrayEntity(data));
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode()==200)
			{
				res = EntityUtils.toString(httpResponse.getEntity());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static boolean HttpDownload(String url, String savePath)
	{
		boolean res = false;
		
		try
		{
			URL sourceUrl = new URL(url);
			URLConnection conn = sourceUrl.openConnection();  
			InputStream inputStream = conn.getInputStream();
	
			int fileSize = conn.getContentLength();
	
			FileOutputStream outputStream = new FileOutputStream(savePath);  
	
			byte[] buffer = new byte[1024];
			int downSize = 0;
			int readLen = 0;
			while (downSize < fileSize && readLen != -1)
			{
				readLen = inputStream.read(buffer);
				if (readLen > -1)
				{
					outputStream.write(buffer, 0, readLen);
					downSize = downSize + readLen;
	
				}
			}
			outputStream.close();
			
			res = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String HttpSOAP(String url, String action, String message)
	{
		String res = null;

		try
		{
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "text/xml; charset="+Charset);
			httpRequest.setHeader("SOAPAction", action);
			httpRequest.setEntity(new StringEntity(message, Charset));
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode()==200)
			{
				res = EntityUtils.toString(httpResponse.getEntity(),Charset);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
	}
}
