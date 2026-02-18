

public class StarShip extends Star{
    private double maxAttack=30;
    private double maxDefense=10;
    private double maxCrew=10;
    private double maxHealth=100;
    public StarBase starbase;
    private boolean docked=false;
    private int cooldown=0;
    private boolean cloaked = false;

    /**
     * Constructor to be used when adding fleet on ship creation.
     * @param id string ID to separate instances.
     * @param sector current location on game board
     * @param fleet fleet to be added to.
     */
    StarShip(String id, String sector,Fleet fleet){
        this.id=id;
        this.curHealth=maxHealth;
        this.curCrew=maxCrew;
        this.sector=sector;
        this.fleet=fleet;
        fleet.add(this);
    }

    /**
     * Constructor to be used when adding fleet after declaring ship.
     * @param id string ID to separate instances.
     * @param sector current location on game board
     */
    StarShip(String id, String sector){
        this.id=id;
        this.curHealth=maxHealth;
        this.curCrew=maxCrew;
        this.sector=sector;

    }

    /**
     * Returns printable ship info.
     * @return ship info.
     */
    public String pullInfo(){
        return "ShipID:"+this.id+
                "\n--Crew:"+this.curCrew+ "/"+this.maxCrew+
                "\n--Health:"+this.curHealth+"/"+this.maxHealth+
                "\n--Defense:"+this.getCurDef()+"/"+this.maxDefense+
                "\n--Attack:"+this.getCurAttack()+"/"+this.maxAttack+
                "\n--Cooldown:"+this.cooldown+
                "\n--Starbase:"+(this.starbase==null?"none":this.starbase.id)+
                "\n--Sector:"+this.sector+
                "\n--Destroyed:"+this.destroyed+
                "\n--Cloaked:"+this.cloaked
                ;
    }

    /**
     * Sets max defense to new value
     * @param newDef
     */
    public void setMaxDef(int newDef){
        this.maxDefense = newDef;
    }
    public int getCooldown(){
        return this.cooldown;
    }
    public boolean isDocked(){return this.docked;}
    /**
     * Formula for current attack value
     * @return value of current attack.
     */
    private double getCurAttack(){
        return Math.ceil(this.maxAttack*(this.curHealth/this.maxHealth));
    }

    /**
     * Formula for current defense value
     * @return value of current defense.
     */
    public double getCurDef(){
        return Math.floor(this.maxDefense*((this.curHealth+this.curCrew)/(this.maxHealth+this.maxCrew)));
    }

    /**
     * Formula for current attack damage
     * @param atk this ship's attack value
     * @param def target ship's defense value
     * @return amount of damage
     */
    private double getDamage(double atk, double def){
        return atk-def>5?atk-def:5;
    }

    /**
     * Checks if ship is on cooldown . reduces cooldown by 1 if it is.
     * @return boolean if action should be skipped.
     */
    private boolean checkSkip() {
        if (this.cooldown > 0) {
            this.cooldown -= 1;
            return true;
        }
        return false;
    }

    /**
     * Moves ship to specified sector,
     * @param sect sector to be moved to
     * @return Integer code <br>
     * 2 if on cooldown
     * 1 if passed with no issues
     * 0 if ship is already in this sector or cannot move because it is docked.
     * -1 if this object destroyed
     */
    public int move(String sect){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(sect.equals(this.sector)||this.docked)return 0;

        this.sector=sect;
        return 1;
    }

    public void cloak(){this.cloaked=true;}
    public void uncloak(){this.cloaked=false;}


    /**
     * Docks ship to specified StarBase object
     * @param sb the base to be docked to
     * @return integer code
     * 2 if on cooldown
     * 1 if passed with no issues
     * 0 if target base is not part of the same fleet or in different sector
     * -1 if this object destroyed
     */
    public int dock(StarBase sb){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(!(sb.fleet==this.fleet) && !(sb.sector.equals(this.sector))) return 0;

        this.starbase = sb;
        this.docked=true;
        starbase.add(this);
        return 1;

    }

    /**
     * Undocks ship from dock.
     * @return integer code <br>
     * 2 if on cooldown <br>
     * 1 if passes no issues <br>
     * 0 if not currently docked <br>
     * -1 if this object destroyed
     */
    public int undock(){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(this.starbase==null)return 0;

        this.starbase=null;
        this.docked=false;
        return 1;


    }
    /**
     * Sets max health to new value
     * @param newHealth
     */
    public void setMaxHealth(int newHealth){
        this.maxHealth = newHealth;
    }
    /**
     * repairs ship's health and crew to full. updates cooldown depending on amount healed.<br>
     * 4 actions if health was below 25% <br>
     * 3 actions if health was between 25% and 50% <br>
     * 2 actions if health between 50% and 75% <br>
     * 1 action if health above 75%<br>
     * upper bounds not exact equals. 50% health exactly will set cooldown to 2 actions.
     * @return integer code <br>
     * 2 if this object is on cooldown.<br>
     * 1 if repair handles with no issues.<br>
     * 0 if this object is not docked.<br>
     * -1 if this object destroyed
     */
    public int repair(){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(!this.docked)return 0;
        double percentDamage=this.curHealth/this.maxHealth;

        this.curCrew=this.maxCrew;
        this.curHealth=this.maxHealth;
        if (percentDamage<0.25)this.cooldown=4;
        else if (percentDamage<.5)this.cooldown=3;
        else if (percentDamage<.75)this.cooldown=2;
        else this.cooldown=1;

        return 1;


    }


    /**
     * Reduces target's health. sets target to destroyed if health <=0.
     * @param target StarShip object. the ship to be attacked
     * @return integer code. <br>
     * 3 if target is cloaked. uncloaks target <br>
     * 2 if this object is on cooldown.<br>
     * 1 if attack handles with no issues.<br>
     * 0 if target not in same sector or part of same fleet.<br>
     * -1 if this object destroyed
     */
    public int attack(StarShip target){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(!this.sector.equals(target.sector) ||target.fleet==this.fleet)return 0;
        if(target.cloaked){
            target.uncloak();
            return 3;
        }
        double def= target.getCurDef();
        double atk = this.getCurAttack();
        double damage = getDamage(atk,def);
        target.curHealth-=damage;
        double crewDam=Math.ceil((damage/target.maxHealth)*target.curCrew);

        target.curCrew=crewDam>=target.curCrew?1:target.curCrew-crewDam;
        target.destroyed=target.curHealth<=0;
        return 1;
    }

    /**
     * Reduces target's health. sets target to destroyed and all ships docked to destroyed if starbase health reduces to 0 or below.
     * @param target StarBase object. the base to be attacked
     * @return integer code. <br>
     * 2 if this object is on cooldown.<br>
     * 1 if attack handles with no issues.<br>
     * 0 if target not in same sector or part of same fleet.<br>
     * -1 if this object destroyed
     */
    public int attack(StarBase target){
        if(this.destroyed)return-1;
        if(checkSkip())return 2;
        if(!this.sector.equals(target.sector) ||target.fleet==this.fleet)return 0;

        double def= target.getCurDef();
        double atk = this.getCurAttack();
        double damage = getDamage(atk,def);
        target.curHealth-=damage;
        target.destroyed=target.curHealth<=0;
        if (target.destroyed){
            for(StarShip ship : target.ships)ship.destroyed=true;
        }
        return 1;
    }
}