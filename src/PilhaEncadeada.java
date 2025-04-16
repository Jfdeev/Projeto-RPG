public class PilhaEncadeada<T> {
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

  public PilhaEncadeada() {
    head = null;
    tamanho = 0;
  }

  public void push(T data) {
    Node novoNode = new Node(data);
    novoNode.next = head;
    head = novoNode;
    tamanho++;
  }

  public T pop() {
    if (estaVazia()) {
      throw new IllegalStateException("Pilha vazia");
    }
    T temp = head.data;
    head = head.next;
    tamanho--;
    return temp;
  }

  public T peek() {
    if (estaVazia()) {
      throw new IllegalStateException("Pilha vazia");
    }
    return head.data;
  }

  public boolean estaVazia() {
    return head == null;
  }

  public int tamanho() {
    return tamanho;
  }
}