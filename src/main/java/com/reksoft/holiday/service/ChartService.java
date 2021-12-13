package com.reksoft.holiday.service;

import com.reksoft.holiday.model.SessionGame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChartService {
    @Autowired
    SessionService sessionService;

    private JFreeChart getChartForHolidays(Map<String,PieDataset> map){
        JFreeChart chart = ChartFactory.createPieChart(
                "Праздники по типам",
                map.get("types"),
                false,
                true,
                false
        );
        return chart;
    }
    private JFreeChart getChartForPoints(Map<String,PieDataset> map){
        JFreeChart chart = ChartFactory.createPieChart(
                "Средние значения очков по типам",
                map.get("points"),
                false,
                true,
                false
        );
        return chart;
    }

    public Map<Integer,byte[]> getImages() throws IOException{

        Map<Integer,byte[]> imageMap = new HashMap<>();
        Map<String,PieDataset> map= createDataSet();
        try {
            imageMap.put(1,
                    ChartUtilities.encodeAsPNG(getChartForHolidays(map).createBufferedImage(400,400)));
            imageMap.put(2,
                    ChartUtilities.encodeAsPNG(getChartForPoints(map).createBufferedImage(400,400)));

        } catch (IOException ioException){
            throw ioException ;
        }
        return imageMap;
    }
    private Map<String,PieDataset> createDataSet(){
        Map<String,PieDataset> map = new HashMap<>();

        SessionGame sessionGame = sessionService.findAnyLast();

        Long simpleCount = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("simple")).count();
        Long banquetCount = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("banquet")).count();
        Long dinnerCount = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("dinner")).count();

        Integer simplePoints = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("simple"))
                .map(calculate -> calculate.getPoints()).reduce((acc,points)->acc+points).get();
        Integer banquetPoints = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("banquet"))
                .map(calculate -> calculate.getPoints()).reduce((acc,points)->acc+points).get();
        Integer dinnerPoints = sessionGame.getCalculateSet().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("dinner"))
                .map(calculate -> calculate.getPoints()).reduce((acc,points)->acc+points).get();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Простой" , simpleCount);
        dataset.setValue("Банкет", banquetCount);
        dataset.setValue("Ужин"  , dinnerCount);

        DefaultPieDataset dataset1 = new DefaultPieDataset();
        dataset1.setValue("Простой",simplePoints/simpleCount);
        dataset1.setValue("Банкет", banquetPoints/banquetCount);
        dataset1.setValue("Ужин"  , dinnerPoints/dinnerCount);
        map.put("types",dataset);
        map.put("points",dataset1);
        return map;
    }

}
