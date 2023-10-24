package testng;

import org.junit.Assert;

public class TestNGTest1 {
    public int add(int x,int y){
        return x+y;
    }

    public int sub(int x,int y){
        if(x==0){
          return 0;
        }else{
            return x-y;
        }

    }

//    @org.testng.annotations.Test
    public void testAdd1() {

    }

//    @org.testng.annotations.Test
    public void testSub1() {
        Assert.assertEquals(0,sub(0,1));

    }
}
