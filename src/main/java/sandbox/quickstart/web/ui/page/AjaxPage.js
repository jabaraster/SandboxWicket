(function() {
    var vars = sandbox.quickstart.AjaxPageVariables;

    $(initialize);

    function initialize() {
        $('#' + vars.customSubmitterId).click(function() {
            onCustomSubmitterClick();
            return false;
        });

        var file = $('#' + vars.fileId);
        var bt = $('#' + vars.customSubmitterId);
        $('#' + vars.fileId).bind('change', function() {
            if (file.val()) {
                bt.removeAttr('disabled');
            } else {
                bt.attr('disabled', 'disabled');
            }
        });
    }

    function onCustomSubmitterClick() {
        var fd = new FormData(document.getElementById(vars.formId));
        $.ajax({
            url: vars.ajaxSubmitUrl,
            type: 'post',
            data: fd,
            contentType: false,
            processData: false,
            dataType: 'text',
            xhr: function() {
                var xhr = $.ajaxSettings.xhr();
                $(xhr.upload).on('progress', function(e) {
                    var evt = e.originalEvent;
                    var monitor = $('#progressMonitor');
                    var percent = evt.loaded / evt.total * 100;
                    monitor.text('アップロード中... ' + Math.floor(percent) + '%');
                    monitor.css('width', percent + '%');
                });
                return xhr;
            },
            beforeSend: function() {
                if (!$('#' + vars.fileId).val()) return false;
                return true;
            },
            dummy: null
       }).done(function(pData) {
           console.log(pData);
           Wicket.Ajax.process(pData);
           $('#progressMonitor').text('アップロード完了！');
       });
    }
})();
