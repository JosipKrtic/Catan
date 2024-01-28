package hr.tvz.catan.catan;

import hr.tvz.catan.catan.File.FileUtils;
import hr.tvz.catan.catan.Models.Board;
import hr.tvz.catan.catan.Models.GameMove;
import hr.tvz.catan.catan.Models.GameState;
import hr.tvz.catan.catan.Models.Player;
import hr.tvz.catan.catan.Threads.ReplayThread;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class CatanGameController {

    @FXML
    private ImageView fieldImage1, fieldImage2, fieldImage3, fieldImage4, fieldImage5, fieldImage6, fieldImage7,
            fieldImage8, fieldImage9, fieldImage10, fieldImage11, fieldImage12, fieldImage13, fieldImage14,
            fieldImage15, fieldImage16, fieldImage17, fieldImage18, fieldImage19;
    @FXML
    private Text fieldNumber1, fieldNumber2, fieldNumber3, fieldNumber4, fieldNumber5, fieldNumber6, fieldNumber7,
            fieldNumber8, fieldNumber9, fieldNumber10, fieldNumber11, fieldNumber12, fieldNumber13, fieldNumber14,
            fieldNumber15, fieldNumber16, fieldNumber17, fieldNumber18, fieldNumber19;
    @FXML
    private Group player1Group, player2Group, player3Group, player4Group, buildGroup, buildCardGroup;
    @FXML
    private ImageView player1Avatar, player2Avatar, player3Avatar, player4Avatar;
    @FXML
    private Text player1Name, player2Name, player3Name, player4Name;
    @FXML
    private Text player1TotalPoints, player1NumOfCities, player1NumOfVillages, player1NumOfRoads, player1NumOfCards,
            player2TotalPoints, player2NumOfCities, player2NumOfVillages, player2NumOfRoads, player2NumOfCards,
            player3TotalPoints, player3NumOfCities, player3NumOfVillages, player3NumOfRoads, player3NumOfCards,
            player4TotalPoints, player4NumOfCities, player4NumOfVillages, player4NumOfRoads, player4NumOfCards;
    @FXML
    public Text sheepCountText, wheetCountText, clayCountText, woodCountText, rockCountText;
    @FXML
    private Text headlineText;
    @FXML
    private ImageView rollDice1, rollDice2;
    @FXML
    private Group player1Village1, player1Village2, player1Village3, player1Village4, player1Village5,
            player2Village1, player2Village2, player2Village3, player2Village4, player2Village5,
            player3Village1, player3Village2, player3Village3, player3Village4, player3Village5,
            player4Village1, player4Village2, player4Village3, player4Village4, player4Village5;
    @FXML
    private Group player1City1, player1City2, player1City3, player1City4,
            player2City1, player2City2, player2City3, player2City4,
            player3City1, player3City2, player3City3, player3City4,
            player4City1, player4City2, player4City3, player4City4;
    @FXML
    private Rectangle player1Road1, player1Road2, player1Road3, player1Road4, player1Road5, player1Road6, player1Road7, player1Road8, player1Road9, player1Road10,
            player2Road1, player2Road2, player2Road3, player2Road4, player2Road5, player2Road6, player2Road7, player2Road8, player2Road9, player2Road10,
            player3Road1, player3Road2, player3Road3, player3Road4, player3Road5, player3Road6, player3Road7, player3Road8, player3Road9, player3Road10,
            player4Road1, player4Road2, player4Road3, player4Road4, player4Road5, player4Road6, player4Road7, player4Road8, player4Road9, player4Road10;
    @FXML
    private Group cityVillageGroup, roadGroup, cityGroup, robberGroup;
    @FXML
    private Group robberImage;
    @FXML
    private Button rollDice, continueGameButton, buildCityButton, buildVillageButton, buildRoadButton;
    @FXML
    private Button buildCardButton;
    @FXML
    private Button cityUpgrade1, cityUpgrade2, cityUpgrade3, cityUpgrade4, cityUpgrade5;
    @FXML
    private MenuItem replayGameMenuItem;

    //Variables
    private static final String FOREST = "forest";
    private static final String FIELDS = "fields";
    private static final String PASTURE = "pasture";
    private static final String MOUNTAINS = "mountains";
    private static final String HILLS = "hills";
    private static final String DESERT = "desert";
    private static final String NUMBER0 = "0";
    private static final String NUMBER1 = "1";
    private static final String NUMBER2 = "2";
    private static final String NUMBER3 = "3";
    private static final String NUMBER4 = "4";
    private static final String NUMBER5 = "5";
    private static final String NUMBER6 = "6";
    private static final String NUMBER8 = "8";
    private static final String NUMBER9 = "9";
    private static final String NUMBER10 = "10";
    private static final String NUMBER11 = "11";
    private static final String NUMBER12 = "12";
    private static final String redText = "-fx-fill: red";
    private static final String blackText = "-fx-fill: black";
    private static final String FIRST_TURN_DICE_ROLL_PHASE = "Players, roll the dice!";
    private static final String VILLAGE_FLAG = "village";
    private static final String CITY_FLAG = "city";
    private static final String ROLLED = " rolled ";
    private static List<String> fields = Arrays.asList(FOREST, FOREST, FOREST, FOREST, FIELDS, FIELDS, FIELDS, FIELDS,
            PASTURE, PASTURE, PASTURE, PASTURE, MOUNTAINS, MOUNTAINS, MOUNTAINS, HILLS, HILLS, HILLS, DESERT);
    private static List<String> fieldNumbers = Arrays.asList(NUMBER2, NUMBER3, NUMBER3, NUMBER4, NUMBER4, NUMBER5, NUMBER5,
            NUMBER6, NUMBER6, NUMBER8, NUMBER8, NUMBER9, NUMBER9, NUMBER10, NUMBER10, NUMBER11, NUMBER11, NUMBER12);
    private static final List<String> diceNumbers = Arrays.asList(NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, NUMBER6);
    private int numberOfPlayers = 0;
    private int numOfDiceRolls = 0;
    private int player1DiceRoll = 0;
    private int player2DiceRoll = 0;
    private int player3DiceRoll = 0;
    private int player4DiceRoll = 0;

    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player activePlayer;
    Player firstPlayer;
    Player lastPlayer;
    Board board = new Board();
    private final Map<ImageView, String> fieldsAndMaterialsMap = new HashMap<>();
    private final Map<Text, String> numberAndFieldsMap = new HashMap<>();

    List<String> playerNamesForCurrentGame = new ArrayList<>();
    List<String> playerAvatarsForCurrentGame = new ArrayList<>();
    List <String> boardImagesForCurrentGame = new ArrayList<>();
    List <String> boardNumbersForCurrentGame = new ArrayList<>();

    private String alertMessage = "";

    private static final String SAVE_GAME_FILE_NAME = "saveGame.bin";

    List <Button> tempDisabledVillages = new ArrayList<>();

    List <Button> tempDisabledRoads = new ArrayList<>();

    private String firstDiceValue = "";

    private String secondDiceValue = "";

    private final List<Double> villageCoordinates = new ArrayList<>();
    private final List<Double> roadCoordinates = new ArrayList<>();

    private final List<Double> cityCoordinates = new ArrayList<>();

    private final List<Double> robberCoordinatesForGameMove = new ArrayList<>();

    private static final List<GameMove> gameMoveList = new ArrayList<>();


    public void setPlayersOnBoard(List<String> playerNames, List<String> playerAvatars) {
        playerNamesForCurrentGame = playerNames;
        playerAvatarsForCurrentGame = playerAvatars;
        numberOfPlayers = playerNames.size();
        player1 = new Player(playerNames.get(0), playerAvatars.get(0),
                Arrays.asList(player1Village1, player1Village2, player1Village3, player1Village4, player1Village5),
                Arrays.asList(player1City1, player1City2, player1City3, player1City4),
                Arrays.asList(player1Road1, player1Road2, player1Road3, player1Road4, player1Road5, player1Road6,
                        player1Road7, player1Road8, player1Road9, player1Road10));
        player1Name.setText(player1.getName());
        player1Avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/gameAvatar" + player1.getAvatar() + ".png"))));
        activePlayer = player1;
        setNewScalePlayerCard(player1Group);
        player2 = new Player(playerNames.get(1), playerAvatars.get(1),
                Arrays.asList(player2Village1, player2Village2, player2Village3, player2Village4, player2Village5),
                Arrays.asList(player2City1, player2City2, player2City3, player2City4),
                Arrays.asList(player2Road1, player2Road2, player2Road3, player2Road4, player2Road5, player2Road6,
                        player2Road7, player2Road8, player2Road9, player2Road10));
        player2Name.setText(player2.getName());
        player2Avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/gameAvatar" + player2.getAvatar() + ".png"))));
        if (numberOfPlayers == 2) {
            player3Group.setVisible(false);
            player4Group.setVisible(false);
        } else {
            player3 = new Player(playerNames.get(2), playerAvatars.get(2),
                    Arrays.asList(player3Village1, player3Village2, player3Village3, player3Village4, player3Village5),
                    Arrays.asList(player3City1, player3City2, player3City3, player3City4),
                    Arrays.asList(player3Road1, player3Road2, player3Road3, player3Road4, player3Road5, player3Road6,
                            player3Road7, player3Road8, player3Road9, player3Road10));
            player3Name.setText(player3.getName());
            player3Avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/gameAvatar" + player3.getAvatar() + ".png"))));
            player3Group.setVisible(true);
            player4Group.setVisible(false);
        }

        if (numberOfPlayers == 4) {
            player4 = new Player(playerNames.get(3), playerAvatars.get(3),
                    Arrays.asList(player4Village1, player4Village2, player4Village3, player4Village4, player4Village5),
                    Arrays.asList(player4City1, player4City2, player4City3, player4City4),
                    Arrays.asList(player4Road1, player4Road2, player4Road3, player4Road4, player4Road5, player4Road6,
                            player4Road7, player4Road8, player4Road9, player4Road10));
            player4Name.setText(player4.getName());
            player4Avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/gameAvatar" + player4.getAvatar() + ".png"))));
            player4Group.setVisible(true);
        }
    }

    @FXML
    public void initialize() {
        //places the hexes and numbers randomly
        setBoardFields();

        //defines all the text variables on the board
        setBoardText();

        //creates a connection between all the numbers and hexes
        connectNumbersToFields();

        //disables all the buttons on the board
        setBoardButtons();
    }

    private void setBoardFields() {
        Collections.shuffle(fields);
        Collections.shuffle(fieldNumbers);
        int count = 0;

        for (int i = 0; i < fields.size(); i++) {
            placeFields(fields.get(i), i);
        }

        for (int i = 0; i < fields.size(); i++) {
            if (!fields.get(i).equals(DESERT)) {
                placeNumbers(fieldNumbers.get(count), i);
                count++;
            }
        }
    }

    private void setBoardText() {
        headlineText.setText(FIRST_TURN_DICE_ROLL_PHASE);
        Text[] playerInfos = new Text[]{
                player1TotalPoints, player1NumOfCities, player1NumOfVillages, player1NumOfRoads, player1NumOfCards,
                player2TotalPoints, player2NumOfCities, player2NumOfVillages, player2NumOfRoads, player2NumOfCards,
                player3TotalPoints, player3NumOfCities, player3NumOfVillages, player3NumOfRoads, player3NumOfCards,
                player4TotalPoints, player4NumOfCities, player4NumOfVillages, player4NumOfRoads, player4NumOfCards,
                sheepCountText, wheetCountText, clayCountText, woodCountText, rockCountText
        };
        Arrays.stream(playerInfos).forEach(t -> t.setText(NUMBER0));
    }

    private void connectNumbersToFields() {
        numberAndFieldsMap.clear();
        numberAndFieldsMap.put(fieldNumber1, fieldsAndMaterialsMap.get(fieldImage1));
        numberAndFieldsMap.put(fieldNumber2, fieldsAndMaterialsMap.get(fieldImage2));
        numberAndFieldsMap.put(fieldNumber3, fieldsAndMaterialsMap.get(fieldImage3));
        numberAndFieldsMap.put(fieldNumber4, fieldsAndMaterialsMap.get(fieldImage4));
        numberAndFieldsMap.put(fieldNumber5, fieldsAndMaterialsMap.get(fieldImage5));
        numberAndFieldsMap.put(fieldNumber6, fieldsAndMaterialsMap.get(fieldImage6));
        numberAndFieldsMap.put(fieldNumber7, fieldsAndMaterialsMap.get(fieldImage7));
        numberAndFieldsMap.put(fieldNumber8, fieldsAndMaterialsMap.get(fieldImage8));
        numberAndFieldsMap.put(fieldNumber9, fieldsAndMaterialsMap.get(fieldImage9));
        numberAndFieldsMap.put(fieldNumber10, fieldsAndMaterialsMap.get(fieldImage10));
        numberAndFieldsMap.put(fieldNumber11, fieldsAndMaterialsMap.get(fieldImage11));
        numberAndFieldsMap.put(fieldNumber12, fieldsAndMaterialsMap.get(fieldImage12));
        numberAndFieldsMap.put(fieldNumber13, fieldsAndMaterialsMap.get(fieldImage13));
        numberAndFieldsMap.put(fieldNumber14, fieldsAndMaterialsMap.get(fieldImage14));
        numberAndFieldsMap.put(fieldNumber15, fieldsAndMaterialsMap.get(fieldImage15));
        numberAndFieldsMap.put(fieldNumber16, fieldsAndMaterialsMap.get(fieldImage16));
        numberAndFieldsMap.put(fieldNumber17, fieldsAndMaterialsMap.get(fieldImage17));
        numberAndFieldsMap.put(fieldNumber18, fieldsAndMaterialsMap.get(fieldImage18));
        numberAndFieldsMap.put(fieldNumber19, fieldsAndMaterialsMap.get(fieldImage19));
    }

    private void setBoardButtons() {
        continueGameButton.setDisable(true);
        buildCityButton.setDisable(true);
        buildVillageButton.setDisable(true);
        buildRoadButton.setDisable(true);
    }

    @FXML
    public void rollDice() {
        Random rand = new Random();
        String firstDice = diceNumbers.get(rand.nextInt(diceNumbers.size()));
        String secondDice = diceNumbers.get(rand.nextInt(diceNumbers.size()));
        firstDiceValue = firstDice;
        secondDiceValue = secondDice;
        rollDice1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + firstDice + ".png"))));
        rollDice2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + secondDice + ".png"))));
        int diceRollValue = Integer.parseInt(firstDice) + Integer.parseInt(secondDice);
        if (board.getFirstTurnDiceRollPhase()) {
            headlineText.setText(activePlayer.getName() + ROLLED + diceRollValue + "!");
            rollDice.setDisable(true);
            if (activePlayer == player1) {
                player1DiceRoll = diceRollValue;
            } else if (activePlayer == player2) {
                player2DiceRoll = diceRollValue;
            } else if (activePlayer == player3) {
                player3DiceRoll = diceRollValue;
            } else if (activePlayer == player4) {
                player4DiceRoll = diceRollValue;
            }
            numOfDiceRolls++;
            continueGameButton.setDisable(false);
        } else if (board.getMainGamePhase()) {
            if (diceRollValue == 7) {
                headlineText.setText(activePlayer.getName() + " choose a spot for the robber!");
                robberGroup.setDisable(false);
                robberGroup.setVisible(true);
                rollDice.setDisable(true);
            } else {
                headlineText.setText(activePlayer.getName() + ROLLED + diceRollValue + "!");

                //adds materials for each player
                addMaterialToPlayersForRolledNumber(String.valueOf(diceRollValue));

                //updates players number of cards text and active players materials
                updateTextOnScreen();

                //enables buttons for the current player if he has enough materials
                checkIfActivePlayerCanBuild();
                rollDice.setDisable(true);
                continueGameButton.setDisable(false);
            }
            //set information alert for given materials
            showAlertForDiceRoll(diceRollValue);
        }
    }

    private void updateTextOnScreen() {
        player1NumOfCards.setText(String.valueOf(player1.getNumOfCards()));
        player2NumOfCards.setText(String.valueOf(player2.getNumOfCards()));
        if (numberOfPlayers > 2) {
            player3NumOfCards.setText(String.valueOf(player3.getNumOfCards()));
        }
        if (numberOfPlayers == 4) {
            player4NumOfCards.setText(String.valueOf(player4.getNumOfCards()));
        }
        //set number of materials for active player
        setMaterialsTextForActivePlayer();
    }

    private void showAlertForDiceRoll(int diceRollValue) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dice roll results");
        alert.setHeaderText(activePlayer.getName() + ROLLED + diceRollValue + "!");
        if (diceRollValue == 7) {
            alert.setContentText("Player has to place the robber on one field!");
        } else if (alertMessage.equals("")) {
            alert.setContentText("No players acquired a card");
        } else {
            alert.setContentText(alertMessage);
        }

        alert.showAndWait();
        alertMessage = "";
    }

    private void addMaterialToPlayersForRolledNumber(String diceRollValue) {

        List<Text> allFieldNumbers = Arrays.asList(fieldNumber1, fieldNumber2, fieldNumber3, fieldNumber4, fieldNumber5,
                fieldNumber6, fieldNumber7, fieldNumber8, fieldNumber9, fieldNumber10, fieldNumber11, fieldNumber12,
                fieldNumber13, fieldNumber14, fieldNumber15, fieldNumber16, fieldNumber17, fieldNumber18, fieldNumber19);

        for (Text fieldNumber : allFieldNumbers) {
            if (!fieldNumber.isDisabled()) {
                if (String.valueOf(diceRollValue).equals(fieldNumber.getText())) {
                    checkForNeighbourVillagesAndCities(numberAndFieldsMap.get(fieldNumber), fieldNumber);
                }
            }
        }
    }

    private void checkForNeighbourVillagesAndCities(String terrain, Text fieldNumber) {
        Bounds bounds1 = fieldNumber.localToScene(fieldNumber.getBoundsInLocal());
        Point2D center1 = new Point2D(bounds1.getMinX() + bounds1.getWidth() / 2, bounds1.getMinY() + bounds1.getHeight() / 2);
        for (Button village : player1.getVillageList()) {
            checkDistanceForEachVillageAndCity(player1, village, center1, terrain, VILLAGE_FLAG);
        }
        for (Button city : player1.getCityList()) {
            checkDistanceForEachVillageAndCity(player1, city, center1, terrain, CITY_FLAG);
        }
        for (Button village : player2.getVillageList()) {
            checkDistanceForEachVillageAndCity(player2, village, center1, terrain, VILLAGE_FLAG);
        }
        for (Button city : player2.getCityList()) {
            checkDistanceForEachVillageAndCity(player2, city, center1, terrain, CITY_FLAG);
        }
        if (numberOfPlayers > 2) {
            for (Button village : player3.getVillageList()) {
                checkDistanceForEachVillageAndCity(player3, village, center1, terrain, VILLAGE_FLAG);
            }
            for (Button city : player3.getCityList()) {
                checkDistanceForEachVillageAndCity(player3, city, center1, terrain, CITY_FLAG);
            }
        }
        if (numberOfPlayers == 4) {
            for (Button village : player4.getVillageList()) {
                checkDistanceForEachVillageAndCity(player4, village, center1, terrain, VILLAGE_FLAG);
            }
            for (Button city : player4.getCityList()) {
                checkDistanceForEachVillageAndCity(player4, city, center1, terrain, CITY_FLAG);
            }
        }
    }

    private void checkDistanceForEachVillageAndCity(Player player, Button villageCity, Point2D center1, String terrain, String villageOrCityFlag) {
        Bounds bounds2 = villageCity.localToScene(villageCity.getBoundsInLocal());
        Point2D center2 = new Point2D(bounds2.getMinX() + bounds2.getWidth() / 2, bounds2.getMinY() + bounds2.getHeight() / 2);
        if (center1.distance(center2) < 100) {
            switch (terrain) {
                case PASTURE -> {
                    if (villageOrCityFlag.equals(VILLAGE_FLAG)) {
                        player.increaseNumOfSheepCards();
                        alertMessage += player.getName() + " got a sheep card!\n";
                    } else if (villageOrCityFlag.equals(CITY_FLAG)) {
                        player.increaseNumOfSheepCards();
                        player.increaseNumOfSheepCards();
                        alertMessage += player.getName() + " got 2 sheep cards!\n";
                    }
                }
                case FIELDS -> {
                    if (villageOrCityFlag.equals(VILLAGE_FLAG)) {
                        player.increaseNumOfWheatCards();
                        alertMessage += player.getName() + " got a wheat card!\n";
                    } else if (villageOrCityFlag.equals(CITY_FLAG)) {
                        player.increaseNumOfWheatCards();
                        player.increaseNumOfWheatCards();
                        alertMessage += player.getName() + " got 2 wheat cards!\n";
                    }
                }
                case HILLS -> {
                    if (villageOrCityFlag.equals(VILLAGE_FLAG)) {
                        player.increaseNumOfClayCards();
                        alertMessage += player.getName() + " got a clay card!\n";
                    } else if (villageOrCityFlag.equals(CITY_FLAG)) {
                        player.increaseNumOfClayCards();
                        player.increaseNumOfClayCards();
                        alertMessage += player.getName() + " got 2 clay cards!\n";
                    }
                }
                case FOREST -> {
                    if (villageOrCityFlag.equals(VILLAGE_FLAG)) {
                        player.increaseNumOfWoodCards();
                        alertMessage += player.getName() + " got a wood card!\n";
                    } else if (villageOrCityFlag.equals(CITY_FLAG)) {
                        player.increaseNumOfWoodCards();
                        player.increaseNumOfWoodCards();
                        alertMessage += player.getName() + " got 2 wood cards!\n";
                    }
                }
                case MOUNTAINS -> {
                    if (villageOrCityFlag.equals(VILLAGE_FLAG)) {
                        player.increaseNumOfRockCards();
                        alertMessage += player.getName() + " got a rock card!\n";
                    } else if (villageOrCityFlag.equals(CITY_FLAG)) {
                        player.increaseNumOfRockCards();
                        player.increaseNumOfRockCards();
                        alertMessage += player.getName() + " got 2 rock cards!\n";
                    }
                }
            }
        }
    }

    private void setLastPlayerInTurn() {
        if (activePlayer == player1) {
            if (numberOfPlayers == 2) {
                lastPlayer = player2;
            } else if (numberOfPlayers == 3) {
                lastPlayer = player3;
            } else {
                lastPlayer = player4;
            }
        } else if (activePlayer == player2) {
            lastPlayer = player1;
        } else if (activePlayer == player3) {
            lastPlayer = player2;
        } else {
            lastPlayer = player3;
        }
    }

    @FXML
    private void continueGame() {
        if (board.getFirstTurnDiceRollPhase()) {
            if (numOfDiceRolls == numberOfPlayers) {
                //game move for game replay function
                GameMove newGameMove = new GameMove(activePlayer.getName(), "firstTurnDiceRollPhase",
                        activePlayer.getName() + " roll the dice!", firstDiceValue,
                        secondDiceValue, activePlayer.getName() + " rolled " +
                        (Integer.parseInt(firstDiceValue) + Integer.parseInt(secondDiceValue)));
                gameMoveList.add(newGameMove);

                int currMaxDiceRoll = player1DiceRoll;
                Player firstTurn = player1;
                if (player2DiceRoll > currMaxDiceRoll) {
                    currMaxDiceRoll = player2DiceRoll;
                    firstTurn = player2;
                }
                if (player3DiceRoll > currMaxDiceRoll) {
                    currMaxDiceRoll = player3DiceRoll;
                    firstTurn = player3;
                }
                if (player4DiceRoll > currMaxDiceRoll) {
                    firstTurn = player4;
                }

                if (numberOfPlayers == 2) {
                    returnScaleOnPlayerCard(player2Group);
                } else if (numberOfPlayers == 3) {
                    returnScaleOnPlayerCard(player3Group);
                } else {
                    returnScaleOnPlayerCard(player4Group);
                }

                if (firstTurn.equals(player1)) {
                    activePlayer = player1;
                    firstPlayer = player1;
                    setNewScalePlayerCard(player1Group);
                } else if (firstTurn.equals(player2)) {
                    activePlayer = player2;
                    firstPlayer = player2;
                    setNewScalePlayerCard(player2Group);
                } else if (firstTurn.equals(player3)) {
                    activePlayer = player3;
                    firstPlayer = player3;
                    setNewScalePlayerCard(player3Group);
                } else {
                    activePlayer = player4;
                    firstPlayer = player4;
                    setNewScalePlayerCard(player4Group);
                }
                headlineText.setText(activePlayer.getName() + " it's your turn!");
                setLastPlayerInTurn();
                board.setFirstTurnDiceRollPhase(false);
                board.setInitialBuildingPhase(true);
                continueGameButton.setDisable(true);
                rollDice.setDisable(true);
                buildVillageButton.setDisable(false);
            } else {
                //game move for game replay function
                GameMove newGameMove = new GameMove(activePlayer.getName(), "firstTurnDiceRollPhase",
                        activePlayer.getName() + " roll the dice!", firstDiceValue,
                        secondDiceValue, activePlayer.getName() + " rolled " +
                        (Integer.parseInt(firstDiceValue) + Integer.parseInt(secondDiceValue)));
                gameMoveList.add(newGameMove);

                switchActivePlayer();
                headlineText.setText(activePlayer.getName() + " roll the dice!");
                continueGameButton.setDisable(true);
                rollDice.setDisable(false);
            }
        } else if (board.getInitialBuildingPhase()) {
            //game move for game replay function
            GameMove newGameMove = new GameMove(activePlayer.getName(), "initialBuildingPhase",
                    activePlayer.getName() + " build your village!", villageCoordinates,
                    activePlayer.getName() + " just placed a village!", roadCoordinates,
                    activePlayer.getName() + " just placed a road!", activePlayer.getSheepCount(),
                    activePlayer.getWheatCount(), activePlayer.getClayCount(), activePlayer.getWoodCount(),
                    activePlayer.getRockCount());
            villageCoordinates.clear();
            roadCoordinates.clear();
            gameMoveList.add(newGameMove);
            if (lastPlayer.getNumOfRoads() < 2) {
                setNextTurn(board.getInitialBuildingPhaseFlag());
            } else {
                //start main game phase and set next Turn
                setNextTurn(board.getMainGamePhaseFlag());
                headlineText.setText(activePlayer.getName() + " roll the dice or build!");
                board.setMainGamePhase(true);
                board.setInitialBuildingPhase(false);
            }
        } else if (board.getMainGamePhase()) {
            //game move for game replay function
            GameMove newGameMove = new GameMove(activePlayer.getName(), "mainGamePhase",
                    activePlayer.getName() + " roll the dice!", firstDiceValue,
                    secondDiceValue, activePlayer.getName() + " rolled " +
                    (Integer.parseInt(firstDiceValue) + Integer.parseInt(secondDiceValue)), cityCoordinates,
                    activePlayer.getName() + " just placed a city!", villageCoordinates,
                    activePlayer.getName() + " just placed a village!", roadCoordinates,
                    activePlayer.getName() + " just placed a road!", robberCoordinatesForGameMove,
                    activePlayer.getName() + " just placed a robber!", activePlayer.getSheepCount(),
                    activePlayer.getWheatCount(), activePlayer.getClayCount(), activePlayer.getWoodCount(),
                    activePlayer.getRockCount());
            cityCoordinates.clear();
            villageCoordinates.clear();
            roadCoordinates.clear();
            robberCoordinatesForGameMove.clear();
            gameMoveList.add(newGameMove);

            //set next Turn
            setNextTurn(board.getMainGamePhaseFlag());
            headlineText.setText(activePlayer.getName() + " roll the dice or build!");
            buildCityButton.setDisable(true);
            buildVillageButton.setDisable(true);
            buildRoadButton.setDisable(true);
        }
    }

    private void checkIfActivePlayerCanBuild() {
        buildCityButton.setDisable(true);
        buildVillageButton.setDisable(true);
        buildRoadButton.setDisable(true);
        //check if player has enough materials to build a city
        if (activePlayer.getWheatCount() > 1 && activePlayer.getRockCount() > 2) {
            //check if player has active villages to replace them
            if (activePlayer.getVillageList().size() > 0) {
                buildCityButton.setDisable(false);
            }
        }
        //check if player has enough materials to build a village
        if (activePlayer.getSheepCount() > 0 && activePlayer.getWheatCount() > 0 &&
            activePlayer.getClayCount() > 0 && activePlayer.getWoodCount() > 0) {
            //check if player has less than 5 active villages
            if (activePlayer.getVillageList().size() < 5) {
                buildVillageButton.setDisable(false);
            }
        }
        //check if player has enough materials to build a road
        if (activePlayer.getWoodCount() > 0 && activePlayer.getClayCount() > 0) {
            buildRoadButton.setDisable(false);
        }
    }

    private void setNextTurn(String flag) {
        //switch active player and change headline
        switchActivePlayer();

        //disable continue button on start of turn
        continueGameButton.setDisable(true);

        //set number of materials for active player
        setMaterialsTextForActivePlayer();

        if (flag.equals("Initial Building Phase")) {
            //enable build village button for next player
            buildVillageButton.setDisable(false);
        } else if (flag.equals("Main Game Phase")) {
            rollDice.setDisable(false);
        }
    }

    private void setMaterialsTextForActivePlayer() {
        //set number of materials for active player
        sheepCountText.setText(String.valueOf(activePlayer.getSheepCount()));
        wheetCountText.setText(String.valueOf(activePlayer.getWheatCount()));
        clayCountText.setText(String.valueOf(activePlayer.getClayCount()));
        woodCountText.setText(String.valueOf(activePlayer.getWoodCount()));
        rockCountText.setText(String.valueOf(activePlayer.getRockCount()));
    }

    @FXML
    private void enableVillageBuilding() {
        cityVillageGroup.setVisible(true);
        cityVillageGroup.setDisable(false);
        if (board.getMainGamePhase()) {
            //disable all buttons
            setBoardButtons();
            //disable all villages
            cityVillageGroup.getChildren().forEach(village -> village.setDisable(true));
            //enable only neighbour roads
            filterVillages();
        }
        buildVillageButton.setDisable(true);
        headlineText.setText(activePlayer.getName() + " build your village!");
    }

    private void filterVillages() {
        for (Rectangle road : activePlayer.getPlacedRoadList()) {
            List<Node> neighbourVillages = cityVillageGroup.getChildren().stream()
                    .filter(village -> village.getLayoutX() < road.getLayoutX() + 60)
                    .filter(village -> village.getLayoutX() > road.getLayoutX() - 60)
                    .filter(village -> village.getLayoutY() < road.getLayoutY() + 45)
                    .filter(village -> village.getLayoutY() > road.getLayoutY() - 45)
                    .toList();
            neighbourVillages.forEach(neighbourVillage -> neighbourVillage.setDisable(false));
        }
        for (Node node : cityVillageGroup.getChildren()) {
            if (node instanceof Button village) {
                for (Button tmpVillage : tempDisabledVillages){
                    if (village == tmpVillage) {
                        village.setDisable(true);
                    }
                }
            }
        }
    }

    @FXML
    private void enableCityBuilding() {
        buildCityButton.setDisable(true);
        headlineText.setText(activePlayer.getName() + " build your city!");
        cityGroup.setVisible(true);
        cityGroup.setDisable(false);
        //disable all buttons
        setBoardButtons();
        for (int i = 0; i < activePlayer.getVillageList().size(); i++) {
            Button village = activePlayer.getVillageList().get(i);
            Bounds villageBounds = village.localToScene(village.getBoundsInLocal());
            double x = villageBounds.getCenterX() - 10;
            double y = villageBounds.getCenterY() - 10;
            if (i == 0) {
                cityUpgrade1.setVisible(true);
                cityUpgrade1.setDisable(false);
                cityUpgrade1.setLayoutX(x);
                cityUpgrade1.setLayoutY(y);
            } else if (i == 1) {
                cityUpgrade2.setVisible(true);
                cityUpgrade2.setDisable(false);
                cityUpgrade2.setLayoutX(x);
                cityUpgrade2.setLayoutY(y);
            } else if (i == 2) {
                cityUpgrade3.setVisible(true);
                cityUpgrade3.setDisable(false);
                cityUpgrade3.setLayoutX(x);
                cityUpgrade3.setLayoutY(y);
            } else if (i == 3) {
                cityUpgrade4.setVisible(true);
                cityUpgrade4.setDisable(false);
                cityUpgrade4.setLayoutX(x);
                cityUpgrade4.setLayoutY(y);
            } else if (i == 4) {
                cityUpgrade5.setVisible(true);
                cityUpgrade5.setDisable(false);
                cityUpgrade5.setLayoutX(x);
                cityUpgrade5.setLayoutY(y);
            }
        }
    }

    @FXML
    private void enableRoadBuilding() {
        roadGroup.setVisible(true);
        roadGroup.setDisable(false);
        //disable all buttons
        setBoardButtons();
        //disable all roads
        roadGroup.getChildren().forEach(road -> road.setDisable(true));
        buildRoadButton.setDisable(true);
        //enable only neighbour roads
        filterRoads();
        headlineText.setText(activePlayer.getName() + " build your road!");
    }

    private void filterRoads() {
        for (Button village : activePlayer.getVillageList()) {
            List<Node> neighbourRoads = roadGroup.getChildren().stream()
                    .filter(road -> road.getLayoutX() < village.getLayoutX() + 60)
                    .filter(road -> road.getLayoutX() > village.getLayoutX() - 60)
                    .filter(road -> road.getLayoutY() < village.getLayoutY() + 45)
                    .filter(road -> road.getLayoutY() > village.getLayoutY() - 45)
                    .toList();
            neighbourRoads.forEach(neighbourRoad -> neighbourRoad.setDisable(false));
        }
        for (Node node : roadGroup.getChildren()) {
            if (node instanceof Button road) {
                for (Button tmpRoad : tempDisabledRoads){
                    if (road == tmpRoad) {
                        road.setDisable(true);
                    }
                }
            }
        }
    }

    @FXML
    private void buildVillage(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            if (activePlayer == player1) {
                placeVillage(player1.getPlacedVillagesList().get(player1.getNumOfVillages()), button, player1, player1NumOfVillages, player1NumOfCards, player1TotalPoints);
            } else if (activePlayer == player2) {
                placeVillage(player2.getPlacedVillagesList().get(player2.getNumOfVillages()), button, player2, player2NumOfVillages, player2NumOfCards, player2TotalPoints);
            } else if (activePlayer == player3) {
                placeVillage(player3.getPlacedVillagesList().get(player3.getNumOfVillages()), button, player3, player3NumOfVillages, player3NumOfCards, player3TotalPoints);
            } else {
                placeVillage(player4.getPlacedVillagesList().get(player4.getNumOfVillages()), button, player4, player4NumOfVillages, player4NumOfCards, player4TotalPoints);
            }
        }
    }

    private void placeVillage(Group village, Button button, Player activePlayer, Text numOfVillages, Text numOfCards, Text totalPoints) {
        //place village on board
        village.setVisible(true);
        village.setLayoutX(button.getLayoutX() - 85);
        village.setLayoutY(button.getLayoutY() - 50);

        //add village to list of disabled villages, so it stays disabled
        tempDisabledVillages.add(button);
        button.setDisable(true);
        activePlayer.addNewVillage(button);

        //store new village for player and increase total points
        activePlayer.increaseNumOfVillagesByOne(numOfVillages, totalPoints);

        //remove material cards from player
        if (board.getMainGamePhase()) {
            activePlayer.decreaseNumOfWoodCards();
            activePlayer.decreaseNumOfClayCards();
            activePlayer.decreaseNumOfWheatCards();
            activePlayer.decreaseNumOfSheepCards();
            numOfCards.setText(String.valueOf(activePlayer.getNumOfCards()));
            //decrease number of materials on screen
            setMaterialsTextForActivePlayer();
        } else if (board.getInitialBuildingPhase()) {
            addMaterialsToPlayersForPlacedVillage(button);
            updateTextOnScreen();
        }

        //add village location for game moves
        villageCoordinates.add(village.getLayoutX());
        villageCoordinates.add(village.getLayoutY());

        //remove buttons for cities and villages from board
        cityVillageGroup.setVisible(false);
        cityVillageGroup.setDisable(true);

        if (board.getInitialBuildingPhase()) {
            //set new headline // maybe move?
            headlineText.setText(activePlayer.getName() + " place your road!");

            //enable buildRoad button to enable road build
            buildRoadButton.setDisable(false);
        } else {
            //set new headline
            headlineText.setText(activePlayer.getName() + " just placed a new village!");
            checkIfActivePlayerCanBuild();
            continueGameButton.setDisable(false);
        }

        checkIfGameOver();
    }

    private void addMaterialsToPlayersForPlacedVillage(Button village) {
        List<Text> allFieldNumbers = Arrays.asList(fieldNumber1, fieldNumber2, fieldNumber3, fieldNumber4, fieldNumber5,
                fieldNumber6, fieldNumber7, fieldNumber8, fieldNumber9, fieldNumber10, fieldNumber11, fieldNumber12,
                fieldNumber13, fieldNumber14, fieldNumber15, fieldNumber16, fieldNumber17, fieldNumber18, fieldNumber19);

        Bounds placedVillageBounds = village.localToScene(village.getBoundsInLocal());
        Point2D placeVillageCenter = new Point2D(placedVillageBounds.getMinX() + placedVillageBounds.getWidth() / 2,
                placedVillageBounds.getMinY() + placedVillageBounds.getHeight() / 2);

        for (Text fieldNumber : allFieldNumbers) {
            Bounds fieldNumberBounds = fieldNumber.localToScene(fieldNumber.getBoundsInLocal());
            Point2D fieldNumberCenter = new Point2D(fieldNumberBounds.getMinX() + fieldNumberBounds.getWidth() / 2,
                    fieldNumberBounds.getMinY() + fieldNumberBounds.getHeight() / 2);
            if (placeVillageCenter.distance(fieldNumberCenter) < 100) {
                switch (numberAndFieldsMap.get(fieldNumber)) {
                    case PASTURE -> activePlayer.increaseNumOfSheepCards();
                    case FIELDS -> activePlayer.increaseNumOfWheatCards();
                    case HILLS -> activePlayer.increaseNumOfClayCards();
                    case FOREST -> activePlayer.increaseNumOfWoodCards();
                    case MOUNTAINS -> activePlayer.increaseNumOfRockCards();
                }
            }
        }
    }

    @FXML
    private void buildCity(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            if (activePlayer == player1) {
                placeCity(player1.getPlacedCityList().get(player1.getNumOfCities()), button, player1,
                        player1NumOfVillages, player1NumOfCities, player1NumOfCards, player1TotalPoints);
            } else if (activePlayer == player2) {
                placeCity(player2.getPlacedCityList().get(player2.getNumOfCities()), button, player2,
                        player2NumOfVillages, player2NumOfCities, player2NumOfCards, player2TotalPoints);
            } else if (activePlayer == player3) {
                placeCity(player3.getPlacedCityList().get(player3.getNumOfCities()), button, player3,
                        player3NumOfVillages, player3NumOfCities, player3NumOfCards, player3TotalPoints);
            } else {
                placeCity(player4.getPlacedCityList().get(player4.getNumOfCities()), button, player4,
                        player4NumOfVillages, player4NumOfCities, player4NumOfCards, player4TotalPoints);
            }
        }
    }

    private void placeCity(Group city, Button button, Player activePlayer, Text numOfVillages, Text numOfCities, Text numOfCards, Text totalPoints) {
        //place city on board
        city.setVisible(true);
        Bounds villageBounds = button.localToScene(button.getBoundsInLocal());
        double x = villageBounds.getCenterX() - 30;
        double y = villageBounds.getCenterY() - 15;
        city.setLayoutX(x);
        city.setLayoutY(y);

        //check if there is a village on that spot and remove it
        for (Group village : activePlayer.getPlacedVillagesList()) {
            Bounds cityBounds = village.localToScene(village.getBoundsInLocal());
            Point2D center1 = new Point2D(villageBounds.getMinX() + villageBounds.getWidth() / 2, villageBounds.getMinY() + villageBounds.getHeight() / 2);
            Point2D center2 = new Point2D(cityBounds.getMinX() + cityBounds.getWidth() / 2, cityBounds.getMinY() + cityBounds.getHeight() / 2);
            if (center1.distance(center2) < 20) {
                village.setVisible(false);
            }
        }

        //Remove village button from players list of villages
        Button tmpVillage = null;
        for (Button village : activePlayer.getVillageList()) {
            Bounds cityBounds = village.localToScene(village.getBoundsInLocal());
            Point2D center1 = new Point2D(villageBounds.getMinX() + villageBounds.getWidth() / 2, villageBounds.getMinY() + villageBounds.getHeight() / 2);
            Point2D center2 = new Point2D(cityBounds.getMinX() + cityBounds.getWidth() / 2, cityBounds.getMinY() + cityBounds.getHeight() / 2);
            if (center1.distance(center2) < 20) {
                tmpVillage = village;
            }
        }

        if (tmpVillage != null) {
            activePlayer.removeVillage(tmpVillage);
        }
        activePlayer.addNewCity(button);

        //store new city for player and increase total points
        activePlayer.increaseNumOfCitiesByOne(numOfVillages, numOfCities, totalPoints);

        //remove material cards from player
        if (board.getMainGamePhase()) {
            activePlayer.decreaseNumOfWheatCards();
            activePlayer.decreaseNumOfWheatCards();
            activePlayer.decreaseNumOfRockCards();
            activePlayer.decreaseNumOfRockCards();
            activePlayer.decreaseNumOfRockCards();
            numOfCards.setText(String.valueOf(activePlayer.getNumOfCards()));
            //decrease number of materials on screen
            setMaterialsTextForActivePlayer();
        }

        //add city coordinates for game move
        cityCoordinates.add(city.getLayoutX());
        cityCoordinates.add(city.getLayoutY());

        headlineText.setText(activePlayer.getName() + " just placed a new city!");
        checkIfActivePlayerCanBuild();
        continueGameButton.setDisable(false);

        cityGroup.setVisible(true);
        cityGroup.setDisable(false);

        cityUpgrade1.setVisible(false);
        cityUpgrade2.setVisible(false);
        cityUpgrade3.setVisible(false);
        cityUpgrade4.setVisible(false);
        cityUpgrade5.setVisible(false);

        checkIfGameOver();
    }

    @FXML
    private void placeRobber(ActionEvent event) {
        //set new headline
        headlineText.setText(activePlayer.getName() + " build or end your turn!");

        //placing robber on map
        robberImage.setVisible(true);
        if (event.getSource() instanceof Button button) {
            Bounds hexBounds = button.localToScene(button.getBoundsInLocal());
            robberImage.setLayoutX(hexBounds.getCenterX() - 6);
            robberImage.setLayoutY(hexBounds.getCenterY() - 5);
        }

        //remove buttons from screen
        robberGroup.setDisable(true);
        robberGroup.setVisible(false);

        //disable field number of chosen spot
        List<Text> allFieldNumbers = Arrays.asList(fieldNumber1, fieldNumber2, fieldNumber3, fieldNumber4, fieldNumber5,
                fieldNumber6, fieldNumber7, fieldNumber8, fieldNumber9, fieldNumber10, fieldNumber11, fieldNumber12,
                fieldNumber13, fieldNumber14, fieldNumber15, fieldNumber16, fieldNumber17, fieldNumber18, fieldNumber19);

        for (Text fieldNumber : allFieldNumbers) {
            fieldNumber.setDisable(false);
            Bounds bounds1 = robberImage.localToScene(robberImage.getBoundsInLocal());
            Bounds bounds2 = fieldNumber.localToScene(fieldNumber.getBoundsInLocal());
            Point2D center1 = new Point2D(bounds1.getMinX() + bounds1.getWidth() / 2, bounds1.getMinY() + bounds1.getHeight() / 2);
            Point2D center2 = new Point2D(bounds2.getMinX() + bounds2.getWidth() / 2, bounds2.getMinY() + bounds2.getHeight() / 2);

            if (center1.distance(center2) < 30) {
                fieldNumber.setDisable(true);
            }
        }

        //robber coordinates for game move
        robberCoordinatesForGameMove.add(robberImage.getLayoutX());
        robberCoordinatesForGameMove.add(robberImage.getLayoutY());

        //enables buttons for the current player if he has enough materials
        checkIfActivePlayerCanBuild();
        rollDice.setDisable(true);
        continueGameButton.setDisable(false);
    }

    private void checkIfGameOver() {
        if (activePlayer.getTotalPoints() > 3) {
            buildCardButton.setDisable(true);
            replayGameMenuItem.setDisable(false);
            rollDice.setDisable(true);
            continueGameButton.setDisable(true);
            buildCityButton.setDisable(true);
            buildVillageButton.setDisable(true);
            buildRoadButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game over");
            alert.setHeaderText(activePlayer.getName() + " has collected 10 points and has won the game!");
            alert.showAndWait();

            GameMove newGameMove = new GameMove(activePlayer.getName(), "mainGamePhase",
                    activePlayer.getName() + " roll the dice!", firstDiceValue,
                    secondDiceValue, activePlayer.getName() + " rolled " +
                    (Integer.parseInt(firstDiceValue) + Integer.parseInt(secondDiceValue)), cityCoordinates,
                    activePlayer.getName() + " just placed a city!", villageCoordinates,
                    activePlayer.getName() + " just placed a village!", roadCoordinates,
                    activePlayer.getName() + " just placed a road!", robberCoordinatesForGameMove,
                    activePlayer.getName() + " just placed a robber!", activePlayer.getSheepCount(),
                    activePlayer.getWheatCount(), activePlayer.getClayCount(), activePlayer.getWoodCount(),
                    activePlayer.getRockCount());
            gameMoveList.add(newGameMove);

            FileUtils.createXmlFileForGameMoves(gameMoveList);
        }
    }

    @FXML
    private void buildRoad(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            if (activePlayer == player1) {
                placeRoad(player1.getPlacedRoadList().get(player1.getNumOfRoads()), button, player1, player1NumOfCards,player1NumOfRoads);
            } else if (activePlayer == player2) {
                placeRoad(player2.getPlacedRoadList().get(player2.getNumOfRoads()), button, player2, player2NumOfCards, player2NumOfRoads);
            } else if (activePlayer == player3) {
                placeRoad(player3.getPlacedRoadList().get(player3.getNumOfRoads()), button, player3, player3NumOfCards, player3NumOfRoads);
            } else {
                placeRoad(player4.getPlacedRoadList().get(player4.getNumOfRoads()), button, player4, player4NumOfCards, player4NumOfRoads);
            }
        }
    }

    private void placeRoad(Rectangle road, Button button, Player activePlayer, Text numOfCards, Text numOfRoads) {
        //place road on board
        road.setVisible(true);
        double x = button.getLayoutX();
        double y = button.getLayoutY();
        road.setLayoutX(x);
        road.setLayoutY(y);

        if (x == 6) {
            if (y == 168 || y == 296 || y == 423) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 122) {
            if (y == 102 || y == 230 || y == 360 || y == 487) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 236) {
            if (y == 39 || y == 168 || y == 296 || y == 423 || y == 552) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 350) {
            if (y == 102 || y == 230 || y == 360 || y == 487 || y == 615) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 464) {
            if (y == 168 || y == 296 || y == 423 || y == 552) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 580) {
            if (y == 230 || y == 360 || y == 487 || y == 615) {
                road.setRotate(-59);
            } else {
                road.setRotate(59);
            }
        } else if (x == 64 || x == 179 || x == 293 || x == 408 || x == 522) {
            road.setRotate(0);
        }

        //add button to list of placed roads, so it stays disabled
        tempDisabledRoads.add(button);

        //store new village for player
        activePlayer.increaseNumOfRoadsByOne(numOfRoads);

        //remove material cards from player;
        if (board.getMainGamePhase()) {
            activePlayer.decreaseNumOfWoodCards();
            activePlayer.decreaseNumOfClayCards();
            numOfCards.setText(String.valueOf(activePlayer.getNumOfCards()));
            //decrease number of materials on screen
            setMaterialsTextForActivePlayer();
        }

        //add road location for game moves
        roadCoordinates.add(road.getLayoutX());
        roadCoordinates.add(road.getLayoutY());
        roadCoordinates.add(road.getRotate());

        //remove buttons for roads from board
        roadGroup.setVisible(false);
        roadGroup.setDisable(true);

        if (board.getInitialBuildingPhase()) {
            //set new headline
            headlineText.setText(activePlayer.getName() + " end your turn!");

            //enable continueGameButton to finish the turn
            continueGameButton.setDisable(false);
        } else {
            //set new headline
            headlineText.setText(activePlayer.getName() + " just build a new road!");
            checkIfActivePlayerCanBuild();
            continueGameButton.setDisable(false);
        }
    }

    private void switchActivePlayer() {
        if (activePlayer == player1) {
            returnScaleOnPlayerCard(player1Group);
            activePlayer = player2;
            setNewScalePlayerCard(player2Group);
        } else if (activePlayer == player2) {
            returnScaleOnPlayerCard(player2Group);
            if (numberOfPlayers == 2) {
                activePlayer = player1;
                setNewScalePlayerCard(player1Group);
            } else {
                activePlayer = player3;
                setNewScalePlayerCard(player3Group);
            }
        } else if (activePlayer == player3) {
            returnScaleOnPlayerCard(player3Group);
            if (numberOfPlayers == 3) {
                activePlayer = player1;
                setNewScalePlayerCard(player1Group);
            } else {
                activePlayer = player4;
                setNewScalePlayerCard(player4Group);
            }
        } else {
            returnScaleOnPlayerCard(player4Group);
            activePlayer = player1;
            setNewScalePlayerCard(player1Group);
        }
        headlineText.setText(activePlayer.getName() + " it's your turn!");
    }

    private void returnScaleOnPlayerCard(Group playerGroup) {
        playerGroup.setScaleX(1);
        playerGroup.setScaleY(1);
        playerGroup.setOpacity(1);
        if (playerGroup == player1Group) {
            playerGroup.setLayoutX(playerGroup.getLayoutX() - 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() + 10);
        } else if (playerGroup == player2Group) {
            playerGroup.setLayoutX(playerGroup.getLayoutX() - 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() - 16);
        } else if (playerGroup == player3Group) {
            playerGroup.setLayoutX(playerGroup.getLayoutX() + 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() - 16);
        } else {
            playerGroup.setLayoutX(playerGroup.getLayoutX() + 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() + 16);
        }
    }
    private void setNewScalePlayerCard(Group playerGroup) {
        AnchorPane.clearConstraints(playerGroup);
        playerGroup.setScaleX(1.15);
        playerGroup.setScaleY(1.15);
        disableAllPlayerGroups();
        if (playerGroup == player1Group) {
            player1Group.setOpacity(1);
            playerGroup.setLayoutX(playerGroup.getLayoutX() + 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() - 10);
        } else if (playerGroup == player2Group) {
            player2Group.setOpacity(1);
            playerGroup.setLayoutX(playerGroup.getLayoutX() + 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() + 16);
        } else if (playerGroup == player3Group) {
            player3Group.setOpacity(1);
            playerGroup.setLayoutX(playerGroup.getLayoutX() - 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() + 16);
        } else {
            player4Group.setOpacity(1);
            playerGroup.setLayoutX(playerGroup.getLayoutX() - 23);
            playerGroup.setLayoutY(playerGroup.getLayoutY() - 16);
        }
    }

    private void disableAllPlayerGroups() {
        player1Group.setOpacity(0.75);
        player2Group.setOpacity(0.75);
        player3Group.setOpacity(0.75);
        player4Group.setOpacity(0.75);
    }

    @FXML
    private void showBuildCard() {
        if (buildGroup.isVisible()) {
            buildGroup.setVisible(false);
            buildCardGroup.setVisible(true);
        } else {
            buildGroup.setVisible(true);
            buildCardGroup.setVisible(false);
        }
    }

    private void placeFields(String field, int index) {
        if (index == 0) {
            fieldImage1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage1, field);
        } else if (index == 1) {
            fieldImage2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage2, field);
        } else if (index == 2) {
            fieldImage3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage3, field);
        } else if (index == 3) {
            fieldImage4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage4, field);
        } else if (index == 4) {
            fieldImage5.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage5, field);
        } else if (index == 5) {
            fieldImage6.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage6, field);
        } else if (index == 6) {
            fieldImage7.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage7, field);
        } else if (index == 7) {
            fieldImage8.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage8, field);
        } else if (index == 8) {
            fieldImage9.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage9, field);
        } else if (index == 9) {
            fieldImage10.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage10, field);
        } else if (index == 10) {
            fieldImage11.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage11, field);
        } else if (index == 11) {
            fieldImage12.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage12, field);
        } else if (index == 12) {
            fieldImage13.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage13, field);
        } else if (index == 13) {
            fieldImage14.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage14, field);
        } else if (index == 14) {
            fieldImage15.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage15, field);
        } else if (index == 15) {
            fieldImage16.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage16, field);
        } else if (index == 16) {
            fieldImage17.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage17, field);
        } else if (index == 17) {
            fieldImage18.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage18, field);
        } else if (index == 18) {
            fieldImage19.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/" + field + ".png"))));
            fieldsAndMaterialsMap.put(fieldImage19, field);
        }
    }

    private void placeNumbers(String number, int index) {
        if (index == 0) {
            if (isSixOrEight(number)) {
                fieldNumber1.setStyle(redText);
            } else {
                fieldNumber1.setStyle(blackText);
            }
            fieldNumber1.setText(number);
        } else if (index == 1) {
            if (isSixOrEight(number)) {
                fieldNumber2.setStyle(redText);
            } else {
                fieldNumber2.setStyle(blackText);
            }
            fieldNumber2.setText(number);
        } else if (index == 2) {
            if (isSixOrEight(number)) {
                fieldNumber3.setStyle(redText);
            } else {
                fieldNumber3.setStyle(blackText);
            }
            fieldNumber3.setText(number);
        } else if (index == 3) {
            if (isSixOrEight(number)) {
                fieldNumber4.setStyle(redText);
            } else {
                fieldNumber4.setStyle(blackText);
            }
            fieldNumber4.setText(number);
        } else if (index == 4) {
            if (isSixOrEight(number)) {
                fieldNumber5.setStyle(redText);
            } else {
                fieldNumber5.setStyle(blackText);
            }
            fieldNumber5.setText(number);
        } else if (index == 5) {
            if (isSixOrEight(number)) {
                fieldNumber6.setStyle(redText);
            } else {
                fieldNumber6.setStyle(blackText);
            }
            fieldNumber6.setText(number);
        } else if (index == 6) {
            if (isSixOrEight(number)) {
                fieldNumber7.setStyle(redText);
            } else {
                fieldNumber7.setStyle(blackText);
            }
            fieldNumber7.setText(number);
        } else if (index == 7) {
            if (isSixOrEight(number)) {
                fieldNumber8.setStyle(redText);
            } else {
                fieldNumber8.setStyle(blackText);
            }
            fieldNumber8.setText(number);
        } else if (index == 8) {
            if (isSixOrEight(number)) {
                fieldNumber9.setStyle(redText);
            } else {
                fieldNumber9.setStyle(blackText);
            }
            fieldNumber9.setText(number);
        } else if (index == 9) {
            if (isSixOrEight(number)) {
                fieldNumber10.setStyle(redText);
            } else {
                fieldNumber10.setStyle(blackText);
            }
            fieldNumber10.setText(number);
        } else if (index == 10) {
            if (isSixOrEight(number)) {
                fieldNumber11.setStyle(redText);
            } else {
                fieldNumber11.setStyle(blackText);
            }
            fieldNumber11.setText(number);
        } else if (index == 11) {
            if (isSixOrEight(number)) {
                fieldNumber12.setStyle(redText);
            } else {
                fieldNumber12.setStyle(blackText);
            }
            fieldNumber12.setText(number);
        } else if (index == 12) {
            if (isSixOrEight(number)) {
                fieldNumber13.setStyle(redText);
            } else {
                fieldNumber13.setStyle(blackText);
            }
            fieldNumber13.setText(number);
        } else if (index == 13) {
            if (isSixOrEight(number)) {
                fieldNumber14.setStyle(redText);
            } else {
                fieldNumber14.setStyle(blackText);
            }
            fieldNumber14.setText(number);
        } else if (index == 14) {
            if (isSixOrEight(number)) {
                fieldNumber15.setStyle(redText);
            } else {
                fieldNumber15.setStyle(blackText);
            }
            fieldNumber15.setText(number);
        } else if (index == 15) {
            if (isSixOrEight(number)) {
                fieldNumber16.setStyle(redText);
            } else {
                fieldNumber16.setStyle(blackText);
            }
            fieldNumber16.setText(number);
        } else if (index == 16) {
            if (isSixOrEight(number)) {
                fieldNumber17.setStyle(redText);
            } else {
                fieldNumber17.setStyle(blackText);
            }
            fieldNumber17.setText(number);
        } else if (index == 17) {
            if (isSixOrEight(number)) {
                fieldNumber18.setStyle(redText);
            } else {
                fieldNumber18.setStyle(blackText);
            }
            fieldNumber18.setText(number);
        } else if (index == 18) {
            if (isSixOrEight(number)) {
                fieldNumber19.setStyle(redText);
            } else {
                fieldNumber19.setStyle(blackText);
            }
            fieldNumber19.setText(number);
        }
    }

    private boolean isSixOrEight(String number) {
        return (number.equals(NUMBER6) || number.equals(NUMBER8));
    }

    @FXML
    public void startNewGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CatanGameScreen.fxml"));
            Parent root = loader.load();
            CatanGameController catanGameController = loader.getController();
            catanGameController.setPlayersOnBoard(playerNamesForCurrentGame, playerAvatarsForCurrentGame);
            Scene scene = new Scene(root);
            Stage stage = (Stage) rollDice.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.out.println("There was an error loading a scene! " + e);
        }
    }
    @FXML
    public void saveGame() {
        GameState recentGameState = new GameState();
        recentGameState.setHeadline(headlineText.getText());
        recentGameState.setCurrentBoardPhase(getCurrentGamePhase());
        recentGameState.setActivePlayer(activePlayer.getName());
        recentGameState.setCityBuildingDisabled(buildCityButton.isDisabled());
        recentGameState.setVillageBuildingDisabled(buildVillageButton.isDisabled());
        recentGameState.setRoadBuildingDisabled(buildRoadButton.isDisabled());
        recentGameState.setContinueButtonDisabled(continueGameButton.isDisabled());
        recentGameState.setDiceRollDisabled(rollDice.isDisabled());
        recentGameState.setPlayerNames(playerNamesForCurrentGame);
        recentGameState.setPlayerAvatars(playerAvatarsForCurrentGame);
        recentGameState.setNumOfPlayers(numberOfPlayers);
        recentGameState.setBoardImages(fields);
        recentGameState.setBoardNumbers(fieldNumbers);

        //save robber position
        if (robberImage.isVisible()) {
            List<Double> robberCoordinates = new ArrayList<>();
            robberCoordinates.add(robberImage.getLayoutX());
            robberCoordinates.add(robberImage.getLayoutY());
            recentGameState.setRobberVisible(true);
            recentGameState.setRobber(robberCoordinates);
        }

        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(player1);
        allPlayers.add(player2);
        if (numberOfPlayers > 2) {
            allPlayers.add(player3);
        }
        if (numberOfPlayers == 4) {
            allPlayers.add(player4);
        }

        //save all players infos
        Map<String, List<Integer>> allPlayerInfos = new HashMap<>();
        for (Player player : allPlayers) {
            allPlayerInfos.put(player.getName(), Arrays.asList(player.getNumOfCities(), player.getNumOfVillages(), player.getNumOfRoads(),
                    player.getNumOfCards(), player.getTotalPoints(), player.getSheepCount(), player.getWheatCount(), player.getClayCount(),
                    player.getWoodCount(), player.getRockCount()));
        }
        recentGameState.setAllPlayerInfos(allPlayerInfos);

        //save all players cities
        Map<String, List<Double>> allPlayersCities = new HashMap<>();
        List<Double> allCitiesCoordinates = new ArrayList<>();
        for (Player player : allPlayers) {
            for (Group playerCity : player.getPlacedCityList()) {
                allCitiesCoordinates.add(playerCity.getLayoutX());
                allCitiesCoordinates.add(playerCity.getLayoutY());
            }
            allPlayersCities.put(player.getName(), new ArrayList<>(allCitiesCoordinates));
            allCitiesCoordinates.clear();
        }
        recentGameState.setAllPlayerCities(allPlayersCities);

        //save all players city buttons
        Map<String, List<Double>> allPlayersCitiesButtons = new HashMap<>();
        List<Double> allCitiesButtonsCoordinates = new ArrayList<>();
        for (Player player : allPlayers) {
            for (Button playerCity : player.getCityList()) {
                allCitiesButtonsCoordinates.add(playerCity.getLayoutX());
                allCitiesButtonsCoordinates.add(playerCity.getLayoutY());
            }
            allPlayersCitiesButtons.put(player.getName(), new ArrayList<>(allCitiesButtonsCoordinates));
            allCitiesButtonsCoordinates.clear();
        }
        recentGameState.setAllPlayerCitiesButtons(allPlayersCitiesButtons);

        //save all players villages
        Map<String, List<Double>> allPlayersVillages = new HashMap<>();
        Map<String, List<Boolean>> allPlayersVillagesVisibility = new HashMap<>();
        List<Double> allVillagesCoordinates = new ArrayList<>();
        List<Boolean> areVillagesVisible = new ArrayList<>();
        for (Player player : allPlayers) {
            for (Group playerVillage : player.getPlacedVillagesList()) {
                allVillagesCoordinates.add(playerVillage.getLayoutX());
                allVillagesCoordinates.add(playerVillage.getLayoutY());
                if (playerVillage.isVisible()) {
                    areVillagesVisible.add(true);
                } else {
                    areVillagesVisible.add(false);
                }
            }
            allPlayersVillages.put(player.getName(), new ArrayList<>(allVillagesCoordinates));
            allPlayersVillagesVisibility.put(player.getName(), new ArrayList<>(areVillagesVisible));
            allVillagesCoordinates.clear();
            areVillagesVisible.clear();
        }
        recentGameState.setAllPlayerVillages(allPlayersVillages);
        recentGameState.setVisibleVillages(allPlayersVillagesVisibility);

        //save all players village buttons
        Map<String, List<Double>> allPlayersVillagesButtons = new HashMap<>();
        Map<String, List<Boolean>> allPlayersVillagesButtonsVisibility = new HashMap<>();
        List<Double> allVillagesButtonsCoordinates = new ArrayList<>();
        List<Boolean> areVillagesButtonsVisible = new ArrayList<>();
        for (Player player : allPlayers) {
            for (Button playerVillageButton : player.getVillageList()) {
                allVillagesButtonsCoordinates.add(playerVillageButton.getLayoutX());
                allVillagesButtonsCoordinates.add(playerVillageButton.getLayoutY());
                if (playerVillageButton.isVisible()) {
                    areVillagesButtonsVisible.add(true);
                } else {
                    areVillagesButtonsVisible.add(false);
                }
            }
            allPlayersVillagesButtons.put(player.getName(), new ArrayList<>(allVillagesButtonsCoordinates));
            allPlayersVillagesButtonsVisibility.put(player.getName(), new ArrayList<>(areVillagesButtonsVisible));
            allVillagesButtonsCoordinates.clear();
            areVillagesButtonsVisible.clear();
        }
        recentGameState.setAllPlayerVillagesButtons(allPlayersVillagesButtons);
        recentGameState.setVisibleVillagesButtons(allPlayersVillagesButtonsVisibility);

        //save all players roads
        Map<String, List<Double>> allPlayersRoads = new HashMap<>();
        List<Double> allRoadsCoordinates = new ArrayList<>();
        for (Player player : allPlayers) {
            for (Rectangle playerRoad : player.getPlacedRoadList()) {
                allRoadsCoordinates.add(playerRoad.getLayoutX());
                allRoadsCoordinates.add(playerRoad.getLayoutY());
                allRoadsCoordinates.add(playerRoad.getRotate());
            }
            allPlayersRoads.put(player.getName(), new ArrayList<>(allRoadsCoordinates));
            allRoadsCoordinates.clear();
        }
        recentGameState.setAllPlayerRoads(allPlayersRoads);

        //save all players cities


        //alert for action
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save game results");

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(SAVE_GAME_FILE_NAME)
            );

            oos.writeObject(recentGameState);

            alert.setHeaderText("Successful game save");
            alert.setContentText("Game saved!");
        } catch (IOException e) {
            alert.setHeaderText("Unsuccessful game save");
            alert.setContentText("Game wasn't saved! " +
                    e.getMessage());
        }
        alert.showAndWait();
    }

    private String getCurrentGamePhase() {
        if (board.getInitialBuildingPhase()) {
            return "Initial building phase";
        } else if (board.getFirstTurnDiceRollPhase()) {
            return "First turn dice roll phase";
        } else if (board.getMainGamePhase()) {
            return "Main game phase";
        } else {
            return "";
        }
    }

    public void loadGame() {
        //return scale for active player
        if (activePlayer == player1) {
            returnScaleOnPlayerCard(player1Group);
        } else if (activePlayer == player2) {
            returnScaleOnPlayerCard(player2Group);
        } else if (activePlayer == player3) {
            returnScaleOnPlayerCard(player3Group);
        } else {
            returnScaleOnPlayerCard(player4Group);
        }

        //alert for action
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Load game results");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_GAME_FILE_NAME));
            if (ois.readObject() instanceof GameState gs) {
                headlineText.setText(gs.getHeadline());
                setCurrentGamePhaseFromSavedGame(gs.getCurrentBoardPhase());
                numberOfPlayers = gs.getNumOfPlayers();
                buildCityButton.setDisable(gs.isCityBuildingDisabled());
                buildVillageButton.setDisable(gs.isVillageBuildingDisabled());
                buildRoadButton.setDisable(gs.isRoadBuildingDisabled());
                continueGameButton.setDisable(gs.isContinueButtonDisabled());
                rollDice.setDisable(gs.isDiceRollDisabled());
                playerNamesForCurrentGame = gs.getPlayerNames();
                playerAvatarsForCurrentGame = gs.getPlayerAvatars();
                boardImagesForCurrentGame = gs.getBoardImages();
                boardNumbersForCurrentGame = gs.getBoardNumbers();

                //load robber location from saved game
                if (gs.isRobberVisible()) {
                    List<Double> robberCoordinates;
                    robberCoordinates = gs.getRobber();
                    robberImage.setVisible(true);
                    robberImage.setLayoutX(robberCoordinates.get(0));
                    robberImage.setLayoutY(robberCoordinates.get(1));
                }

                setPlayersOnBoard(playerNamesForCurrentGame, playerAvatarsForCurrentGame);

                //set active player
                activePlayer = getActivePlayerFromSavedGame(gs.getActivePlayer());

                //set new scale for active player
                if (activePlayer == player2) {
                    returnScaleOnPlayerCard(player1Group);
                    setNewScalePlayerCard(player2Group);
                } else if (activePlayer == player3) {
                    returnScaleOnPlayerCard(player1Group);
                    setNewScalePlayerCard(player3Group);
                } else if (activePlayer == player4) {
                    returnScaleOnPlayerCard(player1Group);
                    setNewScalePlayerCard(player4Group);
                }

                setLastPlayerInTurn();

                //load images and numbers on board
                resetBoardFields();

                //creates a connection between all the numbers and hexes
                connectNumbersToFields();

                for (Map.Entry<Text, String> entry : numberAndFieldsMap.entrySet()) {
                    if (entry.getValue().equals(DESERT)) {
                        entry.getKey().setText("");
                    }
                }

                //set private variables to loaded values
                fields = boardImagesForCurrentGame;
                fieldNumbers = boardNumbersForCurrentGame;

                List<Player> allPlayers = new ArrayList<>();
                allPlayers.add(player1);
                allPlayers.add(player2);
                if (numberOfPlayers > 2) {
                    allPlayers.add(player3);
                }
                if (numberOfPlayers == 4) {
                    allPlayers.add(player4);
                }

                //load all players infos
                Map<String, List<Integer>> allPlayerInfos;
                allPlayerInfos = gs.getAllPlayerInfos();

                for (Player player : allPlayers) {
                    List<Integer> playerInfo = allPlayerInfos.get(player.getName());
                    player.setNumOfCities(playerInfo.get(0));
                    player.setNumOfVillages(playerInfo.get(1));
                    player.setNumOfRoads(playerInfo.get(2));
                    player.setNumOfCards(playerInfo.get(3));
                    player.setTotalPoints(playerInfo.get(4));
                    player.setSheepCount(playerInfo.get(5));
                    player.setWheatCount(playerInfo.get(6));
                    player.setClayCount(playerInfo.get(7));
                    player.setWoodCount(playerInfo.get(8));
                    player.setRockCount(playerInfo.get(9));
                }

                //load all players cities
                Map<String, List<Double>> allPlayersCities;
                allPlayersCities = gs.getAllPlayerCities();
                int index = 0;

                for (Player player : allPlayers) {
                    List<Double> playerCitiesCoordinates = allPlayersCities.get(player.getName());
                    List<Group> playerCities = player.getPlacedCityList();
                    for (Group playerCity : playerCities) {
                        Double x = playerCitiesCoordinates.get(index);
                        Double y = playerCitiesCoordinates.get(index + 1);
                        if (x == 0.0 && y == 0.0) {
                            playerCity.setVisible(false);
                        } else {
                            playerCity.setVisible(true);
                            playerCity.setLayoutX(x);
                            playerCity.setLayoutY(y);
                            index += 2;
                        }
                    }
                    index = 0;
                }

                //load all players city buttons
                Map<String, List<Double>> allPlayersCitiesButtons;
                allPlayersCitiesButtons = gs.getAllPlayerCitiesButtons();

                for (Player player : allPlayers) {
                    List<Double> playerCitiesButtonsCoordinates = allPlayersCitiesButtons.get(player.getName());
                    //List<Buttons> playerCities = player.getPlacedCityList();
                    for (int i = 0; i < playerCitiesButtonsCoordinates.size() / 2; i++) {
                        Double x = playerCitiesButtonsCoordinates.get(index);
                        Double y = playerCitiesButtonsCoordinates.get(index + 1);
                        Button playerCityButton = new Button();
                        cityVillageGroup.getChildren().add(playerCityButton);
                        playerCityButton.setLayoutX(x);
                        playerCityButton.setLayoutY(y);
                        index += 2;
                        player.addNewCity(playerCityButton);
                    }
                    index = 0;
                }

                //load all players villages
                Map<String, List<Double>> allPlayersVillages;
                allPlayersVillages = gs.getAllPlayerVillages();
                Map<String, List<Boolean>> allPlayersVillagesVisibility;
                allPlayersVillagesVisibility = gs.getVisibleVillages();

                for (Player player : allPlayers) {
                    List<Double> playerVillagesCoordinates = allPlayersVillages.get(player.getName());
                    List<Boolean> playerVillagesVisibility = allPlayersVillagesVisibility.get(player.getName());
                    List<Group> playerVillages = player.getPlacedVillagesList();
                    for (int i = 0; i < playerVillages.size(); i++) {
                        Double x = playerVillagesCoordinates.get(index);
                        Double y = playerVillagesCoordinates.get(index + 1);
                        if (x == 0.0 && y == 0.0) {
                            playerVillages.get(i).setVisible(false);
                        } else {
                            playerVillages.get(i).setVisible(true);
                            playerVillages.get(i).setLayoutX(x);
                            playerVillages.get(i).setLayoutY(y);
                            index += 2;
                        }
                        if (!playerVillagesVisibility.get(i)) {
                            playerVillages.get(i).setVisible(false);
                        }
                    }
                    index = 0;
                }

                //load all players village buttons
                Map<String, List<Double>> allPlayersVillagesButtons;
                allPlayersVillagesButtons = gs.getAllPlayerVillagesButtons();
                Map<String, List<Boolean>> allPlayersVillagesButtonsVisibility;
                allPlayersVillagesButtonsVisibility = gs.getVisibleVillagesButtons();

                for (Player player : allPlayers) {
                    List<Double> playerVillagesButtonsCoordinates = allPlayersVillagesButtons.get(player.getName());
                    List<Boolean> playerVillagesButtonsVisibility = allPlayersVillagesButtonsVisibility.get(player.getName());
                    for (Boolean visibleVillageButton : playerVillagesButtonsVisibility) {
                        Double x = playerVillagesButtonsCoordinates.get(index);
                        Double y = playerVillagesButtonsCoordinates.get(index + 1);
                        Button playerVillageButton = new Button();
                        cityVillageGroup.getChildren().add(playerVillageButton);
                        playerVillageButton.setLayoutX(x);
                        playerVillageButton.setLayoutY(y);
                        if (!visibleVillageButton) {
                            playerVillageButton.setVisible(false);
                        }
                        index += 2;
                        player.addNewVillage(playerVillageButton);
                    }
                    index = 0;
                }

                //load all players roads
                Map<String, List<Double>> allPlayersRoads;
                allPlayersRoads = gs.getAllPlayerRoads();

                for (Player player : allPlayers) {
                    List<Double> playerRoadsCoordinates = allPlayersRoads.get(player.getName());
                    List<Rectangle> playerRoads = player.getPlacedRoadList();
                    for (Rectangle playerRoad : playerRoads) {
                        Double x = playerRoadsCoordinates.get(index);
                        Double y = playerRoadsCoordinates.get(index + 1);
                        Double rotation = playerRoadsCoordinates.get(index + 2);
                        if (x == 0.0 && y == 0.0) {
                            playerRoad.setVisible(false);
                        } else {
                            playerRoad.setVisible(true);
                            playerRoad.setLayoutX(x);
                            playerRoad.setLayoutY(y);
                            playerRoad.setRotate(rotation);
                            index += 3;
                        }
                    }
                    index = 0;
                }

                //update number of cards for each player and number of materials for active player
                updateTextOnScreen();

                //update number of cities, villages, roads and total points for all players
                updateTextOnScreenForAllPlayers();
            }

            alert.setHeaderText("Successful game load");
            alert.setContentText("Game load!");
        } catch (IOException | ClassNotFoundException e) {
            alert.setHeaderText("Unsuccessful game load");
            alert.setContentText("Game load failed!" + e.getMessage());
        }
        alert.showAndWait();
    }

    private void setCurrentGamePhaseFromSavedGame(String currentGamePhase) {
        switch (currentGamePhase) {
            case "Initial building phase" -> {
                board.setInitialBuildingPhase(true);
                board.setFirstTurnDiceRollPhase(false);
                board.setMainGamePhase(false);
            }
            case "First turn dice roll phase" -> {
                board.setInitialBuildingPhase(false);
                board.setFirstTurnDiceRollPhase(true);
                board.setMainGamePhase(false);
            }
            case "Main game phase" -> {
                board.setInitialBuildingPhase(false);
                board.setFirstTurnDiceRollPhase(false);
                board.setMainGamePhase(true);
            }
            default -> System.out.println("Error loading game phase!");
        }
    }

    private Player getActivePlayerFromSavedGame(String activePlayerName) {
        if (activePlayerName.equals(player1.getName())) {
            return player1;
        } else if (activePlayerName.equals(player2.getName())) {
            return player2;
        } else if (activePlayerName.equals(player3.getName())) {
            return player3;
        } else if (activePlayerName.equals(player4.getName())) {
            return player4;
        } else {
            System.out.println("ERROR! No active players with that name!");
            return null;
        }
    }

    private void resetBoardFields() {
        int count = 0;

        for (int i = 0; i < boardImagesForCurrentGame.size(); i++) {
            placeFields(boardImagesForCurrentGame.get(i), i);
        }

        for (int i = 0; i < boardImagesForCurrentGame.size(); i++) {
            if (!boardImagesForCurrentGame.get(i).equals(DESERT)) {
                placeNumbers(boardNumbersForCurrentGame.get(count), i);
                count++;
            }
        }
    }

    private void updateTextOnScreenForAllPlayers() {
        player1NumOfCities.setText(String.valueOf(player1.getNumOfCities()));
        player1NumOfVillages.setText(String.valueOf(player1.getNumOfVillages()));
        player1NumOfRoads.setText(String.valueOf(player1.getNumOfRoads()));
        player1TotalPoints.setText(String.valueOf(player1.getTotalPoints()));
        player2NumOfCities.setText(String.valueOf(player2.getNumOfCities()));
        player2NumOfVillages.setText(String.valueOf(player2.getNumOfVillages()));
        player2NumOfRoads.setText(String.valueOf(player2.getNumOfRoads()));
        player2TotalPoints.setText(String.valueOf(player2.getTotalPoints()));
        if (numberOfPlayers > 2) {
            player3NumOfCities.setText(String.valueOf(player3.getNumOfCities()));
            player3NumOfVillages.setText(String.valueOf(player3.getNumOfVillages()));
            player3NumOfRoads.setText(String.valueOf(player3.getNumOfRoads()));
            player3TotalPoints.setText(String.valueOf(player3.getTotalPoints()));
        }
        if (numberOfPlayers == 4) {
            player4NumOfCities.setText(String.valueOf(player4.getNumOfCities()));
            player4NumOfVillages.setText(String.valueOf(player4.getNumOfVillages()));
            player4NumOfRoads.setText(String.valueOf(player4.getNumOfRoads()));
            player4TotalPoints.setText(String.valueOf(player4.getTotalPoints()));
        }
    }

    public void generateDocumentation() {
        Path start = Paths.get(".");

        List<Path> classFilePaths = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(start)) {
            classFilePaths.addAll(stream.toList());
        } catch (IOException e) {
            System.out.println("ERROR! " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter("catanDocumentation.html")) {
            fileWriter.write("<html><head><title>Documentation</title></head><body>");
            fileWriter.write("<h1>This is a documentation page for <b> Catan </b> application! </h1>");

            for (Path path : classFilePaths) {
                String classPathName = path.getFileName().toString();
                List<String> pathElements = new ArrayList<>();
                path.forEach(p -> pathElements.add(p.toString()));
                if (classPathName.endsWith("class") && !classPathName.contains("module-info")) {
                    try {
                        StringBuilder packageName = new StringBuilder();
                        boolean startPackage = false;
                        for (String pathElement : pathElements) {
                            if (startPackage) {
                                packageName.append(pathElement).append(".");
                                continue;
                            }
                            if (pathElement.equals("classes")) {
                                startPackage = true;
                            }
                        }
                        Class<?> myClass = Class.forName(packageName.substring(0, packageName.length() - 7));

                        // Get the class name
                        String className = myClass.getSimpleName();

                        // Get the package name
                        Package classPackage = myClass.getPackage();
                        packageName = new StringBuilder(classPackage.getName());

                        // Write class name and package to HTML file
                        fileWriter.write("<h2> Class: " + className + " (" + packageName + ")" + "</h2>");
                        fileWriter.write("- - - - - - - - - - - - - - - - - - - - - - - - - -");

                        // Get the constructors
                        Constructor<?>[] constructors = myClass.getConstructors();
                        for (Constructor<?> constructor : constructors) {
                            // Write constructors to HTML file
                            fileWriter.write("<p><b>Constructor:</b> " + constructor.getName() + "</p>");
                        }
                        fileWriter.write("- - - - - - - - - - - - - - - - - - - - - - - - - -");

                        // Get the annotations
                        Annotation[] annotations = myClass.getAnnotations();
                        if (annotations.length > 0) {
                            fileWriter.write("<p><b>Annotations:</b></p>");
                            for (Annotation annotation : annotations) {
                                fileWriter.write("<p>" + annotation.toString() + "</p>");
                            }
                        }

                        // Get declared fields
                        Field[] fields = myClass.getDeclaredFields();
                        for (Field field : fields) {
                            fileWriter.write("<p>Variable of type: <b> " + field.getType().getSimpleName() + "</b> with name: <b>" + field.getName() + "</b></p>");
                        }
                        if (fields.length > 0) {
                            fileWriter.write("- - - - - - - - - - - - - - - - - - - - - - - - - -");
                        }


                        // Get declared methods
                        Method[] methods = myClass.getDeclaredMethods();
                        for (Method method : methods) {
                            fileWriter.write("<p>Method with name: <b>" + method.getName() + "</b> has return type: <b>" + method.getReturnType().getSimpleName() + "</b></p>");
                        }
                        fileWriter.write("- - - - - - - - - - - - - - - - - - - - - - - - - -");
                        fileWriter.write("<br><br><br>");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            fileWriter.write("</body></html>");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Generating documentation");
            alert.setHeaderText("Successful generation of documentation!");
            alert.setContentText("Project documentation was generated to catanDocumentation.html!");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("ERROR! " + e.getMessage());
        }
    }

    /*public void replayGame() {
        Platform.runLater(new ReplayThread());
    }*/

    private void clearBoard(Player player) {
        for(Group village : player.getPlacedVillagesList()) {
            village.setVisible(false);
        }
        for(Group city : player.getPlacedCityList()) {
            city.setVisible(false);
        }
        for(Rectangle road : player.getPlacedRoadList()) {
            road.setVisible(false);
        }
    }

    public void replayGame() {
        //clear the board
        setBoardText();
        robberImage.setVisible(false);
        clearBoard(player1);
        clearBoard(player2);
        if (numberOfPlayers > 2) {
            clearBoard(player3);
        }
        if (numberOfPlayers > 3) {
            clearBoard(player4);
        }

        if(numberOfPlayers == 2) {
            Platform.runLater(new ReplayThread(headlineText, rollDice1, rollDice2, player1.getPlacedCityList(),
                    player2.getPlacedCityList(), player1.getPlacedVillagesList(), player2.getPlacedVillagesList(),
                    player1.getPlacedRoadList(), player2.getPlacedRoadList(), robberImage, sheepCountText, wheetCountText,
                    clayCountText, woodCountText, rockCountText, player1, player2));
        } else if (numberOfPlayers == 3) {
            Platform.runLater(new ReplayThread(headlineText, rollDice1, rollDice2, player1.getPlacedCityList(),
                    player2.getPlacedCityList(), player3.getPlacedCityList(), player1.getPlacedVillagesList(),
                    player2.getPlacedVillagesList(), player3.getPlacedVillagesList(), player1.getPlacedRoadList(),
                    player2.getPlacedRoadList(), player3.getPlacedRoadList(), robberImage, sheepCountText,
                    wheetCountText, clayCountText, woodCountText, rockCountText, player1, player2, player3));
        } else {
            Platform.runLater(new ReplayThread(headlineText, rollDice1, rollDice2, player1.getPlacedCityList(),
                    player2.getPlacedCityList(), player3.getPlacedCityList(), player4.getPlacedCityList(),
                    player1.getPlacedVillagesList(), player2.getPlacedVillagesList(), player3.getPlacedVillagesList(),
                    player4.getPlacedVillagesList(), player1.getPlacedRoadList(), player2.getPlacedRoadList(),
                    player3.getPlacedRoadList(), player4.getPlacedRoadList(), robberImage, sheepCountText,
                    wheetCountText, clayCountText, woodCountText, rockCountText, player1, player2, player3, player4));
        }
    }
}