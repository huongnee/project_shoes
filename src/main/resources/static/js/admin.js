document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    const sidebarCollapse = document.getElementById('sidebarCollapse');
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
        const revenueChartElement = document.getElementById('revenueChart');
        if (revenueChartElement) {
            const revenueLabels = revenueChartElement.getAttribute('data-labels').split(',');
            const revenueData = revenueChartElement.getAttribute('data-values').split(',').map(Number);

            new Chart(revenueChartElement, {
                type: 'line',
                data: {
                    labels: revenueLabels,
                    datasets: [{
                        label: 'Doanh thu (VNĐ)',
                        data: revenueData,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 2,
                        tension: 0.4,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value) {
                                    return new Intl.NumberFormat('vi-VN').format(value);
                                }
                            }
                        }
                    },
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return new Intl.NumberFormat('vi-VN').format(context.parsed.y) + ' VNĐ';
                                }
                            }
                        }
                    }
                }
            });
        }
        
        // Product Chart
        const productChartElement = document.getElementById('productChart');
        if (productChartElement) {
            const productLabels = productChartElement.getAttribute('data-labels').split(',');
            const productData = productChartElement.getAttribute('data-values').split(',').map(Number);

            new Chart(productChartElement, {
                type: 'doughnut',
                data: {
                    labels: productLabels,
                    datasets: [{
                        data: productData,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.7)',
                            'rgba(54, 162, 235, 0.7)',
                            'rgba(255, 206, 86, 0.7)',
                            'rgba(75, 192, 192, 0.7)',
                            'rgba(153, 102, 255, 0.7)',
                            'rgba(255, 159, 64, 0.7)',
                            'rgba(199, 199, 199, 0.7)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(199, 199, 199, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'right',
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    const label = context.label || '';
                                    const value = context.parsed || 0;
                                    return label + ': ' + value + ' sản phẩm';
                                }
                            }
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
    
    // Delete product confirmation
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const productId = this.getAttribute('data-id');
            if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
                // Gửi yêu cầu xóa sản phẩm
                fetch(`/api/products/${productId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        // Reload trang sau khi xóa thành công
                        window.location.reload();
                    } else {
                        alert('Có lỗi xảy ra khi xóa sản phẩm!');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi xóa sản phẩm!');
                });
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