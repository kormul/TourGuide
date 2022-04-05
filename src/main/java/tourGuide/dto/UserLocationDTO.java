package tourGuide.dto;

import java.util.UUID;

import tourGuide.model.location.Location;

public class UserLocationDTO {
	
	private final UUID userId;
    private Location location;
    
	public UserLocationDTO(UUID userId, Location location) {
		this.userId = userId;
		this.location = location;
	}

	public UserLocationDTO(UUID userId, double latitude, double longitude) {
		
		this.userId = userId;
		this.location = new Location(latitude, longitude);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UUID getUserId() {
		return userId;
	}
	
}
