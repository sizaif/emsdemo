$(function(){
    layui.use(['layer','form','laydate'], function(){
        var layer = layui.layer;
        var form  = layui.form;
        var laydate = layui.laydate;


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

        // //监听提交
        // form.on('submit(UserSumbit)', function(data){
        //
        //     layer.confirm('确定提交么？', {
        //         btn: ['返回','确认'] //按钮
        //     },function(){
        //         layer.closeAll();
        //     },function() {
        //         // layer.closeAll();//关闭所有弹框
        //         console.log("data--"+data);
        //         formSubmit(data);
        //     });
        //     return false;
        // });



        form.render();
    });
});