function showMenu() {
    var path = window.location.pathname;
    var seleteMenu =  $(".list-group-item a[href*=\'"+path+"\']");
    //被选中的模块标红
    seleteMenu.css("color","red");

    //被选中的模块展开，其他都隐藏
    $(seleteMenu).parent().parent().show();
    $(seleteMenu).parent().parent().parent().removeClass("tree-closed");
}