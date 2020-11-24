import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Arena {

    private static final int Max_Jug = 10;
    private ArrayList<Jugador> participantes = new ArrayList<Jugador>();
    private final Semaphore entrar = new Semaphore(Max_Jug, true);
    private boolean eliminados;


    public synchronized void addParticipante(Jugador j) throws InterruptedException {
        j.arena.entrar.acquire();
        participantes.add(j);

        if(participantes.size()==10){
            notifyAll();
            if(!eliminados) {
                long rapido = 0;
                int pos = 0;
                rapido = participantes.get(pos).dormir;
                for (int k = 1; k < participantes.size(); k++) {
                    if (participantes.get(k).dormir < rapido) {
                        rapido = participantes.get(k).dormir;
                        pos = k;
                    }
                }
                participantes.get(pos).setPuntos(participantes.get(pos).puntos + 20);
                System.out.println(this.participantes.get(pos).getName() + " ha sido el más rápido así que le sumamos 20 puntos: " + participantes.get(pos).puntos);
                eliminarLentos();
                eliminados=true;
            }else{
                Jugador ganador = ganador();
                System.out.println("El ganador es: "+ganador.getName()+" con una puntuación de: "+ganador.puntos);
            }
        }else{
            wait();
        }
    }

    private Jugador ganador() {
        Jugador ganador = new Jugador();
        for(Jugador i : participantes){
            if(i.puntos>ganador.puntos)
                ganador=i;
        }
        return ganador;
    }

    private void eliminarLentos() {
        ArrayList<Jugador> eliminados = new ArrayList<Jugador>();
        long dormilon=0;
        int pos=0;
        for(int i=0; i<5; i++){
            dormilon=participantes.get(0).dormir;
            for(int j=1; j<participantes.size(); j++){
                if(participantes.get(j).dormir>dormilon){
                    dormilon=participantes.get(j).dormir;
                    pos=j;
                }
            }
            eliminados.add(participantes.get(pos));
            participantes.remove(pos);
        }
        for(int i=0; i<5; i++){
            System.out.println("Eliminamos a: "+eliminados.get(i).getName());
            eliminados.get(i).arena.entrar.release();
        }

        for(int i=0; i<participantes.size(); i++){
            System.out.println(participantes.get(i).getName()+" tiene estos puntos: "+participantes.get(i).puntos);
        }
    }


}
