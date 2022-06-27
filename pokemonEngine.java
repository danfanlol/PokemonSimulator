import java.util.ArrayList;

class gameState{
    Team team1;
    Team team2;
    ArrayList<Round> moveLog = new ArrayList();
    int turns = 0;
    public gameState(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
    }
    public void showStats(){
        System.out.println("Team 1: " + team1.current.name + " " + team1.current.stats[0] + " status: " + team1.current.status);
        System.out.println("Team 2: " + team2.current.name + " " + team2.current.stats[0] + " status: " + team2.current.status);
    }
    public ArrayList<Double> calculateDamage(Move move, Pokemon attacking, Pokemon defending){
        double STAB = 1;
        double effectiveness = 1;
        double burnHalver = 1;
        if (move.isPhysical && attacking.status.equals("burn")){
            burnHalver = .5;
        }
        if (defending.immunities.contains(move.type)){
            System.out.println(defending.name + " is immune!");
            effectiveness = 0;  // if immune to move, also no effect
        }
        else if (defending.resistances.contains(move.type) && move.power != 0){
            System.out.println(defending.name + " resisted!");
            effectiveness = .5;
        }
        else if (defending.weaknesses.contains(move.type) && move.power != 0){
            System.out.println(defending.name + " took extra damage!");
            effectiveness = 2;
        }
        else if (move.power != 0){
            System.out.println(defending.name + " took damage!");
        }
        if (attacking.types.contains(move.type))
            STAB = 1.5;
        int defendingDefense;
        int attackingAttack;
        if (move.isPhysical){
            defendingDefense = defending.stats[2];
            attackingAttack = attacking.stats[1];
        }
        else{
            defendingDefense = defending.stats[4];
            attackingAttack = attacking.stats[3];
        }
        double d1 = (2 * attacking.level) / 5.0;
        double d2 = (d1 + 2) * move.power * (attackingAttack / (defendingDefense * 1.0)) / 50;
        double d3 = (d2 + 2) * STAB * effectiveness * burnHalver;
        if (move.power == 0)
            d3 = 0;
        ArrayList<Double> s = new ArrayList();
        s.add(d3);
        s.add(effectiveness);
        return s;
    }
    public void makeMove(Move move, Pokemon attacking, Pokemon defending){
        if (move.type.equals("status")) changeStatus(attacking, move.effect);
        else{
            ArrayList<Double> info = calculateDamage(move, attacking,defending);
            double d3 = info.get(0);
            double effectiveness = info.get(0);
            defending.stats[0] = (int)Math.max(defending.stats[0]-d3,0);
            if ((int)(Math.random()*100+1) <= move.chance && !defending.immunities.contains(move.type)) if (move.target.equals("Defending")) changeStatus(defending, move.effect);else if (move.target.equals("Attacking")) changeStatus(attacking,move.effect);
        }
    }
    public void changeStatus(Pokemon pokemon, String status){
        if (status.equals("poison") && pokemon.status.equals("None")){
            pokemon.status = status;
        }
        if (status.equals("burn") && pokemon.status.equals("None")){
            pokemon.status = status;
        }
        if (status.equals("badly poison") && pokemon.status.equals("None")){
            pokemon.status = status;
            System.out.println(pokemon.name + " has been badly poisoned!");
        }
        if (status.equals("relieve sleeping") && pokemon.status.equals("sleep")){
            pokemon.status = "None";
        }
        if (status.equals("sleep") && pokemon.status.equals("None")) {
            pokemon.status = "sleep";
            pokemon.sleepsince = 1;
        }
        if (status.equals("paralyze") && pokemon.status.equals("None")){
            System.out.println(pokemon.name + " is paralyzed!");
            pokemon.status = "paralyze";
        }
        if (status.equals("freeze") && pokemon.status.equals("None")){
            pokemon.status = "freeze";
        }
        if (status.equals("confuse") && !pokemon.isConfused){
            pokemon.isConfused = true;
            pokemon.confusedsince = 1;
            System.out.println(pokemon.name + " became confused!");
        }
        if (status.equals("recover")){
            System.out.println(pokemon.name + " has recovered health!");
            int initial = pokemon.stats[0];
            pokemon.stats[0] += pokemon.maximumHP/2;
            pokemon.stats[0] = Math.min(pokemon.stats[0],pokemon.maximumHP);
            pokemon.recoverlist.add(pokemon.stats[0]-initial);
        }
        if (status.equals("Special Defense 2")) pokemon.stats[4] += pokemon.maximumSpecialDefense;
        if (status.equals("Special Defense 1")) pokemon.stats[4] += (pokemon.maximumSpecialDefense/2);
        if (status.equals("Special Defense -2")) pokemon.stats[4] -= pokemon.maximumSpecialDefense;
        if (status.equals("Special Defense -1")) pokemon.stats[4] -= (pokemon.maximumSpecialDefense/2);
        if (status.equals("flinch")) pokemon.flinched = true;
        if (status.equals("Coil")){
            pokemon.stats[1] += pokemon.maximumAttack/2;
            pokemon.stats[2] += pokemon.maximumDefense/2;
            pokemon.accuracy += .33;
        }
        if (status.equals("Minimize")){
            if (pokemon.evasion >= 2.9){
                System.out.println(pokemon.name + " can't go higher with its evasion!");
            }
            else{
                pokemon.evasion += .66;
                pokemon.evasion = Math.min(3,pokemon.evasion);
                System.out.println(pokemon.name + " raised its evasion sharply!");
            }
        }
    }
    public boolean freezeCheck(Pokemon pokemon){
        if (pokemon.status.equals("paralyze")){
            if ((int)(Math.random()*100+1) <= 25){
                System.out.println(pokemon.name + " cannot move due to paralysis!");
                return false;
            }
            return true;
        }
        if (pokemon.status.equals("freeze")){
            if ((int)(Math.random()*100+1) <= 20){
                System.out.println(pokemon.name + " has thawed!");
                pokemon.status = "None";
                return true;
            }
            else{
                System.out.println(pokemon.name + " is frozen!");
                return false;
            }
        }
        if (pokemon.status.equals("sleep")){
            if ((int)(Math.random()*100+1) <= 34*pokemon.sleepsince){
                System.out.println(pokemon.name + " has awoke!");
                pokemon.sleepsince = -1;
                pokemon.status = "None";
                return true;
            }
            else{
                System.out.println(pokemon.name + " is sleeping!");
                pokemon.sleepsince += 1;
                return false;
            }
        }
        if (pokemon.isConfused){
            System.out.println(pokemon.name + " is confused!");
            if (pokemon.confusedsince == 1){
                if ((int)(Math.random()*100+1) <= 33 ){
                    pokemon.confusedsince += 1;
                    System.out.println(pokemon.name + "  hurt itself in confusion!");
                    pokemon.stats[0] -= calculateDamage(new Move("",40,"",100,100,"",true,1,""),pokemon,pokemon).get(0);
                    pokemon.stats[0] = Math.max(0,pokemon.stats[0]);
                    return false;
                }
                pokemon.confusedsince += 1;
                return true;
            };
            if (pokemon.confusedsince > 1){
                if ((int)(Math.random()*100+1) <= 25*pokemon.confusedsince-1){
                    System.out.println(pokemon.name + " snapped out of confusion!");
                    pokemon.confusedsince = -1;
                    pokemon.isConfused = false;
                    return true;
                }
                else{
                    if ((int)(Math.random()*100+1) <= 33){
                        pokemon.confusedsince += 1;
                        System.out.println(pokemon.name + "  hurt itself in confusion!");
                        pokemon.stats[0] -= calculateDamage(new Move("",40,"",100,100,"",true,1,""),pokemon,pokemon).get(0);
                        pokemon.stats[0] = Math.max(0,pokemon.stats[0]);
                        return false;
                    }
                    pokemon.confusedsince += 1;
                    return true;
                }
            }
        }
        return true;
    }
    public void makeTurn(Pokemon pokemon1, Move move1, Pokemon pokemon2, Move move2){
        turns += 1;
        Round round = new Round();
        double pokemon1evasion = pokemon1.evasion;
        if (move2.effect.equals("Ignore Evasiveness")) pokemon1evasion = 1;
        double pokemon2evasion = pokemon2.evasion;
        if (move1.effect.equals("Ignore Evasiveness")) pokemon2evasion = 1;
        if (((move1.priority == move2.priority) && (pokemon1.stats[5] > pokemon2.stats[5])) || (move1.priority > move2.priority)){
//            System.out.println(pokemon2.name + " " + pokemon2.evasion);
            if ((((int)(Math.random()*100+1) <= Math.min(100,move1.accuracy*pokemon1.accuracy/pokemon2evasion)) || move1.type.equals("status"))){
                if (freezeCheck(pokemon1)){
                    round.pokemon1 = pokemon1;
                    round.move1 = move1;
                    makeMove(move1, pokemon1, pokemon2);
                }
            }
            else{
                if (pokemon1.flinched) System.out.println(pokemon1.name + " flinched!");
                else System.out.println(pokemon1.name + " missed!");
            }
            if ((pokemon2.stats[0] != 0 && (((int)(Math.random()*100+1) <= Math.min(100,move2.accuracy*pokemon2.accuracy/pokemon1evasion)) || move2.type.equals("status"))) &&  (!pokemon2.flinched)){
                if (freezeCheck(pokemon2)){
                    round.pokemon2 = pokemon2;
                    round.move2 = move2;
                    makeMove(move2,pokemon2,pokemon1);
                }
            }
            else{
                if (pokemon2.flinched) System.out.println(pokemon2.name + " flinched!");
                else if (pokemon2.stats[0] != 0) System.out.println(pokemon2.name + " missed!");
            }
        }
        else{
            if ((((int)(Math.random()*100+1) <= Math.min(100,move2.accuracy*pokemon2.accuracy/pokemon1evasion)) || move2.type.equals("status"))){
                if (freezeCheck(pokemon2)){
                    makeMove(move2,pokemon2,pokemon1);
                    round.pokemon2 = (pokemon2);
                    round.move2 = (move2);
                }
            }
            else{
                if (pokemon2.flinched) System.out.println(pokemon2.name + " flinched!");
                else System.out.println(pokemon2.name + " missed!");
            }
            if (pokemon1.stats[0] != 0 && (((int)(Math.random()*100+1) <= Math.min(100,move1.accuracy*pokemon1.accuracy/pokemon2evasion)) || move1.type.equals("status")) &&  !pokemon1.flinched){
                if (freezeCheck(pokemon1)){
                    makeMove(move1,pokemon1,pokemon2);
                    round.pokemon1 = (pokemon1);
                    round.move1 = (move1);
                }
            }
            else{
                if (pokemon1.flinched) System.out.println(pokemon1.name + " flinched!");
                else if (pokemon1.stats[0] != 0) System.out.println(pokemon1.name + " missed!");
            }
        }
        moveLog.add(round);
        pokemon2.flinched = false;
        pokemon1.flinched = false;
        applyStatus(pokemon1,pokemon2);
    }
    public void applyStatus(Pokemon pokemon1, Pokemon pokemon2){
        if (pokemon1.status.equals("poison") && pokemon1.stats[0] != 0){
            System.out.println(pokemon1.name + " is poisoned!");
            pokemon1.stats[0] -= pokemon1.maximumHP /8;
            pokemon1.stats[0] = Math.max(0,pokemon1.stats[0]);
            if (pokemon1.stats[0] == 0) System.out.println(pokemon1.name + " has died from poison!");
        }
        if (pokemon1.status.equals("badly poison")){
            System.out.println(pokemon1.name + " has been hurt by poison!");
            pokemon1.stats[0] -= Math.min(15*pokemon1.maximumHP/16,pokemon1.badlyPoisonedCounter*pokemon1.maximumHP/16);
            pokemon1.stats[0] = Math.max(0, pokemon1.stats[0]);
            pokemon1.badlyPoisonedCounter += 1;
        }
        if (pokemon1.status.equals("burn")){
            pokemon1.stats[0] -= pokemon1.maximumHP / 16;
            pokemon1.stats[0] = Math.max(0, pokemon1.stats[0]);
        }
        if (pokemon1.status.equals("paralyze")) {
            pokemon1.stats[5] = pokemon1.maxSpeed /2;
        }
        if (pokemon2.status.equals("poison") && pokemon2.stats[0] != 0){
            System.out.println(pokemon2.name + " is poisoned!");
            pokemon2.stats[0] -= pokemon2.maximumHP /8;
            pokemon2.stats[0] = Math.max(0,pokemon2.stats[0]);
            if (pokemon2.stats[0] == 0) System.out.println(pokemon2.name + " has died from poison!");
        }
        if (pokemon2.status.equals("badly poison")){
            System.out.println(pokemon2.name + " has been hurt by poison!");
            pokemon2.stats[0] -= Math.min(15*pokemon2.maximumHP/16,pokemon2.badlyPoisonedCounter*pokemon2.maximumHP/16);
            pokemon2.stats[0] = Math.max(0, pokemon2.stats[0]);
            pokemon2.badlyPoisonedCounter += 1;
        }
        if (pokemon2.status.equals("burn")){
            pokemon2.stats[0] -= pokemon2.maximumHP / 16;
            pokemon2.stats[0] = Math.max(0, pokemon2.stats[0]);
        }
        if (pokemon2.status.equals("paralyze")) pokemon2.stats[5] = pokemon2.maxSpeed /2;
    }
    public void makeSwitch(Pokemon pokemon1, Move move1, Pokemon pokemon2){
        turns += 1;
        Round round = new Round();
        if (((int)(Math.random()*100+1) <= Math.min(100,move1.accuracy*pokemon1.accuracy/pokemon2.evasion)) || move1.type.equals("status")){
            makeMove(move1,pokemon1,pokemon2);
            round.pokemon1 = (pokemon1);
            round.move1 = move1;
        }
        else{
            System.out.println(pokemon1.name + " missed!");
        }
        round.pokemon2 = (pokemon2);
        pokemon2.badlyPoisonedCounter = 1;
        applyStatus(pokemon1, pokemon2);
        moveLog.add(round);
    }
    public void makeSwitch2(Pokemon pokemon1, Pokemon pokemon2){
        turns += 1;
        Round round = new Round();
        round.pokemon1 = pokemon1;
        round.pokemon2 = pokemon2;
        applyStatus(pokemon1,pokemon2);
        moveLog.add(round);
    }
}

