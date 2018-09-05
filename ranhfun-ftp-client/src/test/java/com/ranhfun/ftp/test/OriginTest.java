package com.ranhfun.ftp.test;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.IOException;

public class OriginTest {

	public static void main(String[] args) {
		FTPClient client = new FTPClient();
		client.setType(FTPClient.TYPE_BINARY);
		String site = "117.27.146.20";
		try {
			client.connect(site.indexOf("http://")>-1?site.substring(7):site, 1104);
			client.login("boc", "zhboc1104");
			client.createDirectory("t2");
			//client.download("t/server.xml", new File("server.xml"));
			FTPFile[] files = client.list("test");
			for (FTPFile ftpFile : files) {
				//System.out.println(ftpFile.getName());
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			e.printStackTrace();
		} catch (FTPListParseException e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect(true);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				e.printStackTrace();
			} catch (FTPException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
