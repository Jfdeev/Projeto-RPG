
public class PilhaEncadeada<T> {
  private class Node {
    T dado;
    Node proximo;

    Node(T dado) {
      this.dado = dado;
      this.proximo = null;
    }
  }

  private Node topo;
  private int tamanho;

  public PilhaEncadeada() {
    topo = null;
    tamanho = 0;
  }

  public void push(T dado) {
    Node novoNode = new Node(dado);
    novoNode.proximo = topo;
    topo = novoNode;
    tamanho++;
  }

  public T pop() {
    if (estaVazia()) {
      throw new IllegalStateException("Pilha vazia");
    }
    T dado = topo.dado;
    topo = topo.proximo;
    tamanho--;
    return dado;
  }

  public T peek() {
    if (estaVazia()) {
      throw new IllegalStateException("Pilha vazia");
    }
    return topo.dado;
  }

  public boolean estaVazia() {
    return tamanho == 0;
  }

  public int tamanho() {
    return tamanho;
  }
}