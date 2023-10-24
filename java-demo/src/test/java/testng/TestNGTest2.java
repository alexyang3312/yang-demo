package testng;

public class TestNGTest2 {
    //使用参数化数据
//    @Test(dataProvider = "Parameterized")
    public void parameterIntTest(Class clzz, String[] number) {
        System.out.println("number[0] = " + number[0]+" ,number[1] = "+number[1]);
    }

    //提供参数化数据
//    @DataProvider(name = "Parameterized")
    public Object[][] parameterIntTestProvider() {
        return new Object[][]{
                {String.class, new String[] {"a", "b"}},
                {Integer.class, new String[] {"3", "4"}}
        };
    }
}
