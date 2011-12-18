public class Magic {
  public static final Magic STOPPER = new Expelliarmus();
  public static final Magic SLASHER = new Sectumsempra();
  public static final Magic TORTURER = new Crucio();
  
  public static void show(String s) {
    System.out.println(s);
  }
  
  public static void show(Magic m) {
    show(m.getClass().getName());
  }
  
  public static Magic cast(Magic m) {
    return m;
  }
  
  public Magic cast() {
    return this;
  }
  
  public Magic counter(Magic m) {
    return STOPPER;
  }
  
  public static void main(String[] args) {
    show(STOPPER.counter(STOPPER));
    show(STOPPER.counter(SLASHER));
    show(SLASHER.counter(SLASHER));
    show(TORTURER.counter(SLASHER));
    show(new Sectumsempra().counter(SLASHER));
  } 
}

class Expelliarmus extends Magic { }

class DarkMagic extends Magic {
  public static Magic cast(Magic m) {
    return m instanceof DarkMagic ? m : SLASHER;
  }
  
  public Magic counter(Magic m) {
    return cast(m);
  }
  
  public Magic counter(DarkMagic m) {
    return TORTURER;
  }
}

class Sectumsempra extends DarkMagic {
  public Magic cast() {
    return SLASHER;
  }
}

class UnforgivableCurse extends DarkMagic { }

class Crucio extends UnforgivableCurse {
  public Magic counter(Magic m) {
    return TORTURER;
  }
}