package app.android.muscularstrength.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.BitmapHelper;
import app.android.muscularstrength.Util.MultipartUtility;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.adapter.SelectedImageAdapter;
import app.android.muscularstrength.custom.GridViewWithHeaderAndFooter;
import app.android.muscularstrength.model.Album;
import app.android.muscularstrength.model.ImageModel;
import app.android.muscularstrength.model.PhotoParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by Bisht Bhawna on 9/23/2015.
 */
public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    Button selectphotos;

    ImageView backBtn, cancelbtn, camera;
    GridView imageGallery;
    GridViewWithHeaderAndFooter selectedgallery;
    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;
    int countthumb;
    private List<String> fileList = new ArrayList<String>();
   // List<Bitmap> selectedImages;
    List<String> selectedFiles;
    RelativeLayout selectView, selectionView;
    TextView textselect;
    SessionManager session;
    User userObj;
    PhotoParser photoparser;
    int selectedalbum;
    ProgressDialog pDialog;
    int countUpload = 0;
    ImageView actionbarmenu,back_Btn;
    TextView title;
    Spinner sp_album,selection_sp;
    Button upload;
    public static List<ImageModel>imagesmodel;
    String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_photo);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View v = getSupportActionBar().getCustomView();
        actionbarmenu = (ImageView) v.findViewById(R.id.menu_icon);
        back_Btn = (ImageView) v.findViewById(R.id.back_icon);
        title=(TextView)v.findViewById(R.id.titleactionbar);
        title.setText("ADD PHOTOS");
        session=new SessionManager(this);
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
        photoparser=getIntent().getParcelableExtra("ParcelableList");
       // al_id= getIntent().getStringArrayListExtra("AlbumID");
        //al_name=getIntent().getStringArrayListExtra("AlbumName");
        Log.i("DATA ALBUM", "" + photoparser.getResult());
        actionbarmenu.setVisibility(View.GONE);
        back_Btn.setVisibility(View.VISIBLE);
        back_Btn.setOnClickListener(this);
        Toolbar parent = (Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0, 0);// set padding programmatically to 0dp
       // moveDrawerToTop();
        init();
        /*File root = new File(Environment
                .getExternalStorageDirectory()
                .getAbsolutePath());
        ListDir(root);*/


    }

    private void init() {
        selectView = (RelativeLayout) findViewById(R.id.select_layout);
        selectionView = (RelativeLayout) findViewById(R.id.selectionGridlayout);
        selectphotos = (Button) findViewById(R.id.select_photos);
        selectphotos.setOnClickListener(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        cancelbtn = (ImageView) findViewById(R.id.cancelBtn);
        cancelbtn.setOnClickListener(this);
        camera = (ImageView) findViewById(R.id.camera);
        imageGallery = (GridView) findViewById(R.id.imageGallery);
        textselect = (TextView) findViewById(R.id.selectedsize);
        textselect.setOnClickListener(this);
        selection_sp=(Spinner)findViewById(R.id.selection_sp);
        selectedgallery = (GridViewWithHeaderAndFooter) findViewById(R.id.selectedgallery);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View footerView = layoutInflater.inflate(R.layout.grid_footer, null);
        selectedgallery.addFooterView(footerView);
        sp_album=(Spinner)footerView.findViewById(R.id.album_sp);
        //String[]list=new String[photoparser.getData().getData().size()];
        List<String>list=new ArrayList<String>();
        for (Album ab:photoparser.getData().getData()) {
            list. add(ab.getTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.myspinner_style,list);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        sp_album.setAdapter(adapter);
        //selection_sp
        upload=(Button)footerView.findViewById(R.id.addtoalbum);
        pDialog = new ProgressDialog(this);
        upload.setOnClickListener(this);
        pDialog.setMessage("loading...");
        sp_album.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedalbum=position;
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.cat_color));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void init_data() {
        textselect.setText("0");
       // selectedImages = new ArrayList<Bitmap>();
        selectedFiles = new ArrayList<String>();

        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
                final String orderBy = MediaStore.Images.Media._ID;
                Cursor imagecursor = AddPhotoActivity.this.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                        null, orderBy);
                int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
                count = imagecursor.getCount();
                thumbnails = new Bitmap[count];
                arrPath = new String[count];
                thumbnailsselection = new boolean[count];
                for (int i = 0; i < count; i++) {
                    imagecursor.moveToPosition(i);
                    int id = imagecursor.getInt(image_column_index);
                    int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);

                    /*thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                            getApplicationContext().getContentResolver(), id,
                            MediaStore.Images.Thumbnails.MICRO_KIND, null);*/
                    arrPath[i] = imagecursor.getString(dataColumnIndex);
                    thumbnails[i]=BitmapHelper.decodeFile(new File(imagecursor.getString(dataColumnIndex)),100,100,false);
                }
                imagecursor.close();
                mainHandler.sendMessage(mainHandler.obtainMessage(2));
                // uploadFile(selectedFiles.get(countUpload), WebServices.addPhotos,params);
            }

        }).start();

    }
   /* void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files) {
            fileList.add(file.getPath());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.myspinner_style,fileList);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        selection_sp.setAdapter(adapter);
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_photos:
                hideShow(true);
                init_data();
                break;
            case R.id.selectedsize:
                hideShow(false);
                break;
            case R.id.addtoalbum:
                boolean alltrue=true;
                Log.i("SIZE MODEL","SIZE="+imagesmodel.size());
                for (ImageModel img:imagesmodel) {
                    if(img.getCaption()!=null){
                        continue;
                    }
                    else{
                        alltrue=false;
                        break;
                    }
                }
                if(alltrue){
                   uploadImages();
                }
                else
                    Toast.makeText(AddPhotoActivity.this,"Add  caption to all selected photos",Toast.LENGTH_LONG).show();
                break;
            case R.id.back_icon:
                finish();
                case R.id.cancelBtn:
                    hideShow(false);
                    break;
            default:
                break;
        }

    }

    public void hideShow(boolean value) {
        if (value) {
            getSupportActionBar().hide();
            selectView.setVisibility(View.GONE);
            selectionView.setVisibility(View.VISIBLE);
        } else {
            getSupportActionBar().show();
            selectView.setVisibility(View.VISIBLE);
            selectphotos.setVisibility(View.GONE);
            selectedgallery.setVisibility(View.VISIBLE);
            selectionView.setVisibility(View.GONE);
            if(selectedFiles.size()>0) {
                setSelectedgallery();
            }
            else {
                selectphotos.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setSelectedgallery() {
        imagesmodel=new ArrayList<ImageModel>();
        imagesmodel.clear();
        for (String path:selectedFiles) {
            ImageModel model=new ImageModel();
            model.setCaption(null);
            model.setpath(path);
            imagesmodel.add(model);
        }
        SelectedImageAdapter adapter = new SelectedImageAdapter(AddPhotoActivity.this, selectedFiles);
        selectedgallery.setAdapter(adapter);
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        DisplayMetrics display;
        int height, width;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            display = Util.getDisplay(AddPhotoActivity.this);
            height = display.heightPixels;
            width = display.widthPixels;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.gallery_item, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(((width / 4) - (int) getResources().getDimension(R.dimen._4sdp)), ((width / 4) - (int) getResources().getDimension(R.dimen._4sdp)));
                //  lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
                holder.imageview.setLayoutParams(lp);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkbox.setId(position);
            holder.imageview.setId(position);

            holder.checkbox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();

                    if (thumbnailsselection[id]) {
                        countthumb--;
                        //selectedImages.remove(thumbnails[position]);
                        selectedFiles.remove(arrPath[position]);
                        cb.setChecked(false);
                        thumbnailsselection[id] = false;

                    } else {
                        if (countthumb < 10) {
                            countthumb++;
                            //selectedImages.add(thumbnails[position]);
                            selectedFiles.add(arrPath[position]);
                            cb.setChecked(true);
                            thumbnailsselection[id] = true;
                        } else {
                            cb.setChecked(false);
                            showAlertMax();
                        }
                    }
                    textselect.setText("" + countthumb);
                }
            });
            holder.imageview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int id = v.getId();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
                    startActivity(intent);
                }
            });

           // Glide.with(AddPhotoActivity.this).load(Uri.parse(arrPath[position])).asBitmap().into(holder.imageview);
          holder.imageview.setImageBitmap(thumbnails[position]);
           // holder.imageview.setImageBitmap(BitmapHelper.decodeFile(new File(arrPath[position]),100,100,false));
            holder.checkbox.setChecked(thumbnailsselection[position]);
            holder.id = position;
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }

    private void showAlertMax() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Alert");
        builder.setMessage("Exceed Maximum Number of Selection");
        //builder.setPositiveButton("OK", null);
        builder.setNegativeButton("OK", null);
        builder.show();

    }

    //http://muscularstrength.com/creat_album.php?userid=135953&album_id=1281&caption=test&imgefile=$_FILES["frmFile"]
    private String uploadFile() {
        String response =null;

        String charset = "UTF-8";
       // String requestURL = "YOUR_URL";

        MultipartUtility multipart = null;
        try {
            multipart = new MultipartUtility(WebServices.addPhotos, charset);
            Log.i("USER ID","ID="+userObj.getUserId());
            multipart.addFormField("userid", userObj.getUserId());
            multipart.addFormField("album_id", photoparser.getData().getData().get(selectedalbum).getId());
            multipart.addFormField("caption", imagesmodel.get(countUpload).getCaption());
            multipart.addFilePart("frmFile", new File(selectedFiles.get(countUpload)));
            response = multipart.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        // String pathToOurFile = "/data/file_to_send.mp3";
        //String urlServer = "http://192.168.1.1/handle_upload.php";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile));
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();


            // Allow Inputs &amp; Outputs.
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST.
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            // connection.setRequestProperty("userNumber", sbParams.toString());
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"frmFile\";filename=\"" + "filename.jpg" + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // String name="amir";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"userid\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(params.get("userid")); // mobile_no is String variable
            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"album_id\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(params.get("album_id")); // mobile_no is String variable
            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            // outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"caption\"" + lineEnd);
            //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
            //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(params.get("caption")); // mobile_no is String variable
            outputStream.writeBytes(lineEnd);

            outputStream.writeBytes(twoHyphens + boundary + lineEnd);


            // Responses from the server (code and message)
            // serverResponseCode = connection.getResponseCode();
            //serverResponseMessage = connection.getResponseMessage();

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                connection.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }
        } catch (Exception ex) {
            //Exception handling
            return null;
        }*/
        return response;
    }

    //upload images to server
    private void uploadImages() {
        if(countUpload==0)
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
               /* HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                params.put("album_id", photoparser.getData().getData().get(selectedalbum).getId());
                params.put("caption", imagesmodel.get(countUpload).getCaption());*/
             String resp= uploadFile();
                try {
                    JSONObject json=new JSONObject(resp);
                    if(json!=null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            countUpload++;
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                        } else {
                            errorMessage = json.getString("data");
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("Response","R="+resp);
            }

        }).start();

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {



                switch (message.what) {
                    case 0:
                        pDialog.dismiss();
                        pDialog.cancel();
                        Toast.makeText(AddPhotoActivity.this,""+errorMessage,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if (countUpload < imagesmodel.size()) {
                            uploadImages();
                        }
                        else{
                            pDialog.dismiss();
                            pDialog.cancel();
                        }
                        break;
                    case 2:
                        pDialog.dismiss();
                        pDialog.cancel();
                        imageAdapter = new ImageAdapter();
                        imageGallery.setAdapter(imageAdapter);
                        break;
                }

            } catch (Resources.NotFoundException e) {

            }
        }
    };
}
