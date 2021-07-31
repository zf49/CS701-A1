package kalah.player;

import kalah.house.House;
import kalah.store.Store;

import java.util.*;

public class Player {

    private Map<Integer,House> playerHouse = new LinkedHashMap();
    private Store playerStore = new Store();
    private int score = 0;

    public Player() {
    }

    public Player(int i) {
        for (int j = 0; j < i; j++) {
           playerHouse.put(j+1,new House());
        }
    }

    public Map<Integer, House> getPlayerHouse() {
        return playerHouse;
    }

    public void setPlayerHouse(Map<Integer, House> playerHouse) {
        this.playerHouse = playerHouse;
    }

    public Store getPlayerStore() {
        return playerStore;
    }

    public void setPlayerStore(Store playerStore) {
        this.playerStore = playerStore;
    }

    public int getScore() {
        
        for (int i = 0; i < playerHouse.size() ; i++) {
            score+= playerHouse.get(i+1).getNum_0f_seeds();
        }
         score += playerStore.getStoreSeeds();
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
