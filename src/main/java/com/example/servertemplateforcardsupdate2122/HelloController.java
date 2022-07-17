package com.example.servertemplateforcardsupdate2122;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.swing.JOptionPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import socketfx.Constants;
import socketfx.FxSocketServer;
import socketfx.SocketListener;

public class HelloController implements Initializable {
    boolean areReady = false;
    boolean clientReady = false;
    boolean dealer = false;
    boolean discardCrib = true;
    boolean placeRun = false;
    boolean cribChosen = false;
    boolean clientCribChosen = false;
    int count = 0;
    int pair = 0;
    int sequence = 0;
    boolean placeCombinations = false;

    @FXML
    private Button sendButton, ready;
    @FXML
    private TextField sendTextField;
    @FXML
    private Button connectButton, dealButton, placeButton;
    @FXML
    private TextField portTextField;
    @FXML
    private Label lblMessages, oppLabel, pLabel, instructLbl;
    @FXML
    private AnchorPane startingPane, playPane;
    @FXML
    private ImageView imgS0,imgS1,imgS2,imgS3,imgS4,imgS5,
            imgC0,imgC1,imgC2,imgC3,imgC4, imgC5, imgX0, imgX1, imgX2, imgX3,
            imgR0, imgR1, imgR2, imgR3, imgR4, imgR5, imgR6, imgR7;

    @FXML
    private Circle D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, D14, D15,
            D16, D17, D18, D19, D20, D21, D22, D23, D24, D25, D26, D27, D28, D29, D30,
            D31, D32, D33, D34, D35, D36, D37, D38, D39, D40, D41, D42, D43, D44, D45,
            D46, D47, D48, D49, D50, D51, D52, D53, D54, D55, D56, D57, D58, D59, D60,
            D61, D62, D63, D64, D65, D66, D67, D68, D69, D70, D71, D72, D73, D74, D75,
            D76, D77, D78, D79, D80, D81, D82, D83, D84, D85, D86, D87, D88, D89, D90,
            D91, D92, D93, D94, D95, D96, D97, D98, D99, D100, D101, D102, D103, D104, D105,
            D106, D107, D108, D109, D110, D111, D112, D113, D114, D115, D116, D117, D118, D119, D120,
            U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15, U16, U17, U18, U19, U20,
            U21, U22, U23, U24, U25, U26, U27, U28, U29, U30, U31, U32, U33, U34, U35, U36, U37, U38, U39, U40,
            U41, U42, U43, U44, U45, U46, U47, U48, U49, U50, U51, U52, U53, U54, U55, U56, U57, U58, U59, U60,
            U61, U62, U63, U64, U65, U66, U67, U68, U69, U70, U71, U72, U73, U74, U75, U76, U77, U78, U79, U80,
            U81, U82, U83, U84, U85, U86, U87, U88, U89, U90, U91, U92, U93, U94, U95, U96, U97, U98, U99, U100,
            U101, U102, U103, U104, U105, U106, U107, U108, U109, U110, U111, U112, U113, U114, U115, U116, U117,
            U118, U119, U120, T121, server1, server2, client1, client2;

    FileInputStream back1,tempCard;
    Image imageBack;
    Image imageFront;
    List<Card> deck = new ArrayList<>();
    Card discard;
    List<ImageView> hand1I = new ArrayList<>();
    List<ImageView> hand2I = new ArrayList<>();
    List<ImageView> cribI = new ArrayList<>();
    List<ImageView> runI = new ArrayList<>();
    List<Card> hand1D = new ArrayList<>();
    List<Card> hand2D = new ArrayList<>();
    List<Card> cribD = new ArrayList<>();
    List<Card> runD = new ArrayList<>();
    ArrayList<Card> clientRemove = new ArrayList<>();
    Effect shade = new Lighting();
    ArrayList<Circle> serverCircle = new ArrayList<>();
    ArrayList<Circle> clientCircle = new ArrayList<>();
    ArrayList<Integer> serverPos = new ArrayList<>();
    ArrayList<Integer> clientPos = new ArrayList<>();
    ArrayList<ArrayList<Card>> usedCombo = new ArrayList<>();

