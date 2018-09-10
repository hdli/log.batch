package log.batch.main;


public class TestInstanceof {

	public static void main(String[] args) {
		//instanceofTest(new Student());
		Person student=new Student();
		System.out.println(student instanceof Person);
	}
	
	public static void instanceofTest(Person p){
	   if (p instanceof Postgraduate) System.out.println("p是类Postgraduate的实例");    
	   if (p instanceof Person) System.out.println("p是类Person的实例");    
	   if (p instanceof Student) System.out.println("p是类Student的实例");    
	   if (p instanceof Object) System.out.println("p是类Object的实例");
	}

}

interface  Person{
}

class Student implements Person{
}

class Postgraduate  extends Student{
}

class Animal {    
} 
