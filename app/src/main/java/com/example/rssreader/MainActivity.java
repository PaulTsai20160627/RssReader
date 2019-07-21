package com.example.rssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String temp;
    private boolean findStart;
    private boolean findEnd;
    private RSSFeedInfos rssFeedInfos = new RSSFeedInfos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new readRSSFeedTask().execute("http://valley.egloos.com/theme/rss/recent/fashion");
        new readRSSFeedTask().execute("https://www.yahoo.com/news/rss/world");
    }
    private class readRSSFeedTask extends AsyncTask<String ,String,Void>{


        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG,"RSS description ");
            //RSSFeedInfos rssFeedInfos = parseKoreaRSS(strings[0]);
            RSSFeedInfos rssFeedInfos = parseUSAYahooRSS(strings[0]);
            for(int i = 0 ; i < rssFeedInfos.getTitleList().size(); i++){
                Log.d(TAG,"RSS title : "+rssFeedInfos.getTitleList().get(i));
            }
            for(int j = 0 ; j < rssFeedInfos.getDescriptionList().size();j++){

                Log.d(TAG,"RSS description : "+rssFeedInfos.getDescriptionList().get(j));
            }
            for(int k = 0 ; k< rssFeedInfos.getImgSourceList().size() ; k++){

                Log.d(TAG,"RSS img : "+rssFeedInfos.getImgSourceList().get(k));
            }
            return null;
        }
    }

    private RSSFeedInfos parseUSAYahooRSS(String url){
        try {
            URL rssUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String title = "";
            int indexOfTitle = 0 ;
            String description = "";
            int indexOfDescription = 0;
            String img = "";
            int indexOfImg = 0 ;
            String line;
            while((line = in.readLine())!=null){
                Log.d(TAG,"line = "+line);

                if(line.contains("<title>")){
                    int firstPos = line.indexOf("<title>");
                    temp = line.substring(firstPos);
                    temp = temp.replace("<title>","");
                    int lastPos = temp.indexOf("</title>");
                    temp = temp.substring(0,lastPos);
                    title += temp + "\n";
                    rssFeedInfos.getTitleList().add(indexOfTitle,title);
                    title = "";
                    indexOfTitle++;
                }



                if(line.contains("src=\"")){
                    int firstPos = line.indexOf("src=\"");
                    temp = line.substring(firstPos);
                    temp = temp.replace("src=\"","");
                    int lastPos = temp.indexOf("\"");
                    temp = temp.substring(0,lastPos);
                    img += temp + "\n";
                    rssFeedInfos.getImgSourceList().add(indexOfImg,img);
                    img = "";
                    indexOfImg++;
                }
            }

            in.close();
            return rssFeedInfos;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private RSSFeedInfos parseKoreaRSS(String url){
        try {
            URL rssUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String title = "";
            int indexOfTitle = 0 ;
            String description = "";
            int indexOfDescription = 0;
            String img = "";
            int indexOfImg = 0 ;
            String line;
            while((line = in.readLine())!=null){
                Log.d(TAG,"line = "+line);

                if(line.contains("<title><![CDATA[")){
                    int firstPos = line.indexOf("<title><![CDATA[");
                    temp = line.substring(firstPos);
                    temp = temp.replace("<title><![CDATA[","");
                    int lastPos = temp.indexOf("]]></title>");
                    temp = temp.substring(0,lastPos);
                    title += temp + "\n";
                    rssFeedInfos.getTitleList().add(indexOfTitle,title);
                    title = "";
                    indexOfTitle++;
                }

                if(line.contains("/>")){
                    int firstPos = line.indexOf("/>");
                    line = line.substring(firstPos+2);
                    line.trim();
                    temp = line;
                    if(temp.contains("]]>")){
                        int lastPos = temp.indexOf("]]>");
                        temp = line.substring(0,lastPos);
                        description += temp + "\n";
                        rssFeedInfos.getDescriptionList().add(indexOfDescription,description);
                        description = "";
                        indexOfDescription++;
                    }
                    description += temp + "\n";
                }

                if(line.contains("src=\"")){
                    int firstPos = line.indexOf("src=\"");
                    temp = line.substring(firstPos);
                    temp = temp.replace("src=\"","");
                    int lastPos = temp.indexOf("\"");
                    temp = temp.substring(0,lastPos);
                    img += temp + "\n";
                    rssFeedInfos.getImgSourceList().add(indexOfImg,img);
                    img = "";
                    indexOfImg++;
                }
            }

            in.close();
            return rssFeedInfos;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
