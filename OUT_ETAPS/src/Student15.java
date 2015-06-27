    class Student15{  
        int id;  
        String name;  
        Student15(){System.out.println("default constructor is invoked");}  
          
        Student15(int id,String name){  
        this ();//must be the first statement  
        this.id = id;  
        this.name = name;  
        }  
        void display(){System.out.println(id+" "+name);}  
          
        public static void main(String args[]){  
        Student15 e1 = new Student15(111,"karan");  
        Student15 e2 = new Student15(222,"Aryan");  
        e1.display();  
        e2.display();  
       }  
    }  