class Round{
    public Pokemon pokemon1;
    public Move move1;
    public Pokemon pokemon2;
    public Move move2;
}

class Being{
    double evasion;
    int maximumHP;
    int maximumSpecialDefense;
    int maximumAttack;
    int maximumDefense;
    String name;
    int[] stats;
    boolean isConfused = false;
    ArrayList<String> weaknesses;
    ArrayList<String> types;
    ArrayList<Move> moves;
    int level;
    ArrayList<String> resistances;
    ArrayList<String> immunities;
    String status;
    int badlyPoisonedCounter = 1;
    int maxSpeed;
    boolean flinched = false;
    double accuracy = 1;
    int sleepsince = -1;
    int confusedsince = -1;
    ArrayList<Integer> recoverlist = new ArrayList();
    public Being(String pokemonName, int[] stats, ArrayList<String> weaknesses, ArrayList<String> types, ArrayList<Move> moves, int level, ArrayList<String> resistances, ArrayList<String> immunities, String status){
        this.evasion = 1;
        this.maximumHP = stats[0];
        this.maximumSpecialDefense = stats[4];
        this.name = pokemonName;
        this.maxSpeed = stats[5];
        this.stats = stats; //stats will go [health,attack,defense,special attack, special defense, speed
        this.weaknesses = weaknesses;
        this.types = types;
        this.moves = moves;
        this.level = level;
        this.resistances = resistances;
        this.immunities = immunities;
        this.status = status;
        if (status.equals("paralyze")) stats[5] *= 1.0 /2;
        if (status.equals("sleep")) sleepsince = 1;
    }
    public StringBuilder showMoves(){
        StringBuilder s = new StringBuilder();
        for (int i= 0 ; i < moves.size();i++){
            s.append(moves.get(i).name).append(" ");
        }
        return s;
    }
}
class Pokemon extends Being{
    double evasion;
    int maximumHP;
    int maximumSpecialDefense;
    int maximumAttack;
    int maximumDefense;
    String name;
    int[] stats;
    boolean isConfused = false;
    ArrayList<String> weaknesses;
    ArrayList<String> types;
    ArrayList<Move> moves;
    int level;
    ArrayList<String> resistances;
    ArrayList<String> immunities;
    String status;
    int badlyPoisonedCounter = 1;
    int maxSpeed;
    boolean flinched = false;
    double accuracy = 1;
    int sleepsince = -1;
    int confusedsince = -1;
    ArrayList<Integer> recoverlist = new ArrayList();
    public Pokemon(String pokemonName, int[] stats, ArrayList<String> weaknesses, ArrayList<String> types, ArrayList<Move> moves, int level, ArrayList<String> resistances, ArrayList<String> immunities, String status){
        super(pokemonName,stats,weaknesses,types,moves,level,resistances,immunities,status);
        this.evasion = 1;
        this.maximumHP = stats[0];
        this.maximumSpecialDefense = stats[4];
        this.name = pokemonName;
        this.maxSpeed = stats[5];
        this.stats = stats; //stats will go [health,attack,defense,special attack, special defense, speed
        this.weaknesses = weaknesses;
        this.types = types;
        this.moves = moves;
        this.level = level;
        this.resistances = resistances;
        this.immunities = immunities;
        this.status = status;
        if (status.equals("paralyze")) stats[5] *= 1.0 /2;
        if (status.equals("sleep")) sleepsince = 1;
    }
    public StringBuilder showMoves(){
        StringBuilder s = new StringBuilder();
        for (int i= 0 ; i < moves.size();i++){
            s.append(moves.get(i).name).append(" ");
        }
        return s;
    }
}

class Move{
    String name;
    int power;
    String effect;
    int chance;
    int accuracy;
    String type;
    boolean isPhysical;
    int priority;
    String target; // defending or attacking; does the move effect the castor or the defender

    public Move(String name, int power, String effect, int chance, int accuracy, String moveType, Boolean isPhysical, int priority, String target){
        this.power = power;
        this.effect = effect;
        this.chance = chance;
        this.accuracy = accuracy;
        this.type = moveType;
        this.isPhysical = isPhysical;
        this.priority = priority;
        this.target = target;
        this.name = name;

    }
}



