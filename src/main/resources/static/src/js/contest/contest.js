$(function(){
    layui.use(['layer','form','laydate'], function(){
        var layer = layui.layer;
        var form  = layui.form;
        var laydate = layui.laydate;


        // start 普通用户监听
        // 监听select 分页
        form.on('select(pagesize)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"pageNum="+1+"&pageSize="+data.value;
        });

        // 监听select 赛事级别
        form.on('select(contestlevel)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"level="+data.value;
        });
        // 监听select 赛事类型
        form.on('select(contesttype)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"type="+data.value;
        });
        // end 普通用户监听

        // start 管理用户监听
        // 监听select 分页
        form.on('select(pagesize_admin)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"pageNum="+1+"&pageSize="+data.value+"&isEnabled=true";
        });

        // 监听select 赛事级别
        form.on('select(contestlevel_admin)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"level="+data.value+"&isEnabled=true";
        });
        // 监听select 赛事类型
        form.on('select(contesttype_admin)', function(data){
            // href 跳转设置分页大小
            window.location.href="/contest/toContestList?"+"type="+data.value+"&isEnabled=true";
        });
        // end 管理用户监听


        //监听提交
        form.on('submit(ContestSumbit)', function(data){

            layer.confirm('确定提交么？', {
                btn: ['确认','返回'] //按钮
            },function(){
                // layer.closeAll();//关闭所有弹框
                console.log("data--"+data);
                formSubmit(data);
            },function() {
                layer.closeAll();
            });
            return false;
        });


        form.render();
    });
});

