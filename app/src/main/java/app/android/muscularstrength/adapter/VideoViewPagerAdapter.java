package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.YouTubePlayerActivity;
import app.android.muscularstrength.fragment.YouTubePlayer;
import app.android.muscularstrength.model.UserVideo;
import app.android.muscularstrength.model.UserVideoMaster;

/**
 * Created by sa on 8/25/2015.
 */
public class VideoViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "VideoViewPagerAdapter";
    // Declare Variables
    Context context;
    List<UserVideoMaster> videos;
    int[] flag;
    LayoutInflater inflater;
    TextView album;
    FragmentTransaction ft;
    FragmentManager manager;

    public VideoViewPagerAdapter(Context context,  List<UserVideoMaster> videos,TextView album, FragmentTransaction ft,FragmentManager manager) {
        this.context = context;
      this.videos=videos;
        this.album=album;
        this.ft=ft;
        this.manager=manager;


    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        LinearLayout pager_main;
       GridView viewgrid;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.video_viewpageritem, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        pager_main=(LinearLayout)itemView.findViewById(R.id.pager_main);
         viewgrid=(GridView)itemView.findViewById(R.id.videoGrid);
       final VideoGridAdapter adapter=new VideoGridAdapter(context);
        adapter.clear();
        adapter.addAll(videos.get(position).getVideos());
        viewgrid.setAdapter(adapter);
       // viewgrid.setExpanded(true);
        album.setText("ALBUM-" + position);
        viewgrid.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        viewgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserVideo item=adapter.getItem(position);

              // showPlayerVideo(item. getVideo());
                callYoutube(item. getVideo());
            }
        });
      /*  int totalHeight = 0;
        for (int size = 0; size < adapter.getCount(); size++) {
            RelativeLayout relativeLayout = (RelativeLayout) adapter.getView(
                    size, null, viewgrid);
            RelativeLayout textView = (RelativeLayout) relativeLayout.getChildAt(0);
            textView.measure(0, 0);
            totalHeight += textView.getMeasuredHeight();
        }
        viewgrid.SetHeight(totalHeight);*/
                ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
    private void showPlayerVideo(String Url) {
        /*ThreadFragment kBDetailFragment = new ThreadFragment();
        Bundle args = new Bundle();
        args.putString("threadID", id);
        kBDetailFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        //fm.beginTransaction().detach(this).commit();
        fm.beginTransaction()
                .replace(DashBoardFragment.frame.getId(), kBDetailFragment, "threadfragment")
                .addToBackStack(null)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();  FragmentTransaction ft = getChildFragmentManager().beginTransaction();*/
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", Url);
        Fragment fragment=new YouTubePlayer();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
        ft.commit();

    }
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        //FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    public static String getUrlVideoRTSP(String urlYoutube)
    {

        try
        {
            String gdy = "http://gdata.youtube.com/feeds/api/videos/";
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String id = extractYoutubeId(urlYoutube);
            URL url = new URL(gdy + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Document doc = documentBuilder.parse(connection.getInputStream());
            Element el = doc.getDocumentElement();
            NodeList list = el.getElementsByTagName("media:content");///media:content
            String cursor = urlYoutube;
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                if (node != null)
                {
                    NamedNodeMap nodeMap = node.getAttributes();
                    HashMap<String, String> maps = new HashMap<String, String>();
                    for (int j = 0; j < nodeMap.getLength(); j++)
                    {
                        Attr att = (Attr) nodeMap.item(j);
                        maps.put(att.getName(), att.getValue());
                    }
                    if (maps.containsKey("yt:format"))
                    {
                        String f = maps.get("yt:format");
                        if (maps.containsKey("url"))
                        {
                            cursor = maps.get("url");
                        }
                        if (f.equals("1"))
                            return cursor;
                    }
                }
            }
            return cursor;
        }
        catch (Exception ex)
        {
            Log.e("Get Url Video RTSP Exception>>", ex.toString());
        }
        return urlYoutube;

    }

    protected static String extractYoutubeId(String url) throws MalformedURLException
    {
        String id = null;
        try
        {
            String query = new URL(url).getQuery();
            if (query != null)
            {
                String[] param = query.split("&");
                for (String row : param)
                {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v"))
                    {
                        id = param1[1];
                    }
                }
            }
            else
            {
                if (url.contains("embed"))
                {
                    id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("Exception", ex.toString());
        }
        return id;
    }
    private void getUrlYouTube(final String urlYoutube) {
      //  pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String gdy = "http://gdata.youtube.com/feeds/api/videos/";
                    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    String id = extractYoutubeId(urlYoutube);
                    URL url = new URL(gdy + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    Document doc = documentBuilder.parse(connection.getInputStream());
                    Element el = doc.getDocumentElement();
                    NodeList list = el.getElementsByTagName("media:content");///media:content
                    String cursor = urlYoutube;
                    for (int i = 0; i < list.getLength(); i++)
                    {
                        Node node = list.item(i);
                        if (node != null)
                        {
                            NamedNodeMap nodeMap = node.getAttributes();
                            HashMap<String, String> maps = new HashMap<String, String>();
                            for (int j = 0; j < nodeMap.getLength(); j++)
                            {
                                Attr att = (Attr) nodeMap.item(j);
                                maps.put(att.getName(), att.getValue());
                            }
                            if (maps.containsKey("yt:format"))
                            {
                                String f = maps.get("yt:format");
                                if (maps.containsKey("url"))
                                {
                                    cursor = maps.get("url");
                                }
                                if (f.equals("1")){

                                }
                                  //  return cursor;
                            }
                        }
                    }
                    Log.e("URI", cursor);
                   // return cursor;
                }
                catch (Exception ex)
                {
                    Log.e("Get Url Video RTSP Exception>>", ex.toString());
                }
               // return urlYoutube;


            }
        }).start();
    }
    public void callYoutube(String Url){
        Intent it=new Intent((Activity)context, YouTubePlayerActivity.class);
        it.putExtra("videoUrl",Url);
        ((Activity)context).startActivity(it);
    }

}








