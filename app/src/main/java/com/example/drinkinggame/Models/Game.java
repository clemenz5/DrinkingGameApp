package com.example.drinkinggame.Models;

import java.util.LinkedList;
import java.util.List;

public class Game {
	private List<Package> packages;
	private List<Card> seenCards;
	private List<Card> unseenCards;
	private List<Card> allCards;
	private List<Player> players;
	private int currentCardIndex = -1;
	private int currentPlayerIndex = -1;

	public Game(List<Package> packages, List<Player> players) {
		this.packages = packages;
		this.players = players;
		unseenCards = new LinkedList<>();
		seenCards = new LinkedList<>();
		allCards = new LinkedList<>();
		for (Package currentPackage : packages) {
			unseenCards.addAll(currentPackage.getCards());
			allCards.addAll(currentPackage.getCards());
		}

		seenCards = new LinkedList<>();
	}

	/**
	 * this method is designed to return the next card in this deck of cards
	 *
	 * @return the next Card
	 * @throws IllegalArgumentException is thrown if currentCardIndex is smaller then -1
	 */
	public Card nextCard() {
		if ((currentCardIndex >= -1)) {
			if (allCards.size() - 1 <= currentCardIndex) {
				currentCardIndex = -1;
			}
			currentCardIndex++;
			return allCards.get(currentCardIndex);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * this method is designed to return the previous card in this deck of cards
	 *
	 * @return the previous Card
	 */
	public Card prevCard() {
		if (currentCardIndex >= -1) {
			if (currentCardIndex <= 0) {
				currentCardIndex = allCards.size() - 1;
				return allCards.get(currentCardIndex);
			} else {
				currentCardIndex--;
				return allCards.get(currentCardIndex);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * this method is designed to return the next Player in this round
	 *
	 * @return the next Player
	 * @throws IllegalArgumentException is thrown if currentPlayerIndex is smaller then -1
	 */
	public Player nextPlayer() {
		if ((currentPlayerIndex >= -1)) {
			if (players.size() - 1 <= currentPlayerIndex) {
				currentPlayerIndex = - 1;
			}
			currentPlayerIndex++;
			return players.get(currentPlayerIndex);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * this method is designed to return the previous Player in this round
	 *
	 * @return the previous Player
	 */
	public Player prevPlayer() {
		if (currentPlayerIndex >= -1) {
			if (currentPlayerIndex <= 0) {
				currentPlayerIndex = players.size() - 1;
				return players.get(currentPlayerIndex);
			} else {
				currentPlayerIndex--;
				return players.get(currentPlayerIndex);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

}
