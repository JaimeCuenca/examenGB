public class Main {
    public static void main(String[] args) {
        Arena arena = new Arena();
        for (int i=1; i<=15; i++){
            Jugador j = new Jugador(arena);
            j.setName("jugador "+i);
            j.start();
        }
    }
}
