import java.util.concurrent.ThreadLocalRandom;

public class Jugador extends Thread{

    int puntos;
    Long dormir;
    Arena arena;

    public Jugador(Arena arena){
        this.arena=arena;
    }
    public Jugador(){
        this.puntos=0;
    }

    public void setPuntos(int puntos){
        this.puntos=puntos;
    }

    @Override
    public void run() {
        try {
            dormir=ThreadLocalRandom.current().nextLong(1000, 5000);
            sleep(dormir);
            System.out.println(this.getName()+" ha dormido: "+dormir);
            puntos=ThreadLocalRandom.current().nextInt(0,100);
            arena.addParticipante(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
