package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drinkinggame.Models.Card;
import com.example.drinkinggame.Models.CardType;
import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import java.util.LinkedList;
import java.util.List;

public class PackageEditorActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private Package currentPackage;
	private Card currentCard;
	private Button addCardButton, removeCardButton;
	boolean change = false;
	PackageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_editor);
		Log.d("package_name", getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		recyclerView = findViewById(R.id.package_editor_card_list);
		addCardButton = findViewById(R.id.package_editor_add_card);
		removeCardButton = findViewById(R.id.package_editor_delete_card);

		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		currentPackage = FileTransfer.getPackage(getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		adapter = new PackageAdapter(currentPackage.getCards());
		recyclerView.setAdapter(adapter);

		addCardButton.setOnClickListener(view -> {
			//if(addCardButton.getText().equals("+")){
				startActivityForResult(new Intent(PackageEditorActivity.this, NewCardActivity.class), NewCardActivity.REQUEST_CODE);
			/*}else{
				for(Card card:adapter.selectedCards){
					currentPackage.removeCard(card);
				}
				adapter.setCards(currentPackage.getCards());
				adapter.setSelectedCards(new LinkedList<>());
				adapter.notifyDataSetChanged();
				change = true;
			}*/
		});


		removeCardButton.setOnClickListener(view -> {
			for(Card card:adapter.selectedCards){
				currentPackage.removeCard(card);
			}
			adapter.setCards(currentPackage.getCards());
			adapter.setSelectedCards(new LinkedList<>());
			adapter.notifyDataSetChanged();
			change = true;
		});
	}

	@Override
	public void onBackPressed() {
		if(change){
			FileTransfer.savePackage(currentPackage);
		}
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == NewCardActivity.REQUEST_CODE){
				if(data.getExtras().getBoolean("change")){
					currentPackage.removeCard(currentCard);
					Card newCard = new Card(data.getExtras().getString("message"), data.getExtras().getInt("sip"), CardType.valueOf(data.getExtras().getString("type")));
					currentPackage.addCard(newCard);
					currentCard = null;
					FileTransfer.savePackage(currentPackage);
				}else{
					Card newCard = new Card(data.getExtras().getString("message"), data.getExtras().getInt("sip"), CardType.valueOf(data.getExtras().getString("type")));
					currentPackage.addCard(newCard);
					FileTransfer.saveCardToPackage(currentPackage.getName(), newCard);
				}
				adapter.setCards(currentPackage.getCards());
				adapter.notifyDataSetChanged();
			}
		}
	}

	class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
		private List<Card> cards;
		private List<Card> selectedCards;

		PackageAdapter(List<Card> cards) {
			selectedCards = new LinkedList<>();
			this.cards = cards;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.package_list_item, viewGroup, false);

			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
			viewHolder.setMyCard(cards.get(i));
			if(selectedCards.contains(cards.get(i))){
				viewHolder.checkBox.setChecked(true);
			}else{
				viewHolder.checkBox.setChecked(false);
			}
		}

		@Override
		public int getItemCount() {
			return cards.size();
		}


		public void setCards(List<Card> cards) {
			this.cards = cards;
		}

		public void setSelectedCards(List<Card> selectedCards) {
			this.selectedCards = selectedCards;
		}

		class ViewHolder extends RecyclerView.ViewHolder {
			private TextView nameView;
			private CheckBox checkBox;
			private Card myCard;


			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				nameView = itemView.findViewById(R.id.package_list_name);
				checkBox = itemView.findViewById(R.id.package_list_check_box);
			}

			private void setMyCard(Card myCard) {
				this.myCard = myCard;
				populateData();
			}

			public void enableCheckBox(boolean bool) {
				if (bool) {
					checkBox.setVisibility(View.VISIBLE);
				} else {
					checkBox.setVisibility(View.GONE);
				}
			}

			private void populateData(){
				this.nameView.setText(this.myCard.getMessage());
				checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
					if(isChecked){
						selectedCards.add(myCard);
					}else{
						selectedCards.remove(myCard);
					}
				});

				nameView.setOnClickListener(v -> {
					currentCard = myCard;
					Intent intent = new Intent(PackageEditorActivity.this, NewCardActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("message", currentCard.getMessage());
					bundle.putInt("sip", currentCard.getSip());
					bundle.putString("type", currentCard.getType().toString());
					intent.putExtras(bundle);
					startActivityForResult(intent, NewCardActivity.REQUEST_CODE);
				});
			}
		}
	}


}