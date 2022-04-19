package tourGuide.model.location;

public class Address {
	
	private String address;
	private String zip;
	private String city;
	private String country;
	
	public Address() {
	}
	
	
	public Address(String address, String zip, String city, String country) {
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.country = country;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return this.zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

}
