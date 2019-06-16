package com.example.drinkinggame.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.drinkinggame.R;

public class NewCardActivity extends AppCompatActivity {
	EditText messageInput, sipInput;
	Spinner typeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_card);
		messageInput = findViewById(R.id.new_card_message);
		sipInput = findViewById(R.id.new_card_sip);
		typeSpinner = findViewById(R.id.new_card_type);


		String[]
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, );
// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
}
