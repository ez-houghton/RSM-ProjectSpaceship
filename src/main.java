import java.util.ArrayList;

public class main {
    public static void main(String args[]){
        // Player 1 Fleet. Fleet first construction
        Fleet p1fleet= new Fleet("1f","Player 1");
        StarBase p1b0= new StarBase("p1b0","Sector 1",p1fleet);
        StarShip p1s0 = new StarShip("p1s0","Sector 1",p1fleet);
        StarShip p1s1 =new StarShip("p1s1","Sector 1",p1fleet);
        StarShip p1s2 =new StarShip("p1s2","Sector 1",p1fleet);

        for(StarShip ship: p1fleet.ships)System.out.println("--Ship:"+ship.id+". Fleet:"+ ship.fleet.id);

        // Player 2 Fleet. Ship first Construction.

        ArrayList<StarBase> p2bases = new ArrayList<StarBase>();
        StarBase p2b0= new StarBase("p2b0","Sector 2");
        p2bases.add(p2b0);
        ArrayList<StarShip> p2ships = new ArrayList<StarShip>();
        StarShip p2s0= new StarShip("p2s0","Sector 2");
        StarShip p2s1= new StarShip("p2s1","Sector 2");
        StarShip p2s2= new StarShip("p2s2","Sector 2");
        p2ships.add(p2s0);
        p2ships.add(p2s1);
        p2ships.add(p2s2);
        Fleet p2fleet= new Fleet("2f","Player 2",p2ships,p2bases);

        // ALTERNATIVE THIRD METHOD TO ADD SHIPS. USES NO ARRAYLIST.
        /*
        StarBase p2b0= new StarBase("p2b0","Sector 2");
        StarShip p2s0= new StarShip("p2s0","Sector 2");
        StarShip p2s1= new StarShip("p2s1","Sector 2");
        StarShip p2s2= new StarShip("p2s2","Sector 2");
        Fleet p2fleet= new Fleet("2f","Player 2");
        p2fleet.add(p2b0);
        p2fleet.add(p2s0);
        p2fleet.add(p2s1);
        p2fleet.add(p2s2);
         */
        for(StarShip ship: p2fleet.ships)System.out.println("--Ship:"+ship.id+". Fleet:"+ ship.fleet.id);

        System.out.println("------END OF CONSTRUCTION OF FLEETS-------");
        p1fleet.mobilise("Sector 2");

        System.out.println("------DOCKING P2S0 AND P2S1 TO P2B0------");
        p2s0.dock(p2b0);
        p2s1.dock(p2b0);

        System.out.println(p2s0.pullInfo());
        System.out.println(p2s1.pullInfo());

        System.out.println("------ATTACKING P2S2 TWICE------");
        p1s0.attack(p2s2);
        p1s0.attack(p2s2);

        System.out.println(p2s2.pullInfo());

        System.out.println("-----DOCKING AND REPAIRING P2S2 to P2b0-----");
        p2s2.dock(p2b0);
        p2s2.repair();
        System.out.println(p2s2.pullInfo());

        System.out.println("-----ATTACKING P2b0 UNTIL DEATH-----");
        while(!p2b0.destroyed){
            p1fleet.attack(p2b0);
            System.out.println(p2b0.pullInfo());
        }
    }
}
