package com.example.android.miwok;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
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

public class key_pass_generator extends Thread {

static SharedPreferences sd;
   static Dialog dialog;
    static boolean gotthekey=false;
    private static Handler handler;

    public key_pass_generator(SharedPreferences sd, Dialog dialog) {
        this.sd=sd;
        this.dialog=dialog;
    }

    public key_pass_generator() {

    }

    public key_pass_generator(Handler handler,SharedPreferences sd){
        this.handler=handler;
        this.sd=sd;
    }
static void getkeyval()

    {
        try {
            if((new Date()).getTime() - Long.parseLong(sd.getString("lastcall","")) >= 240000) {
                System.out.println("calling keypass url.........");
                DownloadTask task = new DownloadTask();
                task.seturl("http://enquiry.indianrail.gov.in/ntes/");
                task.doInBackground();
            }else{
                System.out.println("no need to call keypass");
                Message message =Message.obtain();
                message.obj =new customObject("key_pass_generator","success","already having..no need to call keypass");
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            System.out.println("error inside key_pass_generator :"+e.fillInStackTrace());
            String msgSend ="error inside key_pass_generator :"+e.fillInStackTrace();
            Message message =Message.obtain();
            message.obj =new customObject("key_pass_generator","error","pls check ur Internet Connection");
            handler.sendMessage(message);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("key_pass_thread");
        Log.i("current Thread name :",Thread.currentThread().getName());
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

            Log.i("Thread name :",Thread.currentThread().getName());
            HttpURLConnection urlConnection = null;

            try {
                 System.out.println("under downloading function \ncalling url "+uRl);
                url = new URL(uRl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(20000);
                urlConnection.connect();
                Object localObject2;
                if (urlConnection.getResponseCode() == 200) {
                    Object localObject3 = new CookieManager();
                    Object localObject1;
                    localObject1 = (List) urlConnection.getHeaderFields().get("Set-Cookie");
                    Object localObject4;
                    localObject4 = null;

                //    Log.i("cookie found :", String.valueOf(urlConnection.getHeaderFields().get("Set-Cookie")));
                    if (localObject1 != null) {
                        localObject1 = ((List) localObject1).iterator();
                        while (((Iterator) localObject1).hasNext()) {
                            localObject2 = (String) ((Iterator) localObject1).next();
                            System.out.println(localObject2);
                            ((CookieManager) localObject3).getCookieStore().add(null, (HttpCookie) HttpCookie.parse((String) localObject2).get(0));

                        }
                    }

                    localObject1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    localObject2 = new StringBuilder();
                    for (; ; ) {
                        localObject4 = ((BufferedReader) localObject1).readLine();
                        if (localObject4 == null) {
                            break;
                        }
                        ((StringBuilder) localObject2).append((String) localObject4 + "\n");
                        //  System.out.println(localObject4);
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

                                    localObject3 = ((CookieManager) localObject3).getCookieStore().getCookies().toString();
                                    System.out.println("cookie :" + localObject3);
                                    System.out.println("key :" + localObject1);
                                    System.out.println("pass :" + (String) localObject2);
//                                    String datam = (String) localObject3;


                              //      Log.i("cookie ", localObject3.toString());
                                    sd.edit().putString("cookie", localObject3.toString()).apply();
                              //      Log.i("key ", localObject1.toString());
                                    sd.edit().putString("key", localObject1.toString()).apply();
                              //      Log.i("pass ", localObject2.toString());
                                    sd.edit().putString("pass", localObject2.toString()).apply();
                                    result= localObject1.toString();

                                    gotthekey=true;


                                }

                            }
                        }
                    }

                }
                if(gotthekey){
                    Log.i("lastcall", String.valueOf((new Date()).getTime()));
                    sd.edit().putString("lastcall", String.valueOf((new Date()).getTime())).apply();
                    Log.i("got the key :",String.valueOf(gotthekey));
                    Message message =Message.obtain();
                    message.obj =new customObject("key_pass_generator","success","got the key & pass");
                    handler.sendMessage(message);
                }else{
                    Thread.sleep(100);
                    Log.i("got the key :",String.valueOf(gotthekey));
                    String msgSend="got the keyval:"+gotthekey+"\nretrying.....";
                    Message message =Message.obtain();
                    message.obj =new customObject("key_pass_generator","error","key val not found....retrying");
                    handler.sendMessage(message);
                  getkeyval();
                }

            }catch (SocketTimeoutException e){
                System.out.println("Socket Timeout Exception:"+e.fillInStackTrace());
                String msgSend="Socket Timeout Exception:"+e.fillInStackTrace();
                Message message =Message.obtain();
                message.obj =new customObject("key_pass_generator","error","socket timeout");
                handler.sendMessage(message);
            } catch (Exception e) {

               System.out.println("Error inside key pass generator 2:"+e.fillInStackTrace());
                String msgSend="Error inside key pass generator 2:"+e.fillInStackTrace();
                Message message =Message.obtain();
                message.obj =new customObject("key_pass_generator","error","pls Check ur Internet Connection");
                handler.sendMessage(message);

            }


        }


    }
}
