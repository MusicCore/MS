
var toolbar_ = ['bold italic underline strikethrough alignleft aligncenter alignright outdent hr link image emoticons forecolor backcolor fullscreen']
var plugins_ = ['colorpicker colorpicker emoticons fullscreen hr image link textcolor']
tinymce.init({
    selector: '#comment_tinymce_child',
    language_url: '/static/tinymce4.7.5/langs/zh_CN.js',
    language: 'zh_CN',
    height: 200,
    body_class: 'panel-body ',
    object_resizing: false,
    toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar_,
    // menubar: 'file edit insert view format table',
    plugins: plugins_,
    end_container_on_empty_block: true,
    powerpaste_word_import: 'clean',
    code_dialog_height: 450,
    code_dialog_width: 1000,
    advlist_bullet_styles: 'square',
    advlist_number_styles: 'default',
    imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
    default_link_target: '_blank',
    link_title: false,
    nonbreaking_force_tab: true,
    autosave_ask_before_unload: false
});