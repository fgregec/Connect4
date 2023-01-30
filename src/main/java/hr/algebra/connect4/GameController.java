package hr.algebra.connect4;

import hr.algebra.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameController {
    @FXML
    private Label lbPlayerName;
    @FXML
    private Button firstButton;
    @FXML
    private Button secondButton;
    @FXML
    private Button thirdButton;
    @FXML
    private Button fourthButton;
    @FXML
    private Button fifthButton;
    @FXML
    private Button sixthButton;
    @FXML
    private Button seventhButton;

    @FXML
    public  AnchorPane pane;

    //FIRST COLUMN
    @FXML
    private Circle _00;
    @FXML
    private Circle _10;
    @FXML
    private Circle _20;
    @FXML
    private Circle _30;
    @FXML
    private Circle _40;
    @FXML
    private Circle _50;


    List<Circle> firstColumn;


    //SECOND COLUMN
    @FXML
    private Circle _01;
    @FXML
    private Circle _11;
    @FXML
    private Circle _21;
    @FXML
    private Circle _31;
    @FXML
    private Circle _41;
    @FXML
    private Circle _51;

    List<Circle> secondColumn;

    //THIRD COLUMN
    @FXML
    private Circle _02;
    @FXML
    private Circle _12;
    @FXML
    private Circle _22;
    @FXML
    private Circle _32;
    @FXML
    private Circle _42;
    @FXML
    private Circle _52;

    List<Circle> thirdColumn;

    //FOURTH COLUMN
    @FXML
    private Circle _03;
    @FXML
    private Circle _13;
    @FXML
    private Circle _23;
    @FXML
    private Circle _33;
    @FXML
    private Circle _43;
    @FXML
    private Circle _53;

    List<Circle> fourthColumn;

    //FIFTH COLUMN
    @FXML
    private Circle _04;
    @FXML
    private Circle _14;
    @FXML
    private Circle _24;
    @FXML
    private Circle _34;
    @FXML
    private Circle _44;
    @FXML
    private Circle _54;

    List<Circle> fifthColumn;

    //SIXTH COLUMN
    @FXML
    private Circle _05;
    @FXML
    private Circle _15;
    @FXML
    private Circle _25;
    @FXML
    private Circle _35;
    @FXML
    private Circle _45;
    @FXML
    private Circle _55;

    List<Circle> sixthColumn;


    //SEVENTH COLUMN!!

    @FXML
    private Circle _06;
    @FXML
    private Circle _16;
    @FXML
    private Circle _26;
    @FXML
    private Circle _36;
    @FXML
    private Circle _46;
    @FXML
    private Circle _56;
    List<Circle> seventhColumn;

    private static final int ROWS_COUNT = 6;
    private static final int COLUMN_COUNT = 7;

    //private Slot[][] field = new Slot[ROWS_COUNT][COLUMN_COUNT];
    private Slot[][] field;

    private static final String PLAYER_ONE_COLOR = "#ff1313";

    private static final String PLAYER_TWO_COLOR = "#fffc5e";

    private boolean firstPlayerTurn = true;

    private boolean firstPlay = true;

    private static PlayerDetails playerOne;
    private static PlayerDetails playerTwo;

    static long startTime;

    private static List<PlayerLeaderboard> leaderboard;

    private int drawCounter;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button btnNewGame;

    private List<PlayerLeaderboard> tempBoard;

    private List<Move> movesXML;

    private static final String CLASS_EXTENSION = ".class";


    public void buttonClicked(ActionEvent event) throws IOException {
        Button button = (Button)event.getSource();
        displayLabel();
        if (firstPlay){
            initializeBoard();
        }
        switch (button.getText()) {
            case "1" -> handleFirstButton();
            case "2" -> handleSecondButton();
            case "3" -> handleThirdButton();
            case "4" -> handleFourthButton();
            case "5" -> handleFifthButton();
            case "6" -> handleSixthButton();
            case "7" -> handleSeventhButton();
            default -> System.exit(0);
        }
    }

    private void swapPlayers() {
        firstPlayerTurn = !firstPlayerTurn;
        displayLabel();
    }

    public void displayLabel() {
        if (firstPlayerTurn){
            lbPlayerName.setText(playerOne.getPlayerName() + "'s turn");
        }
        else{
            lbPlayerName.setText(playerTwo.getPlayerName() + "'s turn");
        }
    }

    public void initializeBoard() {
        field = new Slot[ROWS_COUNT][COLUMN_COUNT];
        for(int i = 0; i< ROWS_COUNT;i++){
            for(int j=0; j < COLUMN_COUNT;j++){
                field[i][j] = new Slot(false,null);
            }
        }
        initializeColumnLists();
        firstPlay = false;
        preparePlayers(playerOne, playerTwo);
        leaderboard = new ArrayList<>();
        startTime = System.currentTimeMillis();
        drawCounter = 0;
        movesXML = new ArrayList<>();
    }

    public void preparePlayers(PlayerDetails playerOne, PlayerDetails playerTwo) {
        this.playerOne = playerOne;
        this.playerOne.firstPlayer = true;
        this.playerTwo = playerTwo;
        this.playerTwo.firstPlayer = false;
    }

    private void insertIntoColumn(int column) throws IOException {
        for (int i= ROWS_COUNT - 1; i>=0; i--){
          if(!field[i][column].isUsed()){
              handleNewSlotInsert(column, firstPlayerTurn, i);
              field[i][column].setUsed(true);
              field[i][column].getOwner().setMoves(i,column);
              if (firstPlayerTurn) {
                  movesXML.add(new Move(i,column,PLAYER_ONE_COLOR));
              } else {
                  movesXML.add(new Move(i,column,PLAYER_TWO_COLOR));
              }
              checkWin(field);
              swapPlayers();
              drawCounter++;
              if (drawCounter == 42){
                  endGame(new PlayerDetails("Draw"));
              }
              return;
           }
        }
    }

    public void checkWin(Slot[][] board) throws IOException {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                PlayerDetails player = board[r][c].getOwner();
                if (player == null)
                    continue;
                if (c + 3 < WIDTH &&
                        player.equals(board[r][c+1].getOwner()) &&
                        player.equals(board[r][c+2].getOwner()) &&
                        player.equals(board[r][c+3].getOwner()))
                    endGame(player);
                if (r + 3 < HEIGHT) {
                    if (player.equals(board[r+1][c].getOwner()) &&
                            player.equals(board[r+2][c].getOwner()) &&
                            player.equals(board[r+3][c].getOwner()))
                        endGame(player);
                    if (c + 3 < WIDTH &&
                            player == board[r+1][c+1].getOwner() &&
                            player == board[r+2][c+2].getOwner() &&
                            player == board[r+3][c+3].getOwner())
                        endGame(player);
                    if (c - 3 >= 0 &&
                            player == board[r+1][c-1].getOwner() &&
                            player == board[r+2][c-2].getOwner() &&
                            player == board[r+3][c-3].getOwner())
                        endGame(player);
                }
            }
        }
    }

    private void endGame(PlayerDetails player){
        generateXML();
        btnNewGame.setVisible(true);
        menuBar.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over!");
        if (Objects.equals(player.getPlayerName(), "Draw")){
            alert.setHeaderText("Draw!");
        }else{
            alert.setHeaderText("Congratulations " + player.getPlayerName() + " has won!");
        }
        alert.show();
        long estimatedTime = System.currentTimeMillis() - startTime;

        PlayerDetails loser = player == playerOne ? playerTwo:playerOne;

        PlayerLeaderboard newGameToBoard = new PlayerLeaderboard(player, loser, estimatedTime/1000);
        leaderboard.add(newGameToBoard);
    }

    public void openLeaderboardScreen(){
        Replay();
    }

    public void openHistoryScreen() throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("historyScreen.fxml"));
        Parent root = fxmlLoader.load();
        HistoryController historyController = fxmlLoader.getController();
        historyController.displayData(playerOne, playerTwo);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("History board");
        stage.show();
    }

    public void openChatScreen() throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("chatScreen.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Chat");
        stage.show();
    }

    public void resetBoard() {
        tempBoard = leaderboard;
        initializeBoard();
        leaderboard = tempBoard;
        clearBoard();
        swapPlayers();
        displayLabel();
        btnNewGame.setVisible(false);
    }

    private void clearBoard() {
        for(int i = 0; i<6; i++){
            firstColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            secondColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            thirdColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            fourthColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            fifthColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            sixthColumn.get(i).setFill(Paint.valueOf("#ffffff"));
            seventhColumn.get(i).setFill(Paint.valueOf("#ffffff"));
        }
        playerOne.resetMoves();
        playerTwo.resetMoves();
    }

    private void handleNewSlotInsert(int column, boolean firstPlayerTurn, int i) {
        switch (column){
            case 0:
                if (firstPlayerTurn) {
                    firstColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    firstColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 1:
                if (firstPlayerTurn) {
                    secondColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    secondColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 2:
                if (firstPlayerTurn) {
                    thirdColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    thirdColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 3:
                if (firstPlayerTurn) {
                    fourthColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    fourthColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 4:
                if (firstPlayerTurn) {
                    fifthColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    fifthColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 5:
                if (firstPlayerTurn) {
                    sixthColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    sixthColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
            case 6:
                if (firstPlayerTurn) {
                    seventhColumn.get(i).setFill(Paint.valueOf(PLAYER_ONE_COLOR));
                    setFirstOwner(i,column);
                } else {
                    seventhColumn.get(i).setFill(Paint.valueOf(PLAYER_TWO_COLOR));
                    setSecondOwner(i,column);
                }
                break;
        }
    }

    private void setFirstOwner(int i, int column) {
        field[i][column].setOwner(playerOne);
    }

    private void setSecondOwner(int i, int column) {
        field[i][column].setOwner(playerTwo);
    }

    @SuppressWarnings("DuplicatedCode")
    private void initializeColumnLists() {
        firstColumn = new ArrayList<Circle>();
        firstColumn.add(_00);
        firstColumn.add(_10);
        firstColumn.add(_20);
        firstColumn.add(_30);
        firstColumn.add(_40);
        firstColumn.add(_50);

        secondColumn = new ArrayList<Circle>();
        secondColumn.add(_01);
        secondColumn.add(_11);
        secondColumn.add(_21);
        secondColumn.add(_31);
        secondColumn.add(_41);
        secondColumn.add(_51);

        thirdColumn = new ArrayList<Circle>();
        thirdColumn.add(_02);
        thirdColumn.add(_12);
        thirdColumn.add(_22);
        thirdColumn.add(_32);
        thirdColumn.add(_42);
        thirdColumn.add(_52);

        fourthColumn = new ArrayList<Circle>();
        fourthColumn.add(_03);
        fourthColumn.add(_13);
        fourthColumn.add(_23);
        fourthColumn.add(_33);
        fourthColumn.add(_43);
        fourthColumn.add(_53);

        fifthColumn = new ArrayList<Circle>();
        fifthColumn.add(_04);
        fifthColumn.add(_14);
        fifthColumn.add(_24);
        fifthColumn.add(_34);
        fifthColumn.add(_44);
        fifthColumn.add(_54);

        sixthColumn = new ArrayList<Circle>();
        sixthColumn.add(_05);
        sixthColumn.add(_15);
        sixthColumn.add(_25);
        sixthColumn.add(_35);
        sixthColumn.add(_45);
        sixthColumn.add(_55);

        seventhColumn = new ArrayList<Circle>();
        seventhColumn.add(_06);
        seventhColumn.add(_16);
        seventhColumn.add(_26);
        seventhColumn.add(_36);
        seventhColumn.add(_46);
        seventhColumn.add(_56);

    }
    private void handleFirstButton() throws IOException {
        int column = 0;
        insertIntoColumn(column);
    }
    private void handleSecondButton() throws IOException {
        int column = 1;
        insertIntoColumn(column);
    }

    private void handleThirdButton() throws IOException {
        int column = 2;
        insertIntoColumn(column);
    }

    private void handleFourthButton() throws IOException {
        int column = 3;
        insertIntoColumn(column);
    }

    private void handleFifthButton() throws IOException {
        int column = 4;
        insertIntoColumn(column);
    }

    private void handleSixthButton() throws IOException {
        int column = 5;
        insertIntoColumn(column);
    }

    private void handleSeventhButton() throws IOException {
        int column = 6;
        insertIntoColumn(column);
    }

    public void saveGame() throws IOException {

        List<SerializableCircle> serializableCircleList = new ArrayList<>();

        for(int i = ROWS_COUNT-1; i>=0;i--){
            for(int j=COLUMN_COUNT-1; j >=0; j--){
                if (field[i][j].isUsed()){
                    serializableCircleList.add(new SerializableCircle(i,j,field[i][j].getOwner()));
                }
            }
        }


        serializableCircleList.get(0).setPlayerTurn(firstPlayerTurn);
        serializableCircleList.get(0).setPlayerOne(playerOne);
        serializableCircleList.get(0).setPlayerTwo(playerTwo);
        if(leaderboard!=null)serializableCircleList.get(0).setLeaderboard(leaderboard);


        try(ObjectOutputStream serializator = new ObjectOutputStream(new FileOutputStream("savedGame.ser"))){
            serializator.writeObject(serializableCircleList);
        }

    }

    public void loadGame() throws IOException, ClassNotFoundException {

        try(ObjectInputStream deserializator = new ObjectInputStream(new FileInputStream("savedGame.ser"))){
            List<SerializableCircle> serializableCircleList = (List<SerializableCircle>)deserializator.readObject();

            firstPlay = false;
            resetBoard();


            for (SerializableCircle circle : serializableCircleList) {
                field[circle.getxLocation()][circle.getyLocation()].setOwner(circle.getOwner());
                field[circle.getxLocation()][circle.getyLocation()].setUsed(true);
                drawLoadedMap(circle.getxLocation(), circle.getyLocation(), circle.getOwner());
            }

            playerOne = serializableCircleList.get(0).getPlayerOne();
            playerTwo = serializableCircleList.get(0).getPlayerTwo();
            if(serializableCircleList.get(0).getLeaderboard() != null) leaderboard = serializableCircleList.get(0).getLeaderboard();

            firstPlayerTurn = serializableCircleList.get(0).getPlayerTurn();

            displayLabel();

        } catch (Exception ex){
            throw new IOException("not good");
        }

    }

    private void drawLoadedMap(int i, int j, PlayerDetails owner) {
        String color;
        if(owner.firstPlayer){
            color = "#ff1313";
        }else{
            color = "#fffc53";
        }
        switch (j){
            case 0 -> firstColumn.get(i).setFill(Paint.valueOf(color));
            case 1 -> secondColumn.get(i).setFill(Paint.valueOf(color));
            case 2 -> thirdColumn.get(i).setFill(Paint.valueOf(color));
            case 3 -> fourthColumn.get(i).setFill(Paint.valueOf(color));
            case 4 -> fifthColumn.get(i).setFill(Paint.valueOf(color));
            case 5 -> sixthColumn.get(i).setFill(Paint.valueOf(color));
            case 6 -> seventhColumn.get(i).setFill(Paint.valueOf(color));
        }
    }

    public void getDocumentation(){
        File documentationFile = new File("documentation.html");

        try {

            FileWriter writer = new FileWriter(documentationFile);

            writer.write("<!DOCTYPE html>");
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Project documentation</title>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h1>Project documentation</h1>");
            writer.write("<p>Class list:</p>");

            List<Path> paths = Files.walk(Paths.get("."))
                    .filter(path -> path.getFileName().toString().endsWith(CLASS_EXTENSION))
                    .collect(Collectors.toList());

            for (Path path : paths) {
                String[] tokens = path.toString().split(Pattern.quote(System.getProperty("file.separator")));

                Boolean startBuildingPath = false;

                StringBuilder sb = new StringBuilder();

                for (String token : tokens) {
                    if ("classes".equals(token)) {
                        startBuildingPath = true;
                        continue;
                    }

                    if (startBuildingPath) {

                        if (token.endsWith(CLASS_EXTENSION)) {
                            sb.append(token.substring(0, token.indexOf(".")));
                        } else {
                            sb.append(token);
                            sb.append(".");
                        }
                    } else {
                        continue;
                    }
                }

                if ("module-info".equals(sb.toString())) {
                    continue;
                }

                System.out.println("Fully qualified name: " + sb.toString());

                try {
                    Class<?> clazz = Class.forName(sb.toString());

                    writer.write("<h2>" + Modifier.toString(clazz.getModifiers()) + " " + clazz.getName() + "</h2>");

                    StringBuilder classFieldString = new StringBuilder();

                    for (Field classField : clazz.getDeclaredFields()) {
                        Annotation[] annotations = classField.getAnnotations();
                        if (annotations.length != 0) {
                            for (Annotation a : annotations) {
                                classFieldString.append(a.toString());
                                classFieldString.append("<br />");
                            }
                        }
                        classFieldString.append(Modifier.toString(classField.getModifiers()));
                        classFieldString.append(" ");
                        classFieldString.append(classField.getType().getSimpleName());
                        classFieldString.append(" ");
                        classFieldString.append(classField.getName());
                        classFieldString.append(" ");
                        classFieldString.append("<br /><br />");
                    }

                    writer.write("<h3>Fields</h3>");

                    writer.write("<h4>" + classFieldString + "</h4>");

                    Constructor[] constructors = clazz.getConstructors();

                    writer.write("<h3>Constructors:</h3>");

                    for (Constructor c : constructors) {

                        String constructorParams = generateDocumentation(c);

                        writer.write("<h4>Constructor:" + Modifier.toString(c.getModifiers()) + " " + c.getName()
                                + "(" + constructorParams + ")" + "</h4>");
                    }

                    Method[] methods = clazz.getMethods();

                    writer.write("<h3>Methods:</h3>");

                    for (Method m : methods) {

                        String methodsParams = generateDocumentation(m);

                        StringBuilder exceptionsBuilder = new StringBuilder();

                        for (int i = 0; i < m.getExceptionTypes().length; i++) {
                            if (exceptionsBuilder.isEmpty()) {
                                exceptionsBuilder.append(" throws ");
                            }

                            Class exceptionClass = m.getExceptionTypes()[i];
                            exceptionsBuilder.append(exceptionClass.getSimpleName());

                            if (i < m.getExceptionTypes().length - 1) {
                                exceptionsBuilder.append(", ");
                            }
                        }

                        writer.write("<h4>Method:" + Modifier.toString(m.getModifiers())
                                + " " + m.getReturnType().getSimpleName()
                                + " " + m.getName() + "(" + methodsParams + ")"
                                + " " + exceptionsBuilder.toString()
                                + "</h4>");
                    }

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            writer.write("</body>");
            writer.write("</html>");
            writer.close();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while generating documentation!");
            alert.setHeaderText("Cannot find the files");
            alert.setContentText("The class files cannot be accessed.");

            alert.showAndWait();
        }


    }

    private <T extends Executable> String generateDocumentation(T executable) {
        Parameter[] params = executable.getParameters();

        StringBuilder methodsParams = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            String modifierString = Modifier.toString(params[i].getModifiers());

            if (!modifierString.isEmpty()) {
                methodsParams.append(modifierString);
                methodsParams.append(" ");
            }
            methodsParams.append(params[i].getType().getSimpleName());
            methodsParams.append(" ");
            methodsParams.append(params[i].getName());

            if (i < (params.length - 1)) {
                methodsParams.append(", ");
            }
        }

        return methodsParams.toString();
    }

    public void generateXML(){

        try {
            DocumentBuilderFactory documentBuilderFactory
                    = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document xmlDocument = documentBuilder.newDocument();
            Element rootElement = xmlDocument.createElement("playerMoves");
            xmlDocument.appendChild(rootElement);



            for(Move move : movesXML) {

                    Element parentElement = xmlDocument.createElement("Move");

                    Element columnElement
                            = xmlDocument.createElement("Column");
                    Node columnTextNode = xmlDocument.createTextNode(move.getCOLUMN());
                    columnElement.appendChild(columnTextNode);
                    parentElement.appendChild(columnElement);

                    Element rowElement
                            = xmlDocument.createElement("Row");
                    Node rowTextNode = xmlDocument.createTextNode(String.valueOf(move.getROW()));
                    rowElement.appendChild(rowTextNode);
                    parentElement.appendChild(rowElement);

                    Element colorElement = xmlDocument.createElement("Color");
                    Node colorTextNode = xmlDocument.createTextNode(move.getColor());
                    colorElement.appendChild(colorTextNode);
                    parentElement.appendChild(colorElement);

                    rootElement.appendChild(parentElement);
                }



            Transformer transformer
                    = TransformerFactory.newInstance().newTransformer();

            Source xmlSource = new DOMSource(xmlDocument);
            Result xmlResult = new StreamResult(new File("playerMoves.xml"));

            transformer.transform(xmlSource, xmlResult);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("XML file created!");
            alert.setHeaderText("XML file was successfully created!");
            alert.setContentText("File 'playerMoves.xml' was created!");

            alert.showAndWait();

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    public void Replay(){
        List<Move> loadMovesList = LoadMoves();

        clearBoard();
        new Thread(() -> StartReplay(loadMovesList)).start();
    }

    private void StartReplay(List<Move> moves){
        try{
        for(Move move : moves){
            PlayMove(move);
            Thread.sleep(1000);
        }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void PlayMove(Move move) {
        switch (move.getCOLUMN()) {
            case "0" -> firstColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "1" -> secondColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "2" -> thirdColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "3" -> fourthColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "4" -> fifthColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "5" -> sixthColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
            case "6" -> seventhColumn.get(move.getROW()).setFill(Paint.valueOf(move.getColor()));
        }
    }

    private List<Move> LoadMoves() {
        List<Move> listOfMoves;
        try {
            File file = new File("playerMoves.xml");
            FileInputStream fileInputStream = new FileInputStream(file);

            DocumentBuilder parser =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document xmlDocument = parser.parse(fileInputStream);

            NodeList nodeList = xmlDocument.getElementsByTagName("Move");

            listOfMoves = new ArrayList<>();

            for(int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);

                if(parentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element parentElement = (Element) parentNode;

                    String column = parentElement
                            .getElementsByTagName("Column")
                            .item(0)
                            .getTextContent();


                    String row = parentElement
                            .getElementsByTagName("Row")
                            .item(0)
                            .getTextContent();



                    String color = parentElement
                            .getElementsByTagName("Color")
                            .item(0)
                            .getTextContent();

                    listOfMoves.add(new Move(Integer.parseInt(row), Integer.parseInt(column), color));

                }

            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
        return listOfMoves;
    }

}
