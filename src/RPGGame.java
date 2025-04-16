import java.util.Scanner;

public class RPGGame {
  private ListaEncadeada<Jogador> jogadores;
  private Jogador jogadorAtual;
  private Arena arenaAtual;
  private Scanner scanner;
  private int proximoIdJogador;
  private int proximoIdBatalha;
  private Personagem personagemJogadorAtivo;
  private static ListaEncadeada<Habilidade> lojaHabilidades = new ListaEncadeada<>();
  static {
    lojaHabilidades.adicionar(new Habilidade(1, "Bola de Fogo", 20, 50));
    lojaHabilidades.adicionar(new Habilidade(2, "Gelo Cortante", 15, 40));
    lojaHabilidades.adicionar(new Habilidade(3, "Raio Arcano", 30, 70));
  }

  public RPGGame() {
    jogadores = new ListaEncadeada<>();
    jogadorAtual = null;
    arenaAtual = null;
    scanner = new Scanner(System.in);
    proximoIdJogador = 1;
    proximoIdBatalha = 1;
    Jogador bot = new Jogador(proximoIdJogador++, "Bot", "bot");
    bot.criarPersonagem("Monstro1");
    bot.criarPersonagem("Monstro2");
    jogadores.adicionar(bot);
  }

  public void executar() {
    while (true) {
      exibirTelaLoginCadastro();
    }
  }

  private void exibirTelaLoginCadastro() {
    System.out.println("\n=== RPG Turn-Based ===");
    System.out.println("1. Login");
    System.out.println("2. Cadastrar");
    System.out.println("3. Sair");
    System.out.print("Escolha uma opção: ");
    int opcao = lerInteiro();

    switch (opcao) {
      case 1:
        login();
        break;
      case 2:
        cadastrar();
        break;
      case 3:
        System.out.println("Saindo...");
        System.exit(0);
      default:
        System.out.println("Opção inválida!");
    }
  }

  private void login() {
    System.out.print("Digite o nome: ");
    String nome = scanner.nextLine();
    System.out.print("Digite a senha: ");
    String senha = scanner.nextLine();

    for (int i = 0; i < jogadores.tamanho(); i++) {
      Jogador j = jogadores.obter(i);
      if (j.getNome().equals(nome) && j.autenticar(senha)) {
        jogadorAtual = j;
        exibirTelaPrincipal();
        return;
      }
    }
    System.out.println("Nome ou senha incorretos!");
  }

  private void cadastrar() {
    System.out.print("Digite o nome: ");
    String nome = scanner.nextLine();
    for (int i = 0; i < jogadores.tamanho(); i++) {
      if (jogadores.obter(i).getNome().equals(nome)) {
        System.out.println("Nome já em uso!");
        return;
      }
    }
    System.out.print("Digite a senha: ");
    String senha = scanner.nextLine();
    Jogador novo = new Jogador(proximoIdJogador++, nome, senha);
    jogadores.adicionar(novo);
    System.out.println("Cadastro realizado com sucesso!");
  }

  private void exibirTelaPrincipal() {
    while (true) {
      System.out.println("\n=== Menu Principal - Jogador: " + jogadorAtual.getNome() + " ===");
      System.out.println("Moedas: " + jogadorAtual.getSaldoMoedas());
      System.out.println("1. Ver Personagens");
      System.out.println("2. Criar Personagem");
      System.out.println("3. Iniciar Batalha (PvP)");
      System.out.println("4. Iniciar Batalha (PvE)");
      System.out.println("5. Consultar Moedas");
      System.out.println("6. Loja de Habilidades");
      System.out.println("7. Sair");
      System.out.print("Escolha uma opção: ");

      int opcao = lerInteiro();

      switch (opcao) {
        case 1:
          verPersonagens();
          break;
        case 2:
          criarPersonagem();
          break;
        case 3:
          iniciarBatalha(true);
          break;
        case 4:
          iniciarBatalha(false);
          break;
        case 5:
          exibirSaldoMoedas();
          break;
        case 6:
          exibirLojaHabilidades();
          break;
        case 7:
          jogadorAtual = null;
          return;
        default:
          System.out.println("Opcao inválida!");
      }
    }
  }

  private void verPersonagens() {
    ListaEncadeada<Personagem> personagens = jogadorAtual.getPersonagens();
    if (personagens.estaVazia()) {
      System.out.println("Nenhum personagem criado!");
      return;
    }
    System.out.println("\nSeus Personagens:");
    for (int i = 0; i < personagens.tamanho(); i++) {
      Personagem p = personagens.obter(i);
      System.out.println((i + 1) + ". " + p.getNome() + " - Nível: " + p.getNivel() +
          ", Vida: " + p.getVidaAtual() + "/" + p.getVidaMaxima() +
          ", Mana: " + p.getManaAtual() + "/" + p.getManaMaxima() +
          ", Dano Base: " + p.getDanoBase());
    }
  }

