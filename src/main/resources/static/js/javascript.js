//时间
function show(){

    var date = new Date(); //日期对象
    var now = "";
    now = now + date.getHours()+":";
    if(date.getMinutes() < 10){
        now = now + "0" + date.getMinutes();
    }else{
        now = now + date.getMinutes();
    }

    document.getElementById("nowDiv").innerHTML = now;
    setTimeout("show()",1000);
}

function showusername(){
    $("#usernameBut").hide(300);
    $("#username").hide(300);
    if (localStorage.getItem("name") == null){
        $("#usernameA").text("热心市民");
    }else {
        $("#usernameA").text(localStorage.getItem("name"));
    }

}

/*https://api.ooopn.com/image/bing/api.php?type=jump"   */
/*if (window.navigator.onLine == true){
    var index = parseInt(Math.random()*(100,10);
    /!*var str = "https://wallpaper.infinitynewtab.com/wallpaper/"+index+".jpg";*!/
    var str = "https://i.picsum.photos/id/" + index + "/1920/1080.jpg";


    //https://api.ooopn.com/image/bing/api.php?v=2&type=jump    必应随机壁纸*!/
    document.write('<style>body{background:url("https://i.picsum.photos/id/574/1920/1080.jpg") no-repeat center top}</style>');
    //document.write('<style>body{background:url(' +str+') }</style>');
}else {*/
    //创建一个数组变量来存储背景图片的路径
    var bodyBgs = [];
    bodyBgs[0] = "img/1.jpg";
    bodyBgs[1] = "img/2.jpg";
    bodyBgs[2] = "img/3.jpg";
    bodyBgs[3] = "img/4.jpg";
    bodyBgs[4] = "img/5.jpg";
    bodyBgs[5] = "img/6.jpg";
    bodyBgs[6] = "img/7.jpg";
    bodyBgs[7] = "img/8.jpg";
    bodyBgs[8] = "img/9.jpg";
    bodyBgs[9] = "img/10.jpg";
    bodyBgs[10] = "img/03.jpg"; //11不知道为什么不能被加载，可能是文件名相同了
    bodyBgs[11] = "img/12.jpg";
    bodyBgs[12] = "img/1.jpg";
    //随机数 0-12
    var randomBgIndex = Math.round( Math.random() * 12 );
    /*background*/
    document.write('<style>body{background:url(' + bodyBgs[randomBgIndex] + ') no-repeat 50% 0}</style>');
/*}*/

function callbackpath(param) {
    //alert(param.bing.url);
    //获取接口路径
    alert(1);
    var backpath = param.bing.url;
    document.write('<style>body{background:url('+backpath+') no-repeat center top}</style>');
}

//反馈
function mailsome1() {
    var who = "2822930767@qq.com";
    alert("开发人员QQ邮箱:" + who);
}
//显示默认flase
var iftrue = false;
function fun1() {
    var textvalue = $("#username").val();
    if (textvalue == ""){
        //如果为null直接隐藏
        showOrhide();
        iftrue = false;
        return;
    }
    //不为null就存入本地
    localStorage.setItem("name",textvalue);
    //更改名称
    $("#usernameA").text(textvalue);

    //隐藏
    showOrhide();
    //显示为false
    iftrue = false;
}
//隐藏/显示函数
function showOrhide() {
    if (!iftrue){
        $("#usernameBut").show(300);
        $("#username").show(300);
        iftrue = true;
    }else {
        $("#usernameBut").hide(300);
        $("#username").hide(300);
        iftrue = false;
    }
}