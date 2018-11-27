package Game;

import java.util.ArrayList;

public class Unit extends Thing {
    protected int numberOfMovesRemaining;
    protected int level;
    protected transient int attackStrength;
    protected String playerBelongsTo;
    protected UnitTypeEnum type;

    public void move(int xCoordinate, int yCoordinate, String username) {
        GameBoard.gameBoardHolder.get(username).gameTiles[this.xCoordinate][this.yCoordinate].setThing(null);
        GameBoard.gameBoardHolder.get(username).gameTiles[this.xCoordinate][this.yCoordinate].setHasThing(false);
        GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].setThing(this);
        this.tile = GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate];
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        numberOfMovesRemaining--;
    }

    public void attack(int xCoordinate, int yCoordinate, String username) {
        GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].getThing().setHealth(GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].getThing().getHealth() - attackStrength);

        if (GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].getThing().getHealth() <= 0) {
            int id = GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].getThing().getId();

            //delete a base
            for (int i = 0; i < GameController.getInstance().bases.size(); i++) {
                if (GameController.getInstance().bases.get(i).getId() == id) {
                    GameController.getInstance().forfeit(GameController.getInstance().bases.get(i).getPlayerBelongsTo());
                }
            }

            //delete a unit
            for (int i = 0; i < GameController.getInstance().units.size(); i++) {
                if (GameController.getInstance().units.get(i).getId() == id) {
                    GameController.getInstance().units.remove(i);

                    GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].setThing(null);
                    GameBoard.gameBoardHolder.get(username).gameTiles[xCoordinate][yCoordinate].setHasThing(false);
                }
            }
        }
    }

    public Position[] getAttacks(String username) {
        int boardWidth = GameBoard.getBoardWidth();
        int boardHeight = GameBoard.getBoardHeight();

        ArrayList<Position> positions = new ArrayList();

        //UPPER LEFT
        if (this.xCoordinate == 0 && this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //UPPER RIGHT
        else if (this.xCoordinate == boardWidth - 1 && this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //LOWER LEFT
        else if (this.xCoordinate == 0 && this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
        }
        //LOWER RIGHT
        else if (this.xCoordinate == boardWidth - 1 && this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }
        //TOP SIDE
        else if (this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //BOTTOM SIDE
        else if (this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
        }
        //LEFT SIDE
        else if (this.xCoordinate == 0) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
        }
        //RIGHT SIDE
        else if (this.xCoordinate == boardWidth - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }
        //OTHER
        else {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }

        //remove all attackable position without units on them
        for (int i = 0; i < positions.size(); i++) {
            int tileXCoordinate = positions.get(i).getxCoordinate();
            int tileYCoordinate = positions.get(i).getyCoordinate();
            if (GameBoard.gameBoardHolder.get(username).gameTiles[tileXCoordinate][tileYCoordinate].hasThing == false) {
                positions.remove(i);
                i--;
            }
        }

        return positions.toArray(new Position[0]);
    }

    public Position[] getMoves(boolean isForMove, String username) {

        int boardWidth = GameBoard.getBoardWidth();
        int boardHeight = GameBoard.getBoardHeight();

        ArrayList<Position> positions = new ArrayList();

        //UPPER LEFT
        if (this.xCoordinate == 0 && this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //UPPER RIGHT
        else if (this.xCoordinate == boardWidth - 1 && this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //LOWER LEFT
        else if (this.xCoordinate == 0 && this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
        }
        //LOWER RIGHT
        else if (this.xCoordinate == boardWidth - 1 && this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }
        //TOP SIDE
        else if (this.yCoordinate == 0) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
        }
        //BOTTOM SIDE
        else if (this.yCoordinate == boardHeight - 1) {
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
        }
        //LEFT SIDE
        else if (this.xCoordinate == 0) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
        }
        //RIGHT SIDE
        else if (this.xCoordinate == boardWidth - 1) {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }
        //OTHER
        else {
            positions.add(new Position(this.xCoordinate, this.yCoordinate - 1));
            positions.add(new Position(this.xCoordinate, this.yCoordinate + 1));
            positions.add(new Position(this.xCoordinate + 1, this.yCoordinate));
            positions.add(new Position(this.xCoordinate - 1, this.yCoordinate));
        }

        if (isForMove == true) {
            for (int i = 0; i < positions.size(); i++) {
                int tileXCoordinate = positions.get(i).getxCoordinate();
                int tileYCoordinate = positions.get(i).getyCoordinate();
                if (GameBoard.gameBoardHolder.get(username).gameTiles[tileXCoordinate][tileYCoordinate].hasThing == true) {
                    positions.remove(i);
                    i--;
                }
            }
        }

        return positions.toArray(new Position[0]);
    }

    public String getPlayerBelongsTo() {
        return this.playerBelongsTo;
    }

    public void increaseLevel() {
        this.level++;
    }

    public void resetMoves() {
    }
}