package com.bitmakersbd.biyebari.server.util;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.photos.upload.UploadInterface;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

public class FlickrUtil {
    private static final String API_KEY = "fd4e52c23c83fbf7b3fb0b427de7b15f";
    private static final String API_SECRET = "662ab005021e2d7f";

    private Flickr flickr;

    public FlickrUtil() {
        this.flickr = new Flickr(API_KEY, API_SECRET, new REST());
    }

    public static void main(String[] args) throws Exception {
        FlickrUtil flickrUtil = new FlickrUtil();
    }

    private Auth authenticate() throws Exception {
        AuthInterface authInterface = flickr.getAuthInterface();
        Token requestToken = authInterface.getRequestToken();
        String authUrl = authInterface.getAuthorizationUrl(requestToken, Permission.WRITE);
        System.out.println(authUrl);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Give the token key:");
        String tokenKey = scanner.nextLine();
        scanner.close();

        Token accessToken = authInterface.getAccessToken(requestToken, new Verifier(tokenKey));
        Auth auth = authInterface.checkToken(accessToken);
        System.out.println("Authentication success!");

        return auth;
    }

    public String upload(byte[] file) throws Exception {
        flickr.setAuth(authenticate());

        Uploader uploader = flickr.getUploader();
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setFilename("test file");
        return uploader.upload(file, uploadMetaData);
    }

    private String getFrob() throws Exception {
        String methodGetFrob = "flickr.auth.getFrob";
        String sig = API_SECRET + "api_key" + API_KEY + "method" + methodGetFrob;
        String signature = Md5Encryption.encrypt(sig);
        String request = "https://api.flickr.com/services/rest/?method=" + methodGetFrob + "&api_key=" + API_KEY + "&api_sig=" + signature;
        System.out.println("GET frob request: " + request);

        // configure the SSLContext with a TrustManager
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        URL url = new URL(request);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        InputStream rstream = null;

        rstream = (InputStream) conn.getContent();
        Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rstream);
        String frob = null;

        NodeList frobResponse = response.getElementsByTagName("frob");
        Node frobNode = frobResponse.item(0);
        if (frobNode != null) {
            frob = frobResponse.item(0).getTextContent();
        } else {
            System.out.println("error getting forb");
        }

        return frob;
    }

    private String getAuthUrl(String frob) {
        String sig = API_SECRET + "api_key" + API_KEY + "frob" + frob + "permswrite";
        String signature = Md5Encryption.encrypt(sig);
        return "https://flickr.com/services/auth/?api_key=" + API_KEY + "&perms=write&frob=" + frob + "&api_sig=" + signature;
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
