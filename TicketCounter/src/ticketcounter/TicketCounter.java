package ticketcounter;
import java.util.*;

public class TicketCounter {
    public static void main(String[] args) {
        int averageTime, totalTime;
        int[] cashierTime = new int[10];
        Customer customer;
        Queue<Customer> queue = new LinkedList<>();
        for(int cashiers = 1; cashiers <= 10; cashiers++){
            totalTime = 0;
            for (int i=0;i<100;i++){
                queue.add(new Customer(i*15));
            }
            for (int i=0;i<cashiers;i++){
                cashierTime[i] = 0;
            }
            while (!queue.isEmpty()){   
                for(int count=0; count<cashiers; count++){
                    if (queue.isEmpty()) break;
                    customer = queue.remove();                   
                    if(customer.getArriveTime() >= cashierTime[count])
                        customer.setDepartTime(customer.getArriveTime() +120);
                    else 
                        customer.setDepartTime(cashierTime[count] +120);
                    cashierTime[count] = customer.getDepartTime();
                    totalTime += customer.totalTime();
                }                               
            }
            averageTime = totalTime/100;
            System.out.println("The average waiting time while having " + cashiers + " cashiers is " + averageTime + " seconds");
        }            
    }
    
}

class Customer {
    int arriveTime, departTime;

    Customer (int arriveTime) {
        this.arriveTime = arriveTime;
    }

    int getArriveTime () {
        return arriveTime;
    }

    int getDepartTime () {
        return departTime;
    }

    void setDepartTime (int departTime) {
        this.departTime = departTime;
    }

    int totalTime () {
        return departTime - arriveTime;
    }
}