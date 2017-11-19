package com.universalbank.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class RESTInvoker {

	public static <T> T doGet(Class<T> returnClazz, String url, String accept) {
		try {
			T t=returnClazz.newInstance();
			ClientRequest request = new ClientRequest(url);
			request.accept(accept);
			ClientResponse<T> response = request.get(returnClazz);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			t=response.getEntity();
			return t;


		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}
	
	public static <T> T doPost(Class<T> returnClazz, String url, String accept,String body,Object payload) {
		try {

			ClientRequest request = new ClientRequest(
				url);
			request.accept(accept);

			
			request.body(body, payload);

			ClientResponse<T> response = request.post(returnClazz);

			return response.getEntity();
			

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return null;
	}
}
