package hr.tvz.catan.catan.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameMove {

    private String activePlayerName, gamePhase, headlineText1, headlineText2, headlineText3, headlineText4,
            headlineText5, headlineText6, diceImageOne, diceImageTwo;

    private List<Double> cityCoordinates = new ArrayList<>();

    private List<Double> villageCoordinates = new ArrayList<>();

    private List<Double> roadCoordinates = new ArrayList<>();

    private List<Double> robberCoordinates = new ArrayList<>();

    private int sheepCount, wheatCount, clayCount, woodCount, rockCount;

    public GameMove(String activePlayerName) {
        this.activePlayerName = activePlayerName;
    }

    public GameMove(String activePlayerName, String gamePhase, String headlineText, String image1URL, String image2URL,
                    String diceRollHeadlineText) {
        this.activePlayerName = activePlayerName;
        this.gamePhase = gamePhase;
        this.headlineText1 = headlineText;
        this.diceImageOne = image1URL;
        this.diceImageTwo = image2URL;
        this.headlineText2 = diceRollHeadlineText;
    }

    public GameMove(String activePlayerName, String gamePhase, String headlineText1, List<Double> villageCoordinates,
                    String headlineText2, List<Double> roadCoordinates, String headlineText3, int sheepCount, int wheatCount, int clayCount,
                    int woodCount, int rockCount) {
        this.villageCoordinates.clear();
        this.roadCoordinates.clear();
        this.activePlayerName = activePlayerName;
        this.gamePhase = gamePhase;
        this.headlineText1 = headlineText1;
        this.villageCoordinates.addAll(villageCoordinates);
        this.headlineText2 = headlineText2;
        this.roadCoordinates.addAll(roadCoordinates);
        this.headlineText3 = headlineText3;
        this.sheepCount = sheepCount;
        this.wheatCount = wheatCount;
        this.clayCount = clayCount;
        this.woodCount = woodCount;
        this.rockCount = rockCount;
    }

    public GameMove(String activePlayerName, String gamePhase, String headlineText1, String diceImageOne,
                    String diceImageTwo, String headlineText2, List<Double> cityCoordinates, String headlineText3,
                    List<Double> villageCoordinates, String headlineText4, List<Double> roadCoordinates,
                    String headlineText5, List<Double> robberCoordinates, String headlineText6, int sheepCount,
                    int wheatCount, int clayCount, int woodCount, int rockCount) {
        this.cityCoordinates.clear();
        this.villageCoordinates.clear();
        this.roadCoordinates.clear();
        this.robberCoordinates.clear();
        this.activePlayerName = activePlayerName;
        this.gamePhase = gamePhase;
        this.headlineText1 = headlineText1;
        this.diceImageOne = diceImageOne;
        this.diceImageTwo = diceImageTwo;
        this.headlineText2 = headlineText2;
        this.cityCoordinates.addAll(cityCoordinates);
        this.headlineText3 = headlineText3;
        this.villageCoordinates.addAll(villageCoordinates);
        this.headlineText4 = headlineText4;
        this.roadCoordinates.addAll(roadCoordinates);
        this.headlineText5 = headlineText5;
        this.robberCoordinates.addAll(robberCoordinates);
        this.headlineText6 = headlineText6;
        this.sheepCount = sheepCount;
        this.wheatCount = wheatCount;
        this.clayCount = clayCount;
        this.woodCount = woodCount;
        this.rockCount = rockCount;
    }

    public void addCityCoordinate(Double xy) {
        this.cityCoordinates.add(xy);
    }
    public void addVillageCoordinate(Double xy) {
        this.villageCoordinates.add(xy);
    }
    public void addRoadCoordinate(Double xyz) {
        this.roadCoordinates.add(xyz);
    }
    public void addRobberCoordinates(Double xy) {
        this.robberCoordinates.add(xy);
    }
}
