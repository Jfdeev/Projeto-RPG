public class Personagem {
  private int idPersonagem;
  private String nome;
  private int nivel;
  private int vidaMaxima;
  private int vidaAtual;
  private int manaMaxima;
  private int manaAtual;
  private int danoBase;
  private ListaEncadeada<Habilidade> habilidades;

  public Personagem(int idPersonagem, String nome, int nivel, int vidaMaxima, int manaMaxima) {
    this.idPersonagem = idPersonagem;
    this.nome = nome;
    this.nivel = nivel;
    this.vidaMaxima = vidaMaxima;
    this.vidaAtual = vidaMaxima;
    this.manaMaxima = manaMaxima;
    this.manaAtual = manaMaxima;
    this.danoBase = 10;
    this.habilidades = new ListaEncadeada<>();
    habilidades.adicionar(new Habilidade(1, "Ataque Básico", 0, 0));
  }

  public void receberDano(int valor) {
    vidaAtual = Math.max(0, vidaAtual - valor);
  }

  public void curar(int valor) {
    vidaAtual = Math.min(vidaMaxima, vidaAtual + valor);
  }

  public boolean estaVivo() {
    return vidaAtual > 0;
  }

  public void printHabilidades() {
    for (int i = 0; i < habilidades.tamanho(); i++) {
      Habilidade h = habilidades.obter(i);
      System.out.print((i + 1) + ") " + h.getNome() + " ");
    }
  }

  public void usarHabilidade(int idHabilidade, Personagem alvo) {
    Habilidade h = null;
    for (int i = 0; i < habilidades.tamanho(); i++) {
      if (habilidades.obter(i).getId() == idHabilidade) {
        h = habilidades.obter(i);
        break;
      }
    }
    if (h != null && manaAtual >= h.getCustoMana()) {
      int dano = (idHabilidade == 1) ? danoBase : h.getDano();
      alvo.receberDano(dano);
      manaAtual -= h.getCustoMana();
    }
  }

  public void subirNivel() {
    nivel++;
    vidaMaxima += 10;
    manaMaxima += 5;
    danoBase += 5;
    resetarEstado();
  }

  public void resetarEstado() {
    vidaAtual = vidaMaxima;
    manaAtual = manaMaxima;
  }

  public String getNome() {
    return nome;
  }

  public int getVidaAtual() {
    return vidaAtual;
  }

  public int getManaAtual() {
    return manaAtual;
  }

  public ListaEncadeada<Habilidade> getHabilidades() {
    return habilidades;
  }

  public int getIdPersonagem() {
    return idPersonagem;
  }

  public void setIdPersonagem(int idPersonagem) {
    this.idPersonagem = idPersonagem;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getNivel() {
    return nivel;
  }

  public void setNivel(int nivel) {
    this.nivel = nivel;
  }

  public int getVidaMaxima() {
    return vidaMaxima;
  }

  public void setVidaMaxima(int vidaMaxima) {
    this.vidaMaxima = vidaMaxima;
  }

  public void setVidaAtual(int vidaAtual) {
    this.vidaAtual = vidaAtual;
  }

  public int getManaMaxima() {
    return manaMaxima;
  }

  public void setManaMaxima(int manaMaxima) {
    this.manaMaxima = manaMaxima;
  }

  public void setManaAtual(int manaAtual) {
    this.manaAtual = manaAtual;
  }

  public void setHabilidades(ListaEncadeada<Habilidade> habilidades) {
    this.habilidades = habilidades;
  }

  public int getDanoBase() {
    return danoBase;
  }

  public void setDanoBase(int danoBase) {
    this.danoBase = danoBase;
  }

}