  private void criarPersonagem() {
    System.out.print("Digite o nome do personagem: ");
    String nome = scanner.nextLine();
    jogadorAtual.criarPersonagem(nome);
    System.out.println("Personagem criado com sucesso!");
  }

  private void iniciarBatalha(boolean isPvP) {
    ListaEncadeada<Personagem> personagens = jogadorAtual.getPersonagens();
    if (personagens.estaVazia()) {
      System.out.println("Crie um personagem primeiro!");
      return;
    }

    verPersonagens();
    System.out.print("Escolha seu personagem (número): ");
    int indice = lerInteiro() - 1;
    if (indice < 0 || indice >= personagens.tamanho()) {
      System.out.println("Personagem inválido!");
      return;
    }
    personagemJogadorAtivo = jogadorAtual.selecionarPersonagem(indice);
    personagemJogadorAtivo.resetarEstado();

    arenaAtual = new Arena(proximoIdBatalha++);
    arenaAtual.adicionarParticipante(personagemJogadorAtivo);

    if (isPvP) {
      Jogador bot = null;
      for (int i = 0; i < jogadores.tamanho(); i++) {
        if (!jogadores.obter(i).getNome().equals(jogadorAtual.getNome())) {
          bot = jogadores.obter(i);
          break;
        }
      }
      if (bot != null && !bot.getPersonagens().estaVazia()) {
        ListaEncadeada<Personagem> botPersonagens = bot.getPersonagens();
        System.out.println("\nPersonagens do oponente (Bot):");
        for (int i = 0; i < botPersonagens.tamanho(); i++) {
          Personagem p = botPersonagens.obter(i);
          System.out.println((i + 1) + ". " + p.getNome() + " - Vida: " + p.getVidaAtual());
        }
        System.out.print("Escolha o oponente (número): ");
        int botIndice = lerInteiro() - 1;
        Personagem botPersonagem;
        if (botIndice >= 0 && botIndice < botPersonagens.tamanho()) {
          botPersonagem = botPersonagens.obter(botIndice);
        } else {
          System.out.println("Oponente inválido! Usando primeiro personagem.");
          botPersonagem = botPersonagens.obter(0);
        }
        botPersonagem.resetarEstado();
        arenaAtual.adicionarParticipante(botPersonagem);
      } else {
        System.out.println("Nenhum oponente disponível!");
        arenaAtual = null;
        return;
      }
    } else {
      Personagem monstro = new Personagem(999, "Monstro", 1, 80, 30);
      monstro.setDanoBase(10 + (personagemJogadorAtivo.getNivel() - 1) * 5); // Exemplo: 5 de dano por nível
      arenaAtual.adicionarParticipante(monstro);
    }

    exibirTelaBatalha();
  }

  private void exibirTelaBatalha() {
    arenaAtual.iniciarBatalha();
    while (arenaAtual.getEstadoBatalha().equals("EmAndamento")) {
      System.out.println("\n=== Batalha - Turno " + (arenaAtual.getTurnoAtual() + 1) + " ===");
      ListaEncadeada<Personagem> participantes = arenaAtual.getParticipantes();
      for (int i = 0; i < participantes.tamanho(); i++) {
        Personagem p = participantes.obter(i);
        System.out.println("- " + p.getNome() + ": Vida " + p.getVidaAtual() + "/" + p.getVidaMaxima() +
            ", Mana " + p.getManaAtual() + "/" + p.getManaMaxima() +
            ", Dano Base: " + p.getDanoBase());
      }
      Personagem atual = arenaAtual.peekProximoTurno();
      if (atual == null) {
        break;
      }
      System.out.println("Próximo a agir: " + atual.getNome());

      if (atual == personagemJogadorAtivo) {
        // Turno do jogador
        System.out.println("\nAções:");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade");
        System.out.print("Escolha uma ação: ");
        int acao = lerInteiro();

        Personagem alvo = null;
        if (acao == 1 || acao == 2) {
          System.out.println("\nEscolha o alvo:");
          ListaEncadeada<Personagem> alvosVivos = new ListaEncadeada<>();
          int count = 1;
          for (int i = 0; i < participantes.tamanho(); i++) {
            Personagem p = participantes.obter(i);
            if (p.estaVivo() && p != atual) {
              System.out.println(count + ". " + p.getNome() + " (Vida: " + p.getVidaAtual() + ")");
              alvosVivos.adicionar(p);
              count++;
            }
          }
          if (alvosVivos.estaVazia()) {
            System.out.println("Nenhum alvo disponível! Batalha encerrada.");
            arenaAtual.verificarVencedor();
            break;
          }
          System.out.print("Digite o número: ");
          int alvoIndice = lerInteiro() - 1;
          if (alvoIndice >= 0 && alvoIndice < alvosVivos.tamanho()) {
            alvo = alvosVivos.obter(alvoIndice);
          } else {
            System.out.println("Alvo inválido! Ação cancelada.");
            arenaAtual.executarTurno(null, 1, null);
            continue;
          }
        }

        if (acao == 1) {
          arenaAtual.executarTurno(atual, 1, alvo);
        } else if (acao == 2) {
          ListaEncadeada<Habilidade> habilidades = atual.getHabilidades();
          System.out.println("\nHabilidades:");
          for (int i = 0; i < habilidades.tamanho(); i++) {
            Habilidade h = habilidades.obter(i);
            System.out
                .println((i + 1) + ". " + h.getNome() + " (Mana: " + h.getCustoMana() + ", Dano: " + h.getDano() + ")");
          }
          System.out.print("Escolha a habilidade (número): ");
          int habIndice = lerInteiro() - 1;
          if (habIndice >= 0 && habIndice < habilidades.tamanho()) {
            arenaAtual.executarTurno(atual, habilidades.obter(habIndice).getId(), alvo);
          } else {
            System.out.println("Habilidade inválida! Ação cancelada.");
            arenaAtual.executarTurno(null, 1, null);
          }
        } else {
          System.out.println("Ação inválida! Turno perdido.");
          arenaAtual.executarTurno(null, 1, null);
        }
      } else {
        // Turno automático
        Personagem alvo = null;
        for (int i = 0; i < participantes.tamanho(); i++) {
          Personagem p = participantes.obter(i);
          if (p.estaVivo() && p != atual) {
            alvo = p;
            break;
          }
        }
        arenaAtual.executarTurno(null, 1, alvo);
      }
    }
    exibirTelaResultado();
  }

