package ca.carleton.comp3004.project.gameobjects;

public class Card {
	
	
	public enum CardType {
		Color, Supporter, Action;
		
		@Override
		public String toString() {
			switch(this) {
				case Color:
					return "Colored card";
				case Supporter:
					return "Supporter card";
				case Action:
					return "Action card";
				default:
					return "";
			}
		}
	}

	public enum CardColor {
		Purple, Green, Red, Blue, Yellow, White, None;
	}

	private CardColor cardColor;
	private CardType cardType;
	int value;
	private String cardName;
	
	public Card(CardType type, CardColor color, int value) {
		this.cardType = type;
		this.cardColor = color;
		this.value = value;
		
		switch(color) {
		case Purple:
			this.cardName = "Purple";
			break;
		case Green:
			this.cardName = "Green";
			break;
		case Blue:
			this.cardName = "Blue";
			break;
		case None:
			this.cardName = "Special";
			break;
		case Red:
			this.cardName = "Red";
			break;
		case White:
			if ((value == 2) || (value == 3)) this.cardName = "Squire";
			else this.cardName = "Maiden";
			break;
		case Yellow:
			this.cardName = "Yellow";
			break;
		default:
			break;
		}
	}
	
	public Card(CardType type, CardColor color, int value, String name) {
		this(type, color, value);
		this.cardName = name;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public CardColor getCardColor() {
		return this.cardColor;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getCardName() {
		return this.cardName;
	}
	
	@Override
	public String toString() {
		return "Type: " + this.getCardType() + " Color: " + this.getCardColor().toString() + " Value: " + this.value;
	}
	
	public boolean equals(Card aCard) {
		if ((this.cardColor == aCard.cardColor) && (this.cardName.equals(aCard.cardName)) && 
		(this.cardType.toString() == aCard.getCardType().toString()) && (this.value == aCard.getValue())) {
			return true;
		}
		return false;
	}
}
