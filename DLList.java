public class DLList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList(){
        head = new Node<E>(null);
        tail = new Node<E>(null);
  
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
   
    }

    public boolean isEmpty(){
        if(head.next().equals(tail)){
            return true;
        }
        return false;
    }
  
    private Node<E> getNode(int index){
        Node<E> current;
        int i;
        current = head.next();
        for(i = 0; i < index; i++){
            current = current.next();
        }
       
        return current;
    }
   
  
    public void add(E data){
        Node<E> toAdd = new Node<E>(data);
        toAdd.setNext(tail);
        toAdd.setPrevious(tail.previous());
        tail.previous().setNext(toAdd);
        tail.setPrevious(toAdd);
        size++;
    }
    public void add(E data, int index){
        Node<E> current;
        Node<E> toAdd = new Node<E>(data);
        if(index >= size){
            add(data);
            return;
        }
        if(size/2>index){
            // add from beginning
            current = head.next();
            int i = 0;
            while(current != null){
                if(index == i){
                    Node<E> previousNode = current.previous();

  
                   
                    toAdd.setPrevious(previousNode);
                    toAdd.setNext(current);
                   
                    current.setPrevious(toAdd);
                    previousNode.setNext(toAdd);
                    size++;
                    break;
                }
                i++;
                current = current.next();
            }
  
        }else{
            // add from end
            current = tail.previous();
            int i = size-1;
            while(current != null){
                if(index == i){
                    Node<E> previousNode = current.previous();
                   
                    toAdd.setPrevious(previousNode);
                    toAdd.setNext(current);
                   
                    current.setPrevious(toAdd);
                    previousNode.setNext(toAdd);
                    size++;
                    break;
                }
                i--;
                current = current.previous();
            }
  
            current = tail.previous();
           
        }
    }
  
    public E get(int index){
        return getNode(index).get();
    }
  
    public int size(){
        return size;
    }
  
    public String toString(){
        Node<E> current = head.next();
        String returnString = "";
        while(current.next() != null){
            returnString = returnString + current.get().toString();
            current = current.next();
        }
        return returnString;
    }

    public String printString(){
        Node<E> current = head.next();
        String returnString = "";
        while(current.next() != null){
            returnString = returnString + current.get().toString() + " ";
            current = current.next();
        }
        return returnString;
    }
    
    public void remove(int index){
        Node<E> current;
        int i;
        if(size/2>index){
            i = 0;
            current = head.next();
            while(current!=null){
                if(i==index){
                    Node<E> previousNode = current.previous();
                    Node<E> nextNode = current.next();
                    previousNode.setNext(nextNode);
                    nextNode.setPrevious(previousNode);
                    size--;
                    break;
                }
                i++;
                current = current.next();
            }
        }else{
            i = size-1;
            current = tail.previous();
            while(current!=null){
                if(i==index){
                    Node<E> previousNode = current.previous();
                    Node<E> nextNode = current.next();
                    previousNode.setNext(nextNode);
                    nextNode.setPrevious(previousNode);
                    size--;
                    break;
                }
                i--;
                current = current.previous();
            }
  
  
        }
    }
    public void remove(E data){
        Node<E> current = new Node<E>(data);
        current = head.next();
        while(current != null){
            if(current.get().equals(data)){
                Node<E> previousNode = current.previous();
                Node<E> nextNode = current.next();
                previousNode.setNext(nextNode);
                nextNode.setPrevious(previousNode);
                size--;
                break;
            }
            current = current.next();
        }
    }
  
    public void set(int index, E data){
        this.remove(index);
        this.add(data,index);
    }

    public E pop(){
        E temp = get(0);
        this.remove(0);
        return temp;
    }

    public boolean contains(E data){
        Node<E> current = new Node<E>(data);
        current = head.next();
        while(current.next() != null){
            if(current.get().equals(data)){
                return true;
            }
            current = current.next();
        }
        return false;
    }
   
    public void clear(){
        head.setNext(tail);
        tail.setPrevious(head);
    }
 }
 