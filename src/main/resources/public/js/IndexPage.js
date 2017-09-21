$(document).ready(function () {
    $('.modal').load('/refresh-cart');
});


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
                $('.modal').load('/refresh-cart');
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
                $('.modal').load('/refresh-cart');
                $('#cartcounter').text("Cart (" + dataMap + ")");
            }
        });
    });

$(document).on('click', '.addProduct', function(){
    var data = $(this).data('add');
    data = String(data);
    dataMap = {
        addid: data
    }
    $.ajax({
        method: 'GET',
        data: dataMap,
        url: '/index/add-product',
        success: function(dataMap) {
            $('.modal').load('/refresh-cart');
            $('#cartcounter').text("Cart (" + dataMap + ")");
        }
    });
});

$(document).on('click', '.removeItem', function(){
    var data = $(this).data('remove');
    data = String(data);
    dataMap = {
        removeid: data
    }
    $.ajax({
        method: 'GET',
        data: dataMap,
        url: '/index/remove-product',
        success: function(dataMap) {
            $('.modal').load('/refresh-cart');
            $('#cartcounter').text("Cart (" + dataMap + ")");
        }
    });
});

