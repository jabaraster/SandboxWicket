(function() {
    var vars = sandbox.quickstart.AjaxPageVariables;

    $(initialize);

    function initialize() {
        $('#' + vars.customSubmitterId).click(function() {
            onCustomSubmitterClick();
            return false;
        });

        // ユーザがファイルを選択したらボタンを感化する.
        // 親要素に対してイベントリスナを設定しているのは、
        // 直接 input type="file" にイベントリストを設定すると
        // Wicket.Ajax.process()実行後にイベントリスナが削除されてしまうため.
        var file = $('#' + vars.fileId);
        var bt = $('#' + vars.customSubmitterId);
        $('#' + vars.formId).on('change', 'input[type="file"]', function() {
            if (file.val()) {
                onCustomSubmitterClick();
//                bt.removeAttr('disabled');
            } else {
//                bt.attr('disabled', 'disabled');
            }
        });
    }

    function onCustomSubmitterClick() {
        var submitAjax = function() {
            $('#' + vars.ajaxSubmitterId).get(0).click();
        };
        if (!window.FormData) {
            submitAjax();
            return;
        }

        var files = $('#' + vars.fileId).get(0).files;
        if (!files) {
            submitAjax();
            return;
        }

        if (files.length == 0) return;
        if (files[0].size > 5*1024*1024) { // 5MBを越えるなら
            if (!confirm('大きなサイズのファイルのアップロードには時間がかかる可能性があります。アップロードしてよろしいですか？')) {
                $('#' + vars.fileId).val(''); // クリアしておかないと、ユーザが同じファイルを選択したときにイベントが着火しない.
                return;
            }
        }

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
                    monitor.text('アップロード中... ' + Math.floor(percent) + '% (' + evt.loaded + "/" + evt.total + ")");
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
           Wicket.Ajax.process(pData);
           $('#' + vars.fileId).val('');
           $('#' + vars.customSubmitterId).attr('disabled', 'disabled');
           $('#progressMonitor').text('アップロード完了！');
       });
    }
})();
