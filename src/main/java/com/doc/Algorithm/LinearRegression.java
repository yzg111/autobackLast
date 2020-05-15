package com.doc.Algorithm;

import org.jsoup.helper.DataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * com.doc.Algorithm 于2020/5/14 由Administrator 创建 .
 */
public class LinearRegression {
    public double theta0 = 0.0 ;  //截距
    public double theta1 = 0.0 ;  //斜率
    private double alpha = 0.01 ;  //学习速率

    private int max_itea = 100000 ; //最大迭代步数

    public DataSet dataSet = new DataSet() ;

    public  LinearRegression() throws IOException{
//        List<Double> lg={8.04,6.95,7.58,8.81,8.33,9.96,7.24,4.26,10.84,4.82,5.68};
        List<Double> labels=Arrays.asList(8.04,6.95,7.58,8.81,8.33,9.96,7.24,4.26,10.84,4.82,5.68);
        List<Double>datas=Arrays.asList(10.0,8.0,13.0,9.0,11.0,14.0,6.0,4.0,12.0,7.0,5.0);


        dataSet.setLabels(labels);
        dataSet.setDatas(datas);
//        dataSet.loadDataFromTxt("datas/house_price.txt", ",",1);
    }

    public LinearRegression(List<Double>datas,List<Double>labels){
        dataSet.setLabels(labels);
        dataSet.setDatas(datas);
    }


    public double predict(double x){
        return theta0+theta1*x ;
    }

    public double calc_error(double x, double y) {
        return predict(x)-y;
    }



    public void gradientDescient(){
        double sum0 =0.0 ;
        double sum1 =0.0 ;

        for(int i = 0 ; i < dataSet.getSize() ;i++) {
            sum0 += calc_error(dataSet.getDatas().get(i), dataSet.getLabels().get(i)) ;
            sum1 += calc_error(dataSet.getDatas().get(i), dataSet.getLabels().get(i))*dataSet.getDatas().get(i) ;
        }

        this.theta0 = theta0 - alpha*sum0/dataSet.getSize() ;
        this.theta1 = theta1 - alpha*sum1/dataSet.getSize() ;

    }

    public void lineGre() {
        int itea = 0 ;
        while( itea< max_itea){
            //System.out.println(error_rate);
            System.out.println("The current step is :"+itea);
            System.out.println("theta0 "+theta0);
            System.out.println("theta1 "+theta1);
            System.out.println();
            gradientDescient();
            itea ++ ;
        }
    } ;


}