    private final static Logger LOGGER =
            Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private boolean isConnected;
    private int counter = 0;
    private String color;

    public enum ConnectionDisplayState {

        DISCONNECTED, WAITING, CONNECTED, AUTOCONNECTED, AUTOWAITING
    }

    private FxSocketServer socket;

    private void connect() {
        socket = new FxSocketServer(new FxSocketListener(),
                Integer.valueOf(portTextField.getText()),
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    private void displayState(ConnectionDisplayState state) {
//        switch (state) {
//            case DISCONNECTED:
//                connectButton.setDisable(false);
//                sendButton.setDisable(true);
//                sendTextField.setDisable(true);
//                break;
//            case WAITING:
//            case AUTOWAITING:
//                connectButton.setDisable(true);
//                sendButton.setDisable(true);
//                sendTextField.setDisable(true);
//                break;
//            case CONNECTED:
//                connectButton.setDisable(true);
//                sendButton.setDisable(false);
//                sendTextField.setDisable(false);
//                break;
//            case AUTOCONNECTED:
//                connectButton.setDisable(true);
//                sendButton.setDisable(false);
//                sendTextField.setDisable(false);
//                break;
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isConnected = false;
        displayState(ConnectionDisplayState.DISCONNECTED);




        Runtime.getRuntime().addShutdownHook(new ShutDownThread());

        /*
         * Uncomment to have autoConnect enabled at startup
         */
//        autoConnectCheckBox.setSelected(true);
//        displayState(ConnectionDisplayState.WAITING);
//        connect();
    }

    class ShutDownThread extends Thread {

        @Override
        public void run() {
            if (socket != null) {
                if (socket.debugFlagIsSet(Constants.instance().DEBUG_STATUS)) {
                    LOGGER.info("ShutdownHook: Shutting down Server Socket");
                }
                socket.shutdown();
            }
        }
    }
    @FXML
    private void handleConnectButton(ActionEvent event) {
        connectButton.setDisable(true);
        startingPane.setVisible(false);
        playPane.setVisible(true);
        displayState(ConnectionDisplayState.WAITING);
        connect();
    }
    //****************************************************************
    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            System.out.println("message received client " + line);
//            lblMessages.setText(line);
            if (line.equals("ready") && areReady){
                ready.setVisible(false);
                initGame();
            }else if(line.equals("ready")){
                clientReady=true;
            } else if(line.equals("dealt")){
                handleDeal();
            } else if(line.startsWith("cribCards") && cribChosen){
                clientCribChosen = true;
                clientRemove.add(new Card(line.substring(9)));
                System.out.println("boi u running");
                placeCard();
            } else if(line.startsWith("cribCards")){
                clientCribChosen = true;
                clientRemove.add(new Card(line.substring(9)));
            } else if(line.startsWith("runCard")){
                placeButton.setDisable(false);
                runD.add(new Card(line.substring(7)));
                setImages(true, runI, runD);
                setCount();
                if(pair > 0){
                    pair++;
                }
                if(sequence > 0){
                    sequence++;
                }
                checkPoints2();
                checkGo();
            }  else if(line.startsWith("pairFound")){
                pair = 0;
            } else if(line.startsWith("sequenceFound")){
                sequence = 0;
            } else if(line.startsWith("pair")){
                pair = 2;
            } else if (line.startsWith("null2")){
                client2.setFill(null);
            } else if (line.startsWith("null1")){
                client1.setFill(null);
            }else if(line.startsWith("clientPos")){
                int where = Integer.parseInt(line.substring(9));
                clientCircle.get(where).setFill(Color.DARKGREEN);
            } else if(line.startsWith("clearCircle")){
                int where = Integer.parseInt(line.substring(11));
                clientCircle.get(where).setFill(Color.WHITE);
            } else if(line.startsWith("sequence")){
                sequence = 3;
            } else if(line.startsWith("go")){
                placeButton.setDisable(false);
                if (pair > 0) {
                    movePeg(pair*2);
                }
                if(sequence > 0){
                    movePeg(sequence);
                }

                boolean go = true;
                for (int i = 0; i < hand1D.size(); i++) {
                    if(hand1D.get(i).getCardNumber() + count <= 31 && hand1I.get(i).isVisible()){
                        go = false;
                    }
                }

                if(go){
                    placeRun = false;
                    placeCombinations = true;
                    socket.sendMessage("countHands");
                    instructLbl.setText("Non-dealer counts the hand. Click on the label once you are done counting.");
                    runD.clear();
                    tempAdd.clear();
                    for (int i = 0; i < runI.size(); i++) {
                        runI.get(i).setImage(null);
                    }
                    for (int i = 0; i < invisibleCards.size(); i++) {
                        invisibleCards.get(i).setVisible(true);
                    }
                    for (int i = 0; i < hand1I.size(); i++) {
                        hand1I.get(i).setEffect(null);
                    }
                    if(!dealer){
                        placeButton.setDisable(false);
                    } else {
                        placeButton.setDisable(true);
                    }
                }
            } else if(line.startsWith("turnOver")){
                turnOver();
            } else if (line.startsWith("countOver")){
                placeButton.setDisable(false);
                instructLbl.setText("dealer counts the hand. Click on the label once you are done counting.");
                setImages(true, cribI, cribD);
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {

        }
    }

    @FXML
    private void handleSendMessageButton(ActionEvent event) {
        if (!sendTextField.getText().equals("")) {
            socket.sendMessage(sendTextField.getText());
            System.out.println("Message sent client");
        }
    }
    @FXML
    private void handleReady(ActionEvent event) {
//        if (!sendTextField.getText().equals("")) {
//            String x = sendTextField.getText();
//            socket.sendMessage(x);
//            System.out.println("sent message client");
//        }
        areReady=true;
        socket.sendMessage("ready");
        if (clientReady){
            ready.setVisible(false);
            initGame();
        }else{
            ready.setDisable(true);
        }
    }

    public void initGame(){
        if(Math.random() > .5){
            dealer = true;
            dealButton.setDisable(false);
            socket.sendMessage("non-dealer");
            oppLabel.setText("NON-DEALER");
            pLabel.setText("DEALER");
        } else {
            socket.sendMessage("dealer");
            dealButton.setDisable(true);
            oppLabel.setText("DEALER");
            pLabel.setText("NON-DEALER");
        }

        Collections.addAll(serverCircle, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, D14, D15,
                D16, D17, D18, D19, D20, D21, D22, D23, D24, D25, D26, D27, D28, D29, D30,
                D31, D32, D33, D34, D35, D36, D37, D38, D39, D40, D41, D42, D43, D44, D45,
                D46, D47, D48, D49, D50, D51, D52, D53, D54, D55, D56, D57, D58, D59, D60,
                D61, D62, D63, D64, D65, D66, D67, D68, D69, D70, D71, D72, D73, D74, D75,
                D76, D77, D78, D79, D80, D81, D82, D83, D84, D85, D86, D87, D88, D89, D90,
                D91, D92, D93, D94, D95, D96, D97, D98, D99, D100, D101, D102, D103, D104, D105,
                D106, D107, D108, D109, D110, D111, D112, D113, D114, D115, D116, D117, D118, D119, D120);

        Collections.addAll(clientCircle, U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12, U13, U14, U15, U16, U17, U18,
                U19, U20, U21, U22, U23, U24, U25, U26, U27, U28, U29, U30, U31, U32, U33, U34, U35, U36, U37, U38, U39, U40,
                U41, U42, U43, U44, U45, U46, U47, U48, U49, U50, U51, U52, U53, U54, U55, U56, U57, U58, U59, U60,
                U61, U62, U63, U64, U65, U66, U67, U68, U69, U70, U71, U72, U73, U74, U75, U76, U77, U78, U79, U80,
                U81, U82, U83, U84, U85, U86, U87, U88, U89, U90, U91, U92, U93, U94, U95, U96, U97, U98, U99, U100,
                U101, U102, U103, U104, U105, U106, U107, U108, U109, U110, U111, U112, U113, U114, U115, U116, U117,
                U118, U119, U120);

        server1.setFill(Color.DARKBLUE);
        server2.setFill(Color.DARKBLUE);
        client1.setFill(Color.DARKGREEN);
        client2.setFill(Color.DARKGREEN);
    }

    @FXML
    private void handleDeal(){
        placeCombinations = false;
        discardCrib = true;

        hand1I.clear();
        hand1I.add(imgS0);
        hand1I.add(imgS1);
        hand1I.add(imgS2);
        hand1I.add(imgS3);
        hand1I.add(imgS4);
        hand1I.add(imgS5);

        hand2I.clear();
        hand2I.add(imgC0);
        hand2I.add(imgC1);
        hand2I.add(imgC2);
        hand2I.add(imgC3);
        hand2I.add(imgC4);
        hand2I.add(imgC5);

        cribI.clear();
        cribD.clear();
        cribI.add(imgX0);
        cribI.add(imgX1);
        cribI.add(imgX2);
        cribI.add(imgX3);

        for (int i = 0; i < cribI.size(); i++) {
            cribI.get(i).setImage(null);
            cribI.get(i).setEffect(null);
        }

        for (ImageView x: hand2I) {
            x.setEffect(null);
        }

        runI.clear();
        runI.add(imgR0);
        runI.add(imgR1);
        runI.add(imgR2);
        runI.add(imgR3);
        runI.add(imgR4);
        runI.add(imgR5);
        runI.add(imgR6);
        runI.add(imgR7);

        imgC0.setImage(imageBack);
        imgC1.setImage(imageBack);
        imgC2.setImage(imageBack);
        imgC3.setImage(imageBack);
        imgC4.setImage(imageBack);
        imgC5.setImage(imageBack);

        imgS4.setVisible(true);
        imgS5.setVisible(true);
        imgC4.setVisible(true);
        imgC5.setVisible(true);

        tempAdd.clear();
        clientRemove.clear();
        deck.clear();
        //populate deck
        for(int i = 1;i<14;i++)
        {

            deck.add(new Card("C" +Integer.toString(i)));
            deck.add(new Card("S"+Integer.toString(i)));
            deck.add(new Card("H"+Integer.toString(i)));
            deck.add(new Card("D"+Integer.toString(i)));


        }

        //deal each hand
        hand2D.clear();
        hand1D.clear();
        //deal server hand
        System.out.println("Server hand");
        for(int i = 0;i<6;i++)
        {
            int y = (int)(Math.random()*(52-i));
            try {
                tempCard = new FileInputStream(deck.get(y).getCardPath());
                imageFront = new Image(tempCard);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            hand1I.get(i).setImage(imageFront);
            System.out.println(deck.get(y).getCardPath());
            hand1D.add(deck.remove(y));

        }
        //deal client hand
        System.out.println("Client hand");

        for(int i = 0;i<6;i++)
        {
            int y = (int)(Math.random()*(45-i));
            System.out.println(deck.get(y).getCardPath());
            hand2D.add(deck.remove(y));

        }

        socket.sendMessage("dealt");
        sendHandClient();
        instructLbl.setText("Pick 2 cards to discard to crib.");
        socket.sendMessage("discardCrib");
        placeButton.setDisable(false);
        dealButton.setDisable(true);
    }

    public void sendHandClient(){
        String temp = "cCards";
        socket.sendMessage("cCardStart");

        for (Card x: hand2D){
            temp+="," + x.getCardName();
            socket.sendMessage("cCards"+ x.getCardName());
        }

        socket.sendMessage("sCardNum"+hand1D.size());
    }

    public void placeCard(){
        if(discardCrib && tempAdd.size()>=2){
            cribChosen = true;
            System.out.println("place card function running");
            if(clientCribChosen && clientRemove.size()>=2){
                for (Card x: tempAdd){
                    socket.sendMessage("cribCards"+ x.getCardName());
                }

                socket.sendMessage("discarded");
                cribD.add(tempAdd.remove(0));
                hand1D.remove(cribD.get(0));
                cribD.add(tempAdd.remove(0));
                hand1D.remove(cribD.get(1));

//                System.out.println("size: " + clientRemove.size());

                cribD.add(clientRemove.remove(0));
                hand2D.remove(cribD.get(2));
                cribD.add(clientRemove.remove(0));
                hand2D.remove(cribD.get(3));

                for (int i = 0; i < 2; i++) {
                    hand1I.remove(4);
                    hand2I.remove(4);
                }

                imgS4.setVisible(false);
                imgS5.setVisible(false);
                imgC4.setVisible(false);
                imgC5.setVisible(false);

                for (int i = 0; i < hand1I.size(); i++) {
                    hand1I.get(i).setEffect(null);
                }

                setImages(false, cribI, cribD);
                setImages(true, hand1I, hand1D);
                discardCrib = false;
                placeRun = true;
                tempAdd.clear();

                if(dealer){
                    placeButton.setDisable(true);
                } else {
                    placeButton.setDisable(false);
                }

                instructLbl.setText("When it is your turn, place a card in the run.");
            } else {
                instructLbl.setText("Wait for player to place down 2 cards.");
            }
        } else if(placeRun && tempAdd.size() == 1){
            socket.sendMessage("runCard" + tempAdd.get(0).getCardName());

            //setting imageview to invisible
//            try {
//                tempCard = new FileInputStream(tempAdd.get(0).getCardPath());
//                imageFront = new Image(tempCard);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            for (ImageView x: hand1I) {
//                if(x.getImage().equals(imageFront)){
//                    x.setVisible(false);
//                }
//            }
            which.setVisible(false);
            invisibleCards.add(which);
            runD.add(tempAdd.get(0));
            setImages(true, runI, runD);
            placeButton.setDisable(true);
            setCount();
            checkPoints1();
            tempAdd.clear();
        } else if (placeCombinations){
            boolean repeat = false;
            for (int i = 0; i < usedCombo.size(); i++) {
                if(!(tempAdd.containsAll(usedCombo.get(i)) && usedCombo.get(i).containsAll(tempAdd))){
                    repeat = true;
                }
            }
            if(!repeat){
                usedCombo.add(tempAdd);
                int count = 0;
                for (int i = 0; i < tempAdd.size(); i++) {
                    count += tempAdd.get(i).getCardNumber();
                }

                //if combination totals in 15
                if(count == 15){
                    movePeg(2);
                }

                //pairs
                boolean paired = true;
                for (int i = 1; i < tempAdd.size(); i++) {
                    if(tempAdd.get(0).getCardNumber() != tempAdd.get(i).getCardNumber()){
                        paired = false;
                    }
                }

                if(paired){
                    movePeg(2*tempAdd.size());
                }

                //suit
                boolean suit = true;
                for (int i = 1; i < tempAdd.size(); i++) {
                    if(!tempAdd.get(0).getCardColor().equals(tempAdd.get(i).getCardColor())){
                        paired = false;
                    }
                }

                if(suit && tempAdd.size() >= 4){
                    movePeg(tempAdd.size());
                }

                //special card
                if(tempAdd.size()>0){
                    if(tempAdd.get(0).getCardName().equals("H7")){
                        movePeg(10);
                    }
                }
            }
            tempAdd.clear();
        }
    }

    public void setImages(boolean show, List<ImageView> arrI, List<Card> arrD){
        for (int i = 0; i < arrD.size(); i++) {
            if(show){
                try {
                    tempCard = new FileInputStream(arrD.get(i).getCardPath());
                    imageFront = new Image(tempCard);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                arrI.get(i).setImage(imageFront);
            } else {
                arrI.get(i).setImage(imageBack);
            }
        }
    }

    ArrayList<Card> tempAdd = new ArrayList<>();
    ArrayList<Node> invisibleCards = new ArrayList<>();
    Node which;
    public void cardClicked(MouseEvent t){
        int place = Integer.parseInt(t.getPickResult().getIntersectedNode().getId().substring(4));
        //checks whether or not the card is shaded if so then it will unshade the card if not then it will shade the card
        if(t.getPickResult().getIntersectedNode().getEffect() == shade){
            t.getPickResult().getIntersectedNode().setEffect(null);
            tempAdd.remove(hand1D.get(place));

        } else if(discardCrib && tempAdd.size() < 2 && !tempAdd.contains(hand1D.get(place))){
            t.getPickResult().getIntersectedNode().setEffect(shade);
            tempAdd.add(hand1D.get(place));

        } else if(placeRun && tempAdd.size() < 1 && !tempAdd.contains(hand1D.get(place)) && hand1I.get(place).isVisible() && (hand1D.get(place).getCardNumber() + count) <= 31){
            System.out.println("is you runnning or notttt");
            t.getPickResult().getIntersectedNode().setEffect(shade);
            tempAdd.add(hand1D.get(place));
            which = t.getPickResult().getIntersectedNode();
        } else if (placeCombinations && !tempAdd.contains(hand1D.get(place))){
            t.getPickResult().getIntersectedNode().setEffect(shade);
            tempAdd.add(hand1D.get(place));
        }
        if(placeRun){
            System.out.println(tempAdd.size() < 1);
            System.out.println(!tempAdd.contains(hand1D.get(place)));
            System.out.println(hand1I.get(place).isVisible());
            System.out.println((hand1D.get(place).getCardNumber() + count) <= 31);
        }
    }

    public void cribClicked(MouseEvent t){
        int place = Integer.parseInt(t.getPickResult().getIntersectedNode().getId().substring(4));
        if(t.getPickResult().getIntersectedNode().getEffect() == shade) {
            t.getPickResult().getIntersectedNode().setEffect(null);
            tempAdd.remove(hand1D.get(place));
        } else if (placeCombinations && !tempAdd.contains(cribD.get(place))){
            t.getPickResult().getIntersectedNode().setEffect(shade);
            tempAdd.add(cribD.get(place));
        }
    }

//    public void setHandImages(){
//        for (int i = 0; i < hand1D.size(); i++) {
//            try {
//                tempCard = new FileInputStream(hand1D.get(i).getCardPath());
//                imageFront = new Image(tempCard);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            hand1I.get(i).setImage(imageFront);
//        }
//
//    }

    public void setCount(){
        count = 0;
        for (Card x: runD) {
            count += x.getCardNumber();
        }

        instructLbl.setText("Count: " + count);
    }

    public void checkPoints1() {
        if(count == 15){
            System.out.println("count is 15");
            movePeg(1);
        }

        if(count == 31){
            System.out.println("count is 31");
            movePeg(2);
        }

        if(runD.size() >= 2){
            if (runD.get(runD.size() - 1).getCardNumber() == runD.get(runD.size() - 2).getCardNumber()) {
                if(pair == 0){
                    pair = 2;
                    socket.sendMessage("pair");
                    System.out.println("pair");

                } else {
                    pair ++;
                }
            }

            //cehcking if a kind of queen have been placed right next to each other
            if ((runD.get(runD.size() - 1).getCardNumber() == 12 && runD.get(runD.size() - 2).getCardNumber() == 13) ||
                    (runD.get(runD.size() - 2).getCardNumber() == 12 && runD.get(runD.size() - 1).getCardNumber() == 13)){
                System.out.println("marriage");
                movePeg(2);
            }

            //checking if there can be a sequence of 3
            if(runD.size()>=3){
                if(runD.get(runD.size() - 1).getCardNumber() != runD.get(runD.size() - 2).getCardNumber()
                        && runD.get(runD.size() - 1).getCardNumber() != runD.get(runD.size() - 3).getCardNumber()
                        && runD.get(runD.size() - 2).getCardNumber() != runD.get(runD.size() - 3).getCardNumber()) {
                    ArrayList<Integer> order = new ArrayList<>();
                    order.add(runD.get(runD.size() - 1).getCardNumber());
                    order.add(runD.get(runD.size() - 2).getCardNumber());
                    order.add(runD.get(runD.size() - 3).getCardNumber());
                    Collections.sort(order);

                    sequence = 3;

                    for (int i = 0; i < order.size()-1; i++) {
                        if(order.get(i+1) - order.get(i) != 1){
                            sequence = 0;
                        }
                    }

                    if(sequence == 3){
                        socket.sendMessage("sequence");
                    }
                }
            }
        }
    }

    public void checkPoints2() {
        if(pair > 0){
            for (int i = 2; i <= pair; i++) {
                if(runD.get(runD.size()-1).getCardNumber() != runD.get(runD.size()-i).getCardNumber()){
                    if(pair <= 4){
                        movePeg((pair-1)*2);
                        System.out.println("pair");
                    } else {
                        movePeg(12);
                        System.out.println("pair");
                    }
                    pair = 0;
                    socket.sendMessage("pairFound");
                }
            }
        }
        if(sequence > 0){
            boolean yes = true;
            ArrayList<Integer> order = new ArrayList<>();

            for (int i = 1; i < sequence; i++) {
                order.add(runD.get(runD.size() - i).getCardNumber());
                for (int j = i + 1; j <= sequence; j++) {
                    if(runD.get(runD.size() - i).getCardNumber() == runD.get(runD.size() - j).getCardNumber()){
                        yes = false;
                    }
                }
            }

            order.add(runD.get(runD.size() - sequence).getCardNumber());
            Collections.sort(order);

            for (int i = 0; i < order.size()-1; i++) {
                if(order.get(i+1) - order.get(i) != 1){
                    yes = false;
                }
            }

            if(!yes){
                movePeg((sequence-1));
                System.out.println("sequence");
                sequence = 0;
                socket.sendMessage("sequenceFound");
            }
        }
    }

    public void movePeg(int x){
        if(serverPos.size() == 0){
            System.out.println("empty run");
            server2.setFill(null);
            socket.sendMessage("null2");
            serverCircle.get(x-1).setFill(Color.DARKBLUE);
            serverPos.add(x-1);

        } else if (serverPos.size() == 1){
            server1.setFill(null);
            serverCircle.get(serverPos.get(0) + x).setFill(Color.DARKBLUE);
            serverPos.add(serverPos.get(0) + x);
            socket.sendMessage("null1");

        } else {

            if(serverPos.get(0) < serverPos.get(1)){
                serverCircle.get(serverPos.get(0)).setFill(Color.WHITE);
                socket.sendMessage("clearCircle" + serverPos.get(0));
                serverCircle.get(serverPos.get(1) + x).setFill(Color.DARKBLUE);
                serverPos.set(0, serverPos.get(1) + x);

            } else {
                serverCircle.get(serverPos.get(1)).setFill(Color.WHITE);
                socket.sendMessage("clearCircle" + serverPos.get(1));
                serverCircle.get(serverPos.get(0) + x).setFill(Color.DARKBLUE);
                serverPos.set(1, serverPos.get(0) + x);
            }
        }

        for (int i = 0; i < serverPos.size(); i++) {
            socket.sendMessage("serverPos" + serverPos.get(i));
            System.out.println();
        }
    }

    public void checkGo(){
        boolean go = true;
        for (int i = 0; i < hand1D.size(); i++) {
            if(hand1D.get(i).getCardNumber() + count <= 31 && hand1I.get(i).isVisible()){
                go = false;
            }
        }

        if(go){
            socket.sendMessage("go");
            placeButton.setDisable(true);
        }
    }

    public void countOver(){
        if(dealer){
            turnOver();
        } else {
            placeButton.setDisable(true);
            socket.sendMessage("countOver");
        }
    }

    public void turnOver(){
        if(dealer){
            dealer = false;
            dealButton.setDisable(true);
            socket.sendMessage("dealer");
            oppLabel.setText("DEALER");
            pLabel.setText("NON-DEALER");
        } else {
            dealer = true;
            dealButton.setDisable(false);
            instructLbl.setText("Deal the cards.");
            socket.sendMessage("non-dealer");
            oppLabel.setText("NON-DEALER");
            pLabel.setText("DEALER");
        }
        count = 0;
    }

    public HelloController(){
        try {
            back1 = new FileInputStream("src/main/resources/Images/BACK-1.jpg");
            imageBack = new Image(back1);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
