
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
                alert("hello");
                $('#cartcounter').text("Cart (" + dataMap + ")");
                }
        });
    });
});
