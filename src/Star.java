abstract class Star {
    Fleet fleet;
    String sector;
    String id;
    double curHealth;
    double curCrew;
    double maxHealth;
    boolean destroyed=false;

    /**
     * Sets fleet to the object passed.
     * @param fleet fleet to be added to.
     */
    public void setFleet(Fleet fleet){this.fleet=fleet;}

}
