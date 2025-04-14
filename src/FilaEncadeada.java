
public class FilaEncadeada<T> {
  private class Node {
    T dado;
    Node proximo;

    Node(T dado) {
      this.dado = dado;
      this.proximo = null;
    }
  }

  private Node inicio;
  private Node fim;
  private int tamanho;

  public FilaEncadeada() {
    inicio = null;
    fim = null;
    tamanho = 0;
  }

  public void enqueue(T dado) {
    Node novoNode = new Node(dado);
    if (estaVazia()) {
      inicio = novoNode;
      fim = novoNode;
    } else {
      fim.proximo = novoNode;
      fim = novoNode;
    }
    tamanho++;
  }

  public T dequeue() {
    if (estaVazia()) {
      throw new IllegalStateException("Fila vazia");
    }
    T dado = inicio.dado;
    inicio = inicio.proximo;
    tamanho--;
    if (estaVazia()) {
      fim = null;
    }
    return dado;
  }

  public boolean estaVazia() {
    return tamanho == 0;
  }

  public int tamanho() {
    return tamanho;
  }
}