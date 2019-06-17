package com.example.drinkinggame.Models;

import java.util.Objects;

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

	@Override
	public String toString() {
		return message + ";" + sip + ";" + type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return sip == card.sip &&
				message.equals(card.message) &&
				type == card.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, sip, type);
	}
}
