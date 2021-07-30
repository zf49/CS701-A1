package kalah.kalahBoard;

import com.qualitascorpus.testsupport.MockIO;
import kalah.house.House;

import com.qualitascorpus.testsupport.IO;
import kalah.player.Player;

import java.util.*;


public class Board {
    private IO io = new MockIO();
    private Map<Integer, Player> playerMap = new HashMap<Integer, Player>();

    public Board() {
        this.io = io;
        playerMap.put(1,new Player(6));
        playerMap.put(2,new Player(6));
    }

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


    public boolean isGameOver(int playerInTurn) {
        Player player = playerMap.get(playerInTurn);

        for (int i = 0; i < player.getPlayerHouse().size(); i++) {
            if (player.getPlayerHouse().get(i+1).getNum_0f_seeds() > 0)
                return false;
        }
        return true;
    }




   public void playGame(){
       int playerTurn = 1;
        printBoard();
        
       while(!isGameOver(playerTurn)){
           String prompt = "Player P" + playerTurn + "\'s turn - Specify house number or 'q' to quit: ";
           String houseIndex = io.readFromKeyboard(prompt);

           if(houseIndex.equals("q")){
               io.println("Game over");
               printBoard();
               break;
           }
           int indexOfHouse = Integer.parseInt(houseIndex);
           int num_0f_seeds = playerMap.get(playerTurn).getPlayerHouse().get(Integer.parseInt(houseIndex)).getNum_0f_seeds();

           playerMap.get(playerTurn).getPlayerHouse().get(Integer.parseInt(houseIndex)).setNum_0f_seeds(0);
           Map<Integer, House> playerHouse = playerMap.get(playerTurn).getPlayerHouse();

           if (num_0f_seeds == 0){
               io.println("House is empty. Move again.");
               printBoard();

           } else if(num_0f_seeds < 7 - indexOfHouse){

               for (int j = 0; j < num_0f_seeds; j++) {
                   //get opposite player seeds
                   if(j == num_0f_seeds-1 && playerHouse.get(indexOfHouse + 1).getNum_0f_seeds() == 0 && playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get((6-indexOfHouse)).getNum_0f_seeds()!=0){
                       int oppositePlayer = changePlayer(playerTurn);
                       int num_0f_seeds1 = playerMap.get(oppositePlayer).getPlayerHouse().get((6 - indexOfHouse)).getNum_0f_seeds();
                       playerMap.get(playerTurn).getPlayerStore().getOppositeSeeds(num_0f_seeds1+1);
                       playerMap.get(oppositePlayer).getPlayerHouse().get(6 - indexOfHouse).setNum_0f_seeds(0);
                   } else {
                       playerHouse.get(indexOfHouse + 1).setNum_0f_seeds();
                       indexOfHouse++;
                   }
               }
               printBoard();
               //change player
               playerTurn = changePlayer(playerTurn);

           }else if(7- indexOfHouse == num_0f_seeds){
               int firstChange = 6-indexOfHouse;

               for (int j = 0; j < firstChange; j++) {
                   playerHouse.get(indexOfHouse + 1).setNum_0f_seeds();
                   indexOfHouse++;
               }
               playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();
               printBoard();
           }else if(7- indexOfHouse < num_0f_seeds && num_0f_seeds <= (7-indexOfHouse+6 )){
               int firstChange = 6-indexOfHouse;
               int flag = 0;
               for (int j = 0; j < firstChange; j++) {
                   playerHouse.get(indexOfHouse + 1).setNum_0f_seeds();
                   indexOfHouse++;
                   flag++;
               }
               playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();

               int times = num_0f_seeds-flag - (7 - indexOfHouse);
               for(int i =0; i < times;i++){
                   //给对面house放子
                   int addOppPlayer = changePlayer(playerTurn);
                   playerMap.get(addOppPlayer).getPlayerHouse().get(i+1).setNum_0f_seeds();
                   indexOfHouse++;
               }
               printBoard();
               playerTurn = changePlayer(playerTurn);
           }else if(7- indexOfHouse < num_0f_seeds && num_0f_seeds > (7-indexOfHouse+6 )){
               int initial_start = indexOfHouse;

               int firstChange = 6 - indexOfHouse;
               for (int j = 0; j < firstChange; j++) {
                   playerHouse.get(indexOfHouse + 1).setNum_0f_seeds();
                   indexOfHouse++;
               }

               int zijizouwan =  num_0f_seeds-firstChange;
               int i = zijizouwan/13;
               for (int q = 0; q < i;q++){
                  playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();
                   for (Integer integer : playerMap.get(changePlayer(playerTurn)).getPlayerHouse().keySet()) {
                       playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(integer).setNum_0f_seeds();
                       playerMap.get(playerTurn).getPlayerHouse().get(integer).setNum_0f_seeds();
                   }
               }
               int q =  zijizouwan-i*13-1;
               if(q == -1){
                   if( playerHouse.get(initial_start).getNum_0f_seeds()==1 && playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get((7-initial_start)).getNum_0f_seeds()!=0){
                       playerMap.get(playerTurn).getPlayerHouse().get(initial_start).setNum_0f_seeds(0);
                       int oppositePlayer = changePlayer(playerTurn);
                       int num_0f_seeds1 = playerMap.get(oppositePlayer).getPlayerHouse().get((7-initial_start)).getNum_0f_seeds();
                       playerMap.get(playerTurn).getPlayerStore().getOppositeSeeds(num_0f_seeds1+1);
                       playerMap.get(oppositePlayer).getPlayerHouse().get(7-initial_start).setNum_0f_seeds(0);

                   }
               }else if(q<=6){  //
                        playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();

                   for(int k = 0; k < q; k ++){
                           playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(k+1).setNum_0f_seeds();
                       }
                   } else {
                                  playerMap.get(playerTurn).getPlayerStore().addStoreSeeds();

                   for(int k = 0; k < 6; k ++){
                           playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get(k+1).setNum_0f_seeds();
                       }
                       for(int k = 0; k < q-6; k ++){

                           playerMap.get(playerTurn).getPlayerHouse().get(k+1).setNum_0f_seeds();
                       }

                       if( playerHouse.get(q-6).getNum_0f_seeds()==1 && playerMap.get(changePlayer(playerTurn)).getPlayerHouse().get((6-(q-6-1))).getNum_0f_seeds()!=0){
                            playerMap.get(playerTurn).getPlayerHouse().get(q-6).setNum_0f_seeds(0);
                           int oppositePlayer = changePlayer(playerTurn);
                           int num_0f_seeds1 = playerMap.get(oppositePlayer).getPlayerHouse().get((6 - (q-6-1))).getNum_0f_seeds();
                           playerMap.get(playerTurn).getPlayerStore().getOppositeSeeds(num_0f_seeds1+1);
                           playerMap.get(oppositePlayer).getPlayerHouse().get(6 - (q-6-1)).setNum_0f_seeds(0);

                       }

                   }


               printBoard();
                   playerTurn = changePlayer(playerTurn);


           }
       }

       if(isGameOver(playerTurn)){
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
   }

}
