$(function(){
    
    $('.archive > li > a').click(function(){
        $(this).parent('li').children('div').toggle(350);
    });
    
});