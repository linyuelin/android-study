package com.example.chapter07_client;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds;

import com.example.chapter07_client.entity.Contact;

import java.util.ArrayList;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_contact_name;
    private EditText et_contact_phone;
    private EditText et_contact_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_add);

        et_contact_name = findViewById(R.id.et_contact_name);
        et_contact_phone = findViewById(R.id.et_contact_phone);
        et_contact_email = findViewById(R.id.et_contact_email);
        findViewById(R.id.btn_add_contact).setOnClickListener(this);
        findViewById(R.id.btn_read_contact).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_contact){
            Contact contact = new Contact();
            contact.name = et_contact_name.getText().toString().trim();
            contact.phone = et_contact_phone.getText().toString().trim();
            contact.email = et_contact_email.getText().toString().trim();

            //メソッド１
//            addContacts(getContentResolver(),contact);

            //メソッド２
            addFullContacts(getContentResolver(),contact);
        } else if (v.getId() == R.id.btn_read_contact) {
            readPhoneContacts(getContentResolver());
        }
    }

    @SuppressLint("Range")
    private void readPhoneContacts(ContentResolver resolver) {
        Cursor cursor= resolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null);
       while (cursor.moveToNext()){
           int rawContactId = cursor.getInt(0);
           Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
           Cursor dataCursor = resolver.query(uri, new String[]{Contacts.Data.MIMETYPE, Contacts.Data.DATA1, Contacts.Data.DATA2}, null, null);
           Contact contact = new Contact();
           while (dataCursor.moveToNext()){
               String data1 = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.DATA1));
               String mimeType = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.MIMETYPE));

               switch (mimeType){
                   case CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                       contact.name = data1;
                       break;
                   case CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                       contact.email = data1;

                       break;case CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                       contact.phone = data1;
                       break;

               }
           }
           dataCursor.close();
           if(contact.name != null){
               Log.d("ning",contact.toString());
           }

       }
       cursor.close();
    }

    private void addFullContacts(ContentResolver resolver, Contact contact) {
       ContentProviderOperation op_main = ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
               .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,null)
                .build();

        ContentProviderOperation op_name = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID,0)
                .withValue(Contacts.Data.MIMETYPE,CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA2,contact.name)
                .build();

        ContentProviderOperation op_phone = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID,0)
                .withValue(Contacts.Data.MIMETYPE,CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1,contact.phone)
                .withValue(Contacts.Data.DATA2,CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        ContentProviderOperation op_email = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID,0)
                .withValue(Contacts.Data.MIMETYPE,CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1,contact.email)
                .withValue(Contacts.Data.DATA2,CommonDataKinds.Email.TYPE_WORK)
                .build();

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(op_main);
        operations.add(op_name);
        operations.add(op_phone);
        operations.add(op_email);

        try {
            resolver.applyBatch(ContactsContract.AUTHORITY,operations);
        } catch (OperationApplicationException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    private void addContacts(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

        ContentValues name = new ContentValues();
        name.put(Contacts.Data.RAW_CONTACT_ID,rawContactId);
        name.put(Contacts.Data.MIMETYPE,CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        name.put(Contacts.Data.DATA2,contact.name);
        resolver.insert(ContactsContract.Data.CONTENT_URI,name);


        ContentValues phone = new ContentValues();
        phone.put(Contacts.Data.RAW_CONTACT_ID,rawContactId);
        phone.put(Contacts.Data.MIMETYPE,CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        phone.put(Contacts.Data.DATA1,contact.phone);
        phone.put(Contacts.Data.DATA2,CommonDataKinds.Phone.TYPE_MOBILE);
        resolver.insert(ContactsContract.Data.CONTENT_URI,phone);


        ContentValues email = new ContentValues();
        email.put(Contacts.Data.RAW_CONTACT_ID,rawContactId);
        email.put(Contacts.Data.MIMETYPE,CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        email.put(Contacts.Data.DATA1,contact.email);
        email.put(Contacts.Data.DATA2,CommonDataKinds.Email.TYPE_WORK);
        resolver.insert(ContactsContract.Data.CONTENT_URI,email);
    }
}












