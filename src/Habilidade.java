public class Habilidade {
  private int id;
  private String nome;
  private int custoMana;
  private int dano;

  public Habilidade(int id, String nome, int custoMana, int dano) {
    this.id = id;
    this.nome = nome;
    this.custoMana = custoMana;
    this.dano = dano;
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public int getCustoMana() {
    return custoMana;
  }

  public int getDano() {
    return dano;
  }
}