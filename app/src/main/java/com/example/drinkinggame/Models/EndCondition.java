package com.example.drinkinggame.Models;

public class EndCondition {
	ConditionType type;
	double amount;

	public EndCondition(ConditionType type, double amount) {
		this.type = type;
		this.amount = amount;
	}

	public ConditionType getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}
}
