import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class library{
    public HashMap<String, Move> movelibrary = new HashMap();
    public Team team1,team2;
    public library(){
        movelibrary.put("Hell's Fiery",new Move("Hell's Fiery",100,"flinch",10,100,"Dragon",true,0,"Defending"));
        movelibrary.put("Confuse Ray",new Move("Confuse Ray",0,"confuse",100,100,"Ghost",false,0,"Defending"));
        movelibrary.put("Psystrike",new Move("Psystrike",100, "None", 100, 100, "Psychic", false, 0,"Defending"));
        movelibrary.put("Recover",new Move("Recover",0,"recover",100,100,"status",false,0,"Attacking"));
        movelibrary.put("Amnesia",new Move("Amnesia",0,"Special Defense 2",100,100,"status",false,0,"Attacking"));
        movelibrary.put("Dragon Breath",new Move("Dragon Breath",60,"paralyze",30,100,"Dragon",false,0,"Defending"));
//        movelibrary.put("Slash",new Move((140,"None",100,100,"Normal",true,0,"Defending"));
        movelibrary.put("Extrasensory",new Move("Extrasensory",80,"flinch",10,100,"Psychic",false,0,"Defending"));
//        movelibrary.put("Fusion Flare",new Move(100,"None",100,100,"Fire",false,0,"Defending"));
//        movelibrary.put("Crunch",new Move("Crunch",80,"Special Defense -1",20,100,"Dark",true,0,"Defending"));
//        movelibrary.put("Dragon Pulse",new Move("Dragon Pulse",85,"None",100,100,"Dragon",false,0,"Defending"));
//        movelibrary.put("Coil",new Move(0,"Coil",100,100,"status",false,0,"Attacking"));
        movelibrary.put("Minimize",new Move("Minimize",0,"Minimize",100,100,"status",false,0,"Attacking"));
//        movelibrary.put("Shadow Sneak",new Move(30,"None",100,100,"Ghost",false,1,"Defending"));
        movelibrary.put("Toxic",new Move("Toxic",0,"badly poison",100,90,"Poison",false,0,"Defending"));
        ArrayList<Move> reshirammoves = new ArrayList();
        reshirammoves.add(movelibrary.get("Dragon Breath"));
        reshirammoves.add(movelibrary.get("Slash")); //status moves deal no damage and only effect the attacking pokemon
        reshirammoves.add(movelibrary.get("Extrasensory"));
        reshirammoves.add(movelibrary.get("Crunch"));
        ArrayList<Move> blisseymoves = new ArrayList();
        blisseymoves.add(movelibrary.get("Minimize"));
        blisseymoves.add(movelibrary.get("Toxic"));
        ArrayList<String> zType = new ArrayList();
        zType.add("Dragon");
        zType.add("Ghost");
        Move minimize = movelibrary.get("Minimize");
        Move HellsFiery = movelibrary.get("Hell's Fiery");
        Move dragonBreath = movelibrary.get("Dragon Breath");
        Move extrasensory = movelibrary.get("Extrasensory");
        ArrayList<Move> gMoves = new ArrayList();
        gMoves.add(dragonBreath);
        gMoves.add(extrasensory);
        ArrayList<Move> zMoves = new ArrayList();
        zMoves.add(HellsFiery);
        ArrayList<Move> bMoves = new ArrayList();
        bMoves.add(movelibrary.get("Recover"));
        bMoves.add(minimize);
        bMoves.add(movelibrary.get("Toxic"));
        bMoves.add(movelibrary.get("Confuse Ray"));
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
        this.team1 = team1;
        this.team2 = team2;
    }
}
public class pokemonMain extends PApplet {
    Team team1, team2;
    gameState game;
    public static void main(String[] args){
        PApplet.main("pokemonMain");
    }
    public void settings(){
        size(1400,1400);
    }
    public void setup(){
        library Library = new library();
        this.team1 = Library.team1;
        this.team2 = Library.team2;
        game = new gameState(team1,team2);
        background(255);
        line(900,0,900,1400);
        line(500,0,500,1400);
        noLoop();
    }
    public void displayMoves(Pokemon current1, Pokemon current2){
        fill(255);
        int x = 10;
        for (int i =0 ; i < current1.moves.size();i++){
            rect(x,300,100,50);
            PFont f = createFont("Ariel",16,true);
            textFont(f,16);
            fill(0);
            text(current1.moves.get(i).name,x,300,100,50);
            x += 120;
            fill(255);
        }
        fill(255);
        x = 910;
        for (int i =0 ; i < current2.moves.size();i++){
            rect(x,300,100,50);
            PFont f = createFont("Ariel",16,true);
            textFont(f,16);
            fill(0);
            text(current2.moves.get(i).name,x,300,100,50);
            x += 120;
            fill(255);
        }
    }
    public void displayReserves(Team team1, Team team2){
        fill(255);
        int x = 10;
        for (int i =0 ; i < team1.reserves.size();i++){
            rect(x,400,100,50);
            PFont f = createFont("Ariel",16,true);
            textFont(f,16);
            fill(0);
            text(team1.reserves.get(i).name,x,400,100,50);
            x += 120;
            fill(255);
        }
        fill(255);
        x = 910;
        for (int i =0 ; i < team2.reserves.size();i++){
            rect(x,400,100,50);
            PFont f = createFont("Ariel",16,true);
            textFont(f,16);
            fill(0);
            text(team2.reserves.get(i).name,x,400,100,50);
            x += 120;
            fill(255);
        }
    }
    ArrayList<Integer> moveIndexes = new ArrayList();
    ArrayList<Integer> pokemonIndexes = new ArrayList();
    boolean player1move = true;
    boolean player2move = false;
    boolean player1dead, player2dead;
    public void draw(){
        PFont f = createFont("Ariel",16,true);
        textFont(f,16);
        fill(0);
        text(team1.current.name,80,100);
        text(team1.current.stats[0],160,100);
        text(team2.current.name,1240,100);
        text(team2.current.stats[0],1320,100);
        text("Status: ",80,200);
        text(team1.current.status,160,200);
        text("Status: ",1240,200);
        text(team2.current.status,1320,200);
        while (!team1.isEmpty() && !team2.isEmpty()){ // at every iteration, new round should be made
            player1dead = false;
            player2dead = false;
            Pokemon current1 = team1.current;
            Pokemon current2 = team2.current;
            Move move1 = null;
            Move move2 = null;
            boolean player1switch = false;
            boolean player2switch = false;
            player1move = true;
            player2move = false;
            displayMoves(current1,current2);
            displayReserves(team1,team2);
            System.out.println("What would player one like to play?");
            while (true){
                System.out.print("");
                if (moveIndexes.size() != 0){
                    break;
                }
            }
            int move1Index =  moveIndexes.get(0);
            if (move1Index > 3){
                int pokemonIndex = move1Index - 4;
                team1.switchPokemon(pokemonIndex);
                current1 = team1.current;
                player1switch = true;
            }
            else{
                move1 = current1.moves.get(move1Index);
            }
            moveIndexes.clear();
            player1move = false;
            player2move = true;
            System.out.println("What would player 2 like to play?");
            while (true){
                System.out.print("");
                if (moveIndexes.size() != 0){
                    break;
                }
            }
            int move2Index =  moveIndexes.get(0);
            if (move2Index > 3){
                int pokemonIndex = move2Index-4;
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
                player1dead = true;
                showPokemon();
                displayReserves(team1,team2);
                System.out.println(current1.name + " is dead!");
                if (team1.reserves.isEmpty()) break;
                while (true){
                    System.out.print("");
                    if (pokemonIndexes.size() != 0){
                        break;
                    }
                }
                int newPokemonIndex =  pokemonIndexes.get(0);
                pokemonIndexes.clear();
                team1.switchNewPokemon(newPokemonIndex);
            }
            if (current2.stats[0] == 0){
                player2dead = true;
                showPokemon();
                displayReserves(team1,team2);
                System.out.println(current2.name + " is dead!");
                if (team2.reserves.isEmpty()) break;
                while (true){
                    System.out.print("");
                    if (pokemonIndexes.size() != 0){
                        break;
                    }
                }
                int newPokemonIndex =  pokemonIndexes.get(0);
                pokemonIndexes.clear();
                team2.switchNewPokemon(newPokemonIndex);
            }
            showPokemon();
            moveIndexes.clear();
        }
        System.out.println("GAME OVER");
    }
    public void showPokemon(){
        background(255);
        fill(0);
        line(900,0,900,1400);
        line(500,0,500,1400);
        text(team1.current.name,80,100);
        text(team1.current.stats[0],160,100);
        text(team2.current.name,1240,100);
        text(team2.current.stats[0],1320,100);
        text("Status: ",80,200);
        text(team1.current.status,160,200);
        text("Status: ",1240,200);
        text(team2.current.status,1320,200);
    }
    public void mouseClicked(){
        if (player1dead){
            if (mouseX >= 10 && mouseX <= 110 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(0);
            }
            else if (mouseX >= 130 && mouseX <= 230 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(1);
            }
            else if (mouseX >= 250 && mouseX <= 350 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(2);
            }
            else if (mouseX >= 370 && mouseX <= 470 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(3);
            }
        }
        else if (player2dead){
            if (mouseX >= 910 && mouseX <= 1010 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(0);;
            }
            else if (mouseX >= 1030 && mouseX <= 1130 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(1);
            }
            else if (mouseX >= 1150 && mouseX <= 1250 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(2);
            }
            else if (mouseX >= 1270 && mouseX <= 1370 && mouseY >= 400 && mouseY <= 450){
                pokemonIndexes.add(3);
            }
        }
        else if (player1move){
            if (mouseX >= 10 && mouseX <= 110 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(0);
            }
            else if (mouseX >= 130 && mouseX <= 230 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(1);
            }
            else if (mouseX >= 250 && mouseX <= 350 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(2);
            }
            else if (mouseX >= 370 && mouseX <= 470 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(3);
            }
            else if (mouseX >= 10 && mouseX <= 110 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(4);
            }
            else if (mouseX >= 130 && mouseX <= 230 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(5);
            }
            else if (mouseX >= 250 && mouseX <= 350 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(6);
            }
            else if (mouseX >= 370 && mouseX <= 470 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(7);
            }
        }
        else if (player2move){
            if (mouseX >= 910 && mouseX <= 1010 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(0);
            }
            else if (mouseX >= 1030 && mouseX <= 1130 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(1);
            }
            else if (mouseX >= 1150 && mouseX <= 1250 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(2);
            }
            else if (mouseX >= 1270 && mouseX <= 1370 && mouseY >= 300 && mouseY <= 350){
                moveIndexes.add(3);
            }
            else if (mouseX >= 910 && mouseX <= 1010 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(4);
            }
            else if (mouseX >= 1030 && mouseX <= 1130 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(5);
            }
            else if (mouseX >= 1150 && mouseX <= 1250 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(6);
            }
            else if (mouseX >= 1270 && mouseX <= 1370 && mouseY >= 400 && mouseY <= 450){
                moveIndexes.add(7);
            }
        }
    }

}