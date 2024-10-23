import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import { format, fromUnixTime } from 'date-fns';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const TransactionsChart = ({ balances, transactions }) => {
  const labels = transactions.map(transaction => format(new Date(transaction.date), 'd MMM'));

  const incomeData = transactions
    .filter(transaction => transaction.value > 0)
    .reduce((acc, tx) => {
      const date = format(new Date(tx.date), 'd MMM');
      acc[date] = (acc[date] || 0) + tx.value;
      return acc;
    }, {});

  const expenseData = transactions
    .filter(transaction => transaction.value < 0)
    .reduce((acc, tx) => {
      const date = format(new Date(tx.date), 'd MMM');
      acc[date] = (acc[date] || 0) + tx.value;
      return acc;
    }, {});

  const incomeDataset = labels.map(date => incomeData[date] || 0);
  const expenseDataset = labels.map(date => expenseData[date] || 0);

  const data = {
    labels,
    datasets: [
      {
        label: 'Доходи',
        data: incomeDataset,
        borderColor: 'green',
        backgroundColor: 'rgba(0, 255, 0, 0.3)',
        fill: false,
      },
      {
        label: 'Розходи',
        data: expenseDataset,
        borderColor: 'red',
        backgroundColor: 'rgba(255, 0, 0, 0.3)',
        fill: false,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Графік транзакцій',
      },
    },
    scales: {
      x: {
        type: 'category',
        labels: labels,
      },
    },
  };

  return <Line data={data} options={options} />;
};

export default TransactionsChart;
