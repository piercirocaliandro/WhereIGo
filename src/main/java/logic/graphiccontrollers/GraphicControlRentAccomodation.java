package logic.graphiccontrollers;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.ImageViewer;
import logic.beans.RentAccomodationBean;
import logic.controllers.RentAccomodationController;
import logic.view.BasicGui;

public class GraphicControlRentAccomodation extends BasicGui{
	
	@FXML private ListView<HBox> accomodationList;
	@FXML private HBox hBox;
	
	//details
	@FXML private TextArea description;
	@FXML private TextField cityDetail;
	@FXML private TextField address;
	@FXML private TextField bedsDetail;
	@FXML private TextField type;
	@FXML private TextField squareMetres;
	@FXML private TextField renter;
	@FXML private ImageView houseDetail;
	@FXML private Text garden;
	@FXML private Text wifi;
	@FXML private Text bathroom;
	@FXML private Text kitchen;
	@FXML private Text gardenText;
	@FXML private Text wifiText;
	@FXML private Text bathroomText;
	@FXML private Text kitchenText;
	@FXML private Button contactRenter;
	@FXML private TextField rating;
	
	
	private RentAccomodationController controller;
	private ImageViewer viewer;
	private int number = 0;
	
	
	@FXML
	public void initialize() {
		controller = new RentAccomodationController();
		this.userImage.setImage(logUsr.getImage());
		List<RentAccomodationBean> listOfBean = controller.displayAnnouncement();
		for (RentAccomodationBean bean : listOfBean) {
			setDisplayInfo(bean);
		}
	}
	
	public GraphicControlRentAccomodation() {
		viewer = new ImageViewer();
	}
	
	public void setDisplayInfo(RentAccomodationBean bean) {
		BorderPane pane = new BorderPane();
		Text city = new Text();
		city.setText("City  ");
		Text cityValue = new Text();
		cityValue.setText(bean.getCity());
		HBox cityBox = new HBox();
		cityBox.getChildren().addAll(city, cityValue);
		VBox vBox = new VBox();
		HBox bedsBox = new HBox();
		vBox.getChildren().add(bedsBox);
		Text beds = new Text();
		beds.setText("Beds  ");
		Text numberBeds = new Text();
		numberBeds.setText(bean.getBeds());
		bedsBox.getChildren().addAll(beds, numberBeds);
		ImageView house = new ImageView();
		BufferedImage bufImage = viewer.loadImage(bean.getHouseImage());
		house.setFitHeight(150);
		house.setFitWidth(150);
		house.setX(25);
		house.setImage(viewer.convertToFxImage(bufImage));
		Text rating2 = new Text();
		rating2.setText("Rating  ");
		Text ratingValue = new Text();		
		ratingValue.setText("5/5");
		HBox ratingBox = new HBox();
		ratingBox.getChildren().addAll(rating2, ratingValue);
		vBox.getChildren().add(ratingBox);
		Button details = new Button();
		details.setText("View Details");
		details.setOnMouseClicked(e -> 
    		setDetail(bean));
		HBox detailBox = new HBox();
		detailBox.getChildren().add(details);
		detailBox.setAlignment(Pos.CENTER_RIGHT);
		pane.setTop(cityBox);
		pane.setCenter(house);
		pane.setRight(vBox);
		pane.setBottom(detailBox);
		pane.setBorder(new Border(new BorderStroke(Color.BLUE, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		if (number == 0) {
			hBox = new HBox();
			hBox.getChildren().add(pane);
			accomodationList.getItems().addAll(hBox);
		}
		else {
			hBox.getChildren().add(pane);
		}
		number = (number+1)%2;
	}
	
	public void setDetail(RentAccomodationBean bean) {
		description.setVisible(true);
		description.setText(bean.getDescription());
		cityDetail.setVisible(true);
		cityDetail.setText(bean.getCity());
		address.setVisible(true);
		address.setText(bean.getAddress());
		bedsDetail.setVisible(true);
		bedsDetail.setText(bean.getBeds());
		type.setVisible(true);
		type.setText(bean.getType());
		squareMetres.setVisible(true);
		squareMetres.setText(bean.getSquareMetres());
		renter.setVisible(true);
		renter.setText(bean.getRenter());
		houseDetail.setVisible(true);
		BufferedImage bufImage = viewer.loadImage(bean.getHouseImage());
		houseDetail.setFitHeight(180);
		houseDetail.setFitWidth(350);
		houseDetail.setImage(viewer.convertToFxImage(bufImage));
		
		setServices(bean);
		
		contactRenter.setVisible(true);		
	}
	
	public void setServices(RentAccomodationBean bean) {
		
    	garden.setVisible(true);
    	gardenText.setVisible(true);
    	wifi.setVisible(true);
    	wifiText.setVisible(true);
    	bathroom.setVisible(true);
    	bathroomText.setVisible(true);
    	kitchen.setVisible(true);
    	kitchenText.setVisible(true);
    	byte[] list = bean.getServices();
    	if (bean.getServices() != null) {
    		for (int i = 0; i <= 3; i++) {
    			if (list[0] == 1) {
    				garden.setText("SI ");
    			}
    			else {
    				garden.setText("NO");
    			}
    			if (list[1] == 1) {
    				wifi.setText("SI ");
    			}
    			else {
    				wifi.setText("NO");
    			}
    			if (list[2] == 1) {
    				bathroom.setText("SI ");
    			}
    			else {
    				bathroom.setText("NO");
    			}
    			if (list[3] == 1) {
    				kitchen.setText("SI ");
    			}
    			else {
    				kitchen.setText("NO");
    			}
    		}
		}
	}
	
	public void contactRenter(MouseEvent event) {
		controller.createChat(renter.getText());
		setScene("ChatView.fxml");
		loadScene();
		nextGuiOnClick(event);
	}
}

