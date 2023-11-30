package floresta;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Floresta {
    private HashSet<Arvore> arvores;
    private HashSet<Coelho> coelhos;
    private HashSet<Lobo> lobos;

    public Floresta() {
        System.out.println("| ---------------- Floresta!---------------- |");
        

        arvores = new HashSet<>();
        coelhos = new HashSet<>();
        lobos = new HashSet<>();

        Scanner scanner = new Scanner(System.in);

        if (obterResposta(scanner, "| Colocar Arvore?                                         |")) {
            Arvore arvore = new Arvore();
            arvores.add(arvore);
        }
        if (obterResposta(scanner, "| Colocar Coelho?                                         |")) {
            Coelho coelho = new Coelho();
            coelhos.add(coelho);
        }
        if (obterResposta(scanner, "| Colocar Lobo?                                           |")) {
            Lobo lobo = new Lobo();
            lobos.add(lobo);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                atualizarFloresta();
            }
        }, 0, 3 * 1000);
    }

    public void atualizarFloresta() {
        System.out.println("|--------------------- Floresta ------------------------|");
        boolean coelhosVivos = false;
        boolean lobosVivos = false;
        boolean arvoresVivas = false;

        for (Arvore arvore : arvores) {
            if (arvore.getEstagio().equals("morta")) {
                continue;
            }
            arvore.envelhecer(); 
            if (!arvore.getEstagio().equals("morta")) {
                arvoresVivas = true; 
                System.out.println("Arvore: " + arvore.getEstagio());
            }
        }

        for (Coelho coelho : coelhos) {
            if (coelho.estaVivo()) {
                System.out.println("Coelho: " + coelho.getStatus());
                coelhosVivos = true;
                for (Lobo lobo : lobos) {
                    if (lobo.estaVivo()) {
                        coelho.fugir(lobo);
                        if (!coelho.estaVivo()) {
                            System.out.println("Coelho correu do Lobo.");
                        } else {
                            System.out.println("Coelho foi pego pelo Lobo.");
                        }
                    }
                }
            } else {
                System.out.println("Coelho: Morto");
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.estaVivo()) {
                lobo.getVida();
                System.out.println("Lobo: " + lobo.getStatus());
                lobosVivos = true;

                for (Coelho coelho : coelhos) {
                    if (coelho.estaVivo()) {
                        lobo.cacarCoelho(coelho);
                        if (!coelho.estaVivo()) {
                            System.out.println("Lobo não conseguiu pegar o Coelho.");
                        } else {
                            System.out.println("Lobo pegou o Coelho e comeu");
                        }
                    }
                }
            } else {
                System.out.println("Lobo: Morto");
            }
        }

        if (!coelhosVivos && !lobosVivos) {
            System.out.println("| coelhos e lobos estão mortos. A simulação está finalizada. |");
            System.exit(0);
        }
        if (!arvoresVivas) {
            System.out.println("| árvores estão mortas. A simulação está finalizada.          |");
            System.exit(0);
        }
        System.out.println("|-------------------------------------------------------|");
    }

    private boolean obterResposta(Scanner scanner, String pergunta) {
        System.out.println(pergunta);
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("y");
    }

    public static void main(String[] args) {
        new Floresta();
    }
}
    

