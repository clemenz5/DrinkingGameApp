package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.drinkinggame.R;

public class NewCardActivity extends AppCompatActivity {
	public static final int REQUEST_CODE = 7583;
	private EditText messageInput, sipInput;
	private Spinner typeSpinner;
	private Button saveBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_card);
		messageInput = findViewById(R.id.new_card_message);
		sipInput = findViewById(R.id.new_card_sip);
		typeSpinner = findViewById(R.id.new_card_type);
		saveBtn = findViewById(R.id.new_card_save);


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"QUESTION","STATEMENT", "TASK"});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(adapter);
		saveBtn.setOnClickListener(view -> {
			if(messageInput.getText().toString().trim().isEmpty()){
				return;
			}
			if(sipInput.getText().toString().trim().isEmpty()){
				return;
			}
			Intent intent = new Intent();
			Bundle extraBundle = new Bundle();
			extraBundle.putString("message", messageInput.getText().toString().trim());
			extraBundle.putInt("sip", Integer.valueOf(sipInput.getText().toString().trim()));
			extraBundle.putString("type", (String)typeSpinner.getSelectedItem());
			intent.putExtras(extraBundle);
			setResult(RESULT_OK, intent);
			finish();
		});
	}
}
