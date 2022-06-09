package model;

import model.cell.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MapFileManager {
    private final BridgeMap bridgeMap;
    private String mapFileName;
    private String outputMapFileName;

    public MapFileManager() {
        this.bridgeMap = new BridgeMap();
        this.mapFileName = "default.map";
        this.outputMapFileName = "default.map";
    }

    public MapFileManager(String mapFileName) {
        this.bridgeMap = new BridgeMap();
        this.mapFileName = mapFileName;
        this.outputMapFileName = "default.map";
    }

    public MapFileManager(String mapFileName, String outputMapFileName) {
        this.bridgeMap = new BridgeMap();
        this.mapFileName = mapFileName;
        this.outputMapFileName = outputMapFileName;
    }

    public void evaluateFile() {
        try (Stream<String> stream = Files.lines(Paths.get(mapFileName))) {
            stream.forEach(line -> bridgeMap.makeCell(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printCurrentMap() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(Paths.get(outputMapFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<CellService> mapArrayList = bridgeMap.getMapArrayList();
        Direction previousDirection = null;
        for (CellService cell : mapArrayList) {
            String line = "";
            if (cell instanceof StartCell) {
                line += "S ";
                for (Direction d : Direction.values())
                    if (cell.canMove(d)) {
                        line += d.getFileChar() + "\n";
                        previousDirection = d;
                        break;
                    }
            } else if (cell instanceof EndCell) {
                line = "E";
            } else {
                if (cell instanceof CardCell) {
                    switch (cell.getCard()) {
                        case PHILIPS_DRIVER -> line += "P ";
                        case HAMMER -> line += "H ";
                        case SAW -> line += "S ";
                    }
                } else if (cell instanceof BridgeCell) {
                    line += cell.isBridge() ? "B " : "b ";
                } else line += "C ";

                Direction tempDirection = null;
                for (Direction d : Direction.values()) {
                    if (cell.canMove(d)) {
                        if (previousDirection == d.opposite()) {
                            line += d.getFileChar() + " ";
                        } else if (cell instanceof BridgeCell && (d == Direction.LEFT || d == Direction.RIGHT)) {
                        } else tempDirection = d;
                    }
                }
                line += tempDirection.getFileChar() + "\n";
                previousDirection = tempDirection;
            }
            try {
                bufferedWriter.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInputMapFileName(String inputMapFileName) {
        this.mapFileName = inputMapFileName;
    }

    public void setOutputMapFileName(String outputMapFileName) {
        this.outputMapFileName = outputMapFileName;
    }

    public void printCurrentMap(String outputMapFileName) {
        this.outputMapFileName = outputMapFileName;
        printCurrentMap();
    }

    public BridgeMap getBridgeMap() {
        return bridgeMap;
    }
}
