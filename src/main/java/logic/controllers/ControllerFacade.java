package logic.controllers;

import java.util.List;

import javafx.scene.image.Image;
import logic.ImageViewer;
import logic.LoggedUser;
import logic.beans.GroupBean;
import logic.beans.InterestsBean;
import logic.beans.LocationBean;
import logic.beans.MessageBean;
import logic.beans.AccommodationBean;
import logic.beans.UserChatBean;
import logic.beans.UserDataBean;
import logic.beans.UserTravelBean;
import logic.beans.GroupChatBean;
import logic.exceptions.BigDateException;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.EmptyListException;
import logic.exceptions.GroupNameTakenException;
import logic.exceptions.MissingAnswareException;
import logic.exceptions.ServerDownException;
import logic.graphiccontrollers.GraphicControllerChat;
import logic.servlets.ChatRenterServlet;
import logic.servlets.ChatTravellerServlet;

public class ControllerFacade {

	private ManageAnnouncementController controllerManage;
	private ChatController chatController;
	private GraphicControllerChat graphicChat;
	private BookTravelControl bookTravCtrl;
	private LoginController loginCtrl;
	private ImageViewer viewer;
	private UserDataBean dBean;
	
	public ControllerFacade() {
	}
	
	/*ChatController references methods*/
	
	public void callChatController(GraphicControllerChat reference) {
		this.graphicChat = reference;
		chatController = new ChatController(this);
	}
	
	@SuppressWarnings("exports")
	public ControllerFacade(ChatTravellerServlet chatTravellerServlet) {
		this.chatController = new ChatController(this);
	}
	
	@SuppressWarnings("exports")
	public ControllerFacade(ChatRenterServlet chatRenterServlet) {
		this.chatController = new ChatController(this);
	}

	public void createChat(String renter) {
		if (chatController == null) {
			chatController = new ChatController();
		}
		chatController.createChat(renter);
	}
	
	public List<UserChatBean> getUsers() {
		return chatController.retrieveUsers();
	}
	
	public List<GroupChatBean> getGroups() {
		return chatController.retrieveGroups();
	}
	
	public void sendMessage(MessageBean msg) {
		chatController.sendMessage(msg);
	}
	
	public void closeLastChat() {
		chatController.closeLastChat();
	}
	
	public void closeLastChatAndExit() {
		chatController.modificateMyStatus("offline");
		chatController.closeLastChat();
	}
	
	public void setOfflineStatus() {
		chatController.modificateMyStatus("offline");
	}
	
	public List<MessageBean> openChat(String receiver, ChatType type) {
		return chatController.openChat(receiver, type);
	}
	
	public void execute(String receiver, ChatType type) throws ServerDownException {
		chatController.execute(receiver, type);
	}
	
	public void createGroup(GroupChatBean group) throws GroupNameTakenException {
		chatController.createGroup(group);
	}
	
	public void updateUserList(List<UserChatBean> users) {
		if (graphicChat!=null) {
			graphicChat.updateUserList(users);
		}
	}
	
	public void addToChat(MessageBean message) {
		if (graphicChat!=null) {
			graphicChat.addToChat(message);
		}
	}
	
	public void addAsServer(MessageBean message) {
		if (graphicChat!=null) {
			graphicChat.addAsServer(message);
		}
	}
	
	public UserChatBean getUser(String user) {
		return chatController.retrieveUser(user);
	}
	
	/*ManageAnnouncementController references methods*/

	public List<AccommodationBean> retrieveMyAnnouncement() {
		if (controllerManage == null) {
			controllerManage = new ManageAnnouncementController();
		}
		return controllerManage.retrieveMyAnnouncement();	
	}
	
	public void deleteMyAccommodation(long id) {
		if (controllerManage == null) {
			controllerManage = new ManageAnnouncementController();
		}
		controllerManage.deleteMyAccommodation(id);
	}
	
	public List<AccommodationBean> retrieveAnnouncement() {
		RentAccommodationController controllerRent = new RentAccommodationController();
		return controllerRent.retrieveAnnouncement();
	}
	
	/*CreateAccomodation references methods*/
	
	public void createAccommodation(AccommodationBean bean) {
		if (controllerManage == null) {
			controllerManage = new ManageAnnouncementController();
		}
		controllerManage.createAccommodation(bean);
	}
	
	public Image loadImage(byte[] bs) {
		if (viewer == null) {
			this.viewer = new ImageViewer();
		}
		return viewer.loadImage(bs);
	}
	
	/*BookTravelControl references methods*/
	
