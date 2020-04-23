

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

    // 初始化datatable
    $('#databale2').DataTable( {
        // 件数选择下拉框内容
        "lengthMenu": [5,10, 25, 50, 75, 100],
        "language": {
            "processing": "DataTables is currently busy",
            // 当前页显示多少条
            "search": "搜索",

            "lengthMenu": "显示 _MENU_ 数据",
            // _START_（当前页的第一条的序号） ,_END_（当前页的最后一条的序号）,_TOTAL_（筛选后的总件数）,
            // _MAX_(总件数),_PAGE_(当前页号),_PAGES_（总页数）
            "info": "显示第 _PAGE_ 页,共 _PAGES_ 页",
            // 没有数据的显示（可选），如果没指定，会用zeroRecords的内容
            "emptyTable": "Soorry 没有找到数据",
            // 筛选后，没有数据的表示信息，注意emptyTable优先级更高
            "zeroRecords": "Sorry 没有搜索到",
            // 千分位的符号，只对显示有效，默认就是","  一般不要改写
            //"thousands": "'",
            // 小数点位的符号，对输入解析有影响，默认就是"." 一般不要改写
            //"decimal": "-",
            // 翻页按钮文字控制
            "paginate": {
                "first": "首页",
                "last": "尾页",
                "next": "下一页",
                "previous": "前一页"
            },
            // Client-Side用，Server-Side不用这个属性
            "loadingRecords": "Please wait - loading..."
        },
        dom: "<'button-group'B>"+"<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",

        buttons: [
            {
                extend: 'print',
                text: '打印',
                className:'glyphicon glyphicon-print'
            },
            {
                extend: 'csv',
                text: "excel",
                className:'glyphicon glyphicon-save'
            },
            {
                text: '刷新',
                className:'glyphicon glyphicon-refresh',
                action: function ( e, dt, node, config ) {
                    window.location.reload();
                }
            }
        ],
        ajax: {
            url: '/users/myData',
            dataSrc: ''
        },
        columns: [
            {
                data:'users.id',
                title: "序列"
            },
            {
                data:'users.name',
                title: "用户名"
            },
            {
                data:'truename',
                title: "姓名"
            },
            {
                data:'image',
                title: "头像",
                render: function (data, type, row, meta) {
                    // <img th:src="@{/images/}+${jsonuserlist.getImage()}" class="avatar" alt="Avatar">
                    return "<img src='/images/" + row.image + "' class='avatar' alt='Avatar' ></img>"
                }
            },
            {
                data:'users.role',
                title: "用户组"
            },
            {
                data:'users.createDate',
                title: "创建时间"
            },
            {
                data:'users.modifyDate',
                title: "修改时间"
            },
            {
                data:'users.lastLoginDate',
                title: "上次登陆时间"
            },
            {
                data:'users.lastLoginIp',
                title: "上次登录IP"
            },
            {
                data:'users.lockDate',
                title: "锁定时间"
            }
            // {data:'users.name'},

        ]
    } );
});
// 检查选中的角色
function checkRole(){

    // 留坑
    // console.log($("#UserForm").serialize());

    //
    // //选中的角色
    // var array = new Array();
    // var roleCheckd=$(".layui-form-selected");
    //
    // //获取选中的权限id
    // for(var i=0;i<roleCheckd.length;i++){
    //     array.push($(roleCheckd.get(i)).prev().val());
    // }
    //校验是否授权
    var roleIds =document.getElementById("roleDiv").value;
    $("#roleIds").val(roleIds);
    // console.log("roleIds-->"+roleIds);
    return true;
}
//提交数据
function formSubmit(obj) {

    if(checkRole()){
        if($("#flag").val() == "update"){
            // console.log($("#UserForm").serialize());
            $.ajax({
                type: "POST",
                data: $("#UserForm").serialize(),
                url: "/users/updateUMRInfo",
                success: function (data) {
                    if (data == "200") {
                        layer.alert("操作成功",function(){
                            layer.closeAll();
                            // load();
                        });
                    } else {
                        layer.alert("SSSS");
                    }
                },
                error: function (data) {
                    layer.alert("操作请求错误，请您联系技术人员");
                }
            });
        }else if($("#flag").val() == "add"){
            console.log($("#UserForm").serialize());
            $.ajax({
                type: "POST",
                data: $("#UserForm").serialize(),
                url: "/users/addUMRInfo",
                success: function (data) {
                    console.log(data);
                    if (data == "200") {
                        layer.alert("添加成功!", function () {
                            layer.closeAll();
                        });
                    } else if(data == "100"){
                        layer.alert("账户已存在,请重试", function () {
                            layer.closeAll();
                        });
                    }
                    else if(data == "101"){
                        layer.alert("邮箱已存在,请重试", function () {
                            layer.closeAll();
                        });
                    }
                },
                error: function (data) {
                    layer.alert("操作请求错误，请您联系技术人员");
                }
            });
        }
    }

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

function check(obj) {

        $("#id2").val(obj.users.id==null?'':obj.users.id);

        $("#id").val(obj.users.id==null?'':obj.users.id);
        $("#id").attr("disabled","disabled");
        $("#truename").val(obj.truename==null?'':obj.truename);
        $("#truename").attr("disabled","disabled");
        $("#school").val(obj.school==null?'':obj.school);
        $("#school").attr("disabled","disabled");
        $("#mobile").val(obj.users.mobile==null?'':obj.users.mobile);
        $("#mobile").attr("disabled","disabled");
        $("#email").val(obj.email==null?'':obj.email);
        $("#email").attr("disabled","disabled");
        $("#email2").val(obj.email==null?'':obj.email);
        $("#email2").attr("disabled","disabled");
        $("#telephone").val(obj.phone==null?'':obj.phone);
        $("#telephone").attr("disabled","disabled");
        $("#address").val(obj.address==null?'':obj.address);
        $("#address").attr("disabled","disabled");
        $("#username").val(obj.users.name==null?'':obj.users.name);
        $("#username").attr("disabled","disabled");
        $("#birth").attr("disabled","disabled");
        $("#password").attr("disabled","disabled");
        $("#password2").attr("disabled","disabled");
        $("#roleDiv").attr("disabled","disabled");
        $("#men").attr("disabled","disabled");
        $("#women").attr("disabled","disabled");

        $("#send").remove();
        $("#reset").remove();


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



        layui.use(['laydate','layer'], function(){
            var laydate = layui.laydate;
            var layer =layui.layer;
            laydate.render({
                elem: '#birth',
                type: 'datetime',
                value:obj.birth
            });
            layer.open({
                type:1,
                title: "查看用户详细信息",
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

        // start 初始化 全部角色信息 全部角色信息 显示角色数据
        var existRole='';
        if(obj.users.userRoles !=null ){
            existRole+=obj.users.userRoles.roleId+',';
            // console.log("item.roleid->"+existRole);

        }
        // console.log("existRole->" +existRole);
        $("#roleDiv").empty();
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

            // start 初始化 性别
            if(obj.gender == 0){
                // console.log(obj.gender);
                $("#men").removeAttr("checked");
                $("#women").attr("checked","checked");
            }
            layui.form.render('radio');
            // end 初始化 性别

            layui.form.render('select');
        });
        // end 初始化 全部角色信息

        // start  初始化 出生日期 弹窗
        layui.use(['laydate','layer'], function(){
            var laydate = layui.laydate;
            var layer =layui.layer;
            laydate.render({
                elem: '#birth',
                type: 'datetime',
                value:obj.birth
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
        });
        // end  初始化 出生日期 弹窗

    }
}

// 添加
function add(obj) {

    $("#flag").val("add");
    $("#id").remove();
    var existRole='';
    // start 全部角色信息 显示角色数据
    $("#roleDiv").empty();

    $.get("/auth/getRoles",function(data){
        console.log(data);

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
        layui.form.render('select');
    });
    // end 全部角色信息 显示角色数据

    // start 初始化出生日期 和弹窗
    layui.use(['laydate','layer'], function(){
        var laydate = layui.laydate;
        var layer =layui.layer;
        laydate.render({
            elem: '#birth'
            ,type: 'datetime'
        });
        layer.open({
            type:1,
            title: "添加一个用户以及信息",
            fixed:false,
            resize :false,
            offset:'auto',
            shadeClose: true,
            btn: '关闭',
            area: ['600px', '740px'],
            content:$('#eidtUser'),
            end: function(){
                location.reload();
            }
        });
    });
    // end 初始化出生日期 和弹窗

}

// 删除

function del(id,name) {
    if(null!=id){
        layer.confirm('您确定要 <font style=\'font-weight:bold;\' color=\'red\'>删除: </font><font style=\'font-weight:bold;\' color=\'blue\'>'+name+'</font> 用户么?', {
            btn: ['确认','返回'] //按钮
        },  function(){
            $.get("/users/toDeleteUser",{"id":id},function(data){
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
