package tourGuide.model.helper;

import org.springframework.beans.factory.annotation.Autowired;

import tourGuide.service.InternalTestHelperService;

public class InternalTestHelper {
	
	// Set this default up to 100,000 for testing
	private static int internalUserNumber = 100000;
	
	public static void setInternalUserNumbera(int internalUserNumber) {
		InternalTestHelper.internalUserNumber = internalUserNumber;
	}
	
	public static int getInternalUserNumber() {
		return internalUserNumber;
	}
}
