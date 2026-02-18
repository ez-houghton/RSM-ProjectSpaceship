import java.util.ArrayList;

public class StarBase extends Star{
    private final double maxDefense=20;
    private final double maxHealth=500;
    public ArrayList<StarShip> ships = new ArrayList<>();


    /**
     * Constructor to use when fleet already exists
     * @param id string ID to separate instances
     * @param sector current location on game board
     * @param fleet fleet that this ship belongs to.
     */
    StarBase(String id, String sector,Fleet fleet){
        this.id=id;
        this.curHealth=maxHealth;
        this.sector=sector;
        this.fleet=fleet;
        fleet.add(this);
    }
    /**
     * Constructor to be used when adding fleet at later time
     * @param id string ID to separate instances
     * @param sector current location on game board
     */
    StarBase(String id, String sector){
        this.id=id;
        this.curHealth=maxHealth;
        this.sector=sector;
    }
    /**
     * Returns a printable version of all the base's stats
     * @return printable version of base stats
     */
    public String pullInfo(){
        return "ShipID:"+this.id+
                "\n--Docked ships:"+this.ships+
                "\n--Health:"+this.curHealth+"/"+this.maxHealth+
                "\n--Defense:"+this.getCurDef()+"/"+this.maxDefense+
                "\n--Sector:"+this.sector+
                "\n--Destroyed:"+this.destroyed
                ;
    }

    /**
     * Formula for defense of base.
     * @return value of current defense.
     */
    public double getCurDef(){
        double dockDef=0;
        for(StarShip ship : ships) {
            if (ship.getCooldown()!=0)dockDef+=ship.getCurDef();
        }
        return Math.floor(
                this.maxDefense*(this.curHealth/this.maxHealth)
                +(dockDef*(ships.size()/this.maxDefense))
        );
    }
    /**
     * Reduces target's health. sets target to destroyed if health <=0.
     * @param target StarShip object. the ship to be attacked
     * @return integer code. <br>
     * 2 if this object is on cooldown.<br>
     * 1 if attack handles with no issues.<br>
     * 0 if target not in same sector or part of same fleet.<br>
     * -1 if this object destroyed
     */
    public int attack(StarShip target){
        if(this.destroyed)return-1;
        for(StarShip ship :ships){
            ship.attack(target);
        }
        return 1;
    }

    /**
     * Reduces target's health. sets target to destroyed and all ships docked to destroyed if starbase health reduces to 0 or below.
     * @param target StarBase object. the base to be attacked
     * @return integer code. <br>
     * 1 if attack handles with no issues.<br>
     * -1 if this object destroyed
     */
    public int attack(StarBase target){
        if(this.destroyed)return-1;
        for(StarShip ship :ships){
            ship.attack(target);
        }
        return 1;
    }

    /**
     * Adds starship to list of base's ships
     * @param ss ship to be added
     * @return 1 if passes with no issues
     */
    public int add(StarShip ss){
        ships.add(ss);
        return 1;
    }

    /**
     * Moves base to specified sector,
     * @param sect sector to be moved to
     * @return Integer code <br>
     * 1 if passed with no issues
     * 0 if ship is already in this sector or cannot move because it is docked.
     * -1 if this object destroyed
     */
    public int move(String sect){
        if(this.destroyed)return-1;
        if(sect.equals(this.sector))return 0;
        this.sector=sect;
        return 1;
    }
}
