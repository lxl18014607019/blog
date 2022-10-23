//全选
$("#selectAll").click(selectAll)
//删除选中
$("#batchDelete").click(batchDelete)

//全选
function selectAll() {

    //拿到全选复选框的状态
    var ifchecked = $("#selectAll").prop('checked');
    //alert(ifchecked)
    //如果选中为true,全选
    var allcheckbox = $(":checkbox:gt(0)");

    $(allcheckbox).prop("checked",ifchecked);

    //如果选中的个数大于0批量删除按钮可见
    //获取选中个数
    var count = $(":checkbox:gt(0):checked");
/*    $(":checkbox:gt(0)").each(function (index,element) {
        if ($(element).prop('checked')){
            count++;
        }
    })*/

    //alert(count.length)

    //检查删除按钮是否可见
    checkDeleteButton(count.length)

}

//删除按钮可见与否
function checkDeleteButton(count) {
    if (count > 0)
        $("#batchDelete").show(500);
    else
        $("#batchDelete").hide(500);
}


//每条博客对于的checkbox的单击事件
function checkboxClick() {

    //判断选中个数是否为全选如果是全选checkbox为选中状态
    var checkedBox = $(":checkbox:gt(0):checked");

    /*alert(checkedBox.length)
    alert($(":checkbox:gt(1)").length)*/

    if (checkedBox.length == $(":checkbox:gt(0)").length) {
        $("#selectAll").prop("checked",true);
    }else
        $("#selectAll").prop("checked",false);

    //检查删除按钮是否可见
    checkDeleteButton(checkedBox.length)
}

//批量删除按钮被按下
function batchDelete() {

    //判断
    var sure = confirm("是否删除选中博客?");
    if (!sure)
        return;

    //获取到所有选中的列表行
    var checkedBox = $(":checkbox:gt(0):checked")

    //存储数据
    var ids = new Array();

    //遍历
    $(checkedBox).each(function (index,element) {
        //alert($(element).parent().parent().children('td').eq(0).text());

        ids[index] = $(element).parent().parent().children('td').eq(0).text()

    })

/*    for (var i = 0; i < ids.length; i++) {
        alert(ids[i])
    }*/
    

    //ajax
    $.ajax(
        {
            type: "post",
            dataType: "json",
            url : "/admin/blog/delmultiple",
            data: {"ids": ids},
            success: delmResponse,
            traditional: true
        }
    )



    //局部刷新函数
    function delmResponse(ok) {
        if (ok === 'NORMAL'){
            alert("删除成功!")
            //刷新页面
            location.reload();
        }else if (ok === 'MISSING') {
            alert("未删除完成,请重试!")
        }else{
            alert("删除失败!")
        }
    }
}


