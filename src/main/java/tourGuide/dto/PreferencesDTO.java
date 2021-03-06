package tourGuide.dto;

public class PreferencesDTO {
	
	private String user;
	
	private int attractionProximity;
	
	private String currency;
	private int lowerPricePoint;
	private int highPricePoint;
	
	private int tripDuration;
	
	private int ticketQuantity;
	
	private int numberOfAdults;
	private int numberOfChildren;
	
	public PreferencesDTO() {
		super();
	}

	public PreferencesDTO(String user, int attractionProximity, String currency, int lowerPricePoint,
			int highPricePoint, int tripDuration, int ticketQuantity, int numberOfAdults, int numberOfChildren) {
		this.user = user;
		this.attractionProximity = attractionProximity;
		this.currency = currency;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getAttractionProximity() {
		return attractionProximity;
	}
	public void setAttractionProximity(int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getLowerPricePoint() {
		return lowerPricePoint;
	}
	public void setLowerPricePoint(int lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}
	public int getHighPricePoint() {
		return highPricePoint;
	}
	public void setHighPricePoint(int highPricePoint) {
		this.highPricePoint = highPricePoint;
	}
	public int getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}
	public int getTicketQuantity() {
		return ticketQuantity;
	}
	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	public int getNumberOfAdults() {
		return numberOfAdults;
	}
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	
}
