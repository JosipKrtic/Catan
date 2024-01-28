package hr.tvz.catan.catan.Threads;

import hr.tvz.catan.catan.CatanGameController;
import hr.tvz.catan.catan.File.FileUtils;
import hr.tvz.catan.catan.Models.GameMove;
import hr.tvz.catan.catan.Models.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplayThread implements Runnable {

    private Text headlineText;
    private ImageView rollDice1;
    private ImageView rollDice2;
    private List<Group> player1Cities = new ArrayList<>();
    private List<Group> player2Cities = new ArrayList<>();
    private List<Group> player3Cities = new ArrayList<>();
    private List<Group> player4Cities = new ArrayList<>();
    private List<Group> player1Villages = new ArrayList<>();
    private List<Group> player2Villages = new ArrayList<>();
    private List<Group> player3Villages = new ArrayList<>();
    private List<Group> player4Villages = new ArrayList<>();
    private List<Rectangle> player1Roads = new ArrayList<>();
    private List<Rectangle> player2Roads = new ArrayList<>();
    private List<Rectangle> player3Roads = new ArrayList<>();
    private List<Rectangle> player4Roads = new ArrayList<>();
    private Group robber;
    private Text sheepCount;
    private Text wheatCount;
    private Text clayCount;
    private Text woodCount;
    private Text rockCount;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    int player1CityCounter = 0;
    int player1VillageCounter = 0;
    int player1RoadCounter = 0;
    int player2CityCounter = 0;
    int player2VillageCounter = 0;
    int player2RoadCounter = 0;
    int player3CityCounter = 0;
    int player3VillageCounter = 0;
    int player3RoadCounter = 0;
    int player4CityCounter = 0;
    int player4VillageCounter = 0;
    int player4RoadCounter = 0;
    boolean wasDiceRolled = false;

    public ReplayThread(Text headlineText, ImageView rollDice1, ImageView rollDice2, List<Group> player1Cities,
                        List<Group> player2Cities, List<Group> player1Villages, List<Group> player2Villages,
                        List<Rectangle> player1Roads, List<Rectangle> player2Roads, Group robber, Text sheepCount,
                        Text wheatCount, Text clayCount, Text woodCount, Text rockCount, Player player1, Player player2) {
        this.headlineText = headlineText;
        this.rollDice1 = rollDice1;
        this.rollDice2 = rollDice2;
        this.player1Cities.addAll(player1Cities);
        this.player1Villages.addAll(player1Villages);
        this.player1Roads.addAll(player1Roads);
        this.player2Cities.addAll(player2Cities);
        this.player2Villages.addAll(player2Villages);
        this.player2Roads.addAll(player2Roads);
        this.robber = robber;
        this.sheepCount = sheepCount;
        this.wheatCount = wheatCount;
        this.clayCount = clayCount;
        this.woodCount = woodCount;
        this.rockCount = rockCount;
        this.player1 = player1;
        this.player2 = player2;
    }

    public ReplayThread(Text headlineText, ImageView rollDice1, ImageView rollDice2, List<Group> player1Cities,
                        List<Group> player2Cities, List<Group> player3Cities, List<Group> player1Villages,
                        List<Group> player2Villages, List<Group> player3Villages, List<Rectangle> player1Roads,
                        List<Rectangle> player2Roads, List<Rectangle> player3Roads, Group robber, Text sheepCount,
                        Text wheatCount, Text clayCount, Text woodCount, Text rockCount, Player player1,
                        Player player2, Player player3) {
        this.headlineText = headlineText;
        this.rollDice1 = rollDice1;
        this.rollDice2 = rollDice2;
        this.player1Cities.addAll(player1Cities);
        this.player1Villages.addAll(player1Villages);
        this.player1Roads.addAll(player1Roads);
        this.player2Cities.addAll(player2Cities);
        this.player2Villages.addAll(player2Villages);
        this.player2Roads.addAll(player2Roads);
        this.player3Cities.addAll(player3Cities);
        this.player3Villages.addAll(player3Villages);
        this.player3Roads.addAll(player3Roads);
        this.robber = robber;
        this.sheepCount = sheepCount;
        this.wheatCount = wheatCount;
        this.clayCount = clayCount;
        this.woodCount = woodCount;
        this.rockCount = rockCount;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    public ReplayThread(Text headlineText, ImageView rollDice1, ImageView rollDice2, List<Group> player1Cities,
                        List<Group> player2Cities, List<Group> player3Cities, List<Group> player4Cities,
                        List<Group> player1Villages, List<Group> player2Villages, List<Group> player3Villages,
                        List<Group> player4Villages, List<Rectangle> player1Roads, List<Rectangle> player2Roads,
                        List<Rectangle> player3Roads, List<Rectangle> player4Roads, Group robber, Text sheepCount,
                        Text wheatCount, Text clayCount, Text woodCount, Text rockCount, Player player1,
                        Player player2, Player player3, Player player4) {
        this.headlineText = headlineText;
        this.rollDice1 = rollDice1;
        this.rollDice2 = rollDice2;
        this.player1Cities.addAll(player1Cities);
        this.player1Villages.addAll(player1Villages);
        this.player1Roads.addAll(player1Roads);
        this.player2Cities.addAll(player2Cities);
        this.player2Villages.addAll(player2Villages);
        this.player2Roads.addAll(player2Roads);
        this.player3Cities.addAll(player3Cities);
        this.player3Villages.addAll(player3Villages);
        this.player3Roads.addAll(player3Roads);
        this.player4Cities.addAll(player4Cities);
        this.player4Villages.addAll(player4Villages);
        this.player4Roads.addAll(player4Roads);
        this.robber = robber;
        this.sheepCount = sheepCount;
        this.wheatCount = wheatCount;
        this.clayCount = clayCount;
        this.woodCount = woodCount;
        this.rockCount = rockCount;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
    }

    @Override
    public void run() {
        try {
            List<GameMove> readGameMoves = FileUtils.createGameMoveListFromXmlFile();
            Iterator<GameMove> iter = readGameMoves.iterator();

            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                GameMove gameMove = iter.next();
                headlineText.setText(gameMove.getHeadlineText1());
                if(gameMove.getDiceImageOne() != null && gameMove.getDiceImageTwo() != null) {
                    Image firstDice = new Image("file:/D:/Java2/Catan/src/main/resources/hr/tvz/catan/catan/Images/" + gameMove.getDiceImageOne() + ".png");
                    Image secondDice = new Image("file:/D:/Java2/Catan/src/main/resources/hr/tvz/catan/catan/Images/" + gameMove.getDiceImageTwo() + ".png");
                    rollDice1.setImage(firstDice);
                    rollDice2.setImage(secondDice);
                    headlineText.setText(gameMove.getHeadlineText2());
                    wasDiceRolled = true;
                } else {
                    wasDiceRolled = false;
                }
                if(!gameMove.getCityCoordinates().isEmpty()) {
                    if(gameMove.getActivePlayerName().equals(player1.getName())) {
                        player1Cities.get(player1CityCounter).setVisible(true);
                        player1Cities.get(player1CityCounter).setLayoutX(gameMove.getCityCoordinates().get(0));
                        player1Cities.get(player1CityCounter).setLayoutY(gameMove.getCityCoordinates().get(1));
                        player1CityCounter++;
                        headlineText.setText(gameMove.getHeadlineText3());
                    } else if (gameMove.getActivePlayerName().equals(player2.getName())) {
                        player2Cities.get(player2CityCounter).setVisible(true);
                        player2Cities.get(player2CityCounter).setLayoutX(gameMove.getCityCoordinates().get(0));
                        player2Cities.get(player2CityCounter).setLayoutY(gameMove.getCityCoordinates().get(1));
                        player2CityCounter++;
                        headlineText.setText(gameMove.getHeadlineText3());
                    } else if (gameMove.getActivePlayerName().equals(player3.getName())) {
                        player3Cities.get(player3CityCounter).setVisible(true);
                        player3Cities.get(player3CityCounter).setLayoutX(gameMove.getCityCoordinates().get(0));
                        player3Cities.get(player3CityCounter).setLayoutY(gameMove.getCityCoordinates().get(1));
                        player3CityCounter++;
                        headlineText.setText(gameMove.getHeadlineText3());
                    } else if (gameMove.getActivePlayerName().equals(player4.getName())) {
                        player4Cities.get(player4CityCounter).setVisible(true);
                        player4Cities.get(player4CityCounter).setLayoutX(gameMove.getCityCoordinates().get(0));
                        player4Cities.get(player4CityCounter).setLayoutY(gameMove.getCityCoordinates().get(1));
                        player4CityCounter++;
                        headlineText.setText(gameMove.getHeadlineText3());
                    }
                }
                if(!gameMove.getVillageCoordinates().isEmpty()) {
                    if(gameMove.getActivePlayerName().equals(player1.getName())) {
                        player1Villages.get(player1VillageCounter).setVisible(true);
                        player1Villages.get(player1VillageCounter).setLayoutX(gameMove.getVillageCoordinates().get(0));
                        player1Villages.get(player1VillageCounter).setLayoutY(gameMove.getVillageCoordinates().get(1));
                        player1VillageCounter++;
                    } else if (gameMove.getActivePlayerName().equals(player2.getName())) {
                        player2Villages.get(player2VillageCounter).setVisible(true);
                        player2Villages.get(player2VillageCounter).setLayoutX(gameMove.getVillageCoordinates().get(0));
                        player2Villages.get(player2VillageCounter).setLayoutY(gameMove.getVillageCoordinates().get(1));
                        player2VillageCounter++;
                    } else if (gameMove.getActivePlayerName().equals(player3.getName())) {
                        player3Villages.get(player3VillageCounter).setVisible(true);
                        player3Villages.get(player3VillageCounter).setLayoutX(gameMove.getVillageCoordinates().get(0));
                        player3Villages.get(player3VillageCounter).setLayoutY(gameMove.getVillageCoordinates().get(1));
                        player3VillageCounter++;
                    } else if (gameMove.getActivePlayerName().equals(player4.getName())) {
                        player4Villages.get(player4VillageCounter).setVisible(true);
                        player4Villages.get(player4VillageCounter).setLayoutX(gameMove.getVillageCoordinates().get(0));
                        player4Villages.get(player4VillageCounter).setLayoutY(gameMove.getVillageCoordinates().get(1));
                        player4VillageCounter++;
                    }
                    if (wasDiceRolled) {
                        headlineText.setText(gameMove.getHeadlineText4());
                    } else {
                        headlineText.setText(gameMove.getHeadlineText2());
                    }
                }
                if(!gameMove.getRoadCoordinates().isEmpty()) {
                    if(gameMove.getActivePlayerName().equals(player1.getName())) {
                        player1Roads.get(player1RoadCounter).setVisible(true);
                        player1Roads.get(player1RoadCounter).setLayoutX(gameMove.getRoadCoordinates().get(0));
                        player1Roads.get(player1RoadCounter).setLayoutY(gameMove.getRoadCoordinates().get(1));
                        player1Roads.get(player1RoadCounter).setRotate(gameMove.getRoadCoordinates().get(2));
                        player1RoadCounter++;
                    } else if(gameMove.getActivePlayerName().equals(player2.getName())) {
                        player2Roads.get(player2RoadCounter).setVisible(true);
                        player2Roads.get(player2RoadCounter).setLayoutX(gameMove.getRoadCoordinates().get(0));
                        player2Roads.get(player2RoadCounter).setLayoutY(gameMove.getRoadCoordinates().get(1));
                        player2Roads.get(player2RoadCounter).setRotate(gameMove.getRoadCoordinates().get(2));
                        player2RoadCounter++;
                    } else if (gameMove.getActivePlayerName().equals(player3.getName())) {
                        player3Roads.get(player3RoadCounter).setVisible(true);
                        player3Roads.get(player3RoadCounter).setLayoutX(gameMove.getRoadCoordinates().get(0));
                        player3Roads.get(player3RoadCounter).setLayoutY(gameMove.getRoadCoordinates().get(1));
                        player3Roads.get(player3RoadCounter).setRotate(gameMove.getRoadCoordinates().get(2));
                        player3RoadCounter++;
                    } else if (gameMove.getActivePlayerName().equals(player4.getName())) {
                        player4Roads.get(player4RoadCounter).setVisible(true);
                        player4Roads.get(player4RoadCounter).setLayoutX(gameMove.getRoadCoordinates().get(0));
                        player4Roads.get(player4RoadCounter).setLayoutY(gameMove.getRoadCoordinates().get(1));
                        player4Roads.get(player4RoadCounter).setRotate(gameMove.getRoadCoordinates().get(2));
                        player4RoadCounter++;
                    }
                    if (wasDiceRolled) {
                        headlineText.setText(gameMove.getHeadlineText5());
                    } else {
                        headlineText.setText(gameMove.getHeadlineText3());
                    }
                }
                if(!gameMove.getRobberCoordinates().isEmpty()) {
                    robber.setVisible(true);
                    robber.setLayoutX(gameMove.getRobberCoordinates().get(0));
                    robber.setLayoutY(gameMove.getRobberCoordinates().get(1));
                    headlineText.setText(gameMove.getHeadlineText6());
                }
                sheepCount.setText(String.valueOf(gameMove.getSheepCount()));
                wheatCount.setText(String.valueOf(gameMove.getWheatCount()));
                clayCount.setText(String.valueOf(gameMove.getClayCount()));
                woodCount.setText(String.valueOf(gameMove.getWoodCount()));
                rockCount.setText(String.valueOf(gameMove.getRockCount()));

            }), new KeyFrame(Duration.seconds(1.5)));
            clock.setCycleCount(readGameMoves.size());
            clock.play();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
