package com.liferay.springmvc;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import java.io.File;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.io.FileUtils;
import com.squareup.okhttp.*;


import java.io.*;
import java.util.Base64;


public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String tel;
	private String token;
	private String url_signataire;
	private String mail_signataire;
	private String idDemand;
	private String url_info;
	// file uploaded en base 64
	private String filebase64;
	// global varial ( id demande)
	private String id;
	// signé ou pas
	private static String signe="false";
	public String getFilebase64() {
		return filebase64;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static String getSigne() {
		return signe;
	}
	public static void setSigne(String signe) {
		Customer.signe = signe;
	}
	public void setFilebase64(String filebase) {
		filebase64 = filebase;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl_signataire() {
		return url_signataire;
	}

	public void setUrl_signataire(String url_signataire) {
		this.url_signataire = url_signataire;
	}

	public String getMail_signataire() {
		return mail_signataire;
	}

	public void setMail_signataire(String mail_signataire) {
		this.mail_signataire = mail_signataire;
	}

	public String getIdDemand() {
		return idDemand;
	}

	public void setIdDemand(String idDemand) {
		this.idDemand = idDemand;
	}

	public String getUrl_info() {
		return url_info;
	}

	public void setUrl_info(String url_info) {
		this.url_info = url_info;
	}
	private CommonsMultipartFile fileUpload;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public CommonsMultipartFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(CommonsMultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}


	public void sendFile_yousign(String c,String namefile) throws Exception{
        try{
            //System.out.println(c);
        	HttpResponse<String> re = Unirest.post("https://apidemo.yousign.fr:8181/CosignWS/CosignWS")
        			  .header("content-type", "text/xml")
        			  .header("cache-control", "no-cache")
        			  .header("postman-token", "f4a262b1-9c28-ad8e-4320-85ccc4946c51")
        			  .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:yous=\"http://www.yousign.com\" xmlns:xsi=\"xsi\">\r\n    <soapenv:Header>\r\n        "
        			  		+ "<username>oumaima.nouar@capfi.fr</username>\r\n        "
        			  		+ "<password>3b08bc24ed864654a1ccc445cb6485fe657a8918</password>\r\n        "
        			  		+ "<apikey>kFFPnJFsAgf8AML15QNHpPURAAZTuFSQGqc3hiRP</apikey>\r\n    </soapenv:Header>\r\n    <soapenv:Body>\r\n        <yous:initCosign>\r\n            <lstCosignedFile>\r\n                <name>file</name>\r\n                "
        			  		+ "<content>\r\n                \t"
        			  		+c
        			  		+ "\r\n</content>\r\n   "
        			  		+ "<visibleOptions>\r\n" + 
        			  		"               <visibleSignaturePage>1</visibleSignaturePage>\r\n" + 
        			  		"               <isVisibleSignature>true</isVisibleSignature>\r\n" + 
        			  		"               <!--Optional:-->\r\n" + 
        			  		"               <visibleRectangleSignature>350,235,562,351</visibleRectangleSignature>\r\n"  
        			  		+"<mail>\r\n"
        			  		+ getEmail()
        			  		+ "</mail>\r\n"
        			  		+"			 </visibleOptions>"
        			  		+ "          <!--Optional:-->\r\n                <pdfPassword/>\r\n            </lstCosignedFile>\r\n            <lstCosignerInfos>\r\n                "
        			  		+ "<firstName>"
        			  		+ getFirstName()
        			  		+ "</firstName>\r\n                "
        			  		+ "<lastName>"
        			  		+ getLastName()
        			  		+ "</lastName>\r\n                "
        			  		+ "<mail>"
        			  		+ getEmail()
        			  		+ "</mail>\r\n                "
        			  		+ "<phone>"
        			  		+ getTel()
        			  		+ "</phone>\r\n            "
        			  		+ "</lstCosignerInfos>\r\n             "
        			  		+ "       <initMailSubject>Demande de signature envoyée par CAPFI"
        			  		+ "</initMailSubject>\r\n" + 
        			  		"				<initMail>&lt;p&gt; "
        			  		+ "Bonjour "
        			  		+ getFirstName()+" "+getLastName()+",&lt;/p&gt; &lt;p&gt;CAPFI vous invite à signer les documents suivants:    "
        			  		+ "- "+namefile+"&lt;br/&gt;"
        			  		+ "Cliquez sur le lien suivant pour consulter puis signer les documents :  &lt;/p&gt;&lt;p&gt;&lt;a href=\"{yousignUrlAlone}\"&gt;Cliquez ici&lt;/a&gt;&lt;/p&gt;</initMail>"
        			  		+ "<endMailSubject>Document signé avec succés </endMailSubject>\r\n"  
        			  		
        			  		+"<endMail>&lt;p&gt; "
        			  		+ "Bonjour "
        			  		+ getFirstName()+" "+getLastName()+",&lt;/p&gt; &lt;p&gt; Tous les signataires ont signé les documents suivants :    "
        			  		+ "- "+namefile+"&lt;br/&gt;"
        			  		+ "Veuillez accéder à l'adresse suivante pour les télécharger :   {yousignUrl}&lt;/p&gt;</endMail>"
        			  		+"<mode>IFRAME</mode>"
        			  		+"</yous:initCosign>\r\n    </soapenv:Body>\r\n</soapenv:Envelope>\r\n")
        			  .asString();
        	setFilebase64(c);
        	org.w3c.dom.Document doc = convertStringToDocument(re.getBody());
        	doc.getDocumentElement().normalize();
            org.w3c.dom.NodeList nList = doc.getElementsByTagName("fileInfos");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
            }
            org.w3c.dom.NodeList nList3 = doc.getElementsByTagName("tokens");
            for (int temp = 0; temp < nList3.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList3.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                   // System.out.println("token : " + eElement.getElementsByTagName("token").item(0).getTextContent());
                    setToken(eElement.getElementsByTagName("token").item(0).getTextContent());
                    setUrl_signataire("https://demo.yousign.fr/public/cosignature/"+eElement.getElementsByTagName("token").item(0).getTextContent());
                    setMail_signataire(eElement.getElementsByTagName("mail").item(0).getTextContent());
                }
            }
            setIdDemand(doc.getElementsByTagName("idDemand").item(0).getTextContent());
            setUrl_info("https://demo.yousign.fr/cosignature/info/"+doc.getElementsByTagName("idDemand").item(0).getTextContent());
            setId(idDemand);    
        } catch (Exception e) {
        e.printStackTrace();
    }
    }
	public org.w3c.dom.Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	public String code_base64(String SignTest1File){
		File file = new File(SignTest1File);
		SignTest1File=file.getAbsolutePath();
	    // create a byte array that will hold our document bytes
	    byte[] fileBytes = null;
	    try
	    {	
	        // read file from a local directory
	        Path path = Paths.get(SignTest1File);
	        fileBytes = Files.readAllBytes(path);
	    }
	    catch (IOException ioExcp)
	    {
	        System.out.println("Exception: " + ioExcp);
	    }
	    String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
	    return base64Doc;
	}  
	public String getFile_yousign(String idDemand,String fileuploaded)throws Exception{
		HttpResponse<String> response = Unirest.post("https://apidemo.yousign.fr:8181/CosignWS/CosignWS")
				  .header("content-type", "text/xml")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "695255aa-957d-96f6-e1a8-bb7184eea291")
				  .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:yous=\"http://www.yousign.com\">\r\n   <soapenv:Header>\r\n        "
				  		+ "<username>oumaima.nouar@capfi.fr</username>\r\n        "
				  		+ "<password>3b08bc24ed864654a1ccc445cb6485fe657a8918</password>\r\n        "
				  		+ "<apikey>kFFPnJFsAgf8AML15QNHpPURAAZTuFSQGqc3hiRP</apikey>\r\n   </soapenv:Header>\r\n   <soapenv:Body>\r\n      <yous:getCosignedFilesFromDemand>\r\n         "
				  		+ "<idDemand>"
				  		+ idDemand
				  		+ "</idDemand>\r\n         "
				  		+ "<token/>\r\n         "
				  		+ "<idFile/>\r\n      </yous:getCosignedFilesFromDemand>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>")
				  .asString();
		org.w3c.dom.Document doc = convertStringToDocument(response.getBody());
    	doc.getDocumentElement().normalize();
    	String file=doc.getElementsByTagName("file").item(0).getTextContent();
    	if(!fileuploaded.equals(file)) {
    		//System.out.println("done");
    		return file;
    	}
    	//System.out.println("non signé");
    	return null;
	}

	
}

