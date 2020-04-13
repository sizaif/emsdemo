/**
 * 权限列表
 */
$(function() {
    // //初始化treegrid 页面表格
    // layui.config({
    //     base: '../treegrid/'
    // }).use(['laytpl', 'treegrid'], function () {
    //     var laytpl = layui.laytpl,
    //         treegrid = layui.treegrid;
    //     treegrid.config.render = function (viewid, data) {
    //         var view = document.getElementById(viewid).innerHTML;
    //         return laytpl(view).render(data) || '';
    //     };
    //
    //     var treeForm=treegrid.createNew({
    //         elem: 'permTable',
    //         view: 'view',
    //         data: { rows: permList },
    //         parentid: 'pid',
    //         singleSelect: false
    //     });
    //     treeForm.build();
    //
    // });
    //操作
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(permSubmit)', function(data){
            //校验 TODO
            $.ajax({
                type: "POST",
                data: $("#permForm").serialize(),
                url: "/auth/setPermission",
                success: function (data) {

                    if (data == "200") {
                        layer.alert("操作成功",function(){
                            layer.closeAll();
                        });
                    } else {
                        layer.alert(data);
                    }
                },
                error: function (data) {
                    layer.alert("操作请求错误，请您稍后再试");
                }
            });
            return false;
        });
        form.render();
    });

});
//编辑
function edit(id,type){
    // console.log("edit ---> ",id);
    if(null!=id){
        $("#type").val(type);
        $("#id").val(id);
        $.get("/auth/getPermission",{"id":id},function(data) {
            // console.log(data);
            if(null!=data){
                $("input[name='name']").val(data.name);
                $("input[name='code']").val(data.code);
                $("input[name='page']").val(data.page);
                $("textarea[name='descpt']").text(data.descpt);
                $("#pid").val(data.pid);
                data.istype==0?$("input[name='istype']").val(0).checked:$("input[name='istype']").val(1).checked;
                layer.open({
                    type:1,
                    title: "更新权限",
                    fixed:false,
                    resize :false,
                    shadeClose: true,
                    btn: '关闭',
                    area: ['500px', '580px'],
                    content:$('#updatePerm'),
                    end:function(){
                        location.reload();
                    }
                });
            }else{
                layer.alert("获取权限数据出错，联系技术开发人员");
            }
        });
    }
}
//添加权限
function addPerm(pid,flag){
    // console.log("addperm ---> ",pid);
    if(null!=pid){
        //flag[0:开通权限；1：新增子节点权限]
        //type[0:编辑；1：新增]
        if(flag==0){
            // 新增情况下 传过来的pid  设置为0 ,所以  传过来的值无所谓
            //设置类型为 新增
            $("#type").val(1);
            // 设置父节点为0
            $("#pid").val(0);

        }else{
            //设置父id
            $("#type").val(1);
            $("#pid").val(pid);
        }
        layer.open({
            type:1,
            title: "添加权限",
            fixed:false,
            resize :false,
            shadeClose: true,
            btn: '关闭',
            area: ['500px', '580px'],
            content:$('#updatePerm'),  //页面自定义的div，样式自定义
            end:function(){
                location.reload();
            }
        });
    }
}
//删除
function del(id,name){
    console.log("===删除id："+id);
    if(null!=id){
        layer.confirm('您确定要删除'+name+'权限吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/auth/delPermission",{"id":id},function(data){
                if(data == "200"){
                    //回调弹框
                    layer.alert("删除成功！",function(){
                        layer.closeAll();
                        //加载load方法
                        location.reload();//自定义
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

//关闭弹框
function close(){
    layer.closeAll();
}