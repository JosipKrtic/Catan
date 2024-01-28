package hr.tvz.catan.catan.Models;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String avatar;
    private int sheepCount;
    private int wheatCount;
    private int clayCount;
    private int woodCount;
    private int rockCount;
    private int numOfCities;
    private int numOfVillages;
    private int numOfRoads;
    private int numOfCards;
    private int totalPoints;
    private List<Button> villageList;
    private List<Button> cityList;
    private List<Group> placedVillagesList = new ArrayList<>();
    private List<Group> placedCityList = new ArrayList<>();
    private List<Rectangle> placedRoadList = new ArrayList<>();

    public Player(String name, String avatar, List<Group> placedVillagesList, List<Group> placedCityList, List<Rectangle> placedRoadList) {
        super();
        this.name = name;
        this.avatar = avatar;
        this.sheepCount = 0;
        this.wheatCount = 0;
        this.woodCount = 0;
        this.clayCount = 0;
        this.rockCount = 0;
        this.numOfCities = 0;
        this.numOfVillages = 0;
        this.numOfRoads = 0;
        this.numOfCards = 0;
        this.totalPoints = 0;
        this.villageList = new ArrayList<>();
        this.cityList = new ArrayList<>();
        this.placedVillagesList.addAll(placedVillagesList);
        this.placedCityList.addAll(placedCityList);
        this.placedRoadList.addAll(placedRoadList);
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getSheepCount() {
        return sheepCount;
    }

    public int getWheatCount() {
        return wheatCount;
    }

    public int getClayCount() {
        return clayCount;
    }

    public int getWoodCount() {
        return woodCount;
    }

    public int getRockCount() {
        return rockCount;
    }

    public int getNumOfCities() {
        return numOfCities;
    }

    public int getNumOfVillages() {
        return numOfVillages;
    }

    public int getNumOfRoads() {
        return numOfRoads;
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSheepCount(int sheepCount) {
        this.sheepCount = sheepCount;
    }

    public void setWheatCount(int wheatCount) {
        this.wheatCount = wheatCount;
    }

    public void setClayCount(int clayCount) {
        this.clayCount = clayCount;
    }

    public void setWoodCount(int woodCount) {
        this.woodCount = woodCount;
    }

    public void setRockCount(int rockCount) {
        this.rockCount = rockCount;
    }

    public void setNumOfCities(int numOfCities) {
        this.numOfCities = numOfCities;
    }

    public void setNumOfVillages(int numOfVillages) {
        this.numOfVillages = numOfVillages;
    }

    public void setNumOfRoads(int numOfRoads) {
        this.numOfRoads = numOfRoads;
    }

    public void setNumOfCards(int numOfCards) {
        this.numOfCards = numOfCards;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void increaseNumOfVillagesByOne(Text numOfVillages, Text totalPoints) {
        this.numOfVillages += 1;
        this.totalPoints += 1;
        numOfVillages.setText(String.valueOf(this.numOfVillages));
        totalPoints.setText(String.valueOf(this.totalPoints));
    }

    public void increaseNumOfCitiesByOne(Text numOfVillages, Text numOfCities, Text totalPoints) {
        this.numOfVillages -= 1;
        this.numOfCities += 1;
        this.totalPoints += 1;
        numOfVillages.setText(String.valueOf(this.numOfVillages));
        numOfCities.setText(String.valueOf(this.numOfCities));
        totalPoints.setText(String.valueOf(this.totalPoints));
    }

    public void increaseNumOfRoadsByOne(Text numOfRoads) {
        this.numOfRoads += 1;
        numOfRoads.setText(String.valueOf(this.numOfRoads));
    }

    public List<Button> getVillageList() {
        return villageList;
    }

    public void addNewVillage(Button village) {
        this.villageList.add(village);
    }

    public void removeVillage(Button village) {
        this.villageList.remove(village);
    }

    public List<Button> getCityList() {
        return cityList;
    }

    public void addNewCity(Button city) {
        this.cityList.add(city);
    }

    public List<Group> getPlacedVillagesList() {
        return placedVillagesList;
    }

    public List<Group> getPlacedCityList() {
        return placedCityList;
    }

    public List<Rectangle> getPlacedRoadList() {
        return placedRoadList;
    }

    public void increaseNumOfSheepCards() {
        this.sheepCount += 1;
        this.numOfCards += 1;
    }
    public void increaseNumOfRockCards() {
        this.rockCount += 1;
        this.numOfCards += 1;
    }
    public void increaseNumOfClayCards() {
        this.clayCount += 1;
        this.numOfCards += 1;
    }
    public void increaseNumOfWheatCards() {
        this.wheatCount += 1;
        this.numOfCards += 1;
    }
    public void increaseNumOfWoodCards() {
        this.woodCount += 1;
        this.numOfCards += 1;
    }

    public void decreaseNumOfSheepCards() {
        this.sheepCount -= 1;
        this.numOfCards -= 1;
    }
    public void decreaseNumOfRockCards() {
        this.rockCount -= 1;
        this.numOfCards -= 1;
    }
    public void decreaseNumOfClayCards() {
        this.clayCount -= 1;
        this.numOfCards -= 1;
    }
    public void decreaseNumOfWheatCards() {
        this.wheatCount -= 1;
        this.numOfCards -= 1;
    }
    public void decreaseNumOfWoodCards() {
        this.woodCount -= 1;
        this.numOfCards -= 1;
    }
}