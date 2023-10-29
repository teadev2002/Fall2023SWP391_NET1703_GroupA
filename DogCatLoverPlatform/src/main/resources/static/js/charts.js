// Get the canvas element
var ctxDoughnut = document.getElementById('simple-doughnut-chart').getContext('2d');

// Configuration options
var optionsDoughnut = {
    responsive: true,
    maintainAspectRatio: false
};

// Create an empty doughnut chart initially
var ctx = document.getElementById('simple-doughnut-chart').getContext('2d');

// Data for the doughnut chart
var data = {
    labels: ['Blogs', 'services'],
    datasets: [{
        data: [60, 40], // Example data, replace it with your own
        backgroundColor: [
            'rgba(255, 99, 132, 0.8)',
            'rgba(54, 162, 235, 0.8)',
            'rgba(255, 206, 86, 0.8)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)'
        ],
        borderWidth: 1
    }]
};

// Configuration options
var options = {
    responsive: true,
    maintainAspectRatio: false
};

// Create the doughnut chart
var myDoughnutChart = new Chart(ctx, {
    type: 'doughnut',
    data: data,
    options: options
});

// Get the canvas element
var ctxColChart = document.getElementById('myColChart').getContext('2d');

// Data for the column chart
var dataColChart = {
    labels: ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat', 'Sun'],
    datasets: [{
        label: 'Blog',
        data: [
            [50, 75],
            [30, 45],
            [15, 30],
            [60, 80],
            [25, 40],
            [35, 50],
            [10, 20]
        ],
        backgroundColor: 'rgba(255, 99, 132, 0.8)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1
    },
        {
            label: 'Service',
            data: [
                [25, 40],
                [35, 50],
                [40, 60],
                [15, 30],
                [55, 10],
                [20, 40],
                [10, 30]
            ],
            backgroundColor: 'rgba(54, 162, 235, 0.8)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
        }]
};

// Configuration options
var optionsColChart = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
        y: {
            beginAtZero: true
        }
    }
};

// Create the column chart
var myColChart = new Chart(ctxColChart, {
    type: 'bar',
    data: {
        labels: dataColChart.labels,
        datasets: dataColChart.datasets.map(dataset => {
            return {
                ...dataset,
                data: dataset.data.flat() // flatten the nested array
            };
        })
    },
    options: optionsColChart
});

// Get the canvas element
var ctxPieChart = document.getElementById('simple-pie-chart').getContext('2d');

// Data for the pie chart
var dataPieChart = {
    labels: ['Blog', 'Service', 'Booking'],
    datasets: [{
        data: [30, 60, 10],
        backgroundColor: [
            'rgba(255, 99, 132, 0.8)',
            'rgba(54, 162, 235, 0.8)',
            'rgba(255, 206, 86, 0.8)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)'
        ],
        borderWidth: 1
    }]
};

// Configuration options
var optionsPieChart = {
    responsive: true,
    maintainAspectRatio: false
};

// Create the pie chart
var myPieChart = new Chart(ctxPieChart, {
    type: 'pie',
    data: dataPieChart,
    options: optionsPieChart
});

// Get the canvas element
var ctxBarChart = document.getElementById('myBarChart').getContext('2d');

// Data for the bar chart
var dataBarChart = {
    labels: ['A', 'B', 'C', 'D', 'E'],
    datasets: [{
        label: 'My First Dataset',
        data: [65, 59, 80, 81, 56],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 205, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)'
        ],
        borderColor: [
            'rgb(255, 99, 132)',
            'rgb(255, 159, 64)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)'
        ],
        borderWidth: 1
    }]
};

// Configuration options
var optionsBarChart = {
    scales: {
        y: {
            beginAtZero: true
        }
    }
};

// Create the bar chart
var myBarChart = new Chart(ctxBarChart, {
    type: 'bar',
    data: dataBarChart,
    options: optionsBarChart
});

// Get the canvas element
var ctxLineChart = document.getElementById('myChart').getContext('2d');

// Data for the line chart
var dataLineChart = {
    labels: listbook.map(entry => entry.create_date),
    datasets: [{
        label: 'Total Price',
        data: listbook.map(entry => entry.total_price),
        borderColor: 'blue',
        borderWidth: 1
    }]
};

// Configuration options
var optionsLineChart = {
    responsive: true,
    maintainAspectRatio: false
};

// Create the line chart
var myLineChart = new Chart(ctxLineChart, {
    type: 'line',
    data: dataLineChart,
    options: optionsLineChart
});
