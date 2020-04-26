$(function(){
    layui.use(['layer','form','laydate'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;
    });
});

function check(cid,uid,type,isEnabled) {


    console.log(cid+" "+uid+" "+type);
    var static = isEnabled == 1 ? "审核通过":"取消已报名状态";

    if(type == "alone"){

        layer.confirm('确定 <font style=\'font-weight:bold;\' color=\'blue\'>'+static+'</font>'+'?', {
            btn: ['确认','返回'] //按钮
        },function(){
            // layer.closeAll();//关闭所有弹框
            // console.log("data--"+data);
            formSubmit(cid,uid,isEnabled);
        },function() {
            layer.closeAll();
        });
    }else if( type =="team"){
        layer.confirm('确定<font style=\'font-weight:bold;\' color=\'blue\'>'+static+'</font>'+'?', {
            btn: ['确认','返回'] //按钮
        },function(){
            // layer.closeAll();//关闭所有弹框
            // console.log("data--"+data);
            formSubmit(cid,uid,isEnabled);
        },function() {
            layer.closeAll();
        });
    }

}

function  formSubmit(cid,id,isEnabled) {

    $.ajax({
        type: "POST",
        data: {"type":type,"cid":cid,"id":id,"isEnabled":isEnabled},
        url: "/admin/contest/checkCmtEnabled",
        success: function (data) {
            if (data == "200") {
                layer.alert("操作成功",function(){
                    layer.closeAll();
                    // load();
                });
            } else {
                layer.alert("操作失败");
            }
        },
        error: function (data) {
            layer.alert("操作请求错误，请您联系技术人员");
        }
    });
}