package tourGuide.model.location;

public class Address {
	
	private String Address;
	private String Zip;
	private String City;
	private String Country;
	
	public Address() {
	}
	
	
	public Address(String address, String zip, String city, String country) {
		Address = address;
		Zip = zip;
		City = city;
		Country = country;
	}

	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}

}
