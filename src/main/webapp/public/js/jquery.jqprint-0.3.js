
(function($) {
    var opt;

    $.fn.jqprint = function (options) {
        opt = $.extend({}, $.fn.jqprint.defaults, options);

        var $element = (this instanceof jQuery) ? this : $(this);
        
        if (opt.operaSupport && $.browser.opera) 
        { 
            var tab = window.open("","jqPrint-preview");
            tab.document.open();

            var doc = tab.document;
        }
        else 
        {
            var $iframe = $("<iframe  />");
        
            if (!opt.debug) { $iframe.css({ position: "absolute", width: "0px", height: "0px", left: "-600px", top: "-1000px" }); }

            $iframe.appendTo("body");
            var doc = $iframe[0].contentWindow.document;
        }
        debugger;
        if (opt.importCSS)
        {
            if ($("link[media=print]").length > 0) 
            {
                $("link[media=print]").each( function() {
                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' media='print' \/>");
                });
                doc.write("<script type='text/javascript' src='/pages/public/js/jquery-1.8.0.min.js' ><\/script>");

            }
            else 
            {
                $("link").each( function() {
                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' />");
                });
            }
        }
        
        if (opt.printContainer) { doc.write($element.outer()); }
        else { $element.each( function() { doc.write($(this).html()); }); }
        
        doc.close();
        (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow).focus();
        var printWin= (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow);

        setTimeout( function() { reSetLeftTop(printWin);printWin.focus();printWin.print(); if (tab) { tab.close(); } }, 100);
    }
    
    $.fn.jqprint.defaults = {
		debug: false,
		importCSS: true,
		printContainer: true,
		operaSupport: true
	};

    // Thanks to 9__, found at http://users.livejournal.com/9__/380664.html
    jQuery.fn.outer = function() {
      var ohtml= $($('<div></div>').html(this.clone())).html();
      //alert(ohtml);
      return ohtml;
    } 
})(jQuery);