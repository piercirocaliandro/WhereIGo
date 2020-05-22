package logic.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import logic.beans.GroupBean;
import logic.exceptions.GroupNameTakenException;
import logic.exceptions.LengthFieldException;
import logic.exceptions.NullValueException;
import logic.view.BasicGui;
import logic.view.ErrorPopup;

public class GraphicControllerCreateGroup extends BasicGui{
	@FXML private TextField groupName;
	@FXML private TextField groupAdmin;
	@FXML private TextField groupDest;
	@FXML private Button createGroup;
	private GroupBean grpBean;
	private ErrorPopup err;
	
	@FXML
	public void initialize() {
		this.groupAdmin.setText(this.logUsr.getUserName());
		this.grpBean = new GroupBean();
		grpBean.setGroupOwner(this.logUsr.getUserName());
		this.err = new ErrorPopup();
		this.userImage.setImage(setUserImage());
	}
	
	public void saveUserGroup(MouseEvent event) {
		try {
			this.grpBean.setGroupTitle(this.groupName.getText());
			this.grpBean.setGroupDestination(this.groupDest.getText());
			checkValues(event);
		} catch (LengthFieldException e1) {
			this.err.displayLoginError(e1.getMsg());
		} catch (NullValueException e) {
			this.err.displayLoginError(e.getNullExcMsg());
		}
	}
	
	private void checkValues(MouseEvent e) {
		try {
			this.facade.saveGroup(this.grpBean);
			goHome(e);
		} 
		catch (GroupNameTakenException e1) {
			err.displayLoginError("Nome gruppo non disponibile");
		}
	}
}
