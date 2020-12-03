import java.util.concurrent.ThreadLocalRandom;

public class Jugador extends Thread{

    public boolean bonus=false;
    int puntos;
    Long dormir;
    Arena arena;
    boolean eliminado=false;

    public Jugador(Arena arena){
        this.arena=arena;
    }

    public Jugador() {
    }

    public void setPuntos(int puntos){
        this.puntos=puntos;
    }

    @Override
    public void run() {
        try {
            dormir=ThreadLocalRandom.current().nextLong(1000, 5000);
            arena.addParticipante(this);
            if(!eliminado) {
                puntos = puntos + ThreadLocalRandom.current().nextInt(1, 10);
                if(bonus){
                    setPuntos(puntos*2);
                }
                System.out.println("->"+getName()+": "+puntos);
                arena.setGanador(arena.ganador(this));
                if(arena.getGanacont()==10){
                    System.out.println("El ganador es: "+arena.getGanador());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
