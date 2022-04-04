package tourGuide.model.user;

import java.util.UUID;

import tourGuide.model.location.Location;

public class UserNearAttaction extends Location{

	private String name;
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
    
	public UserNearAttaction() {
		super();
	}

	public UserNearAttaction(String name, String attractionName, String city, String state, UUID attractionId) {
		this.name = name;
		this.attractionName = attractionName;
		this.city = city;
		this.state = state;
		this.attractionId = attractionId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttractionName() {
		return attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UUID getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}
    
    
}