function formSubmit(obj) {

    if($("#flag").val() == "update"){
        // console.log($("#UserForm").serialize());
        $.ajax({
            type: "POST",
            data: $("#ContestForm").serialize(),
            url: "/admin/contest/updateContest",
            success: function (data) {
                if (data == "200") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        // load();
                    });
                } else {
                    layer.alert(data);
                }
            },
            error: function (data) {
                layer.alert("操作请求错误，请您联系技术人员");
            }
        });
    }
}
// 删除
function del(id,name) {
    if(null!=id){
        layer.confirm('您确定要 <font style=\'font-weight:bold;\' color=\'red\'>删除: </font><font style=\'font-weight:bold;\' color=\'blue\'>'+name+'</font> 赛事么?', {
            btn: ['确认','返回'] //按钮
        },  function(){
            $.get("/admin/contest/delContest",{"id":id},function(data){
                console.log(data)
                if(data == "200"){
                    //回调弹框
                    layer.alert("删除成功！",function(){
                        layer.closeAll();
                        window.location.reload();
                        //加载load方法
                        // load();//自定义
                    });
                }else{
                    layer.alert(data);//弹出错误提示
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}

//修改
function edit(obj) {

    console.log(obj);

    $("#Cid").val(obj.cid==null?'':obj.cid);
    $("#Title").val(obj.title==null?'':obj.title);
    $("#Memo").val(obj.memo==null?'':obj.memo);
    // $("#StartTime").val(obj.StartTime==null?'':obj.StartTime);
    // $("#EndTime").val(obj.EndTime==null?'':obj.EndTime);

    $("#flag").val("update");


    //Start 初始化 tags
    for(i = 0 ; i < obj.tags.length ; i++){
        $("#tags_1").addTag(obj.tags[i]);
    }
    //end 初始化 tags
    //start 初始化 是否启用
    if(obj.isEnabled == false){
        $("#isEnabled").removeAttr("checked");
    }
    //end 初始化 是否启用

    //start 初始化 赛事类别
    if(obj.type=="单人赛"){
        var div2 =$("<option   value='组队赛' >"+
            "<span>"+"组队赛"+"</span>"+
            "</option>");
        $("#Type").append(div2);
        var div=$("<option value='单人赛'  selected >"+
                "<span>"+"单人赛"+"</span>"+
                "</option>");
        $("#Type").append(div);

        layui.form.render('select');
    }else if(obj.type == "组队赛"){
        var div2 =$("<option   value='组队赛' selected >"+
            "<span>"+"组队赛"+"</span>"+
            "</option>");
        $("#Type").append(div2);
        var div=$("<option value='单人赛'   >"+
            "<span>"+"单人赛"+"</span>"+
            "</option>");
        $("#Type").append(div);
        layui.form.render('select');
    }
    //end 初始化 赛事类别

    //start 初始化 赛事级别
    var div=null;
    switch (obj.level) {
        case "world":
            div=$("<option value='world'  selected >"+ "<span>"+"国际级"+"</span>"+ "</option>"+
                "<option value='nation'   >"+ "<span>"+"国家级"+"</span>"+ "</option>"+
                "<option value='province'   >"+ "<span>"+"省级"+"</span>"+ "</option>"+
                "<option value='college'   >"+ "<span>"+"校级"+"</span>"+ "</option>"
            );
            break;
        case "nation":
            div=$("<option value='world'   >"+ "<span>"+"国际级"+"</span>"+ "</option>"+
                "<option value='nation' selected  >"+ "<span>"+"国家级"+"</span>"+ "</option>"+
                "<option value='province'   >"+ "<span>"+"省级"+"</span>"+ "</option>"+
                "<option value='college'   >"+ "<span>"+"校级"+"</span>"+ "</option>"
            );
            break;
        case "province":
            div=$("<option value='world'   >"+ "<span>"+"国际级"+"</span>"+ "</option>"+
                "<option value='nation'   >"+ "<span>"+"国家级"+"</span>"+ "</option>"+
                "<option value='province' selected >"+ "<span>"+"省级"+"</span>"+ "</option>"+
                "<option value='college'   >"+ "<span>"+"校级"+"</span>"+ "</option>"
            );
            break;
        case "college":
            div=$("<option value='world'   >"+ "<span>"+"国际级"+"</span>"+ "</option>"+
                "<option value='nation'   >"+ "<span>"+"国家级"+"</span>"+ "</option>"+
                "<option value='province'   >"+ "<span>"+"省级"+"</span>"+ "</option>"+
                "<option value='college'  selected >"+ "<span>"+"校级"+"</span>"+ "</option>"
            );
            break;
    }
    $("#Level").append(div);
    layui.form.render('select');
    //end 初始化 赛事级别

    //start 初始化 日期 和弹窗
    layui.use(['laydate','layer'], function(){
            var laydate = layui.laydate;
            var layer =layui.layer;
            laydate.render({
                elem: '#StartTime',
                type: 'datetime',
                value:obj.startTime
            });
            laydate.render({
                elem: '#EndTime',
                type: 'datetime',
                value:obj.endTime
            });
            layer.open({
                type:1,
                title: "编辑赛事信息",
                fixed:false,
                resize :false,
                offset:'auto',
                shadeClose: true,
                btn: '关闭',
                area: ['600px', '720px'],
                content:$('#eidtContest'),
                end: function(){
                    location.reload();
                }
            });
        });

    //end 初始化 日期 和弹窗

}


// 报名
function sign(cid,uid,mytype,contesttype) {

    console.log(cid+ " "+ uid+" "+mytype+ " "+contesttype);
    if(mytype =="个人账户" && contesttype != "个人赛"){
        layer.alert("个人账户不可以参加团队赛!,请使用组队账户登录",{
            icon: 5,
            skin: 'layer-ext-moon'
        });
    }else if(mytype == "组队账户" && contesttype != "组队赛"){
        layer.alert("组队账户不可以参加个人赛赛!,请使用个人账户登录",{
            icon: 5,
            skin: 'layer-ext-moon'
        });
    }else{
        $.get("/contest/signup",{"cid":cid,"uid":uid,"type":mytype},function(data){
            if(data == "100"){
                layer.alert("您已经报名,请不要重复报名!",{
                    icon: 5,
                    skin: 'layer-ext-moon'
                });
            }else if(data == "200"){
                layer.alert("报名成功!",{
                    icon: 6,
                    skin: 'layer-ext-moon'
                });
            }
        });
    }

}