  private void exibirTelaResultado() {
    System.out.println("\n=== Batalha Finalizada ===");
    if (arenaAtual.getVencedor() != null) {
      System.out.println("Vencedor: " + arenaAtual.getVencedor().getNome());
      if (arenaAtual.getVencedor() == personagemJogadorAtivo) {
        jogadorAtual.adicionarMoedas(50);
        personagemJogadorAtivo.subirNivel();
        System.out.println("Você ganhou 50 moedas e subiu de nível!");
      }
    } else {
      System.out.println("Empate!");
    }
    arenaAtual.exibirRanking();
    ListaEncadeada<Personagem> participantes = arenaAtual.getParticipantes();
    for (int i = 0; i < participantes.tamanho(); i++) {
      participantes.obter(i).resetarEstado();
    }
    System.out.println("\n1. Nova Batalha");
    System.out.println("2. Voltar ao Menu");
    System.out.print("Escolha uma opção: ");
    int opcao = lerInteiro();
    arenaAtual = null;
    personagemJogadorAtivo = null;
    if (opcao == 1) {
      iniciarBatalha(true);
    }
    return;
  }

  private void exibirLojaHabilidades() {
    System.out.println("\n=== Loja de Habilidades ===");
    System.out.println("Suas moedas: " + jogadorAtual.getSaldoMoedas());
    for (int i = 0; i < lojaHabilidades.tamanho(); i++) {
      Habilidade h = lojaHabilidades.obter(i);
      System.out.printf("%d. %s (Preço: %d moedas — Mana: %d, Dano: %d)%n",
          i + 1, h.getNome(), h.getCustoMana() * 2, h.getCustoMana(), h.getDano());
    }
    System.out.println((lojaHabilidades.tamanho() + 1) + ". Voltar");
    System.out.print("Escolha o que deseja comprar: ");
    int escolha = lerInteiro() - 1;

    if (escolha >= 0 && escolha < lojaHabilidades.tamanho()) {
      Habilidade comprada = lojaHabilidades.obter(escolha);
      int preco = comprada.getCustoMana() * 2;
      if (jogadorAtual.gastarMoedas(preco)) {
        ListaEncadeada<Personagem> chars = jogadorAtual.getPersonagens();
        if (chars.estaVazia()) {
          System.out.println("Nenhum personagem para equipar! Compra cancelada.");
          jogadorAtual.adicionarMoedas(preco);
        } else {
          System.out.println("Em qual personagem equipar?");
          for (int i = 0; i < chars.tamanho(); i++) {
            System.out.println((i + 1) + ". " + chars.obter(i).getNome());
          }
          int idx = lerInteiro() - 1;
          if (idx >= 0 && idx < chars.tamanho()) {
            chars.obter(idx).getHabilidades().adicionar(comprada);
            System.out.println("Habilidade \"" + comprada.getNome() + "\" equipada em " +
                chars.obter(idx).getNome() + "!");
          } else {
            System.out.println("Personagem inválido! Compra cancelada.");
            jogadorAtual.adicionarMoedas(preco);
          }
        }
      } else {
        System.out.println("Moedas insuficientes!");
      }
    }
  }

  private void exibirSaldoMoedas() {
    System.out.println("\n=== Seu Saldo de Moedas ===");
    System.out.println("Você tem " + jogadorAtual.getSaldoMoedas() + " moedas.\n");
  }

  private int lerInteiro() {
    try {
      int valor = Integer.parseInt(scanner.nextLine());
      return valor;
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public static void main(String[] args) {
    RPGGame jogo = new RPGGame();
    jogo.executar();
  }
}