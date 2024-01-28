package hr.tvz.catan.catan.File;

import hr.tvz.catan.catan.Models.GameMove;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    //private static final String FILE_NAME = "dat/moves.txt";
    private static final String XML_MOVES_FILE_NAME = "dat/gameMoves.xml";

    public static Boolean FILE_USED = false;

    private static Integer arrayIndex = -1;

    /*public synchronized void saveMoveToFile(Simbol simbol, GameMoveCoordinates koordinate, String playerName)
            throws InterruptedException, IOException {
        while(FILE_USED) {
            wait();
        }

        FILE_USED = true;

        FileWriter fw = new FileWriter(new File(FILE_NAME), true);
        fw.append(playerName + " - " + simbol.name() + ", koordinate: " + koordinate.getX() + ", "
                + koordinate.getY() + "\n");
        fw.close();

        FILE_USED = false;

        notify();
    }*/

    /*public synchronized String getLastMoveFromFile() throws InterruptedException, IOException {
        while(FILE_USED) {
            wait();
        }

        FILE_USED = true;

        List<String> result;
        try (Stream<String> lines = Files.lines(Paths.get(FILE_NAME))) {
            result = lines.collect(Collectors.toList());
        }

        String lastMove = result.get(result.size() - 1);

        FILE_USED = false;

        notify();

        return lastMove;

    }*/

    public static void createXmlFileForGameMoves(List<GameMove> gameMoveList) {
        try {
            Document document = createDocument("gameMoves");
            Element gameMoveElement = null;

            for(GameMove gameMove : gameMoveList) {
                switch (gameMove.getGamePhase()) {
                    case "firstTurnDiceRollPhase" -> {
                        gameMoveElement = document.createElement("firstTurnDiceRollPhaseGameMove");
                        gameMoveElement.appendChild(createElement(document, "activePlayerName", gameMove.getActivePlayerName()));
                        gameMoveElement.appendChild(createElement(document, "headlineText1", gameMove.getHeadlineText1()));
                        gameMoveElement.appendChild(createElement(document, "imageURL1", gameMove.getDiceImageOne()));
                        gameMoveElement.appendChild(createElement(document, "imageURL2", gameMove.getDiceImageTwo()));
                        gameMoveElement.appendChild(createElement(document, "headlineText2", gameMove.getHeadlineText2()));
                    }
                    case "initialBuildingPhase" -> {
                        gameMoveElement = document.createElement("initialBuildingPhaseGameMove");
                        gameMoveElement.appendChild(createElement(document, "activePlayerName", gameMove.getActivePlayerName()));
                        gameMoveElement.appendChild(createElement(document, "headlineText1", gameMove.getHeadlineText1()));
                        gameMoveElement.appendChild(createElement(document, "villageCoordinateX", String.valueOf(gameMove.getVillageCoordinates().get(0))));
                        gameMoveElement.appendChild(createElement(document, "villageCoordinateY", String.valueOf(gameMove.getVillageCoordinates().get(1))));
                        gameMoveElement.appendChild(createElement(document, "headlineText2", gameMove.getHeadlineText2()));
                        gameMoveElement.appendChild(createElement(document, "roadCoordinateX", String.valueOf(gameMove.getRoadCoordinates().get(0))));
                        gameMoveElement.appendChild(createElement(document, "roadCoordinateY", String.valueOf(gameMove.getRoadCoordinates().get(1))));
                        gameMoveElement.appendChild(createElement(document, "roadRotation", String.valueOf(gameMove.getRoadCoordinates().get(2))));
                        gameMoveElement.appendChild(createElement(document, "headlineText3", gameMove.getHeadlineText3()));
                        gameMoveElement.appendChild(createElement(document, "sheepCount", String.valueOf(gameMove.getSheepCount())));
                        gameMoveElement.appendChild(createElement(document, "wheatCount", String.valueOf(gameMove.getWheatCount())));
                        gameMoveElement.appendChild(createElement(document, "clayCount", String.valueOf(gameMove.getClayCount())));
                        gameMoveElement.appendChild(createElement(document, "woodCount", String.valueOf(gameMove.getWoodCount())));
                        gameMoveElement.appendChild(createElement(document, "rockCount", String.valueOf(gameMove.getRockCount())));
                    }
                    case "mainGamePhase" -> {
                        gameMoveElement = document.createElement("mainGamePhaseGameMove");
                        gameMoveElement.appendChild(createElement(document, "activePlayerName", gameMove.getActivePlayerName()));
                        gameMoveElement.appendChild(createElement(document, "headlineText1", gameMove.getHeadlineText1()));
                        gameMoveElement.appendChild(createElement(document, "imageURL1", gameMove.getDiceImageOne()));
                        gameMoveElement.appendChild(createElement(document, "imageURL2", gameMove.getDiceImageTwo()));
                        gameMoveElement.appendChild(createElement(document, "headlineText2", gameMove.getHeadlineText2()));
                        if (!gameMove.getCityCoordinates().isEmpty()) {
                            gameMoveElement.appendChild(createElement(document, "cityCoordinateX", String.valueOf(gameMove.getCityCoordinates().get(0))));
                            gameMoveElement.appendChild(createElement(document, "cityCoordinateY", String.valueOf(gameMove.getCityCoordinates().get(1))));
                            gameMoveElement.appendChild(createElement(document, "headlineText3", gameMove.getHeadlineText3()));
                        } else {
                            gameMoveElement.appendChild(createElement(document, "cityCoordinateX", ""));
                            gameMoveElement.appendChild(createElement(document, "cityCoordinateY", ""));
                            gameMoveElement.appendChild(createElement(document, "headlineText3", ""));
                        }
                        if (!gameMove.getVillageCoordinates().isEmpty()) {
                            gameMoveElement.appendChild(createElement(document, "villageCoordinateX", String.valueOf(gameMove.getVillageCoordinates().get(0))));
                            gameMoveElement.appendChild(createElement(document, "villageCoordinateY", String.valueOf(gameMove.getVillageCoordinates().get(1))));
                            gameMoveElement.appendChild(createElement(document, "headlineText4", gameMove.getHeadlineText4()));
                        } else {
                            gameMoveElement.appendChild(createElement(document, "villageCoordinateX", ""));
                            gameMoveElement.appendChild(createElement(document, "villageCoordinateY", ""));
                            gameMoveElement.appendChild(createElement(document, "headlineText4", ""));
                        }
                        if (!gameMove.getRoadCoordinates().isEmpty()) {
                            gameMoveElement.appendChild(createElement(document, "roadCoordinateX", String.valueOf(gameMove.getRoadCoordinates().get(0))));
                            gameMoveElement.appendChild(createElement(document, "roadCoordinateY", String.valueOf(gameMove.getRoadCoordinates().get(1))));
                            gameMoveElement.appendChild(createElement(document, "roadRotation", String.valueOf(gameMove.getRoadCoordinates().get(2))));
                            gameMoveElement.appendChild(createElement(document, "headlineText5", gameMove.getHeadlineText5()));
                        } else {
                            gameMoveElement.appendChild(createElement(document, "roadCoordinateX", ""));
                            gameMoveElement.appendChild(createElement(document, "roadCoordinateY", ""));
                            gameMoveElement.appendChild(createElement(document, "roadRotation", ""));
                            gameMoveElement.appendChild(createElement(document, "headlineText5", ""));
                        }
                        if (!gameMove.getRobberCoordinates().isEmpty()) {
                            gameMoveElement.appendChild(createElement(document, "robberCoordinateX", String.valueOf(gameMove.getRobberCoordinates().get(0))));
                            gameMoveElement.appendChild(createElement(document, "robberCoordinateY", String.valueOf(gameMove.getRobberCoordinates().get(1))));
                            gameMoveElement.appendChild(createElement(document, "headlineText6", gameMove.getHeadlineText6()));
                        } else {
                            gameMoveElement.appendChild(createElement(document, "robberCoordinateX", ""));
                            gameMoveElement.appendChild(createElement(document, "robberCoordinateY", ""));
                            gameMoveElement.appendChild(createElement(document, "headlineText6", ""));
                        }
                        gameMoveElement.appendChild(createElement(document, "sheepCount", String.valueOf(gameMove.getSheepCount())));
                        gameMoveElement.appendChild(createElement(document, "wheatCount", String.valueOf(gameMove.getWheatCount())));
                        gameMoveElement.appendChild(createElement(document, "clayCount", String.valueOf(gameMove.getClayCount())));
                        gameMoveElement.appendChild(createElement(document, "woodCount", String.valueOf(gameMove.getWoodCount())));
                        gameMoveElement.appendChild(createElement(document, "rockCount", String.valueOf(gameMove.getRockCount())));
                    }
                    default -> System.out.println("Error loading game phase!");
                }

                document.getDocumentElement().appendChild(gameMoveElement);
            }

            saveDocument(document, XML_MOVES_FILE_NAME);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Document createDocument(String element) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        DocumentType documentType = domImplementation.createDocumentType("DOCTYPE", null, "gameMoves.dtd");
        return domImplementation.createDocument(null, element, documentType);
    }

    private static Node createElement(Document document, String tagName, String data) {
        Element element = document.createElement(tagName);
        Text text = document.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    private static void saveDocument(Document document, String fileName) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());
        transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
    }

    public static List<GameMove> createGameMoveListFromXmlFile() throws ParserConfigurationException, IOException, SAXException {

        return parse(XML_MOVES_FILE_NAME);
    }

    private static List<GameMove> parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.err.println("Warning: " + exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.err.println("Error: " + exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.err.println("Fatal error: " + exception);
            }
        });

        Document document = builder.parse(new File(fileName));
        List<GameMove> gameMoveList = new ArrayList<>();

        processNode(gameMoveList, document.getDocumentElement(), "", "");

        return gameMoveList;
    }

    private static void processNode(List<GameMove> gameMoveList, Node node, String indent, String status) {
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                //System.out.println("Read element: " + node.getNodeName());
                if (node.getNodeName().equals("activePlayerName")) {
                    status = "activePlayerName";
                } else if (node.getNodeName().equals("headlineText1")) {
                    status = "headlineText1";
                } else if (node.getNodeName().equals("headlineText2")) {
                    status = "headlineText2";
                } else if (node.getNodeName().equals("headlineText3")) {
                    status = "headlineText3";
                } else if (node.getNodeName().equals("headlineText4")) {
                    status = "headlineText4";
                } else if (node.getNodeName().equals("headlineText5")) {
                    status = "headlineText5";
                } else if (node.getNodeName().equals("headlineText6")) {
                    status = "headlineText6";
                } else if (node.getNodeName().equals("imageURL1")) {
                    status = "imageURL1";
                } else if (node.getNodeName().equals("imageURL2")) {
                    status = "imageURL2";
                } else if (node.getNodeName().equals("cityCoordinateX")) {
                    status = "cityCoordinateX";
                } else if (node.getNodeName().equals("cityCoordinateY")) {
                    status = "cityCoordinateY";
                } else if (node.getNodeName().equals("villageCoordinateX")) {
                    status = "villageCoordinateX";
                } else if (node.getNodeName().equals("villageCoordinateY")) {
                    status = "villageCoordinateY";
                } else if (node.getNodeName().equals("roadCoordinateX")) {
                    status = "roadCoordinateX";
                } else if (node.getNodeName().equals("roadCoordinateY")) {
                    status = "roadCoordinateY";
                } else if (node.getNodeName().equals("roadRotation")) {
                    status = "roadRotation";
                } else if (node.getNodeName().equals("robberCoordinateX")) {
                    status = "robberCoordinateX";
                } else if (node.getNodeName().equals("robberCoordinateY")) {
                    status = "robberCoordinateY";
                } else if (node.getNodeName().equals("sheepCount")) {
                    status = "sheepCount";
                } else if (node.getNodeName().equals("wheatCount")) {
                    status = "wheatCount";
                } else if (node.getNodeName().equals("clayCount")) {
                    status = "clayCount";
                } else if (node.getNodeName().equals("woodCount")) {
                    status = "woodCount";
                } else if (node.getNodeName().equals("rockCount")) {
                    status = "rockCount";
                }
                break;
            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                String value = node.getNodeValue().trim();
                switch (status) {
                    case "activePlayerName" -> {
                        gameMoveList.add(new GameMove(value));
                        arrayIndex++;
                    }
                    case "headlineText1" -> gameMoveList.get(arrayIndex).setHeadlineText1(value);
                    case "headlineText2" -> gameMoveList.get(arrayIndex).setHeadlineText2(value);
                    case "headlineText3" -> gameMoveList.get(arrayIndex).setHeadlineText3(value);
                    case "headlineText4" -> gameMoveList.get(arrayIndex).setHeadlineText4(value);
                    case "headlineText5" -> gameMoveList.get(arrayIndex).setHeadlineText5(value);
                    case "headlineText6" -> gameMoveList.get(arrayIndex).setHeadlineText6(value);
                    case "imageURL1" -> gameMoveList.get(arrayIndex).setDiceImageOne(value);
                    case "imageURL2" -> gameMoveList.get(arrayIndex).setDiceImageTwo(value);
                    case "cityCoordinateX" -> gameMoveList.get(arrayIndex).addCityCoordinate(Double.valueOf(value));
                    case "cityCoordinateY" -> gameMoveList.get(arrayIndex).addCityCoordinate(Double.valueOf(value));
                    case "villageCoordinateX" ->
                            gameMoveList.get(arrayIndex).addVillageCoordinate(Double.valueOf(value));
                    case "villageCoordinateY" ->
                            gameMoveList.get(arrayIndex).addVillageCoordinate(Double.valueOf(value));
                    case "roadCoordinateX" -> gameMoveList.get(arrayIndex).addRoadCoordinate(Double.valueOf(value));
                    case "roadCoordinateY" -> gameMoveList.get(arrayIndex).addRoadCoordinate(Double.valueOf(value));
                    case "roadRotation" -> gameMoveList.get(arrayIndex).addRoadCoordinate(Double.valueOf(value));
                    case "robberCoordinateX" ->
                            gameMoveList.get(arrayIndex).addRobberCoordinates(Double.valueOf(value));
                    case "robberCoordinateY" ->
                            gameMoveList.get(arrayIndex).addRobberCoordinates(Double.valueOf(value));
                    case "sheepCount" -> gameMoveList.get(arrayIndex).setSheepCount(Integer.parseInt(value));
                    case "wheatCount" -> gameMoveList.get(arrayIndex).setWheatCount(Integer.parseInt(value));
                    case "clayCount" -> gameMoveList.get(arrayIndex).setClayCount(Integer.parseInt(value));
                    case "woodCount" -> gameMoveList.get(arrayIndex).setWoodCount(Integer.parseInt(value));
                    case "rockCount" -> gameMoveList.get(arrayIndex).setRockCount(Integer.parseInt(value));
                }
                if (!value.isEmpty()) {
                    //System.out.println("Read text: " + value);
                }
                break;
        }

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            processNode(gameMoveList, childNodes.item(i), indent + "\t", status);
        }
    }
}
