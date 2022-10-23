
//加载完毕事件
window.onload = loadingCompleted;
//按键事件
document.onkeydown = keyEvent;

//搜索框
var search = $('#search')
//提示框
var suggestion = $("#suggestion")

//搜索文本框内容被改变事件
$(search).bind("input propertychange",changeSearch)
//搜索框获取焦点
$(search).focus(showSuggestion)

//搜索框失去焦点
$(search).blur(hideSuggestion)

//时间
function currentTime(){

    var date = new Date(); //日期对象
    var now = "";
    now = now + date.getHours()+":";
    if(date.getMinutes() < 10){
        now = now + "0" + date.getMinutes();
    }else{
        now = now + date.getMinutes();
    }

    document.getElementById("currentTime").innerHTML = now;
    setTimeout("currentTime()",1000);
}

//搜索框被单击事件
function searchOnclick() {

    //拿到搜索框元素
    //var obj = document.getElementById("search");
    var obj = $("#search");

    alert(obj)


}

/*搜索框获取失去焦点*/
// $("#search").focus(function () {
//     var obj = $("#search");
//     obj.attr("placeholder",'');
// });

$("#search").blur(function () {
    var obj = $("#search");
    obj.attr("placeholder",'search');
});


//http://suggestion.baidu.com/su?p=3&wd=1&ie=UTF-8&cb=
//搜索文本框内容被改变事件
function changeSearch() {

    //获取关键字
    var keyword = $(search).val();

    //如果关键字为'' 那么取消
    if (!keyword == ''){
        showSuggestion();
    } else{
        hideSuggestion();
    }

}

//将json解析到提示框
function data(data) {

    var count = 0;

    $("#suggestion_table tr:gt(0)").each(function (index) {
        $(this).children('td').each(function (j) {
            $(this).text(data.s[count])
            count ++
        })
    })

}

//搜索事件
function searchSuggestion(obj) {
    var value = $(obj).text()
    //window.location.href="https://www.baidu.com/s?tn=88093251_47_hao_pg&ie=utf-8&wd=" + value
    window.open("https://www.baidu.com/s?tn=88093251_47_hao_pg&ie=utf-8&wd=" + value);
}

//搜索框获取焦点
function showSuggestion() {
    if($(search).val() != '')
        $(suggestion).show();
    getSuggestion();
}

//搜索框失去焦点
function hideSuggestion() {
    setTimeout(
        "$(suggestion).hide()",200
    )
}

//getSuggestion
function getSuggestion() {

    //获取关键字
    var keyword = $(search).val();

    $("#translate").text("翻译: " + $(search).val());

    //http://suggestion.baidu.com/su?wd="  + keyword + "&json=1&p=3&cb=data
    $.ajax({
        url: "http://suggestion.baidu.com/su?p=3&wd=" + keyword + "&ie=UTF-8&cb=data",
        type: "GET",
        dataType: "jsonp", //指定服务器返回的数据类型,跨域请求

    });
}

//获取背景图片
/*function getBackgroundImg() {
    var index = getRandom(1,900);
    var url = "https://wallpaper.infinitynewtab.com/wallpaper/" + index + ".jpg";
    $("#backgroundImg").attr("src",url);
}*/

//置随机数
function getRandom(m,n) {
    var num = Math.floor(Math.random()*(m - n) + n);
    return num;
}

//加载完成
function loadingCompleted() {
    //getBackgroundImg();
    currentTime();
}

//提交
function keyEvent() {
    //回车键
    if (event.keyCode == 13){
        var value = $(search).val()
        window.location.href="https://www.baidu.com/s?tn=88093251_47_hao_pg&ie=utf-8&wd=" + value
    }
    //del
    if (event.keyCode == 46){
        $(search).val('');
        hideSuggestion();
    }
}

/*翻译*/
function translateWord(obj) {
    /*获取需要翻译的字符*/
    var word = $(obj).text().slice(4);
    //去除空格
    var trimword = ltrim(rtrim(word));
    //页面跳转
    if ('' != trimword){
        //window.location = "https://fanyi.baidu.com/?aldtype=16047#en/zh/" + trimword;
        window.open("https://fanyi.baidu.com/?aldtype=16047#en/zh/" + trimword);
    }
}

//去左空格;
function ltrim(s){
    return s.replace(/(^\s*)/g, "");
}
//去右空格;
function rtrim(s){
    return s.replace(/(\s*$)/g, "");
}
