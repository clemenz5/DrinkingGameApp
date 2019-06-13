package com.example.drinkinggame.Models;

public class Card {
	String message;
	int sip;
	CardType type;

	public Card(String message, int sip, CardType type) {
		this.message = message;
		this.sip = sip;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public int getSip() {
		return sip;
	}

	public CardType getType() {
		return type;
	}
}
