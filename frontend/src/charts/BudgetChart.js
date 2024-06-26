import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

const BudgetChart = ({ budgets }) => {
    const labels = budgets.map(budget => budget.name);
    const data = {
        labels,
        datasets: [
            {
                label: 'Використано',
                data: budgets.map(budget => Math.min(budget.currentValue, budget.neededValue)),
                backgroundColor: 'gray'
            },
            {
                label: 'Залишок',
                data: budgets.map(budget => Math.max(0, budget.neededValue - budget.currentValue)),
                backgroundColor: 'green'
            },
            {
                label: 'Перевикористано',
                data: budgets.map(budget => Math.max(0, budget.currentValue - budget.neededValue)),
                backgroundColor: 'red'
            }
        ]
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top'
            },
            title: {
                display: true,
                text: 'Бюджети'
            }
        },
        scales: {
            x: {
                stacked: true
            },
            y: {
                stacked: true
            }
        }
    };

    return <Bar data={data} options={options} />;
};

export default BudgetChart;
