

$(function(){
    layui.use(['layer','form'], function(){
        var layer = layui.layer;
        var form  = layui.form;


        //监听提交
        form.on('submit(UserSumbit)', function(data){

            var

            layer.confirm('确定提交么？', {
                btn: ['返回','确认'] //按钮
            },function(){
                layer.closeAll();
            },function() {
                // layer.closeAll();//关闭所有弹框
                submitAjax(data);
            });
            console.log($("#UserForm").serialize());


            return false;
        });

        form.render();
    });
});
//提交数据
function submitAjax(obj) {
    console.log(obj)
    // if( obj.flag == "update"){
    //     $.ajax({
    //         type: "POST",
    //         data: $("#UserForm").serialize(),
    //         // url: "/auth/setRole",
    //         success: function (data) {
    //             if (data == "200") {
    //                 layer.alert("操作成功",function(){
    //                     layer.closeAll();
    //                     load();
    //                 });
    //             } else {
    //                 layer.alert(data);
    //             }
    //         },
    //         error: function (data) {
    //             layer.alert("操作请求错误，请您联系技术人员");
    //         }
    //     });
    // }else if(obj.flag == "add"){
    //     $.ajax({
    //         type: "POST",
    //         data: $("#RoleForm").serialize(),
    //         url: "/auth/addRole",
    //         success: function (data) {
    //             if (data == "200") {
    //                 layer.alert("操作成功",function(){
    //                     layer.closeAll();
    //                     load();
    //                 });
    //             } else {
    //                 layer.alert(data);
    //             }
    //         },
    //         error: function (data) {
    //             layer.alert("操作请求错误，请您联系技术人员");
    //         }
    //     });
    // }
}
// 切换是否启用
function switchEnable(id,name,flag) {

    var isEnabled = flag;
    console.log(isEnabled);
    var state = isEnabled == true ? "启用":"未启用";
    layer.confirm('您确定要把用户：<font style=\'font-weight:bold;\' color=\'blue\'>'+name+'</font>设置为 <font style=\'font-weight:bold;\' color=\'red\'>'+state+'</font> 状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/users/setEnabled",{"id":id,"isEnabled":isEnabled},function(data){

            if(data=="200"){
                //回调弹框
                layer.alert("操作成功！",function(){
                    layer.closeAll();
                    //加载load方法
                    parent.location.reload();
                });
            }else{
                layer.alert(data,function(){
                    layer.closeAll();
                    //加载load方法
                    parent.location.reload();
                });
            }

        });
    }, function(){
        layer.closeAll();
        //加载load方法
        parent.location.reload();
    });

}

// 切换是否禁用
function switchLocked(id,name,flag) {

    var isLocked = flag;
    console.log(isLocked);
    var state = isLocked == false ? "未锁定":"锁定";
    layer.confirm('您确定要把用户：<font style=\'font-weight:bold;\' color=\'blue\'>'+name+'</font>设置为 <font style=\'font-weight:bold;\' color=\'red\'>'+state+'</font> 状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/users/setLocked",{"id":id,"isLocked":isLocked},function(data){

            if(data=="200"){
                //回调弹框
                layer.alert("操作成功！",function(){
                    layer.closeAll();
                    //加载load方法
                    parent.location.reload();
                });
            }else{
                layer.alert(data,function(){
                    layer.closeAll();
                    //加载load方法
                    parent.location.reload();
                });
            }

        });
    }, function(){
        layer.closeAll();
        //加载load方法
        parent.location.reload();
    });

}


// 修改
function edit(obj) {

    console.log(obj);
    var isLocked = obj.users.locked;
    if(isLocked){
        layer.msg("该用户已经被锁定，不可进行编辑；</br>  如需编辑，请设置为<font style='font-weight:bold;' color='green'>未锁定</font>状态。", {icon: 2});
    }else{

        $("#id2").val(obj.users.id==null?'':obj.users.id);
        $("#id").val(obj.users.id==null?'':obj.users.id);
        $("#truename").val(obj.truename==null?'':obj.truename);
        $("#school").val(obj.school==null?'':obj.school);
        $("#mobile").val(obj.users.mobile==null?'':obj.users.mobile);
        $("#email").val(obj.email==null?'':obj.email);
        $("#email2").val(obj.email==null?'':obj.email);
        $("#telephone").val(obj.phone==null?'':obj.phone);
        $("#address").val(obj.address==null?'':obj.address);
        $("#username").val(obj.users.name==null?'':obj.users.name);

        $("#flag").val("update");


        var existRole='';

        if(obj.users.userRoles !=null ){
            existRole+=obj.users.userRoles.roleId+',';
            // console.log("item.roleid->"+existRole);

        }
        // console.log("existRole->" +existRole);
        $("#roleDiv").empty();
        // 全部角色信息 显示角色数据
        $.get("/auth/getRoles",function(data){
            // console.log(data);

            var jsonobj= eval('(' + data + ')');
            $.each(jsonobj,function (index, item) {

                var div=$("<option  name='roleId' value="+item.id+" title="+item.descpt+">"+
                    "<span>"+item.descpt+"</span>"+
                    "</option>");
                if(existRole!='' && existRole.indexOf(item.id)>=0){

                    div=$("<option   name='roleId' value="+item.id+" title="+item.descpt+" selected >"+
                        "<span>"+item.descpt+"</span>"+
                        "</option>");
                }
                $("#roleDiv").append(div);
            });
            // 修改性别
            if(obj.gender == 0){
                // console.log(obj.gender);
                $("#men").removeAttr("checked");
                $("#women").attr("checked","checked");
            }
            layui.form.render('radio');
            layui.form.render('select');
        });

        layer.open({
            type:1,
            title: "编辑用户详细信息",
            fixed:false,
            resize :false,
            offset:'auto',
            shadeClose: true,
            btn: '关闭',
            area: ['600px', '720px'],
            content:$('#eidtUser'),
            end: function(){
                location.reload();
            }
        });

    }
}