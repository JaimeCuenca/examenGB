import java.util.concurrent.Semaphore;

public class Arena {

    private static final int Max_Jug = 10;
    private int pptes=0, ganacont=0;
    private final Semaphore entrar = new Semaphore(Max_Jug, true);
    private Jugador ganador = new Jugador();
    private boolean tanda1=true;


    public void setGanador(Jugador ganador) {
        this.ganador=ganador;
    }

    public String getGanador() {
        return ganador.getName()+" con "+ganador.puntos+" puntos";
    }

    public int getGanacont() {
        return ganacont;
    }

    public void addParticipante(Jugador j) throws InterruptedException {
        j.arena.entrar.acquire();

        if(tanda1){
            System.out.println(j.getName() + " está dentro y va a dormir");
            j.sleep(j.dormir);
            pptes++;

            if(pptes!=10 && pptes!=9 && pptes!=8 && pptes!=7 && pptes!=6) {
                if (pptes == 1) {
                    System.out.println(j.getName()+ " ha sido el primero, bonus");
                    j.bonus=true;
                }
                System.out.println(j.getName()+" pelea");
                pelear();
            }else{
                System.out.println("Eliminamos a "+j.getName()+" por lento");
                j.arena.entrar.release();
                j.eliminado=true;
                tanda1=false;
            }
        }else{
                System.out.println(j.getName()+" está dentro y pelea");
                pelear();
        }


    }

    public synchronized void pelear(){
        try {
            if (pptes<10)
                wait();
            else
                notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Jugador ganador(Jugador j){
        ganacont++;
        if(ganador.puntos<j.puntos){
            return j;
        }else{
            return ganador;
        }
    }
}
