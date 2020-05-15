package com.doc.Algorithm;

import java.util.List;

/**
 * com.doc.Algorithm 于2020/5/14 由Administrator 创建 .
 */
public class DataSet {


    private List<Double> datas;



    private List<Double> labels;

    public int getSize(){
        return this.getDatas().size();
    }

    public List<Double> getDatas() {
        return datas;
    }

    public void setDatas(List<Double> datas) {
        this.datas = datas;
    }

    public List<Double> getLabels() {
        return labels;
    }

    public void setLabels(List<Double> labels) {
        this.labels = labels;
    }


}
