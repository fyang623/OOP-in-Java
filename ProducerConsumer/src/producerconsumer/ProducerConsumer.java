package producerconsumer;

public class ProducerConsumer {

    public static void main(String[] args) {
        Basket b = new Basket();
        Producer p1 = new Producer(b);
        Producer p2 = new Producer(b);
        Consumer c1 = new Consumer(b);
        Consumer c2 = new Consumer(b);
        Thread tp1 = new Thread(p1);
        Thread tp2 = new Thread(p2);
        Thread tc1 = new Thread(c1);
        Thread tc2 = new Thread(c2);
        tp1.start();
        tp2.start();
        tc1.start();
        tc2.start();
    }
}

class Bread {
    int id;
    
    Bread(int id) {
        this.id = id;
    }
}

class Basket {
    int index = 0, totalCount = 0;
    Bread[] array = new Bread[20];
    
    public synchronized void increase() {        
        while (index == array.length) { 
            try {   
                this.wait();              
            } 
            catch (InterruptedException e) {}
            if(totalCount == 60)  return;
        }  
        this.notifyAll();
        array[index] = new Bread(index);
        System.out.println("A new bread is placed at index " + index);
        index++;
        totalCount++;    
    }

    public synchronized void decrease() {
        while (index == 0) {
            try {
                this.wait();
            } 
            catch (InterruptedException e) {}
        }
        this.notifyAll();
        array[--index] = null;
        System.out.println("The bread at index " + index + " is consumed");
    }
}

class Consumer implements Runnable {

    Basket b = null;
    int count = 0;

    Consumer(Basket b) {
        this.b = b;
    }

    public void run() {
        consume();
    }

    public void consume() {
        while (count != 20) {
            b.decrease();
            count++;
            try {
                Thread.sleep(200);
            } 
            catch (InterruptedException e) {}
        }
        System.out.println(this + " is full");
        return;
    }
}

class Producer implements Runnable {
    Basket b = null;

    Producer(Basket b) {
        this.b = b;
    }

    public void run() {
        produce();
    }

    public void produce() {
        while (b.totalCount < 60) {               
            b.increase();               
            try {
                Thread.sleep(20);
            } 
            catch (InterruptedException e) {}
        }        
        System.out.println("I am done.");
        return;
    }
}
