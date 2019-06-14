package com.example.drinkinggame.Models;

import java.util.LinkedList;
import java.util.List;

public class Package {
	private List<Card> cards;
	private String name;

	public Package(String name, List<Card> cards) {
		this.cards = cards;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Package() {
		cards = new LinkedList<>();
	}

	public List<Card> getCards() {
		return cards;
	}


	/**
	 * returns the amount of cards in this package
	 * @return amount of cards
	 */
	public int getSize() {
		return cards.size();
	}

	/**
	 * add a new card
	 * @param newCard the card to add
	 * @return the addet card
	 */
	public Card addCard(Card newCard) {
		cards.add(newCard);
		return newCard;
	}
	public Card removeCard(Card removeCard) {
		cards.remove(removeCard);
		return removeCard;
	}

}
