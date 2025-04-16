public class Arena {
  private int idBatalha;
  private ListaEncadeada<Personagem> participantes;
  private FilaEncadeada<Personagem> filaTurnos;
  private PilhaEncadeada<Personagem> colocacao;
  private int turnoAtual;
  private String estadoBatalha;
  private Personagem vencedor;

  public Arena(int idBatalha) {
    this.idBatalha = idBatalha;
    this.participantes = new ListaEncadeada<>();
    this.filaTurnos = new FilaEncadeada<>();
    this.colocacao = new PilhaEncadeada<>();
    this.turnoAtual = 0;
    this.estadoBatalha = "Inicial";
    this.vencedor = null;
  }

  public void adicionarParticipante(Personagem p) {
    participantes.adicionar(p);
  }

  public void iniciarBatalha() {
    if (participantes.tamanho() < 2) {
      throw new IllegalStateException("Precisa de pelo menos 2 participantes");
    }
    estadoBatalha = "EmAndamento";
    for (int i = 0; i < participantes.tamanho(); i++) {
      filaTurnos.enqueue(participantes.obter(i));
    }
  }

  public void executarTurno(Personagem jogadorAtivo, int idHabilidade, Personagem alvo) {
    if (!estadoBatalha.equals("EmAndamento")) {
      return;
    }
    turnoAtual++;
    Personagem atual = filaTurnos.dequeue();
    if (atual.estaVivo()) {
      if (jogadorAtivo == atual && alvo != null) {
        atual.usarHabilidade(idHabilidade, alvo);
        System.out.println(atual.getNome() + " usa habilidade em " + alvo.getNome() + "! Vida de " + alvo.getNome()
            + ": " + alvo.getVidaAtual());
      } else {
        // Ação automática para não-jogadores
        for (int i = 0; i < participantes.tamanho(); i++) {
          Personagem alvoAuto = participantes.obter(i);
          if (alvoAuto.estaVivo() && alvoAuto != atual) {
            atual.usarHabilidade(1, alvoAuto);
            System.out.println(atual.getNome() + " ataca " + alvoAuto.getNome() + "! Vida de " + alvoAuto.getNome()
                + ": " + alvoAuto.getVidaAtual());
            break;
          }
        }
      }
      if (atual.estaVivo()) {
        filaTurnos.enqueue(atual);
      } else {
        colocacao.push(atual);
        System.out.println(atual.getNome() + " foi derrotado!");
      }
    }
    verificarVencedor();
  }

  public void verificarVencedor() {
    int vivos = 0;
    Personagem ultimoVivo = null;
    for (int i = 0; i < participantes.tamanho(); i++) {
      if (participantes.obter(i).estaVivo()) {
        vivos++;
        ultimoVivo = participantes.obter(i);
      }
    }
    if (vivos <= 1) {
      estadoBatalha = "Finalizada";
      if (vivos == 1) {
        vencedor = ultimoVivo;
        colocacao.push(vencedor);
      }
    }
  }

  public void exibirRanking() {
    System.out.println("\nRanking Final:");
    PilhaEncadeada<Personagem> temp = new PilhaEncadeada<>();
    int pos = 1;
    while (!colocacao.estaVazia()) {
      Personagem p = colocacao.pop();
      System.out.println(pos + ". " + p.getNome() + (p == vencedor ? " (Vencedor)" : ""));
      temp.push(p);
      pos++;
    }
    // Restaurar pilha
    while (!temp.estaVazia()) {
      colocacao.push(temp.pop());
    }
  }

  public ListaEncadeada<Personagem> getParticipantes() {
    return participantes;
  }

  public Personagem getVencedor() {
    return vencedor;
  }

  public String getEstadoBatalha() {
    return estadoBatalha;
  }

  public int getTurnoAtual() {
    return turnoAtual;
  }

  public int getIdBatalha() {
    return idBatalha;
  }

  public void setIdBatalha(int idBatalha) {
    this.idBatalha = idBatalha;
  }

  public Personagem peekProximoTurno() {
    if (filaTurnos.estaVazia()) {
      return null;
    }
    return filaTurnos.peek(); // usa o novo método peek()
  }
}