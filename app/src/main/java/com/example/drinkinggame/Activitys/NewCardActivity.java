package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggame.R;

public class NewCardActivity extends AppCompatActivity {
	public static final int REQUEST_CODE = 7583;
	private TextView sipInfo, typeInfo;
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
		sipInfo = findViewById(R.id.new_card_sips_info_text);
		typeInfo = findViewById(R.id.new_card_type_info);

		sipInfo.setOnClickListener(v -> Toast.makeText(this,"das ist die Anzahl der Schlücke die bei dieser Karte getrunken werden müssen", Toast.LENGTH_LONG).show());
		typeInfo.setOnClickListener(v -> Toast.makeText(this,"das ist der Typ der Karte", Toast.LENGTH_LONG).show());


		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"QUESTION","STATEMENT", "TASK"});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(adapter);

		if(getIntent().getExtras() != null){
			messageInput.setText(getIntent().getExtras().getString("message"));
			sipInput.setText(String.valueOf(getIntent().getExtras().getInt("sip")));
			switch(getIntent().getExtras().getString("type")){
				case "QUESTION":
					typeSpinner.setSelection(0);
					break;
				case "STATEMENT":
					typeSpinner.setSelection(1);
					break;
				case "TASK":
					typeSpinner.setSelection(2);
					break;

			}
		}

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
			if(getIntent().getExtras() != null){
				extraBundle.putBoolean("change", true);
			}else{
				extraBundle.putBoolean("change", false);
			}
			intent.putExtras(extraBundle);
			setResult(RESULT_OK, intent);
			finish();
		});
	}
}
