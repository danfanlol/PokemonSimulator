import java.util.ArrayList;
import java.util.Scanner;

class game{
    public void playGame(){
        library library = new library();
        ArrayList<String> zType = new ArrayList();
        zType.add("Dragon");
        zType.add("Ghost");
        Move minimize = library.movelibrary.get("Minimize");
        Move HellsFiery = library.movelibrary.get("Hell's Fiery");
        Move dragonBreath = library.movelibrary.get("Dragon Breath");
        Move extrasensory = library.movelibrary.get("Extrasensory");
        ArrayList<Move> gMoves = new ArrayList();
        gMoves.add(dragonBreath);
        gMoves.add(extrasensory);
        ArrayList<Move> zMoves = new ArrayList();
        zMoves.add(HellsFiery);
        ArrayList<Move> bMoves = new ArrayList();
        bMoves.add(library.movelibrary.get("Recover"));
        bMoves.add(minimize);
        bMoves.add(library.movelibrary.get("Toxic"));
        bMoves.add(library.movelibrary.get("Confuse Ray"));
        Pokemon Zygarde = new Pokemon("Zygarde", new int[]{500,500,500,500,500,600},new ArrayList<String>(){},zType,zMoves,100,new ArrayList<String>(),new ArrayList<String>(),"None");
        Pokemon Garchomp = new Pokemon("Garchomp", new int[]{500,500,500,500,500,500},new ArrayList<String>(){},zType,gMoves,100,new ArrayList<String>(),new ArrayList<String>(),"None");
        Pokemon Zygarde3 = new Pokemon("Zygarde", new int[]{500,500,500,500,500,500},new ArrayList<String>(){},zType,zMoves,100,new ArrayList<String>(),new ArrayList<String>(),"None");
        Pokemon blissey = new Pokemon("Blissey", new int[]{500,500,800,500,800,100},new ArrayList<String>(){},zType,bMoves,100,new ArrayList<String>(),new ArrayList<String>(),"None");

        ArrayList<Pokemon> reserves1 = new ArrayList();
        reserves1.add(Garchomp);
        Team team1 = new Team(reserves1,Zygarde);
        ArrayList<Pokemon> reserves2 =  new ArrayList();
        reserves2.add(Zygarde3);
        Team team2 = new Team(reserves2,blissey);


    }

}
public class Battlefield {
    Team team1,team2;
    gameState game;
    public Battlefield(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
        game = new gameState(team1,team2);
    }
    public void play(){ // precondition: team1 and team2 have at least one pokemon in each
        while (!team1.isEmpty() && !team2.isEmpty()){ // at every iteration, new round should be made
            Pokemon current1 = team1.current;
            Pokemon current2 = team2.current;
            Move move1 = null;
            Move move2 = null;
            boolean player1switch = false;
            boolean player2switch = false;
            System.out.println("What move would player 1 like to play? " + current1.showMoves()); // must return the index of the move
            int move1Index =  new Scanner(System.in).nextInt();
            if (move1Index == -1){
                System.out.println("What pokemon would player 1 like to switch to? " + team1.showPokemon()); // must return the index of the move
                int pokemonIndex = new Scanner(System.in).nextInt();
                team1.switchPokemon(pokemonIndex);
                current1 = team1.current;
                player1switch = true;
            }
            else{
                move1 = current1.moves.get(move1Index);
            }
            System.out.println("What move would player 2 like to play? " + current2.showMoves()); // must return the index of the move
            int move2Index =  new Scanner(System.in).nextInt();
            if (move2Index == -1){
                System.out.println("What pokemon would player 2 like to switch to? " + team2.showPokemon()); // must return the index of the move
                int pokemonIndex = new Scanner(System.in).nextInt();
                team2.switchPokemon(pokemonIndex);
                current2 = team2.current;
                player2switch = true;
            }
            else{
                move2 = current2.moves.get(move2Index);
            }
            if (!player1switch && !player2switch) game.makeTurn(current1,move1,current2,move2);
            if (player1switch && !player2switch) {
                game.makeSwitch(current2,move2,current1);
            }
            if (!player1switch && player2switch) game.makeSwitch(current1,move1,current2);
            if (player1switch && player2switch) game.makeSwitch2(current1,current2);
            if (current1.stats[0] == 0){
                System.out.println(current1.name + " is dead!");
                if (team1.reserves.isEmpty()) break;
                System.out.println("What is the next pokemon you want, Player 1? " + team1.showPokemon()); // must return the index of the move
                int newPokemonIndex =  new Scanner(System.in).nextInt();
                team1.switchNewPokemon(newPokemonIndex);
            }
            if (current2.stats[0] == 0){
                System.out.println(current2.name + " is dead!");
                if (team2.reserves.isEmpty()) break;
                System.out.println("What is the next pokemon you want, Player 2? " + team2.showPokemon()); // must return the index of the move
                int newPokemonIndex =  new Scanner(System.in).nextInt();
                team2.switchNewPokemon(newPokemonIndex);
            }

            game.showStats();
        }
    }
}



class Team{
    public ArrayList<Pokemon> reserves = new ArrayList();
    public Pokemon current;
    public Team(ArrayList<Pokemon> reserves, Pokemon current){
        this.reserves = reserves;
        this.current=  current;
    }
    public void switchPokemon(int i){ // precondition: i is in between 0 and reserves.size()-1 inclusive, both pokemon are alive
        reserves.add(current);
        Pokemon newPokemon = reserves.get(i);
        reserves.remove(i);
        current = newPokemon;

    }
    public void switchNewPokemon(int i){  // precondition: i is in between 0 and reserves.size()-1 inclusive, one pokemon is dead
        Pokemon newPokemon = reserves.get(i);
        reserves.remove(i);
        current = newPokemon;
    }
    public boolean isEmpty(){
        return current.stats[0] == 0 && reserves.size() == 0;
    }
    public StringBuilder showPokemon(){
        StringBuilder s =new StringBuilder();
        for (int i =0 ; i <reserves.size();i++){
            s.append(reserves.get(i).name).append(" ");
        }
        return s;
    }
}