(function(){

    $.fn.extend({

        scrollHeader: function(){

            this.each(function(){

                $(window).on('scroll', function(e){
                    var scroll = $(window).scrollTop();

                    if(scroll > 150){
                        
                    }
                });

            });

        }

    });

})(jQuery);