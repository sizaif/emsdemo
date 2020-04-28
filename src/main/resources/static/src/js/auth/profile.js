$(function(){
    layui.use(['layer','form','laydate'], function(){
        var layer = layui.layer;
        var form  = layui.form;
        var laydate = layui.laydate;

        //监听提交
        form.on('submit(UserSumbit)', function(data){

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
function editbirth(obj) {
    layui.use(['laydate','layer'], function(){
        var laydate = layui.laydate;
        var layer =layui.layer;
        laydate.render({
            elem: '#birth3',
            type: 'datetime',
            value:obj
        });

        layer.open({
            type:1,
            title: "编辑详细信息",
            fixed:false,
            resize :false,
            offset:'l',
            shadeClose: true,
            btn: '关闭',
            area: ['400px', '300px'],
            content:$('#eidtUser'),
            end: function(){
                location.reload();
            }
        });
    });
}
