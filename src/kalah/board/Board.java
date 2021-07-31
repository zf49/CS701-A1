package kalah.board;

import kalah.house.House;

import com.qualitascorpus.testsupport.IO;
import kalah.player.Player;

import java.util.*;


public class Board {
    private IO io;
    private Map<Integer, Player> playerMap = new HashMap();
    
    public Board(IO io) {
        this.io = io;
        playerMap.put(1,new Player(6));
        playerMap.put(2,new Player(6));
    }

    public Map<Integer, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<Integer, Player> playerMap) {
        this.playerMap = playerMap;
    }

    // format all nums of house's seeds
    private String formatNum(int i){
            if(i<=9){
                 return " "+i;
            }else {
              return ""+i;
            }
    }

    private void printBoard(){

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6["+formatNum(playerMap.get(2).getPlayerHouse().get(6).getNum0fSeeds())+"] | 5["+formatNum(playerMap.get(2).getPlayerHouse().get(5).getNum0fSeeds())+"] | 4["+formatNum(playerMap.get(2).getPlayerHouse().get(4).getNum0fSeeds())+"] | 3["+formatNum(playerMap.get(2).getPlayerHouse().get(3).getNum0fSeeds())+"] | 2["+formatNum(playerMap.get(2).getPlayerHouse().get(2).getNum0fSeeds())+"] | 1["+formatNum(playerMap.get(2).getPlayerHouse().get(1).getNum0fSeeds())+"] | "+formatNum(playerMap.get(1).getPlayerStore().getStoreSeeds())+" |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| "+formatNum(playerMap.get(2).getPlayerStore().getStoreSeeds())+" | 1["+formatNum(playerMap.get(1).getPlayerHouse().get(1).getNum0fSeeds())+"] | 2["+formatNum(playerMap.get(1).getPlayerHouse().get(2).getNum0fSeeds())+"] | 3["+formatNum(playerMap.get(1).getPlayerHouse().get(3).getNum0fSeeds())+"] | 4["+formatNum(playerMap.get(1).getPlayerHouse().get(4).getNum0fSeeds())+"] | 5["+formatNum(playerMap.get(1).getPlayerHouse().get(5).getNum0fSeeds())+"] | 6["+formatNum(playerMap.get(1).getPlayerHouse().get(6).getNum0fSeeds())+"] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    //change player key value
   private int changePlayer(int indexOfPlayer){
       if (indexOfPlayer - 1 == 0 ){
           return 2;
       }else{
           return 1;
       }
   }

       // judge game is over
       // e.g. when 1 play finish move check opposite player's house's seeds. if all house contain 0 seeds
       // game over
    private boolean isGameOver(int playerInTurn) {
        Player player = playerMap.get(playerInTurn);
        for (int i = 0; i < player.getPlayerHouse().size(); i++) {
            if (player.getPlayerHouse().get(i+1).getNum0fSeeds() > 0)
                return false;
        }
        return true;
    }

    private void getOppositeSeed( int playerTurn, int houseNum){
        int oppositePlayer = changePlayer(playerTurn);
        int num_0f_seed_oppositePlayer = playerMap.get(oppositePlayer).getPlayerHouse().get(houseNum).getNum0fSeeds();
        playerMap.get(playerTurn).getPlayerStore().getOppositeSeeds(num_0f_seed_oppositePlayer+1);
        playerMap.get(oppositePlayer).getPlayerHouse().get(houseNum).setNum0fSeeds(0);
    }

     private int lastSeedInStore(int indexOfHouse,int playerTurn, Map<Integer,House> playerHouse){

        int firstMove = 6-indexOfHouse;
         for (int j = 0; j < firstMove; j++) {
             playerHouse.get(indexOfHouse + 1).addNum0fSeeds();
             indexOfHouse++;
         }
         playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();
         return firstMove;
     }


    private int lastSeedInStore(int indexOfHouse, Map<Integer,House> playerHouse){

        int firstMove = 6-indexOfHouse;
        for (int j = 0; j < firstMove; j++) {
            playerHouse.get(indexOfHouse + 1).addNum0fSeeds();
            indexOfHouse++;
        }
        return firstMove;
    }

    private void addSeedsToAllHouse(int playerTurn,Map<Integer,House> playerHouse){
        for (Integer integer : playerMap.get(changePlayer(playerTurn)).getPlayerHouse().keySet()) {
            playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(integer).addNum0fSeeds();
            playerHouse.get(integer).addNum0fSeeds();
        }
    }

    private boolean isGetOppositeSeed(Map<Integer,House> playerHouse,int playerTurn, int selfIndex, int numOfSeed,int OppositeIndex){
        return  playerHouse.get(selfIndex).getNum0fSeeds() == numOfSeed
                && playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(OppositeIndex).getNum0fSeeds() != 0;
    }

    private void addStore(int playerTurn){
          playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();
    }

    private void addSeeds(int player, int num){
          playerMap.get(player).getPlayerHouse().get(num).addNum0fSeeds();
    }

    private void gameOverMessage(){
          int p1 = playerMap.get(1).getScore();
          int p2 =playerMap.get(2).getScore();
          io.println("Game over");
          printBoard();
          io.println("\tplayer 1:" + p1);
          io.println("\tplayer 2:" + p2);
          if (p1 > p2)
              io.println("Player 1 wins!");
          if (p2 > p1)
              io.println("Player 2 wins!");
          if (p1 == p2)
              io.println("A tie!");
    }

    private void putSeedsOnSelfHouseOnly(int num0fSeeds,Map<Integer,House> playerHouse,int playerTurn,int indexOfHouse){
          for (int j = 0; j < num0fSeeds; j++) {
              //get opposite player seeds
              if(j == num0fSeeds-1 && isGetOppositeSeed(playerHouse,playerTurn,(indexOfHouse + 1),0,(6-indexOfHouse))) {
                  //change player
                  getOppositeSeed(playerTurn,(6 - indexOfHouse));
              } else {
                  playerHouse.get(indexOfHouse + 1).addNum0fSeeds();
                  indexOfHouse++;
              }
          }
    }

   public void playGame(){
        //who first?
        int playerTurn = 1;
        printBoard();
        
       while(!isGameOver(playerTurn)){

           String prompt = "Player P" + playerTurn + "\'s turn - Specify house number or 'q' to quit: ";

           int indexOfHouse = io.readInteger(prompt, 1, 6, 0, "q");

           if(indexOfHouse == 0){
               io.println("Game over");
               printBoard();
               break;
           }

           // get the number of seeds
           int num0fSeeds = playerMap.get(playerTurn).getPlayerHouse().get(indexOfHouse).getNum0fSeeds();

           // take all seeds of this house, set the seeds of this house 0
           playerMap.get(playerTurn).getPlayerHouse().get(indexOfHouse).setNum0fSeeds(0);

           // all house of current player
           Map<Integer, House> playerHouse = playerMap.get(playerTurn).getPlayerHouse();

           // the seed in house is 0, move again  
           if (num0fSeeds == 0){
               io.println("House is empty. Move again.");
               printBoard();

           } else if(7 - indexOfHouse > num0fSeeds){     //      Putting seeds on self side only

               putSeedsOnSelfHouseOnly(num0fSeeds,playerHouse,playerTurn,indexOfHouse);

               printBoard();
               //change player
               playerTurn = changePlayer(playerTurn);
                   //more round
           }else if(7- indexOfHouse == num0fSeeds){        //      The last seed falls right into the store

               lastSeedInStore(indexOfHouse,playerTurn,playerHouse);
               printBoard();

           } else if(7- indexOfHouse < num0fSeeds
                   && num0fSeeds <= (7-indexOfHouse+6)){    //  The last seed falls in the opposing house

               int firstChange = lastSeedInStore(indexOfHouse, playerTurn, playerHouse);
               int loopTimes = num0fSeeds - firstChange - (7 - indexOfHouse-firstChange);

               for(int i =0; i < loopTimes;i++){
                   //put seeds into opposite player house
                   addSeeds(changePlayer(playerTurn),i+1);
                   indexOfHouse++;
               }

               printBoard();
               playerTurn = changePlayer(playerTurn);

           } else if(7- indexOfHouse < num0fSeeds
                   && num0fSeeds > (7-indexOfHouse+6 )){  //  Performing more than 1 lap

               int firstMove = lastSeedInStore(indexOfHouse, playerHouse);    //  Fill up  starting house and store

               int numOfRemainingSeeds =  num0fSeeds-firstMove;        //  Number of seeds remaining

               //check how many circle need to move
               //13 means houses and store are 13 totally
               int i = numOfRemainingSeeds/13;

               for (int q = 0; q < i;q++){               // add all seeds into every houses and store
                   addStore(playerTurn);
                   addSeedsToAllHouse(playerTurn,playerHouse);
               }

               int remainder = numOfRemainingSeeds-i*13-1;     // when add all house and store how many seeds are remain

               if(remainder == -1){          // if no seeds remain, judge whether get opposite player's seeds
                        if( isGetOppositeSeed(playerHouse,playerTurn, indexOfHouse,1,(7- indexOfHouse))){
                            playerMap.get(playerTurn).getPlayerHouse().get(indexOfHouse).setNum0fSeeds(0);
                            getOppositeSeed(playerTurn,(7- indexOfHouse));
                        }

               }else if(remainder<=6){     // put seeds into store and opposite player's house

                        addStore(playerTurn);

                        for(int k = 0; k < remainder; k ++){
                            addSeeds(changePlayer(playerTurn),k+1);
                        }

               } else {         // move remained seeds

                        addStore(playerTurn);

                        for(int k = 0; k < 6; k ++){
                            addSeeds(changePlayer(playerTurn),k+1);
                        }

                        for(int k = 0; k < remainder-6; k ++){
                            addSeeds(playerTurn,k+1);
                        }

                        if(isGetOppositeSeed(playerHouse,playerTurn,(remainder-6),1,(6-(remainder-6-1)))){   // judge whether get opposite seeds
                            playerMap.get(playerTurn).getPlayerHouse().get(remainder-6).setNum0fSeeds(0);
                            getOppositeSeed(playerTurn,(6 - (remainder-6-1)));
                        }

                   }
               printBoard();
               playerTurn = changePlayer(playerTurn);
           }
       }

       if(isGameOver(playerTurn)){
           gameOverMessage();
       }
   }
}