	public void findTravelSugg(List<String> suggLoc) {
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.dBean.setPersonality(LoggedUser.getPersonality());
		this.bookTravCtrl = new BookTravelControl();
		suggLoc.addAll(this.bookTravCtrl.showLocationsControl(dBean));
	}
	
	public void findSuggGroups(List<GroupBean> gBeanList) {
		if(this.bookTravCtrl == null) {
			this.bookTravCtrl = new BookTravelControl();
		}
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.dBean.setPersonality(LoggedUser.getPersonality());
		this.bookTravCtrl.getSuggestedGroupsControl(gBeanList, dBean);
	}
	
	public void getAvailableTick(List<String> depCities, List<String> arrCities) {
		if(this.bookTravCtrl == null) {
			this.bookTravCtrl = new BookTravelControl();
		}
		this.bookTravCtrl.setAvailableTick(depCities, arrCities);
	}
	
	public void retrieveLocInfo(LocationBean bean) {
		this.bookTravCtrl = new BookTravelControl();
		this.bookTravCtrl.retrieveLocInfoControl(bean);
	}
	
	public void retrieveTravelSolutions(UserTravelBean travBean, List<UserTravelBean> travList) throws BigDateException, EmptyListException {
		this.bookTravCtrl = new BookTravelControl();
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.bookTravCtrl.retrieveTravelSolutionsControl(dBean, travBean, travList);
	}
	
	public void saveBoughtTicket(UserTravelBean travBean) {
		this.bookTravCtrl = new BookTravelControl();
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.bookTravCtrl.saveBoughtTicketControl(travBean, dBean);
	}
	
	public void getSimilarUsers(List<UserDataBean> usrList) {
		if(this.bookTravCtrl == null) {
			this.bookTravCtrl = new BookTravelControl();
		}
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.dBean.setPersonality(LoggedUser.getPersonality());
		this.bookTravCtrl.getSamePersUsersControl(usrList, dBean);
	}
	
	public void getUsersGroups(List<GroupBean> grpBean) {
		if(this.bookTravCtrl == null) {
			this.bookTravCtrl = new BookTravelControl();
		}
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.bookTravCtrl.getUserGroupsControl(grpBean, dBean);
	}
	
	public void getBookedTicks(List<UserTravelBean> travBeanList) {
		if(this.bookTravCtrl == null) {
			this.bookTravCtrl = new BookTravelControl();
		}
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.bookTravCtrl.getBookedTicketsControl(travBeanList, dBean);
	}
	
	public void saveGroup(GroupBean grpBean) throws GroupNameTakenException {
		this.bookTravCtrl = new BookTravelControl();
		this.bookTravCtrl.saveGroupControl(grpBean);
	}
	
	public List<UserTravelBean> getSuggTicketsInfo(UserTravelBean travBean) throws EmptyListException {
		this.bookTravCtrl = new BookTravelControl();
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		return this.bookTravCtrl.getSuggTicketsInfoControl(travBean, dBean);
	}
	
	public int insertParticipant(GroupBean bean) {
		this.bookTravCtrl = new BookTravelControl();
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		return this.bookTravCtrl.insertParticipantControl(bean, dBean);
	}
	
	public void deleteSavedTravel(UserTravelBean travBean) {
		this.bookTravCtrl = new BookTravelControl();
		this.dBean = new UserDataBean(LoggedUser.getUserName());
		this.bookTravCtrl.deleteSavedTravelControl(travBean, dBean);
	}
	
	public void deleteTravelGroup(GroupBean grpBean) {
		this.bookTravCtrl = new BookTravelControl();
		if(LoggedUser.getUserName().equalsIgnoreCase(grpBean.getGroupOwner())) {
			this.bookTravCtrl.deleteTravelGroupControl(grpBean);
		}
		else {
			this.dBean = new UserDataBean(LoggedUser.getUserName());
			this.bookTravCtrl.leaveTravelGroupControl(grpBean, this.dBean);
		}
	}
	
	/* methods calls of the Interest Controller*/
	
	public String evaluateInterests(InterestsBean interBean) throws MissingAnswareException {
		InterestsController intCtrl = new InterestsController();
		return intCtrl.evaluateInterestsControl(interBean);
	}
	
	/* methods calls of the Login Controller*/
	
	public int checkLogIn(UserDataBean logBean) {
		this.loginCtrl = new LoginController();
		return this.loginCtrl.checkLogInControl(logBean);
	}
	
	public boolean insertNewUser(UserDataBean usrBean) throws DuplicateUsernameException {
		this.loginCtrl = new LoginController();
		return this.loginCtrl.insertNewUserControl(usrBean);
	}
}
