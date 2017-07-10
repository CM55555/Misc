//Made by Chris Matsumura on July 9, 2017.

import org.jfree.chart.*;
import org.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import java.io.*;
import java.awt.*;
import java.applet.*;
import java.util.*; //esp. java.util.Random;
import java.lang.*; //esp. java.lang.Math

public class simplifiedeconomicmodel
{
   public static void main (String args[])
   {      
      int people = 10000; //Start with some number of people
      int initialcash = 10000; //Each with some cash
      int[] record=new int[people];
      for(int i=0; i <record.length;i++)
      {
         record[i] = initialcash;
         //System.out.println(record[i]);
      }
      int[] updatedrecord = updaterecord(record).clone();
      /*
      for (int i=0; i<updatedrecord.length;i++)
      {
         System.out.print(updatedrecord[i]+ " ");
      }
      */
      for (int t=0; t<100000; t++)
      {
         //System.out.println();
         updatedrecord = updaterecord(updatedrecord).clone();
         /*
         for (int i=0; i<updatedrecord.length;i++)
         {
            System.out.print(updatedrecord[i]+ " ");
         }
         */
      }
      HistogramDataset dataset = new HistogramDataset();
      dataset.setType(HistogramType.FREQUENCY); //or HistogramType.RELATIVE_FREQUENCY
      for(int i = 0; i<updatedrecord.length;i++)
         dataset.addSeries("Distribution of Money",intToDoubleArray(updatedrecord),100);
      String plotTitle = "Distribution of Money"; 
      String xaxis = "Amount of Cash";
      String yaxis = "Number of People"; 
      PlotOrientation orientation = PlotOrientation.VERTICAL; 
      boolean show = false; 
      boolean toolTips = false;
      boolean urls = false; 
      JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls);
      int width = 1000;
      int height = 600;       
      try {
      ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
      } catch (IOException e) {}
      
   }
   
   public static int[] updaterecord(int [] record)
   {
      Random random = new Random();
      int updatedrecord[] = record.clone();
      int recipient = 0;
      for (int i = 0; i<record.length;i++)
      {
         if (record[i] != 0) //If a person has $0, they do not give to anyone for that round.
         {
            updatedrecord[i] -= 1;  //Everyone with $1 or more first loses $1
            recipient = random.nextInt(record.length); //Recipients 0 to (n-1) yields n possibilities
            while (recipient == i)      //Nobody can be their own recipient. n-1 allowed possibilities. If the two are the same, continue the random recipient selection process.
               recipient = random.nextInt(record.length);
            updatedrecord[recipient] += 1; //The array element associated with the recipient in the updated record has one more dollar.
            //System.out.println(i+" : "+list[i]);
         }   
      }
      return updatedrecord;
   }
   
   public static double[] intToDoubleArray(int[] integerArray) 
   {
      double[] doubleArray = new double[integerArray.length];
      for(int i=0; i<integerArray.length; i++)
         doubleArray[i] = integerArray[i];
      return doubleArray;
   }
}


