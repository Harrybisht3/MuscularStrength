package app.android.muscularstrength.webservice;

/**
 * Created by Bisht Bhawna on 8/8/2015.
 */
public class WebServices {
    public static final String host="http://muscularstrength.com/";

    public static final String signin=host+"checkUser.php";
    public static final String article=host+"article_json.php";
    public static final String Recipes=host+"recipes_json.php";
    public static final String notification=host+"notifications_json.php";//userid=
    public static final String Friend_Request=host+"friendrequest_json.php";//userid=
    public static final String Forums= host+"forums_json.php";
    public static  final String Routine=host+"routine_json.php";
   //subForumID=90
    public static final String Thread=host+"forums_json.php?";//threadID=2553
    public static final String Exclusive=host+"exclusive_categories_json.php";
    public static final String Exclusive_Videos=host+ "exclusive_videos_json.php";//categoryID=4
    public static final String Message=host+"message_json.php";//userid=
    public static final String Friend=host+"friends_json.php";//userid=
    public static final String newsFeed=host+"NewsFeed_json.php";//userid
    public static final String newsFeedlike=host+"newsfeed_like.php";//userid=135953&id=79663
    public static final String newsFeedReply=host+"newsfeed_reply.php";
   //userid=135953&id=79663&postowner=147430&comment=hi%20how%20are%20you!
    public static final String videos=host+"videos_json.php";//userid
    public static final String story=host+"story_json.php";//userid
    public static final String edit_profile=host+"edit_profile_json.php";//userid
    public static final String photos=host+"photo_json.php";//userid
    public static final String meal_plan=host+"mealplan_json.php";//userid
    public static final String friends=host+"friends_json.php";//userid
    public static final String addPhotos=host+"creat_album.php";//userid,album_id,caption,imgefile
    //public static final String discussion_board=host+"friends_json.php";//userid




   // http://muscularstrength.com/article_json.php?page=2&display=15

}
