package controllertests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import logic.LoggedUser;
import logic.beans.AccommodationBean;
import logic.controllers.ControllerFacade;
import logic.exceptions.LengthFieldException;

public class TestManageAccomodationsController {
	
	ControllerFacade facade;
	LoggedUser logUser;

	public TestManageAccomodationsController() {
		facade = new ControllerFacade();
	}
	
	@Test
	public void createAccomodationAndCheck() {
		
		LoggedUser.setUserName("Renter");
		
		AccommodationBean bean = new AccommodationBean();
		bean.setBeds("6");
		try {
			bean.setCity("Rome");
			bean.setAddress("Via Cavour 56");
			bean.setDescription("Newly renovated flat in a beautiful Victorian building just a minute away from the tube and the high street.\r\n" + 
					"\r\n" + 
					"The flat is situated on the top floor of a charming building and has an open plan kitchen living room, 2 bedrooms with double beds and a brand new marble bathroom.");
			bean.setRenter(LoggedUser.getUserName());
		} catch (LengthFieldException e) {
			Logger.getLogger("WIG").log(Level.SEVERE, ()-> e.getMessage());
		}
		bean.setSquareMetres("> 60");
		bean.setType("apartment");
		facade.createAccommodation(bean);
		
		List<AccommodationBean> listOfBean = facade.retrieveMyAnnouncement();
		if (listOfBean.isEmpty()) {
			Logger.getLogger("WIG").log(Level.SEVERE, "No accomodation has to been shown");
		}
		else {
			for (AccommodationBean beanReturned : listOfBean) {
				assertEquals(bean.getDescription(), beanReturned.getDescription());
			}
		}
	}
	
	@Test
	public void updateAndDeleteAccomodation() {
		List<AccommodationBean> listOfBeanToUpdate = facade.retrieveMyAnnouncement();
		if (listOfBeanToUpdate.isEmpty()) {
			Logger.getLogger("WIG").log(Level.SEVERE, "No accomodation has to been shown");
		}
		else {
			File imageHouse = new File(System.getProperty("user.dir")+"/src/main/resources/images/houseExample.png");
			AccommodationBean beanReturned = listOfBeanToUpdate.get(0);
			beanReturned.setHouseImage(imageHouse);
			facade.createAccommodation(beanReturned);
		}
		List<AccommodationBean> listOfBeanUpdated = facade.retrieveMyAnnouncement();
		if (listOfBeanToUpdate.isEmpty()) {
			Logger.getLogger("WIG").log(Level.SEVERE, "No accomodation has to been shown");
		}
		else {
			for (AccommodationBean beanReturned : listOfBeanUpdated) {
				for (AccommodationBean beanUpdated : listOfBeanToUpdate) {			
					if (beanUpdated.getID() == beanReturned.getID()) {
						assertEquals(new String(Base64.getEncoder().encodeToString(beanUpdated.getHouseImage())), new String(Base64.getEncoder().encodeToString(beanReturned.getHouseImage())));
					}
				}
				facade.deleteMyAccommodation(beanReturned.getID());
			}
		}
	}
}
