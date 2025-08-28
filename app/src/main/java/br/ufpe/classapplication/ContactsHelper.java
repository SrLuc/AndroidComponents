package br.ufpe.classapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsHelper {

    public static void showContact(Context context, int index, TextView textView) {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null && cursor.moveToPosition(index)) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            cursor.close();
            Toast.makeText(context, "Contato: " + name, Toast.LENGTH_SHORT).show();
            textView.setText("Contato: " + name); // mostra na UI
        }
    }

}
