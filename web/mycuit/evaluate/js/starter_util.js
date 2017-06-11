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
        var task = data[i].task;
        if (task != "no task") {
            tempHtml += "<li class='treeview'>"
                + "<a href='#'><span> " + data[i].couName + " </span>"
                + "<span class='pull-right-container'>"
                + "<i class='fa fa-angle-left pull-right'></i>"
                + "</span>"
                + "</a>"
                + "<ul class='treeview-menu'>";
            for (var j = 0; j < task.length; j++) {
                var temp = "";
                if (task[j].state == "1") {
                    temp = "fa-check-square-o";
                } else {
                    temp = "fa-square-o";
                }
                tempHtml += "<li><a href='question.html?qlId=" + task[j].qlId + "&tsId=" + task[j].tsId + "&tkId=" + task[j].tkId + "' target='question_frame' onclick='clickTest(this)'>" + task[j].tkName + "<span class='pull-right-container' style='margin-top: -8px; margin-right: 10px'><i class='fa " + temp + "'></i></span></a></li>";
            }
            tempHtml += "</ul></li>";
        }else{
            tempHtml += "<li><a href='#' onclick='clickTest(this)'>"+data[i].couName+"</a></li>";
        }
    }
    tempHtml += "</ul></li>";
    return tempHtml;
}

function dealUserInfo(userData) {
    //alert(userData.uName);
    $("#user-name").html(userData.uName);
}

function getQuestionInfo() {
    var qlId = request("qlId");
    var tsId = request("tsId");
    if (location.href.toString().split("?")[1] == "" || qlId == "undefined" || tsId == "undefined"){
        return;
    }
    $("#hidden-ts-id").val(tsId);
    $.ajax({
        type:"GET",
        url:"/evaluate/getTaskInfo.do",
        dataType:"json",
        data:location.href.toString().split("?")[1],
        success: function (taskData) {
            $("#question-list-name").html(taskData.task.qlName);
            $("#time-deadline").html(taskData.task.time);
            dealQuetionList(taskData.question);
        }
    });
}

function dealQuetionList(taskData) {
    var tempHtml = "";
    for (var i = 0; i < taskData.length; i++){
        if (taskData[i].qType == "1"){
            tempHtml += getRadioHtml(taskData[i],i+1);
        }else if (taskData[i].qType == "2"){
            tempHtml += getCheckBoxHtml(taskData[i],i+1);
        }else if (taskData[i].qType == "3"){
            tempHtml += getTextAreaHtml(taskData[i],i+1);
        }
    }
    $("#box-form-question").html(tempHtml);
    setStyleInput();
}

function getRadioHtml(question,count) {
    var tempHtml = "<div class='form-group'>"
        +"<label>"+count+". "+question.qName+"</label><br/>"
        +"<input type='hidden' value='"+question.qtqId+","+question.qType+"'>";
    var options = (question.qOptions).split(",");
    var answers = (question.answer).split(",");
    if (question.answer == "unknown"){
        answers = "";
    }
    for (var i = 0; i < options.length; i++){
        var isChecked = "";
        if (isExist(answers,options[i])){
            isChecked = "checked";
        }
        tempHtml += "<label class='select-input'>"
                    +"<input name='r"+count+"' class='minimal' type='radio' "+isChecked+">"
                    +"<span>"+options[i]+"</span>"
                    +"</label>";
    }
    tempHtml += "</div>";
    return tempHtml;
}

function getCheckBoxHtml(question,count) {
    var tempHtml = "<div class='form-group'>"
        +"<label>"+count+". "+question.qName+"</label><br/>"
        +"<input type='hidden' value='"+question.qtqId+","+question.qType+"'>";
    var options = (question.qOptions).split(",");
    var answers = (question.answer).split(",");
    if (question.answer == "unknown"){
        answers = "";
    }
    for (var i = 0; i < options.length; i++){
        var isChecked = "";
        if (isExist(answers,options[i])){
            isChecked = "checked";
        }
        tempHtml += "<label class='select-input'>"
            +"<input class='minimal' type='checkbox' "+isChecked+">"
            +"<span>"+options[i]+"</span>"
            +"</label>";
    }
    tempHtml += "</div>";
    return tempHtml;
}

function getTextAreaHtml(question,count) {
    var tempHtml = "<div class='form-group'>"
        +"<label>"+count+". "+question.qName+"</label><br/>"
        +"<input type='hidden' value='"+question.qtqId+","+question.qType+"'>";
    var answer = question.answer;
    if (answer == "unknown"){
        answer = "";
    }
    tempHtml += "<textarea class='form-control' rows='1' placeholder='Enter your answer ...'>"+answer+"</textarea>";

    tempHtml += "</div>";
    return tempHtml;
}

function isExist(arr,param) {
    for (var i = 0; i < arr.length; i++){
        if (arr[i] == param){
            return true;
        }
    }
    return false;
}

function submitAnswer() {
    var data = getAnswers();
    $.ajax({
        type:"POST",
        url:"/evaluate/submitAnswer.do",
        dataType:"json",
        data:"answer="+data,
        success:function (submitState) {
            if (submitState.state == "success"){
                alert("提交成功");
            }
        }
    });
}

function getAnswers() {
    var tsId = document.getElementById("hidden-ts-id").value;
    var questionList = document.getElementById("box-form-question").children;
    var resultJson = "{'tsId':'"+tsId+"','answer':[";
    // alert(resultJson);
    // alert(questionList.length);
    for (var i = 0;i < questionList.length; i++){
        var formGroup = questionList[i];
        //(formGroup.getElementsByTagName("input"))[0].val();
        var inputObj = (formGroup.getElementsByTagName("input"))[0];
        //alert(inputObj.value);
        var hiddenValues = (inputObj.value).split(",")
        var tempJson = "{";
        var type = hiddenValues[1];
        var qtqId = hiddenValues[0];
        tempJson += "'qtqId':'"+qtqId+"',";
        var tempAnswer = ""
        //alert(tempJson);
        if (type == 1 || type == 2){
            var labelList = formGroup.getElementsByTagName("label");
            //alert(labelList.length);
            for (var  j = 1; j < labelList.length; j++){
                var tmpLabel = labelList[j];
                var inputTmp;
                var spanTmp;
                if (tmpLabel.getElementsByTagName("div").length <= 0){
                    //alert(tmpLabel.getElementsByTagName("div").length);
                    inputTmp = tmpLabel.getElementsByTagName("input")[0];
                    //alert(tmpLabel.getElementsByTagName("input").length);
                }else {
                    inputTmp = tmpLabel.getElementsByTagName("div")[0].getElementsByTagName("input")[0];
                }
                spanTmp = tmpLabel.getElementsByTagName("span")[0];
                //alert(inputTmp.checked);
                if (inputTmp.checked){
                    tempAnswer += spanTmp.innerHTML+",";
                }
                //alert(tempAnswer);
            }
            tempAnswer = tempAnswer.substring(0,tempAnswer.length-1);
        }else{
            tempAnswer = formGroup.getElementsByTagName("textarea")[0].value;
        }
        //alert(tempAnswer);
        tempJson += "'answer':'"+tempAnswer+"'}";
        resultJson += tempJson;
        if (i < questionList.length - 1){
            resultJson += ",";
        }else {
            resultJson += "]}";
        }
    }
    return resultJson;
}