package cn.sisyphe.coffee.stock.utils;

/**
 * Created by heyong on 2018/1/25 17:50
 * Description:
 * @author heyong
 */
public class SMath {


    /**
     * 求最小值
     * @param array
     * @return
     */
    public static int min(int... array) {
        int minValue = array[0];

        for (int anArray : array) {
            if (anArray < minValue) {
                minValue = anArray;
            }
        }
        return minValue;
    }
}
