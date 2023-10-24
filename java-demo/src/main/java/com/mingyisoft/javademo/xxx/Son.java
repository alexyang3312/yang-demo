package com.mingyisoft.javademo.xxx;

 class Son extends Father{
    String name = "tom";
    public void eat(){
         System.out.print("soneat,");
    }
   
    public static void main(String[] args) {
        String a = "123";
        String b = "123";
        System.out.println(a == b);






//        int a = 0b11101001;//输出233
//        int b = 0B00000001;//输出1
//        int c = 0b11111111_11111111_11111111_11111011;//输出2

        System.out.println(a);
        System.out.println(b);
//        System.out.println(c);

        Son s = new Son();
        Father f = new Son();
        s.eat();
        System.out.print(s.name+".");
        f.eat();
        System.out.print(f.name+".");

        Thread zz = null;
    }
}

class Father{
    String name = "jack";
    public void eat(){
         System.out.print("fathereat,");
    }
}
