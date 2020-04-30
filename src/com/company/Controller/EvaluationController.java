package com.company.Controller;

import com.company.DbHelper.EvaluationRepository;
import com.company.View.EvaluationView;

import java.util.HashMap;

public class EvaluationController {

    private EvaluationRepository evaluationRepository = new EvaluationRepository();
    private EvaluationView evaluationView = new EvaluationView();

    public void printSumOfAllOrders() {
        evaluationView.printSumOfAllOrders(evaluationRepository.getSumOfAllOrders());
    }

    public void printSumOfAllOrdersPerCustomer() {
        HashMap<Integer, Integer> sumOfAllOrdersPerCustomer = evaluationRepository.getSumOfAllOrdersPerCustomer();

        evaluationView.printSumOfAllOrdersPerCustomer(sumOfAllOrdersPerCustomer);
    }

    public void printSumOfAllOrdersPerTown() {
        HashMap<String, Integer> sumOfAllOrdersPerTown = evaluationRepository.getSumOfAllOrdersPerTown();

        evaluationView.printSumOfAllOrdersPerTown(sumOfAllOrdersPerTown);
    }

    public void printTotalRevenue() {
        double totalRevenue = evaluationRepository.getTotalRevenue();

        evaluationView.printTotalRevenue(totalRevenue);
    }

    public void printSalesPerCustomer() {
        String output = evaluationRepository.getSalesPerCustomer();

        evaluationView.printSalesPerCustomer(output);
    }

    public void printSalesPerLocation() {
        String output = evaluationRepository.getSalesPerLocation();

        evaluationView.printSalesPerLocation(output);
    }

    public void printMostOftenOrderedDish() {
        HashMap<String, Integer> dish = evaluationRepository.getMostOftenOrderedDish();

        evaluationView.printMostOftenOrderedDish(dish);
    }

    public void printAllOrdersDesc() {
        HashMap<String, Integer> orders = evaluationRepository.getAllOrdersDesc();

        evaluationView.printAllOrdersDesc(orders);
    }
}
