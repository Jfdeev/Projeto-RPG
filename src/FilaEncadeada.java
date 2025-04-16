public class FilaEncadeada<T> {
  private class Node {
    T data;
    Node next;

    Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  private Node head;
  private Node tail;
  private int tamanho;

  public FilaEncadeada() {
    head = null;
    tail = null;
    tamanho = 0;
  }

  public void enqueue(T data) {
    Node novoNode = new Node(data);
    if (estaVazia()) {
      head = novoNode;
      tail = novoNode;
    } else {
      tail.next = novoNode;
      tail = novoNode;
    }
    tamanho++;
  }

  public T dequeue() {
    if (estaVazia()) {
      throw new IllegalStateException("Fila vazia");
    }
    T temp = head.data;
    head = head.next;
    tamanho--;
    if (estaVazia()) {
      tail = null;
    }
    return temp;
  }

  public boolean estaVazia() {
    return head == null;
  }

  public int tamanho() {
    return tamanho;
  }

  public T peek() {
    if (estaVazia()) {
      throw new IllegalStateException("Fila vazia");
    }
    return head.data;
  }
}