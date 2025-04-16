public class ListaEncadeada<T> {
  private class Node {
    T data;
    Node next;

    Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  private Node head;
  private int tamanho;

  public ListaEncadeada() {
    head = null;
    tamanho = 0;
  }

  public void adicionar(T data) {
    Node novoNode = new Node(data);
    if (head == null) {
      head = novoNode;
    } else {
      Node atual = head;
      while (atual.next != null) {
        atual = atual.next;
      }
      atual.next = novoNode;
    }
    tamanho++;
  }

  public void remover() {
    if (head == null) {
      throw new IllegalStateException("Lista vazia");
    }
    head = head.next;
    tamanho--;
  }

  public T obter(int indice) {
    if (indice < 0 || indice >= tamanho) {
      throw new IndexOutOfBoundsException("Índice inválido: " + indice);
    }
    Node atual = head;
    for (int i = 0; i < indice; i++) {
      atual = atual.next;
    }
    return atual.data;
  }

  public int tamanho() {
    return tamanho;
  }

  public boolean estaVazia() {
    return head == null;
  }
}