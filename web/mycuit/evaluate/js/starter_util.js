/**
 * Created by Esong on 2017/6/7.
 */

function checkLogin() {
    $.ajax({
        type:"GET",
        url:"/evaluate/checkLogin.do",
        dataType: "json",
        success: function (jsonData) {
            if (jsonData.state == "logout"){
                window.location = "login.html";
            }else{
                $.ajax({
                    type:"GET",
                    url:"/evaluate/getCourse.do",
                    dataType:"json",
                    success: function (courseData) {
                        dealCourseInfo(courseData.course);
                    }
                });
                dealUserInfo(jsonData.user);
            }
        }
    });
}
function dealCourseInfo(courseData) {
    //alert((courseData.course.XX)[0].teacherName);
    var xuanXiu = courseData.XX;
    var biXiu = courseData.BX;
    var heXin = courseData.HX;
    var tempHtml = "";
    tempHtml += "<li class='header'>HEADER</li>";
    // 核心
    tempHtml += getCourseHtml(heXin,"核心") + getCourseHtml(biXiu,"必修") +getCourseHtml(xuanXiu,"选修");
    $("#course-information-ul").html(tempHtml);
}

function getCourseHtml(data,type) {
    var tempHtml = "";
    if(type == "核心") {
        tempHtml += "<li class='treeview active'>"
            + "<a href='#'><i class='fa fa-book'></i> <span> " + type + " </span>"
            + "<span class='pull-right-container'>"
            + "<i class='fa fa-angle-left pull-right'></i>"
            + "</span>"
            + "</a>"
            + "<ul class='treeview-menu menu-open' style='display: block'>";
    }else{
        tempHtml += "<li class='treeview'>"
            + "<a href='#'><i class='fa fa-book'></i> <span> " + type + " </span>"
            + "<span class='pull-right-container'>"
            + "<i class='fa fa-angle-left pull-right'></i>"
            + "</span>"
            + "</a>"
            + "<ul class='treeview-menu'>";
    }
    for (var i = 0; i < data.length; i++){
        tempHtml += "<li class='treeview'>"
            +"<a href='#'><span> "+data[i].couName+" </span>"
            +"<span class='pull-right-container'>"
            +"<i class='fa fa-angle-left pull-right'></i>"
            +"</span>"
            +"</a>"
            +"<ul class='treeview-menu'>";
        var task = data[i].task;
        for(var j = 0; j < task.length; j++){
            var temp = "";
            if (task[j].state == "1"){
                temp = "fa-check-square-o";
            }else{
                temp = "fa-square-o";
            }
            tempHtml += "<li><a href='question.html?qlId="+task[j].qlId+"' target='question_frame' onclick='clickTest(this)'>"+task[j].tkName+"<span class='pull-right-container' style='margin-top: -8px; margin-right: 10px'><i class='fa "+temp+"'></i></span></a></li>";
        }
        tempHtml += "</ul></li>";
    }
    tempHtml += "</ul></li>";
    return tempHtml;
}

function dealUserInfo(userData) {
    //alert(userData.uName);
    $("#user-name").html(userData.uName);
}