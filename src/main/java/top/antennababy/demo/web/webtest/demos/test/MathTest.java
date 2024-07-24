package top.antennababy.demo.web.webtest.demos.test;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

/**
 * <p>
 * 简单的数据统计分析
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/8/10
 * @Version V1.0
 **/
public class MathTest {
    public static void main(String[] args){
        double[] values = new double[] {3.35,6.54,2.21,5.522,4.522,5.64,3.45,2.522,2.88};
        double[] values2 = new double[] {0.89,1.51,0.379,0.41,0.712,0.48,0.54,0.56,0.43};
        double[] values3 = new double[] { 2,10, 38, 23, 38, 23, 21, 234};
        for (int i = 5; i < 100; i=i+5) {
            System.out.println("values3 "+i+"百分位"+StatUtils.percentile(values3, i));
        }

        //计数
        System.out.println("计算样本个数为：" +values.length);
        //mean--算数平均数
        System.out.println("平均数：" + StatUtils.mean(values));
        //sum--和
        System.out.println("所有数据相加结果为：" + StatUtils.sum(values));
        //max--最小值
        System.out.println("最小值：" + StatUtils.min(values));
        //max--最大值
        System.out.println("最大值：" + StatUtils.max(values));
        //范围
        System.out.println("范围是：" + (StatUtils.max(values)-StatUtils.min(values)));
        //variance--方差
        System.out.println("一组数据的方差为：" + StatUtils.variance(values));
        //mode--众数
        double[] res = StatUtils.mode(values);
        System.out.println("众数：" + res[0]+","+res[1]);
        for(int i = 0;i<res.length;i++){
            System.out.println("第"+(i+1)+"个众数为："+res[i]);
        }
        //geometricMean--几何平均数
        System.out.println("几何平均数为：" +StatUtils.geometricMean(values));
        //meanDifference-- 平均差，平均概率偏差
        System.out.println("平均差为："+StatUtils.meanDifference(values, values2));
        //normalize--标准化
        double[] norm = StatUtils.normalize(values2);
        for(int i = 0;i<res.length;i++){
            System.out.println("第"+(i+1)+"个数据标准化结果为：" + norm[i]);
        }
        //percentile--百分位数
        System.out.println("从小到大排序后位于80%位置的数：" + StatUtils.percentile(values, 80.0));
        //populationVariance--总体方差
        System.out.println("总体方差为：" + StatUtils.populationVariance(values));
        //product--乘积
        System.out.println("所有数据相乘结果为：" + StatUtils.product(values));
        //sumDifference--和差
        System.out.println("两样本数据的和差为：" + StatUtils.sumDifference(values,values2));
        //sumLog--对数求和
        System.out.println("一组数据的对数求和为：" + StatUtils.sumLog(values));
        //sumSq--计算一组数值的平方和
        System.out.println("一组数据的平方和：" + StatUtils.sumSq(values));

        //标准差
        System.out.println("一组数据的标准差为（起用方差）：" + new StandardDeviation().evaluate(values));
        System.out.println("一组数据的标准差为（不起用方差）：" + new StandardDeviation(false).evaluate(values));
        //median--中位数
        System.out.println("中位数：" + new Median().evaluate(values));
        //varianceDifference --方差差异性。
        System.out.println("一组数据的方差差异性为：" + StatUtils.varianceDifference(values,values2,StatUtils.meanDifference(values, values2)));
        //变异系数
        System.out.println("一组数据的变异系数：" + NumberUtil.mul(NumberUtil.div(new StandardDeviation().evaluate(values), StatUtils.mean(values)), 100));
        System.out.println("一组数据的变异系数1：" + new StandardDeviation().evaluate(values)/StatUtils.mean(values)*100);
        //偏度系数
        System.out.println("一组数据的偏度系数：" + new Skewness().evaluate(values));
        System.out.println("一组数据的偏度系数（自定义公式）：" + skewness(values));
        //峰度系数
        System.out.println("一组数据的峰度系数：" + new Kurtosis().evaluate(values));
        System.out.println("一组数据的峰度系数（自定义公式）：" + kurtosis(values));
        //几何平均标准差
        System.out.println("几何平均标准差：" +geoStandardDeviation(values));
        //计算内罗梅指数/综合指数
        System.out.println("计算内罗梅指数/综合指数：" +neromeIndex(values));
    }

    /**
     * 计算几何标准差
     * @param values 计算数据数组
     * @return 几何标准差
     */
    private static double geoStandardDeviation(double[] values) {
        double v = 0.0;
        double sqrValue = 0.0;
        double powValue = 0.0;
        if (values != null) {
            int length = values.length;
            for (double d : values) {
                sqrValue = sqrValue + Math.pow(Math.log10(d), 2);
                powValue = powValue + Math.log10(d);
            }
            v = Math.pow(10, Math.sqrt((sqrValue - Math.pow(powValue, 2) / (double) length) / (double) (length - 1)));
        }
        return v;
    }

    /**
     * 计算偏度系数
     * @param values 计算数据数组
     * @return 偏度系数
     */
    private static double skewness(double[] values) {
        double v = 0.0;
        if (values != null) {
            int length = values.length;
            //算数平均值
            double mean = StatUtils.mean(values);
            //算数标准差
            double evaluate = new StandardDeviation().evaluate(values);
            for (double d : values) {
                v += Math.pow(d - mean, 3) / Math.pow(evaluate, 3);
            }
            v = v / (double) (length - 1);
        }
        return v;
    }

    /**
     * 计算峰度系数
     * @param values 计算数据数组
     * @return 峰度系数
     */
    private static double kurtosis(double[] values) {
        double v = 0.0;
        if (values != null) {
            int length = values.length;
            //算数平均值
            double mean = StatUtils.mean(values);
            //算数标准差
            double evaluate = new StandardDeviation().evaluate(values);
            for (double d : values) {
                v += Math.pow(d - mean, 4) / Math.pow(evaluate, 4);
            }
            v = v / (double) (length - 1) - 3.0;
        }
        return v;
    }

    /**
     * 计算内罗梅指数/综合指数
     * @param values 计算数据数组
     * @return 内罗梅指数
     */
    private static double neromeIndex(final double[] values) {
        double v = 0.0;
        if (values != null) {
            //算数平均值
            double mean = StatUtils.mean(values);
            //最大值
            double max = StatUtils.max(values);
            // 计算内罗梅指数
            v = Math.sqrt(NumberUtil.div(NumberUtil.add(Math.pow(mean, 2), Math.pow(max, 2)), 2));
        }
        return Double.isNaN(v) ? 0.0 : v;
    }
}
