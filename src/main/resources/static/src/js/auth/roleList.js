/**
 * 角色列表
 */
$(function() {
    // layui.use('table', function(){
    //     var table = layui.table;
    //     var tbody=$("#tbody");
    //     $.get("/auth/getRoleList",function(data){
    //         if(data!=null){
    //             tbody.empty();
    //             $.each(data, function (index, item) {
    //                 var td=$("<tr><td>"+item.code+"</td>"
    //                     +"<td>"+item.roleName+"</td>"
    //                     +"<td>"+item.descpt+"</td>"
    //                     +"<td>"+DateUtils.formatDate(item.insertTime)+"</td>"
    //                     +"<td>"
    //                     +"<button class='layui-btn layui-btn-xs' onclick='updateRole("+item.id+")'>编辑</button>"
    //                     +"<button class='layui-btn layui-btn-danger layui-btn-xs' onclick='delRole("+item.id+")'>删除</button></td></tr>");
    //                 tbody.append(td);
    //             });
    //         }
    //     });
    // });

    // layui.use(['layer'],function () {
    //     var layer= layui.layer;
    // });
    layui.use(['tree','util','form','layer'],function () {
        var form = layui.form;
        var layer= layui.layer;
        var tree = layui.tree;
        var util = layui.util;


        //监听提交
        form.on('submit(RoleSumbit)', function(data){
            //获取选中的权限复选框
            var treecheckdata  = tree.getChecked('demoId1');
            // layer.alert(JSON.stringify(treecheckdata), {shade:0});
            // console.log(treecheckdata);

            var nodeIds = new Array();
            nodeIds = getCheckedId(treecheckdata);
            //校验是否授权
            var permList = nodeIds;
            console.log("permList:"+permList);
            if(permList==null || permList==''){
                layer.alert("请给该角色添加权限菜单！");
                return false;
            }
            $("#rolePermIds").val(permList);

            var flag = document.getElementById("flag").value;

            if( flag == "update"){
                $.ajax({
                    type: "POST",
                    data: $("#RoleForm").serialize(),
                    url: "/auth/setRole",
                    success: function (data) {
                        if (data == "200") {
                            layer.alert("操作成功",function(){
                                layer.closeAll();
                                load();
                            });
                        } else {
                            layer.alert(data);
                        }
                    },
                    error: function (data) {
                        layer.alert("操作请求错误，请您联系技术人员");
                    }
                });
            }else if(flag == "add"){
                $.ajax({
                    type: "POST",
                    data: $("#RoleForm").serialize(),
                    url: "/auth/addRole",
                    success: function (data) {
                        if (data == "200") {
                            layer.alert("操作成功",function(){
                                layer.closeAll();
                                load();
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

            return false;
        });
        form.render();
    });

});
// 获取选中节点的id
function getCheckedId(jsonObj) {
    var id = "";
    $.each(jsonObj, function (index, item) {
        if (id != "") {
            id = id + "," + item.id;
        }
        else {
            id = item.id;
        }
        var i = getCheckedId(item.children);
        if (i != "") {
            id = id + "," + i;
        }
    });
    return id;
}
// 获得perm Tree 数据 并根据当前角色的id 设置 权限树状态
function getPermVoData(id) {
    var data;
    $.ajax({
        url: "/auth/getRolePermTree",    //后台数据请求地址
        data: {id:id},
        type: "post",
        async:false,
        success: function(resut){
            data = eval('('+resut+ ')');
            console.log(data)
        }
    });
    return data;
}
// 获得一个权限树,无状态
function getPermVODataNoParm() {
    var data;
    $.ajax({
        url: "/auth/getPermTree",    //后台数据请求地址
        type: "post",
        async:false,
        success: function(resut){
            data = eval('('+resut+ ')');
            console.log(data)
        }
    });
    return data;
}

function addRole(uid) {
    if( null != uid){
        var inser_uid = uid;
        console.log("操作 id -->  "+uid);

        //设置flag 类别
        $("input[name='flag']").val("add");

        // 设置当前操作的用户id
        $("input[name='insertUid']").val(uid);
        //设置权限树
        layui.use(['tree'], function(){
            var tree = layui.tree;
            tree.render({
                elem: '#treeDemo',
                data: getPermVODataNoParm(),
                showCheckbox: true,  //是否显示复选框
                id: 'demoId1',
                isJump: true , //是否允许点击节点时弹出新窗口跳转
                click: function(obj){
                    var data = obj.data;  //获取当前点击的节点数据
                    console.log(JSON.stringify(data))
                    layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
                }
            });
        });
        layer.open({
            type:1,
            title: "添加角色",
            fixed:false,
            resize :false,
            shadeClose: true,
            btn: '关闭',
            area: ['500px', '580px'],
            content:$('#updateRoleForm'),  //页面自定义的div，样式自定义
            end:function(){
                location.reload();
            }
        });
    }

}
function updateRole(id) {
    //isNaN是数字返回false
    console.log("updateRole ---> " + id);
    if(id!=null && !isNaN(id)){
        $("#id").val(id);
        $("input[name='flag']").val("update");
        $.get("/auth/getRoleById",{"id":id},function(data) {
            if(null!=data){
                var jsonobj= eval('(' + data + ')');
                console.log(jsonobj);
                // console.log(jsonobj[1])
                // console.log(jsonobj[0].roleName)
                $("input[name='roleName']").val(jsonobj.roleName);
                $("input[name='code']").val(jsonobj.code);
                $("textarea[name='descpt']").text(jsonobj.descpt);

                layui.use(['tree'], function(){
                    var tree = layui.tree;
                    tree.render({
                        elem: '#treeDemo',
                        data: getPermVoData(id),
                        showCheckbox: true,  //是否显示复选框
                        id: 'demoId1',
                        isJump: true , //是否允许点击节点时弹出新窗口跳转
                        click: function(obj){
                            var data = obj.data;  //获取当前点击的节点数据
                            layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
                        }
                    });
                });

                layer.open({
                    type:1,
                    title: "更新角色",
                    fixed:false,
                    resize :false,
                    shadeClose: true,
                    btn: '关闭',
                    area: ['500px', '580px'],
                    content:$('#updateRoleForm'),
                    end:function(){
                        location.reload();
                    }
                });
            }else{
                layer.alert("获取权限数据出错，联系技术开发人员");
            }
        });
    }else{
        layer.alert("请求参数有误，请您稍后再试");
    }
}
function delRole(id,name) {
    if(null!=id){
        layer.confirm('您确定要删除'+name+'角色吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/auth/delRole",{"id":id},function(data){
                if(data=="200"){
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

