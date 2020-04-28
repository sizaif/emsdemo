$(function(){

});

function info(id,type) {

    /**
     * 到 参赛的个人用户列表界面 或者 组队列表界面
     * @type {string}
     */
    window.location.href="/contest/ContestTMList?"+"id="+id+"&type="+type;

}

function info2(id,type) {

    /**
     * 到 根据赛事ID  到获奖名单
     * @type {string}
     */
    window.location.href="/contest/ContestRankList?"+"id="+id+"&type="+type;

}