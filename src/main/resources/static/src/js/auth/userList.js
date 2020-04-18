

$(function(){
    layui.use('layer', function(){
        var layer = layui.layer;

        // //监听工具条
        // table.on('tool(userTable)', function(obj){
        //     var data = obj.data;
        //     if(obj.event === 'del'){
        //         delUser(data,data.id,data.username);
        //     } else if(obj.event === 'edit'){
        //         //编辑
        //         getUserAndRoles(data,data.id);
        //     } else if(obj.event === 'recover'){
        //         //恢复
        //         recoverUser(data,data.id);
        //     }
        // });
        // //监听提交
        // form.on('submit(userSubmit)', function(data){
        //     // TODO 校验
        //     formSubmit(data);
        //     return false;
        // });

    });
});
function switchEnable(id,name,flag) {

    var isEnabled = flag;
    console.log(isEnabled);
    var state = isEnabled == true ? "启用":"关闭";
    layer.confirm('您确定要把用户：'+name+'设置为 "'+state+'" 状态吗？', {
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

function switchLocked(id,name,flag) {

    var isLocked = flag;
    console.log(isLocked);
    var state = isLocked == false ? "未锁定":"锁定";
    layer.confirm('您确定要把用户：'+name+'设置为 "'+state+'" 状态吗？', {
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