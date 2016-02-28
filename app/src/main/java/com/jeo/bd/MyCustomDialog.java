package com.jeo.bd;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyCustomDialog extends Dialog {
    private String address;
    EditText adressName;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            MyCustomDialog.this.customDialogListener.back(String.valueOf(MyCustomDialog.this.adressName.getText()), String.valueOf(MyCustomDialog.this.portName.getText()), String.valueOf(MyCustomDialog.this.folderName.getText()));
            MyCustomDialog.this.dismiss();
        }
    };
    private OnCustomDialogListener customDialogListener;
    private String folder;
    EditText folderName;
    private String name;
    private String port;
    EditText portName;

    public MyCustomDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, OnCustomDialogListener paramOnCustomDialogListener) {
        super(paramContext);
        this.name = paramString1;
        this.address = paramString2;
        this.port = paramString3;
        this.folder = paramString4;
        this.customDialogListener = paramOnCustomDialogListener;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.dialog);
        setTitle(this.name);
        this.adressName = ((EditText) findViewById(R.id.adress));
        this.portName = ((EditText) findViewById(R.id.port));
        this.folderName = ((EditText) findViewById(R.id.folder));
        this.adressName.setText(this.address);
        this.portName.setText(this.port);
        this.folderName.setText(this.folder);
        ((Button) findViewById(R.id.clickbtn)).setOnClickListener(this.clickListener);
    }

    public static abstract interface OnCustomDialogListener {
        public abstract void back(String paramString1, String paramString2, String paramString3);
    }
}