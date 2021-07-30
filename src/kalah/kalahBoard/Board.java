package kalah.kalahBoard;

import com.qualitascorpus.testsupport.MockIO;
import kalah.house.House;

import com.qualitascorpus.testsupport.IO;
import kalah.player.Player;

import java.util.*;


public class Board {
    private IO io = new MockIO();
    private Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    
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
    public String formatNum(int i){
            if(i<=9){
                 return " "+i;
            }else {
              return ""+i;
            }
    }

    public void printBoard(){

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6["+formatNum(playerMap.get(2).getPlayerHouse().get(6).getNum_0f_seeds())+"] | 5["+formatNum(playerMap.get(2).getPlayerHouse().get(5).getNum_0f_seeds())+"] | 4["+formatNum(playerMap.get(2).getPlayerHouse().get(4).getNum_0f_seeds())+"] | 3["+formatNum(playerMap.get(2).getPlayerHouse().get(3).getNum_0f_seeds())+"] | 2["+formatNum(playerMap.get(2).getPlayerHouse().get(2).getNum_0f_seeds())+"] | 1["+formatNum(playerMap.get(2).getPlayerHouse().get(1).getNum_0f_seeds())+"] | "+formatNum(playerMap.get(1).getPlayerStore().getStoreSeeds())+" |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| "+formatNum(playerMap.get(2).getPlayerStore().getStoreSeeds())+" | 1["+formatNum(playerMap.get(1).getPlayerHouse().get(1).getNum_0f_seeds())+"] | 2["+formatNum(playerMap.get(1).getPlayerHouse().get(2).getNum_0f_seeds())+"] | 3["+formatNum(playerMap.get(1).getPlayerHouse().get(3).getNum_0f_seeds())+"] | 4["+formatNum(playerMap.get(1).getPlayerHouse().get(4).getNum_0f_seeds())+"] | 5["+formatNum(playerMap.get(1).getPlayerHouse().get(5).getNum_0f_seeds())+"] | 6["+formatNum(playerMap.get(1).getPlayerHouse().get(6).getNum_0f_seeds())+"] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }


    //change player key value
   public int changePlayer(int indexOfPlayer){
       if (indexOfPlayer - 1 == 0 ){
           return 2;
       }else{
           return 1;
       }
   }

       // judge game is over
       // e.g. when 1 play finish move check opposite player's house's seeds. if all house contain 0 seeds
       // game over
    public boolean isGameOver(int playerInTurn) {
        Player player = playerMap.get(playerInTurn);
        for (int i = 0; i < player.getPlayerHouse().size(); i++) {
            if (player.getPlayerHouse().get(i+1).getNum_0f_seeds() > 0)
                return false;
        }
        return true;
    }

    public void getOppositeSeed( int playerTurn, int indexOfHouse,int houseNum){
        int oppositePlayer = changePlayer(playerTurn);
        int num_0f_seed_oppositePlayer = playerMap.get(oppositePlayer).getPlayerHouse().get(houseNum).getNum_0f_seeds();
        playerMap.get(playerTurn).getPlayerStore().getOppositeSeeds(num_0f_seed_oppositePlayer+1);
        playerMap.get(oppositePlayer).getPlayerHouse().get(houseNum).setNum_0f_seeds(0);
    }



     public int lastSeedInStoreAddStore(int indexOfHouse,int playerTurn, Map<Integer,House> playerHouse){

        int firstMove = 6-indexOfHouse;
         for (int j = 0; j < firstMove; j++) {
             playerHouse.get(indexOfHouse + 1).addNum_0f_seeds();
             indexOfHouse++;
         }
         playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();
         return firstMove;
     }


    public int lastSeedInStore(int indexOfHouse, Map<Integer,House> playerHouse){

        int firstMove = 6-indexOfHouse;
        for (int j = 0; j < firstMove; j++) {
            playerHouse.get(indexOfHouse + 1).addNum_0f_seeds();
            indexOfHouse++;
        }
        return firstMove;
    }



    public void addSeedsToAllHouse(int playerTurn,Map<Integer,House> playerHouse){
        for (Integer integer : playerMap.get(changePlayer(playerTurn)).getPlayerHouse().keySet()) {
            playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(integer).addNum_0f_seeds();
            playerHouse.get(integer).addNum_0f_seeds();
        }
    }


    public boolean isGetOppositeSeed(Map<Integer,House> playerHouse,int playerTurn, int selfIndex, int numOfSeed,int OppositeIndex){

        if( playerHouse.get(selfIndex).getNum_0f_seeds()==numOfSeed
                && playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(OppositeIndex).getNum_0f_seeds()!=0){
            return true;
        }else {
            return false;
        }
    }

