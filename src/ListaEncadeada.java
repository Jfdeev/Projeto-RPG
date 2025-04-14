
public class ListaEncadeada<T> {
  private class Node {
    T dado;
    Node proximo;

    Node(T dado) {
      this.dado = dado;
      this.proximo = null;
    }
  }

  private Node cabeca;
  private int tamanho;

  public ListaEncadeada() {
    cabeca = null;
    tamanho = 0;
  }

  public void adicionar(T dado) {
    Node novoNode = new Node(dado);
    if (cabeca == null) {
      cabeca = novoNode;
    } else {
      Node atual = cabeca;
      while (atual.proximo != null) {
        atual = atual.proximo;
      }
      atual.proximo = novoNode;
    }
    tamanho++;
  }

  public T obter(int indice) {
    if (indice < 0 || indice >= tamanho) {
      throw new IndexOutOfBoundsException("Índice inválido: " + indice);
    }
    Node atual = cabeca;
    for (int i = 0; i < indice; i++) {
      atual = atual.proximo;
    }
    return atual.dado;
  }

  public int tamanho() {
    return tamanho;
  }

  public boolean estaVazia() {
    return tamanho == 0;
  }
}