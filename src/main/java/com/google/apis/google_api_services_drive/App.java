package com.google.apis.google_api_services_drive;

//import com.box.sdk.BoxFolder;
//import com.box.sdk.BoxUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Children;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App 
{	
	private static String CLIENT_ID1 = "239045167544-7cdofigo9aroldln3340n1bfqjbv0ksi.apps.googleusercontent.com";
	private static String CLIENT_SECRET1 = "3nj6cTgKqVYovs9cIijfGFhV";
	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	private static String Root_Folder = "0AErPfL4CPS6DUk9PVA";	
	private static String refresh_token ="1/skB6ZW8ppotsHGbOz_kcXVWqZ1MGL2Jxwbv11x5D1jo";
    public static void main( String[] args ) throws IOException, URISyntaxException
    {
    	String arg1 = args[0];
    	String arg2 = args[1];
    	String ClientID = "";
    	String ClientSecret = ""; 
    	String access_token = Readfile("token.gdrive"+arg1).trim();    	
    	HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	    
	    if(arg1.equals("1")){
    		ClientID = CLIENT_ID1;
    		ClientSecret = CLIENT_SECRET1;
    	}
    	else if (arg1.equals("2")){
    		ClientID = "";
    		ClientSecret = "";
    	}
	    /*GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	            httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	            .setAccessType("offline")
	            .setApprovalPrompt("auto").build();	   
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    System.out.println("Enter authorization code:");
	    Desktop.getDesktop().browse(new URI(url));
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String code = br.readLine();
	    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
            .build()
            .setFromTokenResponse(response);
        String accessToken = credential.getAccessToken();
        String refreshToken = credential.getRefreshToken();
        System.out.println(accessToken);
        System.out.println(refreshToken);
        */
        GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(jsonFactory)
        		.setTransport(httpTransport).setClientSecrets(ClientID, ClientSecret).build();
        		credential.setAccessToken(access_token);
        		//credential.setRefreshToken(refresh_token);
       Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
       if(arg2.equals("help")){    	   
    	    	System.out.println("------------------------------------------------------");
    	    	System.out.println("--#######-#####---########-########-##----##-#######--");
    	    	System.out.println("--##---##-##--###-##---###----##----##----##-##-------");
    	    	System.out.println("--##------##---##-##--###-----##----##----##-##-------");
    	    	System.out.println("--##--###-##---##-######------##----##----##-#######---");
    	    	System.out.println("--##---##-##--###-##---##-----##-----##--##--##-------");
    	    	System.out.println("--#######-#####---##----##-########----##----#######--");
    	    	System.out.println("------------------------------------------------------");
    	    	System.out.println("Usage Commnad-line");
    	    	System.out.println("Commmand <need> [option] {detail}");
    	    	System.out.println("- help			{Show more informaion command}");
    	    	System.out.println("- account		{Show account informaion}");
    	    	System.out.println("- space			{Show space informaion}");
    	    	System.out.println("- spaceper		{Show space in percent}");
    	    	System.out.println("- listingAll		{Show all file and Directory}");
    	    	System.out.println("- deletefile		<id> {Delete file with id}");
    	    	System.out.println("- metadata		<id> {Show file information with id}");    	    	
    	    	System.out.println("- download		<id> {Download file with id}");
    	    	System.out.println("- upload		<filename><type> {Upload file with from filename}");    	    	    
      }
      else if(arg2.equals("account")){
      		GetUserInfo(service);
      }
      else if(arg2.equals("space")){
      		space(service);
      }
      else if(arg2.equals("spaceper")){
      		spaceper(service);
      }
      else if(arg2.equals("listingAll")){
   	   		printFilesInFolder(service,Root_Folder);
      }
      else if(arg2.equals("listing")){ 
      		String arg3 = args[2];
      		printFilesInFolder(service,arg3);
      }
      else if(arg2.equals("deletefile")){		
			String arg3 = args[2];
			deleteFile(service,arg3);
		}
      else if(arg2.equals("metadata")){		
			String arg3 = args[2];
			printFile(service,arg3);
		}
      else if(arg2.equals("download")){		
			String arg3 = args[2];
			downloadFile(service,arg3);
		}
      else if(arg2.equals("upload")){		
			String arg3 = args[2];
			String arg4 = args[3];			
			insertFile(service,arg3,"",Root_Folder,arg4,arg3);
			
		}
		else System.out.println("Commnad Error");
    }
    public static void copyStream(InputStream input, OutputStream output) throws IOException
	{
	    byte[] buffer = new byte[1024]; // Adjust if you want
	    int bytesRead;
	    while ((bytesRead = input.read(buffer)) != -1)
	    {
	        output.write(buffer, 0, bytesRead);
	    }	    
	}
    private static String Readfile(String ChFile) throws IOException{
    	BufferedReader br = new BufferedReader(new FileReader(ChFile));
    	StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String everything = sb.toString();        
    	return everything;
    }
    private static void downloadFile(Drive service, String fileid, String newPath) throws IOException {
	 File file = service.files().get(fileid).execute();
    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
      try {
        HttpResponse resp =service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
        InputStream result = resp.getContent();
        OutputStream out = new FileOutputStream(newPath+"file.getTitle()");
        copyStream(result,out);
      } catch (IOException e) {
        // An error occurred.
        e.printStackTrace();           
      }
    } else {
      // The file doesn't have any content stored on Drive.          
    }
  }
    private static String CheckTypeFile(String type) {
    	String mimeType=null;
    	if(type.equals("xls")) mimeType="application/vnd.ms-excel";
		else if(type.equals("xlsx")) mimeType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if(type.equals("xml")) mimeType="text/xml";
		else if(type.equals("csv")) mimeType="text/plain";
		else if(type.equals("tmpl")) mimeType="text/plain";
		else if(type.equals("pdf")) mimeType="application/pdf";
		else if(type.equals("php")) mimeType="application/x-httpd-php";
		else if(type.equals("jpg")) mimeType="image/jpeg";
		else if(type.equals("png")) mimeType="image/png";
		else if(type.equals("gif")) mimeType="image/gif";
		else if(type.equals("bmp")) mimeType="image/bmp";
		else if(type.equals("txt")) mimeType="text/plain";
		else if(type.equals("doc")) mimeType="application/msword";
		else if(type.equals("js")) mimeType="text/js";
		else if(type.equals("swf")) mimeType="application/x-shockwave-flash";
		else if(type.equals("mp3")) mimeType="audio/mpeg";
		else if(type.equals("zip")) mimeType="application/zip";
		else if(type.equals("rar")) mimeType="application/rar";
		else if(type.equals("tar")) mimeType="application/tar";
		else if(type.equals("jar")) mimeType="application/zip";
		else if(type.equals("html")) mimeType="text/html";    	
		else if(type.equals("html")) mimeType="text/html";
		else if(type.equals("ppt")) mimeType="application/vnd.openxmlformats-officedocument.presentationml.presentation";
		else mimeType="application/octet-stream";
    	return mimeType;
    }
    private static File insertFile(Drive service, String title, String description,String parentId, String type, String filename) {
    	   	
    		String mimeType=null;
    		mimeType = CheckTypeFile(type);
    		// File's metadata.
    	    File body = new File();
    	    body.setTitle(title);
    	    body.setDescription(description);
    	    body.setMimeType(mimeType);
    	    // Set the parent folder.
    	    if (parentId != null && parentId.length() > 0) {
    	      body.setParents(
    	          Arrays.asList(new ParentReference().setId(parentId)));
    	    }
    	    // File's content.
    	    java.io.File fileContent = new java.io.File(filename);
    	    FileContent mediaContent = new FileContent(mimeType, fileContent);
    	    try {
    	      File file = service.files().insert(body, mediaContent).execute();
    	      // Uncomment the following line to print the File ID.
    	       System.out.println("File ID: " + file.getId());
    	      return file;
    	    } catch (IOException e) {
    	      System.out.println("An error occured: " + e);
    	      return null;
    	    }
    	  }
    private static void deleteFile(Drive service, String fileId) {
        try {
          service.files().delete(fileId).execute();
        } catch (IOException e) {
          System.out.println("An error occurred: " + e);
        }
      }
    private static void GetUserInfo(Drive service) {
        try {
          About about = service.about().get().execute();
          System.out.println("User Account Info");      
          System.out.println("User ID = " + about.getPermissionId());
          System.out.println("display name = " + about.getName());          
          System.out.println("Root folder ID = " + about.getRootFolderId());
          System.out.println("");
          System.out.println("User space");
          System.out.println("Total = " + about.getQuotaBytesTotal()/1073741824+" Gb");
          System.out.println("Used = " + about.getQuotaBytesUsed()/1073741824+" Gb"); 
          System.out.format("free = %d Gb\n",(about.getQuotaBytesTotal()/1073741824)-(about.getQuotaBytesUsed()/1073741824));
        } catch (IOException e) {
          System.out.println("An error occurred: " + e);
        }
     }
    private static void space(Drive service) {
        try {
          About about = service.about().get().execute();
          System.out.println("User space");
          System.out.println("Total = " + about.getQuotaBytesTotal()/1073741824+" Gb");
          System.out.println("Used = " + about.getQuotaBytesUsed()/1073741824+" Gb"); 
          System.out.format("free = %d Gb\n",(about.getQuotaBytesTotal()/1073741824)-(about.getQuotaBytesUsed()/1073741824));
        } catch (IOException e) {
          System.out.println("An error occurred: " + e);
        }
     }
    private static void spaceper(Drive service) {
        try {
         About about = service.about().get().execute();          
      	long total = about.getQuotaBytesTotal()/1073741824;
      	long use = about.getQuotaBytesUsed()/1073741824;
      	long percents = use/total*100;    	
      	System.out.println(percents);
        } catch (IOException e) {
          System.out.println("An error occurred: " + e);
        }
     }
    private static void printFile(Drive service, String fileId) {

        try {
          File file = service.files().get(fileId).execute();

          System.out.println("Title: " + file.getTitle());
          System.out.println("Description: " + file.getDescription());
          System.out.println("MIME type: " + file.getMimeType());
        } catch (IOException e) {
          System.out.println("An error occured: " + e);
        }
    }
    private static void printFilesInFolder(Drive service, String folderId)throws IOException {
    	    Children.List request = service.children().list(folderId).setQ("trashed = false");
    	    //service.files().list().setQ("trashed = false").execute();
    	    do {
    	      try {
    	        ChildList children = request.execute();    	        
    	        for (ChildReference child : children.getItems()) {
    	          File file = service.files().get(child.getId()).execute();
    	          System.out.println("File Id: " + file.getTitle()+" "+child.getId());    	        	
    	        }
    	        request.setPageToken(children.getNextPageToken());
    	      } catch (IOException e) {
    	        System.out.println("An error occurred: " + e);
    	        request.setPageToken(null);
    	      }
    	    } while (request.getPageToken() != null &&
    	             request.getPageToken().length() > 0);
    	  }
}
