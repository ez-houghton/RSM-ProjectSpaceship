import java.util.ArrayList;

public class Fleet {
    String player;
    String id;
    ArrayList<StarShip> ships = new ArrayList<StarShip>();
    ArrayList<StarBase> bases = new ArrayList<StarBase>();

    /**
     * Constructor to be used when ships already added to arraylist.
     * @param id id to separate instances
     * @param p player name. String
     * @param ss starships arraylist
     * @param sb starbases arraylist
     */
    Fleet(String id,String p, ArrayList<StarShip> ss, ArrayList<StarBase> sb){
        this.id=id;
        this.player=p;
        this.ships=ss;
        this.bases=sb;
        for(StarShip ship:ss)ship.setFleet(this);
        for (StarBase base:sb)base.setFleet(this);
    }

    /**
     * Constructor to be used when no ships already exist in arraylist.
     * @param id id to separate instances
     * @param p player name. string
     */
    Fleet(String id, String p){
        this.id=id;
        this.player=p;
    }

    /**
     * Adds StarBase object to list of Fleet's Bases. Sets starship's fleet to this fleet.
     * @param obj StarBase object.
     */
    public void add(StarBase obj){
        obj.setFleet(this);
        bases.add(obj);
    }

    /**
     * Adds StarShip object to list of Fleet's ships. Sets starship's fleet to this fleet.
     * @param obj
     */
    public void add(StarShip obj){
        obj.setFleet(this);
        ships.add(obj);
    }

    /**
     * Moves all available ships to passed Sector. prints status of all ship's movements.
     * @param sect location to move to on board.
     * @return 1 if passes with no issues.
     */
    public int mobilise(String sect){
        System.out.println("---MOBILISING FLEET "+this.id+"---");
        for (StarShip ship:ships){
            int x = ship.move(sect);
            if (x==2)System.out.println("--"+ship.id+" failed to move, on cooldown");
            else if (x==0) System.out.println("--"+ship.id+ " failed to move, already in sector or docked");
            else System.out.println("--"+ship.id+" moved to "+ sect);
        };
        return 1;
    }

    /**
     * Uses whole fleet to attack single target. prints status of all ship's attacks.
     * @param target the StarBase object to attack.
     * @return 1 if passes with no issues
     */
    public int attack(StarBase target){
        System.out.println("---ATTACKING"+target.id+" USING FLEET "+this.id+"---");
        for (StarShip ship :ships){
            int x = ship.attack(target);
            if (x==2)System.out.println("--"+ship.id+" failed to attack, on cooldown");
            else if (x==0) System.out.println("--"+ship.id+ " failed to attack, target not in sector or of same team");
            else System.out.println("--"+ship.id+" attacked "+target.id);
        }
        return 1;
    }
    /**
     * Uses whole fleet to attack single target. prints status of all ship's attacks.
     * @param target the StarShip object to attack.
     * @return 1 if passes with no issues
     */
    public int attack(StarShip target){
        System.out.println("---ATTACKING"+target.id+" USING FLEET "+this.id+"---");
        for (StarShip ship : ships){
            int x = ship.attack(target);
            if (x==2)System.out.println("--"+ship.id+" failed to attack, on cooldown");
            else if (x==0) System.out.println("--"+ship.id+ " failed to attack, target not in sector or of same team");
            else System.out.println("--"+ship.id+" attacked "+target.id);
        }
        return 1;
    }

}
