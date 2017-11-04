package com.example.android.miwok;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class key_pass_generator_t extends Thread {

static SharedPreferences sd;

    static boolean gotthekey=false;
    private static Handler handler;



    public key_pass_generator_t() {

    }

    public key_pass_generator_t(Handler handler, SharedPreferences sd){
        this.handler=handler;
        this.sd=sd;
    }
static void getkeyval()

    {
        try {
            if((new Date()).getTime() - Long.parseLong(sd.getString("lastcall","")) >= 240000) {
           //System.out.println("calling keypass url.........");
                DownloadTask task = new DownloadTask();
                task.seturl("http://enquiry.indianrail.gov.in/ntes/");
                task.doInBackground();
            }else{
           //System.out.println("no need to call keypass");
                Message message =Message.obtain();
                message.obj =new customObject("key_pass_generator","success","already having..no need to call keypass");
                handler.sendMessage(message);
            }
        } catch (Exception e) {
       //System.out.println("error inside key_pass_generator :"+e.fillInStackTrace());
            String msgSend ="error inside key_pass_generator :"+e.fillInStackTrace();
            Message message =Message.obtain();
            message.obj =new customObject("key_pass_generator","error","Pls Check Your Internet Connection");
            handler.sendMessage(message);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("key_pass_thread");
       // Log.i("current Thread name :",Thread.currentThread().getName());
        getkeyval();
    }

    public static class DownloadTask  {
         private  String uRl;

        public void seturl(String uRl) {
            this.uRl = uRl;
        }
        protected void doInBackground() {
            String result = "";
            URL url;

           // Log.i("Thread name :",Thread.currentThread().getName());
    //        HttpURLConnection urlConnection = null;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("https://enquiry.indianrail.gov.in/ntes/");
            try {


                int timeout = 7; // seconds
                HttpParams httpParams = client.getParams();
                httpParams.setParameter(
                        CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
                httpParams.setParameter(
                        CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

                HttpResponse response = null;

                    response = client.execute(request);




                String sessionid = "";
                try {

                    Header[] headers = response.getAllHeaders();
                    for (Header header : headers) {
                        System.out.println(header.getName() + " : " + header.getValue() + " ");
                        if(header.getName().startsWith("Set-Cookie")){
                            System.out.println("here is the fking  hider :"+header.getValue());
                            String val2=header.getValue();
                            int k1=val2.indexOf(";");
                            int l1=val2.indexOf("=");
                            sessionid+=val2.substring(0,k1)+";";
                        }
                    }


//                    String val2 = headers[7].getValue();
//                    int k1 = val2.indexOf(";");
//                    int l1 = val2.indexOf("=");
//                    sessionid += val2.substring(0, k1) + ";";
//                    System.out.println(sessionid);
//                    String val = headers[2].getValue();
//                    int k = val.indexOf(";");
//                    int l = val.indexOf("=");
//                    sessionid += val.substring(0, k);
//            sessionid = sessionid.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
//            System.out.println( sessionid.split(",", 2)[0] + ";" + sessionid.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("response handler error :" + e.toString());
                }




                    Object localObject4;
                    Object localObject1 = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    Object localObject2 = new StringBuilder();
                    for (; ; ) {

                        localObject4 = ((BufferedReader) localObject1).readLine();
                        if (localObject4 == null) {
                            break;
                        }
                        ((StringBuilder) localObject2).append((String) localObject4 + "\n");
                        //System.out.println(localObject4);
                    }
                    localObject4 = ((StringBuilder) localObject2).toString().replaceAll("\\s+", "");
                    localObject1 = Pattern.compile("<script>_.*?=\"(.*?)\";").matcher((CharSequence) localObject4);
                    if (((Matcher) localObject1).find()) {
                        localObject2 = ((Matcher) localObject1).group(1);
                        localObject1 = localObject2;
                        if (localObject2 != null) {
                            localObject2 = Pattern.compile("name=\"" + (String) localObject1 + "\"value=\"(.*?)\"").matcher((CharSequence) localObject4);
                            if (((Matcher) localObject2).find()) {
                                localObject2 = ((Matcher) localObject2).group(1);
                                if (((String) localObject2).length() == 10) {

                                    // localObject3 = ((CookieManager) localObject3).getCookieStore().getCookies().toString();
                                    //System.out.println("cookie :" + localObject3);
                                    //System.out.println("key :" + localObject1);
                                    //System.out.println("pass :" + (String) localObject2);
//                                    String datam = (String) localObject3;


//                            Log.i("cookie ", localObject3.toString());
                            sd.edit().putString("cookie", sessionid).apply();
                                    System.out.println("key :" + localObject1.toString());
//                            Log.i("key ", localObject1.toString());
                            sd.edit().putString("key", localObject1.toString()).apply();
//                            Log.i("pass ", localObject2.toString());
                                    System.out.println("pass :" + localObject2.toString());
                                    System.out.println("cookie :" + sessionid);
                            sd.edit().putString("pass", localObject2.toString()).apply();
//                            result= localObject1.toString();
                                    String key = localObject1.toString();
                                    String pass = localObject2.toString();
                                    // String res=new stationstatus().stnsts(key,pass,sessionid);
                                    // System.out.println(res);
                                    gotthekey=true;
                                } else {
                                    System.out.println(" else part 10 word wala not find");
                                }

                            } else {
                                System.out.println(" else part local object 2 find");
                            }
                        } else {
                            System.out.println("response dont contain key pass");
                        }
                    }
                    if (gotthekey) {
                        // Log.i("lastcall", String.valueOf((new Date()).getTime()));
                        sd.edit().putString("lastcall", String.valueOf((new Date()).getTime())).apply();
                         Log.i("got the key :",String.valueOf(gotthekey));
                        Message message = Message.obtain();
                        message.obj = new customObject("key_pass_generator", "success", "got the key & pass");
                        handler.sendMessage(message);
                    } else {
                        Thread.sleep(3000);
                         Log.i("got the key :",String.valueOf(gotthekey));
                        String msgSend = "got the keyval:" + gotthekey + "\nretrying.....";
                        Message message = Message.obtain();
                        message.obj = new customObject("key_pass_generator", "error", "Error Connecting to Server....Retrying");
                        handler.sendMessage(message);
                        getkeyval();
                    }

                }catch (ConnectTimeoutException e){
                System.out.println("Error inside key pass generator 2:"+e.fillInStackTrace());
                String msgSend = "Error inside key pass generator 2:" + e.fillInStackTrace();
                Message message = Message.obtain();
                message.obj = new customObject("key_pass_generator", "error", "Connection timeout..Pls Retry");
                handler.sendMessage(message);
                } catch (SocketTimeoutException e ) {
                String msgSend = "Error inside key pass generator 2:" + e.fillInStackTrace();
                Message message = Message.obtain();
                message.obj = new customObject("key_pass_generator", "error", "Read timeout..Pls Retry "+e.toString());
                handler.sendMessage(message);
                 }
                catch (Exception e) {

                    System.out.println("Error inside key pass generator 2:"+e.fillInStackTrace());
                    String msgSend = "Error inside key pass generator 2:" + e.fillInStackTrace();
                    Message message = Message.obtain();
                    message.obj = new customObject("key_pass_generator", "error", "Pls Check Your Internet Connection");
                    handler.sendMessage(message);

                }
            }
    }
}
