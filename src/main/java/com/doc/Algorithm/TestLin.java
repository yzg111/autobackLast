package com.doc.Algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * com.doc.Algorithm 于2020/5/14 由Administrator 创建 .
 */
public class TestLin {
    public static void main(String[] args) throws IOException {
        LinearRegression linearRegression = new LinearRegression() ;
        linearRegression.lineGre();
        List<Double> list = new ArrayList<Double>() ;

        for(int i = 0 ; i < linearRegression.dataSet.getSize() ;i++) {
            list.add(linearRegression.dataSet.getDatas().get(i));
        }

        System.out.println(linearRegression.theta0+","+linearRegression.theta1);
        List<Double> labels= Arrays.asList(8.04,6.95,7.58,8.81,8.33,9.96,7.24,4.26,10.84,4.82,5.68);
        List<Double>datas=Arrays.asList(10.0,8.0,13.0,9.0,11.0,14.0,6.0,4.0,12.0,7.0,5.0);
        LinearRegression linearRegressiondt = new LinearRegression(labels,datas) ;
        linearRegressiondt.lineGre();
        System.out.println(linearRegressiondt.theta0+","+linearRegressiondt.theta1);


//        ScatterPlot.data("Datas", list, linearRegression.dataSet.getLabels(),linearRegression.theta0,linearRegression.theta1);

    }
}
