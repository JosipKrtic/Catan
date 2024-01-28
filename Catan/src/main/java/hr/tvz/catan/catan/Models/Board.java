package hr.tvz.catan.catan.Models;

public class Board {
    private Boolean firstTurnDiceRollPhase;

    private String initialBuildingPhaseFlag;
    private Boolean initialBuildingPhase;
    private String mainGamePhaseFlag;
    private Boolean mainGamePhase;

    public Board() {
        this.firstTurnDiceRollPhase = true;
        this.initialBuildingPhaseFlag = "Initial Building Phase";
        this.initialBuildingPhase = false;
        this.mainGamePhaseFlag = "Main Game Phase";
        this.mainGamePhase = false;
    }

    public Boolean getFirstTurnDiceRollPhase() {
        return firstTurnDiceRollPhase;
    }

    public String getInitialBuildingPhaseFlag() {
        return initialBuildingPhaseFlag;
    }

    public Boolean getInitialBuildingPhase() {
        return initialBuildingPhase;
    }

    public String getMainGamePhaseFlag() {
        return mainGamePhaseFlag;
    }

    public Boolean getMainGamePhase() {
        return mainGamePhase;
    }
    public void setFirstTurnDiceRollPhase(Boolean firstTurnDiceRollPhase) {
        this.firstTurnDiceRollPhase = firstTurnDiceRollPhase;
    }

    public void setInitialBuildingPhaseFlag(String initialBuildingPhaseFlag) {
        this.initialBuildingPhaseFlag = initialBuildingPhaseFlag;
    }

    public void setInitialBuildingPhase(Boolean initialBuildingPhase) {
        this.initialBuildingPhase = initialBuildingPhase;
    }

    public void setMainGamePhaseFlag(String mainGamePhaseFlag) {
        this.mainGamePhaseFlag = mainGamePhaseFlag;
    }

    public void setMainGamePhase(Boolean mainGamePhase) {
        this.mainGamePhase = mainGamePhase;
    }
}
