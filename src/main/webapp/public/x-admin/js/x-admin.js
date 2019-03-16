layui.use(['element'], function () {
    $ = layui.jquery;
    element = layui.element();

    $('.layui-tab-title li').eq(0).find('i').remove();

    height = $('.layui-layout-admin .site-demo').height();
    $('.layui-layout-admin .site-demo').height(height - 100);

    if ($(window).width() < 750) {
        trun = 0;
        $('.x-slide_left').css('background-position', '0px -61px');
    } else {
        trun = 1;
    }

    //导航的hover效果、二级菜单等功能，需要依赖element模块
    // 侧边栏点击隐藏兄弟元素
    $('.layui-nav-item').click(cLayuiNavItem);

    $('.x-slide_left').click(cSlideLeft);

    //监听导航点击
    element.on('nav(side)', onNavSide);
});

function cLayuiNavItem(event) {
    $(this).siblings().removeClass('layui-nav-itemed');
}

function cSlideLeft(event) {
    if (trun) {
        $('.x-side').animate({left: '-255px'}, 255).siblings('.x-main').animate({left: '0px'}, 255);
        $(this).css('background-position', '0px -61px');
        trun = 0;
    } else {
        $('.x-side').animate({left: '0px'}, 255).siblings('.x-main').animate({left: '255px'}, 255);
        $(this).css('background-position', '0px 0px');
        trun = 1;
    }
}

function onNavSide(elem) {
    title = elem.find('cite').text();
    url = elem.find('a').attr('_href');
    // alert(url);

    for (var i = 0; i < $('.x-iframe').length; i++) {
        if ($('.x-iframe').eq(i).attr('src') == url) {
            element.tabChange('x-tab', i);
            return;
        }
    }
    ;

    res = element.tabAdd('x-tab', {
        title: title//用于演示
        , content: '<iframe frameborder="0" src="' + url + '" class="x-iframe"></iframe>'
    });


    element.tabChange('x-tab', $('.layui-tab-title li').length - 1);

    $('.layui-tab-title li').eq(0).find('i').remove();
}

