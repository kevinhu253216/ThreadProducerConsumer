
public class ProducerConsumer {
    public static void main(String[] args){
        SyncStack ss = new SyncStack();
        Comsumer c = new Comsumer(ss);
        Producer p = new Producer(ss);
        new Thread(p).start();
        new Thread(p).start();
        new Thread(c).start();
        new Thread(c).start();
    }
}

class WoTou{
    int id;
    WoTou(int id){
        this.id = id;
    }

    public String toString(){
        return "WoTou :" + id;
    }
}

class SyncStack{

    int index = 0;
    WoTou[] arrWT = new WoTou[6];


    public synchronized void push(WoTou wt)  {
        if(index == arrWT.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } // wait与notify一一对应
        this.notifyAll(); //叫醒一个正在wait在这个对象上的线程
        arrWT[index] = wt;
        index ++;
    }

    public synchronized WoTou pop(){
        if(index == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();
        index --;
        return arrWT[index];
    }
}

class  Producer implements Runnable{
    SyncStack ss = null;
    Producer(SyncStack ss){
        this.ss = ss;
    }

    public void run(){
        for(int i=0; i<20; i++){
            WoTou wt = new WoTou(i);
            ss.push(wt);
            System.out.println("Producer:"+wt);
            try{
                Thread.sleep((int) (Math.random()*1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}

class  Comsumer implements Runnable {
    SyncStack ss = null;

    Comsumer(SyncStack ss) {
        this.ss = ss;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            WoTou wt = ss.pop();
            System.out.println("Comsumer"+wt);
            try{
                Thread.sleep((int) (Math.random()*1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}