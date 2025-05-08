$(document).ready(function() {
    // Xử lý thêm vào giỏ hàng từ danh sách sản phẩm
    $(document).on('click', '.add-to-cart', function(e) {
        e.preventDefault();
        var productId = $(this).data('id');
        console.log('Adding product to cart:', productId);
        
        $.ajax({
            url: '/cart/add/' + productId,
            type: 'POST',
            data: { quantity: 1 },
            success: function(response) {
                console.log('Server response:', response);
                if (response.success) {
                    alert('Đã thêm sản phẩm vào giỏ hàng!');
                    location.reload();
                } else {
                    alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng!');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng!');
            }
        });
    });

    // Xử lý thêm vào giỏ hàng từ trang chi tiết
    $('#add-to-cart').on('click', function(e) {
        e.preventDefault();
        var productId = $(this).data('id');
        var quantity = $('#quantity').val() || 1;
        console.log('Adding product to cart:', productId, 'Quantity:', quantity);
        
        $.ajax({
            url: '/cart/add/' + productId,
            type: 'POST',
            data: { quantity: quantity },
            success: function(response) {
                console.log('Server response:', response);
                if (response.success) {
                    alert('Đã thêm sản phẩm vào giỏ hàng!');
                    location.reload();
                } else {
                    alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng!');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng!');
            }
        });
    });
}); 