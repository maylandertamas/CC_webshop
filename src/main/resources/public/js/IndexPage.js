
$(function() {
    $('.btn-success').click(function(){
        var data = $(this).data('button');
        data = String(data);
        dataMap = {
            id: data
        }
        $.ajax({
            method: 'GET',
            data: dataMap,
            url: '/index/add',
            success: function(dataMap) {
                $('.modal-dialog').load('/refresh-cart');
                $('#cartcounter').text("Cart (" + dataMap + ")");
                }
        });
    });
});

    $(document).on('click', '.substractProduct', function(){
        var data = $(this).data('substract');
        data = String(data);
        dataMap = {
            substractid: data
        }
        $.ajax({
            method: 'GET',
            data: dataMap,
            url: '/index/substract-product',
            success: function(dataMap) {
                $('.modal-dialog').load('/refresh-cart');
                $('#cartcounter').text("Cart (" + dataMap + ")");
            }
        });
    });

