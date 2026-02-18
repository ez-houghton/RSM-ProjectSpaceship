import java.util.ArrayList;

public class Extension {
    public static void main(String[] args) {
        // Player 1 Fleet. Fleet first construction
        Fleet p1fleet = new Fleet("1f", "Player 1");
        StarBase p1b0 = new StarBase("p1b0", "Sector 1", p1fleet);
        StarShip p1s0 = new StarShip("p1s0", "Sector 1", p1fleet);
        StarShip p1s1 = new StarShip("p1s1", "Sector 1", p1fleet);
        StarShip p1s2 = new StarShip("p1s2", "Sector 1", p1fleet);

        for (StarShip ship : p1fleet.ships) System.out.println("--Ship:" + ship.id + ". Fleet:" + ship.fleet.id);

        // Player 2 Fleet. Ship first Construction.

        ArrayList<StarBase> p2bases = new ArrayList<>();
        StarBase p2b0 = new StarBase("p2b0", "Sector 2");
        p2bases.add(p2b0);
        ArrayList<StarShip> p2ships = new ArrayList<>();
        StarShip p2s0 = new StarShip("p2s0", "Sector 2");
        StarShip p2s1 = new StarShip("p2s1", "Sector 2");
        StarShip p2s2 = new StarShip("p2s2", "Sector 2");
        p2ships.add(p2s0);
        p2ships.add(p2s1);
        p2ships.add(p2s2);
        Fleet p2fleet = new Fleet("2f", "Player 2", p2ships, p2bases);
        for(StarShip ship: p2fleet.ships)System.out.println("--Ship:"+ship.id+". Fleet:"+ ship.fleet.id);

        System.out.println("------END OF CONSTRUCTION OF FLEETS-------");


        System.out.println("-----CHANGING SHIP MAX HEALTH-----");
        System.out.println(p2s0.pullInfo());
        p2s0.setMaxHealth(150);
        System.out.println(p2s0.pullInfo());

        System.out.println("-----CHANGING SHIP MAX DEFENCE-----");
        System.out.println(p2s0.pullInfo());
        p2s0.setMaxDef(15);
        System.out.println(p2s0.pullInfo());

        System.out.println("------DOCKING P1S0 AND P1S1 TO P1B0------");
        p1s0.dock(p1b0);
        p1s1.dock(p1b0);

        System.out.println("------ATTACKING P2S0 WITH P1B0------");
        p2s0.move("Sector 1");
        System.out.println(p2s0.pullInfo());
        System.out.println("Attacking");
        p1b0.attack(p2s0);
        System.out.println(p2s0.pullInfo());

        System.out.println("------TOWING P1B0 TO SECTOR 2------");
        p1s0.undock();
        p1s1.undock();
        System.out.println(p1fleet.pullInfo());
        p1fleet.tow(p1b0,"Sector 2");
        System.out.println(p1fleet.pullInfo());

        System.out.println("------CLOAKING P2S1 AND ATTEMPTING ATTACK------");

        p2s1.cloak();
        System.out.println(p2s1.pullInfo());
        p1s1.attack(p2s1);
        System.out.println(p2s1.pullInfo());
    }
}