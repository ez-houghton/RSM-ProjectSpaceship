import java.util.ArrayList;

public class StarBase extends Star{
    private final double maxDefense=20;
    private final double maxHealth=500;
    public ArrayList<StarShip> ships = new ArrayList<StarShip>();


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
     * @return printable verseion of base stats
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
        for(StarShip ship : ships) dockDef+=ship.getCurDef();
        return Math.floor(
                this.maxDefense*(this.curHealth/this.maxHealth)
                +(dockDef*(ships.size()/this.maxDefense))
        );
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
}
