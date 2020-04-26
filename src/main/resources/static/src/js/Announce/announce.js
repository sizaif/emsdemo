$(function(){
    layui.extend({tinymce: '/vendors/tinymce/tinymce'}).use(['tinymce', 'util', 'layer','form','element'], function () {
        var tinymce = layui.tinymce
        var layer = layui.layer;
        var form  = layui.form;
        var element = layui.element;

        var edit = tinymce.render({
            elem: "#editmemo"
            , height: 600
            , width:'100%'
        });
        //

        //监听提交
        form.on('submit(AnnounceSumbit)', function(data){


            // 获取编辑器文本内容
            var inputtext = tinymce.get('#editmemo').getContent({format:'text'});
            // 获取内容 添加到 memo中
            $("#memohidden").val(inputtext);
            // layer.alert(inputtext);
            layer.confirm('确定提交么？', {
                btn: ['确认','返回'] //按钮
            },function(){
                // layer.closeAll();//关闭所有弹框
                formSubmit();
            },function() {
                layer.closeAll();
            });
            return false;
        });
        form.render();

    });
});


//提交数据
function formSubmit() {

    console.log($("#AnnounceForm").serialize(),);
    if($("#flag").val() == "update"){
        // console.log($("#UserForm").serialize());
        $.ajax({
            type: "POST",
            data: $("#AnnounceForm").serialize(),
            url: "/admin/announce/updateAnnounce",
            success: function (data) {
                if (data == "200") {
                    layer.alert("操作成功",function(){
                        parent.location.reload();
                        // load();
                    });
                } else {
                    layer.alert("操作失败" + data);
                    parent.location.reload();
                }
            },
            error: function (data) {
                layer.alert("操作请求错误，请您联系技术人员");
            }
        });
    }else if($("#flag").val() == "add"){
        // console.log($("#UserForm").serialize());
        $.ajax({
            type: "POST",
            data: $("#AnnounceForm").serialize(),
            url: "/admin/announce/addAnnounce",
            success: function (data) {
                console.log(data);
                if (data == "200") {
                    layer.alert("添加成功!", function () {
                        parent.location.reload();
                    });
                } else {
                    layer.alert("添加失败", function () {
                        parent.location.reload();
                    });
                }

            },
            error: function (data) {
                layer.alert("操作请求错误，请您联系技术人员");
            }
        });
    }

}
// 切换是否启用
function switchEnable(id,flag) {

    var isEnabled = flag;
    console.log(isEnabled);
    var state = isEnabled == true ? "启用":"未启用";
    var Enabled = isEnabled == true ? 1:0;
    layer.confirm('您确定要把公告：<font style=\'font-weight:bold;\' color=\'blue\'>'+id+'</font>设置为 <font style=\'font-weight:bold;\' color=\'red\'>'+state+'</font> 状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/admin/announce/setEnabled",{"id":id,"isEnabled":Enabled},function(data){

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
    $("#flag").val("update");
    $("#id").val(obj.id == null?'':obj.id);
    $("#creatorId").val(obj.creatorId == null?'':obj.creatorId);
    $("#creatorName").val(obj.creatorName == null?'':obj.creatorName);


    layui.extend({tinymce: '/vendors/tinymce/tinymce'}).use(['tinymce', 'util', 'layer'], function () {
        var tinymce = layui.tinymce
        tinymce.render({
            elem: "#editmemo"
            , height: 600
            , width: '100%'
        });
        tinymce.get('#editmemo').setContent(obj.memo);
    });

    $("#showAccordion").addClass("layui-show")

}

// 添加
function add(uid,uname) {
    $("#flag").val("add");
    // start 初始化出生日期 和弹窗
    $("#creatorId").val(uid);
    $("#creatorName").val(uname);

    $("#showAccordion").addClass("layui-show")

    // end 初始化出生日期 和弹窗
}

// 删除

function del(id) {
    if(null!=id){
        layer.confirm('您确定要 <font style=\'font-weight:bold;\' color=\'red\'>删除序号: '+id+' </font> 的公告么?', {
            btn: ['确认','返回'] //按钮
        },  function(){
            $.get("/admin/announce/delAnnounce",{"id":id},function(data){
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