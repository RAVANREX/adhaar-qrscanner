package com.example.qrapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    Button btnScan;
    TextView text_uid;
    TextView text_name;
    TextView text_vtc;
    String uid,name,vtc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_uid= findViewById(R.id.text_uid);
        text_name= findViewById(R.id.text_name);
        text_vtc= findViewById(R.id.text_vtc);

        btnScan = findViewById(R.id.btn_scan);


        System.out.println("HEllow World xssdjbsjkbjksdbvjsxjghsxjghsJKGsjkgvjs");
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        MainActivity.this
                );
                intentIntegrator.setPrompt("for flash press vol up key");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capure.class);
                intentIntegrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data);


        ////////////////////////////////////////


        try {




                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();


                Document doc = dBuilder.parse(new InputSource(new StringReader(intentResult.getContents())));
                Element element=doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("PrintLetterBarcodeData");

                for (int i=0; i<nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        System.out.println(">>>>>>>>UID>>>>>>>>>>>"+element2.getAttribute("uid"));
                        uid=element2.getAttribute("uid");
                        name=element2.getAttribute("name");
                        vtc=element2.getAttribute("vtc");
                        System.out.println(">>>>>>>>year of birth>>>>>>>>>>>"+element2.getAttribute("yob"));
                        System.out.println(">>>>>>>>Name >>>>>>>>>>>"+element2.getAttribute("name"));
                        System.out.println(">>>>>>>>error>>>>>>>>>>>"+element2.getAttribute("errror"));

                    }
                }











        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>>>>--IN catch<<<<<<<<<"+ e);

        }


        ////////////////////////////////////////

        if(intentResult.getContents()!=null){
//            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
//            builder.setTitle("Result");
//            builder.setMessage(intentResult.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//                    dialog.dismiss();
//                }
//            });
//            builder.show();

            text_uid.setText(uid);
            text_name.setText(name);
            text_vtc.setText(vtc);

        }else {
            Toast.makeText(getApplicationContext(),"OOps no bar code found",Toast.LENGTH_SHORT).show();
        }
    }
}