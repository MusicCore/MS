
function loadingHide() {
    setTimeout(function () {
        $('#img_loading').css("display","none")
    },2000)
}

//index,search
function listMusic(url,data){
    $.ajax({
        url :  url,
        type : "GET",
        dataType:"json",
        data: data,
        success:function(result) {
            loadingHide()
            if(result.data=='') {
                return false}
            $.each(result.data,function (i,value) {
                $('#musicList').append(
                    "<div  class=\"col-md-12 column music_info\">\n" +
                    "                <div class=\"thumbnail touming show_red\">\n" +
                    "                    <div class=\"caption\" style=\"text-align: center\">\n" +
                    "                         <div class=\"thumbnail touming team-grids\" >\n" +
                    "                             <a href='javascript:void(0)' onclick='musicDetail("+value.id+")'>\n" +
                    "                                 <img src="+value.contentImg+" alt=\"图片加载失败了\" class=\"img-responsive\" style=\"height: 380px\">\n" +
                    "                                 <div class=\"capth\">\n" +
                    "                                     <h4 class=\"span_title\">"+value.title+"</h4>\n" +
                    "                                     <div><span class=\"span_info\">&nbsp;<span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;"+value.authorName+"</span>\n" +
                    "                                         <span class=\"span_info\">&nbsp;<span class=\"glyphicon glyphicon-time\"></span>&nbsp;&nbsp;"+value.createTime+"</span></div>\n" +
                    "                                     <p>"+value.contentShort+"</p>\n" +
                    "                                 </div>\n" +
                    "                             </a>\n" +
                    "                         </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>"
                )
            })
        }
    })
}

//index,search
function musicDetail(id) {
    window.open("/musicdetail?id=" + id)
}