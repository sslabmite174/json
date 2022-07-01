package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class viewdataactivity extends AppCompatActivity {
    TextView xmlcontenttextview,jsoncontenttextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdataactivity);
        xmlcontenttextview = (TextView) findViewById(R.id.view_xml_content_id);
        jsoncontenttextview = (TextView) findViewById(R.id.view_json_content_id);
        Intent intent = getIntent();
        String datatype = intent.getStringExtra("datatype");
        if (datatype.equals("xml")) {
            //  xmlcontenttextview.setText("Test xml parsed content");
            xmlcontenttextview.setText("");
            try {
                InputStream is = getAssets().open("weather.xml");

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);

                Element element = (Element) doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("weather");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        xmlcontenttextview.setText(xmlcontenttextview.getText() + "\nCity Name : " + getValue("City_Name", element2) + "\n");
                        xmlcontenttextview.setText(xmlcontenttextview.getText() + "Latitude : " + getValue("Latitude", element2) + "\n");
                        xmlcontenttextview.setText(xmlcontenttextview.getText() + "Longitude : " + getValue("Longitude", element2) + "\n");
                        xmlcontenttextview.setText(xmlcontenttextview.getText() + "Temperature : " + getValue("Temperature", element2) + "\n");
                        xmlcontenttextview.setText(xmlcontenttextview.getText() + "Humidity : " + getValue("Humidity", element2) + "\n");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (datatype.equals("json")) {
//            String json;
//            StringBuilder stringBuilder = new StringBuilder();
//            try {
//                InputStream is = getAssets().open("weather.json");
//                int size = is.available();
//                byte[] buffer = new byte[size];
//                is.read(buffer);
//                json = new String(buffer, StandardCharsets.UTF_8);
//                JSONArray jsonArray = new JSONArray(json);
//                stringBuilder.append("JSON DATA");
//                stringBuilder.append("\n--------");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    stringBuilder.append("\nName: ").append(jsonObject.getString("name"));
//                    stringBuilder.append("\nLatitude: ").append(jsonObject.getString("lat"));
//                    stringBuilder.append("\nLongitude: ").append(jsonObject.getString("long"));
//                    stringBuilder.append("\nTemperature: ").append(jsonObject.getString("temperature"));
//                    stringBuilder.append("\nHumidity: ").append(jsonObject.getString("humidity"));
//                    stringBuilder.append("\n----------");
//                }
//                jsoncontenttextview.setText(stringBuilder.toString());
//                is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error in reading", Toast.LENGTH_LONG).show();
//            }
            try {
                InputStream inputStream = getAssets().open("weather.json");
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                String jsonString = new String(data);
                JSONObject emp=(new JSONObject(jsonString)).getJSONObject("city");
                String  cityname=emp.getString("City_Name");
                String Latitude=emp.getString("Latitude");
                String Longitude=emp.getString("Longitude");
                String Temperature=emp.getString("Temperature");
                String Humidity=emp.getString("Humidity");
                String str="City Name : "+cityname+"\n"+"Latitude : "+Latitude+"\n"+"Longitude : "+Longitude+"\n"+"Temperature : "+Temperature+"\n"+"Humidity : "+Humidity;
                jsoncontenttextview.setText(str);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


            //jsoncontenttextview.setText("Test json parsed content");
        }
    }


    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
