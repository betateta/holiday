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

@Component
public class ChartService {
    @Autowired
    SessionService sessionService;

    public JFreeChart getChartForHolidays(){
        JFreeChart chart = ChartFactory.createPieChart(
                "Праздники по типам",
                createDataSetForHolidays(),
                false,
                true,
                false
        );
        return chart;
    }

    public byte[] getImageAsByteArray(){
        JFreeChart chart = getChartForHolidays();
        byte [] bytes = null;
        try {
            bytes=ChartUtilities.encodeAsPNG(chart.createBufferedImage(400,400));
        } catch (IOException ioException){

        }
        return bytes;
    }
    private PieDataset createDataSetForHolidays(){
        DefaultPieDataset dataset = new DefaultPieDataset();
        SessionGame sessionGame = sessionService.findAnyLast();
        Long simpleCount = sessionGame.getCalculateList().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("simple")).count();
        Long banquetCount = sessionGame.getCalculateList().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("banquet")).count();
        Long dinnerCount = sessionGame.getCalculateList().stream()
                .filter(calculate -> calculate.getHoliday().getName().equals("dinner")).count();

        dataset.setValue("Простой" , simpleCount);
        dataset.setValue("Банкет", banquetCount);
        dataset.setValue("Ужин"  , dinnerCount);

        return dataset;
    }
}
