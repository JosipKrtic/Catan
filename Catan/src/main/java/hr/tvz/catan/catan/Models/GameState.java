package hr.tvz.catan.catan.Models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState implements Serializable {

    private String headline;

    private int numOfPlayers;

    private String currentBoardPhase;

    private String activePlayer;

    private boolean isVillageBuildingDisabled;

    private boolean isCityBuildingDisabled;

    private boolean isRoadBuildingDisabled;

    private boolean isContinueButtonDisabled;

    private boolean isDiceRollDisabled;

    private List<String> playerNames;

    private List<String> playerAvatars;

    private List<String> boardImages;

    private List<String> boardNumbers;

    private boolean isRobberVisible;

    private List<Double> robber;

    private Map <String, List<Boolean>> visibleVillages;

    private Map<String, List<Boolean>> visibleVillagesButtons;

    private Map<String, List<Integer>> allPlayerInfos;

    private Map<String, List<Double>> allPlayerCities;

    private Map<String, List<Double>> allPlayerCitiesButtons;

    private Map<String, List<Double>> allPlayerVillages;

    private Map<String, List<Double>> allPlayerVillagesButtons;

    private Map<String, List<Double>> allPlayerRoads;
}
