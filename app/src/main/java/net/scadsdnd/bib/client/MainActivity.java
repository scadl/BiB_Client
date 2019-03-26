package net.scadsdnd.bib.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import android.support.v7.app.ActionBar;

public class MainActivity extends Activity {

    String servurl = "";
    TextView label1;
    ListView listView1;
    Spinner comboBox1;
    Spinner comboBox2;
    Spinner comboBox3;
    Context form1;
    String[] rid = null;
    String[] ridE = null;
    String[] rowad = null;
    Integer level = 0;
    String[] PArent = new String[5];
    String PArentDesc = "";
    MenuItem btnDel = null;
    MenuItem btnAdmin = null;
    MenuItem btnAddCat = null;
    String coreCat = "";
    String mainCat = "";
    Boolean newCat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences shrdPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        servurl = shrdPref.getString("server", "http://192.168.137.1/bib/");

        form1 = this;

        label1 = (TextView) findViewById(R.id.label1);
        listView1 = (ListView) findViewById(R.id.listView1);
        label1.setText("Соединяюсь...");

        ReqestData("0");

        listView1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, rid[position], Toast.LENGTH_SHORT).show();
                ReqestData(rid[position]);
                PArentDesc = (String) listView1.getItemAtPosition(position);

            }

        });

        final CheckBox cbCoreCat = (CheckBox) findViewById(R.id.cbCoreCat);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                EditText edtTitle = (EditText) findViewById(R.id.editTitle);
                EditText edtContact = (EditText) findViewById(R.id.editContact);
                //EditText edtExpire = (EditText) findViewById(R.id.editExpire);
                DatePicker edtExpire = (DatePicker) findViewById(R.id.datePicker);
                EditText edtPrice = (EditText) findViewById(R.id.editPrice);
                EditText edtPicURL = (EditText) findViewById(R.id.editImgUrl);
                EditText edtDescr = (EditText) findViewById(R.id.editDescr);

                try {
                    JSONObject jNewAd = new JSONObject();
                    jNewAd.put("subcatid", mainCat);
                    jNewAd.put("title", edtTitle.getText().toString());
                    jNewAd.put("contact", edtContact.getText().toString());
                    jNewAd.put("expire", Integer.toString(edtExpire.getYear()) + "-" + Integer.toString(edtExpire.getMonth()) + "-" + Integer.toString(edtExpire.getDayOfMonth()));
                    jNewAd.put("price", edtPrice.getText().toString());
                    jNewAd.put("picurl", edtPicURL.getText().toString());
                    jNewAd.put("descr", edtDescr.getText().toString());

                    level = 102;
                    ReqestData(jNewAd.toString());

                    Log.i("assets", "JSON: " + jNewAd.toString());
                } catch (Exception e) {
                    Log.e("log_tag", "Can't build JSON to upload: " + e.toString());
                }


                //Toast.makeText(MainActivity.this, "POS: "+pos+"|RID[pos]: "+rid[pos]+"|LVL: "+level, Toast.LENGTH_LONG).show();
            }
        });

        Button btnSubmitCat = (Button) findViewById(R.id.btnSubmitCat);
        btnSubmitCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                EditText edtTitle = (EditText) findViewById(R.id.editNewCat);

                try {

                    if (cbCoreCat.isChecked()) {
                        mainCat = "0";
                    }

                    JSONObject jNewAd = new JSONObject();
                    jNewAd.put("core", mainCat);
                    jNewAd.put("title", edtTitle.getText().toString());

                    level = 103;
                    ReqestData(jNewAd.toString());

                    Log.i("assets", "JSON: " + jNewAd.toString());
                } catch (Exception e) {
                    Log.e("log_tag", "Can't build JSON to upload: " + e.toString());
                }


                //Toast.makeText(MainActivity.this, "POS: "+pos+"|RID[pos]: "+rid[pos]+"|LVL: "+level, Toast.LENGTH_LONG).show();
            }
        });

        cbCoreCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    View llCat = (View) findViewById(R.id.llCat);
                    llCat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    //llCat.setVisibility(View.INVISIBLE);
                } else {
                    View llCat = (View) findViewById(R.id.llCat);
                    llCat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    //llCat.setVisibility(View.VISIBLE);
                }
            }
        });
        cbCoreCat.setText(R.string.cbCoreCat);


        comboBox1 = (Spinner) findViewById(R.id.catSpinner);
        comboBox1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                level = 96;
                ReqestData(rid[pos]);

                coreCat = rid[pos];
                //Toast.makeText(MainActivity.this, "POS: "+pos+"|RID[pos]: "+rid[pos]+"|LVL: "+level, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        comboBox2 = (Spinner) findViewById(R.id.catSpinnerE);
        comboBox2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                mainCat = ridE[pos];
                //Toast.makeText(MainActivity.this, "POS: "+pos+"|RID[pos]: "+rid[pos]+"|LVL: "+level, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        comboBox3 = (Spinner) findViewById(R.id.catSpinnerAdd);
        comboBox3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                mainCat = rid[pos];
                //Toast.makeText(MainActivity.this, "POS: "+pos+"|RID[pos]: "+rid[pos]+"|LVL: "+level, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void onBackPressed() {
        level -= 2;

        switch (level) {

            case -1:
                Toast.makeText(MainActivity.this, "Нажмите ещё раз 'Назад', чтобы выйти.", Toast.LENGTH_LONG).show();
                break;

            case -3:
                finish();
                System.exit(0);
                break;

            default:


                if (level > 10) {
                    level = 0;
                    ReqestData("0");
                } else {
                    ReqestData(PArent[level]);
                }

                ListView lv = (ListView) findViewById(R.id.listView1);
                lv.setVisibility(View.INVISIBLE);

                ScrollView sv1 = (ScrollView) findViewById(R.id.scrollView1);
                sv1.setVisibility(View.INVISIBLE);

                ScrollView sv2 = (ScrollView) findViewById(R.id.scrollView2);
                sv2.setVisibility(View.INVISIBLE);

                ScrollView sv3 = (ScrollView) findViewById(R.id.scrollView3);
                sv3.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);

        btnDel = menu.findItem(R.id.btnDel);
        btnAdmin = menu.findItem(R.id.btnAdmin);
        btnAddCat = menu.findItem(R.id.btnAddCat);
        //btnDel.setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    void PromtDialog(String dlgTitle, String dlgText, final int act) {
        AlertDialog.Builder dlgTpl = new AlertDialog.Builder(MainActivity.this);
        dlgTpl.setTitle(dlgTitle);
        dlgTpl.setMessage(dlgText);
        dlgTpl.setIcon(R.drawable.ic_action_warn);

        final EditText edtPass = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtPass.setLayoutParams(lp);

        if (act == 0) {
            String[] clnAddr = servurl.split("/");
            edtPass.setText(clnAddr[0] + "/" + clnAddr[1] + "/" + clnAddr[2]);
        } else {
            edtPass.setText("");
        }

        if (act > 1) {
            edtPass.setVisibility(4);
            dlgTpl.setView(null);
        } else {
            edtPass.setVisibility(0);
            dlgTpl.setView(edtPass);
        }

        dlgTpl.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlgAdmin, int id) {
                //Click OK
                ///return edtPass.getText().toString();
                switch (act) {
                    case 0:

                        servurl = edtPass.getText().toString() + "/bib/";
                        Toast.makeText(MainActivity.this, "Вы подключились к : " + edtPass.getText().toString(), Toast.LENGTH_LONG).show();

                        SharedPreferences shrdPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor prfEditor = shrdPref.edit();
                        prfEditor.putString("server", servurl);
                        prfEditor.commit();

                        level = 0;
                        ReqestData("0");

                        break;
                    case 1:
                        level = 100;
                        ReqestData(edtPass.getText().toString());
                        //Toast.makeText(MainActivity.this, "PAss: "+edtPass.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        // Del!
                        String delCat = PArent[level - 1] + "_" + Integer.toString(level - 1);
                        level = 101;
                        ReqestData(delCat);
                        break;
                }
            }
        });
        dlgTpl.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlgAdmin, int id) {
                //Click Cancel
                dlgAdmin.cancel();
            }
        });
        AlertDialog dlgReady = dlgTpl.create();
        dlgReady.show();
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnServer:

                PromtDialog("Смена подключения", "Введите новый адрес Сервера, включая проткол (HTTP/HTTPS)!", 0);
                return true;

            case R.id.btnAdmin:


                PromtDialog("Внимание!", "Введите пароль админа", 1);
                return true;

            case R.id.btnAdd:

                newCat = false;
                level = 95;
                ReqestData("0");

                return true;

            case R.id.btnDel:

                if (level <= 1) {
                    Toast.makeText(MainActivity.this, "Вы - в корне. Зайдите в категорию/объявление, чтобы стерть её!", Toast.LENGTH_LONG).show();
                } else {
                    //PromtDialog("Осторожно!", "Удалить  "+PArentDesc+":"+PArent[level-1]+" ?!",5);
                    PromtDialog("Осторожно!", "Удалить '" + PArentDesc + "' со всем содержимым?!", 5);
                }

                return true;

            case R.id.btnAddCat:

                ListView lv = (ListView) findViewById(R.id.listView1);
                lv.setVisibility(View.INVISIBLE);

                ScrollView sv1 = (ScrollView) findViewById(R.id.scrollView1);
                sv1.setVisibility(View.INVISIBLE);

                ScrollView sv2 = (ScrollView) findViewById(R.id.scrollView2);
                sv2.setVisibility(View.INVISIBLE);

                ScrollView sv3 = (ScrollView) findViewById(R.id.scrollView3);
                sv3.setVisibility(View.VISIBLE);

                newCat = true;
                level = 95;
                ReqestData("0");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ReqestData(String position) {

        RetriveData ReqData = new RetriveData();

        switch (level) {

            case 0:
                ReqData.execute(servurl + "category.php", position, "catname", "catid", Integer.toString(level));
                level += 1;
                PArent[0] = position;
                break;

            case 1:
                ReqData.execute(servurl + "category.php", position, "subcatname", "subcatid", Integer.toString(level));
                level += 1;
                PArent[1] = position;
                break;

            case 2:
                ReqData.execute(servurl + "category.php", position, "adtitle", "adid", Integer.toString(level));
                level += 1;
                PArent[2] = position;
                break;

            case 3:
                ReqData.execute(servurl + "category.php", position, "0", "0", Integer.toString(level));
                level += 1;
                PArent[3] = position;
                //listView1.setOnItemClickListener(null);
                break;

            default:
                ReqData.execute(servurl + "category.php", position, "0", "0", Integer.toString(level));
                break;

        }

        return;
    }

    class RetriveData extends AsyncTask<String, String, Integer> {

        String[] rows = null;
        Integer lvl = 0;

        protected void onPreExecute() {
            label1.setVisibility(0);
            listView1.setVisibility(4);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {

            InputStream htis = null;
            StringBuilder htsb = null;
            String resstr = null;
            JSONArray jArr = null;
            lvl = Integer.parseInt(params[4]);

			/*
			// ***********
			// Direct to DB way, using JDBC.
			// This will requre injecting jdbc-bytecode
            Connection conn = null;
            try{

                publishProgress("Запрашиваю данные (JDBC)...");

                String settDBServer = "10.0.3.2";
                String settDBUser = "uBib";
                String settDBPass = "198864";
                String settDBName = "_personal";
                String settDBPrefix = "xzclf_";


                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://"+settDBServer+"/"+settDBName, settDBUser, settDBPass);
                // https://stackoverflow.com/questions/24784871/how-to-mysql-jdbc-driver-to-android-studio

                try {

                    String rq = "SELECT * FROM "+settDBPrefix+"cats ORDER BY catid ASC;";
                    PreparedStatement prs = conn.prepareStatement(rq);
                    ResultSet res = prs.executeQuery();

                    res.last();
                    int resLn = res.getRow();
                    rows = new String[resLn];
                    rowad = new String[resLn];

                    Log.e("ROWS_COUNT:", Integer.toString(resLn));

                    if (lvl == 96) {
                        ridE = new String[resLn];
                    } else {
                        rid = new String[resLn];
                    }

                    int i = 0;
                    res.first();
                    while(res.next()){
                        i++;
                    }
                    
                } catch (SQLException s){
                    Log.e("SQL REQ ERR:", s.getMessage());
                }
            } catch (Exception e){
                Log.e("SQL INIT ERR:", e.getMessage());
            }
            */

            //******
            // WebApp way, using prepered php script to comminicate with db
            // Prepare POST request & connect
            try {

                publishProgress("Запрашиваю данные...");

                HttpClient htcl = new DefaultHttpClient();
                HttpPost hrq = new HttpPost(params[0]);
                //String respTxt = null;

                try {
                    List<NameValuePair> post_vars = new ArrayList<NameValuePair>(2);
                    post_vars.add(new BasicNameValuePair("parent", params[1]));
                    post_vars.add(new BasicNameValuePair("level", params[4]));
                    hrq.setEntity(new UrlEncodedFormEntity(post_vars));
                    HttpResponse reslt = htcl.execute(hrq);

                    HttpEntity htentit = reslt.getEntity();
                    htis = htentit.getContent();

                    Log.e("URL:",params[0]);
                    for (NameValuePair valPair : post_vars) {
                        Log.e("VAL:", valPair.getName() + ':' + valPair.getValue());
                    }
                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocolException", e.getMessage());
                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                    publishProgress("Сервер не отвечает.\nУбедитесь в правильности введнего адреса.");
                    cancel(false);
                }


            } catch (Exception e) {

                Log.e("Connction Error", e.toString());
                publishProgress("Проблема с соединением!\n\nВключите интренет.\n Проверьте адрес сервера.");
                cancel(false);
            }

            // Convert response
            try {

                publishProgress("Декодирую \nданные объявлений...");

                BufferedReader reder = new BufferedReader(new InputStreamReader(htis, "UTF-8"), 8);
                htsb = new StringBuilder();
                htsb.append(reder.readLine() + "\n");

                String ln = "0";
                while ((ln = reder.readLine()) != null) {
                    htsb.append(ln + "\n");
                }
                htis.close();
                resstr = htsb.toString();
                //publishProgress(resstr);


            } catch (Exception e) {

                Log.e("log_tag", "Error in convert result: " + e.toString());
                publishProgress("Не удалось разпознать ответ: \n" + e.toString());
                cancel(false);
            }

            // Make Json Array
            try {
                publishProgress("Разбираю данные...");

                jArr = new JSONArray(resstr);
                rows = new String[jArr.length()];
                rowad = new String[jArr.length()];

                if (lvl == 96) {
                    ridE = new String[jArr.length()];
                } else {
                    rid = new String[jArr.length()];
                }

                JSONObject json_data = null;
                for (int i = 0; i < jArr.length(); i++) {
                    json_data = jArr.getJSONObject(i);
                    //String ct_name = json_data.getString("catname");
                    //publishProgress(ct_name);
                    if (lvl < 3) {
                        rows[i] = json_data.getString(params[2]);
                        rowad[i] = json_data.getString("total_ads");
                        rid[i] = json_data.getString(params[3]);
                        //rows[i] = rows[i] + " - " + rowad[i];
                    }
                    if (lvl == 3) {
                        rows = new String[9];
                        rows[0] = json_data.getString("adid");
                        rows[1] = json_data.getString("adtitle");
                        rows[2] = json_data.getString("addesc");
                        rows[3] = json_data.getString("createdon").split(" ")[0];
                        rows[4] = json_data.getString("expireson").split(" ")[0];
                        //rows[5]=json_data.getString("timestamp").replace(" ", "\n");
                        rows[6] = json_data.getString("price");
                        rows[7] = json_data.getString("email");
                        rows[8] = json_data.getString("picfile");

                        //if ( servurl.indexOf("https:") != -1 ){
                        //	rows[8] = rows[8].replace("http:", "https:");
                        //}
                        Log.e("log_tag", "Picfile: " + rows[8]);
                    }
                    if (lvl == 95) {
                        rows[i] = json_data.getString("catname");
                        rid[i] = json_data.getString("catid");
                    }
                    if (lvl == 96) {
                        rows[i] = json_data.getString("subcatname");
                        ridE[i] = json_data.getString("subcatid");
                    }

                    if (lvl == 100) {
                        rows = new String[2];
                        rows[0] = json_data.getString("login_msg");
                        rows[1] = json_data.getString("login_result");
                    }
                    if (lvl == 101) {
                        rows[0] = json_data.getString("del_result");
                    }
                    if (lvl == 102) {
                        rows[0] = json_data.getString("submit_result");
                    }
                    if (lvl == 103) {
                        rows[0] = json_data.getString("submit_result");
                    }

                }
            } catch (JSONException e) {
                Log.e("log_tag", "JSON Excepton: " + e.toString() + '\n' + resstr + "LEVEL: " + lvl.toString());

                // if (rid[0]=="null"){
                // label1.setVisibility(0);
                // label1.setTextColor( getResources().getColor(R.color.Red) );
                // publishProgress("Сбой в распознании ответа: \n" + e.toString());
                //} else {
                publishProgress("Сбой в распознании ответа.\n\n Проверьте правильность \nуказанного протокла HTTP/HTTPS\n");
                //}

                cancel(true);

            } catch (ParseException e) {
                Log.e("log_tag", "Parse Exception: " + e.toString());
                publishProgress("Проблема разбора данных: \n" + e.toString());
                cancel(false);

            }


            if (isCancelled()) return null;

            return null;
        }

        protected void onProgressUpdate(String... values) {

            label1.setTextColor(getResources().getColor(android.R.color.black));
            label1.setText(values[0]);

            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Integer resl) {

            label1.setVisibility(4);

            if (lvl < 3) {

                ScrollView sV = (ScrollView) findViewById(R.id.scrollView1);
                sV.setVisibility(4);

                //Toast.makeText(MainActivity.this, Integer.toString(rowad.length), Toast.LENGTH_LONG).show();

                if (rid[0] != null) {

                    listView1.setVisibility(0);
                    ListAdapter rwada = new CatAdapter(form1, rows);
                    listView1.setAdapter(rwada);

                } else {

                }


            }

            if (lvl == 3) {

                ScrollView sV = (ScrollView) findViewById(R.id.scrollView1);
                sV.setVisibility(0);

                TextView lbTitle = (TextView) findViewById(R.id.labelTitle);
                lbTitle.setText(rows[1]);

                TextView lbPrice = (TextView) findViewById(R.id.labelPrice);
                lbPrice.setText("Цена: $" + rows[6]);

                TextView lbAdText = (TextView) findViewById(R.id.textAd);
                lbAdText.setText(rows[2]);

                TextView lbCr = (TextView) findViewById(R.id.labelCr);
                lbCr.setText(rows[3]);

                //TextView lbUpd = (TextView) findViewById(R.id.labelUpdt);
                //lbUpd.setText(rows[5]);

                TextView lbExp = (TextView) findViewById(R.id.labelExpir);
                lbExp.setText(rows[4]);

                TextView lbAuth = (TextView) findViewById(R.id.labelAuth);
                lbAuth.setText(rows[7]);

                ImageView imgVw = (ImageView) findViewById(R.id.iVw1);
                //imgVw.setImageBitmap(  )  (rows[7]);
                //ImageDownloader mdw = new
                //URI imguri = new URI( servurl + "../adpics/thumbs/tn_" + rows[8] );
                imgVw.setImageBitmap(getImageBitmap(rows[8]));

            }

            if (lvl == 95) {

                ScrollView sV = (ScrollView) findViewById(R.id.scrollView1);
                sV.setVisibility(View.INVISIBLE);

                listView1.setVisibility(View.INVISIBLE);


                if (newCat) {
                    ScrollView addForm = (ScrollView) findViewById(R.id.scrollView3);
                    addForm.setVisibility(View.VISIBLE);

                    ScrollView sV2 = (ScrollView) findViewById(R.id.scrollView2);
                    sV2.setVisibility(View.INVISIBLE);

                    ArrayAdapter<String> rwadas = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, rows);
                    rwadas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comboBox3.setAdapter(rwadas);
                } else {
                    ScrollView addForm = (ScrollView) findViewById(R.id.scrollView2);
                    addForm.setVisibility(View.VISIBLE);

                    ScrollView sV3 = (ScrollView) findViewById(R.id.scrollView3);
                    sV3.setVisibility(View.INVISIBLE);

                    ArrayAdapter<String> rwadas = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, rows);
                    rwadas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comboBox1.setAdapter(rwadas);
                }

                level = 2;
            }

            if (lvl == 96) {
                ArrayAdapter<String> rwadasE = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, rows);
                rwadasE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                comboBox2.setAdapter(rwadasE);

                level = 2;
            }

            if (lvl == 100) {
                level = 0;
                ReqestData("0");
                Toast.makeText(MainActivity.this, rows[0], Toast.LENGTH_LONG).show();
                if (Integer.parseInt(rows[1]) > 0) {
                    btnDel.setVisible(true);
                    btnAddCat.setVisible(true);
                    btnAdmin.setVisible(false);
                }
            }

            if (lvl == 101) {
                level = 0;
                ReqestData("0");
                Toast.makeText(MainActivity.this, "'" + PArentDesc + "' " + rows[0], Toast.LENGTH_LONG).show();
            }

            if (lvl == 102) {
                level = 0;
                ReqestData("0");

                ScrollView addForm = (ScrollView) findViewById(R.id.scrollView2);
                addForm.setVisibility(View.INVISIBLE);

                //Toast.makeText(MainActivity.this, "'" + PArentDesc + "' " + rows[0], Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, rows[0], Toast.LENGTH_LONG).show();
            }

            if (lvl == 103) {
                level = 0;
                ReqestData("0");

                ScrollView addForm = (ScrollView) findViewById(R.id.scrollView3);
                addForm.setVisibility(View.INVISIBLE);

                //Toast.makeText(MainActivity.this, "'" + PArentDesc + "' " + rows[0], Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, rows[0], Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(5);
        }

        protected void onCanceled() {

            super.onCancelled();
        }

    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Bitma_error", "Error getting bitmap", e);
        }
        return bm;
    }

    class CatAdapter extends ArrayAdapter<String> {

        public CatAdapter(Context context, String[] objects) {
            super(context, R.layout.list_entry, objects);
        }

        public View getView(int posaa, View conVw, ViewGroup parn) {

            LayoutInflater iflt = LayoutInflater.from(getContext());
            View vw = iflt.inflate(R.layout.list_entry, parn, false);

            TextView tVAdTit = (TextView) vw.findViewById(R.id.tVAtitle);
            tVAdTit.setText(getItem(posaa));

            if (posaa < rowad.length) {
                TextView tvAPri = (TextView) vw.findViewById(R.id.tVAdat);
                tvAPri.setText(rowad[posaa]);
                if (level == 3) {
                    tvAPri.setTextColor(getResources().getColor(R.color.Red));
                }
            }
            return vw;
        }

    }

}