      public void addStore(int playerTurn){
          playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();

      }


      public void addSeeds(int player, int num){

          playerMap.get(player).getPlayerHouse().get(num).addNum_0f_seeds();

      }


      public void gameOverMessage(){
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




   public void playGame(){
        //who first?
        int playerTurn = 1;
        printBoard();
        
       while(!isGameOver(playerTurn)){

           String prompt = "Player P" + playerTurn + "\'s turn - Specify house number or 'q' to quit: ";

           int q1 = io.readInteger(prompt, 1, 6, 0, "q");
           if(q1 == 0){
               io.println("Game over");
               printBoard();
               break;
           }

           int indexOfHouse = q1;
           int initialIndexOfHouse = q1;

           // get the number of seeds
           int num_0f_seeds = playerMap.get(playerTurn).getPlayerHouse().get(q1).getNum_0f_seeds();

           // take all seeds of this house
           playerMap.get(playerTurn).getPlayerHouse().get(indexOfHouse).setNum_0f_seeds(0);

           // all house of current player
           Map<Integer, House> playerHouse = playerMap.get(playerTurn).getPlayerHouse();

           // the seed in house is 0, move again  
           if (num_0f_seeds == 0){
               io.println("House is empty. Move again.");
               printBoard();

           } else if(num_0f_seeds < 7 - indexOfHouse){

               for (int j = 0; j < num_0f_seeds; j++) {
                   //get opposite player seeds
                   if(j == num_0f_seeds-1 && isGetOppositeSeed(playerHouse,playerTurn,(indexOfHouse + 1),0,(6-indexOfHouse))) {
                       //change player
                       getOppositeSeed(playerTurn,indexOfHouse,(6 - indexOfHouse));

                   } else {
                       playerHouse.get(indexOfHouse + 1).addNum_0f_seeds();
                       indexOfHouse++;
                   }
               }
               printBoard();
               //change player
               playerTurn = changePlayer(playerTurn);

           }else if(7- indexOfHouse == num_0f_seeds){

               lastSeedInStoreAddStore(indexOfHouse,playerTurn,playerHouse);
               printBoard();

           } else if(7- indexOfHouse < num_0f_seeds && num_0f_seeds <= (7-indexOfHouse+6)){

               int firstChange = lastSeedInStoreAddStore(indexOfHouse, playerTurn, playerHouse);
               int loopTimes = num_0f_seeds - firstChange - (7 - indexOfHouse-firstChange);

               for(int i =0; i < loopTimes;i++){
                   //put seeds into opposite player house
                   addSeeds(changePlayer(playerTurn),i+1);
                   indexOfHouse++;
               }

               printBoard();
               playerTurn = changePlayer(playerTurn);


           } else if(7- indexOfHouse < num_0f_seeds && num_0f_seeds > (7-indexOfHouse+6 )){

               int firstMove = lastSeedInStore(indexOfHouse, playerHouse);

               int numNeedToMove =  num_0f_seeds-firstMove;

               //check how many round need to move
               //13 means houses and store are 13 totally
               int i = numNeedToMove/13;

               for (int q = 0; q < i;q++){
                   addStore(playerTurn);
                   addSeedsToAllHouse(playerTurn,playerHouse);
               }

               int remainder = numNeedToMove-i*13-1;

               if(remainder == -1){
                   if( isGetOppositeSeed(playerHouse,playerTurn,initialIndexOfHouse,1,(7-initialIndexOfHouse))){

                       playerMap.get(playerTurn).getPlayerHouse().get(initialIndexOfHouse).setNum_0f_seeds(0);
                       getOppositeSeed(playerTurn,indexOfHouse,(7-initialIndexOfHouse));
                   }

               }else if(remainder<=6){

                   addStore(playerTurn);

                   for(int k = 0; k < remainder; k ++){
                       addSeeds(changePlayer(playerTurn),k+1);
                   }

               } else {

                    addStore(playerTurn);

                   for(int k = 0; k < 6; k ++){
                       addSeeds(changePlayer(playerTurn),k+1);
                   }

                   for(int k = 0; k < remainder-6; k ++){
                       addSeeds(playerTurn,k+1);
                   }

                   if(isGetOppositeSeed(playerHouse,playerTurn,(remainder-6),1,(6-(remainder-6-1)))){

                       playerMap.get(playerTurn).getPlayerHouse().get(remainder-6).setNum_0f_seeds(0);

                       getOppositeSeed(playerTurn,indexOfHouse,(6 - (remainder-6-1)));

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
