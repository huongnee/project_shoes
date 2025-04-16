document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    var sidebarCollapse = document.getElementById('sidebarCollapse');
    if (sidebarCollapse) {
        sidebarCollapse.addEventListener('click', function() {
            document.querySelector('.sidebar').classList.toggle('active');
            document.querySelector('.main-content').classList.toggle('active');
        });
    }
    
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    });
    
    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl)
    });

    // Initialize datepickers if any
    var datepickers = document.querySelectorAll('.datepicker');
    if (datepickers.length > 0 && typeof flatpickr !== 'undefined') {
        flatpickr('.datepicker', {
            dateFormat: "d/m/Y",
            locale: "vn"
        });
    }
    
    // Initialize charts if any and if Chart.js is loaded
    if (typeof Chart !== 'undefined') {
        // Revenue Chart
        var revenueChartEl = document.getElementById('revenueChart');
        if (revenueChartEl) {
            var revenueCtx = revenueChartEl.getContext('2d');
            var revenueChart = new Chart(revenueCtx, {
                type: 'line',
                data: {
                    labels: ['01/04', '05/04', '10/04', '15/04', '20/04', '25/04', '30/04'],
                    datasets: [{
                        label: 'Doanh thu',
                        data: [3200000, 4500000, 6700000, 8900000, 7200000, 9800000, 12500000],
                        backgroundColor: 'rgba(63, 81, 181, 0.1)',
                        borderColor: 'rgba(63, 81, 181, 1)',
                        borderWidth: 2,
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value) {
                                    return value.toLocaleString() + 'đ';
                                }
                            }
                        }
                    }
                }
            });
        }
        
        // Product Chart
        var productChartEl = document.getElementById('productChart');
        if (productChartEl) {
            var productCtx = productChartEl.getContext('2d');
            var productChart = new Chart(productCtx, {
                type: 'doughnut',
                data: {
                    labels: ['Giày thể thao', 'Giày công sở', 'Giày cao gót', 'Giày sandal'],
                    datasets: [{
                        data: [42, 28, 18, 12],
                        backgroundColor: [
                            'rgba(63, 81, 181, 0.7)',
                            'rgba(76, 175, 80, 0.7)',
                            'rgba(255, 152, 0, 0.7)',
                            'rgba(244, 67, 54, 0.7)'
                        ],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    }
                }
            });
        }
    }
    
    // Data tables initialization
    if (typeof $.fn.DataTable !== 'undefined') {
        $('.datatable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.10.24/i18n/Vietnamese.json'
            },
            responsive: true
        });
    }
    
    // Delete confirmation
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('Bạn có chắc chắn muốn xóa mục này?')) {
                e.preventDefault();
                return false;
            }
        });
    });
    
    // Print order functionality
    const printOrderBtn = document.querySelector('.btn-print-order');
    if (printOrderBtn) {
        printOrderBtn.addEventListener('click', function() {
            window.print();
        });
    }
}); 