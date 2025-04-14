public class Jogador {
  private int idJogador;
  private String nome;
  private String senha;
  private int saldoMoedas;
  private ListaEncadeada<Personagem> personagens;

  public Jogador(int idJogador, String nome, String senha) {
    this.idJogador = idJogador;
    this.nome = nome;
    this.senha = senha;
    this.saldoMoedas = 100;
    this.personagens = new ListaEncadeada<>();
  }

  public boolean autenticar(String senha) {
    return this.senha.equals(senha);
  }

  public void criarPersonagem(String nome) {
    Personagem p = new Personagem(personagens.tamanho() + 1, nome, 1, 100, 50);
    personagens.adicionar(p);
  }

  public Personagem selecionarPersonagem(int indice) {
    return personagens.obter(indice);
  }

  public String getNome() {
    return nome;
  }

  public ListaEncadeada<Personagem> getPersonagens() {
    return personagens;
  }

  public int getSaldoMoedas() {
    return saldoMoedas;
  }

  public void adicionarMoedas(int valor) {
    saldoMoedas += valor;
  }
}