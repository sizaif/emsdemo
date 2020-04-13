/ DataTable初始化
var t = dataTableInit();
//---------------------------点击事件区域-------------------------
//DataTable 行点击事件，用来设置样式点击事件
$('#datatable tbody').on('click', 'tr', function () {
    if (!$(this).hasClass('selected') ) {
        t.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
} );
//DataTable重新渲染时序号一栏重新进行编号点击事件
t.on( 'draw.dt', function () {
    var PageInfo = $('#datatable').DataTable().page.info();
    t.column(0, { page: 'current' }).nodes().each( function (cell, i) {
        cell.innerHTML = i + 1 + PageInfo.start;
    } );
} );
//刷新按钮事件
$("#refreshBt").click(function () {
    // 刷新表格数据，分页信息不会重置
    t.ajax.reload( null, false );
});



//模板下载按钮点击事件
$("#dowloadBtn").click(function () {
    templateDowload()
});
//导入按钮点击事件
$("#importBtn").click(function () {
    importExcel();
});
//打印按钮点击事件
$("#printBtn").click(function () {
    var data = t.rows(['.selected']).data()[0];
    debugger
    if(undefined===data){
        debugger
        swal({
            type: 'warning',
            title: '提示:',
            text: '请首先选择一行数据！',
            confirmButtonColor: "#1ab394",
        })
    }else if(data.status.trim()==="1") {
        debugger
        swal({
            type: 'warning',
            title: '提示:',
            text: '请先提交！',
            confirmButtonColor: "#1ab394",
        })
    }else
        printCode(data.id);

});

// //生成入库单数据按钮点击事件
// $("#InOrderBtn").click(function () {
//     var data = t.rows(['.selected']).data()[0];
//     debugger
//     if(undefined===data){
//         swal({
//             type: 'warning',
//             title: '提示:',
//             text: '请首先选择一行数据！',
//             confirmButtonColor: "#1ab394",
//         })
//     }else if((data.status.trim()==="4")) {
//         swal({
//             type: 'warning',
//             title: '提示:',
//             text: '已经生成了入库单数据！',
//             confirmButtonColor: "#1ab394",
//         })
//     }elseif(data.status.trim()==="1"||data.status.trim()==="2") {
//         debugger
//         swal({
//             type: 'warning',
//             title: '提示:',
//             text: '打印后才能生成入库单数据！',
//             confirmButtonColor: "#1ab394",
//         })
//     }else
//     submitInOrder(data.id);
//
// });

//提交按钮点击事件
$("#submitBtn").click(function () {
    var data = t.rows(['.selected']).data()[0];
    if(undefined===data){
        swal({
            type: 'warning',
            title: '提示:',
            text: '请首先选择一行数据！',
            confirmButtonColor: "#1ab394",
        })
    } else if(data.status.trim()==="3") {
        debugger
        swal({
            type: 'warning',
            title: '提示:',
            text: '已打印不能再提交！',
            confirmButtonColor: "#1ab394",
        })
    }else if(data.status.trim()==="4") {
        debugger
        swal({
            type: 'warning',
            title: '提示:',
            text: '已入库不能再提交！',
            confirmButtonColor: "#1ab394",
        })
    }
    else if(data.status.trim()==="2") {
        debugger
        swal({
            type: 'warning',
            title: '提示:',
            text: '该订单状态已提交！',
            confirmButtonColor: "#1ab394",
        })
    }else{
        submitPrint(data.id);
    }

});



function dataTableInit(){
    return $('#datatable').DataTable({
        "language": {
            "processing": "处理中...",
            "lengthMenu": "显示 _MENU_ 项结果",
            "zeroRecords": "没有匹配结果",
            "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "infoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "infoFiltered": "(由 _MAX_ 项结果过滤)",
            "infoPostFix": "",
            "search": "搜索:",
            "searchPlaceholder": "搜索...",
            "url": "",
            "emptyTable": "表中数据为空",
            "loadingRecords": "载入中...",
            "infoThousands": ",",
            "paginate": {
                "first": "首页",
                "previous": "上页",
                "next": "下页",
                "last": "末页"
            },
            "aria": {
                paginate: {
                    first: '首页',
                    previous: '上页',
                    next: '下页',
                    last: '末页'
                },
                "sortAscending": ": 以升序排列此列",
                "sortDescending": ": 以降序排列此列"
            },
            "decimal": "-",
            "thousands": "."
        },
        "processing": true,
        "searching" : false,
        "serverSide": true,
        "stateSave": true,
        "pageLength": 5,
        "lengthMenu": [ 5,10, 25, 50, 75, 100 ],
        "dom": '<"top">rt<"bottom"flpi><"clear">',
        "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
        columns: [
            { data: 'uid' },
            { data: 'ugender' },
            // { data: 'deliveryTime',
            //     render: function (data, type, full, meta ) {
            //         return moment(data).format("YYYY-MM-DD ");
            //     } },
            { data: 'ulevel' },
            { data: 'uname' },
            { data: 'unickname' },
            { data: 'uimage' },
            { data: 'uphone' },
            { data: 'ucreatetime',
                render: function (data, type, full, meta ) {
                    return moment(data).format("YYYY-MM-DD HH:mm:ss");
                } },

            { data: 'status',
                render: function (data, type, full, meta ) {
                    if(data.trim()=="1"){
                        return '未提交'
                    }else if(data.trim()=="2"){
                        return '已提交'
                    }else if(data.trim()=="3"){
                        return '已打印'
                    }else if(data.trim()=="4"){
                        return '已入库'
                    }else{
                        return '未知状态'
                    }
                }  },
        ],
        "ajax": function (data, callback, setting) {
            $.each(data.columns,function (index,value) {
                //匹配所有input
                $("#searchCondition").find('input[type="text"]').each(function () {
                    if(value.data === $(this).attr('id')){
                        value.search.value = $(this).val();
                    }
                });
                //匹配所有select
                debugger
                $("#searchCondition select").each(function () {
                    debugger
                    if(value.data === $(this).attr('id')){
                        value.search.value = $(this).val();
                    }
                });
            })
            $.ajax({
                type: 'POST',
                url: "/userlist",
                cache: false,  //禁用缓存
                data: JSON.stringify(data),  //传入组装的参数
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    debugger
                    callback(result);
                }
            })
        }
    })
}